package com.example.miteshgandhi.blogger.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miteshgandhi.blogger.Model.Blog;
import com.example.miteshgandhi.blogger.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddPostActivity extends AppCompatActivity {

    private ImageView addImage;
    private EditText postTitle,postDescription;
private Button submit;
private DatabaseReference dbref;
private FirebaseAuth fauth;
private FirebaseDatabase fdb;
private FirebaseUser user;
private ProgressDialog mprogress;
private static final int GALLERYCODE=1;
private Uri imageUri;
    private StorageReference mStorageRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        mprogress=new ProgressDialog(this);


        addImage=(ImageView)findViewById(R.id.addPostImage);
        postDescription=(EditText)findViewById(R.id.postDescription);
        postTitle=(EditText)findViewById(R.id.posttitle);
        submit=(Button)findViewById(R.id.SubmitPost);
        fauth=FirebaseAuth.getInstance();
        user=fauth.getCurrentUser();
        mStorageRef= FirebaseStorage.getInstance().getReference();


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERYCODE);
            }
        });

        dbref=FirebaseDatabase.getInstance().getReference().child("MyBlog");



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startPosting();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERYCODE&&resultCode==RESULT_OK)
        {
            imageUri=data.getData();
            addImage.setImageURI(imageUri);
        }


    }

    private void startPosting()
    {
        mprogress.setMessage("Posting blog...");
        mprogress.show();

       final String title=postTitle.getText().toString();
        final String desc=postDescription.getText().toString();

        if(!TextUtils.isEmpty(title)&&!TextUtils.isEmpty(desc)&&imageUri!=null)
        {

            StorageReference filepath=mStorageRef.child("MyBlogImages").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri download=taskSnapshot.getDownloadUrl();
                    DatabaseReference newPost=dbref.push();

                    //creates new item with unique refernce

                    Map<String,String> dataToSave=new HashMap<>();


                    dataToSave.put("title",title);
                    dataToSave.put("desc",desc);
                    dataToSave.put("image",download.toString());
                    dataToSave.put("timeStamp",String.valueOf(java.lang.System.currentTimeMillis()));
                    dataToSave.put("userID",user.getUid());
                    newPost.setValue(dataToSave);
                    mprogress.dismiss();







                }
            });

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
