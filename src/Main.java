import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.security.Guard;

public class Main implements MouseListener, MouseMotionListener, ActionListener {

    /**
     * Global Variables
     */
    int canvasDimension = 500;
    int mouseX;
    int mouseY;
    enum DrawingTool {
        PLOT,
        LINE
    }

    /**
     * Set defaults
     */
    DrawingTool selectedTool = DrawingTool.PLOT;

    /**
     * Global Buttons
     */
    private JButton plotButton;
    private JButton lineButton;
    private JButton blackButton;
    private JButton redButton;
    private JButton greenButton;
    private JButton blueButton;
    private JButton yellowButton;

    private JMenuItem loadMenu;
    private JMenuItem saveMenu;

    private JFileChooser fileChooser;

    private JFrame fileFrame;


    /**
     * Button handling
     * Takes the actionEvent, determines which button has been pressed and calls the appropriate function
     * @param e the event input
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Load":
                System.out.println("LOAD"); //DEBUG
                load();
                break;
            case "Save":
                System.out.println("SAVE"); //DEBUG
                save();
                break;
            case "Plot":
                System.out.println("PLOT"); //DEBUG
                selectedTool = DrawingTool.PLOT;
                break;
            case "Line":
                System.out.println("LINE"); //DEBUG
                selectedTool = DrawingTool.LINE;
                break;
            case "Black":
                System.out.println("BLACK"); //DEBUG
                break;
            case "Red":
                System.out.println("RED"); //DEBUG
                break;
            case "Green":
                System.out.println("GREEN"); //DEBUG
                break;
            case "Blue":
                System.out.println("BLUE"); //DEBUG
                break;
            case "Yellow":
                System.out.println("YELLOW"); //DEBUG
                break;
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
        mouseX = e.getX();
        mouseY = e.getY();
        switch (selectedTool) {
            case PLOT:
                System.out.println("PLOT DRAW"); //DEBUG
                break;
            case LINE:
                System.out.println("LINE DRAW"); //DEBUG
                break;
        }
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

    /**
     *
     */
    private void load() {
        int returnVale=fileChooser.showOpenDialog(fileFrame);
        if(returnVale==JFileChooser.APPROVE_OPTION){
            File file=fileChooser.getSelectedFile();
            System.out.println("Opening"+file.getName()+"."); //THE DRAWING WILL BE DONE FROM THIS POINT ONWARDS
        }
        else{
            System.out.println("Open command cancelled by user.");
        }
    }

    /**
     *
     */
    private void save() {
        int returnVale=fileChooser.showOpenDialog(fileFrame);
        if(returnVale==JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            System.out.println("Saving: " + file.getName() + ". \n");
        }
        else{
            System.out.println("Save command cancelled by user. \n");
        }
    }

    /**
     * Handles adding all elements inside frame
     * Inc:
     *  Menu Buttons
     *  Drawing Tool and Colour Tool panels and buttons
     *  Canvas for drawing
     * @param frame the frame
     */
    private void buildUI(Frame frame) {
        //MenuBar
        JMenuBar mb = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        frame.add(BorderLayout.NORTH, mb);
        mb.add(fileMenu);
        loadMenu = new JMenuItem("Load");
        saveMenu = new JMenuItem("Save");
        fileMenu.add(loadMenu);
        fileMenu.add(saveMenu);
        loadMenu.addActionListener(this);
        saveMenu.addActionListener(this);

        //FileChoose
        fileChooser=new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("VEC File","VEC"));

        //Drawing Tool Buttons
        JPanel dtPanel = new JPanel();
        dtPanel.setBackground(Color.LIGHT_GRAY);
        dtPanel.setLayout(new BoxLayout(dtPanel, BoxLayout.PAGE_AXIS));
        frame.add(dtPanel, BorderLayout.WEST);
        // buttons
        plotButton = new JButton("Plot");
        lineButton = new JButton("Line");
        dtPanel.add(plotButton);
        dtPanel.add(lineButton);
        plotButton.addActionListener(this);
        lineButton.addActionListener(this);

        //Colour Tool Buttons
        JPanel ctPanel = new JPanel();
        ctPanel.setBackground(Color.LIGHT_GRAY);
        ctPanel.setLayout(new BoxLayout(ctPanel, BoxLayout.PAGE_AXIS));
        frame.add(ctPanel, BorderLayout.EAST);
        // buttons
        blackButton = new JButton("Black");
        redButton = new JButton("Red");
        greenButton = new JButton("Green");
        blueButton = new JButton("Blue");
        yellowButton = new JButton("Yellow");
        blackButton.setBackground(Color.BLACK);
        redButton.setBackground(Color.RED);
        greenButton.setBackground(Color.GREEN);
        blueButton.setBackground(Color.BLUE);
        blackButton.setBackground(Color.YELLOW);
        ctPanel.add(blackButton);
        ctPanel.add(redButton);
        ctPanel.add(greenButton);
        ctPanel.add(blueButton);
        ctPanel.add(yellowButton);
        blackButton.addActionListener(this);
        redButton.addActionListener(this);
        greenButton.addActionListener(this);
        blueButton.addActionListener(this);
        yellowButton.addActionListener(this);

        //Canvas
        Canvas canvas = new Canvas();
        canvas.setSize(canvasDimension,canvasDimension);
        canvas.addMouseListener(this);
        frame.add(canvas);
    }

    /**
     * Creates Frame and calls buildUI
     * @param args args default Java entry
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
