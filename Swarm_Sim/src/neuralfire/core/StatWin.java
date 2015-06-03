package neuralfire.core;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Color;

import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;
import java.io.PrintWriter;

import javax.swing.Action;

public class StatWin extends JFrame {

	private JPanel contentPane;
	private JTextField DroidNo;
	private JTextField FireNo;
	private JLabel ItNo;
	private JLabel lblComplete;
	private JLabel lblMap;
	
	private final SwingAction action = new SwingAction();

	/**
	 * Create the frame.
	 */
	public StatWin(String map) {
		setTitle("Statistics");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 327, 243);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{103, 42, 60, 45};
		gbl_contentPane.rowHeights = new int[]{16, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblIteration = new JLabel("Iteration");
		lblIteration.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		GridBagConstraints gbc_lblIteration = new GridBagConstraints();
		gbc_lblIteration.gridwidth = 4;
		gbc_lblIteration.insets = new Insets(0, 0, 5, 0);
		gbc_lblIteration.gridx = 0;
		gbc_lblIteration.gridy = 0;
		contentPane.add(lblIteration, gbc_lblIteration);
		
		ItNo = new JLabel("0");
		GridBagConstraints gbc_ItNo = new GridBagConstraints();
		gbc_ItNo.gridwidth = 4;
		gbc_ItNo.insets = new Insets(0, 0, 5, 0);
		gbc_ItNo.gridx = 0;
		gbc_ItNo.gridy = 1;
		contentPane.add(ItNo, gbc_ItNo);
		
		JLabel lblDroids = new JLabel("Droids");
		GridBagConstraints gbc_lblDroids = new GridBagConstraints();
		gbc_lblDroids.insets = new Insets(0, 0, 5, 5);
		gbc_lblDroids.anchor = GridBagConstraints.EAST;
		gbc_lblDroids.gridx = 0;
		gbc_lblDroids.gridy = 3;
		contentPane.add(lblDroids, gbc_lblDroids);
		
		DroidNo = new JTextField();
		DroidNo.setEditable(false);
		GridBagConstraints gbc_DroidNo = new GridBagConstraints();
		gbc_DroidNo.insets = new Insets(0, 0, 5, 5);
		gbc_DroidNo.fill = GridBagConstraints.HORIZONTAL;
		gbc_DroidNo.gridx = 2;
		gbc_DroidNo.gridy = 3;
		contentPane.add(DroidNo, gbc_DroidNo);
		DroidNo.setColumns(10);
		
		JLabel lblFireInstances = new JLabel("Fire Instances");
		GridBagConstraints gbc_lblFireInstances = new GridBagConstraints();
		gbc_lblFireInstances.anchor = GridBagConstraints.EAST;
		gbc_lblFireInstances.insets = new Insets(0, 0, 5, 5);
		gbc_lblFireInstances.gridx = 0;
		gbc_lblFireInstances.gridy = 4;
		contentPane.add(lblFireInstances, gbc_lblFireInstances);
		
		FireNo = new JTextField();
		FireNo.setEditable(false);
		GridBagConstraints gbc_FireNo = new GridBagConstraints();
		gbc_FireNo.insets = new Insets(0, 0, 5, 5);
		gbc_FireNo.fill = GridBagConstraints.HORIZONTAL;
		gbc_FireNo.gridx = 2;
		gbc_FireNo.gridy = 4;
		contentPane.add(FireNo, gbc_FireNo);
		FireNo.setColumns(10);
		
		JButton btnEnd = new JButton("End Simulation");
		btnEnd.setAction(action);
		GridBagConstraints gbc_btnEnd = new GridBagConstraints();
		gbc_btnEnd.insets = new Insets(0, 0, 5, 0);
		gbc_btnEnd.gridwidth = 4;
		gbc_btnEnd.gridx = 0;
		gbc_btnEnd.gridy = 6;
		contentPane.add(btnEnd, gbc_btnEnd);
		
		lblComplete = new JLabel("Complete");
		lblComplete.setVisible(false);
		lblComplete.setForeground(Color.GREEN);
		lblComplete.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		GridBagConstraints gbc_lblComplete = new GridBagConstraints();
		gbc_lblComplete.insets = new Insets(0, 0, 0, 5);
		gbc_lblComplete.gridx = 1;
		gbc_lblComplete.gridy = 7;
		contentPane.add(lblComplete, gbc_lblComplete);
		
		lblMap = new JLabel(map);
		lblMap.setVisible(true);
		GridBagConstraints gbc_lblMap = new GridBagConstraints();
		gbc_lblMap.insets = new Insets(0, 0, 0, 5);
		gbc_lblMap.gridx = 2;
		gbc_lblMap.gridy = 7;
		contentPane.add(lblMap, gbc_lblMap);
	}
	
	public void updateValues(int droidNo, int fireNo, int itNo) {
		DroidNo.setText("" + droidNo);
		FireNo.setText("" + fireNo);
		ItNo.setText("" + itNo);
		
		if(fireNo == 0)
			lblComplete.setVisible(true);
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "End Simulation");
			putValue(SHORT_DESCRIPTION, "Ends the current simulation without closing the window");
		}
		public void actionPerformed(ActionEvent e) {
			System.exit(1);
		}
	}
}
