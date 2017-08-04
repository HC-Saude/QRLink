package com.happycode.qrlink;

/**
 * Created by lucas on 03/08/17.
 */
// leembrar de sempre aumentar o DATABASE_VERSION, nunca diminuir

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "paths.db";
    private static final int DATABASE_VERSION = 3;


    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }
}