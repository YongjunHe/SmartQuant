package kChart;

import impl.ATRImpl;

import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;

import service.ATRService;

/*
 * @author: hyj14
 * @date: 2016/04/08
 * 
 * @mender: 
 * @date: 
 * 
 * @type: class
 * @description:生成均幅指标折线图
 */

public class SimpleATRChart {
	private ATRService atrs;
	private SimpleDateFormat dateFormat;
	private Date date;

	public SimpleATRChart() {
		atrs = new ATRImpl();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
	}

	public JFreeChart ATRChart(String idNumber, String beginDate, String endDate)
			throws Exception {
		double highValue = Double.MIN_VALUE;// 设置折线图数据当中的最大值
		double minValue = Double.MAX_VALUE;// 设置折线图数据当中的最小值

		Map map = getDataSet(idNumber, beginDate, endDate);
		IntervalXYDataset atr = (IntervalXYDataset) map.get("atr");
		IntervalXYDataset matr = (IntervalXYDataset) map.get("matr");

		int itemcount = atr.getItemCount(0);
		for (int i = 0; i < itemcount; i++) {
			if (highValue < atr.getYValue(0, i))
				highValue = atr.getYValue(0, i);
			if (minValue > atr.getYValue(0, i))
				minValue = atr.getYValue(0, i);
		}
		itemcount = matr.getItemCount(0);
		for (int i = 0; i < itemcount; i++) {
			if (highValue < matr.getYValue(0, i))
				highValue = matr.getYValue(0, i);
			if (minValue > matr.getYValue(0, i))
				minValue = matr.getYValue(0, i);
		}

		DateAxis x1Axis = new DateAxis();// 设置x轴，也就是时间轴
		x1Axis.setAutoRange(true);// 设置采用自动设置时间范围
		x1Axis.setAutoTickUnitSelection(false);// 设置不采用自动选择刻度值
		x1Axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);// 设置标记的位置
		x1Axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));// 设置显示时间的格式
		x1Axis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());// 设置标准的时间刻度单位
		x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());// 设置时间线显示的规则，用这个方法就摒除掉了周六和周日这些没有交易的日期(很多人都不知道有此方法)，使图形看上去连续
		x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 7));// 设置时间刻度的间隔，一般以周为单位
		NumberAxis y1Axis = new NumberAxis();// 设定y轴，就是数字轴
		y1Axis.setAutoRange(false);// 使用自动设定范围
		y1Axis.setRange(minValue * 0.9, highValue * 1.1);// 设定y轴值的范围，比最低值要低一些，比最大值要大一些，这样图形看起来会美观些
		y1Axis.setTickUnit(new NumberTickUnit(
				(highValue * 1.1 - minValue * 0.9) / 10));// 设置刻度显示的密度

		XYPlot plot = new XYPlot(atr, x1Axis, y1Axis,
				new DefaultXYItemRenderer());
		plot.setDataset(1, matr);
		plot.setRenderer(1, new DefaultXYItemRenderer());

		JFreeChart chart = new JFreeChart(idNumber, plot);
		return chart;
	}

	private Map getDataSet(String idNumber, String beginDate, String endDate)
			throws Exception {
		Map map = new HashMap();
		TimeSeries atr = new TimeSeries("ATR(10)",
				org.jfree.data.time.Day.class);
		TimeSeries matr = new TimeSeries("MATR(5)",
				org.jfree.data.time.Day.class);

		Map<String, Double> ATR = atrs.ATRData(idNumber, beginDate, endDate);
		Iterator<String> keys = ATR.keySet().iterator();
		String key;
		while (keys.hasNext()) {
			key = keys.next();
			date = dateFormat.parse(key);
			atr.add(new Day(date.getDate(), date.getMonth() + 1,
					date.getYear() + 1900), ATR.get(key));
		}

		Map<String, Double> MATR = atrs.MATRData(idNumber, beginDate, endDate);
		keys = MATR.keySet().iterator();
		while (keys.hasNext()) {
			key = keys.next();
			date = dateFormat.parse(key);
			matr.add(
					new Day(date.getDate(), date.getMonth() + 1,
							date.getYear() + 1900), MATR.get(key));
		}

		map.put("atr", new TimeSeriesCollection(atr));
		map.put("matr", new TimeSeriesCollection(matr));
		return map;
	}
}
