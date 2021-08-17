package com.example.spacexcrew;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class CrewRepository {

    private CrewDao mCrewDao;
    private LiveData<List<Crew>> mAllCrews;

    CrewRepository(Application application) {
        CrewDatabase db = CrewDatabase.getDatabase(application);
        mCrewDao = db.crewDao();
        mAllCrews = mCrewDao.getAllCrews();
    }

    LiveData<List<Crew>> getAllNotesData() {
        return mAllCrews;
    }

    void dataFromApi(final Context context){

        String url ="https://api.spacexdata.com/v4/crew";
// Request a string response from the provided URL.
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject jsonObject = (JSONObject) response.get(i);

                                        insert(new Crew(jsonObject.getString("name"),
                                                jsonObject.getString("agency"),
                                                jsonObject.getString("image"),
                                                jsonObject.getString("wikipedia"),
                                                jsonObject.getString("status")
                                        ));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

// Add the request to the RequestQueue.
        if(context!=null) {
            APICallSingleton.getInstance(context).getRequestQueue().add(jsonObjectRequest);
        }
    }

    void insert(final Crew crew) {
        CrewDatabase.databaseWriteExecutor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        mCrewDao.insert(crew);
                    }
                });
    }


    void deleteAll(){
        CrewDatabase.databaseWriteExecutor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        mCrewDao.deleteAll();
                    }
                });
    }
}
