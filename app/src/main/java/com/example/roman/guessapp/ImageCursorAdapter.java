package com.example.roman.guessapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ImageCursorAdapter extends SimpleCursorAdapter {

    private Cursor c;
    private Context context;

    public ImageCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.c = c;
        this.context = context;
    }

    public View getView(int pos, View inView, ViewGroup parent) {
        View v = inView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.activity_country, null);
        }
        this.c.moveToPosition(pos);
        String title = this.c.getString(this.c.getColumnIndex(DBHelper.COLUMN_NAME));
        byte[] imageFlag = this.c.getBlob(this.c.getColumnIndex(DBHelper.COLUMN_FLAG));
        byte[] imageMap = this.c.getBlob(this.c.getColumnIndex(DBHelper.COLUMN_MAP));
        ImageView ivFlag =  v.findViewById(R.id.ImageFlag);
        if (imageFlag != null) {
            // If there is no image in the database "NA" is stored instead of a blob
            // test if there more than 3 chars "NA" + a terminating char if more than
            // there is an image otherwise load the default
            if(imageFlag.length > 3)
            {
                ivFlag.setImageBitmap(BitmapFactory.decodeByteArray(imageFlag, 0, imageFlag.length));
            }
            else
            {
                ivFlag.setImageResource(R.drawable.ic_launcher_background);
            }
        }
        ImageView ivMap =  v.findViewById(R.id.ImageMap);
        if (imageMap != null) {
            // If there is no image in the database "NA" is stored instead of a blob
            // test if there more than 3 chars "NA" + a terminating char if more than
            // there is an image otherwise load the default
            if(imageMap.length > 3)
            {
                ivMap.setImageBitmap(BitmapFactory.decodeByteArray(imageMap, 0, imageMap.length));
            }
            else
            {
                ivMap.setImageResource(R.drawable.ic_launcher_background);
            }
        }
        TextView tvTitle = (TextView) v.findViewById(R.id.TxtViewTitle);
        tvTitle.setText(title);


        return(v);
    }
}
