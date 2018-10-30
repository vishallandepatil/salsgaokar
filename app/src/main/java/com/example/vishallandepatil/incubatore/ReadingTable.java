package com.example.vishallandepatil.incubatore;

public class ReadingTable {

    String id;
    String DateTime;
    String IncubatoreId;

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

    String coreading;
    String o2reaading;

}
