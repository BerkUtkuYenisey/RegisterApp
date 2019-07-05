package com.berkutkuyenisey.registerassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class SignInActivity extends AppCompatActivity {
    EditText eTUsername, eTPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        eTUsername = findViewById(R.id.eTUsername);
        eTPassword = findViewById(R.id.eTPassword);

        ParseUser parseUser = ParseUser.getCurrentUser();
        if(parseUser != null) {
            Intent profileActivityIntent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(profileActivityIntent);
        }
    }

    protected void switchToSignUpActivity(View view) {
        Intent signUpActivityIntent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(signUpActivityIntent);
    }
    protected void signIn(View view) {
        ParseUser.logInInBackground(eTUsername.getText().toString(), eTPassword.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Welcome " + user.getUsername(), Toast.LENGTH_LONG).show();
                    Intent profileActivityIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(profileActivityIntent);
                }
            }
        });
    }
}
