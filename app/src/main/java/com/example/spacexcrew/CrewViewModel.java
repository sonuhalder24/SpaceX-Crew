package com.example.spacexcrew;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CrewViewModel extends AndroidViewModel {
    private CrewRepository mRepository;

    private final LiveData<List<Crew>> mAllCrew;

    public CrewViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CrewRepository(application);
        mAllCrew = mRepository.getAllNotesData();
    }

    LiveData<List<Crew>> getAllCrews()
    {
        return mAllCrew;
    }

    public void fromAPI(Context context){
        mRepository.dataFromApi(context);
    }

    public void insert(Crew crew)
    {
        mRepository.insert(crew);
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }
}
