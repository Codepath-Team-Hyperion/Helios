package com.philips.lighting.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.philips.lighting.fragments.IndividualFragment;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import com.philips.lighting.models.LightBulb;
import com.philips.lighting.quickstart.R;

import java.lang.ref.WeakReference;
import java.util.List;

public class IndividualLightList extends RecyclerView.Adapter<IndividualLightList.ViewHolder> {

    private final List<LightBulb> allLights;
    private final IndividualFragment.ClickListener listener;
    private static String TAG = "adapter";


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, CompoundButton.OnCheckedChangeListener {

        TextView tvlightName;
        TextView tvLabel;
        Button btnControl;
        SwitchCompat lightSwitch;
        private WeakReference<IndividualFragment.ClickListener> listenerRef;
        private PHHueSDK phHueSDK = PHHueSDK.getInstance();
        PHBridge bridge = phHueSDK.getSelectedBridge();



        public ViewHolder(final View itemView, IndividualFragment.ClickListener listener) {
            super(itemView);

            listenerRef = new WeakReference<>(listener);

            tvlightName = (TextView) itemView.findViewById(R.id.tvlightName);
            tvLabel = (TextView) itemView.findViewById(R.id.tvLabel);
            btnControl = itemView.findViewById(R.id.btnControl);
            lightSwitch = itemView.findViewById(R.id.lightSwitch);

            lightSwitch.setOnClickListener(this);
            lightSwitch.setOnCheckedChangeListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == lightSwitch.getId()){
                Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(v.getContext(), "ROW PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

        public void turnOn(PHLight light) {
            PHLightState lightState = new PHLightState();
            lightState.setOn(true);
            bridge.updateLightState(light,lightState);
        }

        public void turnOff(PHLight light) {
            PHLightState lightState = new PHLightState();
            lightState.setOn(false);
            bridge.updateLightState(light,lightState);
        }


        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Log.i(TAG,"The switch is " + (isChecked ? "on" : "off"));
            PHBridge bridge = phHueSDK.getSelectedBridge();
            List<PHLight> allLights = bridge.getResourceCache().getAllLights();

            if(isChecked){
                turnOn(allLights.get(getAdapterPosition()));
            } else {
                turnOff(allLights.get(getAdapterPosition()));
            }
        }
    }

   public IndividualLightList(List<LightBulb> allLights, IndividualFragment.ClickListener listener){
        this.listener = listener;
        this.allLights = allLights;
   }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View lightView = inflater.inflate(R.layout.lightslist, parent, false);

        ViewHolder viewHolder = new ViewHolder(lightView, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IndividualLightList.ViewHolder holder, int position) {
        LightBulb lightBulb = allLights.get(position);

        TextView tvlightName = holder.tvlightName;
        tvlightName.setText(lightBulb.getName());

        TextView tvlabel = holder.tvLabel;
        tvlabel.setText(lightBulb.getLabel());

        Button buttonControl = holder.btnControl;
        buttonControl.setEnabled(lightBulb.stateOn());

        SwitchCompat lightSwitch = holder.lightSwitch;
        if (lightBulb.stateOn())
        {
            lightSwitch.setChecked(lightBulb.stateOn());
        }else{
        lightSwitch.setChecked(false);}
    }

    @Override
    public int getItemCount() {
        return allLights.size();
    }



}
