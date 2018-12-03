package com.example.vishallandepatil.incubatore.reading.database;

import landepatil.vishal.sqlitebuilder.annotations.PrimaryKey;

public class ReadingTable
{

    @PrimaryKey
    String id;

    String DateTime;
    String IncubatoreId;
    String year;
    String month;
    String day;

    String coreading;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getIncubatoreId() {
        return IncubatoreId;
    }

    public void setIncubatoreId(String incubatoreId) {
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
