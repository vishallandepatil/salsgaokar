package com.example.vishallandepatil.incubatore.setting.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface IncubatorsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAll(Incubatore user);

    @Query("select * from incubatore")
    LiveData<List<Incubatore>> fetchAllIncubators();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateIncubator(Incubatore users);

    @Query("update incubatore set name=:name where id =:id")
    long updateIncubatorNameByid(int id, String name);

}