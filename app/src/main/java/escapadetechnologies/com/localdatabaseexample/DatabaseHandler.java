package escapadetechnologies.com.localdatabaseexample;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        super(context, DATABASE_NAME, null, 1);
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


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
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

    public Cursor searchDb(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        /*return db.query(TABLE_NAME,new String[]{TITLE,POPULARITY,OVERVIEW,MOVIE_ID},TITLE + " LIKE" + "'%" + query + "%' OR " + POPULARITY +
                        " LIKE" + "'%" + query + "%' OR " + OVERVIEW + " LIKE" + "'%" + query + "%' OR " + MOVIE_ID + " LIKE" + "'%",
                null, null, null, null, null);*/

        String data = "SELECT * FROM " + DatabaseHandler.TABLE_NAME + " WHERE " + DatabaseHandler.TITLE + " OR " + DatabaseHandler.OVERVIEW + " like "+"'"+query+"'";
        Cursor cursor = db.rawQuery(data,null);
        return cursor;
    }
}
