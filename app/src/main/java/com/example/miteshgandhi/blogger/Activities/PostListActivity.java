package com.example.miteshgandhi.blogger.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.miteshgandhi.blogger.Data.BlogRecyclerViewAdapter;
import com.example.miteshgandhi.blogger.Model.Blog;
import com.example.miteshgandhi.blogger.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PostListActivity extends AppCompatActivity {

    private DatabaseReference dbref;
    private FirebaseUser fuser;
    private FirebaseDatabase fdb;
    private FirebaseAuth fauth;
private BlogRecyclerViewAdapter blogAdapter;
private List<Blog> blogList;
private RecyclerView recyclerView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list_avtivity);

        fauth=FirebaseAuth.getInstance();
        fuser=fauth.getCurrentUser();
        fdb=FirebaseDatabase.getInstance();
        dbref=fdb.getReference().child("MuyBlog");
        dbref.keepSynced(true);

        blogList=new ArrayList<>();

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        // if MyBlog database doesnot exist then the above line will create the database

        //this will get the reference to the loink of firebase database




    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_add:
                if(fuser!=null&&fauth!=null)
                {
                    startActivity(new Intent(PostListActivity.this,AddPostActivity.class));
                    finish();
                }
                break;
            case R.id.action_signout:
                if(fuser!=null&&fauth!=null)
                {
                    fauth.signOut();
                    startActivity(new Intent(PostListActivity.this,MainActivity.class));
                    finish();
                }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();


        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Blog blog=dataSnapshot.getValue(Blog.class);
                blogList.add(blog);
                blogAdapter=new BlogRecyclerViewAdapter(PostListActivity.this,blogList);
                recyclerView.setAdapter(blogAdapter);
                blogAdapter.notifyDataSetChanged();

                  //insert
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


                //update
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //delete
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
}
