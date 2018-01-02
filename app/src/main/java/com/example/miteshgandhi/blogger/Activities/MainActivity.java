package com.example.miteshgandhi.blogger.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miteshgandhi.blogger.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener flistener;
    private Button login,createButton;
    private EditText emailid,password;
private FirebaseUser user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

login=(Button)findViewById(R.id.login);
createButton=(Button)findViewById(R.id.create);
emailid=(EditText)findViewById(R.id.emailid);
password=(EditText)findViewById(R.id.password);

        mAuth=FirebaseAuth.getInstance();
        flistener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                user=firebaseAuth.getCurrentUser();
                if(user!=null)
                {
                    Toast.makeText(MainActivity.this,"Signed in successfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this,PostListActivity.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Failed to sign in",Toast.LENGTH_LONG).show();

                }
            }

        };
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(emailid.getText().toString())&&!TextUtils.isEmpty(password.getText().toString()))
                {
                    String email=emailid.getText().toString();
                    String pwd=password.getText().toString();
                    login(email,pwd);

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(flistener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(flistener!=null)
        mAuth.removeAuthStateListener(flistener);
    }


    private void login(String email,String pwd)
    {
        mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this,"Signed in ",Toast.LENGTH_LONG);

                    startActivity(new Intent(MainActivity.this,PostListActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Sign in Failed ",Toast.LENGTH_LONG);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_signout)
        {
            mAuth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
