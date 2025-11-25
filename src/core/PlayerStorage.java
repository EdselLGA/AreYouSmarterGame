package core;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerStorage {

    private static final String FILE_PATH = "assets/players.txt";

    // save player name
    public static void saveName(String name) {
        try {
            FileWriter fw = new FileWriter(FILE_PATH, true);
            fw.write(name + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving player name: " + e.getMessage());
        }
    }

    // load all names
    public static List<String> loadNames() {
        List<String> names = new ArrayList<>();

        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
                return names;
            }

            BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
            String line;

            while ((line = br.readLine()) != null) {
                names.add(line.trim());
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Error loading names: " + e.getMessage());
        }

        return names;
    }
}
