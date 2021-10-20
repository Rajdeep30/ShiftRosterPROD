package com.example.shiftroster;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DateSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateSearchFragment extends Fragment {

    Button btn_date_search;
    Spinner spinner_date, spinner_resource;
    int selectedDateIndex=1, selectedNameIndex =1, selectedShiftIndex = 1;
    String selectedName, selectedDate;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DateSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DateSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DateSearchFragment newInstance(String param1, String param2) {
        DateSearchFragment fragment = new DateSearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_date_search, container, false);
        // Inflate the layout for this fragment

        btn_date_search = view.findViewById(R.id.btn_name_search);
        spinner_date = view.findViewById(R.id.spinner_date);
        spinner_resource = view.findViewById(R.id.spinner_shift);

        ArrayAdapter<String> myAdapterDate = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,GlobalVar.datesRow);
        myAdapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_date.setAdapter(myAdapterDate);

        ArrayAdapter<String> myAdapterResource = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,GlobalVar.mainCSV.get(0));
        myAdapterResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_resource.setAdapter(myAdapterResource);


        btn_date_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedName = spinner_resource.getSelectedItem().toString();
                selectedNameIndex = spinner_resource.getSelectedItemPosition();
                selectedDateIndex = spinner_date.getSelectedItemPosition();
                selectedDate = spinner_date.getSelectedItem().toString();
                    if (selectedDateIndex != 0 && selectedNameIndex != 0) {
                        String shiftName;
                        shiftName = GlobalVar.mainCSV.get(selectedDateIndex).get(selectedNameIndex);
                        Intent intent = new Intent(getContext(), resourcesearch_Listview.class);
                        intent.putExtra("name",selectedName);
                        intent.putExtra("date", selectedDate);
                        intent.putExtra("shift", shiftName);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getContext(), "Please Select Date and Resource name", Toast.LENGTH_SHORT).show();
                    }
            }
        });


        return view;
    }
}