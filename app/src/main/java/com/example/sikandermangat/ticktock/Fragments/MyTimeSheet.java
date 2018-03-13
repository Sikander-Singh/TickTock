package com.example.sikandermangat.ticktock.Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.sikandermangat.ticktock.Activities.EditActivity;
import com.example.sikandermangat.ticktock.Activities.EditTimeCard;
import com.example.sikandermangat.ticktock.Activities.HomeActivity;
import com.example.sikandermangat.ticktock.Activities.MainActivity;
import com.example.sikandermangat.ticktock.Helpers.FirebaseDataBaseReferences;
import com.example.sikandermangat.ticktock.Models.TimCardClass;
import com.example.sikandermangat.ticktock.R;
import com.example.sikandermangat.ticktock.Adapters.TimeSheetAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnDateClickListener;


public class MyTimeSheet extends Fragment {

    private FirebaseDataBaseReferences firebaseObject;
    private DatabaseReference myRef;
    private RecyclerView recyclerView;
    private TimeSheetAdapter adapter;
    private  List<TimCardClass> list = new ArrayList<TimCardClass>();
    private WeekCalendar week;
    private MaterialCalendarView monthCal;
    public static String dateStr;
    private LayoutAnimationController controller;
    public static boolean status;

    public MyTimeSheet() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_fall_down);
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle("Time Sheet");
        firebaseObject = new FirebaseDataBaseReferences();
        myRef = firebaseObject.getMyRef();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_time_sheet, container, false);
        status=true;
        week = view.findViewById(R.id.weekCalendar);
        monthCal=view.findViewById(R.id.monthCalendar);
        monthCal.setVisibility(View.GONE);
        monthCal.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                Date calDate=date.getDate();

                SimpleDateFormat simpleDayFormat=new SimpleDateFormat("EEEE");
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy");
                setDataInFragment(simpleDayFormat.format(calDate), simpleDateFormat.format(calDate));



            }
        });
        week.setOnDateClickListener(new OnDateClickListener() {

            @Override
            public void onDateClick(DateTime dateTime) {

                DateTime.Property day = dateTime.dayOfWeek();

                String dayStr =day.getAsText();

                setDataInFragment(dayStr,dateTime.toString("dd-MMM-yyyy"));
            }
        });


        recyclerView = view.findViewById(R.id.TimeRecycleView);
        adapter = new TimeSheetAdapter(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        mLayoutManager.setItemPrefetchEnabled(false);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd-MMM-yyyy");
        dateStr = simpleFormat.format(cal.getTime());
        getListOfTimeCards(dateStr);
        adapter.notifyDataSetChanged();
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.time, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.navigation_monthCal) {
            week.setVisibility(View.GONE);
            monthCal.setVisibility(View.VISIBLE);
        }
        if (item.getItemId() == R.id.navigation_weekCal) {
            monthCal.setVisibility(View.GONE);
            week.setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setDataInFragment(String dayStr,String dateString){

        TimCardClass timCardClass=new TimCardClass();
        switch (dayStr) {

            case "Monday":

                dateStr = dateString;
                list.clear();
                getListOfTimeCards(dateString);
                recyclerView.setLayoutAnimation(controller);
                recyclerView.scheduleLayoutAnimation();
                adapter.notifyDataSetChanged();

                break;
            case "Tuesday":

                dateStr = dateString;
                list.clear();
                getListOfTimeCards(dateString);
                recyclerView.setLayoutAnimation(controller);
                recyclerView.scheduleLayoutAnimation();
                adapter.notifyDataSetChanged();

                break;
            case "Wednesday":

                dateStr = dateString;
                list.clear();
                getListOfTimeCards(dateString);
                recyclerView.setLayoutAnimation(controller);
                recyclerView.scheduleLayoutAnimation();
                adapter.notifyDataSetChanged();

                break;
            case "Thursday":

                dateStr = dateString;
                list.clear();
                getListOfTimeCards(dateString);
                recyclerView.setLayoutAnimation(controller);
                recyclerView.scheduleLayoutAnimation();
                adapter.notifyDataSetChanged();
                break;
            case "Friday":

                dateStr = dateString;
                list.clear();
                getListOfTimeCards(dateString);
                recyclerView.setLayoutAnimation(controller);
                recyclerView.scheduleLayoutAnimation();
                adapter.notifyDataSetChanged();
                break;
            case "Saturday":

                dateStr = dateString;
                list.clear();
                getListOfTimeCards(dateString);
                recyclerView.setLayoutAnimation(controller);
                recyclerView.scheduleLayoutAnimation();
                adapter.notifyDataSetChanged();
                break;
            case "Sunday":

                dateStr = dateString;
                list.clear();
                getListOfTimeCards(dateString);
                recyclerView.setLayoutAnimation(controller);
                recyclerView.scheduleLayoutAnimation();
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }

    }

    public  void getListOfTimeCards(final String dateString){

        myRef.child("TimeCard").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot TimeDataSnapshot : dataSnapshot.getChildren()) {

                    TimCardClass timCardClass=TimeDataSnapshot.getValue(TimCardClass.class);
                    if(timCardClass.getDay().equals(dateString)){

                        if(timCardClass.getUserId().equals(firebaseObject.getFirebaseUserid())){

                            list.add(timCardClass);
                        }
                        adapter.notifyDataSetChanged();
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}