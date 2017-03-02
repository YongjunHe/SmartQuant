package financeui;

import impl.ARBRImpl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

import service.ARBRService;
import smartUI.*;
import twaver.TWaverUtil;

import javax.swing.SwingConstants;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutionException;

import kChart.SimpleARBRChart;

public class MainFrame extends JFrame {

    private String outlookPaneXML = "/financeui/outlook.xml";
	private SmartPanel contentPane;
	private int mx, my, jfx, jfy;
	private JPanel centerPanel;
    private SmartReportPane stockTablePanel =null;
    private MarketShortcutPanel marketShortcutPanel = null;
    private JPanel containerPanel = new JPanel(new BorderLayout());
    private SmartReportPane marketTablePanel =null;
	private SmartOutlookPane outlookPane = new SmartOutlookPane();
	private SimpleARBRChart arbrChart;
	private JFreeChart chart;
	private ChartPanel arbrChartPanel;
	private Dimension maxArbrDimension,minArbrDimension,preArbrDimension;
	private boolean arbrIsMax = false;
	public static JLabel titlelabel;
	private SmartLabel areaRecommendLabel; 


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JWindow window = new JWindow();
					window.setVisible(true);
					window.setLayout(new BorderLayout());
					window.setSize(439, 439);
					window.setLocation(480, 150);
					
					JLabel imag=new JLabel(new ImageIcon("presentation/images/72635b6a"
							+ "gw1evyx78ucqqg20c70c70tz.gif"));
					window.getContentPane().add(imag);
					
					new SwingWorker<Boolean, JFrame>() {

						@Override
						protected Boolean doInBackground() throws Exception {
							// TODO Auto-generated method stub
							System.out.println("doinbac");
							MainFrame frame = new MainFrame();
							frame.setVisible(true);
							frame.repaint();
							
							return true;
						}

						@Override
						protected void process(List<JFrame> chunks) {
							// TODO Auto-generated method stub
							
							super.process(chunks);
						}

						@Override
						protected void done() {
							// TODO Auto-generated method stub
							System.out.println("done");
							window.dispose();
							super.done();
						}
						
						
					}.execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		InitGlobalFont(new Font("黑体", Font.PLAIN, 13));  //统一设置字体
		
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		setBounds(240, 50, 900, 620);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SmartCloseButton closebutton = new SmartCloseButton();
		
		contentPane = new SmartPanel();
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				if(getExtendedState()!=JFrame.MAXIMIZED_BOTH){
					setLocation(jfx + (e.getXOnScreen() - mx), jfy + (e.getYOnScreen() - my));	
				}

			}
		});
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(getExtendedState()!=JFrame.MAXIMIZED_BOTH){
					mx = e.getXOnScreen();
					my = e.getYOnScreen();
					jfx = getX();
					jfy = getY();
				}
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		SmartMaxButton maximizeButton = new SmartMaxButton();
		maximizeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (getExtendedState() == JFrame.MAXIMIZED_BOTH) {
					setExtendedState(JFrame.NORMAL);
				} else {
					setExtendedState(JFrame.MAXIMIZED_BOTH);
				}
			}
		});
		
		SmartMiniButton minimizeButton = new SmartMiniButton();
		minimizeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setExtendedState(JFrame.ICONIFIED);
			}
		});
		
		centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		
		JLabel lblNewLabel = new JLabel("");
		
		titlelabel = new JLabel("市场");
		titlelabel.setFont(new Font("黑体", Font.PLAIN, 17));
		titlelabel.setHorizontalAlignment(SwingConstants.LEADING);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(titlelabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 692, Short.MAX_VALUE)
					.addComponent(minimizeButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(maximizeButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(closebutton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 890, Short.MAX_VALUE)
				.addComponent(centerPanel, GroupLayout.DEFAULT_SIZE, 890, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addComponent(minimizeButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(maximizeButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(closebutton, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
						.addComponent(titlelabel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(centerPanel, GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
		);
		
//		GroupLayout gl_centerPanel = new GroupLayout(centerPanel);
//		gl_centerPanel.setHorizontalGroup(
//			gl_centerPanel.createParallelGroup(Alignment.LEADING)
//				.addGap(0, 890, Short.MAX_VALUE)
//		);
//		gl_centerPanel.setVerticalGroup(
//			gl_centerPanel.createParallelGroup(Alignment.LEADING)
//				.addGap(0, 534, Short.MAX_VALUE)
//		);
		centerPanel.setLayout(new BorderLayout());
		contentPane.setLayout(gl_contentPane);

		initSwing();
		try {
			initArBr();
			initOutlookPane();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "网络故障，请检查网络连接", "错误",JOptionPane.WARNING_MESSAGE);
			e1.printStackTrace();
		}
		initColor(Color.WHITE);
		
	}
	
	
	private void initSwing(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(TWaverUtil.getImage("/free/test/logo.png"));
        
        outlookPane.setBackground(new Color(255, 255, 255));
        centerPanel.add(outlookPane,BorderLayout.WEST);
        
        
	}
	
	private void initOutlookPane() throws Exception{
		

		MouseListener barListener =new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	SmartOutlookBar bar = (SmartOutlookBar) e.getSource();
            	outlookPane.changeIcon(outlookPane.getComponentZOrder(bar));
            	if(bar.getTitle().equals("市场")){
            		titlelabel.setText("市场");
            		containerPanel.removeAll();
                    
            		
            		containerPanel.add(marketTablePanel, BorderLayout.CENTER);
            		containerPanel.add(marketShortcutPanel, BorderLayout.SOUTH);
                    containerPanel.add(arbrChartPanel,BorderLayout.EAST);
                    setArbrFixed();
                    arbrIsMax = false;
            		centerPanel.add(containerPanel, BorderLayout.CENTER);
            		

            	    containerPanel.repaint();
            	    containerPanel.updateUI();
            	}else if(bar.getTitle().equals("个股")){
            		if(titlelabel.getText().equals("市场")){
            			titlelabel.setText("宇通客车");
                		containerPanel.removeAll();

                		try {
							stockTablePanel = StockTablePanel.createStockPage("sh600066");
						} catch(Exception e1){
							JOptionPane.showMessageDialog(null, "网络连接错误", "错误",JOptionPane.WARNING_MESSAGE);
							e1.printStackTrace();
							System.out.println("Mainframe300");
							containerPanel.removeAll();
							containerPanel.repaint();
							containerPanel.updateUI();
						}
                		containerPanel.add(stockTablePanel, BorderLayout.CENTER);

                		centerPanel.add(containerPanel, BorderLayout.CENTER);
                	    containerPanel.repaint();
                	    containerPanel.updateUI();
            		}
            	}
 
            }
		};
		
		
		marketTablePanel = MarketTablePanel.createMarketTablePanel(containerPanel, outlookPane);
		marketShortcutPanel = new MarketShortcutPanel(containerPanel, outlookPane);
		
	    maxArbrDimension = arbrChartPanel.getMaximumSize();
		minArbrDimension = arbrChartPanel.getMinimumSize();
		preArbrDimension = arbrChartPanel.getPreferredSize();

		
		arbrChartPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(arbrIsMax){
					setArbrFixed();
					containerPanel.removeAll();
					containerPanel.add(marketTablePanel, BorderLayout.CENTER);
			        containerPanel.add(marketShortcutPanel, BorderLayout.SOUTH);
			        containerPanel.add(arbrChartPanel,BorderLayout.EAST);
					containerPanel.updateUI();
					arbrIsMax = false;
				}else{
					setArbrMax();
					containerPanel.removeAll();
					ARBRService arbrService = ARBRImpl.create();
					areaRecommendLabel = new SmartLabel(arbrService.getRecommend());
					containerPanel.add(arbrChartPanel, BorderLayout.CENTER);
					containerPanel.add(areaRecommendLabel, BorderLayout.SOUTH);
					containerPanel.updateUI();
					arbrIsMax = true;

				}
			}
		});
		
		containerPanel.add(marketTablePanel, BorderLayout.CENTER);
        containerPanel.add(marketShortcutPanel, BorderLayout.SOUTH);
        containerPanel.add(arbrChartPanel,BorderLayout.EAST);
        setArbrFixed();
        arbrIsMax = false;
		centerPanel.add(containerPanel, BorderLayout.CENTER);
	    containerPanel.repaint();
	    containerPanel.updateUI();
	    
	    
		outlookPane.loadOutlookPane(outlookPaneXML, barListener);
	}
	
	private void initArBr(){
		arbrChart = new SimpleARBRChart();
		chart = arbrChart.ARBRChart();
		arbrChartPanel = new ChartPanel(chart);
	}
	
	private void initColor(Color color){
		centerPanel.setBackground(Color.white);
		containerPanel.setOpaque(false);
		outlookPane.setOpaque(false);
		arbrChartPanel.setOpaque(false);
	}
	
	private void setArbrFixed(){
		Dimension arbrDimension = new Dimension(250,200);
		arbrChartPanel.setMaximumSize(arbrDimension);
		arbrChartPanel.setMinimumSize(arbrDimension);
		arbrChartPanel.setPreferredSize(arbrDimension);
	}
	
	private void setArbrMax(){
		arbrChartPanel.setMaximumSize(maxArbrDimension);
		arbrChartPanel.setMinimumSize(minArbrDimension);
		arbrChartPanel.setPreferredSize(preArbrDimension);
	}
	
//	
//	private void setArbrFixed(){
//		Dimension arbrDimension = new Dimension(250,200);
//		arbrChartPanel.setMaximumSize(arbrDimension);
//		arbrChartPanel.setMinimumSize(arbrDimension);
//		arbrChartPanel.setPreferredSize(arbrDimension);
//	}
//	
//	private void setArbrMax(){
//		arbrChartPanel.setMaximumSize(maxArbrDimension);
//		arbrChartPanel.setMinimumSize(minArbrDimension);
//		arbrChartPanel.setPreferredSize(preArbrDimension);
//	}
	
	 /** 
	* 统一设置字体，父界面设置之后，所有由父界面进入的子界面都不需要再次设置字体 
	*/  
	private static void InitGlobalFont(Font font) {  
	  FontUIResource fontRes = new FontUIResource(font);  
	  for (Enumeration<Object> keys = UIManager.getDefaults().keys();  
	  keys.hasMoreElements(); ) {  
	  Object key = keys.nextElement();  
	  Object value = UIManager.get(key);  
	  if (value instanceof FontUIResource) {  
	  UIManager.put(key, fontRes);  
	  }  
	  }  
	}
}
