import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends Canvas {

    public static void main(String[] args) {

        //Frame
        JFrame frame = new JFrame("VEC Drawing App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(400, 300);
        frame.setResizable(false);

        //MenuBar
        JMenuBar mb = new JMenuBar();
        JMenu loadMenu = new JMenu("Load");
        JMenu saveMenu = new JMenu("Save");
        mb.add(loadMenu);
        mb.add(saveMenu);
        frame.getContentPane().add(BorderLayout.NORTH, mb);

        //Drawing Tool Buttons
        JPanel dtPanel = new JPanel();
        dtPanel.setBackground(Color.LIGHT_GRAY);
        dtPanel.setLayout(new BoxLayout(dtPanel, BoxLayout.PAGE_AXIS));
        frame.getContentPane().add(dtPanel, BorderLayout.WEST);
            // shapes
        JButton plotButton = new JButton("Plot");
        JButton lineButton = new JButton("Line");
        dtPanel.add(plotButton);
        dtPanel.add(lineButton);

        JPanel ctPanel = new JPanel();
        ctPanel.setBackground(Color.LIGHT_GRAY);
        ctPanel.setLayout(new BoxLayout(ctPanel, BoxLayout.PAGE_AXIS));
        frame.getContentPane().add(ctPanel, BorderLayout.EAST);
            // colours
        JButton blackButton = new JButton("Black");
        JButton redButton = new JButton("Red");
        blackButton.setBackground(Color.BLACK);
        redButton.setBackground(Color.RED);
        ctPanel.add(blackButton);
        ctPanel.add(redButton);

        //Canvas
        Canvas canvas = new Canvas();
        canvas.setSize(500,500);
        frame.add(canvas);

        //Finalise Frame
        frame.pack();
        frame.setVisible(true);

        

    }

}
