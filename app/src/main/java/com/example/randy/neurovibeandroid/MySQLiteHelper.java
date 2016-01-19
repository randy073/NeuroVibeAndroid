package com.example.randy.neurovibeandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "NeuroVibeDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_RECORD_TABLE = "CREATE TABLE records ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "time DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "mode TEXT, " +
                "manualOrAuto TEXT, " +
                "person TEXT, " +
                "vibration TEXT, " +
                "generatedNumber TEXT, " +
                "guessedNumber TEXT )";

        // create books table
        db.execSQL(CREATE_RECORD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS records");

        // create fresh books table
        this.onCreate(db);
    }

    public void addRecord(String mode, String manualOrAuto, String person, String vibration, String generatedNumber, String guessedNumber){

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("mode", mode);
        values.put("manualOrAuto", manualOrAuto);
        values.put("person", person);
        values.put("vibration", vibration);
        values.put("generatedNumber", generatedNumber);
        values.put("guessedNumber", guessedNumber);

        // 3. insert
        long insert = db.insert("records", // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }


}
