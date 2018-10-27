package com.gianlucamonica.locatorlamapp.fragments.param;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.gianlucamonica.locatorlamapp.R;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParamName;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParams;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParamsUtils;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.MyApp;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.Building;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MagnParamFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MagnParamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MagnParamFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AutoCompleteTextView sizeEditText;
    private int sizeValue;

    private Algorithm algorithm;
    private Building building;
    private List<Config> configList;
    int gridSize;
    private Config config;
    private ArrayList<IndoorParams> indoorParams;
    private IndoorParamsUtils indoorParamsUtils;
    private OnFragmentInteractionListener mListener;
    private DatabaseManager databaseManager;

    public MagnParamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MagnParamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MagnParamFragment newInstance(String param1, String param2) {
        MagnParamFragment fragment = new MagnParamFragment();
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
        indoorParamsUtils = new IndoorParamsUtils();


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_param, container, false);
        sizeEditText = v.findViewById(R.id.sizeEditText);

        Log.i("param fragment", "indoor"+MyApp.getLocationMiddlewareInstance().isINDOOR_LOC());
        // se outdoor scan button disabled
        if(!MyApp.getLocationMiddlewareInstance().isINDOOR_LOC()){
            sizeEditText.setEnabled(false);
        }

        sizeEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sizeEditText.showDropDown();
                return false;
            }
        });


        sizeEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // quando l'utente clicca una config preesistente
                sizeValue = Integer.parseInt(sizeEditText.getText().toString());

                //controllo se la size scelta è già presente nella tabella config !deve essere già presente se è qui!
                for (int i = 0; i < configList.size(); i++){
                    if(configList.get(i).getParName().equals("gridSize")){
                        if(configList.get(i).getParValue() == sizeValue){
                            config = configList.get(i); // setto config
                            break;
                        }
                    }
                }

                Log.i("passo a main",config.toString());
                mListener.onFragmentInteraction(config, IndoorParamName.CONFIG);
            }
        });

        sizeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) { // quando l'utente clicca una nuova configurazione viene aggiunta in db

                if( sizeEditText.getText().toString().equals("")){
                    sizeValue = -1;
                }else{
                    sizeValue = Integer.parseInt( sizeEditText.getText().toString() ); // getting value
                }
                Log.i("after text changed", String.valueOf(sizeValue));

                boolean newConfig = true;

                //controllo se la size scelta è già presente nella tabella config
                for (int i = 0; i < configList.size(); i++){
                    if(configList.get(i).getParName().equals("gridSize")){
                        if(configList.get(i).getParValue() == sizeValue){
                            config = configList.get(i); // setto config
                            newConfig = false;
                            break;
                        }
                    }
                }

                Log.i("new conf", String.valueOf(newConfig));

                if(newConfig){
                    Log.i("insert new config","insert");
                    try {
                        // controllo se esiste già una config del genere
                        List<Config> existingConfigs = databaseManager.getAppDatabase().getConfigDAO().
                                getConfigByIdAlgorithm(algorithm.getId(),"gridSize",sizeValue);

                        if(existingConfigs.size() == 0){
                            // inserisco nuova config
                            databaseManager.getAppDatabase().getConfigDAO().insert(
                                    new Config(algorithm.getId(),"gridSize",sizeValue)
                            );

                            // pesco nuova config
                            List<Config> configs = databaseManager.getAppDatabase().getConfigDAO().getConfigByIdAlgorithm(
                                    algorithm.getId(),"gridSize",sizeValue);
                            Log.i("pesco nuova config","nuova config "+ configs.toString());

                            if(configs.size() == 1){
                                config = configs.get(0);
                            }
                        }else{
                            Log.i("exisConfig","size " + existingConfigs.size());
                            Log.i("exisConfig","config " + existingConfigs.toString());
                            // estraggo config già esistente
                            config = existingConfigs.get(0);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                Log.i("passo a main",config.toString());
                // devo passare la config corrispondente alla size scelta, altrimenti creare una nuova config
                mListener.onFragmentInteraction(config, IndoorParamName.CONFIG);

            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.onFragmentInteraction(uri);
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
        void onFragmentInteraction(Object object, IndoorParamName tag);
    }

    public void loadIndoorParams(ArrayList<IndoorParams> indoorParams){
        Log.i("sono qui","ssssss");
        this.indoorParams = indoorParams;
        for (int i = 0; i < indoorParams.size(); i++){
            switch (indoorParams.get(i).getName()){
                case BUILDING:
                    this.building = (Building) indoorParams.get(i).getParamObject();
                    break;
                case ALGORITHM:
                    this.algorithm = (Algorithm) indoorParams.get(i).getParamObject();
                    break;
                case SIZE:
                    this.gridSize = (int) indoorParams.get(i).getParamObject();
                    break;
            }
        }
        getSizeFromDB();
    }

    /* cerco config per l'algoritmo e il building scelto*/
    public void getSizeFromDB(){
        Algorithm algorithm = (Algorithm) indoorParamsUtils.getParamObject(indoorParams,IndoorParamName.ALGORITHM);
        Building building = (Building) indoorParamsUtils.getParamObject(indoorParams,IndoorParamName.BUILDING);

        try {
            configList = databaseManager.getAppDatabase().getMyDAO().findConfigByBuildingAndAlgorithm(building.getId(),algorithm.getId());
            //configList = databaseManager.getAppDatabase().getConfigDAO().getConfigByIdAlgorithm(algorithm.getId(),"gridSize");
            Log.i("config trovate",configList.toString());

            List<String> size = new ArrayList<>();
            for(int i = 0; i < configList.size(); i++){
                if(configList.get(i).getParName().equals("gridSize"))
                    size.add(String.valueOf(configList.get(i).getParValue()));
            }
            // popolo lo spinner con le config trovate
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_dropdown_item_1line, size);
            sizeEditText.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
