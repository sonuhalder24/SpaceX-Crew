package com.example.spacexcrew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.spacexcrew.Crew;
import com.example.spacexcrew.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.CrewHolder>{
    Context context;
    ArrayList<Crew> list;
    onClick hyperClick;

    public interface onClick{
        void click(int index);
    }
    public CrewAdapter(Context c, ArrayList<Crew> crews, onClick hyperClick){
        this.context=c;
        this.list=crews;
        this.hyperClick=hyperClick;
    }

    @NonNull
    @Override
    public CrewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.single_item,parent,false);
        return new CrewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CrewHolder holder, int position) {
        Glide.with(context).load(list.get(position).image).into(holder.imgHolder);
        holder.name.setText(list.get(position).crew_name);
        holder.agency.setText(list.get(position).agency);
        holder.status.setText(list.get(position).status);
        holder.link.setText(list.get(position).wikipedia);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void submitList(List<Crew> newList){
        list.clear();
        list.addAll(newList);

        notifyDataSetChanged();
    }


    public class CrewHolder extends RecyclerView.ViewHolder{
        ImageView imgHolder;
        TextView name,agency,status,link;
        public CrewHolder(@NonNull View itemView) {
            super(itemView);
            imgHolder=itemView.findViewById(R.id.imgHolder);
            name=itemView.findViewById(R.id.name);
            agency=itemView.findViewById(R.id.agency);
            status=itemView.findViewById(R.id.status);
            link=itemView.findViewById(R.id.link);
            link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   hyperClick.click(getAdapterPosition());
                }
            });
        }
    }
}
