package Package;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by Matball on 2017-05-14.
 */
public class CodeMorsea extends JFrame {
    /*public static void main(){
        System.out.println("Choose option and write");
        System.out.println("1.English alphabet to Morse'a Code");
        System.out.println("1.Morse'a Code to English alphabet");


        int a;


    }*/

    public static void main(String[]args)throws IOException {
        EventQueue.invokeLater(() -> {
            MainFrame mainframe = new MainFrame();
        });
    }
}
