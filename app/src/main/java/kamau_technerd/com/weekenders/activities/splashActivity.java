package kamau_technerd.com.weekenders.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import kamau_technerd.com.weekenders.R;

public class splashActivity extends AppCompatActivity {

    private static int timeout = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(splashActivity.this, login.class);
                startActivity(intent);
                finish();

            }
        },timeout);
    }
}
