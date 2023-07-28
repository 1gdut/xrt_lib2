package com.example.intelligentinsertion.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.intelligentinsertion.Manager.LineChartManager;
import com.example.intelligentinsertion.R;
import com.github.mikephil.charting.charts.LineChart;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {
    private LineChart mLineChartRealTime, mLineChartHistory, mLineChartPredicted;

    private LineChartManager mChartManagerRealTime, mChartManagerHistory, mChartManagerPredicted;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        //实时
        mLineChartRealTime = view.findViewById(R.id.line_chart_real_time);
        mChartManagerRealTime = new LineChartManager(mLineChartRealTime, null, Color.BLACK);
        for (int i = 0; i < 25; i++) {
            mChartManagerRealTime.addEntry((int) (Math.random() * 100));
        }

        //历史
        mLineChartHistory = view.findViewById(R.id.line_chart_history);
        mChartManagerHistory = new LineChartManager(mLineChartHistory, null, Color.BLACK);
        for (int i = 0; i < 25; i++) {
            mChartManagerHistory.addEntry((int) (Math.random() * 100));
        }

        //预测
        mLineChartPredicted = view.findViewById(R.id.line_chart_predicted);
        mChartManagerPredicted = new LineChartManager(mLineChartPredicted, null, Color.BLACK);
        for (int i = 0; i < 25; i++) {
            mChartManagerPredicted.addEntry((int) (Math.random() * 100));
        }
        return view;
    }
}