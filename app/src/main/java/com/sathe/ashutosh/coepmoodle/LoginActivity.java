package com.sathe.ashutosh.coepmoodle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.sathe.ashutosh.coepmoodle.R.layout.activity_login;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onResume()
    {
        super.onResume();
        SharedPreferences profile = getSharedPreferences("userdata",0);
        SharedPreferences.Editor editor = profile.edit();
        String checkIfLoginFailed = profile.getString("LoginFailed",null);
        if(checkIfLoginFailed.equalsIgnoreCase("True"))
        {
            editor.putString("username","default");
            editor.putString("password","default");
            editor.commit();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences profile = getSharedPreferences("userdata",0);
        SharedPreferences.Editor editor = profile.edit();
        String username = profile.getString("username",null);
        String checkIfLoginFailed = profile.getString("LoginFailed",null);
        if(checkIfLoginFailed.equalsIgnoreCase("True"))
        {
            Toast.makeText(this, "Login Was Unsuccessful ! Please Try Again", Toast.LENGTH_LONG).show();
            editor.putString("LoginFailed","False");
            editor.putString("username","default");
            editor.putString("password","default");
            editor.commit();
        }
        TextView textView = (TextView) findViewById(R.id.saved_user);
        if(username.equalsIgnoreCase("default"))
            textView.setText("No user is currently saved");
        else
            textView.setText(username);

    }
    public void login(View view)
    {
        SharedPreferences profile = getSharedPreferences("userdata",0);
        EditText uname=(EditText) findViewById(R.id.username);
        String username = uname.getText().toString();
        EditText pass = (EditText) findViewById(R.id.password);
        String password = pass.getText().toString();
        if(username.equalsIgnoreCase("") || password.equalsIgnoreCase(""))
        {   if(profile.getString("username",null).equals("default")){
                Toast.makeText(getApplicationContext(),"Login Form is empty !",Toast.LENGTH_LONG).show();
                return ;
            }
            else {
                Toast.makeText(getApplicationContext(), "Login Form is empty !", Toast.LENGTH_LONG).show();
                Intent log = new Intent(this,Moodle.class);
                startActivity(log);
                Toast.makeText(getApplicationContext(),"Using last entered credentials",Toast.LENGTH_LONG).show();
                return;
            }
        }
        SharedPreferences.Editor editor = profile.edit();
        editor.putString("username",username);
        editor.putString("password",password);
        editor.commit();
        Intent i = new Intent(this, Moodle.class);
        startActivity(i);
    }
}
