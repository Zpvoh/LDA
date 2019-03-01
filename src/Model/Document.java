package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Document {
    public int getIndex() {
        return index;
    }

    public int getTopicNum() {
        return topicNum;
    }

    private int index;
    private int topicNum;
    private ArrayList<String> stopWords=new ArrayList<>();
    public ArrayList<String> words=new ArrayList<>();
    public ArrayList<Integer> topics=new ArrayList<>();

    public Document(){

    }

    public Document(int index){
        this.index=index;
    }

    public Document(int index, String article, int topicNum, ArrayList<String> stopWords){
        this.index=index;
        this.stopWords=stopWords;
        splitArticle(article);
        this.topicNum=topicNum;
    }

    public void splitArticle(String article){
        //ArrayList<String> words=new ArrayList<>();
        Pattern wordPattern=Pattern.compile("([a-z]|[A-Z])+");
        Matcher matcher=wordPattern.matcher(article);
        while(matcher.find()){
            String word=matcher.group().toLowerCase();
            if(!stopWords.contains(word)) {
                words.add(word);
                topics.add(0);
            }
        }
    }

    public String getWord(int index){
        return words.get(index);
    }

    public int getTopic(int index){
        return topics.get(index);
    }
}
