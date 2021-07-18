package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    
    private JLabel jlbFileName;
    private JTextField jtfFileName, jtfSearchFile;
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
    
    public void init() {
        
        /*
        Inicializate the variables
        */
        
        this.jlbFileName = new JLabel("File Name");
        this.jtfFileName = new JTextField();
        this.jtfSearchFile = new JTextField();
        this.btnUpload = new JButton("Upload...");
        this.btnGetFile = new JButton("Get File");
        this.btnCancel = new JButton("Exit");
        this.btnGetFileName = new JButton("Search File Name");
        this.tablePanel = new JIFTable();
        this.jtaFile = new JTextArea();

        this.jlbFileName.setBounds(420, 10, 100, 30);
        this.jtfFileName.setBounds(520, 10, 200, 30);
        this.btnGetFile.setBounds(770, 10, 100, 30);
        this.jtfSearchFile.setBounds(10, 420, 150, 30);
        this.btnGetFileName.setBounds(170, 420, 150, 30);
        this.btnUpload.setBounds(10, 460, 100, 30);
        this.btnCancel.setBounds(10, 530, 100, 30);
        this.jtaFile.setBounds(420, 60, 450, 490);
        
        this.jtaFile.setBackground(Color.lightGray);
        this.jtaFile.setEditable(false);

        this.btnUpload.addActionListener(this);
        this.btnGetFile.addActionListener(this);
        this.btnCancel.addActionListener(this);
        this.btnGetFileName.addActionListener(this);

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
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnCancel) {
            this.dispose();
        } else {
            if (e.getSource() == this.btnUpload) {
                try {
                    chooseFile();
                    this.tablePanel.clean();
                    this.tablePanel.getBooks();
                } catch (IOException ex) {
                    System.out.println("GUI.JIFBooks.actionPerformed() "+ex.toString());
                }
            }else{
                if(e.getSource() == this.btnGetFile){
                    getFile();
                    this.dispose();
                }else{
                    if(e.getSource() == this.btnGetFileName){

                    }
                }
            }
        }
    }
    
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
    
    public void sendFile(String filePath) throws IOException{

        this.book = new File(filePath);
        this.bookSize = (int) this.book.length();
        this.bookName = this.book.getName();

        MainWindow.client.send(this.bookName);
        MainWindow.client.send(String.valueOf(this.bookSize));

        Scanner s = new Scanner(book);
        String contents = "";
        
	/*
        Read line to line the file
        */
	while (s.hasNextLine()) {
            String line = s.nextLine();
            /*
            Save the line in the contents.
            */
            contents += line; 
	}
        
        System.out.println(contents);
        
        MainWindow.client.send(contents);
        
    }
    
    public void getFile(){
        this.bookName = this.jtfFileName.getText();
        this.contentBook = MainWindow.client.receiveFile(this.bookName);
        this.jtaFile.setText(contentBook);
        createBooksFolder();
        try {
            File fileContent = new File(path+"\\"+bookName+".txt");
        
            if (!fileContent.exists()) {
                fileContent.createNewFile();
            }
            
            FileWriter fw = new FileWriter(fileContent);
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(contentBook);
            }
        } catch (IOException e) {
            System.err.println("Book.Save() "+e.getMessage());
        }
        
    }
    
    public void receiveFile(){
        
        
    }
    
    public void createBooksFolder(){
        
        File file = new File("Books");
        if (!file.exists()) {
            file.mkdir();
        }
        
    }
}
