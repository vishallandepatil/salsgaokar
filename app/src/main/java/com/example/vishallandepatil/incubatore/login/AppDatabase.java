package com.example.vishallandepatil.incubatore.login;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.vishallandepatil.incubatore.reading.database.ReadingTable;
import com.example.vishallandepatil.incubatore.reading.database.ReadingTableDao;
import com.example.vishallandepatil.incubatore.setting.database.Incubatore;
import com.example.vishallandepatil.incubatore.setting.database.IncubatorsDao;


@Database(entities = {Incubatore.class,ReadingTable.class ,}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public static String DATABASE_NAME="incumonitor";
    public abstract IncubatorsDao uncubatorsDao();
    public abstract ReadingTableDao readingTableDao();


    private static volatile AppDatabase INSTANCE;

   public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}