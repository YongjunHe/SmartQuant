package kChart;

import java.math.BigDecimal;
import java.util.Iterator;

import impl.ARBRImpl;
import message.ARBRReport;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import service.ARBRService;
import enums.StockType;

/*
 * @author: hyj14
 * @date: 2016/04/08
 * 
 * @mender: hyj14
 * @date: 2016/04/13
 * 
 * @type: class
 * @description:生成人气意愿雷达图
 */

public class SimpleARBRChart {
	private ARBRService as;
	private Iterator<ARBRReport> ars;
	private ARBRReport ar;
	private BigDecimal bd;
	private double maxvalue;
	private double minvalue;
	private double avgAr;
	private double avgBr;
	private StockType type;

	public SimpleARBRChart() {
		as = ARBRImpl.create();
	}

	public JFreeChart ARBRChart() {
		CategoryDataset dataset = getDataSet();
		CalibrationSpiderWebPlot radarplot = new CalibrationSpiderWebPlot(
				dataset, maxvalue-minvalue*2);
		JFreeChart chart = new JFreeChart(radarplot);
		return chart;
	}

	/*
	 * @author: hyj14
	 * 
	 * @date: 2016/04/08
	 * 
	 * @description: 创建股票数据集
	 * 
	 * 
	 * @change:保留4位小数
	 * 
	 * @changeDate:2016/04/12
	 */
	private CategoryDataset getDataSet() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		ars = as.getSelectAnswer();		
		while (ars.hasNext()) {
			ar = ars.next();
			avgAr = ar.getAvgAR();
			avgBr = ar.getAvgBR();
			if (avgAr > maxvalue)
				maxvalue = avgAr;
			if (avgBr > maxvalue)
				maxvalue = avgBr;
			if (avgAr < minvalue)
				minvalue = avgAr;
			if (avgBr < minvalue)
				minvalue = avgBr;
		}
		
		ars = as.getSelectAnswer();	
		while (ars.hasNext()) {
			ar = ars.next();
			avgAr = ar.getAvgAR();
			bd = new BigDecimal(avgAr-minvalue*2);
			avgAr = bd.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();

			avgBr = ar.getAvgBR();
			bd = new BigDecimal(avgBr-minvalue*2);
			avgBr = bd.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();

			dataset.addValue(avgAr, "AR", ar.getcType());
			dataset.addValue(avgBr, "BR", ar.getcType());

		}
		return dataset;
	}
}
