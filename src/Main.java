import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {

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
        frame.add(dtPanel);
            // shapes
        JButton plotButton = new JButton("Plot");
        JButton lineButton = new JButton("Line");
        dtPanel.add(plotButton);
        dtPanel.add(lineButton);
            // colours
        JButton blackButton = new JButton("Black");
        JButton redButton = new JButton("Red");
        blackButton.setBackground(Color.BLACK);
        redButton.setBackground(Color.RED);
        dtPanel.add(blackButton);
        dtPanel.add(redButton);

        //Canvas Label
        JLabel canvasLabel = new JLabel();
        canvasLabel.setOpaque(true);
        canvasLabel.setBackground(Color.WHITE);
        canvasLabel.setPreferredSize(new Dimension(500,500));
        frame.getContentPane().add(canvasLabel, BorderLayout.SOUTH);

        //Finalise Frame
        frame.pack();
        frame.setVisible(true);

        //Action Listeners
        // plot
        plotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        //line
        lineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        //black
        blackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        //red
        redButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

}
