import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;

public class Main implements MouseListener, MouseMotionListener, ActionListener {

    private JButton plotButton;


    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("Plot")) {
            System.out.println("PLOT"); //DEBUG
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("CLICK"); //DEBUG
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public void paint(Graphics g)
    {
        g.fillOval(100,100,200,200);
    }

    /**
     * Handles adding all elements inside frame
     * Inc:
     *  Menu Buttons
     *  Drawing Tool and Colour Tool panels and buttons
     *  Canvas for drawing
     * @param frame - The frame
     */
    private void buildUI(Frame frame) {
        //MenuBar
        JMenuBar mb = new JMenuBar();
        JMenu loadMenu = new JMenu("Load");
        JMenu saveMenu = new JMenu("Save");
        mb.add(loadMenu);
        mb.add(saveMenu);
        frame.add(BorderLayout.NORTH, mb);

        //Drawing Tool Buttons
        JPanel dtPanel = new JPanel();
        dtPanel.setBackground(Color.LIGHT_GRAY);
        dtPanel.setLayout(new BoxLayout(dtPanel, BoxLayout.PAGE_AXIS));
        frame.add(dtPanel, BorderLayout.WEST);
        // shapes
        plotButton = new JButton("Plot");
        JButton lineButton = new JButton("Line");
        dtPanel.add(plotButton);
        plotButton.addActionListener(this);
        dtPanel.add(lineButton);

        //Colour Tool Buttons
        JPanel ctPanel = new JPanel();
        ctPanel.setBackground(Color.LIGHT_GRAY);
        ctPanel.setLayout(new BoxLayout(ctPanel, BoxLayout.PAGE_AXIS));
        frame.add(ctPanel, BorderLayout.EAST);
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
        canvas.addMouseListener(this);
        frame.add(canvas);
    }

    /**
     * Creates Frame and calls buildUI
     * @param args - args default Java entry
     */
    public static void main(String[] args) {

        //Frame
        JFrame frame = new JFrame("VEC Drawing App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(400, 300);
        frame.setResizable(false);

        Main drawing = new Main();
        drawing.buildUI(frame);

        //Finalise Frame
        frame.pack();
        frame.setVisible(true);

    }

}
