package com.gianlucamonica.locatorlamapp.fragments.buttons;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gianlucamonica.locatorlamapp.R;
import com.gianlucamonica.locatorlamapp.activities.gps.fragments.MapsActivity;
import com.gianlucamonica.locatorlamapp.activities.locate.LocateActivity;
import com.gianlucamonica.locatorlamapp.activities.scan.ScanActivity;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParams;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.MyApp;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.buildingFloor.BuildingFloor;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ButtonsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ButtonsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ButtonsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String EXTRA_LAT = "lat";
    public static final String EXTRA_LNG = "lng";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button scanButton;
    private Button locateButton;
    private OnFragmentInteractionListener mListener;

    private Algorithm algorithm;
    private Building building;
    private BuildingFloor buildingFloor;
    private int gridSize;
    private Config config;
    private ArrayList<IndoorParams> indoorParams;

    public ButtonsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ButtonsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ButtonsFragment newInstance(String param1, String param2) {
        ButtonsFragment fragment = new ButtonsFragment();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_buttons, container, false);

        scanButton =  v.findViewById(R.id.scanButton);
        locateButton = v.findViewById(R.id.locateButton);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // quando clicco su scan button

                Intent intent = new Intent(getActivity(),ScanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("indoorParams", indoorParams);
                Log.i("buttonsFrag",indoorParams.toString());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        locateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // quando clicco su scan button

                Intent intent = new Intent(getActivity(), LocateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("indoorParams", indoorParams);
                Log.i("buttonsFrag", indoorParams.toString());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void manageLocateButton(boolean isOfflineScan){

    }

    public void manageScanButton(boolean visibility){
        scanButton.setEnabled(visibility);
    }

    public void loadIndoorParams(ArrayList<IndoorParams> indoorParams){
        this.indoorParams = indoorParams;
        for (int i = 0; i < indoorParams.size(); i++){
            switch (indoorParams.get(i).getName()){
                case BUILDING:
                    this.building = (Building) indoorParams.get(i).getParamObject();
                    break;
                case ALGORITHM:
                    this.algorithm = (Algorithm) indoorParams.get(i).getParamObject();
                    break;
                case FLOOR:
                    this.buildingFloor = (BuildingFloor) indoorParams.get(i).getParamObject();
                    break;
                case SIZE:
                    this.gridSize = (int) indoorParams.get(i).getParamObject();
                    break;
                case CONFIG:
                    this.config = (Config) indoorParams.get(i).getParamObject();
                    break;
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
