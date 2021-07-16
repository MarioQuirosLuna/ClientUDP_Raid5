package GUI;

import Client.Client;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Isabel
 */
public class MainWindow extends JFrame implements ActionListener{
    
    /*
    Create the attributes for the menu in the main window.
    */
    private JMenuBar jMenuBar;
    private JMenu jMenuBooks;
    private JMenuItem jmiBooks;
    public static JDesktopPane desktop;  
    public static Client client;

    public MainWindow(Client client) {
        super();
        
        /*
        Get the client and set propierties dor the window
        */
        
        this.client = client;
        this.setSize(1000, 770);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        init();
        this.setVisible(true);
    }
    
    public void init() {

        /*
        Inicializate the attributes
        */
        
        this.jMenuBooks = new JMenu("RAID 5");
        this.jMenuBar = new JMenuBar();
        this.jmiBooks = new JMenuItem("Digital Books");
        this.desktop = new JDesktopPane();

        this.jMenuBar.setBounds(0, 0, this.getWidth(), 20);
        this.desktop.setBounds(0, 20, this.getWidth(), getHeight());

        this.jmiBooks.addActionListener(this);

        this.jMenuBooks.add(this.jmiBooks);

        this.jMenuBar.add(this.jMenuBooks);

        this.add(this.jMenuBar);
        this.add(this.desktop);

    }// init
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.jmiBooks) {
            /*
            If the boton books is pressed, go to the JIFBooks
            */
            JIFBooks jifBooks = new JIFBooks();
            this.desktop.add(jifBooks);
        }
    }
    
}
