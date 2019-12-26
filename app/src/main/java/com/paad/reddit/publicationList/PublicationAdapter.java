package com.paad.reddit.publicationList;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.paad.reddit.BuildConfig;
import com.paad.reddit.PhotoActivity;
import com.paad.reddit.R;
import com.paad.reddit.model.Children;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import static androidx.core.content.ContextCompat.startActivity;


public class PublicationAdapter extends RecyclerView.Adapter<PublicationAdapter.MyViewHolder> {

    private List<Children> publicationList;
    private OnPublicationClickListener listener;
    Context context;



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

        holder.onBind(publicationList.get(position));

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

        private TextView author, date, num_comments, info;

        ImageView thumbnail;

        ImageButton save;

        MyViewHolder(final View itemView) {

            super(itemView);

            item = itemView.findViewById(R.id.publication_item);

            author = itemView.findViewById(R.id.author);

            date = itemView.findViewById(R.id.date);

            num_comments = itemView.findViewById(R.id.num_comments);

            thumbnail = itemView.findViewById(R.id.thumbnail);

            info = itemView.findViewById(R.id.info);

            save = itemView.findViewById(R.id.save);


        }



    public void onBind(Children dataChild) {

        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        author.setText(dataChild.getData().getAuthor());

        date.setText(convertUtc2Local(Long.toString(dataChild.getData().getCreated_utc()).toString()).toString() + " " + context.getResources().getString(R.string.hours_ago));

        num_comments.setText(Integer.toString(dataChild.getData().getNum_comments()) + " " + context.getResources().getString(R.string.comments));

        if (dataChild.getData().getThumbnail().contains("self") || dataChild.getData().getThumbnail().contains("default")) {

            Picasso.with(context).load(R.drawable.noimage).resize(150, 150).into(thumbnail);
        } else {


            Picasso.with(context).load(dataChild.getData().getThumbnail()).into(thumbnail);

        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BitmapDrawable draw = (BitmapDrawable) thumbnail.getDrawable();
                Bitmap bitmap = draw.getBitmap();

                String savedImageURL = MediaStore.Images.Media.insertImage(
                        context.getContentResolver(),
                        bitmap,
                        "Bird",
                        "Image of bird"
                );

                // Parse the gallery image url to uri
                Uri savedImageURI = Uri.parse(savedImageURL);

                // Display saved image url to TextView
                info.setText("Image saved to gallery.\n" + savedImageURL);
            }
        });



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

    private void saveImageStorage(Bitmap finalBitmap) {
        String directories = "Pictures";
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        File myDir = new File(root + "/" + directories);
        if(!myDir.exists()){
            myDir.mkdir();
        }

        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        // inform to the media scanner about the new file so that it is immediately available to the user.
        MediaScannerConnection.scanFile(context, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });

    }

}
