package PaintGUI;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.security.Guard;

public class GUI extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    //Declaring GUI components
    //Buttons for functionality
    private JButton plotButton,openButton,saveButton;
    //Text area to display what's happening in the system
    private JTextArea systemLogs;
    JFileChooser fileChooser;

    JMenuBar menuBar;



    //Class constructor
    public GUI(){
        super(new BorderLayout());

        //Creating buttons and attaching icons
        //Open button
        openButton=new JButton("Open VEC file",settingUpImage("/Images/open.png"));
        openButton.addActionListener(this);
        //Save button
        saveButton=new JButton("Save VEC file",settingUpImage("/Images/save.png"));
        saveButton.addActionListener(this);

        //Creating the File chooser
        fileChooser=new JFileChooser();
        //Setting file filer to select only VEC files
        fileChooser.setFileFilter(new FileNameExtensionFilter("VEC File","VEC"));

        //Creating text area for logging system
        systemLogs=new JTextArea(5,20);
        systemLogs.setMargin(new Insets(5,5,5,5));
        systemLogs.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(systemLogs);

        //Panels for the buttons
        JPanel buttonPanel=new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);

    }

    protected static ImageIcon settingUpImage(String path){
        java.net.URL URLImage=GUI.class.getResource(path);

        if(URLImage!=null){
            return new ImageIcon(URLImage);
        }
        else{
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private static void buildingAndDisplayingGUI(){
        JFrame frame = new JFrame("FileChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new GUI());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("Plot")) {
            System.out.println("PLOT"); //DEBUG
        }
        else if(e.getSource()==openButton){
            int returnVale=fileChooser.showOpenDialog(GUI.this);

            if(returnVale==JFileChooser.APPROVE_OPTION){
                File file=fileChooser.getSelectedFile();
                //Application functionality begins here
                systemLogs.append("Opening"+file.getName()+". \n");
                //SOO THE DRAWING WILL BE DONE FROM THIS POINT ONWARDS
            }

            else{
                systemLogs.append("Open command cancelled by user. \n");
            }
            systemLogs.setCaretPosition(systemLogs.getDocument().getLength());
        }
        else if(e.getSource()==saveButton){
            int returnVale=fileChooser.showOpenDialog(GUI.this);
            if(returnVale==JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                //Application functionality begins here
                systemLogs.append("Saving: " + file.getName() + ". \n");
            }
            else{
                systemLogs.append("Save command cancelled by user. \n");
            }
            systemLogs.setCaretPosition(systemLogs.getDocument().getLength());
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
//
//    public void paint(Graphics g)
//    {
//        g.fillOval(100,100,200,200);
//    }

    /**
     * Handles adding all elements inside frame
     * Inc:
     *  Menu Buttons
     *  Drawing Tool and Colour Tool panels and buttons
     *  Canvas for drawing
     * @param frame - The frame
     */
//    private void buildUI(Frame frame) {
//        //MenuBar
//        JMenuBar mb = new JMenuBar();
//        JMenu loadMenu = new JMenu("Load");
//        JMenu saveMenu = new JMenu("Save");
//        mb.add(loadMenu);
//        mb.add(saveMenu);
//        frame.add(BorderLayout.NORTH, mb);
//
//        //Drawing Tool Buttons
//        JPanel dtPanel = new JPanel();
//        dtPanel.setBackground(Color.LIGHT_GRAY);
//        dtPanel.setLayout(new BoxLayout(dtPanel, BoxLayout.PAGE_AXIS));
//        frame.add(dtPanel, BorderLayout.WEST);
//        // shapes
//        plotButton = new JButton("Plot");
//        JButton lineButton = new JButton("Line");
//        dtPanel.add(plotButton);
//        plotButton.addActionListener(this);
//        dtPanel.add(lineButton);
//
//        //Colour Tool Buttons
//        JPanel ctPanel = new JPanel();
//        ctPanel.setBackground(Color.LIGHT_GRAY);
//        ctPanel.setLayout(new BoxLayout(ctPanel, BoxLayout.PAGE_AXIS));
//        frame.add(ctPanel, BorderLayout.EAST);
//        // colours
//        JButton blackButton = new JButton("Black");
//        JButton redButton = new JButton("Red");
//        blackButton.setBackground(Color.BLACK);
//        redButton.setBackground(Color.RED);
//        ctPanel.add(blackButton);
//        ctPanel.add(redButton);
//
//        //Canvas
//        Canvas canvas = new Canvas();
//        canvas.setSize(500,500);
//        canvas.addMouseListener(this);
//        frame.add(canvas);
//    }
//
//    /**
//     * Creates Frame and calls buildUI
//     * @param args - args default Java entry
//     */
//    public static void main(String[] args) {
//
//        //Frame
//        JFrame frame = new JFrame("VEC Drawing App");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLocation(400, 300);
//        frame.setResizable(false);
//
//        GUI drawing = new GUI();
//        drawing.buildUI(frame);
//
//        //Finalise Frame
//        frame.pack();
//        frame.setVisible(true);
//
//    }
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
               // UIManager.put("swing.boldMetal", Boolean.FALSE);
                buildingAndDisplayingGUI();
            }
        });
    }
}
