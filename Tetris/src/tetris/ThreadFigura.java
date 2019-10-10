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
        for (int i = -3; i < tablero.FILAS_Y; i++) {
           int columnaActual = tablero.getIndex_x();
                mostrarPieza(columnaActual,i);
            try {
                sleep(600);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadFigura.class.getName()).log(Level.SEVERE, null, ex);
            }
                borrarPieza(columnaActual, i);
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
        Figura pieza = tablero.nextPieza();
        setPiezas();
        pieza.setCoordenadas(columnaActual,filaActual);
        tablero.figuraActual  = pieza;
        for(int i = 0;i<4;i++){
            //System.out.println(pieza.maxY);
            if(filaActual>0 &&  filaActual<Tablero.FILAS_Y-tablero.figuraActual.maxY)
            tablero.tableroLabels[pieza.coordenadas[i][0]][pieza.coordenadas[i][1]].label.setBackground(pieza.color);
        }
    }
    public void borrarPieza(int columnaActual,int filaActual){
        Figura pieza = tablero.figuraActual;
        pieza.getCoordenadas();
        for(int i = 0;i<4;i++){
            if(filaActual>0 &&  filaActual<Tablero.FILAS_Y-1-tablero.figuraActual.maxY)
                tablero.tableroLabels[pieza.coordenadas[i][0]][pieza.coordenadas[i][1]].label.setBackground(Color.DARK_GRAY);
        }
        }
    }
    
    
    

