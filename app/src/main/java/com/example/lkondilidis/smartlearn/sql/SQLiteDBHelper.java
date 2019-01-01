package com.example.lkondilidis.smartlearn.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lkondilidis.smartlearn.model.User;

import java.util.ArrayList;
import java.util.List;


public class SQLiteDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UserDetails";

    public static final String TABLE_NAME = "user";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_USER_EMAIL = "user_email";
    public static final String KEY_USER_PASSWORD = "user_password";
    public static final String KEY_USER_NICKNAME = "user_nickname";
    public static final String KEY_STUDIES = "studies";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_PLAN = "plan";
    public static final String KEY_RATING = "rating";
    //Create Table Query
    private static final String SQL_CREATE_USER =
            "CREATE TABLE user (" + KEY_USER_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_USER_NAME + " TEXT, " + KEY_USER_EMAIL + "  TEXT, "
                    + KEY_USER_PASSWORD + "  TEXT, " + KEY_USER_NICKNAME + "  TEXT, "
                    + KEY_STUDIES + "  TEXT, " + KEY_SUBJECT + "  TEXT, "
                    + KEY_PLAN + "  TEXT, " + KEY_RATING + "  INTEGER  );";

    private static final String SQL_DELETE_USER =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //Drop the table while upgrading the database
        // such as adding new column or changing existing constraint
        db.execSQL(SQL_DELETE_USER);
        this.onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.onUpgrade(db, oldVersion, newVersion);
    }

    public long addUser(User user) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        //Create a map having movie details to be inserted
        ContentValues user_details = new ContentValues();
        user_details.put(KEY_USER_NAME, user.getName());
        user_details.put(KEY_USER_EMAIL, user.getEmail());
        user_details.put(KEY_USER_NICKNAME, user.getNickname());
        user_details.put(KEY_USER_PASSWORD, user.getPassword());
        user_details.put(KEY_STUDIES, user.getStudies());
        user_details.put(KEY_SUBJECT, user.getSubject());
        user_details.put(KEY_PLAN, user.getPlan());
        user_details.put(KEY_RATING, user.getRatings());

        long newRowId = db.insert(TABLE_NAME, null, user_details);
        db.close();
        return newRowId;

    }

    public List getAllUser() {
        List userDetailsList = new ArrayList();
        String selectQuery = "SELECT * FROM " + TABLE_NAME
                + " ORDER BY " + KEY_USER_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //if TABLE has rows
        if (cursor.moveToFirst()) {
            //Loop through the table rows
            do {
                User userDetails = new User();
                userDetails.setId(cursor.getInt(0));
                userDetails.setName(cursor.getString(1));
                userDetails.setEmail(cursor.getString(2));
                userDetails.setPassword(cursor.getString(3));
                userDetails.setNickname(cursor.getString(4));
                userDetails.setStudies(cursor.getString(5));
                userDetails.setSubject(cursor.getString(6));
                userDetails.setPlan(cursor.getString(7));
                userDetails.setRatings(cursor.getInt(8));

                //Add movie details to list
                userDetailsList.add(userDetails);
            } while (cursor.moveToNext());
        }
        db.close();
        return userDetailsList;
    }

    public User getUserEmail(String email) {

        User userDetails = new User();
        SQLiteDatabase db = this.getReadableDatabase();
        //specify the columns to be fetched
        String[] columns = {KEY_USER_ID, KEY_USER_NAME, KEY_USER_EMAIL, KEY_USER_PASSWORD, KEY_USER_NICKNAME,
                KEY_STUDIES, KEY_SUBJECT, KEY_PLAN, KEY_RATING};
        //Select condition
        String selection = KEY_USER_EMAIL + " = ?";
        //Arguments for selection
        String[] selectionArgs = {String.valueOf(email)};


        Cursor cursor = db.query(TABLE_NAME, columns, selection,
                selectionArgs, null, null, null);
        if (null != cursor) {
            cursor.moveToFirst();
            userDetails.setId(cursor.getInt(0));
            userDetails.setName(cursor.getString(1));
            userDetails.setEmail(cursor.getString(2));
            userDetails.setPassword(cursor.getString(3));
            userDetails.setNickname(cursor.getString(4));
            userDetails.setStudies(cursor.getString(5));
            userDetails.setSubject(cursor.getString(6));
            userDetails.setPlan(cursor.getString(7));
            userDetails.setRatings(cursor.getInt(8));

        }
        db.close();
        return userDetails;

    }

    public User getUser(int user_id) {

        User userDetails = new User();
        SQLiteDatabase db = this.getReadableDatabase();
        //specify the columns to be fetched
        String[] columns = {KEY_USER_ID, KEY_USER_NAME, KEY_USER_EMAIL, KEY_USER_PASSWORD, KEY_USER_NICKNAME,
                KEY_STUDIES, KEY_SUBJECT, KEY_PLAN, KEY_RATING};
        //Select condition
        String selection = KEY_USER_ID + " = ?";
        //Arguments for selection
        String[] selectionArgs = {String.valueOf(user_id)};


        Cursor cursor = db.query(TABLE_NAME, columns, selection,
                selectionArgs, null, null, null);
        if (null != cursor) {
            cursor.moveToFirst();
            userDetails.setId(cursor.getInt(0));
            userDetails.setName(cursor.getString(1));
            userDetails.setEmail(cursor.getString(2));
            userDetails.setPassword(cursor.getString(3));
            userDetails.setNickname(cursor.getString(4));
            userDetails.setStudies(cursor.getString(5));
            userDetails.setSubject(cursor.getString(6));
            userDetails.setPlan(cursor.getString(7));
            userDetails.setRatings(cursor.getInt(8));

        }
        db.close();
        return userDetails;

    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String userIds[] = {String.valueOf(user.getId())};

        ContentValues user_details = new ContentValues();
        user_details.put(KEY_USER_NAME, user.getName());
        user_details.put(KEY_USER_EMAIL, user.getEmail());
        user_details.put(KEY_USER_NICKNAME, user.getNickname());
        user_details.put(KEY_USER_PASSWORD, user.getPassword());
        user_details.put(KEY_STUDIES, user.getStudies());
        user_details.put(KEY_SUBJECT, user.getSubject());
        user_details.put(KEY_PLAN, user.getPlan());
        user_details.put(KEY_RATING, user.getRatings());
        db.update(TABLE_NAME, user_details, KEY_USER_ID + " = ?", userIds);
        db.close();
    }

    public void deleteUser(int userId) {
        String userIds[] = {String.valueOf(userId)};
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_USER_ID + " = ?", userIds);
        db.close();
    }

    /**
     * check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                KEY_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = KEY_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * SELECT user_id FROM user WHERE user_email = 'smartlearnexample.com';
         */
        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                KEY_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = KEY_USER_EMAIL + " = ?" + " AND " + KEY_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * SELECT user_id FROM user WHERE user_email = 'smartlearnexample.com' AND user_password = 'smartlearn';
         */
        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

}