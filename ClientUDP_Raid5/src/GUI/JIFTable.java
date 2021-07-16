/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Utility.MyUtility;
import java.awt.Graphics;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Isabel
 */
public class JIFTable extends JPanel{
    
    JScrollPane scroll;
	JTable table;
	String[] books;

	public JIFTable() {
		this.setLayout(null);
		this.setBounds(10, 10, 360, 400);
		
		init();
		this.setVisible(true);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		repaint();
	}

	public void init() {

		String[] colName = { "File Name" };
		if (table == null) {
			table = new JTable() {
				@Override
				public boolean isCellEditable(int nRow, int nCol) {
					return false;
				}
			};
		}

		DefaultTableModel contactTableModel = (DefaultTableModel) table.getModel();
		contactTableModel.setColumnIdentifiers(colName);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		table.setBounds(0, 0, 359, 400);

		scroll = new JScrollPane(table);
		scroll.setBounds(0, 0, 359, 400);
		scroll.setVisible(true);
		this.add(scroll);

	}

	public void clean() {
		DefaultTableModel tb = (DefaultTableModel) this.table.getModel();
		int a = this.table.getRowCount() - 1;
		for (int i = a; i >= 0; i--) {
			tb.removeRow(tb.getRowCount() - 1);
		}
	}

	public JTable getTable() {
		return this.table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}
    
}
