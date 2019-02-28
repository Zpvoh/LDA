import Gibbs.LDAGibbsSample;
import Model.Corpus;
import Model.Dictionary;
import Model.Document;

import java.io.*;
import java.util.Scanner;

public class Main {
    private static Dictionary dictionary=new Dictionary();
    private static Corpus corpus=new Corpus();
    private static int topicNum=10;

    public static void main(String[] args) {
        try {
            //readDocument("corpus/1.txt");
            readDirectory("text");
            LDAGibbsSample sample=new LDAGibbsSample(topicNum, dictionary, corpus);
            sample.convergent();
            System.out.println(topicNum);
        } catch (FileNotFoundException e) {
            System.out.println("We cannot find the file");
        }

    }

    public static void readDocument(String filename) throws FileNotFoundException {
        FileInputStream fileInputStream=new FileInputStream(filename);
        Scanner scanner=new Scanner(fileInputStream);
        StringBuilder content=new StringBuilder();
        while (scanner.hasNext()){
            content.append(scanner.next());
            content.append(" ");
        }

        Document document=new Document(corpus.size(), content.toString(), topicNum);
        corpus.add(document);

        for(String word : document.words){
            dictionary.searchWord(word);
        }

    }

    public static void readDirectory(String dirname) throws FileNotFoundException {
        File dir=new File(dirname);
        if(dir.isDirectory()){
            String[] fileList=dir.list();
            for(String filename : fileList){
                readDocument(dirname + "/" + filename);
            }
        }
    }

}
