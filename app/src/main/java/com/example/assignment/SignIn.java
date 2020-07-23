package com.example.assignment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class SignIn extends AppCompatActivity {

    EditText email , password ;
    TextView sigin;
    List<User> userList;
    int i=0 , z =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        sigin = (TextView) findViewById(R.id.sign_in);
        sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isValidEmail(email.getText().toString()))
                    email.setError("Enter the valid email address");
                if(password.getText().toString().isEmpty())
                    password.setError("Enter the Password ");

                if(isValidEmail(email.getText().toString()) && !password.getText().toString().isEmpty()){

                     class queryTask extends AsyncTask<Void, Void , Void> {
                        private List<User> userList;

                         @Override
                         protected Void doInBackground(Void... voids) {
                             AppDatabase db = AppDatabase.getInstance(SignIn.this);
                             userList = db.task().getPersonList();
                             z = userList.size();
                             for(User user : userList){
                                 i += 1;
                                 if(user.getEmail().equals(email.getText().toString()) && user.getPassword().equals(password.getText().toString())) {
                                     startActivity(new Intent(SignIn.this , MainActivity.class));
                                 }
                             }
                             return null;
                         }
                     }

                     queryTask qTask = new queryTask();
                     qTask.execute();

                    if(i >= z)
                        Toast.makeText(SignIn.this , "Email or password is incorrect" , Toast.LENGTH_LONG);
                }
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
}