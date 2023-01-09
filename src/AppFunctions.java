import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;

public class AppFunctions {
    private AppWindow parent;

    public AppFunctions(AppWindow parent) {
        this.parent = parent;
    }

    static String getPath() {
        String currentPath = null;
        try {
            currentPath = new java.io.File(".").getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return currentPath;
    }

    public void play(String option) {
        File song = new File(parent.currentPath + "/" + option);
        if (song.isFile()){
            Desktop dsk = Desktop.getDesktop();
            try {
                dsk.open(song);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public String display(String path) {
        StringBuilder ret = new StringBuilder();
        String[] audioFormats = new String[]{".wav", ".mp3", ".m4a", ".ogg", ".raw", ".m4p"};
        File dir = new File(path);
        File[] content = dir.listFiles();
        if (content != null) {
            for (File each : content) {
                if (each.isFile()) {
                    if (Arrays.asList(audioFormats).contains(getFileExtension(each))) {
                        ret.append(each.getName());
                        ret.append("\n");
                    }
                } else {                    ret.append(each.getName());
                    ret.append("\n");
                }
            }
        }
        return ret.toString();
    }

    private String getFileExtension(File file) {  //obtinem extensia fisiserului
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // extensie goala
        }
        return name.substring(lastIndexOf);
    }

    public void find(String song) {

    }

    public void rename(String song) {

    }

    public String cd(String param) {
        param = param.trim();
        if (param.equals("..")) {
            int lastIndexOf = parent.currentPath.lastIndexOf("/");
            parent.currentPath = parent.currentPath.substring(0, lastIndexOf);
            System.out.println(parent.currentPath);
        } else {

            File dir = new File(parent.currentPath + "/" + param);
            if (dir.isDirectory()) {
                parent.currentPath += "/" + param;
            } else {
                return "No such folder";
            }
        }
        return "Directory changed succesfully !";
    }
}
