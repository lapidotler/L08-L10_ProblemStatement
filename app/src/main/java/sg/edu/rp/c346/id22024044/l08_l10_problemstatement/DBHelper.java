package sg.edu.rp.c346.id22024044.l08_l10_problemstatement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    // Start version with 1
    // increment by 1 whenever db schema changes.
    private static final int DATABASE_VER = 1;

    // Filename of the database
    private static final String DATABASE_NAME = "song.db";

    private static final String TABLE_SONG = "song";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_SINGER = "singer";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_SONG +  "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_SINGER + " TEXT,"
                + COLUMN_YEAR + " INTEGER,"
                + COLUMN_STARS + " INTEGER )";
        db.execSQL(createTableSql);
        Log.i("info" ,"created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);

        // Create table(s) again
        onCreate(db);
    }

    public void insertSong(String title, String singers, int year, int stars){
        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();

        // We use ContentValues object to store the values for the db operation
        ContentValues values = new ContentValues();

        // Store the column name as key and NEEDED FIELDS as value
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_SINGER, singers);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_STARS, stars);

        // Insert the row into the TABLE_SONG
        db.insert(TABLE_SONG, null, values);

        // Close the database connection
        db.close();
    }

    public ArrayList<Song> getSongs() {
        ArrayList<Song> songs = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGER, COLUMN_YEAR, COLUMN_STARS};

        Cursor cursor = db.query(TABLE_SONG, columns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song song = new Song(id, title, singers, year, stars);
                songs.add(song);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return songs;
    }

    public ArrayList<Song> getTitle(boolean ascending) {
        ArrayList<Song> songs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sortOrder = ascending ? "ASC" : "DESC";
        String query = "SELECT * FROM " + TABLE_SONG + " ORDER BY " + COLUMN_TITLE + " " + sortOrder;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song song = new Song(id, title, singers, year, stars);
                songs.add(song);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return songs;
    }

    public ArrayList<Song> getSinger(boolean ascending) {
        ArrayList<Song> songs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sortOrder = ascending ? "ASC" : "DESC";
        String query = "SELECT * FROM " + TABLE_SONG + " ORDER BY " + COLUMN_SINGER + " " + sortOrder;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song song = new Song(id, title, singers, year, stars);
                songs.add(song);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return songs;
    }

    public ArrayList<Song> get5Stars(int keyword) {
        ArrayList<Song> songs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns= { "*" };
        String condition = COLUMN_STARS + " Like ?";
        String[] args = { "%" +  keyword + "%"};

        String query = "SELECT * FROM " + TABLE_SONG + " WHERE " + COLUMN_SINGER + " LIKE " + keyword;

        Cursor cursor = db.query(TABLE_SONG, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song song = new Song(id, title, singers, year, stars);
                songs.add(song);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return songs;
    }

    public ArrayList<String> getDistinctYears() {
        ArrayList<String> years = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_YEAR};

        Cursor cursor = db.query(true, TABLE_SONG, columns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int year = cursor.getInt(0);
                years.add(String.valueOf(year));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return years;
    }

    public int updateSong(Song data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_SINGER, data.getSingers());
        values.put(COLUMN_YEAR, data.getYear());
        values.put(COLUMN_STARS, data.getStar());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_SONG, values, condition, args);
        if (result < 1){
            Log.d("DBHelper", "Update failed");
        }

        db.close();
        return result;
    }

    public int deleteSong(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SONG, condition, args);
        if (result < 1){
            Log.d("DBHelper", "Delete failed");
        }

        db.close();
        return result;
    }
}