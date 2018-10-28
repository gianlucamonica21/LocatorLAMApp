package com.gianlucamonica.locatorlamapp.fragments.algorithm;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gianlucamonica.locatorlamapp.R;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParamName;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.MyApp;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algorithm.Algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlgorithmFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlgorithmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlgorithmFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<Algorithm> algorithms;
    private DatabaseManager databaseManager;
    private Spinner s;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AlgorithmFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlgorithmFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlgorithmFragment newInstance(String param1, String param2) {
        AlgorithmFragment fragment = new AlgorithmFragment();
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
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_algorithm, container, false);

        databaseManager = new DatabaseManager();

        try{
            algorithms = databaseManager.getAppDatabase().getAlgorithmDAO().getAlgorithms();
        }
        catch (Exception e){
            Log.e("error get alg", String.valueOf(e));
        }

        populateSpinner(v);

        // getting selected item from algorithm spinner
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Algorithm chosenAlgorithm = getSelectedAlgorithm();
                mListener.onFragmentInteraction(chosenAlgorithm, IndoorParamName.ALGORITHM);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.onFragmentInteraction(uri,"");
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

    public void populateSpinner(View v){
        List<String> algorithmsName = new ArrayList<>();
        for (int i=0; i < algorithms.size(); i++){
            algorithmsName.add(algorithms.get(i).getName());
        }
        // getting algorithm spinner
        s = (Spinner) v.findViewById(R.id.spinner);

        // se outdoor scan button disabled
        if(!MyApp.getLocationMiddlewareInstance().isINDOOR_LOC()){
            s.setEnabled(false);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, algorithmsName);
//        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        s.setAdapter(adapter);
    }

    public Algorithm getSelectedAlgorithm(){
        String algorithm =  s.getSelectedItem().toString();
        for(int i=0; i<algorithms.size(); i++){
            if(algorithms.get(i).getName().equals(algorithm)){
                return algorithms.get(i);
            }
        }
        return null;
    }

}
