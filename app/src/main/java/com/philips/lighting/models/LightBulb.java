package com.philips.lighting.models;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

import static com.philips.lighting.fragments.IndividualFragment.URL;

public class LightBulb {
    private static PHHueSDK phHueSDK = PHHueSDK.create();
    private String nameID;
    private String group;
    private boolean stateOn;
    private int brightness;
    private int hue;
    private int saturation;
    public static final String TAG = "Lightbulb_Model";

    static PHBridge bridge = phHueSDK.getSelectedBridge();
    private static List<PHLight> allLights = bridge.getResourceCache().getAllLights();

    public LightBulb(PHLight light) throws JSONException {
            nameID = light.getName();
            stateOn = light.getLastKnownLightState().isOn();
            brightness = light.getLastKnownLightState().getBrightness();
            hue = light.getLastKnownLightState().getHue();
            saturation = light.getLastKnownLightState().getSaturation();
            group = "grpName";


            Log.i(TAG, "Creating Light objects... \n" + nameID + "\nIs light on? : " + stateOn + "\nBrightness: " + brightness + "\nhue: " + hue + "\nsaturation: " + saturation + bridge.getResourceCache().getAllGroups().toString());

    }

    public String getName(){
        return nameID;
    }

    public String getLabel(){
        return group;
    }

    public boolean stateOn(){
        return stateOn;
    }

        /*public static List<LightBulb> fromJsonArray(JSONArray bulbJsonArray) throws JSONException {
        List<LightBulb> bulbs = new ArrayList<>();
        for(int i=0; i < bulbJsonArray.length();i++){
            bulbs.add(new LightBulb(bulbJsonArray.getJSONObject(i)));
        }
        return bulbs;
    }*/

    public static ArrayList<LightBulb> createAllLightsList(){
        ArrayList<LightBulb> allLightsList = new ArrayList<>();

        //String access = "https://"+LAST_CONNECTED_IP;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG,"onSuccess");
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG,"onFailure");
            }
        });

        for (int i=0; i < allLights.size(); i++) {
            try {
                allLightsList.add(new LightBulb(allLights.get(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, "Size of Array: " + allLights.size());

        return allLightsList;
    }
}
