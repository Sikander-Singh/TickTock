package com.example.sikandermangat.ticktock.Fragments;

import android.app.ProgressDialog;

import android.graphics.Color;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.sikandermangat.ticktock.Activities.HomeActivity;
import com.example.sikandermangat.ticktock.Activities.MainActivity;
import com.example.sikandermangat.ticktock.Helpers.FirebaseDataBaseReferences;
import com.example.sikandermangat.ticktock.Models.JobClass;
import com.example.sikandermangat.ticktock.Models.TimCardClass;
import com.example.sikandermangat.ticktock.R;
import com.example.sikandermangat.ticktock.Adapters.TimeSheetAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MyOverview extends Fragment {

    private FirebaseDataBaseReferences firebaseObject;
    private DatabaseReference myRef;
    private ArrayList<TimCardClass> jobList = new ArrayList<TimCardClass>();
    private ArrayList timeList = new ArrayList ();
    private float sum=0;
    private  ArrayList percentList=new ArrayList();
    private ArrayList jobNameList = new ArrayList ();
    private  PieChart pieChart;
    private ProgressDialog progress;
    private RecyclerView recyclerView;
    private TimeSheetAdapter adapter;
    private TextView totalTime;
    private Button lastMonth;
    private Button thisMonth;
    private Button lastWeek;
    private Button today;
    private Button thisWeek;
    private  SimpleDateFormat simpleDateFormat;
    private Calendar cal;
    private Date startDateString;
    private Date endDateString;
    private Date date;
    private float viewtotalTime;
    private   DecimalFormat df;
    public MyOverview() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {


        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Overview");
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_my_overview, container, false);
        firebaseObject=new FirebaseDataBaseReferences();
        myRef=firebaseObject.getMyRef();
        MyTimeSheet.status=false;
        df= new DecimalFormat("###.#");
        adapter = new TimeSheetAdapter(jobList);
        totalTime=view.findViewById(R.id.viewtotalTime);
        lastMonth=view.findViewById(R.id.lastMonth);
        thisMonth=view.findViewById(R.id.thisMonth);
        lastWeek=view.findViewById(R.id.lastWeek);
        thisWeek=view.findViewById(R.id.thisWeek);
        today=view.findViewById(R.id.today);
        recyclerView=view.findViewById(R.id.overRecycleView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        mLayoutManager.setItemPrefetchEnabled(false);
        progress=new ProgressDialog(getContext());
        progress.setMessage("Loading....");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
        progress.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        pieChart = view.findViewById(R.id.piechart);
        cal=Calendar.getInstance();
        startDateString=cal.getTime();
        simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy");
        String dateString=simpleDateFormat.format(startDateString);
        getTimeCards(dateString,dateString);
        today.setBackgroundResource(R.drawable.change_custom_border);

        //Last month pie chart
        lastMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cal=Calendar.getInstance();
                cal.add(Calendar.MONTH,-1);
                cal.set(Calendar.DAY_OF_MONTH,1);
                startDateString=cal.getTime();
                cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                endDateString=cal.getTime();
                percentList.clear();
                jobList.clear();
                recyclerView.getAdapter().notifyDataSetChanged();
                totalTime.setText("0.0s");
                if(!pieChart.isEmpty()){

                    pieChart.clearValues();
                    pieChart.clear();
                    pieChart.animateY(1000);
                    pieChart.invalidate();
                }

                System.out.println("Start dateeeeeeeeeeeeeeeeee"+startDateString);
                System.out.println("End dateeeeeeeeeeeeeeeeee"+endDateString);
                getTimeCards(simpleDateFormat.format(startDateString),simpleDateFormat.format(endDateString));
                lastMonth.setBackgroundResource(R.drawable.change_custom_border_left);
                thisMonth.setBackgroundResource(R.drawable.custom_border);
                lastWeek.setBackgroundResource(R.drawable.custom_border);
                today.setBackgroundResource(R.drawable.custom_border_left);
                thisWeek.setBackgroundResource(R.drawable.custom_border);
            }
        });

        //This month pie chart
        thisMonth.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                cal=Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH,1);
                startDateString=cal.getTime();
                cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                endDateString=cal.getTime();
                percentList.clear();
                jobList.clear();
                recyclerView.getAdapter().notifyDataSetChanged();
                totalTime.setText("0.0s");
                if(!pieChart.isEmpty()){

                    pieChart.clearValues();
                    pieChart.clear();
                    pieChart.animateY(1000);
                    pieChart.invalidate();
                }

                System.out.println("Start dateeeeeeeeeeeeeeeeee"+startDateString);
                System.out.println("End dateeeeeeeeeeeeeeeeee"+endDateString);
                getTimeCards(simpleDateFormat.format(startDateString),simpleDateFormat.format(endDateString));
                lastMonth.setBackgroundResource(R.drawable.custom_border_left);
                thisMonth.setBackgroundResource(R.drawable.change_custom_border);
                lastWeek.setBackgroundResource(R.drawable.custom_border);
                today.setBackgroundResource(R.drawable.custom_border_left);
                thisWeek.setBackgroundResource(R.drawable.custom_border);
            }
        });

        //Last week pie chart
        lastWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                percentList.clear();
                jobList.clear();
                recyclerView.getAdapter().notifyDataSetChanged();
                totalTime.setText("0.0s");
                if(!pieChart.isEmpty()){

                    pieChart.clearValues();
                    pieChart.clear();
                    pieChart.animateY(1000);
                    pieChart.invalidate();
                }
                date=new Date();
                cal = Calendar.getInstance();
                cal.setTime(date);
                int i = cal.get(Calendar.DAY_OF_WEEK) - cal.getFirstDayOfWeek();
                cal.add(Calendar.DATE, -i - 7);
                startDateString = cal.getTime();
                cal.add(Calendar.DATE, 6);
                endDateString = cal.getTime();


                System.out.println("Start dateeeeeeeeeeeeeeeeee"+startDateString);
                System.out.println("End dateeeeeeeeeeeeeeeeee"+endDateString);

                getTimeCards(simpleDateFormat.format(startDateString),simpleDateFormat.format(endDateString));
                lastMonth.setBackgroundResource(R.drawable.custom_border_left);
                lastWeek.setBackgroundResource(R.drawable.change_custom_border);
                thisMonth.setBackgroundResource(R.drawable.custom_border);
                today.setBackgroundResource(R.drawable.custom_border_left);
                thisWeek.setBackgroundResource(R.drawable.custom_border);
            }
        });

        thisWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                percentList.clear();
                jobList.clear();
                recyclerView.getAdapter().notifyDataSetChanged();
                totalTime.setText("0.0s");
                DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
                Calendar calendar = Calendar.getInstance();
                //calendar.setFirstDayOfWeek(Calendar.MONDAY);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

                String[] days = new String[7];
                for (int i = 0; i < 7; i++)
                {
                    days[i] = format.format(calendar.getTime());
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }

                if(!pieChart.isEmpty()){

                    pieChart.clearValues();
                    pieChart.clear();
                    pieChart.animateY(1000);
                    pieChart.invalidate();
                }
                getTimeCards(days[0],days[6]);
                thisWeek.setBackgroundResource(R.drawable.change_custom_border);
                lastMonth.setBackgroundResource(R.drawable.custom_border_left);
                today.setBackgroundResource(R.drawable.custom_border_left);
                thisMonth.setBackgroundResource(R.drawable.custom_border);
                lastWeek.setBackgroundResource(R.drawable.custom_border);

            }
        });
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                percentList.clear();
                jobList.clear();
                recyclerView.getAdapter().notifyDataSetChanged();
                totalTime.setText("0 s");
                if(!pieChart.isEmpty()){

                    pieChart.clearValues();
                    pieChart.clear();
                    pieChart.animateY(1000);
                    pieChart.invalidate();
                }
                cal=Calendar.getInstance();
                cal.setTime(new Date());
                startDateString=cal.getTime();
                String dateString=simpleDateFormat.format(startDateString);

                System.out.println("Start dateeeeeeeeeeeeeeeeee"+startDateString);
                getTimeCards(dateString,dateString);
                lastMonth.setBackgroundResource(R.drawable.custom_border_left);
                today.setBackgroundResource(R.drawable.change_custom_border_left);
                thisMonth.setBackgroundResource(R.drawable.custom_border);
                lastWeek.setBackgroundResource(R.drawable.custom_border);
                thisWeek.setBackgroundResource(R.drawable.custom_border);
            }
        });

      return view;
    }


    //Get Time cards of users
    private void getTimeCards(final String startDate, final String endDate){

        myRef.child("TimeCard").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Date start=new Date();
                Date end=new Date();
                try {
                    start=simpleDateFormat.parse(startDate);
                    end=simpleDateFormat.parse(endDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                sum = 0;
                timeList.clear();
                jobList.clear();
                percentList.clear();
                jobNameList.clear();
                for (DataSnapshot jobDataSnapshot : dataSnapshot.getChildren()) {

                    TimCardClass obj = jobDataSnapshot.getValue(TimCardClass.class);
                    Date objDate=new Date();
                    try {
                        simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy");
                        objDate=simpleDateFormat.parse(obj.getDay());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    if((objDate.compareTo(start)>0 && objDate.compareTo(end)<0)|| objDate.compareTo(start)==0 ){

                        if (obj.getUserId().equals(firebaseObject.getFirebaseUserid())) {
                            sum = sum + obj.getTotalTime();
                            timeList.add(obj.getTotalTime());
                            jobList.add(obj);
                        }
                    }



                }

                for (int i = 0; i < timeList.size(); i++) {

                    percentList.add((Float.parseFloat(timeList.get(i).toString()) / sum) * 100);

                }

                getJobList();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //Get Time cards of users




    //Get Job List data for pie chart

    private void getJobList() {

        viewtotalTime=0;
        myRef.child("Jobs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!jobList.isEmpty()) {
                    for (DataSnapshot jobDataSnapshot : dataSnapshot.getChildren()) {


                        for (int i = 0; i <jobList.size(); i++) {

                            JobClass jobObj = jobDataSnapshot.getValue(JobClass.class);

                            if (jobObj.getJobId().equals(jobList.get(i).getJobId())) {

                                jobNameList.add(jobObj.getJobName());
                                viewtotalTime= viewtotalTime+jobList.get(i).getTotalTime();
                            }
                        }


                    }

                    pieChart.setUsePercentValues(true);
                    pieChart.setDrawHoleEnabled(true);
                    pieChart.setTransparentCircleRadius(30f);
                    pieChart.setHoleRadius(30f);
                    pieChart.getDescription().setText("Overview");
                    List<PieEntry> pieEntries = new ArrayList<PieEntry>();
                    for (int i = 0; i < percentList.size(); i++){

                        pieEntries.add(new PieEntry(Float.parseFloat(percentList.get(i).toString()), jobNameList.get(i).toString()));
                    }

                    PieDataSet dataSet = new PieDataSet(pieEntries, "Jobs");
                    dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    PieData pieData = new PieData(dataSet);
                    pieChart.setData(pieData);
                    pieData.setValueTextSize(13f);
                    pieData.setValueTextColor(Color.BLACK);
                    pieData.setValueFormatter(new PercentFormatter());
                    pieChart.invalidate();
                    if(viewtotalTime<1){

                        double value= Double.parseDouble(df.format(viewtotalTime*60));
                        totalTime.setText(value+"s");
                    }
                    else{

                        double value= Double.parseDouble(df.format(viewtotalTime));
                        totalTime.setText(value+"m");
                    }
                    adapter.notifyDataSetChanged();
                    progress.dismiss();
                    //getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    progress.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
                else {
                    //getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                    progress.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    progress.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    //Get Job List data for pie chart

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.finder, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.navigation_finder) {



        }
        return super.onOptionsItemSelected(item);
    }
}

