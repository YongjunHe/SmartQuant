package kChart;

import kChart.SimpleKChart;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;

import org.jfree.chart.JFreeChart;

import enums.Cyc;
import enums.MarketType;

public class Main {
	public static void main(String[] args) {
		try {

			SimpleKChart kchart = new SimpleKChart();
			JFreeChart chart = kchart.kChart("sh600011", "2016-01-05",
					"2016-03-21", Cyc.week);

			// SimpleARBRChart arbrchart = new SimpleARBRChart();
			// JFreeChart chart = arbrchart.ARBRChart();
			//
			// SimpleATRChart atrchart = new SimpleATRChart();
			// JFreeChart chart = atrchart.ATRChart("sh600011", "2016-01-21",
			// "2016-02-11");
			//
			// SimpleSummaryChart summarychart = new SimpleSummaryChart();
			// JFreeChart chart = summarychart.SummaryChart(MarketType.hs300,
			// "2015-01-21", "2016-02-21", Cyc.day);

			ChartPanel chartpanel = new ChartPanel(chart);
			JFrame jframe = new JFrame();
			jframe.setContentPane(chartpanel);
			jframe.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
