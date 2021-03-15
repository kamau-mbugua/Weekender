package kamau_technerd.com.weekenders.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.icu.util.ULocale;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kamau_technerd.com.weekenders.R;

public class manager extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button btnChooseFile, btnUpload;
    private TextView tvViewEvents;
    private EditText etEnterPrice,etTime, etDate,etTitle,etVanue,etDescription;
    Spinner spinner;
    private ImageView ivXhoosenFile;

    private Uri mImageUrl;
    DatabaseReference mDatabaseRef;
    StorageReference mStoraageRef;

    StorageTask mUploadTaks;
    ProgressBar mProgressBar;

    FirebaseDatabase mfirebase;
    FirebaseStorage mFirebaseStorage;
    private static final int PICK_IMAGE_REQUEST = 1;
    public static final int IMAGE_REQUEST = 1;
    String[] events = {"plays, clubs, cafes, parties"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);


        btnChooseFile   = findViewById(R.id.btnChooseFile);
        btnUpload   =findViewById(R.id. btnUpload) ;
        tvViewEvents    = findViewById(R.id. tvViewEvents);
        etEnterPrice    = findViewById(R.id.etEnterPrice);
        etDate  = findViewById(R.id.etDate);
        etDescription   =findViewById(R.id.etDescription) ;
        etTitle =findViewById(R.id.etTitle) ;
        etVanue =findViewById(R.id.etVenue) ;
        etTime  =findViewById(R.id.etTime) ;
        spinner =findViewById(R.id.spinner) ;
        ivXhoosenFile   =findViewById(R.id.ivChoosenFile) ;
        mProgressBar = findViewById(R.id.progress_barID);

        mStoraageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        spinner.setOnItemSelectedListener(this);

        List<String> caregories = new ArrayList<>();
        caregories.add("plays");
        caregories.add("clubs");
        caregories.add("cafes");
        caregories.add("parties");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, caregories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etDate.getText().toString().isEmpty() || etDescription.getText().toString().isEmpty()
                || etEnterPrice.getText().toString().isEmpty() || etTime.getText().toString().isEmpty()
            || etTitle.getText().toString().isEmpty() || etVanue.getText().toString().isEmpty())
                {
                    Toast.makeText(manager.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                }
                if (mUploadTaks != null && mUploadTaks.isInProgress()){
                    Toast.makeText(manager.this, "Uploads is in the progress", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadEvaent();
                }
            }
        });

        tvViewEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewEvents();
            }
        });
    }
    private  void chooseFile(){
       /* Intent intent = new Intent();
        intent.setType("images/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);*/

        Intent chooseIntent = new Intent();
        chooseIntent.setType("image/*");
        chooseIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(chooseIntent, IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null &&

        data.getData() != null){ mImageUrl= data.getData();
            Picasso.with(this).load(mImageUrl).into(ivXhoosenFile);
        }
    }

    private  String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private  void uploadEvaent(){
        StorageReference storageReference = mStoraageRef.child(spinner.getTransitionName());
        if (mImageUrl != null ){
            StorageReference fileRefference = storageReference .child(System.currentTimeMillis() +
                    "." + getFileExtension(mImageUrl));

            mUploadTaks = fileRefference.putFile(mImageUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(manager.this, "Upload Done", Toast.LENGTH_SHORT).show();
                            String imageUrl = taskSnapshot.getDownloadUrl().toString();
                            DatabaseReference databaseReference = mDatabaseRef.child(spinner.getTransitionName());
                            DatabaseReference ref = databaseReference.child(etTitle.getText().toString());
                            ref.child("price").setValue(etEnterPrice.getText().toString());
                            ref.child("imageurl").setValue(imageUrl);
                            ref.child("date").setValue(etDate.getText().toString());
                            ref.child("time").setValue(etTime.getText().toString());
                            ref.child("venue").setValue(etVanue.getText().toString());
                            ref.child("details").setValue(etDescription.getText().toString());

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(manager.this, e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress =(100.0 * taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        }
        else {
            Toast.makeText(this, "No File Chosen!", Toast.LENGTH_SHORT).show();
        }
    }
    private  void openViewEvents(){
        startActivity(new Intent( manager.this, MainActivity.class));
        finish();
        return;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String Item = adapterView.getItemAtPosition(i).toString();

        Toast.makeText(adapterView.getContext(), "Selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }
}
