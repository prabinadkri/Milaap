package com.example.milaap;

import static com.example.milaap.MainActivity.MyPREFERENCES;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class searchlist extends RecyclerView.Adapter<MyViewHolder> {
    //public Dialog del_mon;
    //private Monthly_bill mb;
    //public DBhandler db;
    public Context context;
    public ArrayList name,email;
    private LayoutInflater inflater;
    private View view;
    // ArrayList<String> months ;
    //ArrayList<Integer> tamounts;

    searchlist(Context context,ArrayList name,ArrayList email){

//        this.email.clear();
        this.context= context;
        this.name=name;
        this.email=email;
        // this.moid=moid;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater=LayoutInflater.from(context);
        view=inflater.inflate(R.layout.showsearch,parent,false);
        //db = new DBhandler(view.getContext());



        return new MyViewHolder(view).linkAdapter(this);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.uname.setText(String.valueOf(name.get(position)));
        holder.uemail.setText(String.valueOf(email.get(position)));
        //holder.date.setText(String.valueOf(date.get(position)));
    }

    @Override
    public int getItemCount() {
        return email.size();
    }


}
class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private searchlist adapter;
    TextView uname,uemail;
    //ImageButton delbtn;
    //onNoteListener onnotelistener;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        uname=itemView.findViewById(R.id.textView11);
        uemail=itemView.findViewById(R.id.textView12);
        //date=itemView.findViewById(R.id.date);
        //delbtn=itemView.findViewById(R.id.imageButton4);
        //this.onnotelistener=onnotelistener;

        itemView.setOnClickListener(this);
    }
    public MyViewHolder linkAdapter(searchlist adapter)
    {
        this.adapter=adapter;
        return this;
    }

    @Override
    public void onClick(View view) {
        SharedPreferences preferences = view.getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String mapTypeString = preferences.getString("auth", "DEFAULT");


        String[] senname = mapTypeString.split("@ ~ ", 0);
        String url = "http://192.168.101.22/fetch_msg.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(RegisterActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(view.getContext(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(view.getContext(), error.toString(), Toast.LENGTH_SHORT).show();
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
                params.put("sender", senname[0]);
                params.put("reciever", adapter.name.get(getAdapterPosition()).toString());
                //params.put("msg", cpass.getText().toString());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(stringRequest);



        Intent intent=new Intent(view.getContext(),ChatActivity.class);
        intent.putExtra("milaap.UNAME",adapter.name.get(getAdapterPosition()).toString());
        view.getContext().startActivity(intent);


    }
    public void clear() {
        int size = adapter.email.size();
        adapter.name.clear();
        adapter.email.clear();
        adapter.notifyItemRangeRemoved(0, size);
    }

}
