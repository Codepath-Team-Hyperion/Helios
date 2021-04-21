package com.philips.lighting.models;

import android.util.Log;

import com.philips.lighting.model.PHLight;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LightBulb {
    String nameID;
    int brightness;
    int hue;
    int saturation;
    public static final String TAG = "Lightbulb.java";

    public LightBulb(PHLight light) throws JSONException {
        nameID = light.getName();
        //
        brightness = light.getLastKnownLightState().getBrightness();
        //Log.i(TAG,"Creating Light objectss... " + nameID + "Brightness " +brightness);
        hue = light.getLastKnownLightState().getHue();
        saturation = light.getLastKnownLightState().getSaturation();
        Log.i(TAG,"Creating Light objects... \n" + nameID + "\nBrightness: " +brightness + "\nhue: " + hue + "\nsaturation: " + saturation);

    }

    /*public static List<LightBulb> fromJsonArray(JSONArray bulbJsonArray) throws JSONException {
        List<LightBulb> bulbs = new ArrayList<>();
        for(int i=0; i < bulbJsonArray.length();i++){
            bulbs.add(new LightBulb(bulbJsonArray.getJSONObject(i)));
        }
        return bulbs;
    }*/
}
