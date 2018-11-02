package com.gianlucamonica.locatorlamapp.fragments.building;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.gianlucamonica.locatorlamapp.R;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.indoorParams.IndoorParamName;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.MyApp;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.BuildingDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BuildingFragment.BuildingListener} interface
 * to handle interaction events.
 * Use the {@link BuildingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuildingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<Building> buildings;
    private Spinner  s;
    private Button newButton;
    private ImageView imageView;

    private EditText widthEditText;
    private EditText heightEditText;
    private EditText gridSizeEditText;
    private CheckBox scanCheckBox;

    private DatabaseManager databaseManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private BuildingListener mListener;

    public BuildingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuildingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuildingFragment newInstance(String param1, String param2) {
        BuildingFragment fragment = new BuildingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        databaseManager = new DatabaseManager();
        buildings = new ArrayList<>();
        buildings = getBuildingsFromDb();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.building_fragment, container, false);

        scanCheckBox = v.findViewById(R.id.checkboxScan);
        heightEditText = v.findViewById(R.id.heightEditText);
        widthEditText = v.findViewById(R.id.widthEditText);
        gridSizeEditText = v.findViewById(R.id.gridSizeEditText);

        if(getBuildingsFromDb().size() > 0){
            heightEditText.setText(String.valueOf(getBuildingsFromDb().get(0).getHeight()) + " meters");
            widthEditText.setText(String.valueOf(getBuildingsFromDb().get(0).getWidht())+ " meters");
        }


        return v;

    }

    public void manageCheckBox(boolean offlineScan){
        this.scanCheckBox.setChecked(offlineScan);
    }

    public void loadGridSize(int gridSize){
        gridSizeEditText.setText(String.valueOf(gridSize) + " meter");
    }

    public List<Building> getBuildingsFromDb(){
        BuildingDAO buildingDAO = null;
        try{
            buildingDAO = databaseManager.getAppDatabase().getBuildingDAO();
        }catch (Exception e){
            Log.e("error get buildings", String.valueOf(e));
        }

        return buildingDAO.getBuildings();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.onFragmentInteraction(uri,"");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        buildings = getBuildingsFromDb();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BuildingListener) {
            mListener = (BuildingListener) context;
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
    public interface BuildingListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Object object, IndoorParamName tag);

        void manageSpinner(boolean enable);
    }

    public void populateSpinner(View v){
        List<String> buildingsName= new ArrayList<>();
        for (int i=0; i < buildings.size(); i++){
            buildingsName.add(buildings.get(i).getName());
        }


        // se outdoor scan button disabled
        if(!MyApp.getLocationMiddlewareInstance().isINDOOR_LOC()){
            s.setEnabled(false);
        }

        // populate scansSpinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, buildingsName);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        s.setAdapter(arrayAdapter);
    }

    public Building getSelectedBuilding(){
        String building =  s.getSelectedItem().toString();
        for(int i=0; i<buildings.size(); i++){
            if(buildings.get(i).getName().equals(building)){
                return buildings.get(i);
            }
        }
        return null;
    }

    public void manageSpinner(boolean enable){
        s.setEnabled(enable);
    }

}
