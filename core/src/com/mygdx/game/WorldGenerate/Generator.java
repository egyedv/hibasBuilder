package com.mygdx.game.WorldGenerate;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by tanulo on 2017. 01. 12..
 */
public class Generator {

    private int [][] WORLD;
    private int x,y;

    public Generator(int ww, int wh){
        WORLD = new int[ww][wh];
        for(int i = 0; i < WORLD.length; i++){
            for (int j = 0; j <WORLD[0].length; j++){
                WORLD[i][j] = 0;
            }
        }
        generalo();
        vizeleenorzo();
    }

    public static int vel(int a, int b){
        return (int)(Math.random()*(b-a+1)+a);
    }

    private int gachivel(){
        int a = (int)(Math.random()*(2-0+1)+0);
        if(a == 2) return -1;
        else return a;
    }

    private void folyo(){
        int folyoszam = vel(1,10)<3?2:1;
        for(int i = 0; i<folyoszam; i++){
            int elso = -1;
            if(i == 0) elso = vel(WORLD[0].length/3,WORLD[0].length/3*2);
            else elso = vel(0, WORLD[0].length-1);
            for(int j = 0; j<WORLD[0].length;j++){
                WORLD[j][elso]= 1;
                //szélesítés
                if(elso > 0)WORLD[j][elso-1] = 1;
                if(elso > 1)WORLD[j][elso-2] = 1;
                if(elso < WORLD.length-1)WORLD[j][elso+1] = 1;
                if(elso < WORLD.length-2)WORLD[j][elso+2] = 1;
                //lefele
                if(elso == 0) elso += vel(0,1);
                else if (elso == WORLD.length-1) elso -=vel(0,1);
                else elso+= gachivel();
            }

        }
    }

    private void kit(int x,int y,int n,int parameter){
        if(n>0 && x<WORLD.length && x>=0 && y<WORLD[0].length && y>=0 && WORLD[x][y] == 0){
            WORLD[x][y] = parameter;
            int xes = vel(0,1)==1?1:-1;
            int yos = vel(0,1)==1?1:-1;
            kit(x+xes,y,n-1,parameter);
            kit(x,y+yos,n-1,parameter);
        }

    }

    private void erdo(){
        int kezdox, kezdoy;
        int erdoszam = vel(10,16);
        for(int i = 0; i < erdoszam; i++) {
            do {
                kezdox = vel(0, WORLD.length - 1);
                kezdoy = vel(0, WORLD[0].length - 1);
            } while (WORLD[kezdox][kezdoy] != 0);
            kit(kezdox, kezdoy, 10, 2);
        }
    }

    private void kavics(){
        int kezdox, kezdoy;
        int kavicsszam = vel(5,10);
        for(int i = 0; i < kavicsszam; i++) {
            do {
                kezdox = vel(0, WORLD.length - 1);
                kezdoy = vel(0, WORLD[0].length - 1);
            } while (WORLD[kezdox][kezdoy] != 0);
            kit(kezdox, kezdoy, 7, 3);
        }
    }

    private void city(){
        x = vel(0,98);
        y = vel(1,99);
        //folyó 1
        while(!jo(x,y)){
            x = vel(0,98);
            y = vel(1,99);
        }
        WORLD[x][y] = 9;
        WORLD[x-1][y] = 9;
        WORLD[x][y-1] = 9;
        WORLD[x-1][y-1] = 9;
        //fa közelben
        int helyx = vel(x-8,x+8);
        int helyy = vel(y-8,y+8);
        while(helyx > (x - 4) && helyx < x + 3){
            helyx = vel(x-8,x+8);
        }
        while (helyy > y - 4 && helyy < y + 3){
            helyy = vel(y-8,y+8);
        }
        kit(helyx,helyy,7,2);
        //kő közelben
        int fax = helyx;
        int fay = helyy;
        helyx = vel(x-8,x+8);
        helyy = vel(y-8,y+8);
        while(helyx > (x - 4) && helyx < x + 3 || (helyx > fax - 3 && helyx < fax + 2)){
            helyx = vel(x-8,x+8);
        }
        while (helyy > y - 4 && helyy < y + 3 || (helyy > fay - 3 && helyy < fay + 2)){
            helyy = vel(y-8,y+8);
        }

        kit(helyx,helyy,5,3);
    }

    private boolean jo(int x, int y){
        boolean b = true;
        for (int i = -11; i <= 10; i++) {
            for (int j = -11; j <= 10; j++) {
                if(x+i > 99 || x+i < 0) b = false;
                else if(y+j > 99 || y+j < 0) b = false;
                else if(WORLD[x+i][y+j] == 1) b = false;
            }
        }
        return b;
    }

    private void generalo(){
        folyo();
        city();
        erdo();
        kavics();
    }

    private void vizeleenorzo(){
        for(int i = 0; i < WORLD.length; i++){
            for(int j = 0; j < WORLD[0].length; j++){
                if(WORLD[i][j] == 1){
                    if(i != 0 && i != 99)
                    if(WORLD[i+1][j] != 1 && WORLD[i-1][j] != 1){
                        WORLD[i][j] = 0;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        String sor="";
        for(int i = 0; i < WORLD.length; i++){
            for (int j = 0; j <WORLD[0].length; j++){
                sor+=WORLD[i][j]+"";
            }
            sor+="\n";
        }
        return sor;
    }


    public int[][] getWORLD() {
        return WORLD;
    }
}
