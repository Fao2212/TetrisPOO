/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.Color;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author ferol
 */
public class ThreadFigura extends Thread {
    private ArrayList<Figura> figuras;
    private Tablero tablero;
    
    private boolean running,chocar;
    private boolean paused = false;
    private double factorVelocidad = 1.0;
    private int milisegundosDefault = 2000;
    private boolean gameOver = false;

    public ThreadFigura(Tablero tablero) {
        this.tablero = tablero;
        this.running = true;
        this.figuras = new ArrayList<Figura>();
        setFiguras();
    }
    
    
    @Override
    public void run() {
    while(running){
        tablero.nextPieza();
        setPiezas();
        //tablero.figuraActual;
        for (int i = tablero.getIndex_y(); i <= tablero.FILAS_Y; i++) {
           int columnaActual = tablero.getIndex_x();
                mostrarPieza(columnaActual,i);
            if(chocar == true){
                if(gameOver() == false){
                    chocar = false;
                    break;
                }
                else{
                    gameOver = true;
                    break;
                }
            }
            try {
                //Creo que por aca seria bueno descartivar botones
                //sleep((long) (factorVelocidad*milisegundosDefault));
                sleep(800);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadFigura.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (paused) {                
               try {
                   sleep(100);
               } catch (InterruptedException ex) {
                   Logger.getLogger(ThreadFigura.class.getName()).log(Level.SEVERE, null, ex);
               }
            }
                borrarPieza(columnaActual, i);
                llamarFuncion();
        }
        //O desactivar aca
        tablero.checkTablero();
        tablero.setIndex_y(0);
        if(gameOver == true){
            System.out.println("game Over");
            break;
        }
    }
}
    
    private void setFiguras(){
        this.figuras.add(new FiguraL());
    }
    
    public void setPiezas(){
        for(int i = 0;i <3;i++){
            if(tablero.piezas[i] == null)
                tablero.piezas[i] = randomFigura();
        }
    }
    
    void decrementVelocidad() {
        
        if (factorVelocidad > 0.30)
            this.factorVelocidad -= 0.10;
    }
    
    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
    
    public Figura randomFigura(){
        return new FiguraL();
    }
    
    public void mostrarPieza(int columnaActual,int filaActual){
        tablero.setIndex_y(filaActual);
        Figura pieza = tablero.figuraActual;
        if(filaActual>0 &&  filaActual<Tablero.FILAS_Y-tablero.figuraActual.maxY ){
            pieza.impresion(columnaActual, filaActual);
            for(int i = 0;i<4;i++){
                        tablero.tableroLabels[pieza.coordenadas[i][0]][pieza.coordenadas[i][1]].label.setBackground(pieza.color);

            }
                if(choqueDeFigura())
                    chocar = true;
        }
    }
    
    public void borrarPieza(int columnaActual,int filaActual){
        Figura pieza = tablero.figuraActual;
        pieza.getCoordenadas();
        for(int i = 0;i<4;i++){
            if(filaActual>0 &&  filaActual<Tablero.FILAS_Y-1-tablero.figuraActual.maxY)
                tablero.tableroLabels[pieza.coordenadas[i][0]][pieza.coordenadas[i][1]].label.setBackground(Color.DARK_GRAY);
        }
                pieza.original(pieza.tipo.ordinal());
        }
        
        boolean choqueDeFigura(){
            
            Figura pieza = tablero.figuraActual;
            pieza.getCoordenadas();
            boolean choque = false;
            for(int i = 0;i<4;i++){
                for(int j = 0;j<2;j++){
                    if(pieza.coordenadas[i][1]<Tablero.FILAS_Y-1)
                    if(!tablero.tableroLabels[pieza.coordenadas[i][0]][pieza.coordenadas[i][1]+1].isEmpty())
                        choque = true;
                }
            }
            return choque;
    }
        
    void llamarFuncion(){
        tablero.fullRows();
    }
    
    boolean gameOver(){
        for (int i = 0; i < 4; i++) {
                if(tablero.figuraActual.coordenadas[i][1]<3)
                    return true;
        }
        return false;
    }
    
    void pause(){
        paused = !paused;
    }
}
    
    
    

//Si da tiempo un creador de figuras.