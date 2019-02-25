package escapadetechnologies.com.localdatabaseexample;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GitHubListActivity extends AppCompatActivity {

    RecyclerView githubRecyclerView;

    public static final String GITHUB_URL = "https://api.github.com/repositories?since=1";

    ArrayList<HashMap<String,String>> githubnArrayaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_git_hub_list);

        githubRecyclerView = findViewById(R.id.githubRecyclerView);

        githubnArrayaList = new ArrayList<>();

        getGithubData();

    }

    private void getGithubData() {

        StringRequest stringRequest = new StringRequest(GITHUB_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    githubnArrayaList = new ArrayList<>();


                    for (int i = 0; i < jsonArray.length() ; i++){


                        HashMap<String,String> hashMap = new HashMap<>();

                        String name = jsonArray.getJSONObject(i).getString("name");
                        String fullname = jsonArray.getJSONObject(i).getString("full_name");
                        String id = jsonArray.getJSONObject(i).getString("id");
                        String owner = jsonArray.getJSONObject(i).getString("owner");

                        JSONObject object = new JSONObject(owner);
                        String avatar_url = object.getString("avatar_url");
                        String type = object.getString("type");


                        hashMap.put("name",name);
                        hashMap.put("full_name",fullname);
                        hashMap.put("id",id);
                        hashMap.put("avatar_url",avatar_url);
                        hashMap.put("type",type);


                        githubnArrayaList.add(hashMap);

                    }

                    attachAdapter(githubnArrayaList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("errorr",error.getMessage());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }

    private void attachAdapter(ArrayList<HashMap<String, String>> githubnArrayaList) {

        GithubListAdpater githubListAdpater = new GithubListAdpater(githubnArrayaList,this);
        githubRecyclerView.setHasFixedSize(true);
        githubRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        githubRecyclerView.setAdapter(githubListAdpater);

    }
}
