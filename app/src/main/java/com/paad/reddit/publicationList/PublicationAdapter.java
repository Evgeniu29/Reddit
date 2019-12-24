package com.paad.reddit.publicationList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.paad.reddit.PhotoActivity;
import com.paad.reddit.R;
import com.paad.reddit.model.Children;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static androidx.core.content.ContextCompat.startActivity;


public class PublicationAdapter extends RecyclerView.Adapter<PublicationAdapter.MyViewHolder> {

    private List<Children> publicationList;
    private OnPublicationClickListener listener;
    Context context;
    String name;
    int id;

    public PublicationAdapter(Context context, List<Children> publicationList, OnPublicationClickListener listener) {
        this.context = context;
        this.publicationList = publicationList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View result = LayoutInflater.from(parent.getContext()).inflate(R.layout.publication_item, parent, false);
        return new MyViewHolder(result);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        try {
            holder.onBind(publicationList.get(position));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {

        if (publicationList == null)
            return 0;
        return publicationList.size();
    }

    public void setListData(List<Children> publicationList) {
        publicationList = publicationList;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout item;

        private TextView author, date, num_comments;

        ImageView thumbnail;

        MyViewHolder(final View itemView) {

            super(itemView);

            item = itemView.findViewById(R.id.publication_item);

            author = itemView.findViewById(R.id.author);

            date = itemView.findViewById(R.id.date);

            num_comments = itemView.findViewById(R.id.num_comments);

            thumbnail = itemView.findViewById(R.id.thumbnail);
        }


    public void onBind(Children dataChild) throws MalformedURLException {

        author.setText(dataChild.getData().getAuthor());

        date.setText(convertUtc2Local(Long.toString(dataChild.getData().getCreated_utc()).toString()).toString()+" "+ context.getResources().getString(R.string.hours_ago));

        num_comments.setText(Integer.toString(dataChild.getData().getNum_comments())+" "+ context.getResources().getString(R.string.comments));

        Picasso.with(context).load(dataChild.getData().getThumbnail()).into(thumbnail);


        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, PhotoActivity.class);

                intent.putExtra("url", dataChild.getData().getThumbnail());

                startActivity(context, intent, null);
            }
        });

    }

}

    public List<Children> getArrayList() {
        return publicationList;
    }

    public static String convertUtc2Local(String utcTime) {
        String time = "";
        if (utcTime != null) {
            SimpleDateFormat utcFormatter = new SimpleDateFormat("HH", Locale.getDefault());
            utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date gpsUTCDate = null;
            try {
                gpsUTCDate = utcFormatter.parse(utcTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat localFormatter = new SimpleDateFormat("HH", Locale.getDefault());
            localFormatter.setTimeZone(TimeZone.getDefault());
            assert gpsUTCDate != null;
            time = localFormatter.format(gpsUTCDate.getTime());
        }
        return time;
    }

}
