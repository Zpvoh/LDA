import Gibbs.LDAGibbsSample;
import Model.Corpus;
import Model.Dictionary;
import Model.Document;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static Corpus corpus = new Corpus();
    private static ArrayList<String> stopWords = new ArrayList<>();
    private static Dictionary dictionary = new Dictionary(stopWords);
    private static int topicNum = 3;

    public static void main(String[] args) {
        try {
            readDocument("1.txt");
            //readDirectory("text");
            //readStopWords("listOfStopWord");
            readDirectory("corpus");
            LDAGibbsSample sample = new LDAGibbsSample(topicNum, dictionary, corpus);
            sample.convergent();
            sample.printCooccurence();
            //System.out.println(topicNum);
        } catch (FileNotFoundException e) {
            System.out.println("We cannot find the file");
        }

    }

    public static void readDocument(String filename) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(filename);
        Scanner scanner = new Scanner(fileInputStream);
        StringBuilder content = new StringBuilder();
        while (scanner.hasNext()) {
            String word = scanner.next();
            content.append(word);
            content.append(" ");
        }


        Document document = new Document(corpus.size(), content.toString(), topicNum, stopWords);
        corpus.add(document);

        for (String word : document.words) {
            if (!stopWords.contains(word)) {
                dictionary.searchWord(word);
            }
        }

    }

    public static void readDirectory(String dirname) throws FileNotFoundException {
        File dir = new File(dirname);
        if (dir.isDirectory()) {
            String[] fileList = dir.list();
            for (String filename : fileList) {
                readDocument(dirname + "/" + filename);
            }
        }
    }

    public static void readStopWords(String filename) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(filename);
        Scanner scanner = new Scanner(fileInputStream);
        while (scanner.hasNext()) {
            stopWords.add(scanner.next());
        }
    }

}
