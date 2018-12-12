package com.example.vishallandepatil.incubatore.reading.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.vishallandepatil.incubatore.setting.database.Incubatore;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(indices = {@Index("id")})
public class ReadingTable
{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "DateTime")
    @TypeConverters({TimestampConverter.class})
    Date DateTime;
    @ColumnInfo(name = "IncubatoreId")
    int IncubatoreId;
    @ColumnInfo(name = "year")
    String year;
    @ColumnInfo(name = "month")
    String month;
    @ColumnInfo(name = "day")
    String day;
    @ColumnInfo(name = "coreading")
    String coreading;
    @ColumnInfo(name = "o2reaading")
    String o2reaading;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateTime() {
        return DateTime;
    }

    public void setDateTime(Date dateTime) {
        DateTime = dateTime;
    }

    public int getIncubatoreId() {
        return IncubatoreId;
    }

    public void setIncubatoreId(int incubatoreId) {
        IncubatoreId = incubatoreId;
    }

    public String getCoreading() {
        return coreading;
    }

    public void setCoreading(String coreading) {
        this.coreading = coreading;
    }

    public String getO2reaading() {
        return o2reaading;
    }

    public void setO2reaading(String o2reaading) {
        this.o2reaading = o2reaading;
    }

}
