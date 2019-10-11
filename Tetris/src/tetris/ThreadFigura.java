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
    
    private boolean running,chocar;
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
        tablero.nextPieza();
        setPiezas();
        //tablero.figuraActual;
        for (int i = -3; i <= tablero.FILAS_Y; i++) {
           int columnaActual = tablero.getIndex_x();
                mostrarPieza(columnaActual,i);
            if(chocar == true){
                chocar = false;
                break;
            }
            try {
                //Creo que por aca seria bueno descartivar botones
                sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadFigura.class.getName()).log(Level.SEVERE, null, ex);
            }
                borrarPieza(columnaActual, i);
                llamarFuncion();
        }
        //O desactivar aca
        tablero.checkTablero();
        try {
            sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadFigura.class.getName()).log(Level.SEVERE, null, ex);
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
                pieza.original(4);
        }
        
        boolean choqueDeFigura(){
            
            Figura pieza = tablero.figuraActual;
            pieza.getCoordenadas();
            boolean choque = false;
            for(int i = 0;i<4;i++){
                for(int j = 0;j<2;j++){
                    if(pieza.coordenadas[i][1]<19)
                    if(!tablero.tableroLabels[pieza.coordenadas[i][0]][pieza.coordenadas[i][1]+1].isEmpty())
                        choque = true;
                }
            }
            return choque;
    }
        
    void llamarFuncion(){
        tablero.fullRows();
    }
}
    
    
    

//Si da tiempo un creador de figuras.