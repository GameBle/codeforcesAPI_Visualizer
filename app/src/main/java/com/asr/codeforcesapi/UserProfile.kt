package com.asr.codeforcesapi

import android.graphics.Color.BLACK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class UserProfile : AppCompatActivity() {
    lateinit var barChart: BarChart
    lateinit var barData: BarData
    lateinit var barDataSet: BarDataSet
    lateinit var barEntriesList: ArrayList<BarEntry>

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)


        val numberList = intent.getIntegerArrayListExtra("key")

        barChart = findViewById(R.id.idBarChart)
        getBarChartData(numberList)
        barDataSet = BarDataSet(barEntriesList, "Bar Chart Data")
        barData = BarData(barDataSet)
        barChart.data = barData
        barDataSet.valueTextColor = BLACK
        barDataSet.color = resources.getColor(R.color.teal_200)
        barDataSet.valueTextSize = 16f
        barChart.description.isEnabled = false

        val btn = findViewById<Button>(R.id.btn_clr)
        btn.setOnClickListener {

            textView.text = ""

            barChart.data?.clearValues()
            barChart.xAxis.valueFormatter = null
            barChart.notifyDataSetChanged()
            barChart.clear()
            barChart.invalidate()

        }
    }

    private fun getBarChartData(numberList: java.util.ArrayList<Int>?) {
        barEntriesList = ArrayList()
        val len = numberList?.size
        for (i in 0 until len!!) {
            barEntriesList.add(BarEntry(i * 1f + 1f, numberList.elementAt(i) * 1f))
        }
    }
}