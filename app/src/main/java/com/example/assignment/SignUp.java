package com.example.assignment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    EditText email , name , password , cn_password;
    TextView Signup;
    String namePattern = "^[a-zA-Z]*$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.up_email);
        name = findViewById(R.id.up_name);
        password = findViewById(R.id.up_pass);
        cn_password = findViewById(R.id.uo_cnpass);
        Signup = findViewById(R.id.sign_up);
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
        if(!name.getText().toString().matches(namePattern))
            name.setError("Enter name only in alphabets");
        if(!password.getText().toString().equals(cn_password.getText().toString()) || password.getText().toString().isEmpty())
            cn_password.setError("Password doesn't matches");

        if(isValidEmail(email.getText().toString())
                && password.getText().toString().equals(cn_password.getText().toString())
                && !password.getText().toString().isEmpty() ){


            class queryTask extends AsyncTask<Void, Boolean , Boolean> {
                Boolean flag = false;

                @Override
                protected Boolean doInBackground(Void... voids) {
                    AppDatabase db = AppDatabase.getInstance(SignUp.this);
                    User user = new User(email.getText().toString(),
                            name.getText().toString(),
                            password.getText().toString());
                    if(db.task().checkEmail(email.getText().toString()) == null)
                        db.task().insert(user);
                    else
                        flag = true;
                    return flag;
                }

                @Override
                protected void onPostExecute(Boolean flag){
                    super.onPostExecute(flag);

                    if(flag)
                        Toast.makeText(SignUp.this , "this email exist" , Toast.LENGTH_SHORT).show();
                    else
                        finish();
                }


            }

            queryTask qTask = new queryTask();
            qTask.execute();
        }

    }
}