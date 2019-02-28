package Gibbs;

public abstract class GibbsSample {
    protected int dimensionNum0;
    protected int dimensionNum1;
    protected int[][] sampleByDimension;
    protected int rangeOfDimension;
    protected final int N=100;
    public double[][][] probableByDimension;

    public GibbsSample(){

    }

    public GibbsSample(int dimensionNum0, int dimensionNum1, int rangeOfDimension){
        this.dimensionNum0=dimensionNum0;
        this.dimensionNum1=dimensionNum1;
        this.sampleByDimension=new int[dimensionNum0][dimensionNum1];
        this.rangeOfDimension=rangeOfDimension;
        this.probableByDimension=new double[dimensionNum0][dimensionNum1][rangeOfDimension];
    }

    public void convergent(){
        for(int i=0; i<dimensionNum0; i++){
            for (int j=0; j<dimensionNum1; j++) {
                sampleByDimension[i][j] = (int) Math.floor(Math.random() * (rangeOfDimension + 1));
            }
        }
        System.out.println("Initialization completed!");

        assignData();

        for(int n=0; n<N; n++) {
            System.out.println("Epoch "+n);
            for (int i = 0; i < dimensionNum0; i++) {
                for(int j=0; j<dimensionNum1; j++) {
                    computeProbable();
                    sampleByDimension[i][j] = randomGenerateByDistribute(probableByDimension[i][j]);
                }
            }
        }

        assignData();
    }

    public int randomGenerateByDistribute(double[] distribute){
        double sum=0;
        for(double p: distribute){
            sum+=p;
        }

        double random=Math.random()*sum;
        double lower=0;
        double upper=0;
        for(int i=0; i<distribute.length; i++){
            upper+=distribute[i];
            if(random>=lower && random<=upper){
                return i;
            }
            lower+=distribute[i];
        }

        return -1;
    }

    public abstract void computeProbable();

    public abstract void assignData();
}
