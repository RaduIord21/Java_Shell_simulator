import java.io.IOException;

public class AppFunctions {
    public void play(String option){

    }

    public void display() {

    }

    public void find(String song) {

    }

    public void rename(String song) {

    }

    public void cd(String path) {
        String currentPath = null;
        System.out.println(path);
        try {
            currentPath = new java.io.File(".").getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Current dir:" + currentPath);
    }
}
