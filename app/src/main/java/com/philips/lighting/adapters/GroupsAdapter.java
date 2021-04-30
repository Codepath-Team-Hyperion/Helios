package com.philips.lighting.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.philips.lighting.model.PHGroup;
import com.philips.lighting.quickstart.R;

import java.util.List;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvGroupName;
        public SwitchCompat swGroup;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvGroupName = itemView.findViewById(R.id.tvGroupName);
            swGroup = itemView.findViewById(R.id.swGroup);
        }
    }

    //Member variable to store groups
    private List<PHGroup> groups;

    //Pass in the groups array into the constructor
    public GroupsAdapter(List<PHGroup> groups){
        this.groups = groups;
    }

    @NonNull
    @Override
    public GroupsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //inflate the custom layout
        View groupView = inflater.inflate(R.layout.item_group, parent, false);

        //return a new holder instance
        ViewHolder viewHolder = new ViewHolder(groupView);
        return viewHolder;
    }

    // Populate data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull GroupsAdapter.ViewHolder holder, int position) {
        //Get the data model based on position
        PHGroup group = groups.get(position);

        //Set item views based on view and data model
        TextView tvGroupName = holder.tvGroupName;
        tvGroupName.setText(group.getName());
        SwitchCompat swGroup = holder.swGroup;
        //TODO: set group switch to check for ON/OFF
        swGroup.setChecked(true);

    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
}
