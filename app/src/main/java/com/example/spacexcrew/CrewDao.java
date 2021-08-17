package com.example.spacexcrew;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CrewDao {

    @Query("DELETE FROM crew_table")
    void deleteAll();

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(Crew crew);

    @Query("SELECT * from crew_table")
    LiveData<List<Crew>> getAllCrews();
}
