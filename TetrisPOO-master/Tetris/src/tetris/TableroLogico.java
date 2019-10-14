/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

/**
 *
 * @author ferol
 */
public class TableroLogico {
    int ALTO = 20;//Y
    int LARGO = 10;//X
    Slot matriz[][];
    private int score;
    private int totalscore;
    
    TableroLogico(){
        this.score = 10;
        this.totalscore = 0;
        crearTablero();
    }
    void gravedad(int fila){
        for(int i= 0;i<LARGO;){
            if(matriz[i][fila+1].state == true)//si hay piezas encima de la fila las hace caer
                //pieceFall();
                 i = LARGO;
        }
    }
    void crearTablero(){
        for(int i = 0;i<LARGO;i++){
          for(int j = 0;j<ALTO;j++){  
              matriz[i][j] = new Slot();
          }
        }
    }
    
    void fullRows(){
        boolean fullRow = true;
        int cantidadDeLineas = 0;
        for(int i = ALTO;i>-1;i--){
          for(int j = LARGO;j>-1;j--){
              if(matriz[i][j].state==false){
                  fullRow = false;
                  break;
              }
          }
          if(fullRow == true){
              cantidadDeLineas ++;
              gravedad(i);
          }
        }
        addToScore(cantidadDeLineas);
    }//Luego que se encargue de repintar el tablero
    
    void addToScore(int cantidadDeLineas){
        this.totalscore += this.score*cantidadDeLineas;
    }
}

 class Slot{
    int ranura;
    boolean state;
    
    Slot(){
     ranura = 0;
     state = false;
    }
    boolean isEmpty(){
        return state==false;
    }
    
    void setState(boolean state){
        this.state = state;
    }
}

