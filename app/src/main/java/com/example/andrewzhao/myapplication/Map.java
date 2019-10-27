//package com.example.andrewzhao.myapplication;
//
//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.data.Entry;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//public class Map extends Fragment {
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.article_view, container, false);
//    LineChart chart = (LineChart) findViewById(R.id.chart);
//
//    // The key is the hours of sleep gotten, and the value is the day of the week
//    java.util.Map<Float, Float> timesMap = new HashMap<>();
//        timesMap.put(7.5F, 1.0F);
//        timesMap.put(6.5F, 2.0F);
//        timesMap.put(8.5F, 3F);
//    List<Entry> entries = new ArrayList<>();
//        for (java.util.Map.Entry<Float, Float> singleTime : timesMap.entrySet()) {
//        entries.add(new Entry(singleTime.getKey(), singleTime.getValue()));
//    }
//
//
//}
