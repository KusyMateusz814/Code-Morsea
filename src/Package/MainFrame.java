package Package;


import org.w3c.dom.Document;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matball on 2017-05-14.
 */
public class MainFrame extends JFrame  implements ActionListener{
    JButton MorseaToEnglish;
    JButton EnglishToMorsea;
    JButton Translate;

    JLabel UppWriteUser;
    JLabel UppScore;
    JTextField WriteUser;
    JTextField Score;

    Map<String,String>map_English_Morsea=new HashMap<String,String>(200);
    Map<String,String>map_Morsea_English=new HashMap<String,String>(200);
    String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    boolean choosean_translate=true; //domyslnie tlumaczy angielski na morsea
    String string_to_translate="WpiszTekst";
    String string_after_translate="Wynik";
    String string_convert="";
    public MainFrame() {

        createMap();

        this.setSize(350,350);
        this.setTitle("Translator English - Morsea");
        this.setLayout(null);
        //
        EnglishToMorsea = new JButton("EnglishToMorsea");
        EnglishToMorsea.setFont(new Font("Serif", Font.PLAIN, 10));
        EnglishToMorsea.setBounds(10, 10, 150, 25);
        //EnglishToMorsea.setVisible(true);
        this.add(EnglishToMorsea);
        EnglishToMorsea.addActionListener(this);
        //
        MorseaToEnglish = new JButton("MorseaToEnglish");
        MorseaToEnglish.setFont(new Font("Serif", Font.PLAIN, 10));
        MorseaToEnglish.setBounds(150, 10, 150, 25);
       // MorseaToEnglish.setVisible(true);
        this.add(MorseaToEnglish);
        MorseaToEnglish.addActionListener(this);
        //
        UppWriteUser = new JLabel("English");
        UppWriteUser.setFont(new Font("Serif", Font.PLAIN, 10));
        UppWriteUser.setBounds(150, 50, 150, 25);
        this.add( UppWriteUser);
        //UppWriteUser.addActionListener(this);

        //
        WriteUser= new JTextField("Draw");
        WriteUser.setFont(new Font("Serif", Font.PLAIN, 20));
        WriteUser.setBounds(10, 70, 310, 30);
       // WriteUser.setVisible(true);
        this.add(WriteUser);
        WriteUser.addActionListener(this);
        //
        Translate= new JButton ("Translate");
        Translate.setFont(new Font("Serif", Font.PLAIN, 15));
        Translate.setBounds(100, 120, 125, 30);
       // Translate.setVisible(true);
        this.add(Translate);
        Translate.addActionListener(this);
        //
        UppScore = new JLabel("Morsea");
        UppScore.setFont(new Font("Serif", Font.PLAIN, 10));
        UppScore.setBounds(150, 160, 150, 25);
        this.add( UppScore);

        //
        Score = new JTextField("Score");
        Score.setFont(new Font("Serif", Font.PLAIN, 20));
        Score.setBounds(10, 180, 310, 30);
        this.add(Score);
        Score.addActionListener(this);
        //
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        //bierzemy buttony, które nasłuchują na zdarzenie
        Object source = event.getSource();
        //przejście do operacji Morsea->English
        if(source==EnglishToMorsea) {
            choosean_translate=true;
            if(WriteUser.getText().equals("Morsea")) {
                String buffor = Score.getText();
                Score.setText(WriteUser.getText());
                WriteUser.setText(buffor);
            }

            UppWriteUser.setText("English");
            UppScore.setText("Morsea");

        }

        //przejście do operacji English->Morssea
        else if(source==MorseaToEnglish) {
            choosean_translate=false;

            if(WriteUser.getText().equals("English")) {
                String buffor = Score.getText();
                Score.setText(WriteUser.getText());
                WriteUser.setText(buffor);
            }

            UppScore.setText("English");
            UppWriteUser.setText("Morsea");

        }
        else if (source==Translate){
            string_to_translate="";
            String non_upper_word=WriteUser.getText();
            string_to_translate= non_upper_word.toUpperCase();
            if(choosean_translate==true) {
                string_after_translate="";
                createAnswearMorsea();
            }
            else{
                string_after_translate="";
                createAnswearEnglish();
                //System.out.println("tak");
            }
            Score.setText(string_after_translate);

        }

    }

    void createMap(){

        for(int i=0;i<alphabet.length();i++) {
            String one_letter_from_alphabet=Character.toString(alphabet.charAt(i));
            map_English_Morsea.put(one_letter_from_alphabet,downloadMorseaLetter(alphabet.charAt(i)));
            map_Morsea_English.put(downloadMorseaLetter(alphabet.charAt(i)),one_letter_from_alphabet);
        }
    }
    String downloadMorseaLetter(char letter){
        try {
            File file = new File("src\\Package\\EnglishToMorsea.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            String letter_string =Character.toString(letter);

            return doc.getElementsByTagName(letter_string).item(0).getTextContent();

        } catch (FileNotFoundException e) {
            System.out.println("Blad w downloadMorseaLetter FileNotFoundException");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.out.println("Blad w downloadMorseaLetter Exception");
            e.printStackTrace();
            return null;
        }
    }

    void createAnswearMorsea(){
        string_after_translate="";
        for(int i=0;i<string_to_translate.length();i++) {
            String letter=Character.toString(string_to_translate.charAt(i));
            String answear_for_letter=map_English_Morsea.get(letter);
            StringBuffer string_buffer=new StringBuffer().append(string_after_translate).append(answear_for_letter).append(" ");

            string_after_translate=string_buffer.toString();

            answear_for_letter=null;
        }
        System.out.println(string_after_translate);
    }

    void createAnswearEnglish(){
        string_after_translate="";
        string_convert="";
        ArrayList<String> temporary=new ArrayList<>();
        String buffor[];
        buffor= string_to_translate.split(" ");
        for(String buff:buffor) {
            temporary.add(buff);
        }
        for(int i=0;i<temporary.size();i++) {
            String letter_morsea=temporary.get(i);
            String answear_for_letter=map_Morsea_English.get(letter_morsea);
            StringBuffer string_buffer=new StringBuffer().append(string_convert).append(answear_for_letter);

            string_convert=string_buffer.toString();

        }
        string_after_translate=string_convert;
        System.out.println(string_convert);
    }
}
