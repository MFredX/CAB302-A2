import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class Main extends JApplet {
    /**
     * The public class main is extends JApplet which is a feature of JAVA swing public class designed for developers
     * and the Applet basically is a small application that runs within the scope of a dedicated widget engine.
     */
    public static void main(String[] args) {
        JFrame window = new JFrame("Enjoy Painting!!!");

        //System.out.println("Hello Wodjbfgjngjrld!");

        paintScreen displayStuff = new paintScreen();

        window.setContentPane (displayStuff);

        window.setLocation(250, 150);
        window.setSize(650, 500);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    /**
     * We have used init method of the applet used the Class paintScreen which displays
     * the content on the applet.
     *
     */

    public void init() {
        setContentPane(new paintScreen());
    }


    public static class paintScreen extends JPanel implements MouseMotionListener, MouseListener {

        private final static int redColour = 1;
        private final static int greenColour = 2;
        private final static int blueColour = 3;
        private final static int yellowColour = 4;
        private final static int blackColour = 5;

        private int currentColor = blackColour;
        //Selecting BLACK as the default colour

        // The next three private variables used for drawing on the screen.
        private int initialXcoord, initialYcoord;
        // The initial X and Y coordinates of the mouse before dragging and making a drawing on the page.

        private Graphics graphicsForDrawing;
        private boolean drawing;
        // This boolean is set to true whenever the user starts drawing


        /**
         * Using the constructor paintScreen which basically activates the action when
         * the user uses their mouse to make any action.
         * This constructor holds addMouseListener and addMouseMotionListener which
         * triggers the action on the basis of user's action.
         */
        paintScreen() {
            addMouseMotionListener(this);
            addMouseListener(this);
            setBackground(Color.WHITE);
        }


        /**
         * This public void paintComponent is used to draw on the panel.
         */
        public void paintComponent(Graphics b) {

            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int colorSpacing = (panelHeight - 50) / 5;


            /// Clear all button
            b.setColor(Color.lightGray);
            b.fillRect(panelWidth - 50, panelHeight - 50, 50, 50);
            b.setColor(Color.white);
            b.drawRect(panelWidth - 50, panelHeight - 50, 50, 49);
            b.drawString("Clear All", panelWidth - 48, panelHeight - 23);

            /**
             * 5 different coloured boxes drawn below
             */

            b.setColor(Color.green);
            b.fillRect( panelWidth-50, 3 + 0 * colorSpacing, 50, colorSpacing - 3);
            b.setColor(Color.blue);
            b.fillRect(panelWidth -50, 3 + 1 * colorSpacing, 50, colorSpacing - 3);
            b.setColor(Color.black);
            b.fillRect( panelWidth -50, 3 + 2 * colorSpacing, 50, colorSpacing - 3);
            b.setColor(Color.red);
            b.fillRect( panelWidth -50, 3 + 3 * colorSpacing, 50, colorSpacing - 3);
            b.setColor(Color.YELLOW);
            b.fillRect( panelWidth -50, 3 + 4 * colorSpacing, 50, colorSpacing - 3);


        }

        private void changeColor(int y) {

            int PanelHeight = getHeight();
            int PanelWidth = getWidth();

            int colorSpacing = (PanelHeight - 50) / 5;
            int newColor = y / colorSpacing;
            /// gives the information about which colour is selected from the list
            // if the number is not in the range of 1 t0 5 then it will return null
            if (newColor < 1 || newColor > 5)
                return;


            Graphics a = getGraphics();
            a.setColor(Color.orange);

            a.drawRect(PanelWidth - 48, 2 + currentColor * colorSpacing, 51, colorSpacing - 2);
            a.drawRect(PanelWidth - 49, 1 + currentColor * colorSpacing, 53, colorSpacing);


            currentColor = newColor;
            a.setColor(Color.blue);

            a.drawRect(PanelWidth - 48, 2 + currentColor * colorSpacing, 51, colorSpacing - 2);
            a.drawRect(PanelWidth - 49, 1 + currentColor * colorSpacing, 53, colorSpacing);

            a.dispose();

        }

        private void SettingUpColours() {
            graphicsForDrawing = getGraphics();
            switch (currentColor) {
                case blackColour:
                    graphicsForDrawing.setColor(Color.black);
                    break;
                case redColour:
                    graphicsForDrawing.setColor(Color.red);
                    break;
                case greenColour:
                    graphicsForDrawing.setColor(Color.green);
                    break;
                case blueColour:
                    graphicsForDrawing.setColor(Color.blue);
                    break;

                case yellowColour:
                    graphicsForDrawing.setColor(Color.YELLOW);
                    break;
            }
        }

        public void mousePressed(MouseEvent evt) {

            int x = evt.getX();
            int y = evt.getY();

            int width = getWidth();
            int height = getHeight();

            if (drawing == true)
                return;



            if (x > width - 50) {

                if (y > height - 50)
                    repaint();
                else
                    changeColor(y);
            } else if (x > 3 && x < width - 50 && y > 3 && y < height - 3) {

                initialXcoord = x;
                initialYcoord = y;
                drawing = true;
                SettingUpColours();
            }

        }



        public void mouseDragged(MouseEvent evt) {

            if (drawing == false)
                return;
            ///// Coordinates of the mouse
            int x = evt.getX();
            int y = evt.getY();

            if (x < 3)
                x = 3;
            if (x > getWidth() - 50)
                x = getWidth() - 50;

            if (y < 3)
                y = 3;
            if (y > getHeight() - 4)
                y = getHeight() - 4;

            graphicsForDrawing.drawLine(initialXcoord, initialYcoord, x, y);

            initialXcoord = x;
            initialYcoord = y;

        }
        public void mouseReleased(MouseEvent evt) {
            if (drawing == false)
                return;
            drawing = false;
            graphicsForDrawing.dispose();
            graphicsForDrawing = null;
        }


        public void mouseClicked(MouseEvent evt) {
        }

        public void mouseMoved(MouseEvent evt) {
        }

        public void mouseEntered(MouseEvent evt) {
        }

        public void mouseExited(MouseEvent evt) {
        }




    }
}

