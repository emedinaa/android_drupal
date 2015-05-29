package com.isil.am2.examplerest;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.isil.am2.examplerest.model.entity.response.LoginResponse;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends ActionBarActivity {

    private static final String TAG = "HomeActivity";
    private EditText eteUsername,etePassword;
    private View btnLogin,vLoading,tviSignIn;

    private String username, password;

    private RequestQueue queue;
    private LoginResponse loginResponse;
    private String headerCookie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eteUsername = (EditText)findViewById(R.id.eteUsername);
        etePassword = (EditText)findViewById(R.id.etePassword);
        btnLogin = findViewById(R.id.btnLogin);
        vLoading = findViewById(R.id.vLoading);
        tviSignIn = findViewById(R.id.tviSignIn);

        //events
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               //validate
                if(validate())
                {
                    //ir al servidor
                    login();
                }
            }
        });
        tviSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSignIn();
            }
        });
        vLoading.setVisibility(View.GONE);
    }

    private boolean validate() {

        username = eteUsername.getText().toString().trim();
        password = etePassword.getText().toString().trim();

        eteUsername.setError(null);
        etePassword.setError(null);
        if(username.isEmpty())
        {
            eteUsername.setError("Ingresar este campo");
            return false;
        }
        if(password.isEmpty())
        {
            etePassword.setError("Ingresar este campo");
            return false;
        }
        return true;
    }

    private void gotoSignIn() {

    }

    private void login()
    {

        vLoading.setVisibility(View.VISIBLE);
        queue = Volley.newRequestQueue(this);

        String url = getString(R.string.url_drupal_login);
        Log.i("HomeActivity", "url " + url);

        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.i("HomeActivity", "response "+response);
                        GsonBuilder gsonb = new GsonBuilder();
                        Gson gson = gsonb.create();

                        loginResponse=null;
                        try
                        {
                            loginResponse= gson.fromJson(response.toString(),LoginResponse.class);
                            if(loginResponse!=null)
                            {
                                Log.i(TAG, "loginResponse "+loginResponse.toString());

                                gotoHome();
                            }

                        }catch (Exception e)
                        {

                        }
                        vLoading.setVisibility(View.GONE);

                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.i("HomeActivity", "Error: " + error.getMessage());
                // hide the progress dialog

                vLoading.setVisibility(View.GONE);

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //return super.getParams();
                Map<String,String> nParams = new HashMap<String, String>();
                nParams.put("username", username);
                nParams.put("password", password);

                Log.d(TAG, "POST params " + nParams.toString());
                return nParams;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                //params.put("Content-Type", "application/json");
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Map headers = response.headers;
                headerCookie = (String) headers.get("Set-Cookie");

                return super.parseNetworkResponse(response);
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(10000,
                1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjReq);
    }

    private void gotoHome() {
        loginResponse.setHeaderCookie(headerCookie);
        Log.i(TAG, "cookie "+headerCookie);
        Bundle bundle=new Bundle();
        bundle.putSerializable("ENTITY",loginResponse);
        Intent intent= new Intent(this,MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return false;
    }
}
