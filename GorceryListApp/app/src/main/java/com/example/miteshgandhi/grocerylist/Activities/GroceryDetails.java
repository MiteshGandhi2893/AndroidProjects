package com.example.miteshgandhi.grocerylist.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.miteshgandhi.grocerylist.R;

public class GroceryDetails extends AppCompatActivity {

    private TextView itemName,itemQuantity,dateAdded;
    private int id;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        itemName=(TextView)findViewById(R.id.itemName);
        itemQuantity=(TextView)findViewById(R.id.itemQuantity);
        dateAdded=(TextView)findViewById(R.id.itemDate1);


        Bundle bundle=getIntent().getExtras();
    if(bundle!=null)
    {
        itemName.setText(bundle.getString("name"));
        itemQuantity.setText(bundle.getString("qty"));
        dateAdded.setText(bundle.getString("date"));
        id=bundle.getInt("ID");
    }



    }

}
