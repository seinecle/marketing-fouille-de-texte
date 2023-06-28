/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package net.clementlevallois.adhoc.java4scientometrics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.clementlevallois.utils.Multiset;

/**
 *
 * @author LEVALLOIS
 */
public class Java4Scientometrics {

    public static void main(String[] args) throws IOException, Exception {
        Path dataFolderRoot = Path.of("data\\");
        Path pathFileWithAbstracts = Path.of("all refs.xlsx");
        Path pathFileWithSelectedAbstracts = Path.of("20230420 - results - 1.txt");
        int minNumberOfOccurrences = 1;

        Java4Scientometrics java4Scientometrics = new Java4Scientometrics();

        List<AbstractItem> abstractItems = java4Scientometrics.listAbstracts(dataFolderRoot, pathFileWithAbstracts);
        System.out.println("abstracts loaded as a list: " + abstractItems.size());
        System.out.println("abstracts loaded as a set: " + new HashSet(abstractItems).size());

        Set<AbstractItem> newSet = java4Scientometrics.filterOutSomeAbstracts(abstractItems, minNumberOfOccurrences);
        System.out.println("abstracts after filtering out: " + newSet.size());

        java4Scientometrics.printAbstracts(newSet, dataFolderRoot, pathFileWithSelectedAbstracts);

    }

    private List<AbstractItem> listAbstracts(Path dataFolderRoot, Path filePath) throws IOException, Exception {

        Path path = Path.of(dataFolderRoot.toString(), filePath.toString());
        List<AbstractItem> abstractItems = ExcelReader.read(path, 10, 61, 62);
        return abstractItems;
    }

    private Set<AbstractItem> filterOutSomeAbstracts(List<AbstractItem> abstractItems, int minNumberOfOccurrences) {
        Set<AbstractItem> setOutput = new HashSet();
        Multiset<AbstractItem> multisetInputAllReviews = new Multiset();
        multisetInputAllReviews.addAllFromListOrSet(abstractItems);

        // adding all Wos and Zotero refs to the final set of abstracts
        for (AbstractItem ai : abstractItems) {
            if (ai.getSource().equals(AbstractItem.SOURCE.ZOTERO) || ai.getSource().equals(AbstractItem.SOURCE.WOS)) {
                setOutput.add(ai);
            }
        }

        // proceeding to the count
        for (AbstractItem ai : abstractItems) {
            if (ai.getSource().equals(AbstractItem.SOURCE.ZOTERO) || ai.getSource().equals(AbstractItem.SOURCE.WOS)) {
                continue;
            }

            if (multisetInputAllReviews.getCount(ai) >= minNumberOfOccurrences) {
                setOutput.add(ai);
            }
        }

        return setOutput;

    }

    private void printAbstracts(Set<AbstractItem> newSet, Path dataFolderRoot, Path pathFileWithSelectedAbstracts) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (AbstractItem ai : newSet) {
            sb.append(ai.getContent());
            sb.append("\n");
        }
        Files.writeString(Path.of(dataFolderRoot.toString(), pathFileWithSelectedAbstracts.toString()), sb.toString());
    }
}
