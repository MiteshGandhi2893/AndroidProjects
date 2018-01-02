package com.example.miteshgandhi.blogger.Data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miteshgandhi.blogger.Model.Blog;
import com.example.miteshgandhi.blogger.R;

import java.util.Date;
import java.util.List;

/**
 * Created by miteshgandhi on 12/30/17.
 */

public class BlogRecyclerViewAdapter extends RecyclerView.Adapter<BlogRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Blog> blogList;

    public BlogRecyclerViewAdapter(Context context, List<Blog> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row,parent,false);



        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(BlogRecyclerViewAdapter.ViewHolder holder, int position) {

        Blog blog=blogList.get(position);
        String imageUrl="";
        holder.title.setText(blog.getTitle());

        holder.desc.setText(blog.getDesc());

        java.text.DateFormat dateFormat=java.text.DateFormat.getDateInstance();
        String formatedDate=dateFormat.format(new Date(Long.valueOf(blog.getTimeStamp())));



        holder.timestamp.setText(formatedDate);
        imageUrl=blog.getImage();





    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title,desc,timestamp;
        public ImageView image;
String userid;




        public ViewHolder(View itemView,Context ctx) {
            super(itemView);

context=ctx;
                title=(TextView)itemView.findViewById(R.id.postTitle);
                desc=(TextView)itemView.findViewById(R.id.postTextList);
                timestamp=(TextView)itemView.findViewById(R.id.timeStampList);
                image=(ImageView)itemView.findViewById(R.id.postimage);
                userid=null;

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });


        }
    }
}
