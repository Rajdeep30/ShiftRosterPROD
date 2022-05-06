package com.example.shiftroster;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShiftSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShiftSearchFragment extends Fragment {

    Button btn_shift_search;
    Spinner spinner_date, spinner_shift;
    String G1 = "G1";
    String G3 = "G3";
    int selectedDateIndex=1, selectedNameIndex =1, selectedShiftIndex = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShiftSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShiftSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShiftSearchFragment newInstance(String param1, String param2) {
        ShiftSearchFragment fragment = new ShiftSearchFragment();
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

        View view = inflater.inflate(R.layout.fragment_shift_search, container, false);
        // Inflate the layout for this fragment
        btn_shift_search = view.findViewById(R.id.btn_shift_search);
        spinner_date = view.findViewById(R.id.spinner_date);
        spinner_shift = view.findViewById(R.id.spinner_name);

        ArrayAdapter<String> myAdapterDate = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,GlobalVar.datesRow);
        myAdapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_date.setAdapter(myAdapterDate);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.shifts));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_shift.setAdapter(myAdapter);

        btn_shift_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Integer> personIndex = new ArrayList<>();
                ArrayList<String> personName = new ArrayList<>();
                ArrayList<String> shiftName = new ArrayList<>();
                ArrayList<String> finalString = new ArrayList<>();
                selectedShiftIndex = spinner_shift.getSelectedItemPosition();
                selectedDateIndex = spinner_date.getSelectedItemPosition();
                String selectedDate = spinner_date.getSelectedItem().toString();

                if(selectedDateIndex != 0){
                for(int i=0; i<GlobalVar.mainCSV.get(selectedDateIndex).size();i++)
                {
                    if (GlobalVar.shiftSearch.get(selectedShiftIndex).equalsIgnoreCase("F3")){
                        if(GlobalVar.shiftSearch.get(selectedShiftIndex).equalsIgnoreCase(GlobalVar.mainCSV.get(selectedDateIndex).get(i)) || G1.equalsIgnoreCase(GlobalVar.mainCSV.get(selectedDateIndex).get(i)))
                        {
                            personIndex.add(i);
                        }
                    }else if (GlobalVar.shiftSearch.get(selectedShiftIndex).equalsIgnoreCase("S2")){
                        if(GlobalVar.shiftSearch.get(selectedShiftIndex).equalsIgnoreCase(GlobalVar.mainCSV.get(selectedDateIndex).get(i)) || G3.equalsIgnoreCase(GlobalVar.mainCSV.get(selectedDateIndex).get(i)))
                        {
                            personIndex.add(i);
                        }
                    }else {
                        if(GlobalVar.shiftSearch.get(selectedShiftIndex).equalsIgnoreCase(GlobalVar.mainCSV.get(selectedDateIndex).get(i)))
                        {
                            personIndex.add(i);
                        }
                    }
                }

                for(Integer i: personIndex){

                    personName.add(GlobalVar.mainCSV.get(0).get(i));
                    shiftName.add(GlobalVar.mainCSV.get(selectedDateIndex).get(i));
                    //Toast.makeText(MainActivity.this, "Persons having "+ shift_search.get(selectedShiftIndex) + " are: "+allRows.get(0).get(i), Toast.LENGTH_SHORT).show();
                }

                for (int i=0; i<personName.size(); i++){
                    String temp = personName.get(i) +" -- "+ shiftName.get(i);
                    finalString.add(temp);
                }

                    Intent intent = new Intent(getContext(), ShiftSearchListView.class);
                    intent.putStringArrayListExtra("personName", finalString);
                    intent.putExtra("shiftName", GlobalVar.shiftSearch.get(selectedShiftIndex));
                    intent.putExtra("date", selectedDate);// getText() SHOULD NOT be static!!!
                    startActivity(intent);

                   //startActivity(new Intent(MainActivity.this,ShiftSearchListView.class));


                    //Toast.makeText(MainActivity.this, ": selected shift"+ shift_search.get(selectedShiftIndex)+"z,  searched in " +  allRows.get(selectedDateIndex), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Please Select a Date", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}