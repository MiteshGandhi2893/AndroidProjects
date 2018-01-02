package com.example.miteshgandhi.moviedirectory.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.miteshgandhi.moviedirectory.Data.MovieRecyclerView;
import com.example.miteshgandhi.moviedirectory.Model.Movie;
import com.example.miteshgandhi.moviedirectory.R;
import com.example.miteshgandhi.moviedirectory.Util.Constants;
import com.example.miteshgandhi.moviedirectory.Util.Prefs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerView;
    private List<Movie> movieList;
    private RequestQueue requestQueue;

    private AlertDialog.Builder alertdialogueBuilder;
    private AlertDialog alertDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        requestQueue= Volley.newRequestQueue(this);




       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShowDialogue();



            }
        });

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieList=new ArrayList<>();
        Prefs pr=new Prefs(MainActivity.this);
        String str=pr.getSearch();
       movieList=getMovieList(str);
       movieRecyclerView =new MovieRecyclerView(this,movieList);
       recyclerView.setAdapter(movieRecyclerView);
       movieRecyclerView.notifyDataSetChanged();

    }

    public List<Movie> getMovieList(String SearchItem)
    {

        movieList.clear();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Constants.URL_LEFT + SearchItem + Constants.URL_RIGHT, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try
                {
                    JSONArray jsonArray=response.getJSONArray("Search");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        Movie movie=new Movie();
                        movie.setTitle(jsonObject.getString("Title"));
                        movie.setYear("Year Released :"+jsonObject.getString("Year"));
                             movie.setMovieType("Type :"+jsonObject.getString("Type"));
                             movie.setPoster(jsonObject.getString("Poster"));
                             movie.setImdbId(jsonObject.getString("imdbID"));

                             //Log.d("tutle: ",movie.getTitle());

                            movieList.add(movie);
                    }
                    movieRecyclerView.notifyDataSetChanged();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

requestQueue.add(jsonObjectRequest);
return movieList;
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
        if (id == R.id.newSearch) {
            ShowDialogue();
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ShowDialogue()
    {
        alertdialogueBuilder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.dialogue_view,null);

        final EditText search=(EditText)view.findViewById(R.id.editTitle);
        final Button submit=(Button)view.findViewById(R.id.searchButton);
        alertdialogueBuilder.setView(view);
        alertDialog=alertdialogueBuilder.create();
        alertDialog.show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs prefs=new Prefs(MainActivity.this);
                if(!search.getText().toString().isEmpty())
                {
                    String Search=search.getText().toString();
                    prefs.setSearch(Search);

                    movieList.clear();
                    movieList=getMovieList(Search);
                    movieRecyclerView.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No such  movie found ",Toast.LENGTH_SHORT);
                }
                alertDialog.dismiss();

            }
        });


    }



}
