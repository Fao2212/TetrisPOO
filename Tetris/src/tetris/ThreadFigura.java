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

/**
 *
 * @author ferol
 */
public class ThreadFigura extends Thread {
    private ArrayList<Figura> figuras;
    private Tablero tablero;
   
    
    private boolean running;
    private boolean paused = false;
    private double factorVelocidad = 1.0;
    private int milisegundosDefault = 2000;

    public ThreadFigura(Tablero tablero) {
        this.tablero = tablero;
        this.running = true;
        this.figuras = new ArrayList<Figura>();
        setFiguras();
    }
    
    
    @Override
    public void run() {
    while(running){
        for (int Y = -3; Y < tablero.FILAS_Y; Y++) {
           int X_Actual = tablero.getIndex_x();
                mostrarPieza(X_Actual,Y);
            try {
                sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadFigura.class.getName()).log(Level.SEVERE, null, ex);
            }
            borrarPieza(X_Actual,Y);
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
    
    public void mostrarPieza(int X_Actual,int Y_Actual){
        Figura pieza = tablero.nextPieza();
        setPiezas();
        pieza.setCoordenadas(X_Actual,Y_Actual);
        this.tablero.piezaActual = pieza;
        for(int i = 0;i<4;i++){
            if(Y_Actual>0 && X_Actual <Tablero.FILAS_Y - tablero.piezaActual.maxY)
            tablero.tableroLabels[pieza.coordenadas[i][0]][pieza.coordenadas[i][1]].label.setBackground(pieza.color);
        }
    }
public void borrarPieza(int X_Actual,int Y_Actual){
        Figura pieza = tablero.piezaActual;
        pieza.getCoordenadas();
        for(int i = 0;i<4;i++){
            if(Y_Actual>0 &&  Y_Actual<Tablero.FILAS_Y - tablero.piezaActual.maxY);
                tablero.tableroLabels[pieza.coordenadas[i][0]][pieza.coordenadas[i][1]].label.setBackground(Color.DARK_GRAY);
        }
        }
}
