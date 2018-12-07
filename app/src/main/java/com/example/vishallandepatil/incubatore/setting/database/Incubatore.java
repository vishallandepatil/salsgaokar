package com.example.vishallandepatil.incubatore.setting.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(indices = {@Index("id")})
public class Incubatore implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;

    public Incubatore()
    {

    }
    protected Incubatore(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Incubatore> CREATOR = new Creator<Incubatore>() {
        @Override
        public Incubatore createFromParcel(Parcel in) {
            return new Incubatore(in);
        }

        @Override
        public Incubatore[] newArray(int size) {
            return new Incubatore[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @ColumnInfo(name = "name")
    String name;
}
