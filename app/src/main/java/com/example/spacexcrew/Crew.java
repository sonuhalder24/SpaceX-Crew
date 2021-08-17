package com.example.spacexcrew;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "crew_table")
public class Crew {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "crew_name")
    public String crew_name;

    @ColumnInfo(name = "agency")
    public String agency;

    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "wikipedia")
    public String wikipedia;

    @ColumnInfo(name = "status")
    public String status;

    public Crew(String crew_name,String agency,String image,String wikipedia,String status) {
        this.crew_name = crew_name;
        this.agency=agency;
        this.image=image;
        this.wikipedia=wikipedia;
        this.status=status;
    }


}
