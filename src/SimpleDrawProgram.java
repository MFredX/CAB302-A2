import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SimpleDrawProgram extends Frame implements ActionListener {

    // Declare new canvas
    private SimpleDrawCanvasWithFiles canvas;   // This is where the drawing is actually done

    // JButton Declarations
    private JButton colorButton;
    private JButton plotButton;
    private JButton lineButton;
    private JButton rectButton;
    private JButton ellipseButton;

    /**
     * Creates a frame belonging to the class SimpleDrawProgram
     * @param args default
     */
    public static void main(String[] args) {
        new SimpleDrawProgram();
    }

    /**
     * Constructor for frame
     * Adds menu and canvas and sets size and location
     */
    private SimpleDrawProgram() {
        // Create Canvas
        super("Simple Draw");
        setLayout(new BorderLayout());
        canvas = new SimpleDrawCanvasWithFiles();
        add(canvas,BorderLayout.CENTER);
        canvas.setPenShape("Line"); //sets default pen to Line

        // Add File Menu
        Menu fileMenu = new Menu("File",true);
        fileMenu.add("New");
        fileMenu.addSeparator();
        fileMenu.add("Save");
        fileMenu.addSeparator();
        fileMenu.add("Load");
        fileMenu.addSeparator();
        fileMenu.add("Undo");
        fileMenu.addSeparator();
        fileMenu.add("Quit");
        fileMenu.addActionListener(this);

        MenuBar mb = new MenuBar();
        setMenuBar(mb);
        mb.add(fileMenu);

        // Colour Picker Button
        colorButton=new JButton("Colour Picker");
        colorButton.addActionListener(this);
        JMenuBar jmb=new JMenuBar();
        add(jmb,BorderLayout.PAGE_START);
        jmb.add(colorButton);

        // Shapes buttons
            //plot
        plotButton=new JButton("Plot");
        plotButton.addActionListener(this);
        jmb.add(plotButton);
            //line
        lineButton=new JButton("Line");
        lineButton.addActionListener(this);
        jmb.add(lineButton);
            //rect
        rectButton=new JButton("Rect");
        rectButton.addActionListener(this);
        jmb.add(rectButton);
            //ellipse
        ellipseButton=new JButton("Ellipse");
        ellipseButton.addActionListener(this);
        jmb.add(ellipseButton);

        // Closing Functionality
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent evt) {
                        dispose();
                        System.exit(0);
                    }
                }
        );
        pack();
        show();

    } // end constructor

    /**
     *  Displays the JColourChooser GUI
     */
    private void displayColorSelection(){
        Color c = JColorChooser.showDialog(this,"Choose a Colour",Color.BLACK);
        canvas.setPenColor(c);
    }

    /**
     * Switch case for determining actions from menu and button press
     * @param evt   the event call from the menu or button
     */
    public void actionPerformed(ActionEvent evt) {

        String command = evt.getActionCommand();

        switch (command) {
            // File Menu
            case "New" :
                canvas.doClear();
                break;
            case "Save" :
                canvas.doSaveToFile(this);
                break;
            case "Load" :
                canvas.doLoadFromFile(this);
                break;
            case "Undo" :
                canvas.doUndo();
                break;
            case "Quit" :
                dispose();
                System.exit(0);
                break;
            // Buttons
            case "Colour Picker" :
                displayColorSelection();
                break;
            case "Plot" :
                canvas.setPenShape("Plot");
                break;
            case "Line" :
                canvas.setPenShape("Line");
                break;
            case "Rect" :
                canvas.setPenShape("Rect");
                break;
            case "Ellipse" :
                canvas.setPenShape("Ellipse");
                break;
        }

    } // end actionPerformed

} // end class SimpleDrawApplet