package com.example.roman.guessapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CountryActivity extends AppCompatActivity {

    TextView tvTitle;
    ImageView imageFlag, imageMap;

    DBHelper sqlHelper;
    SQLiteDatabase db;
    Cursor countryCursor;
    long countryId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        tvTitle = findViewById(R.id.TxtViewTitle);
        imageFlag = findViewById(R.id.ImageFlag);
        imageMap = findViewById(R.id.ImageMap);


        sqlHelper = new DBHelper(this);
        db = sqlHelper.open();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            countryId = extras.getLong("id");
        }
        // если 0, то добавление
        if (countryId > 0) {
            // получаем элемент по id из бд
            String sqlQuery = "select * from " + DBHelper.TABLE + " where " + DBHelper.COLUMN_ID + " = ?";



            countryCursor = db.rawQuery(sqlQuery, new String[]{String.valueOf(countryId)});
            countryCursor.moveToFirst();
            tvTitle.setText(countryCursor.getString(1));
            countryCursor.close();
        }

    }

    private void goHome(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
