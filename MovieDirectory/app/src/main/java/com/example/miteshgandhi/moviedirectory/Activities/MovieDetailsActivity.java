package com.example.miteshgandhi.moviedirectory.Activities;

import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.miteshgandhi.moviedirectory.Model.Movie;
import com.example.miteshgandhi.moviedirectory.R;
import com.example.miteshgandhi.moviedirectory.Util.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailsActivity extends AppCompatActivity {


    private Movie movie;
    private TextView mTitle,mYear,director,actors,category,rating,writers,plot,boxOffice,runtime;
    private ImageView poster;

    private RequestQueue requestQueue;
    private String movieId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

requestQueue= Volley.newRequestQueue(this);

    movie=(Movie)getIntent().getSerializableExtra("movie");
    movieId=movie.getImdbId();

    setupUi();

getMoviesDetails(movieId);

    }

    public void setupUi()
    {
        mTitle=(TextView)findViewById(R.id.movieTitleDet);
        mYear=(TextView)findViewById(R.id.movieYearDet);
        director=(TextView)findViewById(R.id.movieDirectedDet);;
        actors=(TextView)findViewById(R.id.actorsDet);
        category=(TextView)findViewById(R.id.movieCatDet);
        rating=(TextView)findViewById(R.id.movieRateDet);
        writers=(TextView)findViewById(R.id.writersDet);
        plot=(TextView)findViewById(R.id.plotDet);
        boxOffice=(TextView)findViewById(R.id.boxOfficeDet);
        runtime=(TextView)findViewById(R.id.movieRuntimeDet);
        poster=(ImageView)findViewById(R.id.detailMovieImage);
    }

public void getMoviesDetails( String id)
{
   String URL=Constants.URL+id+Constants.KEY;


    JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {

            try
            {
                Log.d("name:",response.getString("Title"));

                if(response.has("Ratings"))
                {
                    JSONArray jsonArray=response.getJSONArray("Ratings");
                    String source="";
                    String value="";
                    Log.d("name:",response.getString("Title"));

                    if(jsonArray.length()>0)
                    {
                        JSONObject mratings=jsonArray.getJSONObject(jsonArray.length()-1);
                        source=mratings.getString("Source");
                        value =mratings.getString("Value");
                        rating.setText(source+":"+value);
                    }else
                    {
                        rating.setText("Ratings not yet available");
                    }

                    mTitle.setText(response.getString("Title"));
                    mYear.setText("Release year :"+response.getString("Released"));
                    director.setText("Directed By :"+response.getString("Director"));
                    writers.setText("Writers :"+response.getString("Writer"));
                    plot.setText("Plot :"+response.getString("Plot"));
                    runtime.setText("Runtime :"+response.getString("Runtime"));
                    actors.setText("Actors :"+response.getString("Actors"));

                    Picasso.with(getApplicationContext()).load(response.getString("Poster")).into(poster);
                    boxOffice.setText("Box office : "+response.getString("BoxOffice") );


                }

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }



        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            Log.d("err",error.getMessage());
        }
    });
    requestQueue.add(jsonObjectRequest);
    }
}
