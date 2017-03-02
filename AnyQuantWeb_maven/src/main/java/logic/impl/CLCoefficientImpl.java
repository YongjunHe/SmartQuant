package logic.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.NTCredentials;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import data.helper.FinalSign;
import data.message.UniversalDateNode;
import javafx.print.Collation;
import logic.service.CLCoefficientService;
import twaver.base.A.E.i;

public class CLCoefficientImpl implements CLCoefficientService{
	
	private static double[] index = {0.2,0.4,0.7}; 
	private static String[]strings1 = {"正相关", "负相关"};
	private static String[]strings2 = {"极低相关", "低度相关", "中等相关", "高度相关", "完全相关"};

	@Override
	public String calculation(Iterator<Double> list1, Iterator<Double> list2 , int size) {
		// TODO Auto-generated method stub
		double x;
		double y;
		double sumXY = 0;
		double sumX = 0;
		double sumY = 0;
		double sumX2 = 0;
		double sumY2 = 0;
		double n = (double) (size);
		while(list1.hasNext()){
           x = list1.next();
           y = list2.next();
           
           sumXY = x * y + sumXY;
           sumX = sumX + x;
           sumY = sumY + y;
           sumX2 = Math.pow(x, 2) + sumX2;
           sumY2 = Math.pow(y, 2) + sumY2;
           	
		}
		
		double CC = (n * sumXY - sumX * sumY) /
				(Math.sqrt(n* sumX2- Math.pow(sumX, 2)) * Math.sqrt(n* sumY2- Math.pow(sumY, 2)));
		
		
		return degree(CC);
	}
	
	
	
	private String degree(double CC){
		
         int temp = 1;	
         if(CC == 0)
        	 return "它们为零相关";
         else if(CC>0)
        	 temp = 0;

         
         String result = "相关系数为" + CC;
         CC = Math.abs(CC);

			int level = 0;
			while(CC > index[level]){
				level++;
				if(level >= index.length)
					break;
			}
			
			if(CC >= 1)
				level++;


		result = result + ";相关方向为" + strings1[temp] + ";相关程度为" + strings2[level] + ".";
		return result;
		
		

	}
	
	
	 public static void main(String[]args){
		 
		 double []l1 = {12.5,15.3,23.2,26.4,33.5,34.4,39.4,45.2,55.4,60.9};
		 double []l2 = {21.2,23.9,32.9,34.1,42.5,43.2,49,52.8,59.4,63.5};
		 List<Double>list1 = new ArrayList<Double>();
		 List<Double> list2 = new ArrayList<Double>();
      for(double i:l1)
    	  list1.add(i);
      for(double i:l2)
    	  list2.add(i);
      

		 
		 
		 CLCoefficientService coefficientService = new CLCoefficientImpl();
		 System.out.println(coefficientService.calculation(list1.iterator(), list2.iterator(), list1.size()));

		 
		 
		 
		 
		 
	 }

}
