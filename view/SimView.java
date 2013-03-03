package view;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;



public class SimView extends ApplicationFrame {

	 JFreeChart chart = null;
  public SimView(String title, DefaultHighLowDataset data) {
  super(title);

  chart = ChartFactory.createCandlestickChart("", "", "", data, false);
  
  XYPlot plot = chart.getXYPlot();
  CandlestickRenderer renderer = (CandlestickRenderer) plot.getRenderer();
  renderer.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);
  renderer.setCandleWidth(5);
  renderer.setAutoWidthFactor(30);
  //renderer.setBaseShapesVisible(true);
  //NumberFormat currency = NumberFormat.getCurrencyInstance();
  //currency.setMaximumFractionDigits(0);
  NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
  rangeAxis.setAutoRangeIncludesZero(false);
  //rangeAxis.setNumberFormatOverride(currency);
 
  
  ChartPanel chartPanel = new ChartPanel(chart);
  chartPanel.setPreferredSize(new java.awt.Dimension(600, 350));
  setContentPane(chartPanel);

	   this.pack();
	   RefineryUtilities.centerFrameOnScreen(this);
	   this.setVisible(true);
	   
  }
  
  public void setModel(DefaultHighLowDataset data){
	  chart.getXYPlot().setDataset(data);
	  
  }

  }

