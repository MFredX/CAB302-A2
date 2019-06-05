import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.math.*;

class SimpleDrawCanvasWithFiles extends Canvas implements MouseListener, MouseMotionListener {

    //ArrayList to store data
    private ArrayList<String[]> imageData;  // Printable on canvas
    private ArrayList<String[]> scaledData = new ArrayList<>(); // For saving and loading to file

    // Mouse Drag Locations
    int startX, startY;
    int prevX, prevY;
    double scaledStartX, scaledStartY;
    double scaledEndX, scaledEndY;

    // Dragging Boolean
    boolean dragging = false;

    // Declare Graphics Contect
    Graphics gc;

    // Declarations of global choices
    private String penShape;
    private Color penColor;
    private JFileChooser fc;
    private JFileChooser fs;

    /**
     * Constructor for canvas
     */
    SimpleDrawCanvasWithFiles() {
        setBackground(Color.white);
        imageData = new ArrayList<>();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * Sets pen shape
     * @param newPenShape shape to be drawn by pen
     */
    void setPenShape(String newPenShape){
        penShape=newPenShape;
    }

    /**
     * Sets pen colour
     * @param newPenColor colour to be drawn by pen
     */
    void setPenColor(Color newPenColor){
        penColor = newPenColor;
        String hex = "#"+Integer.toHexString(penColor.getRGB()).substring(2);
        String[] newLINE = {"PEN", hex};
        imageData.add(newLINE);
        scaledData.add(newLINE);
    }

    /**
     * Clears the array lists and repaints
     */
    void doClear() {
        if(imageData.size()>0){
            imageData = new ArrayList<>();
            scaledData = new ArrayList<>();
            repaint();
        }
    }

    /**
     * Removes the most recent action
     */
    void doUndo() {
        if(imageData.size()>0){
            imageData.remove(imageData.size()-1);
            scaledData.remove(scaledData.size()-1);
            repaint();
        }
    }

    /**
     * Saves the canvas to file
     * @param parentFrame   frame
     */
    void doSaveToFile(Frame parentFrame) {

        // Converts all of scaledData to a string
        String fileString = "";
        for (int i=0;i<scaledData.size();i++) {
            String[] singleLine=scaledData.get(i);
            for (int j=0; j<singleLine.length; j++) {
                fileString = fileString + singleLine[j] + " ";
            }
            fileString = fileString + "\r\n";
        }

        // Opens file chooser and saves string to VEC file
        fs = new JFileChooser();
        fs.setFileFilter(new FileNameExtensionFilter("VEC File","VEC"));
        fs.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = fs.showSaveDialog(SimpleDrawCanvasWithFiles.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                FileWriter fw = new FileWriter(fs.getSelectedFile()+".VEC");
                fw.write(fileString);
                fw.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * Loads from file to array list to be drawn
     * @param parentFrame   frame
     */
    void doLoadFromFile(Frame parentFrame) {

        doClear(); // clears current context

        // Opens a file chooser to select VEC file
        fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("VEC File","VEC"));
        int returnVal = fc.showOpenDialog(SimpleDrawCanvasWithFiles.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String st;
                ArrayList<String[]> FileData = new ArrayList<>();
                while ((st = br.readLine()) != null) {
                    FileData.add(st.split("\\s"));
                }

                double startX, startY, endX, endY;
                int scaledStartX, scaledStartY, scaledEndX, scaledEndY;
                gc = getGraphics();
                gc.setXORMode(getBackground());

                // Converts VEC file to arrayList for drawing and future saving
                for (String[] line : FileData) {
                    for (int i = 0; i < line.length; i++) {
                        switch (line[i]) {
                            case "PEN" :
                                String[] newPEN = {"PEN", line[i+1]};
                                imageData.add(newPEN);
                                scaledData.add(newPEN);
                                break;
                            case "PLOT" :
                                startX = Double.valueOf(line[i+1]);
                                startY = Double.valueOf(line[i+2]);
                                String[] newScaledPLOT={"PLOT",String.valueOf(startX),String.valueOf(startY),String.valueOf(startX),String.valueOf(startY)};
                                scaledData.add(newScaledPLOT);
                                scaledStartX = (int) (startX * 500);
                                scaledStartY = (int) (startY * 500);
                                String[] newPLOT = {"PLOT", String.valueOf(scaledStartX), String.valueOf(scaledStartY), String.valueOf(scaledStartX), String.valueOf(scaledStartY)};
                                imageData.add(newPLOT);
                                break;
                            case "LINE":
                                startX = Double.valueOf(line[i+1]);
                                startY = Double.valueOf(line[i+2]);
                                endX = Double.valueOf(line[i+3]);
                                endY = Double.valueOf(line[i+4]);
                                String[] newScaledLINE = {"LINE", String.valueOf(startX), String.valueOf(startY), String.valueOf(endX), String.valueOf(endY)};
                                scaledData.add(newScaledLINE);
                                scaledStartX = (int) (startX * 500);
                                scaledStartY = (int) (startY * 500);
                                scaledEndX = (int) (endX * 500);
                                scaledEndY = (int) (endY * 500);
                                String[] newLINE = {"LINE", String.valueOf(scaledStartX), String.valueOf(scaledStartY), String.valueOf(scaledEndX), String.valueOf(scaledEndY)};
                                imageData.add(newLINE);
                                break;
                            case "RECT" :
                                startX = Double.valueOf(line[i+1]);
                                startY = Double.valueOf(line[i+2]);
                                endX = Double.valueOf(line[i+3]);
                                endY = Double.valueOf(line[i+4]);
                                String[] newScaledRECT={"RECT",String.valueOf(startX),String.valueOf(startY),String.valueOf(endX),String.valueOf(endY)};
                                scaledData.add(newScaledRECT);
                                scaledStartX = (int) (startX * 500);
                                scaledStartY = (int) (startY * 500);
                                scaledEndX = (int) (endX * 500);
                                scaledEndY = (int) (endY * 500);
                                String[] newRECT = {"RECT", String.valueOf(scaledStartX), String.valueOf(scaledStartY), String.valueOf(scaledEndX), String.valueOf(scaledEndY)};
                                imageData.add(newRECT);
                                break;
                            case "ELLIPSE" :
                                startX = Double.valueOf(line[i+1]);
                                startY = Double.valueOf(line[i+2]);
                                endX = Double.valueOf(line[i+3]);
                                endY = Double.valueOf(line[i+4]);
                                String[] newScaledELLIPSE={"ELLIPSE",String.valueOf(startX),String.valueOf(startY),String.valueOf(endX),String.valueOf(endY)};
                                scaledData.add(newScaledELLIPSE);
                                scaledStartX = (int) (startX * 500);
                                scaledStartY = (int) (startY * 500);
                                scaledEndX = (int) (endX * 500);
                                scaledEndY = (int) (endY * 500);
                                String[] newELLIPSE = {"ELLIPSE", String.valueOf(scaledStartX), String.valueOf(scaledStartY), String.valueOf(scaledEndX), String.valueOf(scaledEndY)};
                                imageData.add(newELLIPSE);
                                break;
                        }
                        repaint();
                    }
                }
            }
            catch (FileNotFoundException e){
                System.err.println("File not found");
            }
            catch (IOException e) {
                System.err.println("Unable to read the file.");
            }
        }

    } // end LoadFromFile()

    /**
     * Draws everything from imageData arraylist
     * @param g graphics context
     */
    public void paint(Graphics g) {

        for(int i=0;i<imageData.size();i++){
            String[] singleLine=imageData.get(i);
            switch (singleLine[0]) {
                case "PEN" :
                    Color drawColour = Color.decode(singleLine[1]);
                    g.setColor(drawColour);
                    break;
                case "PLOT" :
                    g.drawLine(Integer.valueOf(singleLine[1]),Integer.valueOf(singleLine[2]),Integer.valueOf(singleLine[3]),Integer.valueOf(singleLine[4]));
                    break;
                case "LINE" :
                    g.drawLine(Integer.valueOf(singleLine[1]),Integer.valueOf(singleLine[2]),Integer.valueOf(singleLine[3]),Integer.valueOf(singleLine[4]));
                    break;
                case "RECT" :
                    g.drawRect(Integer.valueOf(singleLine[1]),Integer.valueOf(singleLine[2]),(Integer.valueOf(singleLine[3])-Integer.valueOf(singleLine[1])),(Integer.valueOf(singleLine[4])-Integer.valueOf(singleLine[2])));
                    break;
                case "ELLIPSE" :
                    g.drawOval(Integer.valueOf(singleLine[1]),Integer.valueOf(singleLine[2]),(Integer.valueOf(singleLine[3])-Integer.valueOf(singleLine[1])),(Integer.valueOf(singleLine[4])-Integer.valueOf(singleLine[2])));
                    break;
            }
        }

    }

    /**
     * gets preferred size and sets it to 500 by 500
     * @return returns size dimensions
     */
    public Dimension getPreferredSize() {
        return new Dimension(500,500);
    }

    /**
     * Event handler for mouse pressed
     * @param evt   the event
     */
    public void mousePressed(MouseEvent evt) {
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

    /**
     * Event handler for mouse dragged
     * Shows the guide for drawing tools
     * @param evt   the event
     */
    public void mouseDragged(MouseEvent evt) {
        if (!dragging)
            return;

        switch (penShape) {
            case "Line" :
                gc.drawLine(startX, startY, prevX, prevY);  // Erase the previous line.
                prevX = evt.getX();
                prevY = evt.getY();
                gc.drawLine(startX, startY, prevX, prevY);  // Draw the new line.
                break;
            case "Rect" :
                gc.drawRect(startX, startY, Math.abs(prevX-startX), Math.abs(prevY-startY));
                prevX = evt.getX();
                prevY = evt.getY();
                gc.drawRect(startX, startY, Math.abs(prevX-startX), Math.abs(prevY-startY));
                break;
            case "Ellipse" :
                gc.drawOval(startX, startY, Math.abs(prevX-startX), Math.abs(prevY-startY));
                prevX = evt.getX();
                prevY = evt.getY();
                gc.drawOval(startX, startY, Math.abs(prevX-startX), Math.abs(prevY-startY));
                break;
        }

    }

    /**
     * Event handler for mouse released
     * Saves shape to arrayLists
     * @param evt   the event
     */
    public void mouseReleased(MouseEvent evt) {
        if (!dragging)
            return;

        int endX = evt.getX();
        int endY = evt.getY();
        double scaledStartX = startX / 500.0;
        double scaledStartY = startY / 500.0;
        double scaledEndX = endX / 500.0;
        double scaledEndY = endY / 500.0;
        String stScaledStartX = String.format("%.2f", scaledStartX);
        String stScaledStartY = String.format("%.2f", scaledStartY);
        String stScaledEndX = String.format("%.2f", scaledEndX);
        String stScaledEndY = String.format("%.2f", scaledEndY);

        switch (penShape) {
            case "Plot" :
                String[] newPLOT={"PLOT",String.valueOf(startX),String.valueOf(startY),String.valueOf(startX),String.valueOf(startY)};
                imageData.add(newPLOT);
                String[] newScaledPLOT={"PLOT",stScaledStartX,stScaledStartY};
                scaledData.add(newScaledPLOT);
                break;
            case "Line":
                String[] newLINE = {"LINE", String.valueOf(startX), String.valueOf(startY), String.valueOf(endX), String.valueOf(endY)};
                imageData.add(newLINE);
                String[] newScaledLINE={"LINE",stScaledStartX,stScaledStartY, stScaledEndX, stScaledEndY};
                scaledData.add(newScaledLINE);
                break;
            case "Rect" :
                String[] newRECT={"RECT",String.valueOf(startX),String.valueOf(startY),String.valueOf(endX),String.valueOf(endY)};
                imageData.add(newRECT);
                String[] newScaledRECT={"RECT",stScaledStartX,stScaledStartY, stScaledEndX, stScaledEndY};
                scaledData.add(newScaledRECT);
                break;
            case "Ellipse" :
                String[] newELLIPSE={"ELLIPSE",String.valueOf(startX),String.valueOf(startY),String.valueOf(endX),String.valueOf(endY)};
                imageData.add(newELLIPSE);
                String[] newScaledELLIPSE={"ELLIPSE",stScaledStartX,stScaledStartY, stScaledEndX, stScaledEndY};
                scaledData.add(newScaledELLIPSE);
                break;
        }
        repaint();

    } // end mouseReleased

    // Other methods in the MouseListener interface
    public void mouseClicked(MouseEvent evt) { }
    public void mouseEntered(MouseEvent evt) { }
    public void mouseExited(MouseEvent evt) { }
    public void mouseMoved(MouseEvent evt) { }

} // end class SimpleDrawCanvas
