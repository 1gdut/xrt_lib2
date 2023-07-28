package com.example.intelligentinsertion.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.intelligentinsertion.Activity.SetTimeActivity;
import com.example.intelligentinsertion.MyApplication;
import com.example.intelligentinsertion.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {
    private BarChart mBarChartTop, mBarChartLeft, mBarChartRight;
    private List<BarEntry> mBarEntriesTop, mBarEntriesLeft, mBarEntriesRight;
    private BarData mBarDataTop, mBarDataLeft, mBarDataRight;
    private BarDataSet mBarDataSetTop, mBarDataSetLeft, mBarDataSetRight;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView set_time_top, set_time_left, set_time_right;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
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
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        //top
        mBarChartTop = view.findViewById(R.id.bar_chart_top);
        mBarChartTop.setNoDataText("正在初始化...");
        initBarEntriesTop();
        initBarDataTop();
        set_time_top = view.findViewById(R.id.set_time_top);
        set_time_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyApplication.getContext(), SetTimeActivity.class);
                getContext().startActivity(intent);
            }
        });

        //left
        mBarChartLeft = view.findViewById(R.id.bar_chart_left);
        mBarChartLeft.setNoDataText("正在初始化...");
        set_time_left = view.findViewById(R.id.set_time_left);
        set_time_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyApplication.getContext(), SetTimeActivity.class);
                getContext().startActivity(intent);
            }
        });
        initBarEntriesLeft();
        initBarDataLeft();


        //right
        mBarChartRight = view.findViewById(R.id.bar_chart_right);
        mBarChartRight.setNoDataText("正在初始化...");
        set_time_right = view.findViewById(R.id.set_time_right);
        set_time_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyApplication.getContext(), SetTimeActivity.class);
                getContext().startActivity(intent);
            }
        });
        initBarEntriesRight();
        initBarDataRight();

        return view;
    }

    private void initBarEntriesTop() {
        //创建一个BarEntry对象，表示一个柱状图的数据点，其中x轴的值为0f，y轴的值为30f。
        BarEntry barEntry1 = new BarEntry(0f, 0f);
        BarEntry barEntry2 = new BarEntry(1f, 40f);
        BarEntry barEntry3 = new BarEntry(2f, 50f);

        //创建一个ArrayList对象，用于存储BarEntry对象。
        mBarEntriesTop = new ArrayList<>();
        mBarEntriesTop.add(barEntry1);
        mBarEntriesTop.add(barEntry2);
        mBarEntriesTop.add(barEntry3);
    }

    private void initBarDataTop() {
        //创建一个BarDataSet对象，传入的参数为数据集和数据集的标签。这里将数据集设置为mBarEntries列表，标签设置为"sleep"。
        mBarDataSetTop = new BarDataSet(mBarEntriesTop, "");
        //创建一个BarData对象，传入的参数为数据集。
        mBarDataTop = new BarData(mBarDataSetTop);

        // mBarDataSet.setColors(colors); 给柱状图设置颜色
        //调用initXY  方法，用于初始化X轴的一些属性
        initX(mBarChartTop);
        initY(mBarChartTop);
        //创建一个Description对象，用于设置BarChart的描述信息。
        Description desc = new Description();
        desc.setText("");
        //将描述信息设置给BarChart。
        mBarChartTop.setDescription(desc);
        //将更新后的BarData对象设置给BarChart。
        mBarChartTop.setData(mBarDataTop);
        //柱宽
        mBarDataTop.setBarWidth(0.4f);
        //刷新BarChart的显示
        mBarChartTop.invalidate();
    }

    private void initBarEntriesLeft() {
        //创建一个BarEntry对象，表示一个柱状图的数据点，其中x轴的值为0f，y轴的值为30f。
        BarEntry barEntry1 = new BarEntry(0f, 0f);
        BarEntry barEntry2 = new BarEntry(1f, 40f);
        BarEntry barEntry3 = new BarEntry(2f, 50f);

        //创建一个ArrayList对象，用于存储BarEntry对象。
        mBarEntriesLeft = new ArrayList<>();
        mBarEntriesLeft.add(barEntry1);
        mBarEntriesLeft.add(barEntry2);
        mBarEntriesLeft.add(barEntry3);
    }

    private void initBarDataLeft() {
        //创建一个BarDataSet对象，传入的参数为数据集和数据集的标签。这里将数据集设置为mBarEntries列表，标签设置为"sleep"。
        mBarDataSetLeft = new BarDataSet(mBarEntriesLeft, "");
        //创建一个BarData对象，传入的参数为数据集。
        mBarDataLeft = new BarData(mBarDataSetLeft);

        // mBarDataSet.setColors(colors); 给柱状图设置颜色
        //调用initXY  方法，用于初始化X轴的一些属性
        initX(mBarChartLeft);
        initY(mBarChartLeft);
        //创建一个Description对象，用于设置BarChart的描述信息。
        Description desc = new Description();
        desc.setText("");
        //将描述信息设置给BarChart。
        mBarChartLeft.setDescription(desc);
        //将更新后的BarData对象设置给BarChart。
        mBarChartLeft.setData(mBarDataTop);
        //柱宽
        mBarDataLeft.setBarWidth(0.4f);
        //刷新BarChart的显示
        mBarChartLeft.invalidate();
    }

    private void initBarEntriesRight() {
        //创建一个BarEntry对象，表示一个柱状图的数据点，其中x轴的值为0f，y轴的值为30f。
        BarEntry barEntry1 = new BarEntry(0f, 0f);
        BarEntry barEntry2 = new BarEntry(1f, 40f);
        BarEntry barEntry3 = new BarEntry(2f, 50f);

        //创建一个ArrayList对象，用于存储BarEntry对象。
        mBarEntriesRight = new ArrayList<>();
        mBarEntriesRight.add(barEntry1);
        mBarEntriesRight.add(barEntry2);
        mBarEntriesRight.add(barEntry3);
    }

    private void initBarDataRight() {
        //创建一个BarDataSet对象，传入的参数为数据集和数据集的标签。这里将数据集设置为mBarEntries列表，标签设置为"sleep"。
        mBarDataSetRight = new BarDataSet(mBarEntriesRight, "");
        //创建一个BarData对象，传入的参数为数据集。
        mBarDataRight = new BarData(mBarDataSetRight);

        // mBarDataSet.setColors(colors); 给柱状图设置颜色
        //调用initXY  方法，用于初始化X轴的一些属性
        initX(mBarChartRight);
        initY(mBarChartRight);
        //创建一个Description对象，用于设置BarChart的描述信息。
        Description desc = new Description();
        desc.setText("");
        //将描述信息设置给BarChart。
        mBarChartRight.setDescription(desc);
        //将更新后的BarData对象设置给BarChart。
        mBarChartRight.setData(mBarDataRight);
        //柱宽
        mBarDataRight.setBarWidth(0.4f);
        //刷新BarChart的显示
        mBarChartRight.invalidate();
    }

    private void initX(BarChart barChart) {
        final String[] strings = {"电压", "电流", "功率"};
        //获取BarChart对象的X轴对象。
        XAxis xAxis = barChart.getXAxis();
        //设置X轴的显示位置为底部。
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //创建一个IAxisValueFormatter对象，并将其设置为X轴的值格式化器。
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return strings[(int) value];
            }
        });
        //禁用图例，即不显示图例。
        barChart.getLegend().setEnabled(false);
        //设置X轴的标签数量为星期名称的数量，并禁用强制绘制标签。
        xAxis.setLabelCount(strings.length, false);
    }

    private void initY(BarChart barChart) {
        barChart.getAxisLeft().setDrawZeroLine(false);
        //去掉左侧Y轴刻度
        barChart.getAxisLeft().setDrawLabels(false);
        //去掉左侧Y轴
        barChart.getAxisLeft().setDrawAxisLine(false);
        //去掉中间竖线
        barChart.getXAxis().setDrawGridLines(false);
        //去掉中间横线
        barChart.getAxisLeft().setDrawGridLines(true);
    }
}