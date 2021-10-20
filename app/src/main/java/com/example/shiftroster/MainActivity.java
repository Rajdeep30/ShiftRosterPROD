package com.example.shiftroster;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    Spinner spinner_name, spinner_shift, spinner_date, spinner_leave;
    ListView shiftsearchlistview;
    Button btn_shift_search, btn_name_search, btn_leave_search;

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    FragmentAdapter fragmentAdapter;

    int day, month, YYYY;
    String csvfileString, leaveVar="Leave";
    int selectedDateIndex=1, selectedNameIndex =1, selectedShiftIndex = 1;
    String selectedName, selectedDate, selectedShift;

    ArrayList<String> dates = new ArrayList<>();
    ArrayList<Integer> leaveIndex = new ArrayList<>();
    ArrayList<String> shift_search = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<ArrayList<String>> allRows = new ArrayList<>();


        shift_search.add("F3");
        shift_search.add("S2");
        shift_search.add("T7");
        shift_search.add("GS");
        shift_search.add("Leave");

        GlobalVar.shiftSearch = shift_search;

//        btn_name_search = findViewById(R.id.btn_name_search);
//        btn_shift_search = findViewById(R.id.btn_shift_search);
//        btn_leave_search = findViewById(R.id.btn_leave_search);
//        spinner_name = findViewById(R.id.spinner_name);
//        spinner_shift = findViewById(R.id.spinner_shift);
//        spinner_date = findViewById(R.id.spinner_date);
//        spinner_leave = findViewById(R.id.spinner_leave);
//        shiftsearchlistview = findViewById(R.id.listview_shiftsearch);

        tabLayout=findViewById(R.id.tabLayout);
        viewPager2=findViewById(R.id.view_pager2);

        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fm, getLifecycle());
        viewPager2.setAdapter(fragmentAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Shift Search"));
        tabLayout.addTab(tabLayout.newTab().setText("Date Search"));
        tabLayout.addTab(tabLayout.newTab().setText("Resource Search"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });





        csvfileString = this.getApplicationInfo().dataDir + File.separatorChar + "October_shift_roster_2021.csv";

        try {
            AssetManager am = getAssets();
            InputStream inputStream = am.open("October_shift_roster_2021.csv");
            File csvfile = createFileFromInputStream(inputStream);
            CSVReader reader = new CSVReader(new FileReader(csvfile));
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                ArrayList<String> singleRow = new ArrayList<>();
                for (int i = 0; i <nextLine.length; i++) {
                    singleRow.add(nextLine[i]);
                }
                allRows.add(singleRow);
            }
            GlobalVar.mainCSV = allRows;

            for(int i = 0; i<allRows.size(); i++)
            {
                dates.add(allRows.get(i).get(0));
            }
            GlobalVar.datesRow = dates;
            Toast.makeText(this, "Welcome..." , Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error in Parsing CSV" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    private File createFileFromInputStream(InputStream inputStream) {
        try{
            File f = new File(""+csvfileString);
            OutputStream outputStream = new FileOutputStream(f);
            byte buffer[] = new byte[1024];
            int length = 0;

            while((length=inputStream.read(buffer)) > 0) {
                outputStream.write(buffer,0,length);
            }

            outputStream.close();
            inputStream.close();

            return f;
        }catch (IOException e) {
            //Logging exception
        }

        return null;
    }


}