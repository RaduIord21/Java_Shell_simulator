import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;

import static java.lang.Thread.sleep;

public class AppWindow{
    private JTextArea textBox;
    private int lastLine = 0;
    public String currentPath = "";

    AppWindow(){
        currentPath = AppFunctions.getPath();
    }
    public void show(){
        JFrame Frame = new JFrame();// frame creat
        Frame.setPreferredSize(new Dimension(1000,600));
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String Welcome_message = "Welcome to SoundManager ! \n Type !help for a list oc commands !\n";
        this.textBox = new JTextArea(Welcome_message);// se creeaza un nou textbox
        this.textBox.setBackground(new Color(125, 133, 151));

        //se adauga elementele
        Frame.setLayout(new GridLayout());
        Frame.add(this.textBox);
        Frame.pack();
        Frame.setVisible(true);
    }

    public void startProcessingCommands() throws InterruptedException {
        String currentCommand = "";
        String song = "";
        AppFunctions fun = new AppFunctions(this);
        String path = "";
        while (true){
            currentCommand = this.getCommand();
            sleep(1000);
            if (currentCommand.equals("!exit\n")) {
                System.exit(0);
            } else if (currentCommand.equals("!help\n")) {
                this.textBox.append("This is the list of commands :\n !exit = Exit the program \n !display = Displays the files that are in the current directory\n !play <number> = Plays the selected track \n");
            } else if (currentCommand.startsWith("!play ")) { // ruleaza fisierul audio
                int firstIndex = currentCommand.indexOf(" ");
                fun.play(currentCommand.substring(firstIndex).trim());
            } else if (currentCommand.startsWith("!display")) {   //arata continutul directorului curent
                this.textBox.append(fun.display(currentPath));
            } else if (currentCommand.startsWith("!find ")){ //spune daca fisierul audio se gaseste in ierarhia de directorare
                fun.find(currentCommand.split(" ")[1]);
            } else if (currentCommand.startsWith("!rename ")){ //redenumeste fisierul audio
                fun.rename(currentCommand.split(" ")[1]);
            } else if (currentCommand.startsWith("!cd ")){ //schimba directorul
                this.textBox.append(fun.cd(currentCommand.split(" ")[1]));
            }
        }
    }

    public String getCommand() {                 //listener
        int lines = this.textBox.getLineCount();
        System.out.println(lines);
        String ret = "";
        try{
            for (int i = lastLine; i <= lines; i++){
                int start = this.textBox.getLineStartOffset(i);
                int end = this.textBox.getLineEndOffset(i);
                String line = this.textBox.getText(start,end-start);
                if(line.length() > 0 && line.charAt(line.length() - 1) == 10){
                    System.out.println(line);
                     ret = line;
                     lastLine++;
                }
            }
        }
        catch (Exception e){

        }
        return ret;
    }
}
