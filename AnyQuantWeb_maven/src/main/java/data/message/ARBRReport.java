package data.message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import data.enums.Cyc;
import data.enums.Recommendation;
import data.enums.StockType;
import data.helper.FinalSign;



public class ARBRReport {

	private Map<String, ARBRStock> ARBRMap;
	private double avgAR;
	private double avgBR;
	private Recommendation recommendation;
	private StockType type;
	private String cType;
	
	
	public double getAvgAR(){
		return avgAR;
	}
	
	public double getAvgBR(){
		return avgBR;
	}


	public ARBRReport(){
		ARBRMap = new HashMap<String, ARBRStock>();
		avgAR = 0;
		avgBR = 0;
	}

	public ARBRStock getARBRMap(String name) {
		return ARBRMap.get(name);
	}
	
	public void setARBR(ARBRStock stock) {
		ARBRMap.put(stock.getName(), stock);
	}

	//算法需要改进
	public void analyse(){
		Iterator iterator = ARBRMap.values().iterator();
		while(iterator.hasNext()){
			ARBRStock stock = (ARBRStock) iterator.next();
			Iterator it = stock.getARBRMap(Cyc.week);
			ARBRDateNode node = (ARBRDateNode)it.next();

			avgAR = avgAR + node.getAR();
			avgBR = avgBR + node.getBR();

		}
		avgAR = avgAR/ARBRMap.size();
		avgBR = avgBR/ARBRMap.size();


		if(avgBR < avgAR && avgBR < 100)
			setRecommendation(Recommendation.excellent);

		else if(avgBR < avgAR && avgAR < 50)
			setRecommendation(Recommendation.excellent);

		else if(avgBR > 400 || avgAR > 180)
			setRecommendation(Recommendation.bad);

		else if(avgBR < 40 ||  avgAR < 40)

			setRecommendation(Recommendation.excellent);
		else
			setRecommendation(Recommendation.well);
	}

	public Recommendation getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(Recommendation recommendation) {
		this.recommendation = recommendation;
	}

	public StockType getType() {
		return type;
	}

	public void setType(StockType type) {
		this.type = type;
		cType = FinalSign.getStockCType(this.type);
	}

	public String getcType() {
		return cType;
	}



	




}
