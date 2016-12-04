package projet.algav.benchmarck;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yassine on 04/12/2016.
 */
public class Shakespeare {

    public static List<String>  shakespeareFileToArray(String path) {
        List<String> res = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            try {
                String line = br.readLine();
                while (line != null) {
                    res.add(line);
                    line = br.readLine();
                }
            } finally {
                br.close();
            }
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            return res;
        }
    }

    public static void main(String[] args) {
    }

    public static List<String> listFilesForShakespeareFolder() {
        String path ="Shakespeare/";
        File folder = new File(path);
        List<String> res = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
                res.add(fileEntry.getName());
        }
        return res;
    }
}
