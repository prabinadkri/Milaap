package com.example.milaap;

import static com.example.milaap.Friends.curusr;
import static com.example.milaap.MainActivity.MyPREFERENCES;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class chatview extends RecyclerView.Adapter<MyViewHolder2> {
    public Context context;
    public ArrayList sender,msg;
    private LayoutInflater inflater;
    private View view,view2;
    public static int VIEW_TYPE_SENT=1,VIEW_TYPE_REC=2;
    // ArrayList<String> months ;
    //ArrayList<Integer> tamounts;
public Boolean sen=true;
    chatview(Context context,ArrayList sender,ArrayList msg){

//        this.email.clear();
        this.context= context;
        this.sender=sender;
        this.msg=msg;
        // this.moid=moid;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        inflater=LayoutInflater.from(context);
        Toast.makeText(context, "error track", Toast.LENGTH_SHORT).show();
        try{
            if(viewType==VIEW_TYPE_SENT) {
                view = inflater.inflate(R.layout.sendercontainer, parent, false);
            }else {
                view = inflater.inflate(R.layout.recievercontainer, parent, false);
            }
        }catch (Exception e)
        {
            //Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            Log.d("errory", e.toString());
        }

        //view2=inflater.inflate(R.layout.recievercontainer,parent,false);
        //db = new DBhandler(view.getContext());

    //if(sen) {
        return new MyViewHolder2(view).linkAdapter(this);
//    }else {
//        return new MyViewHolder2(view2).linkAdapter(this);
//    }
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {

        //holder.uname.setText(String.valueOf(name.get(position)));
//if(String.valueOf(sender.get(position)).equals(curusr[0]))
//{
if (getItemViewType(position)==VIEW_TYPE_SENT) {
    holder.senmsg.setText(String.valueOf(msg.get(position)));
}else if (getItemViewType(position)==VIEW_TYPE_REC) {
    holder.recmsg.setText(String.valueOf(msg.get(position)));
}

//}else{
//
//}
//        try{
//            if (String.valueOf(sender.get(position + 1)).equals(curusr[0])) {
//                sen = true;
//            } else {
//                sen = false;
//            }
//        }catch (Exception e)
//        {
//
//        }


        //holder.date.setText(String.valueOf(date.get(position)));
    }

    @Override
    public int getItemViewType(int position) {
        if(String.valueOf(sender.get(position)).equals(curusr[0]))
        {
            return VIEW_TYPE_SENT;
        }else {
            return VIEW_TYPE_REC;
        }
    }

    @Override
    public int getItemCount() {
        return msg.size();
    }


}
class MyViewHolder2 extends RecyclerView.ViewHolder{
    private chatview adapter;
    TextView senmsg,recmsg;
    //ImageButton delbtn;
    //onNoteListener onnotelistener;
    public MyViewHolder2(@NonNull View itemView) {
         super(itemView);
//if(adapter.sen) {
        try {
            senmsg = itemView.findViewById(R.id.sentmsg);
            recmsg = itemView.findViewById(R.id.recmsg);
        }catch (Exception e)
        {

        }
//}else {
    //
//}
        //date=itemView.findViewById(R.id.date);
        //delbtn=itemView.findViewById(R.id.imageButton4);
        //this.onnotelistener=onnotelistener;


    }
    public MyViewHolder2 linkAdapter(chatview adapter)
    {
        this.adapter=adapter;
        return this;
    }


    public void clear() {
        int size = adapter.msg.size();
        //adapter.name.clear();
        adapter.msg.clear();
        adapter.notifyItemRangeRemoved(0, size);
    }

}
