package com.philips.lighting.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.philips.lighting.model.PHLight;
import com.philips.lighting.models.LightBulb;
import com.philips.lighting.quickstart.R;

import org.w3c.dom.Text;

import java.util.List;

public class IndividualLightList extends RecyclerView.Adapter<IndividualLightList.ViewHolder> {

    private List<LightBulb> allLights;

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvlightName;
        TextView tvLabel;
        Button btnControl;
        Switch lightSwitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvlightName = (TextView) itemView.findViewById(R.id.tvlightName);
            tvLabel = (TextView) itemView.findViewById(R.id.tvLabel);
            btnControl = itemView.findViewById(R.id.btnControl);
            lightSwitch = itemView.findViewById(R.id.lightSwitch);

        }
    }

   public IndividualLightList(List<LightBulb> allLights){
        this.allLights = allLights;
   }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View lightView = inflater.inflate(R.layout.lightslist, parent, false);

        ViewHolder viewHolder = new ViewHolder(lightView);
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

        Switch lightSwitch = holder.lightSwitch;
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
