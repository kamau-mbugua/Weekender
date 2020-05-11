package kamau_technerd.com.weekenders.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import kamau_technerd.com.weekenders.R;

public class login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener firebaseAuthListener;
    ProgressBar progressBar;

    private EditText etEmail,etPassword;
    private TextView tvFpassword,tvRegister;
    private Button btnLogin, btnSkip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail) ;
        etPassword = findViewById(R.id.etPassword) ;
        tvFpassword = findViewById(R.id.tvForgetPassword) ;
        tvRegister = findViewById(R.id.tvRegister) ;
        btnLogin = findViewById(R.id.btnLogin) ;
        btnSkip = findViewById(R.id.btnSkip) ;

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user!=null){
                    Intent intent = new Intent(login.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }

            }
        };

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedregister();
            }
        });
        tvFpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPassword();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedlogin();
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this,MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
    public void proceedregister(){
        startActivity( new Intent(getApplicationContext(),register.class));
        finish();
        return;


    }

    public void forgotPassword(){
        final String email = etEmail.getText().toString().trim();
        final String emailParten = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter Your Registered Email Address", Toast.LENGTH_SHORT).show();
            return;

        }
        if (!email.matches(emailParten)){
            etEmail.setError("Invalid Email Address");
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(login.this, "Password Reset SuccessFull, Check Your Email", Toast.LENGTH_SHORT).show();

                        }
                        else if (!task.isSuccessful()){
                            Toast.makeText(login.this, "Recheck and Confirm Your Email", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(login.this, "Something Went Wrong, Try Again!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
    public void proceedlogin(){
        if (etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter All Fields Appropriately", Toast.LENGTH_SHORT).show();
        }

        else{
            final  String email = etEmail.getText().toString().trim();
            final  String password = etPassword.getText().toString().trim();
            final String emailParten = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                  if (!task.isSuccessful()){

                      if (password.length()<6){
                          etPassword.setError("Password At least 6 Characters");
                      }
                      else if (!email.matches(emailParten)){
                          etEmail.setError("Invalid Email, Check our Email");
                      }
                      else {
//                          etPassword && etEmail .setError("Login Failed");
                          Toast.makeText(login.this, "Login Failed, Try Again", Toast.LENGTH_SHORT).show();
                      }

                  }
                  else if (task.isSuccessful()){
                      if (mAuth.getCurrentUser().getUid().equals("bt9VLGesaKargLXwnK5Xh5v1Z143"))//Admin ID
                      {
                          Intent intent = new Intent(login.this, manager.class);
                          startActivity(intent);
                          finish();
                          return;

                      }
                  }
                  else {
                      Intent intent = new Intent(login.this, MainActivity.class);
                      startActivity(intent);
                      finish();
                      return;

                      /*String user_id= mAuth.getCurrentUser().getProviderId();
                      FirebaseDatabase.getInstance().getReference().child().child().child(user_id);
                      current_user_db.setValueTrue();*/
                  }
                }
            });

        }

    }
}
