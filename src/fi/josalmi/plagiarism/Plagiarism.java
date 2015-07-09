package fi.josalmi.plagiarism;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Plagiarism {

    private final static String submissionPath = "/tmp/plag/Tehtava1";
    private final static String fileName = "Ohjelma.java";

    public static void main(String[] args) throws IOException {
        Map<Path, String> files = Files.walk(new File(submissionPath).toPath())
                .filter(p -> p.toString()
                        .endsWith(File.separator + fileName))
                .collect(Collectors.toMap(p -> p, p -> {
                    try {
                        return new String(Files.readAllBytes(p));
                    } catch (IOException ex) {
                        return "";
                    }
                }));

        List<Path> paths = new ArrayList(files.keySet());
        for (int i = 0; i < paths.size(); i++) {
            for (int j = i + 1; j < paths.size(); j++) {
                int distance = Levenshtein.distance(files.get(paths.get(i)), files.get(paths.get(j)));
                if (distance < 50) {
                    System.out.println("Submissions " + paths.get(i).toString() + " and " + paths.get(i).toString() + ", distance: " + distance);
                }

            }
        }
    }

}
