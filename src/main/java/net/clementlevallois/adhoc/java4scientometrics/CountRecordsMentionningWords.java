/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package net.clementlevallois.adhoc.java4scientometrics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author LEVALLOIS
 */
public class CountRecordsMentionningWords {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        var countRecordsMentionningTwitter = new CountRecordsMentionningWords();
        countRecordsMentionningTwitter.analyze();
    }

    private void analyze() throws IOException {
        String path = "G:\\Mon Drive\\Writing\\Article Umigon FR\\Article for RAM\\bibliometric analysis\\analyses\\20230418\\20230418 - results - 1.txt";
        List<String> lines = Files.readAllLines(Paths.get(path));

        int counterAbstracts = 0;
        for (String line : lines) {
            line = line.toLowerCase();
            if (line.contains("advertis")) {
                counterAbstracts++;
            }
        }
        System.out.println("count docs mentionning this word(s): " + counterAbstracts);

    }

}
