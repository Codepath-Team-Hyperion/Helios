package com.philips.lighting.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.philips.lighting.fragments.MainFragment;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHGroup;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import com.philips.lighting.quickstart.R;

import java.lang.ref.WeakReference;
import java.util.List;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> {

    private static String TAG = "Groups Adapter";
    private PHHueSDK phHueSDK = PHHueSDK.getInstance();
    private PHBridge bridge = phHueSDK.getSelectedBridge();

    public class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        public TextView tvGroupName;
        public SwitchCompat swGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvGroupName = itemView.findViewById(R.id.tvGroupName);
            swGroup = itemView.findViewById(R.id.swGroup);

            swGroup.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            String groupName = groups.get(getAdapterPosition()).getIdentifier();

            if (isChecked) {
                Log.i(TAG, "The switch is " + (isChecked ? "on" : "off"));
                turnOnGroup(groupName);

            } else {
                Log.i(TAG, "The switch is " + (isChecked ? "on" : "off"));
                turnOffGroup(groupName);
            }

        }
    }

    //Member variable to store groups
    private List<PHGroup> groups;
    //Member variable to store lights
    private List<PHLight> allLights;

    //Pass in the groups array into the constructor
    public GroupsAdapter(List<PHGroup> groups, List<PHLight> allLights) {
        this.groups = groups;
        this.allLights = allLights;
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

        swGroup.setChecked(false);

        //Get all lights in this group
        List<String> groupLights = group.getLightIdentifiers();

        //Check if any lights in the group are on
        for (int i = 0; i < groupLights.size(); i++) {
            for (int j = 0; j < allLights.size(); j++) {
                if (groupLights.get(i).equals(allLights.get(j).getIdentifier()) && allLights.get(j).getLastKnownLightState().isOn()) {
                    Log.i(TAG, allLights.get(j).getIdentifier() + " SWITCH WILL TURN ON");
                    swGroup.setChecked(true);
                }

            }
        }

    }

    public void turnOnGroup(String group) {
        PHLightState lightState = new PHLightState();
        lightState.setOn(true);
        bridge.setLightStateForGroup(group, lightState);
        Log.i(TAG, group + " group is On");
    }

    public void turnOffGroup(String group) {
        PHLightState lightState = new PHLightState();
        lightState.setOn(false);
        bridge.setLightStateForGroup(group, lightState);
        Log.i(TAG, group + " group is Off");
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
}
