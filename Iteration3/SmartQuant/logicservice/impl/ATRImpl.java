package impl;

import java.net.SocketException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import message.StockDateNode;
import service.ATRService;
import service.StockCheckService;

public class ATRImpl implements ATRService {
	private StockCheckService scs;
	private Iterator<StockDateNode> sdns;
	private StockDateNode sdn1;
	private StockDateNode sdn2;

	public ATRImpl() {
		scs = StockCheck.create();
	}

	/*
	 * @author: hyj14
	 * 
	 * @date: 2016/04/13
	 * 
	 * @description: 生成均幅指标
	 * 
	 * 
	 * @change:
	 * 
	 * @changeDate:
	 */
	@Override
	public Map ATRData(String name, String beginDate, String endDate) throws Exception {
		// TRi 的值等于以下三个数值中的最大值：
		// |第i日的最高价-第i日的最低价|（即当日最高价与当日最低价的距离）
		// |第i-1日的收盘价-第i日的最高价| （即当日最高价与前一日收盘价的距离）
		// |第i-1日的收盘价-第i日的最低价| （即前一日收盘价与当日最低价的距离）
		// ATR(N)i= N-1/N * ATR(N)i-1+ TRi/N
		// 一般取n=10，m=5
		double tr;
		double temp1;
		double temp2;
		double temp3;
		int count = 0;
		Map<String, Double> ATR = new HashMap<String, Double>();
		ArrayList<Double> TR = new ArrayList<Double>();
		ArrayList<String> tempDate = new ArrayList<String>();

		beginDate = FinalSign.weekChange(beginDate, -2);
		sdns = scs.getStockMessage(name, beginDate, endDate);
		sdn2 = sdns.next();

		while (sdns.hasNext()) {
			count++;
			sdn1 = sdn2;
			sdn2 = sdns.next();
			tempDate.add(sdn2.getDate());
			temp1 = sdn2.getHigh() - sdn2.getLow();
			temp2 = Math.abs(sdn1.getClose() - sdn2.getHigh());
			temp3 = Math.abs(sdn1.getClose() - sdn2.getLow());
			tr = temp1 > temp2 ? temp1 : temp2;
			tr = tr > temp3 ? tr : temp3;
			TR.add(tr);
		}

		count = count - 9;
		double Atr[] = new double[count];
		for (int i = 0; i < count; i++)
			for (int j = 0; j < 10; j++)
				Atr[i] += TR.get(i + j) / 10;
		for (int i = 0; i < count; i++)
			ATR.put(tempDate.get(i + 9), Atr[i]);

		return ATR;
	}

	/*
	 * @author: hyj14
	 * 
	 * @date: 2016/04/13
	 * 
	 * @description: 生成平均均幅指标
	 * 
	 * 
	 * @change:修改计算天数
	 * 
	 * @changeDate:2016/04/14
	 */
	@Override
	public Map MATRData(String name, String beginDate, String endDate)
			throws Exception {
		beginDate = FinalSign.weekChange(beginDate, -1);
		Map<String, Double> ATR = this.ATRData(name, beginDate, endDate);
		sdns = scs.getStockMessage(name, beginDate, endDate);
		Map<String, Double> MATR = new HashMap<String, Double>();
		ArrayList<String> tempDate = new ArrayList<String>();

		while (sdns.hasNext())
			tempDate.add(sdns.next().getDate());

		int count = tempDate.size() - 5;
		double Matr[] = new double[count];
		for (int i = 0; i < count; i++)
			for (int j = 0; j < 5; j++)
				Matr[i] += ATR.get(tempDate.get(i + j + 1)) / 5;
		for (int i = 0; i < count; i++) 
			MATR.put(tempDate.get(i + 5), Matr[i]);

		return MATR;
	}
}
