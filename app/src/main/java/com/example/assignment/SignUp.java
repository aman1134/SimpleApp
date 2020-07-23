package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

public class SignUp extends AppCompatActivity {

    EditText email , name , password , cn_password;
    TextView Signup;
    String namePattern = "[a-z]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = (EditText) findViewById(R.id.up_email);
        name = (EditText) findViewById(R.id.up_name);
        password = (EditText) findViewById(R.id.up_pass);
        cn_password = (EditText) findViewById(R.id.uo_cnpass);
        Signup = (TextView) findViewById(R.id.sign_up);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private void saveUser() {

        if(!isValidEmail(email.getText().toString()))
            email.setError("Enter the valid email address");
        if(!password.getText().toString().equals(cn_password.getText().toString()) || password.getText().toString().isEmpty())
            cn_password.setError("Password doesn't matches");

        if(isValidEmail(email.getText().toString())
                && password.getText().toString().equals(cn_password.getText().toString())
                && !password.getText().toString().isEmpty() ){


            class queryTask extends AsyncTask<Void, Void , Void> {
                private List<User> userList;

                @Override
                protected Void doInBackground(Void... voids) {
                    AppDatabase db = AppDatabase.getInstance(SignUp.this);
                    User user = new User(email.getText().toString(),
                            name.getText().toString(),
                            password.getText().toString());
                    db.task().insert(user);
                    return null;
                }

            }

            queryTask qTask = new queryTask();
            qTask.execute();

            finish();
        }

    }
}