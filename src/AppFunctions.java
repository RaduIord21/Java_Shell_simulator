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

    public String display(String path) {         //filtreaza fisierele audio si directoarele
        StringBuilder ret = new StringBuilder();
        String[] audioFormats = new String[]{".aac", ".aiff", ".amr", ".ape", ".au", ".flac", ".m4a", ".m4p", ".midi",
                ".mp3", ".ogg", ".opus", ".ra", ".wav", ".wma", ".dsf", ".dff", ".dsd", ".sds",
                ".sd2", ".mscx", ".mscz", ".mod", ".raw", ".snd", ".337", ".sun", ".xm", ".it",
                ".s3m", ".mtm"};
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

    public String find(String fileName, String directoryPath) {
        String ret = "File Not Found !";
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    ret = find(fileName, file.getAbsolutePath());
                } else if (fileName.equalsIgnoreCase(file.getName())) {
                    ret = "The path is : " + file.getAbsolutePath();
                    break;
                }
            }
        }
        return ret;
    }

    public String rename(String dst, String src) {
        File newFilename = new File(parent.currentPath + "/" + dst);
        File oldFilename = new File(parent.currentPath + "/" + src);
        if(oldFilename.renameTo(newFilename)){
            return "File renamed succesfully";
        }
        return "Unable to rename file !";
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
