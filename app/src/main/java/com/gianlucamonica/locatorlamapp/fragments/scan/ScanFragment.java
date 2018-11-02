package com.gianlucamonica.locatorlamapp.fragments.scan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gianlucamonica.locatorlamapp.R;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.indoorParams.IndoorParams;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.scanSummary.ScanSummary;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    DatabaseManager databaseManager;
    Spinner scansSpinner;

    Algorithm algorithm;
    Building building;
    int gridSize;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ScanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScanFragment newInstance(String param1, String param2) {
        ScanFragment fragment = new ScanFragment();
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

        databaseManager = new DatabaseManager();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_scan, container, false);

        // scansSpinner
        scansSpinner = (Spinner) v.findViewById(R.id.scansSpinner);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
           // mListener.onFragmentInteraction(uri);
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

    /***
     * called by main activity to refresh alg and building choosen
     */
    public void updateScansList(ArrayList<IndoorParams> indoorParams){

        for (int i = 0; i < indoorParams.size(); i++){
            switch (indoorParams.get(i).getName()){
                case ALGORITHM:
                    algorithm = (Algorithm) indoorParams.get(i).getParamObject();
                    break;
                case BUILDING:
                    building = (Building) indoorParams.get(i).getParamObject();
                    break;
                case SIZE:
                    gridSize = (int) indoorParams.get(i).getParamObject();
                    break;
            }
        }

        if(algorithm != null && building != null){
            // getting offline and online scans
            try {
                List<ScanSummary> scanSummaries = databaseManager.getAppDatabase().getScanSummaryDAO().getScanSummaryByBuildingAlgorithm(building.getId(),
                        algorithm.getId(), gridSize);
                List<String> scanString = new ArrayList<>();
                boolean isOfflineScan = false;

                for (int i = 0; i < scanSummaries.size(); i++){
                    if(scanSummaries.get(i).getType().equals("offline")){
                        scanString.add(String.valueOf("Offline scan with size " + scanSummaries.get(i).getIdConfig()));
                        isOfflineScan = true;
                    }else if(scanSummaries.get(i).getType().equals("online")){
                        scanString.add(String.valueOf("Online scan with size " + scanSummaries.get(i).getIdConfig()));
                    }
                }

                mListener.onFragmentInteraction(isOfflineScan);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_list_item_1, scanString);
                scansSpinner.setAdapter(arrayAdapter);
            } catch (Exception e) {
                e.printStackTrace();
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
        void onFragmentInteraction(Boolean isOfflineScan);
    }
}
