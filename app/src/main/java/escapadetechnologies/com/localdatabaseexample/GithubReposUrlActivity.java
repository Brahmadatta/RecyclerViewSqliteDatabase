package escapadetechnologies.com.localdatabaseexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GithubReposUrlActivity extends AppCompatActivity {

    RecyclerView githubRepoUrlRecyclerView;


    ArrayList<HashMap<String, String>> githubreposArrayList;

    DatabaseHandler databaseHandler;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_repos_url);

        githubRepoUrlRecyclerView = findViewById(R.id.githubRepoUrlRecyclerView);


        bundle = getIntent().getExtras();

        githubreposArrayList = new ArrayList<>();

        databaseHandler = new DatabaseHandler(this);

        if (bundle != null) {

            String repo_url = bundle.getString("repos_url");
            String id = bundle.getString("id");
            getGithubReposData(repo_url, id);
        }


    }

    private void getGithubReposData(String repo_url, final String github_id) {


        StringRequest request = new StringRequest(repo_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    githubreposArrayList = new ArrayList<>();



                    for (int i = 0; i < jsonArray.length(); i++) {


                        HashMap<String, String> hashMap = new HashMap<>();

                        String name = jsonArray.getJSONObject(i).getString("name");
                        String fullname = jsonArray.getJSONObject(i).getString("full_name");
                        String id = jsonArray.getJSONObject(i).getString("id");
                        String owner = jsonArray.getJSONObject(i).getString("owner");

                        JSONObject object = new JSONObject(owner);
                        String avatar_url = object.getString("avatar_url");
                        String type = object.getString("type");
                        String repos_url = object.getString("repos_url");


                        hashMap.put("name", name);
                        hashMap.put("full_name", fullname);
                        hashMap.put("id", id);
                        hashMap.put("avatar_url", avatar_url);
                        hashMap.put("type", type);
                        hashMap.put("repos_url", repos_url);


                        ContentValues contentValues = new ContentValues();

                        SQLiteDatabase database = databaseHandler.getWritableDatabase();

                        contentValues.put(DatabaseHandler.GITHUB_REPOS_ID, id);
                        contentValues.put(DatabaseHandler.GITHUB_ID, github_id);
                        contentValues.put(DatabaseHandler.NAME, name);
                        contentValues.put(DatabaseHandler.FULL_NAME, fullname);
                        contentValues.put(DatabaseHandler.TYPE, type);
                        contentValues.put(DatabaseHandler.AVATAR_URL, avatar_url);

                        database.insertWithOnConflict(DatabaseHandler.GITHUB_REPOS_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);


                        githubreposArrayList.add(hashMap);
                    }

                    /*attachAdapter(githubreposArrayList);*/

                    loadTheDatabasetoRecyclerView();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("errorr", error.getMessage());
                if (error.getClass().equals(NoConnectionError.class)){

                    if (databaseHandler.getAllGithubReposData().size() != 0){

                        ArrayList arrayList = databaseHandler.getGithubReposDataById(github_id);

                        if (arrayList.size() == 0){
                            Toast.makeText(GithubReposUrlActivity.this, "First download data through internet", Toast.LENGTH_SHORT).show();
                        }else {

                            GithubListAdpater githubListAdpater = new GithubListAdpater(arrayList, GithubReposUrlActivity.this);
                            githubRepoUrlRecyclerView.setHasFixedSize(true);
                            githubRepoUrlRecyclerView.setLayoutManager(new LinearLayoutManager(GithubReposUrlActivity.this));
                            githubRepoUrlRecyclerView.setAdapter(githubListAdpater);
                        }

                    }else {
                        Toast.makeText(GithubReposUrlActivity.this, "Please enable the internet", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

    private void loadTheDatabasetoRecyclerView() {

        if (checkInternetConnection(this)) {

            ArrayList arrayList = databaseHandler.getAllGithubReposData();
            GithubListAdpater githubListAdpater = new GithubListAdpater(arrayList, this);
            githubRepoUrlRecyclerView.setHasFixedSize(true);
            githubRepoUrlRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            githubRepoUrlRecyclerView.setAdapter(githubListAdpater);
        }else if (databaseHandler.getAllGithubReposData().size() != 0){
            ArrayList arrayList = databaseHandler.getAllGithubReposData();
            GithubListAdpater githubListAdpater = new GithubListAdpater(arrayList, this);
            githubRepoUrlRecyclerView.setHasFixedSize(true);
            githubRepoUrlRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            githubRepoUrlRecyclerView.setAdapter(githubListAdpater);
        }else {
            Toast.makeText(this, "Please Enable the Internet", Toast.LENGTH_SHORT).show();
        }

    }

    private void attachAdapter(ArrayList<HashMap<String, String>> githubreposArrayList) {

        GithubListAdpater githubListAdpater = new GithubListAdpater(githubreposArrayList, this);
        githubRepoUrlRecyclerView.setHasFixedSize(true);
        githubRepoUrlRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        githubRepoUrlRecyclerView.setAdapter(githubListAdpater);

    }

    private static boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}


