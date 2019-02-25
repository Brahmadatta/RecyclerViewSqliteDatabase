package escapadetechnologies.com.localdatabaseexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DataDecidingActivity extends AppCompatActivity implements View.OnClickListener {

    Button openMovieData,openGithubData,githubdataseData,moviesDatabaseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_deciding);


        openMovieData = findViewById(R.id.openMovieData);
        openGithubData = findViewById(R.id.openGithubData);
        githubdataseData = findViewById(R.id.github_databasedata);
        moviesDatabaseData = findViewById(R.id.movies_database_data);
        openMovieData.setOnClickListener(this);
        openGithubData.setOnClickListener(this);
        moviesDatabaseData.setOnClickListener(this);
        githubdataseData.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.openMovieData:
                startActivity(new Intent(this,MainActivity.class));
                break;

            case R.id.openGithubData:
                startActivity(new Intent(this,GitHubListActivity.class));
                break;

            case R.id.movies_database_data:
                startActivity(new Intent(this,SqliteDataActivity.class));
                break;

            case R.id.github_databasedata:
                startActivity(new Intent(this,GitHubDatabaseActivity.class));
                break;
        }
    }
}
