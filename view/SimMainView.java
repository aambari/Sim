package view;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
 

 
/*
 * This application demonstrates using spinners.
 * Other files required:
 *   SpringUtilities.java
 *   CyclingSpinnerListModel.java
 */
 
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import controller.SimController;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
 
public class SimMainView extends JPanel {
	
	SpinnerModel monthModel = null;
	SpinnerModel dateModel = null;
	 SpinnerModel yearModel  = null;
	 SimController controller = null;
	
    public SimMainView(boolean cycleMonths) {
        super(new FlowLayout());
 
        String[] labels = {"Month: ", "Year: ", "Day: "};
        int numPairs = labels.length;
        Calendar calendar = Calendar.getInstance();
        JFormattedTextField ftf = null;
 
        //Add the first label-spinner pair.
        
 
        monthModel = new SpinnerNumberModel(calendar.get(Calendar.MONTH), 1, 12,  1);                //step
       
        JSpinner spinner = addLabeledSpinner(this,
                                             labels[0],
                                             monthModel);
        //Tweak the spinner's formatted text field.
        ftf = getTextField(spinner);
        if (ftf != null ) {
            ftf.setColumns(2); //specify more width than we need
            ftf.setHorizontalAlignment(JTextField.RIGHT);
        }
 
        ChangeListener listener = new ChangeListener() {
        	  public void stateChanged(ChangeEvent e) {
        			Date date = null;
                	try {
                		date = new java.text.SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH).parse(yearModel.getValue() + "." + monthModel.getValue() + "." + dateModel.getValue());
                	} catch (ParseException ex) {
                		// TODO Auto-generated catch block
                		ex.printStackTrace();
                	}
                	if (controller == null) controller = new SimController("EURUSD", date );
                	else controller.setModel(date); 
        	  }
        	};

        	spinner.addChangeListener(listener);
        
   
        
        //Add second label-spinner pair.
        int currentYear = calendar.get(Calendar.YEAR);
         yearModel = new SpinnerNumberModel(currentYear, //initial value
                                       currentYear - 10, //min
                                       currentYear + 0, //max
                                       1);                //step
 
        spinner = addLabeledSpinner(this, labels[1], yearModel);
        //Make the year be formatted without a thousands separator.
        spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));
 
 
        spinner.addChangeListener(listener);
        

        
        
        //Add third label-spinner pair.
        int date = calendar.get(Calendar.DATE);
         dateModel = new SpinnerNumberModel(date, //initial value
        		1, //min
        		 31, //max
                                       1);                //step
   
        spinner = addLabeledSpinner(this, labels[2], dateModel);
        //Make the year be formatted without a thousands separator.
        spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));
        spinner.addChangeListener(listener);
        
        addOkButton(this);
 
        //Lay out the panel.
       /* SpringUtilities.makeCompactGrid(this,
                                        numPairs, 2, //rows, cols
                                        10, 10,        //initX, initY
                                        6, 10);       //xPad, yPad */
    }
 
    /**
     * Return the formatted text field used by the editor, or
     * null if the editor doesn't descend from JSpinner.DefaultEditor.
     */
    public JFormattedTextField getTextField(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            return ((JSpinner.DefaultEditor)editor).getTextField();
        } else {
            System.err.println("Unexpected editor type: "
                               + spinner.getEditor().getClass()
                               + " isn't a descendant of DefaultEditor");
            return null;
        }
    }
 
    /**
     * DateFormatSymbols returns an extra, empty value at the
     * end of the array of months.  Remove it.
     */
    static protected String[] getMonthStrings() {
        String[] months = new java.text.DateFormatSymbols().getMonths();
        int lastIndex = months.length - 1;
 
        if (months[lastIndex] == null
           || months[lastIndex].length() <= 0) { //last item empty
            String[] monthStrings = new String[lastIndex];
            System.arraycopy(months, 0,
                             monthStrings, 0, lastIndex);
            return monthStrings;
        } else { //last item not empty
            return months;
        }
    }
 
    static protected JSpinner addLabeledSpinner(Container c,
                                                String label,
                                                SpinnerModel model) {
        JLabel l = new JLabel(label);
        c.add(l);
 
        JSpinner spinner = new JSpinner(model);
        l.setLabelFor(spinner);
        c.add(spinner);
 
        return spinner;
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private  void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("SpinnerDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add content to the window.
        frame.add(new SimMainView(false));
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 


    private void addOkButton(Container c) {
    
         JButton okButton = new JButton("OK");
         c.add(okButton);
         okButton.setBounds(150, 160, 180, 130);
         okButton.addActionListener(new  java.awt.event.ActionListener() {
             public void actionPerformed( java.awt.event.ActionEvent event) {

             	Date date = null;
            	try {
            		date = new java.text.SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH).parse(yearModel.getValue() + "." + monthModel.getValue() + "." + dateModel.getValue());
            	} catch (ParseException e) {
            		// TODO Auto-generated catch block
            		e.printStackTrace();
            	}
            	if (controller == null) controller = new SimController("EURUSD", date );
            	else controller.setModel(date);
            	
            }
    
         });
    }
    
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
            UIManager.put("swing.boldMetal", Boolean.FALSE);
            SimMainView ds = new SimMainView(true);
            ds.createAndShowGUI();
            }
        });
    }
    
    


	
}
	
    
