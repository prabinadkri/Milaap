package com.example.milaap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.milaap.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    public static String Serurl="192.168.101.29";
    public static String MyPREFERENCES="pref";
ImageButton menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        replaceFragment(new Friends());
        setContentView(binding.getRoot());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId()==R.id.friend)
            {
                replaceFragment(new Friends());
            }else if(item.getItemId()==R.id.lan)
            {
                replaceFragment(new Lan());
            }else if(item.getItemId()==R.id.room)
            {
                replaceFragment(new Room());
            }

            return true;
        });
        menu=findViewById(R.id.imageButton);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });
        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        String mapTypeString = preferences.getString("auth", "DEFAULT");
        Toast.makeText(this, mapTypeString, Toast.LENGTH_SHORT).show();
//        Intent intent;
//        intent =new Intent(this,LoginActivity.class);
//        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);

        return true;
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);

        MenuInflater inflater = popup.getMenuInflater();


        inflater.inflate(R.menu.nav_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this::onMenuItemClick);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popup.setForceShowIcon(true);
        }
        popup.show();
    }


//    public void showMenu(View v) {
//        PopupMenu popup = new PopupMenu(this, v);
//
//        // This activity implements OnMenuItemClickListener
//        popup.setOnMenuItemClickListener(this);
//        popup.inflate(R.menu.actions);
//        popup.show();
//    }


    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
//                Intent intent;
//                intent =new Intent(this,LoginActivity.class);
//                startActivity(intent);
                SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.apply();
                finish();
                return true;
            case R.id.prof:
                //Toast.makeText(this, "prof setting", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }


    }
private void replaceFragment(Fragment fragment)
{
    FragmentManager fragmentManager=getSupportFragmentManager();
    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.frame,fragment);
    fragmentTransaction.commit();
}

}