package com.example.spacexcrew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.spacexcrew.Adapter.CrewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CrewViewModel mCrewViewModel;
    Button deleteAll,refresh;
    RecyclerView recyclerView;
    CrewAdapter adapter;
    ArrayList<Crew> list;
    CrewAdapter.onClick clicking;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        deleteAll=findViewById(R.id.delete);
        refresh=findViewById(R.id.refresh);
        recyclerView=findViewById(R.id.recycler);
        progressBar=findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        clicking=new CrewAdapter.onClick() {
            @Override
            public void click(int index) {

                String url=list.get(index).wikipedia;

                CustomTabsIntent.Builder builder=new CustomTabsIntent.Builder();
                int color= Color.parseColor("#C3004D");
                CustomTabColorSchemeParams.Builder colorbuilder1=new CustomTabColorSchemeParams.Builder();
                colorbuilder1.setToolbarColor(color);
                builder.setDefaultColorSchemeParams(colorbuilder1.build());

                CustomTabsIntent intent=builder.build();
                if (isNetworkConnected()){
                    intent.launchUrl(MainActivity.this, Uri.parse(url));
                }
                else {
                    Toast.makeText(MainActivity.this, "Please turn on Internet", Toast.LENGTH_SHORT).show();
                }
            }
        };
        adapter = new CrewAdapter(this,list,clicking);
        recyclerView.setAdapter(adapter);

        mCrewViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(CrewViewModel.class);
        mCrewViewModel.getAllCrews().observe(this,
                new Observer<List<Crew>>() {
                    @Override
                    public void onChanged(List<Crew> crews) {
//                        if(crews.size()>0){
//
//                        }
//                        else{
//                            if(isNetworkConnected()){
//                                mCrewViewModel.fromAPI(MainActivity.this);
//                            }
//                            else{
//                                Toast.makeText(MainActivity.this, "Turn on your internet for load first time", Toast.LENGTH_SHORT).show();
//                            }
//                        }
                        adapter.submitList(crews);
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkConnected()){
                    if(list.size()>0){
                        Toast.makeText(MainActivity.this, "Refreshing", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        mCrewViewModel.fromAPI(MainActivity.this);
                        Toast.makeText(MainActivity.this, "Refreshing", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Please turn on your internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.size()>0){
                    Toast.makeText(MainActivity.this, "Deleting", Toast.LENGTH_SHORT).show();
                    mCrewViewModel.deleteAll();
                }
                else {
                    Toast.makeText(MainActivity.this, "Nothing to delete", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}