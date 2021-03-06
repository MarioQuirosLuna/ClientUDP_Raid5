/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Graphics;
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
        String[] searchBooks;

	public JIFTable() {
		this.setLayout(null);
		this.setBounds(10, 10, 360, 400);
		init();
                getBooks();
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

        /**
         * This methos receive the name books from the server.
         * Put all in a Strin array variable.
         * And put all in the table.
         */
        public void getBooks(){
            this.books = MainWindow.client.receiveFiles();
            DefaultTableModel defaultTableModel = (DefaultTableModel) this.table.getModel();

		clean();

		for (int i = 0; i < this.books.length; i++) {

			String[] data = new String[1];
			data[0] = this.books[i];
			defaultTableModel.addRow(data);

		}
        }
        
        /**
         * This methos receive the name books from the server, they are similar at the name that we need.
         * Put all in a Strin array variable.
         * And put all in the table.
         */
        public void getSearchBooks(String searchBook){
            this.searchBooks = MainWindow.client.receiveFileName(searchBook);
            DefaultTableModel defaultTableModel = (DefaultTableModel) this.table.getModel();

		clean();

		for (int i = 0; i < this.searchBooks.length; i++) {

			String[] data = new String[1];
			data[0] = this.searchBooks[i];
			defaultTableModel.addRow(data);

		}
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
