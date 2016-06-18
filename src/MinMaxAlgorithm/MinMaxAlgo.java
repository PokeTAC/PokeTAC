/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MinMaxAlgorithm;

import java.util.List;
import java.util.Random;

/**
 *
 * @author Luis
 */
public class MinMaxAlgo {
    private int maxDepthLevel;
    private boolean usePrune = true;
    
    public MinMaxAlgo(int maxDepthLevel){
        this.maxDepthLevel = maxDepthLevel;
    }
    
    public MinMaxState getNextMove(MinMaxState state, boolean withPrune){
        this.usePrune = withPrune;
        return minMaxAlgo(state, 0);
    }
    
    private MinMaxState minMaxAlgo(MinMaxState state, int depth){
        boolean max = depth % 2 == 0;
        MinMaxState bestChild = null;
        
        //If the state is a leaf or we reached maximum depth, calculate the heuristic value
        if(state.isLeaf() || depth==maxDepthLevel){
            state.calculateHValue();
            return state;
        }else{
            //Expand all the children and explore by depth
            List<MinMaxState> children = state.findChildren();
            for(MinMaxState child : children){
                //Pass the alpha and beta values to children
                child.initAlphaBeta(state.getAlpha(), state.getBeta());
                //Explore the child
                minMaxAlgo(child, depth+1);
                if (bestChild==null) bestChild = child;
                else{
                    if(max && bestChild.getHValue()<child.getHValue())
                        bestChild = child;
                    else if(!max && bestChild.getHValue()>child.getHValue())
                        bestChild = child;
                    else if (bestChild.getHValue()==child.getHValue())
                        if((new Random()).nextBoolean()) bestChild = child;
                }
                
                //Update the state's alpha/beta value and check if we must prune
                if(usePrune){
                    boolean prune = state.updateAlphaBeta(child.getHValue(), max);
                    if(prune) break;
                }
            }
            state.setHValue(bestChild.getHValue());
            return bestChild;
        }
    }

    /**
     * @return the maxDepthLevel
     */
    public int getMaxDepthLevel() {
        return maxDepthLevel;
    }

    /**
     * @param maxDepthLevel the maxDepthLevel to set
     */
    public void setMaxDepthLevel(int maxDepthLevel) {
        this.maxDepthLevel = maxDepthLevel;
    }
}
