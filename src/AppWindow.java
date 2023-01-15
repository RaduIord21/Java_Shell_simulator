import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;


import static java.lang.Thread.sleep;

public class AppWindow{
    private JTextArea textBox;
    private int lastLine = 0;
    public String currentPath = "";
    private JFrame frame;// frame creat

    AppWindow(){
        currentPath = AppFunctions.getPath();
    }
    public void show(){
        this.frame = new JFrame();
        this.frame.setPreferredSize(new Dimension(1000,600));
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String Welcome_message = "Welcome to SoundManager ! \n Type !help for a list oc commands !\n";
        this.textBox = new JTextArea(Welcome_message);// se creeaza un nou textbox
        this.textBox.setBackground(new Color(125, 133, 151));

        //se adauga elementele
        this.frame.setLayout(new GridLayout());
        this.frame.add(this.textBox);
        this.frame.pack();
        this.frame.setVisible(true);
    }


    public void startProcessingCommands() throws InterruptedException {
        String currentCommand = "";
        String song = "";
        AppFunctions fun = new AppFunctions(this);
        String path = "";
        while (true){

            currentCommand = this.getCommand();
            if (currentCommand.startsWith("!exit")) {
                frame.dispose();
                System.exit(0);
            } else if (currentCommand.equals("!help\n")) {
                this.printInBox("This is the list of commands :\n !exit = Exit the program \n !display = Displays the files that are in the current directory\n !play <song> = Plays the selected file \n" +
                        "\n !rename = starts the renaming process");
            } else if (currentCommand.startsWith("!play ")) { // ruleaza fisierul audio
                int firstIndex = currentCommand.indexOf(" ");
                fun.play(currentCommand.substring(firstIndex).trim());
            } else if (currentCommand.startsWith("!display")) {   //arata continutul directorului curent
                this.printInBox(fun.display(currentPath));
            } else if (currentCommand.startsWith("!find")){ //spune daca fisierul audio se gaseste in ierarhia de directorare
                int lastIndexOf = currentCommand.lastIndexOf(" ");
                this.printInBox("\n" + fun.find(currentCommand.substring(lastIndexOf).trim(),currentPath));
            } else if (currentCommand.startsWith("!rename")){ //redenumeste fisierul audio
                this.printInBox("Chose a name for your file : ");
                String name = this.getCommand();
                this.printInBox("Chose the file you want to rename :");
                String file = this.getCommand();
                this.printInBox(fun.rename(name,file));
            } else if (currentCommand.startsWith("!cd ")){ //schimba directorul
                this.printInBox(fun.cd(currentCommand.split(" ")[1]));
            }

        }
    }

    private void printInBox(String str){
        this.textBox.append(str + "\n");
        this.textBox.setCaretPosition(this.textBox.getText().length());
    }

    public String getCommand() throws InterruptedException {                 //listener
        String ret = "";
        int lines = this.textBox.getLineCount();
        int oldLineNumber = lines;
        while (oldLineNumber == lines) {
            sleep(200);
            lines = this.textBox.getLineCount();
        }
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
            System.out.println(e);
        }
        return ret.trim();
    }
}
