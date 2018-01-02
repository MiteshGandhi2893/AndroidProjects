package com.example.miteshgandhi.grocerylist.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.miteshgandhi.grocerylist.Activities.MainActivity;
import com.example.miteshgandhi.grocerylist.Model.Grocery;
import com.example.miteshgandhi.grocerylist.Util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by miteshgandhi on 11/6/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

private Context ctx;
    public DatabaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        ctx=context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_table="create table "+Constants.TABLE_NAME+" ( "+Constants.KEY_ID+" INTEGER PRIMARY KEY,"+Constants.KEY_GROCERY_ITEM+" TEXT,"+Constants.KEY_QTY_NUMBER+" TEXT,"+Constants.KEY_DATE_NAME+" LONG);";

        db.execSQL(create_table);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);
        onCreate(db);




    }

    public String insertGrocery(Grocery grocery)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Constants.KEY_GROCERY_ITEM,grocery.getGroceryItem());
        values.put(Constants.KEY_QTY_NUMBER,grocery.getGroceryQty());
        values.put(Constants.KEY_DATE_NAME,java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME,null,values);
            return "Grocery stored successfully";

    }
    private Grocery getGrocery(int id)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Grocery grocery=new Grocery();

        Cursor cursor=db.query(Constants.TABLE_NAME,new String[]{Constants.KEY_ID,Constants.KEY_GROCERY_ITEM,Constants.KEY_QTY_NUMBER,Constants.KEY_DATE_NAME},Constants.KEY_ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
            grocery.setGroceryItem(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
            grocery.setGroceryQty(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));

            java.text.DateFormat dateFormat=java.text.DateFormat.getDateInstance();
            String formatedDate=dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());
            grocery.setGrocery_Date(formatedDate);


        }
        return grocery;

    }
    public List<Grocery> getAllGrocery()
    {
        List<Grocery>groceryList=new ArrayList<Grocery>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(Constants.TABLE_NAME,new String[]{Constants.KEY_ID,Constants.KEY_GROCERY_ITEM,Constants.KEY_QTY_NUMBER,Constants.KEY_DATE_NAME},null,null,null,null,Constants.KEY_DATE_NAME+" DESC");
        if(cursor.moveToFirst())
        {
            do {

                Grocery grocery=new Grocery();
                grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                grocery.setGroceryItem((cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM))));
                grocery.setGroceryQty(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));

                java.text.DateFormat dateFormat=java.text.DateFormat.getDateInstance();
                String formatedDate=dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());
                grocery.setGrocery_Date(formatedDate);

                groceryList.add(grocery);


            }while(cursor.moveToNext());
        }


        return groceryList;




    }
    public int updateGrocery(Grocery grocery)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Constants.KEY_GROCERY_ITEM,grocery.getGroceryItem());
        values.put(Constants.KEY_QTY_NUMBER,grocery.getGroceryQty());
        values.put(Constants.KEY_DATE_NAME,grocery.getGrocery_Date());



        return db.update(Constants.TABLE_NAME,values,Constants.KEY_ID+" = ?",new String[]{String.valueOf(grocery.getId())});
    }
    public void deleteGrocery(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME,Constants.KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();


    }
    public int getGroceryCount()
    {
        String query="Select * from "+Constants.TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.rawQuery(query,null);
        return cursor.getCount();

    }

}
