package escapadetechnologies.com.localdatabaseexample;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "local.db";
    private static DatabaseHandler sInstance;

    //table name
    public static final String TABLE_NAME = "MOVIES";


    //column names
    public static final String ID = "id";
    public static final String MOVIE_ID = "movie_id";
    public static final String TITLE = "title";
    public static final String POPULARITY = "popularity";
    public static final String POSTER_PATH = "poster_path";
    public static final String OVERVIEW = "overview";


    //github data table name
    public static final String TABLE_GITHUB_NAME = "GITHUB";

    //github table column names
    public static final String GITHUB_ID = "github_id";
    public static final String NAME = "name";
    public static final String FULL_NAME = "full_name";
    public static final String AVATAR_URL = "avatar_url";
    public static final String TYPE = "type";
    public static final String REPOS_URL = "repos_url";


    //github repos table name
    public static final String GITHUB_REPOS_TABLE = "GITHUB_REPOS";

    //github repos column names;
    public static final String GITHUB_REPOS_ID = "github_repos_id";



    public static synchronized DatabaseHandler getInstance(Context context){
        if (sInstance == null){
            sInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    public static synchronized DatabaseHandler getsInstance(){
        if (sInstance == null){
            return null;
        }
        return sInstance;
    }


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create Movies table
        String CREATE_MOVIE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MOVIE_ID + " TEXT,"
                + TITLE + " TEXT,"
                + POPULARITY + " TEXT,"
                + POSTER_PATH + " TEXT,"
                + OVERVIEW + " TEXT"
                + ")";

        db.execSQL(CREATE_MOVIE_TABLE);


        String GITHUB_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GITHUB_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + GITHUB_ID + " TEXT UNIQUE,"
                + NAME + " TEXT,"
                + FULL_NAME + " TEXT,"
                + TYPE + " TEXT,"
                + AVATAR_URL + " TEXT,"
                + REPOS_URL + " TEXT"
                + ")";

        db.execSQL(GITHUB_TABLE);


        String GITHUB_REPOS = "CREATE TABLE IF NOT EXISTS " + GITHUB_REPOS_TABLE + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + GITHUB_REPOS_ID + " INTEGER UNIQUE,"
                + GITHUB_ID + " INTEGER,"
                + NAME + " TEXT,"
                + FULL_NAME + " TEXT,"
                + TYPE + " TEXT,"
                + AVATAR_URL + " TEXT,"
                + REPOS_URL + " TEXT,"
                + " FOREIGN KEY ("+GITHUB_ID+") REFERENCES "+DatabaseHandler.TABLE_GITHUB_NAME+ " ("+GITHUB_ID+"))";
                //+ " FOREIGN KEY ("+GITHUB_ID+") REFERENCES "+GITHUB_TABLE+" ("+GITHUB_ID+"));";

        db.execSQL(GITHUB_REPOS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GITHUB_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GITHUB_REPOS_TABLE);
    }


    public ArrayList getAllMovieData(){
        /*SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME,null);*/

        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHandler.TABLE_NAME,null);
        if (cursor.moveToFirst()){

            do {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(DatabaseHandler.ID, cursor.getString(cursor.getColumnIndex(DatabaseHandler.ID)));
                hashMap.put(DatabaseHandler.TITLE, cursor.getString(cursor.getColumnIndex(DatabaseHandler.TITLE)));
                hashMap.put(DatabaseHandler.OVERVIEW, cursor.getString(cursor.getColumnIndex(DatabaseHandler.OVERVIEW)));
                hashMap.put(DatabaseHandler.POPULARITY, cursor.getString(cursor.getColumnIndex(DatabaseHandler.POPULARITY)));
                hashMap.put(DatabaseHandler.POSTER_PATH, cursor.getString(cursor.getColumnIndex(DatabaseHandler.POSTER_PATH)));
                arrayList.add(hashMap);
            }while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return arrayList;
    }


    public ArrayList getAllGithubData(){


        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHandler.TABLE_GITHUB_NAME,null);
        if (cursor.moveToFirst()){
            do {

                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(DatabaseHandler.ID, cursor.getString(cursor.getColumnIndex(DatabaseHandler.ID)));
                hashMap.put(GITHUB_ID,cursor.getString(cursor.getColumnIndex(GITHUB_ID)));
                hashMap.put(NAME,cursor.getString(cursor.getColumnIndex(NAME)));
                hashMap.put(FULL_NAME,cursor.getString(cursor.getColumnIndex(FULL_NAME)));
                hashMap.put(TYPE,cursor.getString(cursor.getColumnIndex(TYPE)));
                //byte[] avatar = cursor.getBlob(cursor.getColumnIndex(AVATAR_URL));
                /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // Could be Bitmap.CompressFormat.PNG or Bitmap.CompressFormat.WEBP
                byte[] bai = baos.toByteArray();*/
                hashMap.put(AVATAR_URL, cursor.getString(cursor.getColumnIndex(AVATAR_URL)));
                hashMap.put(REPOS_URL,cursor.getString(cursor.getColumnIndex(REPOS_URL)));
                arrayList.add(hashMap);

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;

    }


    public ArrayList getGithubReposDataById(String id){

        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHandler.GITHUB_REPOS_TABLE + " WHERE " + GITHUB_REPOS_ID + " = " + id,null);

        if (cursor.moveToFirst()){
            do {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(DatabaseHandler.ID,cursor.getString(cursor.getColumnIndex(DatabaseHandler.ID)));
                hashMap.put(GITHUB_REPOS_ID,cursor.getString(cursor.getColumnIndex(DatabaseHandler.GITHUB_REPOS_ID)));
                hashMap.put(GITHUB_ID,cursor.getString(cursor.getColumnIndex(GITHUB_ID)));
                hashMap.put(NAME,cursor.getString(cursor.getColumnIndex(NAME)));
                hashMap.put(FULL_NAME,cursor.getString(cursor.getColumnIndex(FULL_NAME)));
                hashMap.put(TYPE,cursor.getString(cursor.getColumnIndex(TYPE)));
                //byte[] avatar = cursor.getBlob(cursor.getColumnIndex(AVATAR_URL));
                /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // Could be Bitmap.CompressFormat.PNG or Bitmap.CompressFormat.WEBP
                byte[] bai = baos.toByteArray();*/
                hashMap.put(AVATAR_URL, cursor.getString(cursor.getColumnIndex(AVATAR_URL)));
                hashMap.put(REPOS_URL,cursor.getString(cursor.getColumnIndex(REPOS_URL)));
                arrayList.add(hashMap);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;
    }



    public ArrayList getAllGithubReposData(){


        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHandler.GITHUB_REPOS_TABLE,null);

        if (cursor.moveToFirst()){
            do {

                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(DatabaseHandler.ID,cursor.getString(cursor.getColumnIndex(DatabaseHandler.ID)));
                hashMap.put(GITHUB_REPOS_ID,cursor.getString(cursor.getColumnIndex(DatabaseHandler.GITHUB_REPOS_ID)));
                hashMap.put(GITHUB_ID,cursor.getString(cursor.getColumnIndex(GITHUB_ID)));
                hashMap.put(NAME,cursor.getString(cursor.getColumnIndex(NAME)));
                hashMap.put(FULL_NAME,cursor.getString(cursor.getColumnIndex(FULL_NAME)));
                hashMap.put(TYPE,cursor.getString(cursor.getColumnIndex(TYPE)));
                //byte[] avatar = cursor.getBlob(cursor.getColumnIndex(AVATAR_URL));
                /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // Could be Bitmap.CompressFormat.PNG or Bitmap.CompressFormat.WEBP
                byte[] bai = baos.toByteArray();*/
                hashMap.put(AVATAR_URL, cursor.getString(cursor.getColumnIndex(AVATAR_URL)));
                hashMap.put(REPOS_URL,cursor.getString(cursor.getColumnIndex(REPOS_URL)));
                arrayList.add(hashMap);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    public Cursor searchDb(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        /*return db.query(TABLE_NAME,new String[]{TITLE,POPULARITY,OVERVIEW,MOVIE_ID},TITLE + " LIKE" + "'%" + query + "%' OR " + POPULARITY +
                        " LIKE" + "'%" + query + "%' OR " + OVERVIEW + " LIKE" + "'%" + query + "%' OR " + MOVIE_ID + " LIKE" + "'%",
                null, null, null, null, null);*/

        //String data = "SELECT * FROM " + DatabaseHandler.TABLE_NAME + " WHERE " + DatabaseHandler.TITLE + " OR " + DatabaseHandler.OVERVIEW + " like "+"'"+query+"'";
        String data = "SELECT * FROM " +DatabaseHandler.TABLE_NAME + " WHERE " + DatabaseHandler.TITLE + " like "+"'"+query+"'";
        return db.rawQuery(data,null);
    }
}
