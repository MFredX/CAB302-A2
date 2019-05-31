

import java.awt.Graphics;
import javax.swing.*;

import java.awt.Frame;
import java.awt.event.*;
import java.awt.*;

//
//public class drawing extends Canvas{
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("My Drawing");
//        Canvas canvas = new drawing();
//        canvas.setSize(400, 400);
//        frame.add(canvas);
//        frame.pack();
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//
//    }
//    public void paint(Graphics g) {
//        g.setColor(Color.red);
//        g.fillOval(100, 100, 200, 200);
//        g.drawLine(45,90,97,57);
//        g.drawLine(75,75,75,75);
//        int x = 45;
//        int y = 15;
//        g.drawLine(x,y,x,y);
//
//    }
//
//
//
//}



import java.awt.*;
import java.awt.event.*;
public class drawing extends Frame implements MouseListener, MouseMotionListener
{
    int x, y;
    String str="";

    public drawing()
    {
        addMouseListener(this);
        addMouseMotionListener(this);


        setSize(300, 300);
        setVisible(true);
    }
    // override MouseListener five abstract methods
    public void mousePressed(MouseEvent e)
    {
        x = e.getX();
        y = e.getY();
        str = "Mouse Pressed";
        repaint();
    }
    public void mouseReleased(MouseEvent e)
    {
        x = e.getX();
        y = e.getY();
        str = "Mouse Released";
        repaint();
    }
    public void mouseClicked(MouseEvent e)
    {
        x = e.getX();
        y = e.getY();
        str = "Mouse Clicked";
        repaint();
    }
    public void mouseEntered(MouseEvent e)
    {
        x = e.getX();
        y = e.getY();
        str = "Mouse Entered";
        repaint();
    }
    public void mouseExited(MouseEvent e)
    {
        x = e.getX();
        y = e.getY();
        str = "Mouse Exited";
        repaint();
    }
    // override MouseMotionListener two abstract methods
    public void mouseMoved(MouseEvent e)
    {
        x = e.getX();
        y = e.getY();
        str = "Mouse Moved";
        repaint();
    }
    public void mouseDragged(MouseEvent e)
    {
        x = e.getX();
        y = e.getY();
        str = "Mouse dragged";
        repaint();
    }
    public void paint(Graphics g)
    {
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        g.fillOval(x, y, 10, 10);                 // gives the bullet
        g.drawString(x + "," + y,  x+10, y -10);  // displays the x and y position
        g.drawString(str, x+10, y+20);            // displays the action performed
    }
    public static void main(String args[])
    {
        new drawing();
        JFrame frame = new JFrame("My Drawing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}