package com.example.roman.guessapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    ListView countriesList;
    DBHelper databaseHelper;
    SQLiteDatabase db;
    Cursor countryCursor;
    //SimpleCursorAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        countriesList = (ListView)findViewById(R.id.lvCountries);
        countriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(), CountryActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        databaseHelper = new DBHelper(getApplicationContext());
        // создаем базу данных
        databaseHelper.create_db();
    }


    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.open();
        //получаем данные из бд в виде курсора
        countryCursor =  db.rawQuery("select * from "+ DBHelper.TABLE, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[] {DBHelper.COLUMN_NAME, DBHelper.COLUMN_FLAG, DBHelper.COLUMN_MAP};
        // создаем адаптер, передаем в него курсор




        BaseAdapter countryAdapter = new ImageCursorAdapter(this, // Context.
                R.layout.activity_country,
                countryCursor,
                headers,
                new int[] {R.id.TxtViewTitle, R.id.ImageFlag, R.id.ImageMap});
        countriesList.setAdapter(countryAdapter);
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        countryCursor.close();
    }
}
