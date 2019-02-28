package Model;

import java.util.ArrayList;

public class Corpus extends ArrayList<Document> {
    public String getWord(int m, int i){
        return this.get(m).getWord(i);
    }

    public int getTopic(int m, int n){
        return this.get(m).getTopic(n);
    }
}
