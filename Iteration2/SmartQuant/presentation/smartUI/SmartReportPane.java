package smartUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.net.SocketException;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import kChart.SimpleATRChart;
import kChart.SimpleKChart;
import kChart.SimpleSummaryChart;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import enums.Cyc;
import enums.MarketType;
import financeui.MarketTablePanel;
import smartUI.SmartLabel;
import smartUI.SmartScrollPane;
import smartUI.SmartTable;
import smartUI.SmartToolBar;

public class SmartReportPane extends JPanel{
    private SmartTable table = new SmartTable();
    private SmartScrollPane scrollPane = new SmartScrollPane(table);
    private JPanel containPanel = new JPanel(new BorderLayout());
    private ChartPanel chartpanel;
    private SmartToolBar toolPanel = new SmartToolBar();
	private SmartToolBar leftToolBar,rightToolBar;

    private Color descriptionBackgroundColor = Color.WHITE;
    
    
    public SmartReportPane(){
    	setBackground(Color.WHITE);
    	init();
    }
    
    private void init(){
		leftToolBar = new SmartToolBar();
		rightToolBar = new SmartToolBar();

		toolPanel.setLayout(new BorderLayout());
		toolPanel.add(leftToolBar,BorderLayout.WEST);

		toolPanel.add(rightToolBar,BorderLayout.EAST);

		scrollPane.setBackground(Color.WHITE);
		table.setBackground(Color.WHITE);

		containPanel.setBackground(Color.WHITE);

		
		
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));	
        this.setLayout(new BorderLayout());
        containPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(containPanel,BorderLayout.CENTER);
        
        JLabel lbCorner = new JLabel();
        lbCorner.setOpaque(true);
        lbCorner.setBackground(descriptionBackgroundColor);
        this.scrollPane.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, lbCorner);
        
        
        this.add(toolPanel, BorderLayout.NORTH);
        
    }
    
    public JTable getTable(){
    	return table;
    }
    
    public SmartToolBar getLeftToolBar(){
    	return leftToolBar;
    }
    
    public SmartToolBar getRightToolBar(){
    	return rightToolBar;
    }
    
    public void showKchart(String idNumber, String beginDate, String endDate,Cyc cyc) throws Exception{
    	

        containPanel.removeAll();
		try {
			SimpleKChart kchart = new SimpleKChart();
			JFreeChart chart = kchart.kChart(idNumber, beginDate, endDate,cyc);
			chartpanel = new ChartPanel(chart);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		containPanel.add(chartpanel, BorderLayout.CENTER);
		containPanel.updateUI();
    	
    }
    
    public void showKchartOfSummary(MarketType type, String beginDate,String endDate, Cyc cyc) throws Exception{
    	containPanel.removeAll();


		try {    	
	    	SimpleSummaryChart kChart= new SimpleSummaryChart();			
			JFreeChart chart = kChart.SummaryChart(type, beginDate, endDate, cyc);
	    	chartpanel = new ChartPanel(chart);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		containPanel.add(chartpanel, BorderLayout.CENTER);
		containPanel.updateUI();
    }
    
    public void showATR(String idNumber, String beginDate, String endDate){
    	SmartTextArea textArea = new SmartTextArea();
    	containPanel.removeAll();
    	try {
			SimpleATRChart atrChart = new SimpleATRChart();
			JFreeChart chart =atrChart.ATRChart(idNumber, beginDate, endDate);
			chartpanel = new ChartPanel(chart);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	textArea.setText("均幅指标无论是从下向上穿越移动平均线，还是从上向下穿越移动平均线时，都是一种研判信号。它表示股价运行趋势有可能发生逆转，具体如何转变需结合趋势类指标进行综合研判。");
    	textArea.setLineWrap(true);
    	containPanel.add(chartpanel, BorderLayout.CENTER);
    	containPanel.add(textArea, BorderLayout.SOUTH);
    	containPanel.updateUI();
    }
    public void showGraph(){
    	containPanel.removeAll();
    	containPanel.add(scrollPane, BorderLayout.CENTER);
    	containPanel.updateUI();
    }
    
    
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
//		g2.setPaint(new GradientPaint(0, 0, new Color(3, 200, 100), getWidth(), 
//				getHeight(), new Color(23, 220, 120)));


		g2.setColor(new Color(219, 207, 202));
		g2.drawLine(getWidth()-1, 0, getWidth()-1, getHeight());
	}
	

       
}
