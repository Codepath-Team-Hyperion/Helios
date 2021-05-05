package com.philips.lighting.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.philips.lighting.adapters.IndividualLightList;
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

public class IndividualFragment extends Fragment {

    private PHHueSDK phHueSDK;
    private static final int MAX_HUE = 65535;
    public static final String TAG = "IndividualFragment";
    public static final String URL = "https://192.168.1.16/api/JdB10AnH-xIL0vetG7MwgGi7QzbVbydpc9uwoGeZ";
    ArrayList<LightBulb> allLights = new ArrayList<>();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public IndividualFragment() {
        // Required empty public constructor
    }

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

        //Lookup recyclerview in activity layout
        RecyclerView rvIndividual = view.findViewById(R.id.rvIndividual);

        //initialize light list
        allLights = LightBulb.createAllLightsList();
        //Create adapter passing in the lights data
        IndividualLightList adapter = new IndividualLightList(allLights, new ClickListener() {
            @Override
            public void onPositionClicked(int position) { }

            @Override
            public void onLongClicked(int position) { }

           /* public void onClick(){
                RandomFragment randomFragment = new RandomFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frgContainer, randomFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }*/
        });
        //Attach adapter to the rv to populate items
        rvIndividual.setAdapter(adapter);
        //Set layout manager to position the items
        rvIndividual.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

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

    public interface ClickListener {
        void onPositionClicked(int position);
        void onLongClicked(int position);
    }
}