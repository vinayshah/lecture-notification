package in.vinayshah.lecturenotification;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import packageJson.Common;
import packageJson.JSONParser;


public class MainActivity extends Activity {
    private final Common common = new Common();
    private final JSONParser jsonParser = new JSONParser();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USERS = "users";
    private static final String TAG_AUTH = "auth";

    // private static final String TAG_USER_ID = "user_id";
    // private static final String TAG_PASSWORD = "password";

    AlertDialog.Builder alert;

    private final String url_user_check = common.getUrl_path() + "/check_user";

    private EditText un,pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        un = (EditText) findViewById(R.id.txtUsername );
        pw = (EditText) findViewById(R.id.txtPassword );

        Button b1 = (Button) findViewById(R.id.btnLogin);
        Button b2 = (Button) findViewById(R.id.btnReset);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CheckUser().execute();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private class CheckUser extends AsyncTask<String,String,String>{

        /**
         * Before starting background thread Show Progress Dialog
         * */
		/*
		 * @Override protected void onPreExecute() { super.onPreExecute();
		 * pDialog = new ProgressDialog(Login.this);
		 * pDialog.setMessage("Loading user details. Please wait...");
		 * pDialog.setIndeterminate(false); pDialog.setCancelable(true);
		 * pDialog.show(); }
		 */
        @Override
        protected String doInBackground(String... args){
            runOnUiThread(new Runnable() {
                @SuppressWarnings("deprecation")
                @Override
                public void run() {
                    int success;
                    try {
                        String uname = un.getText().toString();
                        String password = pw.getText().toString();

                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("uname", uname));
                        params.add(new BasicNameValuePair("password", password));

                        JSONObject json = jsonParser.makeHttpRequest(
                                url_user_check, "POST", params);

                        Log.d("Check User Details", json.toString());

                        success = json.getInt(TAG_SUCCESS);

                        if( success == 1 ){
                            JSONArray userchkobj = json.getJSONArray(TAG_USERS);

                            JSONObject check_user = userchkobj.getJSONObject(0);

                            String auth = check_user.getString(TAG_AUTH);

                            if(auth.equalsIgnoreCase("t")){
                                Intent inTeacher = new Intent(
                                        getApplicationContext(), TimeTable.class
                                );
                                startActivity(inTeacher);
                            } else {
                                Intent inStudent = new Intent(
                                    getApplicationContext(), StudentTimeTable.class
                                );
                                startActivity(inStudent);
                            }
                        } else {
                            finish();
                        }

                    } catch( JSONException e){
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
