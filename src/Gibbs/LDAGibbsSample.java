package Gibbs;

import Model.Corpus;
import Model.Dictionary;
import Model.Document;

import java.util.Arrays;

public class LDAGibbsSample extends GibbsSample {
    private Corpus corpus;
    private Dictionary dictionary;
    private int topicNum;
    private double[][] cooccurence;
    public int[] alphas;
    public int[] betas;

    public LDAGibbsSample() {

    }

    public LDAGibbsSample(int topicNum, Dictionary dictionary, Corpus corpus) {
        this.topicNum = topicNum;
        this.dictionary = dictionary;
        this.corpus = corpus;

        this.dimensionNum0 = corpus.size();
        for (int i = 0; i < corpus.size(); i++) {
            this.dimensionNum1 = this.dimensionNum1 > corpus.get(i).words.size() ?
                    this.dimensionNum1 : corpus.get(i).words.size();
        }
        this.rangeOfDimension = topicNum;

        this.sampleByDimension = new int[dimensionNum0][dimensionNum1];
        this.probableByDimension = new double[dimensionNum0][dimensionNum1][topicNum];

        this.alphas = new int[topicNum];
        this.betas = new int[dictionary.size()];
        this.cooccurence = new double[topicNum][dictionary.size()];
    }

    @Override
    public void computeProbable() {
        int[][][] nmk = new int[dimensionNum0][dimensionNum1][topicNum];
        int[][] nkt = new int[topicNum][dictionary.size()];

        int[][] sum_nmk = new int[dimensionNum0][dimensionNum1];
        int[] sum_nkt = new int[topicNum];

        for (int m = 0; m < corpus.size(); m++) {
            for (int n = 0; n < corpus.get(m).words.size(); n++) {
                sum_nmk[m][n] = 0;
                for (int k = 0; k < topicNum; k++) {
                    nmk[m][n][k] = 0;
                    for (int n1 = 0; n1 < corpus.get(m).words.size(); n1++) {
                        boolean notTheWord = n1 != n;
                        boolean topicIsRight = corpus.getTopic(m, n1) == k;
                        nmk[m][n][k] += notTheWord && topicIsRight ? 1 : 0;
                    }
                    //nmk[m][n][k]+=alphas[k];
                    nmk[m][n][k] += 1;
                    sum_nmk[m][n] += nmk[m][n][k];
                }
            }
        }

        for (int k = 0; k < topicNum; k++) {
            sum_nkt[k] = 0;
            for (int t = 0; t < dictionary.size(); t++) {
                nkt[k][t] = 0;
                for (int m = 0; m < corpus.size(); m++) {
                    for (int n = 0; n < corpus.get(m).words.size(); n++) {
                        int indexInDict = dictionary.searchWord(corpus.getWord(m, n));
                        boolean indexIsTheWord = (indexInDict == t);
                        boolean topicIsRight = (corpus.getTopic(m, n) == k);
                        nkt[k][t] += indexIsTheWord && topicIsRight ? 1 : 0;
                    }
                }
                nkt[k][t]--;
                //nkt[k][t]+=betas[t];
                nkt[k][t] += 1;
                sum_nkt[k] += nkt[k][t];
            }
        }

        //this.cooccurence = nkt;

        for (int m = 0; m < corpus.size(); m++) {
            for (int n = 0; n < corpus.get(m).words.size(); n++) {
                for (int k = 0; k < topicNum; k++) {
                    int t = dictionary.searchWord(corpus.getWord(m, n));
                    probableByDimension[m][n][k] = ((double) (nmk[m][n][k] * nkt[k][t])) / (sum_nmk[m][n] * sum_nkt[k]);
                }
            }
        }

        //System.out.println("Done with computing probable");

    }

    public void printCooccurence() {
        int[] wordsNum=new int[dictionary.size()];

        for(int t=0; t<dictionary.size(); t++){
            wordsNum[t]=0;
            for(int k=0; k<topicNum; k++){
                cooccurence[k][t]=0;
            }
        }

        for (int m = 0; m < corpus.size(); m++) {
            for (int n = 0; n < corpus.get(m).words.size(); n++) {
                int t = dictionary.searchWord(corpus.getWord(m, n));
                int k=sampleByDimension[m][n];
                cooccurence[k][t]++;
                wordsNum[t]++;
            }
        }

        /*for(int t=0; t<dictionary.size(); t++){
            for(int k=0; k<topicNum; k++){
                cooccurence[k][t]/=wordsNum[t];
            }
        }*/

        for (int k = 0; k < topicNum; k++) {
            System.out.print("topic " + k + ": ");
            //Arrays.sort(cooccurence[k]);
            for (int t = 0; t < dictionary.size(); t++) {
                System.out.print(dictionary.searchIndex(t) + ":" + cooccurence[k][t] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void assignData() {
        for (int m = 0; m < corpus.size(); m++) {
            for (int n = 0; n < corpus.get(m).words.size(); n++) {
                corpus.get(m).topics.set(n, sampleByDimension[m][n]);
            }
        }
    }
}
