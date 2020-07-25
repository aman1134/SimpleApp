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

    public static String header_email;
    EditText email , password ;
    TextView sigin;
    int i=0 , z =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        sigin = findViewById(R.id.sign_in);
        sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isValidEmail(email.getText().toString())) {
                    email.setError("Enter the valid email address");
                    email.setFocusable(true);
                }
                if(password.getText().toString().isEmpty()) {
                    password.setError("Enter the Password ");
                    password.setFocusable(true);
                }

                if(isValidEmail(email.getText().toString()) && !password.getText().toString().isEmpty()){

                     class queryTask extends AsyncTask<Void, Void, Boolean> {
                        private List<User> userList;

                         @Override
                         protected Boolean doInBackground(Void... voids) {
                             AppDatabase db = AppDatabase.getInstance(SignIn.this);
                             User user = db.task().checkEmail(email.getText().toString());
                             if(user != null && user.getPassword().equals(password.getText().toString()))
                                 startActivity(new Intent(SignIn.this , MainActivity.class));
                             else
                                 return true;
                             return false;
                         }

                         @Override
                         protected void onPostExecute(Boolean flag){
                             if(flag)
                                 Toast.makeText(SignIn.this , "Entered email or password is incorrect", Toast.LENGTH_SHORT).show();
                         }
                     }
                     queryTask qTask = new queryTask();
                     qTask.execute();
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