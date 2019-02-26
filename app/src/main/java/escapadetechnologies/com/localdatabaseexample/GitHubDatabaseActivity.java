package escapadetechnologies.com.localdatabaseexample;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

public class GitHubDatabaseActivity extends AppCompatActivity {

    RecyclerView githubDatabaseRecyclerView;
    DatabaseHandler databaseHandler;
    Bundle bundle;
    TextView name,fullname,avatar_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_git_hub_database);


        name = findViewById(R.id.name);
        fullname = findViewById(R.id.fullName);
        avatar_url = findViewById(R.id.avatar_url);

        bundle = getIntent().getExtras();

        if (bundle != null){

            String id = bundle.getString("id");
            executeSqldata(id);

        }

        databaseHandler = new DatabaseHandler(this);





        githubDatabaseRecyclerView = findViewById(R.id.githubDatabaseRecyclerView);


        getSqliteData();

    }

    private void executeSqldata(String id) {

        SQLiteDatabase db = DatabaseHandler.getInstance(this).getReadableDatabase();


        //String query = "SELECT * FROM " + DatabaseHandler.TABLE_GITHUB_NAME + " WHERE " + DatabaseHandler.GITHUB_ID + " = " + id.trim();

        //Cursor cursor = db.rawQuery("SELECT * FROM GITHUB where github_id = " + id,null);
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHandler.TABLE_GITHUB_NAME + " WHERE " + DatabaseHandler.GITHUB_ID + " = " + id,null);

        try{


            if (cursor.getCount() > 0 && cursor.moveToFirst()){

                String nameid = cursor.getString(cursor.getColumnIndex(DatabaseHandler.NAME));

                String fullnameid = cursor.getString(cursor.getColumnIndex(DatabaseHandler.FULL_NAME));

                String avatar = cursor.getString(cursor.getColumnIndex(DatabaseHandler.AVATAR_URL));


                name.setText(nameid);
                fullname.setText(fullnameid);
                avatar_url.setText(avatar);

            }



        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void getSqliteData() {

        ArrayList arrayList = databaseHandler.getAllGithubData();

        GithubListAdpater githubListAdpater = new GithubListAdpater(arrayList,this);

        githubDatabaseRecyclerView.setHasFixedSize(true);
        githubDatabaseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        githubDatabaseRecyclerView.setAdapter(githubListAdpater);


    }
}
