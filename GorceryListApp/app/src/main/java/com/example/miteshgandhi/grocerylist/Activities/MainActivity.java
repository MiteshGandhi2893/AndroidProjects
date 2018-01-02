package com.example.miteshgandhi.grocerylist.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.database.DatabaseUtilsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miteshgandhi.grocerylist.Data.DatabaseHandler;
import com.example.miteshgandhi.grocerylist.Model.Grocery;
import com.example.miteshgandhi.grocerylist.R;

import java.sql.DatabaseMetaData;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogueBuilder;
    private AlertDialog alertDialog;
    private EditText name,qty;
    DatabaseHandler db;
    private Button saveButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        db=new DatabaseHandler(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialogue();
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        FloatingActionButton views=(FloatingActionButton)findViewById(R.id.fab1);
        views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byPassActivity();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void createPopupDialogue()
    {
            dialogueBuilder=new AlertDialog.Builder(this);
            View view =getLayoutInflater().inflate(R.layout.popup,null);
            name=(EditText)view.findViewById(R.id.groceryItem);
        qty=(EditText)view.findViewById(R.id.groceryQty);
        saveButton=(Button)view.findViewById(R.id.saveButton);
            dialogueBuilder.setView(view);



        alertDialog=dialogueBuilder.create();
        alertDialog.show();



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!name.getText().toString().isEmpty()&&!qty.getText().toString().isEmpty())
                {
                    saveGroceryToDB(view);
                }
                   // name.setText("Hellp  ");
            }
        });
    }



    private void saveGroceryToDB(View v)
    {

        Grocery grocery=new Grocery();


        String groceryName=name.getText().toString();
        String groceryqty=qty.getText().toString();

        grocery.setGroceryItem(groceryName);
        grocery.setGroceryQty(groceryqty);


        db.insertGrocery(grocery);
        Snackbar.make(v,"Grocery saved to database successfully",Snackbar.LENGTH_LONG).show();
        Log.d("Item added ID:",String.valueOf(db.getGroceryCount()));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
               // startActivity(new Intent(MainActivity.this, listActivity.class));
            }
        },1200);





    }
    public void byPassActivity()
    {
        if(db.getGroceryCount()>0)
        {
            startActivity(new Intent(MainActivity.this,listActivity.class));
            finish();
        }
        else
        {
            Toast.makeText(MainActivity.this,"There arr no items in the list",Toast.LENGTH_LONG).show();
        }
    }
}
