package com.fiap.heitor.android.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class PlaceDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "PlaceDB.db";
    private static final int DATABASE_VERSION = 1;
    public static final String PLACE_TABLE_NAME = "place";
    public static final String PLACE_COLUMN_ID = "_id";
    public static final String PLACE_COLUMN_NAME = "name";
    public static final String PLACE_COLUMN_LATITUDE = "latitude";
    public static final String PLACE_COLUMN_LONGITUDE = "longitude";
    public static final String PLACE_UNIQUE_COLUMN = "info";

    //User
    public static final String USER_TABLE_NAME = "user";
    public static final String USER_COLUMN_NAME = "name";
    public static final String PASSWORD_COLUMN_NAME = "password";

    //Session
    public static final String SESSION_TABLE_NAME = "session";
    public static final String SESSION_USER_COLUMN_NAME = "name";
    public static final String SESSION_PASSWORD_COLUMN_NAME = "password";


    public PlaceDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PLACE_TABLE_NAME + "(" +
                PLACE_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                PLACE_COLUMN_NAME + " TEXT, " + PLACE_COLUMN_LATITUDE + " REAL,"
                + PLACE_COLUMN_LONGITUDE + " REAL, " + PLACE_UNIQUE_COLUMN + " TEXT)"
        );

        db.execSQL("CREATE TABLE " + USER_TABLE_NAME + "(" +
                PLACE_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                USER_COLUMN_NAME + " TEXT, " + PASSWORD_COLUMN_NAME + " TEXT)"
        );

        db.execSQL("CREATE TABLE " + SESSION_TABLE_NAME + "(" +
                PLACE_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                SESSION_USER_COLUMN_NAME + " TEXT, " + SESSION_PASSWORD_COLUMN_NAME + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PLACE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SESSION_TABLE_NAME);
        onCreate(db);
    }
}
