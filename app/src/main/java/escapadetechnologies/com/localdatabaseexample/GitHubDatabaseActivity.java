package escapadetechnologies.com.localdatabaseexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class GitHubDatabaseActivity extends AppCompatActivity {

    RecyclerView githubDatabaseRecyclerView;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_git_hub_database);


        databaseHandler = new DatabaseHandler(this);


        githubDatabaseRecyclerView = findViewById(R.id.githubDatabaseRecyclerView);


        getSqliteData();

    }

    private void getSqliteData() {

        ArrayList arrayList = databaseHandler.getAllGithubData();

        GithubListAdpater githubListAdpater = new GithubListAdpater(arrayList,this);

        githubDatabaseRecyclerView.setHasFixedSize(true);
        githubDatabaseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        githubDatabaseRecyclerView.setAdapter(githubListAdpater);


    }
}
