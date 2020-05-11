package kamau_technerd.com.weekenders.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kamau_technerd.com.weekenders.R;
import kamau_technerd.com.weekenders.adapters.mainActivityCustomAdapter;
import kamau_technerd.com.weekenders.adapters.mainactivityListRow;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    private EditText etSearchText;
    private ImageView ibSearchButton;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    mainActivityCustomAdapter myAdapter;

    public ArrayList<mainactivityListRow> list;
    String[] parties= {"0","0","0","0","0","0"};
    String[] cafes= {"0","0","0","0","0","0"};
    String[] clubs= {"0","0","0","0","0","0"};
    String[] plays= {"0","0","0","0","0","0"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearchText = findViewById(R.id.etSearchText);
        ibSearchButton = findViewById(R.id.ibSearchBtn);

        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview = navigationView.getHeaderView(0);
        final TextView mfName = headerview.findViewById(R.id.tvMfname);

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progress_bar);

        if (mAuth.getCurrentUser() != null){
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid()).child("firstname");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mfName.setText(dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else
            mfName.setVisibility(View.GONE);

        ListView listView = findViewById(R.id.listview);

        list = new ArrayList<>();
        myAdapter = new mainActivityCustomAdapter(this, list);
        listView.setAdapter(myAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("uploads");
        Query refplays = reference.child("plays").limitToFirst(6);
        refplays.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    String url = postSnapshot.child("imageurl").getValue(String.class);
                    plays[i] = url;
                    i++;
                }
                myAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        Query refclubs = reference.child("clubs").limitToFirst(6);
        refclubs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    String url = postSnapshot.child("imageurl").getValue(String.class);
                    clubs[i] = url;
                    i++;
                }
                myAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        Query refcafes = reference.child("cafes").limitToFirst(6);
        refcafes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    String url = postSnapshot.child("imageurl").getValue(String.class);
                    cafes[i] = url;
                    i++;
                }
                myAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        Query refparties = reference.child("parties").limitToFirst(6);
        refparties.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    String url = postSnapshot.child("imageurl").getValue(String.class);
                    parties[i] = url;
                    i++;
                }
                myAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        list.clear();
        list.add(new mainactivityListRow(plays,"plays"));
        list.add(new mainactivityListRow(clubs,"clubs"));
        list.add(new mainactivityListRow(cafes,"cafes"));
        list.add(new mainactivityListRow(parties,"parties"));

        etSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
               if (i  == EditorInfo.IME_NULL && keyEvent.getAction()==keyEvent.ACTION_DOWN){
                  if (!TextUtils.isEmpty(etSearchText.getText().toString())){
                   Intent intent =  new Intent( getApplicationContext(),MainActivity.class);
                   intent.putExtra("Search",1);
                   intent.putExtra("searchText", etSearchText.getText().toString());
                   startActivity(intent);
                  }
                  return true;
               }

                return false;
            }
        });

        ibSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(etSearchText.getText().toString())){
                    /*startActivity(new Intent(getApplicationContext(), MainActivity.class));*/
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("search",1);
                    intent.putExtra("searchtext", etSearchText.getText().toString());

                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
        {
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            case R.id.home:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case  R.id.mybookings:
                if (mAuth.getCurrentUser()!=null){
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("booking",1);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "Login To see Bookings", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bookmarks:
                if (mAuth.getCurrentUser()!=null) {
                    Intent intent = new Intent(this, whole.class);
                    intent.putExtra("bookmark", 1);
                    startActivity(intent);
                }
                else
                    Toast.makeText(this,"Login to see Bookmarks",Toast.LENGTH_LONG).show();
                break;
            case R.id.logout:
                if(mAuth.getCurrentUser()!=null) {
                    mAuth.signOut();
                    finish();
                    startActivity(new Intent(this,login.class));
                }
                else
                    startActivity(new Intent(this,login.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
