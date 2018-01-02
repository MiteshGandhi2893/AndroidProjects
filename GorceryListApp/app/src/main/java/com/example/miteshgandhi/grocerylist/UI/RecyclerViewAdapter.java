package com.example.miteshgandhi.grocerylist.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.miteshgandhi.grocerylist.Activities.GroceryDetails;
import com.example.miteshgandhi.grocerylist.Activities.MainActivity;
import com.example.miteshgandhi.grocerylist.Data.DatabaseHandler;
import com.example.miteshgandhi.grocerylist.Model.Grocery;
import com.example.miteshgandhi.grocerylist.R;
import com.example.miteshgandhi.grocerylist.Util.Constants;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by miteshgandhi on 11/6/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Grocery> groceryList;
    private AlertDialog.Builder alertDialogueBuilder;
    private AlertDialog dialogue;
    private LayoutInflater inflator;

    public DatabaseHandler db;
    public RecyclerViewAdapter(Context context,List<Grocery>groceryItemIn) {

        groceryList=groceryItemIn;
        this.context = context;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow,parent,false);
       ViewHolder holder=new ViewHolder(view,context);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        Grocery grocery=groceryList.get(position);

        ((ViewHolder)holder).grocerItemName.setText(grocery.getGroceryItem());
        ((ViewHolder)holder).Quantity.setText(grocery.getGroceryQty());
        ((ViewHolder)holder).dateAdded.setText(grocery.getGrocery_Date());



    }




    @Override
    public int getItemCount() {
        return groceryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView grocerItemName;
        public TextView Quantity;
        public TextView dateAdded;
        public Button editButton;
        public Button deleteButton;
        public int id;




        public ViewHolder(View itemView,Context ctx) {
            super(itemView);
            context=ctx;
            grocerItemName=(TextView)itemView.findViewById(R.id.name);
            Quantity=(TextView)itemView.findViewById(R.id.qty);
            dateAdded=(TextView) itemView.findViewById(R.id.dateadded);

            editButton=(Button)itemView.findViewById(R.id.editbutton);
            deleteButton=(Button)itemView.findViewById(R.id.delete);

               // grocerItemName.setText(groceryList.get(getAdapterPosition()).getGroceryItem());
            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int positon= getAdapterPosition();
                    Grocery grocery=groceryList.get(positon);
                    Intent in=new Intent(context, GroceryDetails.class);
                    in.putExtra("name",grocery.getGroceryItem());
                    in.putExtra("qty",grocery.getGroceryQty());
                    in.putExtra("ID",grocery.getId());
                    in.putExtra("date",grocery.getGrocery_Date());
                    context.startActivity(in);
                }
            });
        }

        @Override
        public void onClick(View view) {
            switch(view.getId())
            {
                case R.id.editbutton:
                    int id=getAdapterPosition();
                    Grocery grocery=groceryList.get(id);
                    editItem(grocery);
                    break;
                case R.id.delete:
                     id=getAdapterPosition();
                     grocery=groceryList.get(id);
                    deletItem(grocery.getId());
                    break;
                default:
            break;

            }





        }

        public void editItem(final Grocery grocery)
        {
            alertDialogueBuilder=new AlertDialog.Builder(context);
            inflator=LayoutInflater.from(context);
            View view =inflator.inflate(R.layout.popup,null);

           final EditText groceryname=(EditText)view.findViewById(R.id.groceryItem);
            final EditText gqty=(EditText)view.findViewById(R.id.groceryQty);
            Button save=(Button)view.findViewById(R.id.saveButton);
            groceryname.setText(grocery.getGroceryItem());
            groceryname.setEnabled(false);
            groceryname.setHighlightColor(Color.BLACK);
            alertDialogueBuilder.setView(view);
            dialogue=alertDialogueBuilder.create();
            dialogue.show();

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DatabaseHandler db=new DatabaseHandler(context);
                    grocery.setGroceryItem(groceryname.getText().toString());
                    grocery.setGroceryQty(gqty.getText().toString());
                    java.text.DateFormat dateFormat=java.text.DateFormat.getDateInstance();
                    String formatedDate=dateFormat.format(new Date().getTime());
                    grocery.setGrocery_Date("Edited on date:"+formatedDate);

                    if(!groceryname.getText().toString().isEmpty()&&!gqty.getText().toString().isEmpty())
                    {
                        db.updateGrocery(grocery);
                        notifyItemChanged(getAdapterPosition(),null);
                        Snackbar.make(view,"Update Successful",Snackbar.LENGTH_LONG).show();

                        dialogue.dismiss();
                    }
                    else
                    {
                        Snackbar.make(view,"Add grocer and quantity",Snackbar.LENGTH_LONG).show();
                    }

                }
            });



        }
        public void deletItem(final int id)
        {
            //create alertdialogue builder object
            alertDialogueBuilder=new AlertDialog.Builder(context);
            inflator=LayoutInflater.from(context);
            View view =inflator.inflate(R.layout.confirmationdialogue,null);
            Button no=(Button)view.findViewById(R.id.noButton);
            Button yes=(Button)view.findViewById(R.id.yesButton);
            alertDialogueBuilder.setView(view);
            dialogue=alertDialogueBuilder.create();
            dialogue.show();

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dialogue.dismiss();
                }
            });
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHandler db=new DatabaseHandler(context);
                    db.deleteGrocery(id);
                    groceryList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    if(db.getGroceryCount()==0)
                    {
                        Intent intent=new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    }
                    dialogue.dismiss();

                }
            });



        }
    }
}
