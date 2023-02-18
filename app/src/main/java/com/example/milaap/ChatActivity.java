package com.example.milaap;

import static com.example.milaap.Friends.curusr;
import static com.example.milaap.MainActivity.Serurl;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class ChatActivity extends AppCompatActivity {
    Fetchmsg task;
    int state=0;
    Parcelable recylerViewState;
    Boolean scrl=false;
    LinearLayoutManager l;
    //ArrayList<String> sen;
    //ArrayList<String> msgs;
    ArrayList<String> senders=new ArrayList<>();
    ArrayList<String> msgs=new ArrayList<>();
    chatview chatvw;
    TextView header;
    ImageButton back,send;
    EditText msg;
    String msgval,name;
    RecyclerView m;
    Boolean run;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent=getIntent();
        name=intent.getStringExtra("milaap.UNAME");
        header=findViewById(R.id.text2);
        back=findViewById(R.id.imageButton3);
        send=findViewById(R.id.imageButton4);
        msg=findViewById(R.id.msg);
        msg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        header.setText(name);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(ChatActivity.this, MainActivity.class);
                finish();
                overridePendingTransition(0, 0);
                startActivity(i);
                overridePendingTransition(0, 0);

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ChatActivity.this, "sending", Toast.LENGTH_SHORT).show();

                if(!(TextUtils.isEmpty(msg.getText())))
                {
                    msgval=msg.getText().toString();
                    //Toast.makeText(ChatActivity.this, name+"&"+curusr[0]+" "+msg., Toast.LENGTH_SHORT).show();
                    String url = "http://"+Serurl+"/send_msg.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(RegisterActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                //Toast.makeText(ChatActivity.this, jsonObject.getString("try"), Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(ChatActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
                            params.put("reciever",name);
                            params.put("sender",curusr[0]);
                            params.put("msg", msgval);


                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(ChatActivity.this);
                    requestQueue.add(stringRequest);

                    msg.setText("");
                }

            }

        });

        run=false;
        //while(true) {
        final Handler handler = new Handler();
        final int delay = 1000; // 1000 milliseconds == 1 second
        m = findViewById(R.id.chatviewa);

        chatvw = new chatview(ChatActivity.this, senders, msgs);

        if (state==0) {
            state = msgs.size() - 1;
        }


        m.setAdapter(chatvw);
        l=new LinearLayoutManager(ChatActivity.this);
        l.setStackFromEnd(true);
        m.setLayoutManager(l);
        handler.postDelayed(new Runnable() {
            public void run() {
                task=new Fetchmsg();
                task.execute();
                handler.postDelayed(this, delay);
            }
        }, delay);

            //task.cancel(true);
        //}


    }
    class Fetchmsg extends AsyncTask<String, String, String> {
        @Override
        protected String  doInBackground(String... str) {

            String url = "http://"+Serurl+"/fetch_msg.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    senders.clear();
                    msgs.clear();
                    //Toast.makeText(ChatActivity.this, curusr[0], Toast.LENGTH_SHORT).show();
                    //Toast.makeText(ChatActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        //Toast.makeText(ChatActivity.this, jsonObject.getString(1+"msg"), Toast.LENGTH_SHORT).show();
                        for(int i=1;i<=jsonObject.getInt("len");i++)
                        {
                            senders.add(jsonObject.getString(String.valueOf(i)));
                            msgs.add(jsonObject.getString(String.valueOf(i)+"msg"));
                        }
                        //Toast.makeText(ChatActivity.this, "done", Toast.LENGTH_SHORT).show();
                        run=true;
                        //notifyAll();
                        //Toast.makeText(RegisterActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(ChatActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ChatActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
                    params.put("sender", curusr[0]);
                    params.put("reciever", name);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(ChatActivity.this);
            requestQueue.add(stringRequest);

            while (!run)
            {
                try{
                    this.wait();
                }catch(Exception e)
                {

                }
            }
            return null;
        }

        protected void onPreExecute(Integer... progress) {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(run){


                m.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        state = l.findLastCompletelyVisibleItemPosition();
                    }
                });
                m.scrollToPosition(state);
//                if(scrl)
//                {
//                    m.scrollToPosition(state);
//                    m.getLayoutManager().onRestoreInstanceState(recylerViewState);
//                    recylerViewState = m.getLayoutManager().onSaveInstanceState();
//                }else{
//                    m.scrollToPosition(msgs.size()-1);
//                    recylerViewState = m.getLayoutManager().onSaveInstanceState();
//                }
//                state=l.findLastCompletelyVisibleItemPosition();
                Toast.makeText(ChatActivity.this, String.valueOf(state), Toast.LENGTH_SHORT).show();
                scrl=true;
                //Toast.makeText(ChatActivity.this, "errro", Toast.LENGTH_SHORT).show();
                //Toast.makeText(ChatActivity.this, senders.get(1)+"asdf", Toast.LENGTH_SHORT).show();
            }

        }
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}