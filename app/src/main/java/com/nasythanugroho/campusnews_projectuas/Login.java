package com.nasythanugroho.campusnews_projectuas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends Activity {

    private Context context;
    private Button buttonLogin;
    private ProgressDialog pDialog;
    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = Login.this;
        pDialog = new ProgressDialog(context);
        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
        buttonLogin = (Button)findViewById(R.id.button);

        buttonLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                login();
            }
        });
    }

    private void login(){
        final String username = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
        pDialog.setMessage("Login Proses .. ");
        showDialog();
        //Membuat string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppVar.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
        //Jika sukses dari server
                        if (response.contains(AppVar.LOGIN_SUCCESS)) {
                            hideDialog();
                            gotoCourseActivity();
                        } else {
                            hideDialog();
        //Memunculkan error message di toast
                            Toast.makeText(context, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
        //Handle error
                        hideDialog();
                        //Toast adalah pesan text yang ditampilkan pada android
                        Toast.makeText(context, "The server unreachable",Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            //HashMap adalah sebuah class yang berisi sekumpulan pasangan nilai (value) dan kunci (key)
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(AppVar.KEY_USERNAME, username);
                params.put(AppVar.KEY_PASSWORD, password);
                return params;
            }
        };
        //Volley = meminta data ke server melalui web service
        Volley.newRequestQueue(this).add(stringRequest);
    }
    private void gotoCourseActivity() {
        Intent intent = new Intent(context, Main2Activity.class);
        startActivity(intent);
        finish();
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}