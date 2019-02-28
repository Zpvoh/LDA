package Model;

import java.util.HashMap;

public class Dictionary {
    private HashMap<String, Integer> wordList=new HashMap<>();

    public Dictionary(){}

    public Dictionary(HashMap<String, Integer> wordList){
        this.wordList=wordList;
    }

    public int searchWord(String word){
        if(wordList.containsKey(word)){
            return wordList.get(word);
        }

        int index=wordList.size();
        wordList.put(word, index);
        return index;
    }

    public int size(){
        return wordList.size();
    }
}