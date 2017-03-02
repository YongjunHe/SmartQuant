import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.xmlbeans.impl.xb.xsdschema.impl.AttributeGroupRefImpl;

import data.enums.*;
import data.helper.*;
import data.impl.*;
import data.message.*;
import data.service.*;
import logic.impl.*;
import logic.service.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		StockCheckService scs = StockCheck.create();
//        Iterator it;
//		try {
//			it = scs.getSelectedStock();
//	        while(it.hasNext()){
//
//	        	Stock stock=(Stock) it.next();
//	        	StockDateNode node=stock.getTodayData();
//	        	System.out.println(stock.getCName());
//	        }
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
//		ARBRService arbrService = ARBRImpl.create();
//		Iterator iterator = arbrService.getSelectAnswer();
//
//		while(iterator.hasNext()){
//			ARBRReport report = (ARBRReport) iterator.next();
//			System.out.println(report.getAvgAR()+" "+report.getAvgBR()+" "+report.getcType()+" "+report.getRecommendation());
//		}
		
		
//		ATRService atrService = new ATRImpl();
//		try {
//			Map<String, Double> ATR = atrService.ATRData("sh000300", "2016-05-07", "2016-06-07");
//			Iterator<String> keys = ATR.keySet().iterator();
//			while (keys.hasNext()) {
//				String key = keys.next();
//				System.out.println(key);
//				System.out.println(ATR.get(key));
//			}
//		} catch (SocketException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
//		CCIService cciService = CCIImpl.create();
//		try {
//			Iterator iterator = cciService.getDayCCI("sh000300", "2016-05-07", "2016-06-07");
//			ArrayList<UniversalDateNode> list = new ArrayList<UniversalDateNode>();
//			while(iterator.hasNext()){
//				UniversalDateNode node = (UniversalDateNode) iterator.next();
//				System.out.println(node.getDate()+" "+node.getData()+" "+node.getDataType());
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		CCIService cciService = CCIImpl.create();
//		ArrayList<UniversalDateNode> list = new ArrayList<UniversalDateNode>();
//		Iterator iterator = null;
//		
//		try {
//			iterator = cciService.getDayCCI("sh600066", "2016-05-07", "2016-06-07");
//			while(iterator.hasNext()){
//				UniversalDateNode node = (UniversalDateNode) iterator.next();
//				System.out.println(node.getData()+" "+node.getDate());
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		StockCheckService stockCheckService = StockCheck.create();
		Iterator iterator = stockCheckService.getStockMessageByCName("中国");
		while(iterator.hasNext()){
			Stock stock = (Stock) iterator.next();
			System.out.println(stock.getCName());
		}
		
	}

}
