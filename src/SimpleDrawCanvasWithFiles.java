import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

class SimpleDrawCanvasWithFiles extends Canvas implements MouseListener, MouseMotionListener {
    // A canvas where the user can draw lines in various colors.

    private int currentColorIndex;  // Color that is currently being used for drawing new lines,
    // given as an index in the ColoredLine.colorList array.

    private int currentBackgroundIndex;  // Current background color, given as an index in the
    // ColoredLine.colorList array.

    private ColoredLine[] lines;    // An array to hold all the lines that have been
    private ArrayList<String[]> imageData; //ArrayList to store imageData
    //        drawn on the canvas.
    private int lineCount;   // The number of lines that are in the array.
    int x, y, x2, y2;
    private String penShape;

    SimpleDrawCanvasWithFiles() {
        // Construct the canvas, and set it to listen for mouse events.
        // Also create an array to hold the lines that are displayed on
        // the canvas.
        setBackground(Color.white);
        currentColorIndex = 0;
        currentBackgroundIndex = 12;
        //lines = new ColoredLine[1000]; //ARRAYS?????

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

    void setColorIndex(int c) {
        // Set the currentColorIndex, which is used for drawing, to c.
        // For safety, check first that it is in the range of legal indices
        // for the ColoredLine.colorList array.
        if (c >= 0 && c < ColoredLine.colorList.length)
            currentColorIndex = c;
    }

    void setBackgroundIndex(int c) {
        // Set the background color, and redraw the applet using the new background.
        if (c >= 0 && c < ColoredLine.colorList.length) {
            currentBackgroundIndex = c;
            setBackground(ColoredLine.colorList[c]);
            repaint();
        }
    }

    void doClear() {
        // Clear all the lines from the picture.
//        if (lineCount > 0) {
//          //  lines = new ColoredLine[1000]; //BACK TO SQUARE ONE
//            imageData=new ArrayList<>(); //BACK TO SQUARE ONE
//          //  lineCount = 0;
//            repaint();
//        }
        if(imageData.size()>0){
            imageData=new ArrayList<>();
            repaint();
        }
    }

    void doUndo() {
        // Remove most recently added line from the picture.
//        if (lineCount > 0) {
//            lineCount--;
//            repaint();
//        }
        if(imageData.size()>0){
            imageData.remove(imageData.size()-1);
            repaint();
        }
    }

    void doSaveToFile(Frame parentFrame) {
        // Save all the data for the current drawing to a file.
        // The file is chosen by the user using a file dialog box.
        // The parentFrame parameter is requuired to open the
        // file dialog.

        FileDialog fd;  // A file dialog box that will let the user
        // specify the output file.

        fd = new FileDialog(parentFrame, "Save to File", FileDialog.SAVE);
        fd.show();

        String fileName = fd.getFile();  // Get the file name specified by the user.

        if (fileName == null)
            return;  // User has canceled.

        String directoryName = fd.getDirectory();  // The name of the directory
        //   where the specified file is located.

        File file = new File(directoryName, fileName);  // Combine the directory name with the
        //  name to produce a usable file specification.

        PrintWriter out;  // Output stream for writing all the data for the current
        //    drawing to the file.

        try {    // Open the file.
            out = new PrintWriter( new FileWriter(file) );
        }
        catch (IOException e) {
            //new MessageDialog(parentFrame, "Error while trying to open file \"" + fileName + "\": " + e.getMessage());
            return;
        }

        // Write the data for the drawing to the file...

        out.println(currentBackgroundIndex);         // The index of the current background color.
        out.println(lineCount);                      // The number of lines in the data array.
        for (int i = 0; i < lineCount; i++) {        // Write the data for each indifvidual line.
            out.print(lines[i].x1);
            out.print(" ");
            out.print(lines[i].y1);
            out.print(" ");
            out.print(lines[i].x2);
            out.print(" ");
            out.print(lines[i].y2);
            out.print(" ");
            out.print(lines[i].colorIndex);
            out.println();
        }

        out.close();  // Close the output file.

        // Note that a PrintWriter never throws an exception. In order to make sure that
        // the date was written successfully, call the PrintWriter's checkError() method.
        // If out.checkError() returns a value of true, then an error occured while writing
        // the data and the user should be informed.

        if (out.checkError()){}
        //new MessageDialog(parentFrame,"Some error occured while trying to save data to the file.");

    } // end doSaveToFile()



    void doLoadFromFile(Frame parentFrame) {
        // Read data for a drawing from a file specified by the user in a file dialog box.
        // Assuming that the data is read successfully, discard the current data and disply
        // the drawing that was read from the file.  If the user cancels or if an error
        // occurs while the data is being read, then the current drawing will NOT be
        // discarded or changed.

        //new MessageDialog(parentFrame, "Sorry, loading is not yet implemented.  (That's your job.)");

    } // end LoadFromFile()


    public void paint(Graphics g) {
        // Redraw all the lines.
//        for (int i = 0; i < lineCount; i++) {
//            int c = lines[i].colorIndex;
//            g.setColor(ColoredLine.colorList[c]);
//            g.drawLine(lines[i].x1,lines[i].y1,lines[i].x2,lines[i].y2);
//        }
        for(int i=0;i<imageData.size();i++){
            String[] singleLine=imageData.get(i);
            if(singleLine[0]=="LINE"){
                g.drawLine(Integer.valueOf(singleLine[1]),Integer.valueOf(singleLine[2]),Integer.valueOf(singleLine[3]),Integer.valueOf(singleLine[4]));
            }
            else if(singleLine[0]=="RECT"){
                g.drawRect(Integer.valueOf(singleLine[1]),Integer.valueOf(singleLine[2]),(Integer.valueOf(singleLine[3])-Integer.valueOf(singleLine[1])),(Integer.valueOf(singleLine[4])-Integer.valueOf(singleLine[2])));
            }

        }
    }

    public Dimension getPreferredSize() {
        // Say what size this canvas wants to be.
        return new Dimension(500,400);
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
        gc.setColor(ColoredLine.colorList[currentColorIndex]);
        gc.setXORMode(getBackground());
        if (penShape=="Line"){
            gc.drawLine(startX, startY, prevX, prevY);
        }
        else if (penShape=="Rect"){
            //gc.drawRect(startX, startY, prevX, prevY);
            setStartPoint(evt.getX(), evt.getY());

        }

    }

    public void mouseDragged(MouseEvent evt) {
        // This is called by the system when the user moves the mouse while holding
        // down a mouse button.  The previously drawn rubber band line is erased by
        // drawing it a second time, and a new rubber band line is drawn from the
        // start point to the current mouse location.
        if (!dragging)  // Make sure that the drag operation has been properly started.
            return;
        else if(penShape=="Line") {
            gc.drawLine(startX, startY, prevX, prevY);  // Erase the previous line.
            prevX = evt.getX();
            prevY = evt.getY();
            gc.drawLine(startX, startY, prevX, prevY);  // Draw the new line.
        }
        else if(penShape=="Rect"){
//            gc.drawRect(startX,startY,prevX,prevY);
//            prevX = evt.getX();
//            prevY = evt.getY();
//            gc.drawRect(startX,startY,prevX,prevY);
            setEndPoint(evt.getX(), evt.getY());
            repaint();
        }
    }

    public void mouseReleased(MouseEvent evt) {
        // This is called by the system when the user releases the mouse button.
        // The previously drawn rubber band line is erased by drawing it a second
        // time.  Then a permanent line is drawn in the current drawing color,
        // and is added to the array of lines.
        if (!dragging)  // Make sure that the drag operation has been properly started.
            return;

        if(penShape=="Line") {
            gc.drawLine(startX, startY, prevX, prevY);  // Erase the previous line.
            int endX = evt.getX();  // Where the mouse was released.
            int endY = evt.getY();
            gc.setPaintMode();
            gc.drawLine(startX, startY, endX, endY);  // Draw the permanent line in regular "paint" mode.
            gc.dispose();  // Free the graphics context, now that the draw operation is over.
//            if (lineCount < lines.length) {  // Add the line to the array, if there is room.
//                lines[lineCount] = new ColoredLine();
//                lines[lineCount].x1 = startX;
//                lines[lineCount].y1 = startY;
//                lines[lineCount].x2 = endX;
//                lines[lineCount].y2 = endY;
//                lines[lineCount].colorIndex = currentColorIndex;
//                lineCount++;
//            }
            String[] newLINE = {"LINE", String.valueOf(startX), String.valueOf(startY), String.valueOf(endX), String.valueOf(endY)};
            imageData.add(newLINE);
            System.out.print(imageData);
        }
        else if(penShape=="Rect"){
            //gc.drawRect(startX, startY, prevX, prevY);
            //int endX = evt.getX();  // Where the mouse was released.
            //int endY = evt.getY();
            setEndPoint(evt.getX(), evt.getY());
            int px = Math.min(x,x2);
            int py = Math.min(y,y2);
            int pw=Math.abs(x-x2);
            int ph=Math.abs(y-y2);
            gc.drawRect(px, py, pw, ph);

            gc.setPaintMode();
            //gc.drawRect(startX, startY, prevX, prevY);
            gc.dispose();  // Free the graphics context, now that the draw operation is over.
            //Adding Info to list data
            //String[] newRECT={"RECT",String.valueOf(startX),String.valueOf(startY),String.valueOf(endX),String.valueOf(endY)};
            String[] newRECT={"RECT",String.valueOf(px),String.valueOf(py),String.valueOf(px+pw),String.valueOf(py+ph)};
            //Since we store the cordinates in the Array
            imageData.add(newRECT);
            System.out.print(imageData);
        }

    } // end mouseReleased

    public void mouseClicked(MouseEvent evt) { }  // Other methods in the MouseListener interface
    public void mouseEntered(MouseEvent evt) { }
    public void mouseExited(MouseEvent evt) { }
    public void mouseMoved(MouseEvent evt) { }  // Required by the MouseMotionListener interface.

} // end class SimpleDrawCanvas
