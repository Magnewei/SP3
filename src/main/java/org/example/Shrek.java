package org.example;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Shrek extends JFrame{
    //To Play shrek movie, simply write "new Shrek();" in main.
    public Shrek() {
        // Set up the JFrame.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("GIF Player");

        // Load the GIF file and make JLabel.
        ImageIcon imageIcon = new ImageIcon("doc/4vwc2telggr21.gif");
        JLabel gifLabel = new JLabel(imageIcon);

        //Add to JFrame and center.
        add(gifLabel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
