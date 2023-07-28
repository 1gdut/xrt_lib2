package com.example.intelligentinsertion.Manager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LineChartManager {
    private LineChart lineChart;
    private YAxis leftAxis;
    private YAxis rightAxis;
    private XAxis xAxis;
    private LineData lineData;
    private LineDataSet lineDataSet;
    private List<ILineDataSet> lineDataSets = new ArrayList<>();
    private SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
    private List<String> timeList = new ArrayList<>(); //存储x轴的时间

    public LineChartManager(LineChart mLineChart, String name, int color) {
        this.lineChart = mLineChart;
        leftAxis = lineChart.getAxisLeft();//左轴
        rightAxis = lineChart.getAxisRight();//右轴
        xAxis = lineChart.getXAxis();//X轴
        initLineChart();
        initLineDataSet(name, color);
    }

    private void initLineChart() {
        //设置是否绘制网格背景，默认为false，即不绘制。
        lineChart.setDrawGridBackground(true);
        //显示边界
        lineChart.setDrawBorders(true);


        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);//设置X轴的最小间隔为1f。
        xAxis.setLabelCount(24);//设置X轴的标签数量为10个。


        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);
    }

    private void initLineDataSet(String name, int color) {

        lineDataSet = new LineDataSet(null, name);//创建一个LineDataSet对象，传入的参数为数据集和名称。
        lineDataSet.setLineWidth(1f);//设置线条的宽度为1.5f。
        lineDataSet.setCircleRadius(1f);//设置线条的宽度为1.5f。
        lineDataSet.setColor(color);//设置线条的颜色。
        lineDataSet.setCircleColor(color);//设置圆圈的颜色
        lineDataSet.setHighLightColor(color);//设置高亮的颜色。
        //设置曲线填充
        lineDataSet.setDrawFilled(true);//设置是否绘制曲线的填充，默认为false，即不绘制。
        lineDataSet.setDrawValues(true);//设置是否绘制数据值，默认为false，即不绘制
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);//设置数据集依赖于左轴
        lineDataSet.setValueTextSize(10f);//设置数据值的文字大小为10f
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);//设置曲线的模式为CUBIC_BEZIER，即贝塞尔曲线。
        //添加一个空的 LineData
        lineData = new LineData();//创建一个空的LineData对象。
        lineChart.setData(lineData);//将LineData对象设置给LineChart对象
        lineChart.invalidate();//刷新LineChart的显示。

    }

    public void addEntry(int number) {

        //如果数据集中还没有数据点，则将lineDataSet添加到lineData中。这个判断是为了保证最开始的时候只添加一次数据集。
        if (lineDataSet.getEntryCount() == 0) {
            lineData.addDataSet(lineDataSet);
        }
        //将更新后的lineData设置给lineChart
        lineChart.setData(lineData);
        //如果timeList中的数据点数量超过9个，则清空timeList。这个判断是为了避免集合数据过多
        if (timeList.size() > 9) {
            timeList.clear();
        }
        //将当前时间的格式化字符串添加到timeList中。
        timeList.add(df.format(System.currentTimeMillis()));
        //创建一个Entry对象，表示一个数据点，其中x轴的值为lineDataSet中数据点的数量，y轴的值为传入的number参数。
        Entry entry = new Entry(lineDataSet.getEntryCount(), number);
        //将entry添加到lineData中的索引为0的数据集中
        lineData.addEntry(entry, 0);
        //通知数据已经改变
        lineData.notifyDataChanged();
        lineChart.notifyDataSetChanged();
        //设置在曲线图中显示的最大数量
        lineChart.setVisibleXRangeMaximum(12);
        //移到某个位置
        lineChart.moveViewToX(lineData.getEntryCount()-25);
    }
}
