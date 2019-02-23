package escapadetechnologies.com.localdatabaseexample;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/top_rated?api_key=b7ae4931443ae44ae879c87b191bb8e5";
    ArrayList<HashMap<String,String>> moviesList;

    RecyclerView recyclerView;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);

        databaseHandler = new DatabaseHandler(this);

        getTheData();
        moviesList = new ArrayList<>();

    }

    private void getTheData() {

        StringRequest stringRequest = new StringRequest(BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String results = jsonObject.getString("results");

                    JSONArray jsonArray = new JSONArray(results);

                    moviesList = new ArrayList<>();

                    for (int i = 0 ;i < jsonArray.length() ; i++){

                        HashMap<String,String> hashMap = new HashMap<>();

                        JSONObject object = jsonArray.getJSONObject(i);

                        String poster_path = object.getString("poster_path");
                        String title = object.getString("title");
                        String popularity = object.getString("popularity");
                        String over_view = object.getString("overview");
                        String id = object.getString("id");

                        hashMap.put("poster_path",poster_path);
                        hashMap.put("title",title);
                        hashMap.put("popularity",popularity);
                        hashMap.put("overview",over_view);
                        hashMap.put("id",id);


                        /*final Cursor moviesData = databaseHandler.getAllMovieData();*/


                        if (jsonArray.length() > databaseHandler.getAllMovieData().size()) {

                            ContentValues contentValues = new ContentValues();
                            SQLiteDatabase db = databaseHandler.getWritableDatabase();

                            contentValues.put(DatabaseHandler.MOVIE_ID, id);
                            contentValues.put(DatabaseHandler.OVERVIEW, over_view);
                            contentValues.put(DatabaseHandler.POPULARITY, popularity);
                            contentValues.put(DatabaseHandler.POSTER_PATH, poster_path);
                            contentValues.put(DatabaseHandler.TITLE, title);

                            db.insert(DatabaseHandler.TABLE_NAME, null, contentValues);
                        }
                        moviesList.add(hashMap);

                    }

                    attachAdapter(moviesList);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("errror",error.getMessage());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void attachAdapter(ArrayList<HashMap<String,String>> moviesList) {

        MoviesAdapter moviesAdapter = new MoviesAdapter(moviesList,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(moviesAdapter);

    }
}
