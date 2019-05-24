import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class Main extends JApplet{
    /**
     * The public class main is extends JApplet which is a feature of JAVA swing public class designed for developers
     * and the Applet basically is a small application that runs within the scope of a dedicated widget engine.
     */
    public static void main(String[] args) {
        JFrame window= new JFrame("Enjoy Painting!!!");
        //System.out.println("Hello Wodjbfgjngjrld!");
        //SimplePaintPanel content = new SimplePaintPanel();
        // window.setContentPane(content);

        window.setLocation(250,150);
        window.setSize(650,500);
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setVisible(true);
    }
/*
    public void controlPanel(){setContentPane(new SimplePaintPanel());
    }

    */

}