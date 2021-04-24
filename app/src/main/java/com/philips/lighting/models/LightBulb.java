package com.philips.lighting.models;

import android.util.Log;

import com.philips.lighting.model.PHLight;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LightBulb {
    private String nameID;
    private boolean stateOn;
    private int brightness;
    private int hue;
    private int saturation;
    public static final String TAG = "Lightbulb_Model";

    public LightBulb(PHLight light) throws JSONException {
            nameID = light.getName();
            stateOn = light.getLastKnownLightState().isOn();
            brightness = light.getLastKnownLightState().getBrightness();
            hue = light.getLastKnownLightState().getHue();
            saturation = light.getLastKnownLightState().getSaturation();

            Log.i(TAG, "Creating Light objects... \n" + nameID + "\nIs light on? : " + stateOn + "\nBrightness: " + brightness + "\nhue: " + hue + "\nsaturation: " + saturation);

    }
    /*public static List<LightBulb> fromJsonArray(JSONArray bulbJsonArray) throws JSONException {
        List<LightBulb> bulbs = new ArrayList<>();
        for(int i=0; i < bulbJsonArray.length();i++){
            bulbs.add(new LightBulb(bulbJsonArray.getJSONObject(i)));
        }
        return bulbs;
    }*/
}
