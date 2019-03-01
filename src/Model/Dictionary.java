package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Dictionary {
    private HashMap<String, Integer> wordList=new HashMap<>();
    private HashMap<Integer, String> indexList=new HashMap<>();
    private ArrayList<String> stopWords=new ArrayList<>();

    public Dictionary(){}

    public Dictionary(ArrayList<String> stopWords){
        this.stopWords=stopWords;
    }

    public Dictionary(HashMap<String, Integer> wordList){
        this.wordList=wordList;
    }

    public int searchWord(String word){
        if(wordList.containsKey(word)){
            return wordList.get(word);
        }

        if(!stopWords.contains(word)) {
            int index = wordList.size();
            wordList.put(word, index);
            indexList.put(index, word);
            return index;
        }

        return -1;
    }

    public String searchIndex(int index){
        if(indexList.containsKey(index)){
            return indexList.get(index);
        }

        return null;
    }

    public int size(){
        return wordList.size();
    }
}