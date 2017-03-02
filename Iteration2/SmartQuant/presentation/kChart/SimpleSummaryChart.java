package kChart;

/*
 * @author: hyj14
 * @date: 2016/04/15
 * 
 * @mender: 
 * @date: 
 * 
 * @type: class
 * @description:生成大盘点数据图
 */
import java.awt.Color;
import java.awt.Paint;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import message.MeanDateNode;
import message.StockDateNode;
import message.SummaryDateNode;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.BorderArrangement;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;

import enums.Cyc;
import enums.MarketType;
import impl.MeanCaculation;
import impl.SummaryCheck;
import service.MeanService;
import service.SummaryCheckService;

public class SimpleSummaryChart {
	private MeanService ms;
	private SummaryCheckService scs;
	private Iterator<SummaryDateNode> sdns;
	private MeanDateNode mdn;
	private SummaryDateNode sdn;
	private SimpleDateFormat dateFormat;
	private Date date;
	private Calendar cal;

	/*
	 * @author: hyj14
	 * 
	 * @date: 2016/04/15
	 * 
	 * @description: 对象初始化
	 */
	public SimpleSummaryChart() {
		scs = SummaryCheck.create();
		ms = MeanCaculation.create();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
	}

	/*
	 * @author: hyj14
	 * 
	 * @date: 2016/04/15
	 * 
	 * @description: 获得在特定时间的大盘点走势图和交易量直方图
	 * 
	 * 
	 * @change:
	 * 
	 * @changeDate:
	 */
	public JFreeChart SummaryChart(MarketType type, String beginDate,
			String endDate, Cyc cyc) throws Exception {
		double highValue = Double.MIN_VALUE;// 设置K线数据当中的最大值
		double minValue = Double.MAX_VALUE;// 设置K线数据当中的最小值
		double high2Value = Double.MIN_VALUE;// 设置成交量的最大值
		double min2Value = Double.MAX_VALUE;// 设置成交量的最低值

		Map map = createDatasetMap(type, beginDate, endDate);

		final OHLCSeriesCollection k_line = new OHLCSeriesCollection();// 保留K线数据的数据集，必须申明为final，后面要在匿名内部类里面用到
		k_line.addSeries((OHLCSeries) map.get("k_line"));
		TimeSeriesCollection vol = new TimeSeriesCollection();// 保留成交量数据的集合
		vol.addSeries((TimeSeries) map.get("vol"));
		IntervalXYDataset avg_line5 = (IntervalXYDataset) map.get("avg_line5");
		IntervalXYDataset avg_line10 = (IntervalXYDataset) map
				.get("avg_line10");
		IntervalXYDataset avg_line20 = (IntervalXYDataset) map
				.get("avg_line20");
		IntervalXYDataset avg_line60 = (IntervalXYDataset) map
				.get("avg_line60");
		IntervalXYDataset vol_avg_line5 = (IntervalXYDataset) map
				.get("vol_avg_line5");
		IntervalXYDataset vol_avg_line10 = (IntervalXYDataset) map
				.get("vol_avg_line10");

		// 获取K线数据的最高值和最低值
		int seriesCount = k_line.getSeriesCount();// 一共有多少个序列，目前为一个
		for (int i = 0; i < seriesCount; i++) {
			int itemCount = k_line.getItemCount(i);// 每一个序列有多少个数据项
			for (int j = 0; j < itemCount; j++) {
				if (highValue < k_line.getHighValue(i, j)) {// 取第i个序列中的第j个数据项的最大值
					highValue = k_line.getHighValue(i, j);
				}
				if (minValue > k_line.getLowValue(i, j)) {// 取第i个序列中的第j个数据项的最小值
					minValue = k_line.getLowValue(i, j);
				}
			}
		}
		// 获取最高值和最低值
		int seriesCount2 = vol.getSeriesCount();// 一共有多少个序列，目前为一个
		for (int i = 0; i < seriesCount2; i++) {
			int itemCount = vol.getItemCount(i);// 每一个序列有多少个数据项
			for (int j = 0; j < itemCount; j++) {
				if (high2Value < vol.getYValue(i, j)) {// 取第i个序列中的第j个数据项的值
					high2Value = vol.getYValue(i, j);
				}
				if (min2Value > vol.getYValue(i, j)) {// 取第i个序列中的第j个数据项的值
					min2Value = vol.getYValue(i, j);
				}
			}
		}
		// 设置若干个时间线的Render，目的是用来让几条均线显示不同的颜色，和为时间线加上鼠标提示
		XYLineAndShapeRenderer xyLineRender = new XYLineAndShapeRenderer(true,
				false);
		xyLineRender.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
				"{0}: ({1}, {2})", dateFormat, new DecimalFormat("0.00")));
		xyLineRender.setSeriesPaint(0, Color.red);

		XYLineAndShapeRenderer xyLineRender1 = new XYLineAndShapeRenderer(true,
				false);
		xyLineRender1.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
				"{0}: ({1}, {2})", dateFormat, new DecimalFormat("0.00")));
		xyLineRender1.setSeriesPaint(0, Color.BLACK);

		XYLineAndShapeRenderer xyLineRender2 = new XYLineAndShapeRenderer(true,
				false);
		xyLineRender2.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
				"{0}: ({1}, {2})", dateFormat, new DecimalFormat("0.00")));
		xyLineRender2.setSeriesPaint(0, Color.blue);

		XYLineAndShapeRenderer xyLineRender3 = new XYLineAndShapeRenderer(true,
				false);
		xyLineRender3.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
				"{0}: ({1}, {2})", dateFormat, new DecimalFormat("0.00")));
		xyLineRender3.setSeriesPaint(0, Color.darkGray);
		XYLineAndShapeRenderer xyLineRender4 = new XYLineAndShapeRenderer(true,
				false);
		xyLineRender4.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
				"{0}: ({1}, {2})", dateFormat, new DecimalFormat("0.00")));
		xyLineRender4.setSeriesPaint(0, Color.orange);
		XYLineAndShapeRenderer xyLineRender5 = new XYLineAndShapeRenderer(true,
				false);
		xyLineRender5.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
				"{0}: ({1}, {2})", dateFormat, new DecimalFormat("0.00")));
		xyLineRender5.setSeriesPaint(0, Color.blue);

		final CandlestickRenderer candlestickRender = new CandlestickRenderer();// 设置K线图的画图器，必须申明为final，后面要在匿名内部类里面用到
		candlestickRender.setUseOutlinePaint(true); // 设置是否使用自定义的边框线，程序自带的边框线的颜色不符合中国股票市场的习惯
		candlestickRender
				.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);// 设置如何对K线图的宽度进行设定
		candlestickRender.setAutoWidthGap(0.001);// 设置各个K线图之间的间隔
		candlestickRender.setUpPaint(Color.RED);// 设置股票上涨的K线图颜色
		candlestickRender.setDownPaint(Color.GREEN);// 设置股票下跌的K线图颜色
		DateAxis x1Axis = new DateAxis();// 设置x轴，也就是时间轴
		x1Axis.setAutoRange(false);// 设置不采用自动设置时间范围
		x1Axis.setAutoTickUnitSelection(false);// 设置不采用自动选择刻度值
		x1Axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);// 设置标记的位置
		x1Axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));// 设置显示时间的格式
		x1Axis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());// 设置标准的时间刻度单位
		try {
			sdns = scs.getSummaryMessage(type, beginDate, endDate);
			beginDate = sdns.next().getDate();
			cal = Calendar.getInstance();
			cal.setTime(dateFormat.parse(endDate));
			cal.add(Calendar.DATE, 1);
			x1Axis.setRange(dateFormat.parse(beginDate), cal.getTime());// 设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 设置日大盘点走势图日期坐标值
		x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());// 设置时间线显示的规则，用这个方法就摒除掉了周六和周日这些没有交易的日期(很多人都不知道有此方法)，使图形看上去连续
		x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 7));// 设置时间刻度的间隔，一般以周为单位
		NumberAxis y1Axis = new NumberAxis();// 设定y轴，就是数字轴
		y1Axis.setAutoRange(false);// 不使用自动设定范围
		y1Axis.setRange(minValue * 0.9, highValue * 1.1);// 设定y轴值的范围，比最低值要低一些，比最大值要大一些，这样图形看起来会美观些
		y1Axis.setTickUnit(new NumberTickUnit(
				(highValue * 1.1 - minValue * 0.9) / 10));// 设置刻度显示的密度
		XYPlot plot1 = new XYPlot(k_line, x1Axis, y1Axis, candlestickRender);// 设置画图区域对象
		plot1.setDataset(1, avg_line5);
		plot1.setRenderer(1, xyLineRender1);
		plot1.setDataset(2, avg_line10);
		plot1.setRenderer(2, xyLineRender2);
		plot1.setDataset(3, avg_line20);
		plot1.setRenderer(3, xyLineRender3);
		plot1.setDataset(4, avg_line60);
		plot1.setRenderer(4, xyLineRender);

		XYBarRenderer xyBarRender = new XYBarRenderer() {
			private static final long serialVersionUID = 1L;// 为了避免出现警告消息，特设定此值

			public Paint getItemPaint(int i, int j) {// 匿名内部类用来处理当日的成交量柱形图的颜色与K线图的颜色保持一致
				if (k_line.getCloseValue(i, j) > k_line.getOpenValue(i, j)) {// 收盘价高于开盘价，股票上涨，选用股票上涨的颜色
					return candlestickRender.getUpPaint();
				} else {
					return candlestickRender.getDownPaint();
				}
			}
		};
		xyBarRender.setMargin(0.1);// 设置柱形图之间的间隔
		NumberAxis y2Axis = new NumberAxis();// 设置Y轴，为数值,后面的设置，参考上面的y轴设置
		y2Axis.setAutoRange(false);
		y2Axis.setRange(min2Value * 0.9, high2Value * 1.1);
		y2Axis.setTickUnit(new NumberTickUnit(
				(high2Value * 1.1 - min2Value * 0.9) / 4));
		XYPlot plot2 = new XYPlot(vol, null, y2Axis, xyBarRender);// 建立第二个画图区域对象，主要此时的x轴设为了null值，因为要与第一个画图区域对象共享x轴
		plot2.setDataset(1, vol_avg_line5);
		plot2.setRenderer(1, xyLineRender4);
		plot2.setDataset(2, vol_avg_line10);
		plot2.setRenderer(2, xyLineRender5);

		CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(
				x1Axis);// 建立一个恰当的联合图形区域对象，以x轴为共享轴
		combineddomainxyplot.add(plot1, 2);// 添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域2/3
		combineddomainxyplot.add(plot2, 1);// 添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域1/3
		combineddomainxyplot.setGap(10);// 设置两个图形区域对象之间的间隔空间

		JFreeChart chart = new JFreeChart(type.toString(),
				JFreeChart.DEFAULT_TITLE_FONT, combineddomainxyplot, false);

		// 为Chart图添加一个图例，这里我们可以定义需要显示的一些信息，及图例放置的位置，我们选择的顶部
		LegendTitle legendtitle = new LegendTitle(plot1);
		BlockContainer blockcontainer = new BlockContainer(
				new BorderArrangement());
		blockcontainer.setFrame(new BlockBorder(0.10000000000000001D,
				0.10000000000000001D, 0.10000000000000001D,
				0.10000000000000001D));
		BlockContainer blockcontainer1 = legendtitle.getItemContainer();
		blockcontainer1.setPadding(2D, 10D, 5D, 2D);
		blockcontainer.add(blockcontainer1);
		legendtitle.setWrapper(blockcontainer);
		legendtitle.setPosition(RectangleEdge.TOP);
		legendtitle.setHorizontalAlignment(HorizontalAlignment.CENTER);
		chart.addSubtitle(legendtitle);

		return chart;
	}

	/*
	 * @author: hyj14
	 * 
	 * @date: 2016/04/15
	 * 
	 * @description: 创建大盘点数据集
	 * 
	 * 
	 * @change:
	 * 
	 * @changeDate:
	 */
	private Map createDatasetMap(MarketType type, String beginDate,
			String endDate) throws Exception {
		Map map = new HashMap();
		OHLCSeries k_line = new OHLCSeries("MA");// 高开低收数据序列，大盘点的四个数据，依次是开，高，低，收
		TimeSeries vol = new TimeSeries("VOL");// 对应时间成交量数据
		TimeSeries avg_lin5 = new TimeSeries("MA5",
				org.jfree.data.time.Day.class);
		TimeSeries avg_lin10 = new TimeSeries("MA10",
				org.jfree.data.time.Day.class);
		TimeSeries avg_lin20 = new TimeSeries("MA20",
				org.jfree.data.time.Day.class);
		TimeSeries avg_lin60 = new TimeSeries("MA60",
				org.jfree.data.time.Day.class);
		TimeSeries vol_avg_lin5 = new TimeSeries("MAVOL5",
				org.jfree.data.time.Day.class);
		TimeSeries vol_avg_lin10 = new TimeSeries("MAVOL10",
				org.jfree.data.time.Day.class);

		// 获取大盘点数据
		sdns = scs.getSummaryMessage(type, beginDate, endDate);
		while (sdns.hasNext()) {
			sdn = sdns.next();
			date = dateFormat.parse(sdn.getDate());
			k_line.add(
					new Day(date.getDate(), date.getMonth() + 1,
							date.getYear() + 1900), sdn.getOpen(), sdn
							.getHigh(), sdn.getLow(), sdn.getClose());
			vol.add(new Day(date.getDate(), date.getMonth() + 1,
					date.getYear() + 1900), sdn.getVolume() / 1000);
		}

		Iterator<MeanDateNode> k5 = ms.dateMeanClose(type.toString(), Cyc.week,
				beginDate, endDate);
		Iterator<MeanDateNode> k10 = ms.dateMeanClose(type.toString(),
				Cyc.twoweek, beginDate, endDate);
		Iterator<MeanDateNode> k20 = ms.dateMeanClose(type.toString(),
				Cyc.month, beginDate, endDate);
		Iterator<MeanDateNode> k60 = ms.dateMeanClose(type.toString(),
				Cyc.threemonths, beginDate, endDate);
		Iterator<MeanDateNode> v5 = ms.dateMeanVolume(type.toString(),
				Cyc.week, beginDate, endDate);
		Iterator<MeanDateNode> v10 = ms.dateMeanVolume(type.toString(),
				Cyc.twoweek, beginDate, endDate);
		while (k5.hasNext()) {
			mdn = k5.next();
			date = dateFormat.parse(mdn.getDate());

			avg_lin5.add(
					new Day(date.getDate(), date.getMonth() + 1,
							date.getYear() + 1900), mdn.getData());

			mdn = k10.next();
			avg_lin10.add(
					new Day(date.getDate(), date.getMonth() + 1,
							date.getYear() + 1900), mdn.getData());

			mdn = k20.next();
			avg_lin20.add(
					new Day(date.getDate(), date.getMonth() + 1,
							date.getYear() + 1900), mdn.getData());

			mdn = k60.next();
			avg_lin60.add(
					new Day(date.getDate(), date.getMonth() + 1,
							date.getYear() + 1900), mdn.getData());

			mdn = v5.next();
			vol_avg_lin5.add(
					new Day(date.getDate(), date.getMonth() + 1,
							date.getYear() + 1900), mdn.getData() / 1000);

			mdn = v10.next();
			vol_avg_lin10.add(
					new Day(date.getDate(), date.getMonth() + 1,
							date.getYear() + 1900), mdn.getData() / 1000);
		}

		map.put("k_line", k_line);
		map.put("vol", vol);
		map.put("avg_line5", new TimeSeriesCollection(avg_lin5));
		map.put("avg_line10", new TimeSeriesCollection(avg_lin10));
		map.put("avg_line20", new TimeSeriesCollection(avg_lin20));
		map.put("avg_line60", new TimeSeriesCollection(avg_lin60));
		map.put("vol_avg_line5", new TimeSeriesCollection(vol_avg_lin5));
		map.put("vol_avg_line10", new TimeSeriesCollection(vol_avg_lin10));
		return map;
	}
}
