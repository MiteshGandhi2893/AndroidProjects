package com.example.miteshgandhi.grocerylist.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewDebug;

import com.example.miteshgandhi.grocerylist.Data.DatabaseHandler;
import com.example.miteshgandhi.grocerylist.Model.Grocery;
import com.example.miteshgandhi.grocerylist.R;
import com.example.miteshgandhi.grocerylist.UI.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class listActivity extends AppCompatActivity {

    private RecyclerView rv;
    private RecyclerViewAdapter rva;
    private List<Grocery>groceryList;
    private List<Grocery>items;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(listActivity.this,MainActivity.class));

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        db=new DatabaseHandler(this);
        rv=(RecyclerView)findViewById(R.id.recyclerID);
        rv.setHasFixedSize(true);
        final LinearLayoutManager lm=new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(lm);

        groceryList=new ArrayList<>();
        items=new ArrayList<>();



        groceryList=db.getAllGrocery();

        for(Grocery grocery:groceryList)
        {
            Grocery grocery1=new Grocery();
            grocery1.setGroceryItem(grocery.getGroceryItem());
            grocery1.setGroceryQty(grocery.getGroceryQty());
            grocery1.setGrocery_Date("Added on date :"+grocery.getGrocery_Date());
            grocery1.setId(grocery.getId());
            items.add(grocery1);
        }


        rva=new RecyclerViewAdapter(this,items);
        rv.setAdapter(rva);
        rva.notifyDataSetChanged();
    }

}
