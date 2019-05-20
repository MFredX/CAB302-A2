import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class FramesAndPanels extends JFrame implements ActionListener,Runnable {
    public static final int WIDTH = 300;
    public static final int HEIGHT = 200;


    public FramesAndPanels(String title) throws HeadlessException {
        super(title);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void run() {

    }
}
