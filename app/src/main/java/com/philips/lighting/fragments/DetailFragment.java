package com.philips.lighting.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.slider.Slider;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import com.philips.lighting.quickstart.R;

import java.util.Locale;

public class DetailFragment extends Fragment {

    private PHHueSDK phHueSDK;
    private PHBridge bridge;
    private PHLight light;
    private PHLightState lightState;
    public TextView tvLightName;
    public TextView tvBrightness;
    public TextView tvRed;
    public TextView tvGreen;
    public TextView tvBlue;
    public SeekBar skBrightness;
    public SeekBar skRed;
    public SeekBar skGreen;
    public SeekBar skBlue;
    public static final String TAG = "DetailFragment";
    private static final int MAX_HUE=65535;
    private int red;
    private int green;
    private int blue;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String LIGHT_POSITION = "param1";

    private int position;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(int position) {
        DetailFragment fragment = new DetailFragment();

        Bundle args = new Bundle();
        args.putInt(LIGHT_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(LIGHT_POSITION);
        }
        phHueSDK = PHHueSDK.getInstance();
        bridge = phHueSDK.getSelectedBridge();
        light = bridge.getResourceCache().getAllLights().get(position);
        lightState = light.getLastKnownLightState();

        //Get colour information
        float hue = (lightState.getHue() / (float)MAX_HUE);
        float saturation = (lightState.getSaturation()/256f);
        float value = (lightState.getBrightness()/256f);
        String rgbColour = hsvToRgb(hue ,saturation, value);
        String[] rgbColArray = rgbColour.split(",");
        red = Integer.parseInt(rgbColArray[0]);
        green = Integer.parseInt(rgbColArray[1]);
        blue = Integer.parseInt(rgbColArray[2]);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvLightName = view.findViewById(R.id.tvLightName);
        tvBrightness = view.findViewById(R.id.tvBrightness);
        skBrightness = view.findViewById(R.id.skBrightness);
        tvRed = view.findViewById(R.id.tvRed);
        skRed = view.findViewById(R.id.skRed);
        tvGreen = view.findViewById(R.id.tvGreen);
        skGreen = view.findViewById(R.id.skGreen);
        tvBlue = view.findViewById(R.id.tvBlue);
        skBlue = view.findViewById(R.id.skBlue);

        skBrightness.setMin(1);
        skBrightness.setMax(254);
        skBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvBrightness.setText(String.format(Locale.getDefault(),"%d%s",Math.round(((progress-1)/253f)*100), "%"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                lightState.setBrightness(skBrightness.getProgress());
                String validState = lightState.validateState();
                Log.i(TAG, "Brightness Error " + validState);
                bridge.updateLightState(light,lightState);
                refreshLightDetails();
            }
        });

        skRed.setMin(0);
        skRed.setMax(255);
        skRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvRed.setText(String.format(Locale.getDefault(),"%d%s",Math.round((progress/255f)*100),"%"));
                red = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateLightColor("Red");
                refreshLightDetails();
            }
        });

        skGreen.setMin(0);
        skGreen.setMax(255);
        skGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvGreen.setText(String.format(Locale.getDefault(),"%d%s",Math.round((progress/255f)*100),"%"));
                green = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateLightColor("Green");
                refreshLightDetails();
            }
        });

        skBlue.setMin(0);
        skBlue.setMax(255);
        skBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvBlue.setText(String.format(Locale.getDefault(),"%d%s",Math.round((progress/255f)*100),"%"));
                blue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateLightColor("Blue");
                refreshLightDetails();
            }
        });

        updateLightColor("RGB");
        refreshLightDetails();
    }

    private void updateLightColor(String color) {
        float[] xy = PHUtilities.calculateXYFromRGB(red, green, blue, light.getModelNumber());
        lightState.setX(xy[0]);
        lightState.setY(xy[1]);
        String validState = lightState.validateState();
        Log.i(TAG, color + " Error " + validState);
        bridge.updateLightState(light,lightState);
    }

    private void refreshLightDetails() {
        tvLightName.setText(light.getName());
        tvBrightness.setText(String.format(Locale.getDefault(),"%d%s",Math.round(((lightState.getBrightness() -1)/253f)*100 ),"%"));
        skBrightness.setProgress(lightState.getBrightness());

        tvRed.setText(String.format(Locale.getDefault(),"%d%s", Math.round((red/255f)*100),"%"));
        skRed.setProgress(red);

        tvGreen.setText(String.format(Locale.getDefault(),"%d%s",Math.round((green/255f)*100),"%"));
        skGreen.setProgress(green);

        tvBlue.setText(String.format(Locale.getDefault(),"%d%s",Math.round((blue/255f)*100),"%"));
        skBlue.setProgress(blue);
    }

    public static String hsvToRgb(float hue, float saturation, float value) {

        int h = (int)(hue * 6);
        float f = hue * 6 - h;
        float p = value * (1 - saturation); // SATURATION IS LARGER THAN 1
        float q = value * (1 - f * saturation);
        float t = value * (1 - (1 - f) * saturation);

        switch (h) {
            case 0: return rgbToString(value, t, p);
            case 1: return rgbToString(q, value, p);
            case 2: return rgbToString(p, value, t);
            case 3: return rgbToString(p, q, value);
            case 4: return rgbToString(t, p, value);
            case 5: return rgbToString(value, p, q);
            default: throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
        }
    }

    public static String rgbToString(float r, float g, float b) {
        String rs = Integer.toString((int)(r * 256 ));
        String gs = Integer.toString((int)(g * 256));
        String bs = Integer.toString((int)(b * 256));
        return rs + "," + gs + "," + bs;
    }



}