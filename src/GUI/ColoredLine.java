package GUI;

import java.awt.*;

class ColoredLine {  // an object of this class represents a colored line segment

    public static final Color[] colorList = {
            // List of available colors; colors are always indicated as
            // indices into this array.
            Color.black, Color.gray, Color.red, Color.green, Color.blue,
            new Color(200,0,0), new Color(0,180,0), new Color(0,0,180),
            Color.cyan, Color.magenta, Color.yellow, new Color(120,80,20),
            Color.white
    };

    int x1, y1;   // One endpoint of the line segment.
    int x2, y2;   // The other endpoint of the line segment.
    int colorIndex;  // The color of the line segment, given as an index in the colorList array.

} // end class GUI.ColoredLine
