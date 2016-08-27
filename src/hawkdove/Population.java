/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hawkdove;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Adbello
 */
public class Population {
    
    ArrayList<Bird> birds;
    
    double disease;
    
    int maxPopulation, count, hawkCount, doveCount;
    
    public static void main(String args[]) {
        Population p = new Population(10000);
        
        p.printBirds();
        
        for(int i = 0; i < 10000; i++) {
            p.populationFight();
            //p.printBirds();
            
            p.clearExperience();

            p.generateBirds();
            p.printBirds();
        }
        
    }

    public Population(int count) {
        this.disease = 0.0;
        this.maxPopulation = count;
        this.count      = 0;
        this.hawkCount  = 0;
        this.doveCount  = 0;
        
        birds = new ArrayList<>();
        
        generateBirds();
    }
    
    public void generateBirds() {
        for(int i = count; i < maxPopulation; i++) {
            birds.add(new Bird(getRandomBirdType()));
        }
        count = birds.size();
    }
    
    public String getRandomBirdType() {
        double rand = Math.random();
        
        if(rand < (0.5 - disease)) {
            doveCount = doveCount + 1;
            return Bird.TYPE_DOVE;
        }
        else {
            hawkCount = hawkCount + 1;
            return Bird.TYPE_HAWK;
        }
    }
    
    public void fight() {
        
        int num1 = randInt(0, birds.size()-1);
        int num2 = randInt(0, birds.size()-1);
        
        while(num2 == num1) {
            num2 = randInt(0, birds.size()-1);
        }
        
        Bird bird1 = birds.get(num1);
        Bird bird2 = birds.get(num2); 
        
        bird1.contest = bird1.contest + 1;
        bird2.contest = bird2.contest + 1;
        
        if(bird1.type.equalsIgnoreCase(Bird.TYPE_DOVE) && bird1.type.equalsIgnoreCase(Bird.TYPE_DOVE)) {
            //Both birds are doves fight is avoided
            //Resource is shared
            bird1.resource = bird1.resource + 0.5;
            bird2.resource = bird2.resource + 0.5;
            
        }
        else if(bird1.type.equalsIgnoreCase(Bird.TYPE_DOVE) && bird1.type.equalsIgnoreCase(Bird.TYPE_HAWK)) {
            //Bird 1 is a dove and bird 2 is a hawk
            //The dove backs off
            bird1.resource = bird1.resource + 0;        //Nothing for the dove
            bird2.resource = bird2.resource + 1;        //Hawk takes all
        }
        else if(bird1.type.equalsIgnoreCase(Bird.TYPE_HAWK) && bird1.type.equalsIgnoreCase(Bird.TYPE_DOVE)) {
            //Bird 1 is a hawk and bird 2 is a dove
            //The dove backs off
            bird1.resource = bird1.resource + 1;        //Hawk takes all
            bird2.resource = bird2.resource + 0;        //Nothing for the dove
        }
        else if(bird1.type.equalsIgnoreCase(Bird.TYPE_HAWK) && bird1.type.equalsIgnoreCase(Bird.TYPE_HAWK)) {
            //Both birds are hawks
            //So they fight with 50% chance of winning.
            //Winner takes all;
            
            double prob = Math.random();
            
            double bird1Win = 0;
            double bird2Win = 0;
            
            if(prob >= 0.5) {
                bird1Win = 1.0;
                bird2Win = 0.0;
            }
            else {
                bird1Win = 0.0;
                bird2Win = 1.0;
            }
            
            bird1.resource = bird1.resource + (bird1Win * 1);        //May the better fighter win
            bird2.resource = bird2.resource + (bird2Win * 2);        //May the better fighter win
        }
        
        bird1.updateResourcePerContest();
        bird2.updateResourcePerContest();
        
    }
    
    public void populationFight() {
        for(int i = 0; i < count; i++) {
            fight();
        }
        kill();
        updateCounts();
    }
    
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
    
    public void kill() {
        int i = 0;
        while(i < birds.size()) {
            Bird b = birds.get(i);
            if(b.getDeath()) {
                
                //System.out.println(i + " kill - " + b.type + " " + b.contest + " " + b.resource + " " + b.resourcePerContest);
                birds.remove(i);
            }
            else {
                i++;
            }
        }
    }
    
    public void updateCounts() {
        count = birds.size();
        doveCount = 0;
        hawkCount = 0;
        for (Bird bird : birds) {
            if(bird.type.equalsIgnoreCase(Bird.TYPE_DOVE)) {
                doveCount = doveCount + 1;
            }
            else {
                hawkCount = hawkCount + 1;
            }
        }
    }
    
    public void clearExperience() {
        for (Bird bird : birds) {
            bird.clearExperience();
        }
    }
    
    public void printBirds() {
        
        System.out.println();
        System.out.println("Population - " + count);
        System.out.println("Hawk - " + hawkCount + " - " + Math.round(hawkCount*100.0/(double)count) + "%");
        System.out.println("Dove - " + doveCount + " - " + Math.round(doveCount*100.0/(double)count) + "%");
        
        for(int i = 0; i < birds.size(); i++) {
            
            Bird b = birds.get(i);
            //System.out.println(i + " - " + b.type + " " + b.contest + " " + b.resource + " " + b.resourcePerContest);
        }
    }
    
    public void setDisease(String type, double factor) {
        if(type.equalsIgnoreCase(Bird.TYPE_DOVE)) {
            disease = factor/2.5;
        }
        else {
            disease = 0 - (factor/2.5);
        }
    }
    
    public void removeDisease() {
        disease = 0;
    }
    
}
