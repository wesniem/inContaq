package nyc.c4q.jonathancolon.inContaq.utlities.graphs.bargraphs;

import android.graphics.Color;

import com.db.chart.Tools;
import com.db.chart.model.Bar;
import com.db.chart.model.BarSet;
import com.db.chart.view.BarChartView;

import java.util.ArrayList;

import nyc.c4q.jonathancolon.inContaq.utlities.sms.model.Sms;
import nyc.c4q.jonathancolon.inContaq.data.WordCount;

import static com.db.chart.renderer.AxisRenderer.LabelPosition.NONE;

/**
 * Created by jonathancolon on 3/16/17.
 */

public class WordCountBarGraph {
    private static final String FILL_COLOR = "#7F000000";
    private static final String SENT_COLOR = "#EF7674";
    private static final String RECEIVED_COLOR = "#FDFFFC";
    private int averageWordCountSent;
    private int averageWordCountReceived;
    private BarChartView barChartView;
    private String[] mLabels = {"Sent", "Received"};
    private ArrayList<Sms> smsList;
    private int highestValue;

    public WordCountBarGraph(BarChartView barChartView, ArrayList<Sms> smsList) {
        this.barChartView = barChartView;
        this.smsList = smsList;
    }

    public void showBarGraph() {
        getBarGraphValues(smsList);
        loadGraph();
    }

    private void getBarGraphValues(ArrayList<Sms> smsList) {
        averageWordCountSent = WordCount.getAverageWordCountSent(smsList);
        averageWordCountReceived = WordCount.getAverageWordCountReceived(smsList);
        this.smsList = smsList;
    }

    private void loadGraph() {
        getYvalue();
        setGraphData();
        setGraphAttributes();
    }

    private void setGraphData() {
        BarSet barSet = new BarSet();
        Bar barSent = new Bar(mLabels[0], averageWordCountSent);
        Bar barReceived = new Bar(mLabels[1], averageWordCountReceived);
        barReceived.setColor(Color.parseColor(RECEIVED_COLOR));
        barSent.setColor(Color.parseColor(SENT_COLOR));
        barSet.addBar(barSent);
        barSet.addBar(barReceived);

        barChartView.addData(barSet);
        barChartView.setBarSpacing(Tools.fromDpToPx(15));
        barChartView.setRoundCorners(Tools.fromDpToPx(2));
        barChartView.setBarBackgroundColor(Color.parseColor(FILL_COLOR));
    }

    private void setGraphAttributes() {
        // Chart
        barChartView.setXAxis(false)
                .setYAxis(false)
                .setAxisBorderValues(0, increaseByQuarter(highestValue))
                .setXLabels(NONE)
                .setYLabels(NONE);
        barChartView.show();
    }

    private void getYvalue() {
        highestValue = Math.max(averageWordCountReceived, averageWordCountSent);
        if (highestValue == 0){
            highestValue = 10;
        }
    }

    private int increaseByQuarter(int input) {
        return (int) Math.round(input * 1.25);
    }

}
