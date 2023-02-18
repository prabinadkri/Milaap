package com.example.milaap;

import static com.example.milaap.MainActivity.Serurl;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    public static String MyPREFERENCES="pref";
    Button signup,login;
    EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup= findViewById(R.id.button2);
        login=findViewById(R.id.button);

        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        String mapTypeString = preferences.getString("auth", "DEFAULT");
        Toast.makeText(this, mapTypeString, Toast.LENGTH_SHORT).show();
        if(!(mapTypeString.equals("DEFAULT")))
        {
            Intent intent;
            intent =new Intent(this,MainActivity.class);
            startActivity(intent);
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent =new Intent(view.getContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=findViewById(R.id.editTextTextPersonName);
                password=findViewById(R.id.editTextTextPassword);


                String url = "http://"+Serurl+"/fetch.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(RegisterActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //Toast.makeText(RegisterActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                            if (jsonObject.getString("result").equals("false")) {
                                // displaying a toast message if we get error
                                //Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                                username.setError("Invalid Username or Password");
                                password.setError("Invalid Username or Password");
                            }else {
                                Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();

                                SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("auth", username.getText().toString()+"@ ~ "+password.getText().toString());
                                editor.apply();
                                password.setText("");
                                Intent intent;
                                intent =new Intent(view.getContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(RegisterActivity.this, response["result"].toString(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(RegisterActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //if (!(error.toString().equals("com.android.volley.ServerError"))) {
                                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                // } else {
                                //Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                                // }
//                                error.printStackTrace();
                            }
                        }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", username.getText().toString());
                        //params.put("email", email.getText().toString());
                        params.put("password", password.getText().toString());

                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.add(stringRequest);

            }
        });
    }
}