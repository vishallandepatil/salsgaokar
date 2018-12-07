package com.example.vishallandepatil.incubatore.reading.database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import java.util.List;

@Dao
public interface ReadingTableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ReadingTable> readingTables);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertReading(ReadingTable readingTables);

    @Query("SELECT * FROM readingtable WHERE IncubatoreId=:IncubatoreId")
    LiveData<List<ReadingTable>> fetchReading(int IncubatoreId) ;

    @Query("select * from readingtable")
    LiveData<List<ReadingTable>> fetchAllReadingTable ();
}