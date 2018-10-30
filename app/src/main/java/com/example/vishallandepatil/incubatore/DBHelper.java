package com.example.vishallandepatil.incubatore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import landepatil.vishal.sqlitebuilder.TableFactory;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context){
        super(context,"Incubatore",null,1);
    }
    public void onCreate(SQLiteDatabase db) {
        TableFactory.createTable(ReadingTable.class,db);

    }
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {


    }
}