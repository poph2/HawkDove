/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hawkdove;

/**
 *
 * @author Adbello
 */
public class Census {
    
    int count;
    int hawkCount;
    int doveCount;
    double hawkPercent;
    double dovePercent;

    public Census(int count, int hawkCount, int doveCount) {
        this.count = count;
        this.hawkCount = hawkCount;
        this.doveCount = doveCount;
        
        updatePercentages();
    }
    
    private void updatePercentages() {
        hawkPercent = ((double)hawkCount * 100.0)/(double)count;
        dovePercent = ((double)doveCount * 100.0)/(double)count;
    }


}