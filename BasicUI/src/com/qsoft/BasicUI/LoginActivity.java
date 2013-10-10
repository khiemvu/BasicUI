package com.qsoft.BasicUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * User: Khiemvx
 * Date: 10/8/13
 */
public class LoginActivity extends Activity
{
    private EditText etUsername;
    private EditText etPassword;
    private Button btLogin;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btLogin);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if(userName.equals("android") && password.equals("1234")){
                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                    intent.putExtra("USERNAME",userName);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"UserName or Password is invalid! Please enter again",0).show();
                }
            }
        });
    }
}
