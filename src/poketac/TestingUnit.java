/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poketac;

import View.MainWindow.AIType;
import java.util.HashSet;
import java.util.Objects;

/**
 *
 * @author alulab14
 */
public class TestingUnit {
    private final PokeTAC logicMan;
    
    private class TableEntry{
        AIType type1;
        AIType type2;
        int win=0;
        int lose=0;
        int draw=0;

        public TableEntry(AIType type1, AIType type2, int win, int lose, int draw) {
            this.type1 = type1;
            this.type2 = type2;
            this.win = win;
            this.lose = lose;
            this.draw = draw;
        }     

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final TableEntry other = (TableEntry) obj;
            
            return ((this.type2 == other.type2 && this.type1 == other.type1) || (this.type1 == other.type2 && this.type2 == other.type1));
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + Objects.hashCode(this.type1)+ Objects.hashCode(this.type2);
            return hash;
        }
    }
    
    public TestingUnit(){
        logicMan = new PokeTAC();
        logicMan.initGame("A1", "A2");
    }
    
    private int[] testAIs(int times, AIType aiType1, AIType aiType2){
        int result[] = new int[2];
        double weights1[] = null, weights2[] = null;
        boolean rand1 = false, rand2 = false;

        switch(aiType1){
            case ALEATORIO:
                rand1 = true;
                break;
            case EVOLUTIVO:
                weights1 = PokeTAC.GENETIC_WEIGHTS;
        }

        switch(aiType2){
            case ALEATORIO:
                rand2 = true;
                break;
            case EVOLUTIVO:
                weights2 = PokeTAC.GENETIC_WEIGHTS;
        }
        
        for(int i=0; i<times; i++){
            logicMan.prepareAutoBattleTeam();
            int winner = logicMan.weightedAutoBattle(weights1, weights2, rand1, rand2);
            if(winner!=-1) result[winner]++;
        }
        return result;
    }
    
    public void printTestAIs(boolean sameTeams, int times){
        HashSet<TableEntry> set = new HashSet<>();
        
        for(AIType type1 : AIType.values()){
            for(AIType type2 : AIType.values()){
                if(type1 == type2) continue;
                
                TableEntry e = new TableEntry(type1, type2, 0, 0, 0);
                //if(!set.contains(entry)){
                    int result[] = testAIs(times, type1, type2);
                    e.win = result[0];
                    e.lose = result[1];
                    e.draw = times -e.win - e.lose;
                    System.out.println(e.type1.name() + " VS " + e.type2.name() + " " + e.win + " " + e.lose + " " + e.draw);
                    //set.add(entry);
                //}
            }
        }
        
        for(TableEntry e : set){
            //System.out.println(e.type1.name() + " VS " + e.type2.name() + " " + e.win + " " + e.lose + " " + e.draw);
        }
        
    }
}
