package GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;


class SimpleDrawCanvasWithFiles extends Canvas implements MouseListener, MouseMotionListener {
    // A canvas where the user can draw lines in various colors.

    private ColoredLine[] lines;    // An array to hold all the lines that have been
    private ArrayList<String[]> imageData; //ArrayList to store imageData drawn on the canvas.

    int x, y, x2, y2;


    private String penShape;
    private Color penColor=Color.BLACK;
    private JFileChooser fc;

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
        repaint();
        penColor=newPenColor;
        Graphics gc;
        gc=getGraphics();
        gc.setColor(penColor);
        System.out.println("The new pen colour is"+penColor);
        repaint();

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
            //SOO THE DRAWING WILL BE DONE FROM THIS POINT ONWARDS


        }

    } // end LoadFromFile()


    public void paint(Graphics g) {
        g.setColor(penColor);
        for(int i=0;i<imageData.size();i++){
            String[] singleLine=imageData.get(i);
            if(singleLine[0]=="PLOT"){
                g.drawOval(Integer.valueOf(singleLine[1]),Integer.valueOf(singleLine[2]),(Integer.valueOf(singleLine[3])-Integer.valueOf(singleLine[1])),(Integer.valueOf(singleLine[4])-Integer.valueOf(singleLine[2])));
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
            else if(singleLine[0]=="POLYGON"){

            }

        }

    }

    public Dimension getPreferredSize() {
        // Say what size this canvas wants to be.
        return new Dimension(500,400);
    }

    //Upon the press of the mouse the location of the mouse is stored in the following variables
    int startX, startY;

    //Recent mouse location is stored in these variables
    int prevX, prevY;

    //Boolean value to keep tack the drag operation
    boolean dragging = false;

    Graphics gc;  // While dragging, gc is a graphics context that can be used to
    // draw to the canvas.

    public void mousePressed(MouseEvent evt) {
        //Recording locations when mouse is pressed
        startX = evt.getX();
        startY = evt.getY();
        prevX = startX;
        prevY = startY;
        dragging = true;
        gc = getGraphics();  // Setting up a graphics content to conduct drawing
        gc.setXORMode(getBackground());
        if(penShape=="Plot") {
            gc.setPaintMode();
            gc.drawOval(startX, startY, 1, 1);
            gc.dispose();  // Free the graphics context, now that the draw operation is over.
            String[] newPLOT = {"PLOT", String.valueOf(startX), String.valueOf(startY), String.valueOf(startX + 1), String.valueOf(startY + 1)};
            imageData.add(newPLOT);
        }
        else if (penShape=="Line"){

            gc.drawLine(startX, startY, prevX, prevY);
        }
        else if (penShape=="Rect"){
            gc.drawRect(startX, startY, Math.abs(prevX-startX), Math.abs(prevY-startY));
        }
        else if(penShape=="Ellipse"){
            gc.drawOval(startX, startY, Math.abs(prevX-startX), Math.abs(prevY-startY));
        }
        else if(penShape=="Polygon"){
        }

    }

    public void mouseDragged(MouseEvent evt) {
        //Allowing drawing to be dynamic while the mouse is moved across the screen

        //Checking if dragging is in process
        if (!dragging)
            return;
        else if(penShape=="Line") {
            gc.drawLine(startX, startY, prevX, prevY);  // Erase the previous line.
            prevX = evt.getX();
            prevY = evt.getY();
            gc.drawLine(startX, startY, prevX, prevY);  // Draw the new line.
        }
        else if(penShape=="Rect"){
            gc.drawRect(startX, startY, Math.abs(prevX-startX), Math.abs(prevY-startY));
            prevX = evt.getX();
            prevY = evt.getY();
            gc.drawRect(startX, startY, Math.abs(prevX-startX), Math.abs(prevY-startY));
        }
        else if(penShape=="Ellipse"){
            gc.drawOval(startX, startY, Math.abs(prevX-startX), Math.abs(prevY-startY));
            prevX = evt.getX();
            prevY = evt.getY();
            gc.drawOval(startX, startY, Math.abs(prevX-startX), Math.abs(prevY-startY));
        }
        else if(penShape=="Polygon"){

        }

    }

    public void mouseReleased(MouseEvent evt) {
        ///Drawing when mouse is released
        if (!dragging)  // Checking if dragging is occurring
            return;

        if(penShape=="Line") {
            gc.drawLine(startX, startY, prevX, prevY);  // Erase the previous line.
            int endX = evt.getX();  // Where the mouse was released.
            int endY = evt.getY();
            gc.setPaintMode();
            gc.drawLine(startX, startY, endX, endY);  // Draw the permanent line in regular "paint" mode.
            gc.dispose();  // Free the graphics context, now that the draw operation is over.
//
            String[] newLINE = {"LINE", String.valueOf(startX), String.valueOf(startY), String.valueOf(endX), String.valueOf(endY)};
            imageData.add(newLINE);
            System.out.print(imageData);
        }
        else if(penShape=="Rect"){
            gc.drawRect(startX, startY, Math.abs(prevX-startX), Math.abs(prevY-startY));
            int endX = evt.getX();  // Where the mouse was released.
            int endY = evt.getY();
            gc.setPaintMode();
            gc.drawRect(startX, startY, Math.abs(endX-startX), Math.abs(endY-startY));
            gc.dispose();  // Free the graphics context, now that the draw operation is over.
            //Adding Info to list data
            String[] newRECT={"RECT",String.valueOf(startX),String.valueOf(startY),String.valueOf(endX),String.valueOf(endY)};
            //Since we store the cordinates in the Array
            imageData.add(newRECT);
            System.out.print(imageData);
        }
        else if(penShape=="Ellipse"){
            gc.drawOval(startX, startY, Math.abs(prevX-startX), Math.abs(prevY-startY));
            int endX = evt.getX();  // Where the mouse was released.
            int endY = evt.getY();
            gc.setPaintMode();
            gc.drawOval(startX, startY, Math.abs(endX-startX), Math.abs(endY-startY));

            String[] newELLIPSE={"ELLIPSE",String.valueOf(startX),String.valueOf(startY),String.valueOf(endX),String.valueOf(endY)};
            imageData.add(newELLIPSE);
            System.out.print(imageData);
        }
        else if(penShape=="Polygon"){

        }

    } // end mouseReleased

    public void mouseClicked(MouseEvent evt) { }  // Other methods in the MouseListener interface
    public void mouseEntered(MouseEvent evt) { }
    public void mouseExited(MouseEvent evt) { }
    public void mouseMoved(MouseEvent evt) { }  // Required by the MouseMotionListener interface.

} // end class SimpleDrawCanvas
