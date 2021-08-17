package com.example.spacexcrew;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Crew.class}, version = 1, exportSchema = false)
abstract public class CrewDatabase extends RoomDatabase {

    public abstract CrewDao crewDao();

    private static volatile CrewDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static CrewDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CrewDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CrewDatabase.class, "crew_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
