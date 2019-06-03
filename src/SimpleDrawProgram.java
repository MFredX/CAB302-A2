/*
   In this simple drawing program, the user can draw lines by pressing
   the mouse button and moving the mouse before releasing the button.
   A line is drawn from the point where the mouse button is pressed to the
   point where it is released.  A choice of drawing colors is offered in a menu.
   Another menu offers a choice of background colors.  Drawings can be saved to
   files and reloaded from files.
*/

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SimpleDrawProgram extends Frame implements ActionListener {

    public static void main(String[] args) {
        // The "main program" simply creates a frame belonging to the class
        // SimpleDrawProgram.  From then on, the frame takes care of itself.
        new SimpleDrawProgram();
    }

    SimpleDrawCanvasWithFiles canvas;  // This is where the drawing is actually done.
    // This frame displayes this canvas along with a menu bar.

    public SimpleDrawProgram() {
        // Constructor.  Create the menus and the canvas, and add them to the
        // frame.  Set the frames' size and location, and show it on the screen.
        super("Simple Draw");

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

        Menu colorMenu = new Menu("Line Color",true);
        colorMenu.add("Black");
        colorMenu.add("Gray");
        colorMenu.add("Red");
        colorMenu.add("Green");
        colorMenu.add("Blue");
        colorMenu.add("Dark Red");
        colorMenu.add("Dark Green");
        colorMenu.add("Dark Blue");
        colorMenu.add("Cyan");
        colorMenu.add("Magenta");
        colorMenu.add("Yellow");
        colorMenu.add("Brown");
        colorMenu.add("White");
        colorMenu.addActionListener(this);


        Menu shapesMenu = new Menu("Shapes");
        shapesMenu.add("Line");

        shapesMenu.add("Plot");

        shapesMenu.add("Rect");

        shapesMenu.add("Ellipse");

        shapesMenu.add("Polygon");

        shapesMenu.addActionListener(this);

        JTextField polygonNum=new JTextField("Enter Text");

        MenuBar mb = new MenuBar();
        mb.add(fileMenu);
        mb.add(colorMenu);
        mb.add(shapesMenu);





        //Create a file chooser



        setMenuBar(mb);

        canvas = new SimpleDrawCanvasWithFiles();
        add("Center",canvas);

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

    public void actionPerformed(ActionEvent evt) {
        // A menu command has bee given by the user.  Respond by calling
        // the appropriate method in the canvas (except in the case of the
        // Quit command, which is handled by ending the program).

        String command = evt.getActionCommand();

        if (command.equals("Quit")) {
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


    } // end actionPerformed


} // end class SimpleDrawApplet




