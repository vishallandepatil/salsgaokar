package com.example.vishallandepatil.incubatore.login;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.vishallandepatil.incubatore.R;
import com.example.vishallandepatil.incubatore.home.MainActivity;
import com.example.vishallandepatil.incubatore.reading.database.ReadingTable;



public class LoginActivity extends AppCompatActivity {

    private Button btnlogin;
    private EditText username,password;
    LinearLayout splashSceen,loginScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        splashSceen=(LinearLayout) findViewById(R.id.slashscreen);
        loginScreen=(LinearLayout) findViewById(R.id.loginscreen);
        splashSceen.setVisibility(View.VISIBLE);
        loginScreen.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                splashSceen.setVisibility(View.GONE);

                //if(new PrefManager(LoginActivity.this).ISLOGing())
                {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                //loginScreen.setVisibility(View.VISIBLE);
                btnlogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String Username =username.getText().toString();
                        String passwordsrt =password.getText().toString();
                        if(Username!=null && passwordsrt!=null && Username.length()==10&&passwordsrt.equalsIgnoreCase(Username))
                        {
                            Intent intent = new Intent(view.getContext(), MainActivity.class);
                            view.getContext().startActivity(intent);
                            new PrefManager(getBaseContext()).createLogin(Username);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Please enter Valid User name or Password",Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        }, 3000);


    }
}
