package financeui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import impl.StockCheck;
import impl.SummaryCheck;

import javax.naming.InitialContext;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import enums.MarketType;
import message.StockDateNode;
import message.SummaryDateNode;
import service.StockCheckService;
import service.SummaryCheckService;
import smartUI.SmartLabel;
import smartUI.SmartOutlookPane;

public class MarketShortcutPanel extends JPanel{
	
	private SmartLabel titleLabel;
	private SmartLabel summaryLabel;
	private SummaryCheckService scs;
	private JPanel containerPanel;
	private SmartOutlookPane outlookPane;

	
	public MarketShortcutPanel(JPanel containerPanel, SmartOutlookPane outlookPane) throws Exception {
		// TODO Auto-generated constructor stub
		this.containerPanel = containerPanel;
		this.outlookPane = outlookPane;
		this.setOpaque(false);
	    scs=SummaryCheck.create();
	    
		init();
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
//		g2.setPaint(new GradientPaint(0, 0, new Color(3, 200, 100), getWidth(), 
//				getHeight(), new Color(23, 220, 120)));

//		g2.setColor(new Color(255, 255, 255, 195));
//		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setColor(new Color(219, 207, 202));
		g2.drawLine(0, 0, getWidth()-1, 0);
	}
	
	
	private void init() throws Exception{
		String title = "沪深300：";
		titleLabel = new SmartLabel(title);
		
    	Date date=new Date();
    	DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    	String nowTime=format.format(date);
    	date.setMonth(date.getMonth()-1);
    	String beginTime=format.format(date);
    	
    	
        Iterator iterator = scs.getSummaryMessage(MarketType.hs300, beginTime, nowTime);
        
        while(iterator.hasNext()){
        	SummaryDateNode node = (SummaryDateNode) iterator.next();
        	if(!iterator.hasNext()){
        		String summaryString;
        		summaryString = "开盘价："+node.getOpen()+" 收盘价："+node.getClose();
        		summaryLabel = new SmartLabel(summaryString);
        	}
        }
        
        JPanel summaryPanel =new JPanel();
        summaryPanel.setOpaque(false);
        summaryPanel.add(titleLabel);
        summaryPanel.add(summaryLabel);
        this.setLayout(new BorderLayout());
        this.add(summaryPanel,BorderLayout.WEST);
        
        summaryPanel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		// TODO Auto-generated method stub
        		MainFrame.titlelabel.setText("沪深300");
            	outlookPane.changeIcon(1);   //更改图标至个股
        		containerPanel.removeAll();
        		try {
					containerPanel.add(MarketPanel.createMarketPage("sh000300", "沪深300", MarketType.hs300));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "网络故障，请检查网络连接", "错误",JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				}
        		
        		containerPanel.repaint();
        		containerPanel.updateUI();
        	}
		});
        
        
		
	}
}
