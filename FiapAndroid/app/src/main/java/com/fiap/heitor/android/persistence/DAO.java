package com.fiap.heitor.android.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fiap.heitor.android.exception.FiapDatabaseException;
import com.fiap.heitor.android.model.AuthResponse;
import com.fiap.heitor.android.model.Place;
import com.fiap.heitor.android.model.User;

import java.util.ArrayList;
import java.util.List;


public class DAO implements Repository {

    private static DAO mInstance;
    private PlaceDBHelper dbHelper;
    private static Context mCtx;
    private SQLiteDatabase mDB;

    private DAO() {
        dbHelper = new PlaceDBHelper(mCtx);
        mDB = dbHelper.getWritableDatabase();
    }

    public static DAO getInstance(Context ctx) {
        if (mInstance == null) {
            mCtx = ctx;
            mInstance = new DAO();
        }
        return mInstance;
    }

    @Override
    public void save(Place place) throws FiapDatabaseException {
        ContentValues cv = buildContentValue(place);
        Long result = mDB.insert(dbHelper.PLACE_TABLE_NAME, null, cv);
        if (result == -1) {
            throw new FiapDatabaseException("Could Save in the DB");
        }
    }

    private ContentValues buildContentValue(Place place) {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.PLACE_COLUMN_NAME, place.getFeatureName());
        cv.put(dbHelper.PLACE_COLUMN_LATITUDE, place.getLatitude());
        cv.put(dbHelper.PLACE_COLUMN_LONGITUDE, place.getLongitude());
        cv.put(dbHelper.PLACE_UNIQUE_COLUMN, place.toString());
        return cv;
    }

    @Override
    public int delete(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(dbHelper.PLACE_TABLE_NAME,
                dbHelper.PLACE_COLUMN_ID + " = ? ",
                new String[]{id});
        return result;
    }

    @Override
    public Place show(String idPlace) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + dbHelper.PLACE_TABLE_NAME
                + " WHERE " + dbHelper.PLACE_COLUMN_ID + " =?", new String[]{idPlace});
        if (cursor.moveToFirst()) {
            String namePlace = cursor.getString(cursor.getColumnIndex(dbHelper.PLACE_COLUMN_NAME));
            double latitude = cursor.getDouble(cursor.getColumnIndex(dbHelper.PLACE_COLUMN_LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndex(dbHelper.PLACE_COLUMN_LONGITUDE));
            String id = cursor.getString(cursor.getColumnIndex(dbHelper.PLACE_COLUMN_ID));
            Place place = new Place(namePlace, latitude, longitude);
            place.setId(id);
            return place;
        }

        return null;
    }

    @Override
    public List<Place> listAll() {
        List<Place> placeList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + dbHelper.PLACE_TABLE_NAME, null);
        String name;
        double latitude;
        double longitude;
        String id;
        Place place;

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                name = cursor.getString(cursor.getColumnIndex(dbHelper.PLACE_COLUMN_NAME));
                latitude = cursor.getDouble(cursor.getColumnIndex(dbHelper.PLACE_COLUMN_LATITUDE));
                longitude = cursor.getDouble(cursor.getColumnIndex(dbHelper.PLACE_COLUMN_LONGITUDE));
                id = cursor.getString(cursor.getColumnIndex(dbHelper.PLACE_COLUMN_ID));
                place = new Place(name, latitude, longitude);
                place.setId(id);
                placeList.add(place);
                cursor.moveToNext();
            }
        }
        return placeList;
    }

    @Override
    public void update(Place place) throws FiapDatabaseException {
        ContentValues cv = buildContentValue(place);
        int result = mDB.update(dbHelper.PLACE_TABLE_NAME, cv, "_id=" + place.getId(), null);
        if (result == -1) {
            throw new FiapDatabaseException("Could Save in the DB");
        }
    }

    /**
     * USER DB
     */

    private ContentValues buildUserContentValue(User user) {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.USER_COLUMN_NAME, user.getName());
        cv.put(dbHelper.PASSWORD_COLUMN_NAME, user.getPassword());
        return cv;
    }

    public void saveUser(User user) throws FiapDatabaseException {
        ContentValues cv = buildUserContentValue(user);
        Long result = mDB.insert(dbHelper.USER_TABLE_NAME, null, cv);
        if (result == -1) {
            throw new FiapDatabaseException("Could Save in the DB");
        }
    }

    public int deleteUser(User user) throws FiapDatabaseException {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(dbHelper.USER_TABLE_NAME,
                dbHelper.PLACE_COLUMN_ID + " = ? ",
                new String[]{user.getId()});
        return result;
    }

    public User findOneUser(String id) throws FiapDatabaseException {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + dbHelper.USER_TABLE_NAME
                + " WHERE " + dbHelper.PLACE_COLUMN_ID + " =?", new String[]{id});
        if (cursor.moveToFirst()) {
            String userName = cursor.getString(cursor.getColumnIndex(dbHelper.USER_COLUMN_NAME));
            String userId = cursor.getString(cursor.getColumnIndex(dbHelper.PLACE_COLUMN_ID));
            User user = new User();
            user.setName(userName);
            user.setId(userId);
            return user;
        }
        return null;
    }

    public User findOneUser() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + dbHelper.USER_TABLE_NAME, null);
        String name;
        String password;
        String id;
        User user;

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                name = cursor.getString(cursor.getColumnIndex(dbHelper.PLACE_COLUMN_NAME));
                password = cursor.getString(cursor.getColumnIndex(dbHelper.PASSWORD_COLUMN_NAME));
                id = cursor.getString(cursor.getColumnIndex(dbHelper.PLACE_COLUMN_ID));
                user = new User();
                user.setId(id);
                user.setName(name);
                user.setPassword(password);
                userList.add(user);
                break;
            }
        }

        if (userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }
    }

    /**
     * USER SESSION
     */

    private ContentValues buildSessionUserContentValue(AuthResponse auth) {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.SESSION_USER_COLUMN_NAME, auth.getmUser());
        cv.put(dbHelper.SESSION_PASSWORD_COLUMN_NAME, auth.getmPassword());
        return cv;
    }

    public void saveUserSession(AuthResponse auth) throws FiapDatabaseException {
        ContentValues cv = buildSessionUserContentValue(auth);
        Long result = mDB.insert(dbHelper.SESSION_TABLE_NAME, null, cv);
        if (result == -1) {
            throw new FiapDatabaseException("Could Save in the DB");
        }
    }

    public AuthResponse findOneUserSession() {
        List<AuthResponse> userList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + dbHelper.SESSION_TABLE_NAME, null);
        String name;
        String password;
        String id;
        AuthResponse auth;

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                name = cursor.getString(cursor.getColumnIndex(dbHelper.PLACE_COLUMN_NAME));
                password = cursor.getString(cursor.getColumnIndex(dbHelper.PASSWORD_COLUMN_NAME));
                id = cursor.getString(cursor.getColumnIndex(dbHelper.PLACE_COLUMN_ID));
                auth = new AuthResponse();
                auth.setId(id);
                auth.setmUser(name);
                auth.setmPassword(password);
                userList.add(auth);
                break;
            }
        }
        return userList.get(0);
    }


}
