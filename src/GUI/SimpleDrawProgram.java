package GUI;/*
   In this simple drawing program, the user can draw lines by pressing
   the mouse button and moving the mouse before releasing the button.
   A line is drawn from the point where the mouse button is pressed to the
   point where it is released.  A choice of drawing colors is offered in a menu.
   Another menu offers a choice of background colors.  Drawings can be saved to
   files and reloaded from files.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleDrawProgram extends Frame implements ActionListener, KeyListener {

    public static void main(String[] args) {
        // The main function creates a frame belonging to the class
        // GUI.SimpleDrawProgram.
        new SimpleDrawProgram();
    }

    SimpleDrawCanvasWithFiles canvas;  // This is where the drawing is actually done.
    // This frame displayes this canvas along with a menu bar.
    JButton colorButton;
    JButton polyNButton;
    int NPoly;
    JTextField polygonNum;
    public SimpleDrawProgram() {
        // Constructor.  Create the menus and the canvas, and add them to the
        // frame.  Set the frames' size and location, and show it on the screen.
        super("Simple Draw");
        setLayout(new BorderLayout());

        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;
        JFileChooser fc;



        Menu fileMenu = new Menu("File",true);
        fileMenu.add("New");
        fileMenu.addSeparator();
        fileMenu.add("Save...");
        fileMenu.addSeparator();
        fileMenu.add("Load...");
        fileMenu.addSeparator();
        fileMenu.add("Undo");
        fileMenu.addSeparator();
        fileMenu.add("Quit");
        fileMenu.addActionListener(this);



        Menu shapesMenu = new Menu("Shapes");
        shapesMenu.add("Line");

        shapesMenu.add("Plot");

        shapesMenu.add("Rect");

        shapesMenu.add("Ellipse");

        shapesMenu.add("Polygon");

        shapesMenu.addActionListener(this);



        MenuBar mb = new MenuBar();
        mb.add(fileMenu);
        mb.add(shapesMenu);

        polygonNum=new JTextField("");
        //Button to open colour picker
        colorButton=new JButton("Select Colour");
        colorButton.addActionListener(this);
        polyNButton=new JButton("Submit");
        polyNButton.addActionListener(this);
        JLabel polyLabel=new JLabel("Enter Polygon n here:");
        JMenuBar jmb=new JMenuBar();
        jmb.add(polyLabel);
        jmb.add(polygonNum);
        jmb.add(polyNButton);
        jmb.add(colorButton);

        //Create a file chooser

        setMenuBar(mb);

        canvas = new SimpleDrawCanvasWithFiles();
        add(canvas,BorderLayout.CENTER);
        add(jmb,BorderLayout.PAGE_START);

        addWindowListener(
                new WindowAdapter() {  // Window listener object closes the window and ends the
                    //   program when the user clicks the window's close box.
                    public void windowClosing(WindowEvent evt) {
                        dispose();
                        System.exit(0);
                    }
                }
        );

        pack();
        show();

    } // end constructor

    public void displayColorSelection(){
        Color c=JColorChooser.showDialog(this,"Choose the colour your heart desires",Color.CYAN);
        canvas.setPenColor(c);
        System.out.println(c);
       // repaint();
    }

    public void setNPoly(int newNPoly){
        NPoly=newNPoly;
    }

    public void actionPerformed(ActionEvent evt) {

        String command = evt.getActionCommand();
        if(evt.getSource()==colorButton){
            displayColorSelection();
        }
        if(evt.getSource()==polyNButton){
            //Need to add exception to only allow number not string
            setNPoly(Integer.valueOf(polygonNum.getText()));
        }
        else if (command.equals("Quit")) {
            dispose();  // Close the window, then end the program.
            System.exit(0);
        }
        else if (command.equals("New"))
            canvas.doClear();
        else if (command.equals("Undo"))
            canvas.doUndo();
        else if (command.equals("Save..."))
            canvas.doSaveToFile(this);
        else if (command.equals("Load..."))
            canvas.doLoadFromFile(this);
        else if(command.equals("Plot"))    //PLOTTTTTTT
            canvas.setPenShape("Plot");
        else if(command.equals("Line"))    //LINEEEE
            canvas.setPenShape("Line");
        else if(command.equals("Rect"))    //RECTTTT
            canvas.setPenShape("Rect");
        else if(command.equals("Ellipse"))    //ELLIPSE
            canvas.setPenShape("Ellipse");
        else if(command.equals("Polygon"))    //POLYGON
            canvas.setPenShape("Polygon");


    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}




