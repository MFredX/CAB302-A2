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

    //ArrayList to store imageData drawn on the canvas.
    private ArrayList<String[]> imageData;

    // Mouse Drag Locations
    int startX, startY;
    int prevX, prevY;

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

    void setPenShape(String newPenShape){
        penShape=newPenShape;
    }

    void setPenColor(Color newPenColor){
        penColor = newPenColor;
        System.out.println("The new pen colour is " + penColor);
    }

    void doClear() {
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
        String fileString = "";

        for (int i=0;i<imageData.size();i++) {
            String[] singleLine=imageData.get(i);
            for (int j=0; j<singleLine.length; j++) {
                fileString = fileString + singleLine[j] + " ";
            }
            fileString = fileString + "\r\n";

        }

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

    void doLoadFromFile(Frame parentFrame) {
        doClear();

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

                int startX, startY, endX, endY;
                gc = getGraphics();  // Get a graphics context for use while drawing.
                gc.setXORMode(getBackground());

                for (String[] line : FileData) {
                    for (int i = 0; i < line.length; i++) {
                        System.out.println(line[i]);
                        switch (line[i]) {
                            case "PLOT" :
                                startX = Integer.valueOf(line[i+1]);
                                startY = Integer.valueOf(line[i+2]);
                                String[] newPLOT={"PLOT",String.valueOf(startX),String.valueOf(startY),String.valueOf(startX),String.valueOf(startY)};
                                imageData.add(newPLOT);
                                break;
                            case "LINE":
                                startX = Integer.valueOf(line[i+1]);
                                startY = Integer.valueOf(line[i+2]);
                                endX = Integer.valueOf(line[i+3]);
                                endY = Integer.valueOf(line[i+4]);
                                String[] newLINE = {"LINE", String.valueOf(startX), String.valueOf(startY), String.valueOf(endX), String.valueOf(endY)};
                                imageData.add(newLINE);
                                break;
                            case "RECT" :
                                startX = Integer.valueOf(line[i+1]);
                                startY = Integer.valueOf(line[i+2]);
                                endX = Integer.valueOf(line[i+3]);
                                endY = Integer.valueOf(line[i+4]);
                                String[] newRECT={"RECT",String.valueOf(startX),String.valueOf(startY),String.valueOf(endX),String.valueOf(endY)};
                                imageData.add(newRECT);
                                break;
                            case "ELLIPSE" :
                                startX = Integer.valueOf(line[i+1]);
                                startY = Integer.valueOf(line[i+2]);
                                endX = Integer.valueOf(line[i+3]);
                                endY = Integer.valueOf(line[i+4]);
                                String[] newELLIPSE={"ELLIPSE",String.valueOf(startX),String.valueOf(startY),String.valueOf(endX),String.valueOf(endY)};
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

    public void paint(Graphics g) {

        for(int i=0;i<imageData.size();i++){
            String[] singleLine=imageData.get(i);
            switch (singleLine[0]) {
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

    public Dimension getPreferredSize() {
        return new Dimension(500,500);
    }

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

    public void mouseReleased(MouseEvent evt) {
        if (!dragging)
            return;

        int endX = evt.getX();
        int endY = evt.getY();

        switch (penShape) {
            case "Plot" :
                String[] newPLOT={"PLOT",String.valueOf(startX),String.valueOf(startY),String.valueOf(startX),String.valueOf(startY)};
                imageData.add(newPLOT);
                break;
            case "Line":
                String[] newLINE = {"LINE", String.valueOf(startX), String.valueOf(startY), String.valueOf(endX), String.valueOf(endY)};
                imageData.add(newLINE);
                break;
            case "Rect" :
                String[] newRECT={"RECT",String.valueOf(startX),String.valueOf(startY),String.valueOf(endX),String.valueOf(endY)};
                imageData.add(newRECT);
                break;
            case "Ellipse" :
                String[] newELLIPSE={"ELLIPSE",String.valueOf(startX),String.valueOf(startY),String.valueOf(endX),String.valueOf(endY)};
                imageData.add(newELLIPSE);
                break;
        }
        repaint();

    } // end mouseReleased

    public void mouseClicked(MouseEvent evt) { }  // Other methods in the MouseListener interface
    public void mouseEntered(MouseEvent evt) { }
    public void mouseExited(MouseEvent evt) { }
    public void mouseMoved(MouseEvent evt) { }  // Required by the MouseMotionListener interface.

} // end class SimpleDrawCanvas
