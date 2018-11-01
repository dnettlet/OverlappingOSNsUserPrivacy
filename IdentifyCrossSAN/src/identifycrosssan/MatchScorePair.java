/*
 * IdentifyCrossSAN and MatchScorePair Copyright (C) 2017-2018 Vladimir Estivill-Castro 
 * This program comes with ABSOLUTELY NO WARRANTY 
 * GNU GENERAL PUBLIC LICENSE V 3 
 *
 */
package identifycrosssan;

/**
 *
 * @author vlad
 */
class MatchScorePair {
    
        private int match;
    private int score;
    
    public MatchScorePair(int theID, int theScore) {
        match = theID;
        score =theScore;
    }
    
    public int getTheID() {
        return match;
    }
    
    public int getTheScore() {
        return score;
    }
    
}
