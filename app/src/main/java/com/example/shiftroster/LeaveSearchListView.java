package com.example.shiftroster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class LeaveSearchListView extends AppCompatActivity {

    ListView listview;
    TextView toptext1;
    ArrayList<String> test = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_search_list_view);

        listview = findViewById(R.id.listview_leavesearch);
        toptext1 = findViewById(R.id.toptext1);

        ArrayList<String> urls = getIntent().getStringArrayListExtra("leaveDates");

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("name");
        String shiftName = bundle.getString("shiftName");


        toptext1.setText("@"+ message+ " has "+ shiftName+" for this month on below dates: ");

        if (urls.size()!=0) {
            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(LeaveSearchListView.this,
                    android.R.layout.simple_list_item_1, urls);
            myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            listview.setAdapter(myAdapter);
        }else
        {
            test.add(" ");
            test.add(" ");
            test.add("              No "+shiftName+" shift for "+message+" in this month");
            test.add(" ");
            test.add(" ");
            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(LeaveSearchListView.this,
                    android.R.layout.simple_list_item_1, test);
            myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            listview.setAdapter(myAdapter);
        }
    }
}