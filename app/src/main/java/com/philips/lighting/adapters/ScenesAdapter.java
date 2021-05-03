package com.philips.lighting.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHScene;
import com.philips.lighting.quickstart.R;

import java.util.List;

public class ScenesAdapter extends RecyclerView.Adapter<ScenesAdapter.ViewHolder> {

    private static String TAG = "Scenes Adapter";
    private PHHueSDK phHueSDK = PHHueSDK.getInstance();
    private PHBridge bridge = phHueSDK.getSelectedBridge();

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvScene;
        public Button btnScene;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvScene = itemView.findViewById(R.id.tvScene);
            btnScene = itemView.findViewById(R.id.btnScene);
        }
    }

    //Member variable to store scenes
    private List<PHScene> scenes;

    //Pass in the scenes array into the constructor
    public ScenesAdapter(List<PHScene> scenes){
        this.scenes = scenes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //inflate the custom layout
        View sceneView = inflater.inflate(R.layout.item_scene, parent, false);

        //return a new holder instance
        ViewHolder viewHolder = new ViewHolder(sceneView);
        return viewHolder;
    }

    //Populate data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PHScene scene = scenes.get(position);

        //Set item views based on view and data model
        TextView tvScene = holder.tvScene;
        tvScene.setText(scene.getName());
        Button btnScene = holder.btnScene;
    }

    @Override
    public int getItemCount() {
        return scenes.size();
    }



}
