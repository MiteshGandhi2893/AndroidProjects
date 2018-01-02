package com.example.miteshgandhi.moviedirectory.Data;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miteshgandhi.moviedirectory.Activities.MovieDetailsActivity;
import com.example.miteshgandhi.moviedirectory.Model.Movie;
import com.example.miteshgandhi.moviedirectory.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by miteshgandhi on 12/29/17.
 */

public class MovieRecyclerView extends RecyclerView.Adapter<MovieRecyclerView.ViewHolder> {


    private Context context;
    private List<Movie>movies;


    public MovieRecyclerView(Context context, List<Movie> movies)
    {
        this.context=context;
        this.movies=movies;
    }
    @Override
    public MovieRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row,parent,false);



        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(MovieRecyclerView.ViewHolder holder, int position) {
        Movie movie=movies.get(position);

String Poster=movie.getPoster();
holder.title.setText(movie.getTitle());
holder.type.setText(movie.getMovieType());
        Picasso.with(context).load(Poster).placeholder(android.R.drawable.ic_btn_speak_now).into(holder.poster);
        holder.year.setText(movie.getYear());



    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView year;
        TextView type;
        ImageView poster;



        public ViewHolder(View itemView, final Context ctx) {
            super(itemView);
            context=ctx;

            title=(TextView)itemView.findViewById(R.id.titleId);
            year=(TextView)itemView.findViewById(R.id.realeseId);
            type=(TextView)itemView.findViewById(R.id.moviecatID);
            poster=(ImageView)itemView.findViewById(R.id.movieImage);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Movie movie=movies.get(getAdapterPosition());
                    Intent intent=new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra("movie",movie);
                    ctx.startActivity(intent);

                }
            });

        }

        @Override
        public void onClick(View view) {


        }
    }
}
