package controller;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import model.SimModel;

import view.SimView;

import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.ui.RefineryUtilities;



public class SimController {
	
	SimModel dailyModel = null;
	SimView dailyView = null; 
	String currency = null;
	SimModel monthlyModel = null;
	SimView monthlyView = null; 
	
	public SimController (String currency, Date date){
	
	this.currency = currency;	
	dailyModel = new SimModel(currency, date).getDailyModel();
    DefaultHighLowDataset ddata = new DefaultHighLowDataset(
	"", dailyModel.getDate(), dailyModel.getHigh(), dailyModel.getLow(), dailyModel.getOpen(), dailyModel.getClose(), dailyModel.getVolume());
		
    dailyView  = new SimView("Daily",ddata);
    
	monthlyModel = new SimModel(currency, date).getMonthlyModel();
    DefaultHighLowDataset mdata = new DefaultHighLowDataset(
	"", monthlyModel.getDate(), monthlyModel.getHigh(), monthlyModel.getLow(), monthlyModel.getOpen(), monthlyModel.getClose(), monthlyModel.getVolume());
		
    monthlyView  = new SimView("Monthly",mdata);
		

	}

	public void setModel(Date date){
		dailyModel = new SimModel(currency, date).getDailyModel();
	    DefaultHighLowDataset ddata = new DefaultHighLowDataset(
		"", dailyModel.getDate(), dailyModel.getHigh(), dailyModel.getLow(), dailyModel.getOpen(), dailyModel.getClose(), dailyModel.getVolume());
		if (dailyView != null ) dailyView.setModel(ddata);
		
	    monthlyModel = new SimModel(currency, date).getMonthlyModel();
	    DefaultHighLowDataset mdata = new DefaultHighLowDataset(
		"", monthlyModel.getDate(), monthlyModel.getHigh(), monthlyModel.getLow(), monthlyModel.getOpen(), monthlyModel.getClose(), monthlyModel.getVolume());
		if (monthlyView != null ) monthlyView.setModel(mdata);
	}

public static void main(String[] args){
	Date date = null;
	try {
		date = new java.text.SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH).parse("2013.02.15");
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	SimController c = new SimController("EURUSD", date );
	
}
	
	
}
