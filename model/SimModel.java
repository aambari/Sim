package model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import org.skife.csv.*;;

public class SimModel {
	

	private	  Date[] date = null;
	private  double[] high = null;
	private  double[] low = null;
	private  double[] open = null;
	private  double[] close = null;
	private  double[] volume = null;
	private String currency = null;
	private Date stopHere = null;
	private int index = 0;
	
	
	public SimModel(String currency, Date stopHere){
		this.currency = currency;
		this.stopHere = stopHere;
	}
	

	
	public Date[] getDate() {
		return date;
	}


	public void setDate(Date[] date) {
		this.date = date;
	}


	public double[] getHigh() {
		return high;
	}


	public void setHigh(double[] high) {
		this.high = high;
	}


	public double[] getLow() {
		return low;
	}


	public void setLow(double[] low) {
		this.low = low;
	}


	public double[] getOpen() {
		return open;
	}


	public void setOpen(double[] open) {
		this.open = open;
	}


	public double[] getClose() {
		return close;
	}


	public void setClose(double[] close) {
		this.close = close;
	}


	public double[] getVolume() {
		return volume;
	}


	public void setVolume(double[] volume) {
		this.volume = volume;
	}


	

	public List<String[]> readCSV(String file){
		List<String[]> result = null;
		CSVReader reader = null;
		
		try {
			reader = new SimpleReader();
			URL url = this.getClass().getClassLoader().getResource(file);
		        InputStream in = url.openStream();
		        result = reader.parse(in);
		      
		        in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			return result;
	}

	
	@SuppressWarnings("deprecation")
	private void populateArrays(List<String[]> items) {
		
	int size = items.size();
		
		 date = new Date[size];
		 high = new double[size];
		 low = new double[size];
		 open = new double[size];
		 close = new double[size];
		 volume = new double[size]; 
	
	
	
		 Date currentRow = null;
		 index = 0;
		 for (int i= 0; i < size; i++) {
			 String[] row =  items.get(i);
			 try {
				
				currentRow = new java.text.SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH).parse(row[0]);
				if (stopHere.after(currentRow)|| stopHere.equals(currentRow)) {
				date[index] = currentRow;
				open[index] = new Double(row[2]);
				 high[index] = new Double(row[3]);
				 low[index] = new Double(row[4]);
				 close[index] = new Double(row[5]);
				 volume[index] =0; //new Double(row[6]);
				//	System.out.println(date[index]);
				 index++;
		
				} else {
					//System.out.println(stopHere + " < " + currentRow);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			 
			 
		 }
		 //index--;
		
		 
		
	}
	



	public SimModel getDailyModel(){
		
		String fileName = currency + "_daily.csv";
		this.populateArrays(readCSV(fileName)); 
		this.trimArraysForDaily();
		return this;
	}
	
	
	
	private void trimArraysForDaily() {
		 trimArrays(); 
		
	}



	private void trimArrays() {
		open = Arrays.copyOfRange(open, 0, index);
		 high = Arrays.copyOfRange(high, 0, index);
		 low = Arrays.copyOfRange(low, 0, index);
		 close = Arrays.copyOfRange(close, 0, index);
		 date = Arrays.copyOfRange(date, 0, index);
	}



	public SimModel getMonthlyModel(){
		
		String fileName = currency + "_monthly.csv";
		this.populateArrays(readCSV(fileName)); 
		this.trimArraysForMonthly();
		return this;
	}



	private void trimArraysForMonthly() {
		index--;
		trimArrays(); 
		
	}


}



