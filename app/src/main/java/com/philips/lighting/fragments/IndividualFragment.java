package com.philips.lighting.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResource;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import com.philips.lighting.models.LightBulb;
import com.philips.lighting.quickstart.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.Headers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IndividualFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndividualFragment extends Fragment {

    private PHHueSDK phHueSDK;
    private static final int MAX_HUE=65535;
    public static final String TAG = "IndividualFragment";

    public static final String URL = "https://192.168.1.16/api/JdB10AnH-xIL0vetG7MwgGi7QzbVbydpc9uwoGeZ";

    ArrayList<LightBulb> BulbList = new ArrayList<LightBulb>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IndividualFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IndividualFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IndividualFragment newInstance(String param1, String param2) {
        IndividualFragment fragment = new IndividualFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_individual, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        phHueSDK = PHHueSDK.create();
        Button btnGetLights;
        btnGetLights = (Button) view.findViewById(R.id.btnGetLights);
        btnGetLights.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getLights();
            }

        });

    }

    public void getLights() {
        PHBridge bridge = phHueSDK.getSelectedBridge();
        int hue = 0;
        int bri = 0;
        int sat = 0;

        List<PHLight> allLights = bridge.getResourceCache().getAllLights();
        Log.i(TAG,"Getting Lights... " + allLights.toString());

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
        /*public void get_json(){
            JsonHttpResponseHandler listLights = bridge.getResourceCache().getAllLights();
            String json;
            InputStream is = bridge.getResourceCache().getAllLights();
        }
*/

        //JsonHttpResponseHandler.JSON json= bridge.getResourceCache().getAllLights();
        //JSONObject jsonObject = json.jsonObject;
        //adding async to get info about individual lights
        //AsyncHttpClient client = new AsyncHttpClient();

        /*
        client.get(allLights.toString(), new JsonReader() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
        });
*/
        for (PHLight light : allLights) {
            PHLightState lightState = light.getLastKnownLightState();
            sat = lightState.getSaturation();
            bri = lightState.getBrightness();
            hue = lightState.getHue();

            Log.i(TAG,"Getting State... " + light.getName() + " Brightness " + bri + " Hue " + hue + " Saturation " + sat + "\n");

            try {
                LightBulb test = new LightBulb(light);
                BulbList.add(test);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, "Size of Array: " + BulbList.size());


    }


/*
*       Example of turning on light
*        PHLightState lightState = allLights.get(39).getLastKnownLightState();
        lightState.setOn(true);
        bridge.updateLightState(allLights.get(39), lightState);
* */



    // If you want to handle the response from the bridge, create a PHLightListener object.
    PHLightListener listener = new PHLightListener() {

        @Override
        public void onSuccess() {
        }

        @Override
        public void onStateUpdate(Map<String, String> arg0, List<PHHueError> arg1) {
            Log.w(TAG, "Light has updated");
        }

        @Override
        public void onError(int arg0, String arg1) {}

        @Override
        public void onReceivingLightDetails(PHLight arg0) {}

        @Override
        public void onReceivingLights(List<PHBridgeResource> arg0) {}

        @Override
        public void onSearchComplete() {}
    };

    @Override
    public void onDestroy() {
        PHBridge bridge = phHueSDK.getSelectedBridge();
        if (bridge != null) {

            if (phHueSDK.isHeartbeatEnabled(bridge)) {
                phHueSDK.disableHeartbeat(bridge);
            }

            phHueSDK.disconnect(bridge);
            super.onDestroy();
        }
    }

}