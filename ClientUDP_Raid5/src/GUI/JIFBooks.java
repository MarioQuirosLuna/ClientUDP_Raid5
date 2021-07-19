package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Isabel
 */
public class JIFBooks extends JInternalFrame implements ActionListener{
    
    /*
    Create the window variables
    */
    
    private JLabel jlbFileName, jlbMetaName, jlbMetaSize;
    private JTextField jtfFileName, jtfSearchFile, jtfMetaName, jtfMetaSize;
    private JButton btnUpload, btnGetFile, btnCancel, btnGetFileName;
    private JFileChooser jchooser = null;
    private JIFTable tablePanel;
    private JTextArea jtaFile;
    
    private File book = null;
    private int bookSize = 0;
    private String bookName = "";
    private String contentBook = "";
    
    private final String path;
    
    

    public JIFBooks() {
        
        /*
        Set the window properties
        */
        
        super("| Digital Books | Client | ");
        this.path = "Books";
	this.setSize(900, 600);
        this.setLocation(40, 50);
        this.setLayout(null);
        this.setResizable(false);
        init();
        this.setVisible(true);
    }
    
    /**
     * This method inicializate all the variables.
     * And put it on the window.
     */
    public void init() {
        
        /*
        Inicializate the variables
        */
        
        this.jlbFileName = new JLabel("File Name");
        this.jlbMetaSize = new JLabel("File Size");
        this.jlbMetaName = new JLabel("File Name");
        this.jtfMetaName = new JTextField();
        this.jtfMetaSize = new JTextField();
        this.jtfFileName = new JTextField();
        this.jtfSearchFile = new JTextField();
        this.btnUpload = new JButton("Upload...");
        this.btnGetFile = new JButton("Get File");
        this.btnCancel = new JButton("Exit");
        this.btnGetFileName = new JButton("Search File Name");
        this.tablePanel = new JIFTable();
        this.jtaFile = new JTextArea();

        
        /**
        * Personalization.
        */
        this.jlbFileName.setBounds(420, 10, 100, 30);
        this.jtfFileName.setBounds(520, 10, 200, 30);
        this.btnGetFile.setBounds(770, 10, 100, 30);
        this.jtfSearchFile.setBounds(10, 420, 150, 30);
        this.btnGetFileName.setBounds(170, 420, 150, 30);
        this.btnUpload.setBounds(10, 460, 100, 30);
        this.btnCancel.setBounds(10, 530, 100, 30);
        this.jlbMetaName.setBounds(420, 60, 100, 30);
        this.jtfMetaName.setBounds(520, 60, 200, 30);
        this.jlbMetaSize.setBounds(420, 110, 100, 30);
        this.jtfMetaSize.setBounds(520, 110, 200, 30);
        this.jtaFile.setBounds(420, 150, 450, 400);
        
        this.jtaFile.setBackground(Color.lightGray);
        this.jtaFile.setEditable(false);
        this.jtaFile.setLineWrap(true);
        this.jtfMetaName.setEditable(false);
        this.jtfMetaSize.setEditable(false);

        /**
         * Add listeners to the buttons
         */
        this.btnUpload.addActionListener(this);
        this.btnGetFile.addActionListener(this);
        this.btnCancel.addActionListener(this);
        this.btnGetFileName.addActionListener(this);

        /**
         * Add it to the window
         */
        this.add(this.jlbMetaName);
        this.add(this.jlbMetaSize);
        this.add(this.jtfMetaName);
        this.add(this.jtfMetaSize);
        this.add(this.jlbFileName);
        this.add(this.jtfFileName);
        this.add(this.btnUpload);
        this.add(this.btnGetFile);
        this.add(this.btnGetFileName);
        this.add(this.btnCancel);
        this.add(this.jtaFile);
        this.add(this.jtfSearchFile);
        this.add(tablePanel);
	}
    
    /**
     * This method make action by the listener buttons
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //Cancel button close the window.
        if (e.getSource() == this.btnCancel) {
            this.dispose();
        } else {
            //Upload botton open a choose book window, clean the table and show the books.
            if (e.getSource() == this.btnUpload) {
                try {
                    chooseFile();
                    this.tablePanel.clean();
                    this.tablePanel.getBooks();
                } catch (IOException ex) {
                    System.out.println("GUI.JIFBooks.actionPerformed() "+ex.toString());
                }
            }else{
                //This button get the file name and send to the server an action for get it.
                if(e.getSource() == this.btnGetFile){
                    getFile();
                }else{
                    //This button send an action for get all the book names.
                    if(e.getSource() == this.btnGetFileName){
                        this.tablePanel.clean();
                        this.tablePanel.getSearchBooks(jtfSearchFile.getText());
                    }
                }
            }
        }
    }
    
    
    /**
     * This method make possible to choose a file.
     * Send the action to the server for notify that will send a file.
     * And call at the method that send it.
     * @throws IOException 
     */
    public void chooseFile() throws IOException{
        this.jchooser = new JFileChooser();
        this.jchooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = this.jchooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Choosed File "+ this.jchooser.getSelectedFile().getAbsolutePath());
            MainWindow.client.stockFile();
            sendFile(this.jchooser.getSelectedFile().getAbsolutePath());
        }
    }
    
    /**
     * This method get the properties of this file, send all of them.
     * Join all the file content in a string.
     * And send it.
     * @param filePath
     * @throws IOException 
     */
    public void sendFile(String filePath) throws IOException{

        this.book = new File(filePath);
        this.bookSize = (int) this.book.length();
        this.bookName = this.book.getName();

        MainWindow.client.send(this.bookName);
        MainWindow.client.send(String.valueOf(this.bookSize));

        String contents = "";
        
	/*
        Read line to line the file
        */
        FileReader fr = null;
        try {
            fr = new FileReader(book);
            BufferedReader br = new BufferedReader(fr);

            String linea;
            while((linea=br.readLine())!=null){
                contents += linea;
            }
        }catch(IOException e){
            System.out.println("Book.read(): "+e.getMessage());
        }finally{
            try{                    
                if( null != fr ){   
                    fr.close();     
                }                  
            }catch (IOException e2){ 
                System.out.println("Book.read(): "+e2.getMessage());
            }
        }
        
        System.out.println(contents);
        
        MainWindow.client.send(contents);
        
    }
    
    /**
     * This method get the file name.
     * Send at the server the action, the file name and receive the content file.
     * Put it content in a text area to show it.
     * Show the metadata.
     * And save it in a file in the computer.
     */
    public void getFile(){
        this.bookName = this.jtfFileName.getText();
        this.contentBook = MainWindow.client.receiveFile(this.bookName);
        this.jtaFile.setText(contentBook);
        this.jtfMetaName.setText(bookName);
        this.jtfMetaSize.setText(String.valueOf(contentBook.length()));
        createBooksFolder();
        FileWriter fw = null;
        try {
            File fileContent = new File(path+"\\"+bookName+".txt");
        
            if (!fileContent.exists()) {
                fileContent.createNewFile();
            }
            
            fw = new FileWriter(fileContent);
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(contentBook);
            }
        } catch (IOException e) {
            System.err.println("JIFBooks.GetFile() "+e.getMessage());
        }finally{
            try{                    
                if( null != fw ){   
                    fw.close();     
                }                  
            }catch (IOException e2){ 
                System.out.println("JIFBooks.GetFile(): "+e2.getMessage());
            }
        }
        
    }
    
    public void receiveFile(){
        
        
    }
    
    /**
     * This method create the place where are going to stay all the files/books
     */
    public void createBooksFolder(){
        
        File file = new File("Books");
        if (!file.exists()) {
            file.mkdir();
        }
        
    }
}
