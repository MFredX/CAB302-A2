package DataProcessing;
import java.util.ArrayList;

public class DrawVEC {

    public static void ReadAndDraw(ArrayList<String[]> FileData)  {
        //Loop through the parametrised list
        for (String[] line : FileData) {
                //Loop through the VEC line arrays in the list
                for (int i = 0; i < line.length; i++) {
                    if (line[i].equals("LINE")) {
                        System.out.println("There is a line here lads");
                    }
                    System.out.println(line[i]);
                }
            }


    }



}
