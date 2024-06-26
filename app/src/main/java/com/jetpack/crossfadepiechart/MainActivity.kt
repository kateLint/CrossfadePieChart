package com.jetpack.crossfadepiechart

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.jetpack.crossfadepiechart.ui.theme.*
import com.intuit.sdp.R as DP

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrossfadePieChartTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Compose PieChart",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            )
                        }
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Text(
                                text = "Compose PieChart using MPAndroidChart library",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp),
                                textAlign = TextAlign.Center
                            )


                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {

                                CrossFadePieChart()
                            }
                        }
                    }
                }
            }
        }
    }
}

var counter = 20

// Creating a composable
// function to create a button
@Composable
fun CreateButton(text:String, onClick: ()-> Unit){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = androidx.compose.ui.graphics.Color.Blue)
    ){
        Text(text = text, color = androidx.compose.ui.graphics.Color.Black)
    }
}

@Composable
fun CrossFadePieChart() { //the pie chart that o want to upadte

    var getPieChart by remember { mutableStateOf(getPieChartData.get(1).value) }

    CreateButton(text = "something"){
        counter = counter + counter
        getPieChart = getPieChartData.get(1).value?.plus(counter)

    }
    Column(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = DP.dimen._8sdp))
            .padding(top = dimensionResource(id = DP.dimen._4sdp))
            .size(dimensionResource(id = DP.dimen._200sdp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
      //  val getPieChart = getPieChartData
        println("Kate getPieChartData =" + getPieChartData.get(1).value)
        Crossfade(targetState = getPieChartData) { chartData ->
            AndroidView(
                factory = { context ->
                    PieChart(context).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                        )
                        initPieChart(this)
                    }
                },
                modifier = Modifier
                    .wrapContentSize()
                    .padding(dimensionResource(id = DP.dimen._8sdp)),
                update = {
                    updatePieChart(it, chartData)
                }
            )
        }
    }
}

fun initPieChart(chart: PieChart) {
    chart.description.isEnabled = false
    chart.isDrawHoleEnabled = false
    chart.legend.isEnabled = false
    chart.setEntryLabelColor(Color.BLACK)
}

fun updatePieChart(
    chart: PieChart,
    data: List<PieChartItem>
) {
    val entries = ArrayList<PieEntry>()

    for (i in data.indices) {
        val item = data[i]
        entries.add(PieEntry(item.value ?: 0.toFloat(), item.key ?: ""))
    }

    val ds = PieDataSet(entries, "")
    ds.colors = arrayListOf(
        Blue200.toArgb(),
        BlueGrey200.toArgb(),
        Blue300.toArgb(),
        BlueGrey300.toArgb(),
        Blue500.toArgb(),
        BlueGrey500.toArgb(),
        Blue700.toArgb(),
        BlueGrey700.toArgb()
    )
    ds.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
    ds.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
    ds.sliceSpace = 2f
    ds.valueTextColor = Color.BLACK
    ds.valueTextSize = 14f

    val d = PieData(ds)
    chart.data = d
    chart.invalidate()
}














