package com.example.milaap;
import static com.example.milaap.MainActivity.Serurl;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Friends#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Friends extends Fragment{
    ArrayList<String> result=new ArrayList<String>();
    ArrayList<String> resulte=new ArrayList<String>();
    ArrayList<String> frnname=new ArrayList<String>();
    ArrayList<String> recmsg=new ArrayList<String>();
    ArrayList<String> empty=new ArrayList<String>();
    public static String MyPREFERENCES="pref";
    public static String[] curusr;
    Boolean run;
    Boolean frun;
    FloatingActionButton addfrn;
    searchlist searchview,frnview;
    RecyclerView r,f;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Friends() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Friends.
     */
    // TODO: Rename and change types and number of parameters
    public static Friends newInstance(String param1, String param2) {
        Friends fragment = new Friends();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


            View view = inflater.inflate(R.layout.fragment_friends, container, false);
            addfrn = view.findViewById(R.id.floatingActionButton3);

            addfrn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogue(view);
                }
            });

            SharedPreferences preferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            String mapTypeString = preferences.getString("auth", "DEFAULT");


            curusr = mapTypeString.split("@ ~ ", 0);



            frun = false;
            new Fetchfrn(view).execute();
           Toast.makeText(getContext(), "loop", Toast.LENGTH_SHORT).show();
            //onCreateView(inflater,container,savedInstanceState);
            return view;


    }
    class Fetchfrn extends AsyncTask<String, String, String> {
        public View vc;
        Fetchfrn(View view){
            this.vc=view;
        }
        @Override
        protected String  doInBackground(String... str) {
            String url = "http://"+Serurl+"/fetch_frnlist.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                    frnname.clear();
//                    recmsg.clear();

                    //Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        for(int i=1;i<=jsonObject.getInt("len");i++)
                        {
                            frnname.add(jsonObject.getString(String.valueOf(i)));
                            recmsg.add("default");
                        }
                        frun=true;
                        //notifyAll();
                        //Toast.makeText(RegisterActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getContext(), error.toString()+"ddd", Toast.LENGTH_SHORT).show();
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
                    params.put("username", curusr[0]);
                    //Toast.makeText(getContext(), curusr[0], Toast.LENGTH_SHORT).show();
                    //params.put("email", email.getText().toString());

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
//                    r=dialog.findViewById(R.id.recycle);
//
//                    searchview=new searchlist(getContext(),result,resulte);
//
//
//
//                    r.setAdapter(searchview);
//
//                    r.setLayoutManager(new LinearLayoutManager(getContext()));
            // return "Donghe";
            while (!frun)
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

            if(frun){
                f = vc.findViewById(R.id.frnlistshw);

                frnview = new searchlist(getContext(), frnname, recmsg);
                //Toast.makeText(getContext(), curusr[0], Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();

                f.setAdapter(frnview);
                //
                f.setLayoutManager(new LinearLayoutManager(getContext()));

            }

        }
    }

    private void showDialogue(View view) {

        //Toast.makeText(getContext(), "helo", Toast.LENGTH_SHORT).show();
        final Dialog dialog= new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.addfrn_dialog);

        EditText search = dialog.findViewById(R.id.editTextTextPersonName3);

        ImageButton sbtn=dialog.findViewById(R.id.imageButton2);


        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                run=false;
                new Fetchdata().execute();



                //r=new RecyclerView(getContext());

            }
            class Fetchdata extends AsyncTask<String, String, String> {
                @Override
                protected String  doInBackground(String... str) {
                    String url = "http://"+Serurl+"/fetch_user.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            result.clear();
                            resulte.clear();

                            Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                              try {
                                JSONObject jsonObject = new JSONObject(response);
                                for(int i=1;i<=jsonObject.getInt("len");i++)
                                {
                                    result.add(jsonObject.getString(String.valueOf(i)));
                                    resulte.add(jsonObject.getString(String.valueOf(i)+'e'));
                                }
                                run=true;
                                //notifyAll();
                                //Toast.makeText(RegisterActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
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
                            params.put("username", search.getText().toString());
                            //params.put("email", email.getText().toString());

                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(stringRequest);
//                    r=dialog.findViewById(R.id.recycle);
//
//                    searchview=new searchlist(getContext(),result,resulte);
//
//
//
//                    r.setAdapter(searchview);
//
//                    r.setLayoutManager(new LinearLayoutManager(getContext()));
                   // return "Donghe";
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
                        r = dialog.findViewById(R.id.recycle);

                        searchview = new searchlist(getContext(), result, resulte);

                        //Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();

                        r.setAdapter(searchview);

                        r.setLayoutManager(new LinearLayoutManager(getContext()));
                    }

                }
            }




        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations=R.style.DialogueAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }




}