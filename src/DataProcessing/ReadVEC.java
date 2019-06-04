package DataProcessing;

import java.io.*;
import java.util.ArrayList;


public class ReadVEC {

    public static ArrayList<String[]> ReadFileContents(String selectedVEC) throws IOException {

        try {
            File file = new File(selectedVEC);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            //Declaring an array list to hold the contents of the VEC file
            ArrayList<String[]> FileData = new ArrayList<>();

            while ((st = br.readLine()) != null) {
                //Splitting the string lines returning an array
                //List<String> st_split = new ArrayList<>(Arrays.asList(st.split("\\s")));

                //System.out.println(st_split);
                FileData.add(st.split("\\s"));

            }
//Tests
//            System.out.println(FileData);
//
//            for (String[] line : FileData) {
//                for (int i = 0; i < line.length; i++) {
//                    if (line[i].equals("LINE")) {
//                        System.out.println("There is a line here lads");
//                    }
//                    System.out.println(line[i]);
//                }
//            }

            return FileData;
        }

        catch (FileNotFoundException e){
            System.err.println("File not found");
            return null;
        }
        catch (IOException e) {
            System.err.println("Unable to read the file.");
            return null;
        }

    }

//    public static void main (String args[])throws Exception{
//        ReadFileContents("C:\\Users\\sachi\\Documents\\Example1.VEC");
//    }

}
