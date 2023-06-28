/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.clementlevallois.adhoc.java4scientometrics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author LEVALLOIS
 */
public class TextReader {

    public static void read() throws IOException {
        Path pathFileToRead = Paths.get("", "");
        List<String> lines = Files.readAllLines(pathFileToRead);
        Set<AbstractItem> abstractItems = new HashSet();

    }

}
