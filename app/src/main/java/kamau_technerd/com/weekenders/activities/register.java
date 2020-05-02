package kamau_technerd.com.weekenders.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kamau_technerd.com.weekenders.R;
import kamau_technerd.com.weekenders.adapters.user;

public class register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText etfirstName,etlastName,etEmail,etPassword,etRpassword;
    private Button btnRegister;
    private TextView tvLogin;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null){
            finish();
        }
        etfirstName = findViewById(R.id.etfirstName);
        etlastName = findViewById(R.id.etlastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword= findViewById(R.id.etPassword);
        etRpassword = findViewById(R.id.etRePassword);
        btnRegister = findViewById(R.id.btnSignUp);
        tvLogin = findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(new
                                               View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       registeruser();
                                                   }
                                               });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });

    }
    public void registeruser(){
        if (etfirstName.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your First Name", Toast.LENGTH_SHORT).show();
            etfirstName.setError("Missing First Name");
        }
        else if (etlastName.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Last Name", Toast.LENGTH_SHORT).show();
            etfirstName.setError("Missing Last Name");
        }
        else if (etPassword.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your New Password", Toast.LENGTH_SHORT).show();

        }
        if (etEmail.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Email Address", Toast.LENGTH_SHORT).show();

        }
        else if (etRpassword.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Confirm Your Password", Toast.LENGTH_SHORT).show();
        }

        else if (etPassword  != etRpassword){
            Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
            etPassword.setError("Password Don't Match");
        }
        else {
            final String fName = etfirstName.getText().toString().trim();
            final String lName = etlastName.getText().toString().trim();
            final  String email = etEmail.getText().toString().trim();
            final  String password = etPassword.getText().toString().trim();
            final  String RePassword = etRpassword.getText().toString().trim();
            final String emailParten = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()){
                        if (email.matches(emailParten)){
                            etEmail.setError("Invalid Email!");
                        }
                        else if (password.length()<6){
                            etPassword.setError("weak password");
                        }
                        else {
                            Toast.makeText(register.this, "Registering Error, Try Again!!", Toast.LENGTH_SHORT).show();
                        }


                    }

                    else {
                        String user_id = mAuth.getUid();
                        user user = new user(fName,lName,email);
                        mRef.child(user_id).setValue(user);
                        mAuth.getCurrentUser().sendEmailVerification();
                        finish();
                        Intent intent = new Intent(register.this, MainActivity.class);
                        startActivity(intent);

                    }
                }
            });
        }
    }
}
