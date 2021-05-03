package com.philips.lighting.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.philips.lighting.adapters.ScenesAdapter;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHGroup;
import com.philips.lighting.model.PHScene;
import com.philips.lighting.quickstart.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SceneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SceneFragment extends Fragment {

    private PHHueSDK phHueSDK;
    private PHBridge bridge;
    List<PHScene> scenes;
    public static final String TAG = "SceneFragment";


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public SceneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SceneFragment.
     */

    public static SceneFragment newInstance(String param1, String param2) {
        SceneFragment fragment = new SceneFragment();
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
        return inflater.inflate(R.layout.fragment_scene, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        //Lookup the recyclerview in activity layout
        RecyclerView rvScenes = view.findViewById(R.id.rvScenes);

        //Initialize scenes
        phHueSDK = PHHueSDK.getInstance();
        bridge = phHueSDK.getSelectedBridge();
        scenes = bridge.getResourceCache().getAllScenes();
        //Create adapter passing in the sample scene data
        ScenesAdapter adapter = new ScenesAdapter(scenes);
        //Attach the adapter to the recyclerview to populate items
        rvScenes.setAdapter(adapter);
        //Set layout Manager to position the items
        rvScenes.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }
}