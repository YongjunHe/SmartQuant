package data.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import sun.text.normalizer.VersionInfo;

public class ExcelHelper {


	public static HSSFWorkbook getExcel(String fileName){
		InputStream inputStream = null;
		HSSFWorkbook workBook = null;
		try {
			//			inputStream = new FileInputStream(new File(fileName));
			//			workBook = HSSFWorkbook.g;
			InputStream input = new FileInputStream(fileName);
			workBook = new HSSFWorkbook(input);
            input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return workBook;
	}
	
	
	public static void saveExcel(HSSFWorkbook workBook, String fileName){
		FileOutputStream fileOut = null;  
		try{              
			fileOut = new FileOutputStream(fileName);  
			workBook.write(fileOut);  
			fileOut.close();  
			System.out.print("OK");  
		}catch(Exception e){  
			e.printStackTrace();  
		}  
		finally{  
			if(fileOut != null){  
				try {  
					fileOut.close();  
				} catch (IOException e) {  
					// TODO Auto-generated catch block  
					e.printStackTrace();  
				}  
			}  
		}  
	}  
	
	
	public static void main(String[]args){
		
		
		HSSFWorkbook workbook = ExcelHelper.getExcel("workbook.xls");
		HSSFSheet sheet = workbook.getSheetAt(0);
		System.out.println(sheet.getSheetName());
	}

}

