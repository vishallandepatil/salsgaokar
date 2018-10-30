package com.example.vishallandepatil.incubatore.login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vishallandepatil.incubatore.reading.database.ReadingTable;
import com.example.vishallandepatil.incubatore.setting.database.Incubatore;

import landepatil.vishal.sqlitebuilder.TableFactory;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context){
        super(context,"Incubatore",null,1);
    }
    public void onCreate(SQLiteDatabase db) {
        TableFactory.createTable(ReadingTable.class,db);
        TableFactory.createTable(Incubatore.class,db);

    }
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {


    }
}