package escapadetechnologies.com.localdatabaseexample;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class SqliteDataActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHandler databaseHandler;
    ArrayList<HashMap<String,String>> sqliteMoviesList;
    MoviesAdapter moviesAdapter;
    RecyclerView recyclerViewSqlite;
    EditText searchName;
    ImageView searchTheText,back_search_view;
    HashMap<String, String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_data);

        recyclerViewSqlite = findViewById(R.id.recyclerViewSqlite);
        searchName = findViewById(R.id.searchName);
        searchTheText = findViewById(R.id.searchTheText);
        back_search_view = findViewById(R.id.back_search_view);

        back_search_view.setOnClickListener(this);

        databaseHandler = new DatabaseHandler(this);

        sqliteMoviesList = new ArrayList<>();




        searchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable search) {
                //coder in sqlite check it
                searchThedata(search.toString());

            }
        });


        //getTheMoviesData();

    }

    private void searchThedata(String search) {


        SQLiteDatabase db = databaseHandler.getReadableDatabase();

       /* String query = "SELECT * FROM " + DatabaseHandler.TABLE_NAME + " WHERE " + DatabaseHandler.TITLE + " LIKE '" + search + "%'";

        Cursor cursor = db.rawQuery(query, null);*/

        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHandler.TABLE_NAME + " WHERE " + DatabaseHandler.TITLE + " LIKE '%"+search.trim()+"%'", null);



            try {
                //cursor = db.query(DatabaseHandler.TABLE_NAME, new String[]{DatabaseHandler.TITLE, DatabaseHandler.OVERVIEW, DatabaseHandler.POPULARITY}, DatabaseHandler.TABLE_NAME + " MATCH ?", new String[]{search}, null, null, null, null);
                sqliteMoviesList.clear();
                if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                        /*int iUsername = cursor.getColumnIndex(KEY_USERNAME);
                        int iFullname = cursor.getColumnIndex(KEY_FULLNAME);
                        int iEmail = cursor.getColumnIndex(KEY_EMAIL);*/

                    do {
                            /*results.add(
                                    new String(
                                            "Username: "+cursor.getString(iUsername) +
                                                    ", Fullname: "+cursor.getString(iFullname) +
                                                    ", Email: "+cursor.getString(iEmail)
                                    )
                            );*/

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put(DatabaseHandler.ID, cursor.getString(cursor.getColumnIndex(DatabaseHandler.ID)));
                        hashMap.put(DatabaseHandler.TITLE, cursor.getString(cursor.getColumnIndex(DatabaseHandler.TITLE)));
                        hashMap.put(DatabaseHandler.OVERVIEW, cursor.getString(cursor.getColumnIndex(DatabaseHandler.OVERVIEW)));
                        hashMap.put(DatabaseHandler.POPULARITY, cursor.getString(cursor.getColumnIndex(DatabaseHandler.POPULARITY)));
                        hashMap.put(DatabaseHandler.POSTER_PATH, cursor.getString(cursor.getColumnIndex(DatabaseHandler.POSTER_PATH)));
                        sqliteMoviesList.add(hashMap);
                        moviesAdapter = new MoviesAdapter(sqliteMoviesList, this);
                        recyclerViewSqlite.setLayoutManager(new LinearLayoutManager(this));
                        recyclerViewSqlite.setHasFixedSize(true);
                        recyclerViewSqlite.setAdapter(moviesAdapter);
                    } while (cursor.moveToNext());
                }

                /*if (cursor.moveToFirst()) {
                    do {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put(DatabaseHandler.ID, cursor.getString(cursor.getColumnIndex(DatabaseHandler.ID)));
                        hashMap.put(DatabaseHandler.TITLE, cursor.getString(cursor.getColumnIndex(DatabaseHandler.TITLE)));
                        hashMap.put(DatabaseHandler.OVERVIEW, cursor.getString(cursor.getColumnIndex(DatabaseHandler.OVERVIEW)));
                        hashMap.put(DatabaseHandler.POPULARITY, cursor.getString(cursor.getColumnIndex(DatabaseHandler.POPULARITY)));
                        hashMap.put(DatabaseHandler.POSTER_PATH, cursor.getString(cursor.getColumnIndex(DatabaseHandler.POSTER_PATH)));
                        sqliteMoviesList.add(hashMap);
                        moviesAdapter = new MoviesAdapter(sqliteMoviesList, this);
                        recyclerViewSqlite.setLayoutManager(new LinearLayoutManager(this));
                        recyclerViewSqlite.setHasFixedSize(true);
                        recyclerViewSqlite.setAdapter(moviesAdapter);
                    }while (cursor.moveToNext());*/

                    /*while (cursor.moveToNext()){
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put(DatabaseHandler.ID, cursor.getString(cursor.getColumnIndex(DatabaseHandler.ID)));
                        hashMap.put(DatabaseHandler.TITLE, cursor.getString(cursor.getColumnIndex(DatabaseHandler.TITLE)));
                        hashMap.put(DatabaseHandler.OVERVIEW, cursor.getString(cursor.getColumnIndex(DatabaseHandler.OVERVIEW)));
                        hashMap.put(DatabaseHandler.POPULARITY, cursor.getString(cursor.getColumnIndex(DatabaseHandler.POPULARITY)));
                        hashMap.put(DatabaseHandler.POSTER_PATH, cursor.getString(cursor.getColumnIndex(DatabaseHandler.POSTER_PATH)));
                        sqliteMoviesList.add(hashMap);
                        moviesAdapter = new MoviesAdapter(sqliteMoviesList, this);
                        recyclerViewSqlite.setLayoutManager(new LinearLayoutManager(this));
                        recyclerViewSqlite.setHasFixedSize(true);
                        recyclerViewSqlite.setAdapter(moviesAdapter);
                    }*/
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


        /*String query = "SELECT * FROM "+ DatabaseHandler.TABLE_NAME + " WHERE " + DatabaseHandler.TITLE + " LIKE " + ""+ search +"";

        Cursor cursor = db.rawQuery(query,null);*/

        /*Cursor cursor = db.query(DatabaseHandler.TABLE_NAME,new String[]{DatabaseHandler.TITLE,DatabaseHandler.OVERVIEW,DatabaseHandler.POPULARITY},DatabaseHandler.TITLE + "=?",
                    new String[]{search.toString()},null,null,null,null);*/

        /*String query = "SELECT * FROM " + DatabaseHandler.TABLE_NAME + " WHERE " + DatabaseHandler.TITLE + " OR " + DatabaseHandler.OVERVIEW + " like "+"'"+search+"'";
        Cursor cursor = db.rawQuery(query,null);
        try{

            if (cursor.getCount() == 0){

                while (cursor.moveToNext()){

                }

            }

            if (cursor.moveToFirst()){
                do {
                    sqliteMoviesList.clear();
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put(DatabaseHandler.ID,cursor.getString(cursor.getColumnIndex(DatabaseHandler.ID)));
                    hashMap.put(DatabaseHandler.TITLE,cursor.getString(cursor.getColumnIndex(DatabaseHandler.TITLE)));
                    hashMap.put(DatabaseHandler.OVERVIEW,cursor.getString(cursor.getColumnIndex(DatabaseHandler.OVERVIEW)));
                    hashMap.put(DatabaseHandler.POPULARITY,cursor.getString(cursor.getColumnIndex(DatabaseHandler.POPULARITY)));
                    hashMap.put(DatabaseHandler.POSTER_PATH,cursor.getString(cursor.getColumnIndex(DatabaseHandler.POSTER_PATH)));
                    sqliteMoviesList.add(hashMap);
                    moviesAdapter = new MoviesAdapter(sqliteMoviesList,this);
                    recyclerViewSqlite.setLayoutManager(new LinearLayoutManager(this));
                    recyclerViewSqlite.setHasFixedSize(true);
                    recyclerViewSqlite.setAdapter(moviesAdapter);
                }while (cursor.moveToNext());

                cursor.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/

    }

        private void getTheMoviesData () {

            ArrayList arrayList = databaseHandler.getAllMovieData();

            MoviesAdapter moviesAdapter = new MoviesAdapter(arrayList, this);
            recyclerViewSqlite.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewSqlite.setHasFixedSize(true);
            recyclerViewSqlite.setAdapter(moviesAdapter);

        /*SQLiteDatabase data = databaseHandler.getReadableDatabase();
        //Cursor cursor = data.rawQuery("SELECT * FROM " + DatabaseHandler.TABLE_NAME,null);
        Cursor cursor = databaseHandler.getAllMovieData();
        Log.e("moviesdata", String.valueOf(cursor.getCount()));
        while (cursor.moveToNext()){

            Movies movies = new Movies(cursor.getString(5),cursor.getString(4),cursor.getString(3),cursor.getString(2),cursor.getString(1),cursor.getString(0));

            sqliteMoviesList.add(movies.getId(),movies.getTitle(),movies.getOverView(),movies.getPopularity(),movies.getIsAdult(),movies.getPosterPath());

        }


       if (cursor.getCount() != 0 ) {
           if (cursor.moveToFirst()) {
                   do {
                       hashMap = new HashMap<>();
                       hashMap.put(DatabaseHandler.ID, cursor.getString(cursor.getColumnIndex(DatabaseHandler.ID)));
                       hashMap.put(DatabaseHandler.TITLE, cursor.getString(cursor.getColumnIndex(DatabaseHandler.TITLE)));
                       hashMap.put(DatabaseHandler.OVERVIEW, cursor.getString(cursor.getColumnIndex(DatabaseHandler.OVERVIEW)));
                       hashMap.put(DatabaseHandler.POPULARITY, cursor.getString(cursor.getColumnIndex(DatabaseHandler.POPULARITY)));
                       hashMap.put(DatabaseHandler.POSTER_PATH, cursor.getString(cursor.getColumnIndex(DatabaseHandler.POSTER_PATH)));
                       sqliteMoviesList.add(hashMap);

                   } while (cursor.moveToNext());

                   moviesAdapter = new MoviesAdapter(sqliteMoviesList, this);
                   cursor.close();

           while (cursor.moveToNext()){

           }

           }


           recyclerViewSqlite.setLayoutManager(new LinearLayoutManager(this));
           recyclerViewSqlite.setHasFixedSize(true);
           recyclerViewSqlite.setAdapter(moviesAdapter);
       }*/


        /*ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHandler.TABLE_NAME,null);
        if (cursor.moveToFirst()){

            do {
                hashMap = new HashMap<>();
                hashMap.put(DatabaseHandler.ID, cursor.getString(cursor.getColumnIndex(DatabaseHandler.ID)));
                hashMap.put(DatabaseHandler.TITLE, cursor.getString(cursor.getColumnIndex(DatabaseHandler.TITLE)));
                hashMap.put(DatabaseHandler.OVERVIEW, cursor.getString(cursor.getColumnIndex(DatabaseHandler.OVERVIEW)));
                hashMap.put(DatabaseHandler.POPULARITY, cursor.getString(cursor.getColumnIndex(DatabaseHandler.POPULARITY)));
                hashMap.put(DatabaseHandler.POSTER_PATH, cursor.getString(cursor.getColumnIndex(DatabaseHandler.POSTER_PATH)));
                arrayList.add(hashMap);
            }while (cursor.moveToNext());

        }
        cursor.close();
        db.close();*/
        }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.back_search_view:
                onBackPressed();
                break;

            case R.id.searchTheText:



                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
