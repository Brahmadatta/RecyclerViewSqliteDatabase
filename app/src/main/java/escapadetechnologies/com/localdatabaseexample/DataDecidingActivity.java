package escapadetechnologies.com.localdatabaseexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DataDecidingActivity extends AppCompatActivity implements View.OnClickListener {

    Button openMovieData,openGithubData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_deciding);


        openMovieData = findViewById(R.id.openMovieData);
        openGithubData = findViewById(R.id.openGithubData);
        openMovieData.setOnClickListener(this);
        openGithubData.setOnClickListener(this);

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
        }
    }
}
