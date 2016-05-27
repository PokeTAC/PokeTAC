package MinMaxAlgorithm;

import java.util.List;

/**
 *
 * @author Luis
 */
public abstract class MinMaxState {
    private static final int INFINITY = 90000000;
    private int hValue;
    private int alpha;
    private int beta;
    
    public MinMaxState(){
        hValue = 0;
        beta = INFINITY;
        alpha = -INFINITY;
    }
    public abstract List<MinMaxState> findChildren();
    public abstract void calculateHValue();
    abstract boolean isLeaf();
    public int getHValue(){ return hValue;}
    public void setHValue(int hValue){ this.hValue = hValue;}
    
    //Returns true if we must prune
    protected boolean updateAlphaBeta(int value, boolean max){
        if(max && value>alpha){
            alpha = value;
        }else if(!max && value<beta){
            beta = value;
        }
        return alpha>=beta;
    }
    
    protected void initAlphaBeta(int alpha, int beta){
        this.alpha = alpha;
        this.beta = beta;
    }

    /**
     * @return the alpha
     */
    public int getAlpha() {
        return alpha;
    }

    /**
     * @return the beta
     */
    public int getBeta() {
        return beta;
    }
    
}
