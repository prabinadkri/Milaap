package com.example.milaap;

import static com.example.milaap.MainActivity.Serurl;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
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

public class RegisterActivity extends AppCompatActivity {
    Button signup;
    EditText username,email,cpass,rpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=findViewById(R.id.editTextTextPersonName2);
        email=findViewById(R.id.editTextTextEmailAddress);
        cpass=findViewById(R.id.editTextTextPassword2);
        rpass=findViewById(R.id.editTextTextPassword3);
        signup=findViewById(R.id.button3);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(username.getText()) )
                {
                    username.setError("Required");
                }else if(TextUtils.isEmpty(email.getText()) )
                {
                    email.setError("Required");
                }else if(TextUtils.isEmpty(cpass.getText()) )
                {
                    cpass.setError("Required");
                }else if(!(cpass.getText().toString().equals(rpass.getText().toString())))
                {
                    rpass.setError("Password doesnot match");
                }
                else{





                        String url = "http://"+Serurl+"/signup.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Toast.makeText(RegisterActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    //Toast.makeText(RegisterActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                                    if (jsonObject.getString("result").equals("false")) {
                                        // displaying a toast message if we get error
                                        Toast.makeText(RegisterActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                                        username.setError("Username or Email already exists");
                                        email.setError("Username or Email already exists");
                                    }else {
                                        Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                                        finish();
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
                                            Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
                                params.put("email", email.getText().toString());
                                params.put("password", cpass.getText().toString());

                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                        requestQueue.add(stringRequest);

                    //new Signup(view.getContext()).execute(username.getText(),email.getText(),cpass.getText());
                }
            }
        });
    }
}