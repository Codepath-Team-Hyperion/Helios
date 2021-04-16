package com.philips.lighting.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IndividualLightList extends RecyclerView.Adapter<IndividualLightList.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvlightName;
        TextView tvLabel;
        Button btnControl;
        Switch lightSwitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
