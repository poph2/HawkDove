/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pop.hawkdove;

/**
 *
 * @author Adbello
 */
public class Bird {
    
    public static final String TYPE_HAWK = "hawk";
    public static final String TYPE_DOVE = "dove";
    
    String type;
    double contest;
    double resource;
    double resourcePerContest;

    public Bird(String type) {
        this.type = type;
        this.contest = 0.0;
        this.resource = 0.0;
        this.resourcePerContest = 0.0;
    }
    
    public void updateResourcePerContest() {
        resourcePerContest = resource/contest;
    }
    
    public boolean getDeath() {
        double minResourcePerContest = 0.5/1.0;
        
        double randomDeath = Math.random();
        
        if(resourcePerContest < minResourcePerContest && randomDeath < 0.025) {
            return true;
        }
        return false;
    }
    
    public void clearExperience() {
        this.contest = 0.0;
        this.resource = 0.0;
        this.resourcePerContest = 0.0;
    }
    
    
    
}
