package financeui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import smartUI.SmartFrame;
import smartUI.SmartPanel;
import smartUI.SmartToolBar;

public abstract class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SmartPanel panel = new SmartPanel();
		panel.setLayout(new BorderLayout());
		panel.setSize(400, 300);
		JPanel jPanel = new JPanel();
		jPanel.setSize(200, 100);
		
		
		
		panel.add(jPanel,BorderLayout.NORTH);
		
		JFrame frame = new JFrame();
		frame.getContentPane().add(panel);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}

}
