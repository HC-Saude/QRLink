package com.happycode.qrlink;

/**
 * Created by lucas on 03/08/17.
 */

//funções de acesso à db

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }



    public List<String> teste(){
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM paths", null);
        cursor.moveToFirst();
        int i = 0;
        while ( (!cursor.isAfterLast())){

            list.add(cursor.getString(0));
            i++;
            cursor.moveToNext();
        }

        cursor.close();
        return list;
    }

    public dbEntry getEntry(String key){
        dbEntry att = new dbEntry("","","");
        Cursor cursor = database.rawQuery("SELECT * FROM paths", null);
        cursor.moveToFirst();

        while ( (!cursor.isAfterLast())){

            if (cursor.getString(0).equalsIgnoreCase(key)){
                att.setId(cursor.getString(0));
                att.setLocal(cursor.getString(1));
                att.setUrl(cursor.getString(2));
            }

            cursor.moveToNext();
        }

        cursor.close();
        return att;
    }

}