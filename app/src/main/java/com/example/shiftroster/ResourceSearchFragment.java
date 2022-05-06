package com.example.shiftroster;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
 * Use the {@link ResourceSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResourceSearchFragment extends Fragment {

    Button btn_resource_search;
    Spinner spinner_shift, spinner_resource;
    int selectedDateIndex=1, selectedNameIndex =1, selectedShiftIndex = 1;
    String selectedName, selectedDate, selectedShift;


    ArrayList<Integer> leaveIndex = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResourceSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResourceSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResourceSearchFragment newInstance(String param1, String param2) {
        ResourceSearchFragment fragment = new ResourceSearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_resource_search, container, false);
        // Inflate the layout for this fragment

        btn_resource_search = view.findViewById(R.id.btn_name_search);
        spinner_resource= view.findViewById(R.id.spinner_shift);
        spinner_shift = view.findViewById(R.id.spinner_name);

        ArrayAdapter<String> myAdapterResource = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,GlobalVar.mainCSV.get(0));
        myAdapterResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_resource.setAdapter(myAdapterResource);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.shifts_for_res));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_shift.setAdapter(myAdapter);

        btn_resource_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveIndex.clear();
                ArrayList<String> leaveDates = new ArrayList<>();
                selectedName = spinner_resource.getSelectedItem().toString();
                selectedNameIndex = spinner_resource.getSelectedItemPosition();
                selectedShiftIndex = spinner_shift.getSelectedItemPosition();
                selectedShift =  GlobalVar.shiftSearch.get(selectedShiftIndex);
                if (selectedNameIndex != 0){
                    for (int i= 0; i<GlobalVar.mainCSV.size(); i++)
                    {
                        if (GlobalVar.shiftSearch.get(selectedShiftIndex).equalsIgnoreCase(GlobalVar.mainCSV.get(i).get(selectedNameIndex)))
                        {
                            leaveIndex.add(i);
                        }
                    }

                    for (Integer i:leaveIndex)
                    {
                        leaveDates.add(GlobalVar.mainCSV.get(i).get(0));
                        //Toast.makeText(MainActivity.this, "@"+selectedName+ " has leaves on: "+ allRows.get(i).get(0), Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(getContext(), LeaveSearchListView.class);
                    intent.putStringArrayListExtra("leaveDates", leaveDates);
                    intent.putExtra("name",selectedName);
                    intent.putExtra("shiftName", selectedShift);
                    startActivity(intent);

                }else{
                    Toast.makeText(getContext(), "Please Select Resource Name.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}