package com.example.vishallandepatil.incubatore.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vishallandepatil.incubatore.R;
import com.example.vishallandepatil.incubatore.home.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private Button btnlogin;
    private EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        if(new PrefManager(this).ISLOGing())
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
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
}