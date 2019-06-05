import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.math.*;

class SimpleDrawCanvasWithFiles extends Canvas implements MouseListener, MouseMotionListener {

    //ArrayList to store imageData drawn on the canvas.
    private ArrayList<String[]> imageData;

    //Global variables for drawing
    int x, y, x2, y2;

    // Declarations of global choices
    private String penShape;
    private Color penColor;
    private JFileChooser fc;

    /**
     * Constructor for canvas
     */
    SimpleDrawCanvasWithFiles() {
        // Constructing the canvas, and allowing listening to mouse events
        // Also create an array to hold the shapes that have been drawn on
        // the canvas.
        setBackground(Color.white);
        imageData=new ArrayList<>();  // IMAGE DATA ARRAY LIST
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void setStartPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setEndPoint(int x, int y) {
        x2 = (x);
        y2 = (y);
    }

    void setPenShape(String newPenShape){
        penShape=newPenShape;
    }

    void setPenColor(Color newPenColor){
        penColor=newPenColor;
        System.out.println("The new pen colour is " + penColor);
    }

    void doClear() {
        // Clear all the drawings from the canvas.
        if(imageData.size()>0){
            imageData=new ArrayList<>();
            repaint();
        }
    }

    void doUndo() {
        if(imageData.size()>0){
            imageData.remove(imageData.size()-1);
            repaint();
        }
    }

    void doSaveToFile(Frame parentFrame) {
       //Function to save drawings to a VEC file

    }

    void doLoadFromFile(Frame parentFrame) {
        //Function to load VEC files

        fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("VEC File","VEC"));
        int returnVal = fc.showOpenDialog(SimpleDrawCanvasWithFiles.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would open the file.

            JOptionPane.showMessageDialog(null, "Insert the Drawing functionality here");
            //THE DRAWING WILL BE DONE FROM THIS POINT ONWARDS

        }

    } // end LoadFromFile()

    public void paint(Graphics g) {

        System.out.println("DEBUG: Paint Called");

        for(int i=0;i<imageData.size();i++){
            String[] singleLine=imageData.get(i);
            if(singleLine[0]=="PLOT"){
                g.drawLine(Integer.valueOf(singleLine[1]),Integer.valueOf(singleLine[2]),Integer.valueOf(singleLine[3]),Integer.valueOf(singleLine[4]));
            }
            else if(singleLine[0]=="LINE"){
                g.drawLine(Integer.valueOf(singleLine[1]),Integer.valueOf(singleLine[2]),Integer.valueOf(singleLine[3]),Integer.valueOf(singleLine[4]));
            }
            else if(singleLine[0]=="RECT"){
                g.drawRect(Integer.valueOf(singleLine[1]),Integer.valueOf(singleLine[2]),(Integer.valueOf(singleLine[3])-Integer.valueOf(singleLine[1])),(Integer.valueOf(singleLine[4])-Integer.valueOf(singleLine[2])));
            }
            else if(singleLine[0]=="ELLIPSE"){
                g.drawOval(Integer.valueOf(singleLine[1]),Integer.valueOf(singleLine[2]),(Integer.valueOf(singleLine[3])-Integer.valueOf(singleLine[1])),(Integer.valueOf(singleLine[4])-Integer.valueOf(singleLine[2])));

            }

        }
    }

    public Dimension getPreferredSize() {
        // Say what size this canvas wants to be.
        return new Dimension(500,500);
    }



    // ------------------------------------------------------------------------------------

    // The remainder of the class implements drawing of lines.  While the user
    // is drawing a line, the line is represented by a "rubber band" lines that
    // follows the mouse.  The rubber band line is drawn in XOR mode, which has
    // the property that drawing the same thing twice has no effect.  (That is,
    // the second draw operation undoes the first.)  When the user releases the
    // mouse button, the rubber band line is replaced by a regular line and is
    // added to the array.

    int startX, startY;  // When the user presses the mouse button, the
    //   location of the mouse is stored in these variables.
    int prevX, prevY;    // The most recent mouse location; a rubber band line has
    //    been drawn from (startX, startY) to (prevX, prevY).

    boolean dragging = false;  // For safety, this variable is set to true while a
    // drag operation is in progress.

    Graphics gc;  // While dragging, gc is a graphics context that can be used to
    // draw to the canvas.

    public void mousePressed(MouseEvent evt) {
        // This is called by the system when the user presses the mouse button.
        // Record the location at which the mouse was pressed.  This location
        // is one endpoint of the line that will be drawn when the mouse is
        // released.  This method is part of the MouseLister interface.
        startX = evt.getX();
        startY = evt.getY();
        prevX = startX;
        prevY = startY;
        dragging = true;
        gc = getGraphics();  // Get a graphics context for use while drawing.
        gc.setXORMode(getBackground());

        switch (penShape) {
            case "Plot" :
                gc.drawLine(startX, startY, startX, startY);
                break;
            case "Line":
                gc.drawLine(startX, startY, prevX, prevY);
                break;
            case "Rect" :
                gc.drawRect(startX, startY, Math.abs(prevX-startX), Math.abs(prevY-startY));
                break;
            case "Ellipse" :
                gc.drawOval(startX, startY, Math.abs(prevX-startX), Math.abs(prevY-startY));
                break;
        }

    }

    public void mouseDragged(MouseEvent evt) {
        // This is called by the system when the user moves the mouse while holding
        // down a mouse button.  The previously drawn rubber band line is erased by
        // drawing it a second time, and a new rubber band line is drawn from the
        // start point to the current mouse location.
        if (!dragging)  // Make sure that the drag operation has been properly started.
            return;
        else if(penShape.equals("Line")) {
            gc.drawLine(startX, startY, prevX, prevY);  // Erase the previous line.
            prevX = evt.getX();
            prevY = evt.getY();
            gc.drawLine(startX, startY, prevX, prevY);  // Draw the new line.
        }
        else if(penShape.equals("Rect")){
            gc.drawRect(startX, startY, Math.abs(prevX-startX), Math.abs(prevY-startY));
            prevX = evt.getX();
            prevY = evt.getY();
            gc.drawRect(startX, startY, Math.abs(prevX-startX), Math.abs(prevY-startY));
        }
        else if(penShape.equals("Ellipse")){
            gc.drawOval(startX, startY, Math.abs(prevX-startX), Math.abs(prevY-startY));
            prevX = evt.getX();
            prevY = evt.getY();
            gc.drawOval(startX, startY, Math.abs(prevX-startX), Math.abs(prevY-startY));
        }

    }

    public void mouseReleased(MouseEvent evt) {
        // This is called by the system when the user releases the mouse button.
        // The previously drawn rubber band line is erased by drawing it a second
        // time.  Then a permanent line is drawn in the current drawing color,
        // and is added to the array of lines.
        if (!dragging)  // Make sure that the drag operation has been properly started.
            return;

        int endX = evt.getX();  // Where the mouse was released.
        int endY = evt.getY();

        switch (penShape) {
            case "Plot" :
                gc.drawLine(startX, startY, startX, startY);
                gc.setPaintMode();
                gc.drawLine(startX, startY, startX, startY);
                gc.dispose();

                String[] newPLOT={"PLOT",String.valueOf(startX),String.valueOf(startY),String.valueOf(startX),String.valueOf(startY)};
                imageData.add(newPLOT);
                System.out.print("\n"+imageData);
                break;
            case "Line":
                gc.drawLine(startX, startY, prevX, prevY);  // Erase the previous line.
                gc.setPaintMode();
                gc.drawLine(startX, startY, endX, endY);  // Draw the permanent line in regular "paint" mode.
                gc.dispose();  // Free the graphics context, now that the draw operation is over.

                String[] newLINE = {"LINE", String.valueOf(startX), String.valueOf(startY), String.valueOf(endX), String.valueOf(endY)};
                imageData.add(newLINE);
                System.out.print("\n"+imageData);
                break;
            case "Rect" :
                gc.drawRect(startX, startY, Math.abs(prevX-startX), Math.abs(prevY-startY));
                gc.setPaintMode();
                gc.drawRect(startX, startY, Math.abs(endX-startX), Math.abs(endY-startY));
                gc.dispose();  // Free the graphics context, now that the draw operation is over.
                //Adding Info to list data
                String[] newRECT={"RECT",String.valueOf(startX),String.valueOf(startY),String.valueOf(endX),String.valueOf(endY)};
                //Since we store the cordinates in the Array
                imageData.add(newRECT);
                System.out.print("\n"+imageData);
                break;
            case "Ellipse" :
                gc.drawOval(startX, startY, Math.abs(prevX-startX), Math.abs(prevY-startY));
                gc.setPaintMode();
                gc.drawOval(startX, startY, Math.abs(endX-startX), Math.abs(endY-startY));

                String[] newELLIPSE={"ELLIPSE",String.valueOf(startX),String.valueOf(startY),String.valueOf(endX),String.valueOf(endY)};
                imageData.add(newELLIPSE);
                System.out.print("\n"+imageData);
                break;
        }

    } // end mouseReleased

    public void mouseClicked(MouseEvent evt) { }  // Other methods in the MouseListener interface
    public void mouseEntered(MouseEvent evt) { }
    public void mouseExited(MouseEvent evt) { }
    public void mouseMoved(MouseEvent evt) { }  // Required by the MouseMotionListener interface.

} // end class SimpleDrawCanvas
