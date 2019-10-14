/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import tetris.ThreadFigura;

/**
 *
 * @author ferol
 */
public abstract class Figura {
    
    Color color;
    Point ejeDeRotacion = new Point();
    int coordenadas [][];
    int valores [][][];
    TipoFiguras tipo;
    int maxY,maxX,minX;
    Figura figuraActualll;
    
    Figura(){
        coordenadas = new int[4][2];
        valores = new int[][][]{
        { { -1, -1 },  { 0, -1},   { 0, 0 },   {1, 0 } }, // z
        { { 1, -1 },  { 0, -1 },   { 0, 0 },  { -1, 0 } }, // z al revés
        { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } }, // I
        { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, -1 } }, // T
        { { -1, -1 }, { 0, -1},   { -1, 0 }, { 0, 0 } },  // Cuadrado
        { { 0, -1 },  { 0, 0 },  { 0, 1 },   { 1, 1 } }, // L
        { { 0, -1 },  { 0, 0 },  { 0, 1 },   { -1, 1 } } // L al revés
        };
    }
    
    abstract void rotar();
    abstract void impresion(int x,int y);
    abstract void nuevaFigura();
    
    void detenerCaida(){

    }

    void setCoordenadas(int x,int y){
        for(int i = 0;i<4;i++){
            for(int j = 0;j<2;j++){
                if(j == 0)
                    coordenadas[i][j] += x;
                else
                    coordenadas[i][j] += y;
            }
        }
    }
    void original(int figura){
        for(int i = 0;i<4;i++){
            for(int j = 0;j<2;j++){
                    coordenadas[i][j] = valores[figura][i][j];
            }
        }
    }
    
    int[][] getCoordenadas(){
        return coordenadas;
    }
    
    void setMaxY(){
        int mayor = coordenadas[0][1];
        for(int i = 1;i<4;i++){
            mayor = Math.max(mayor, coordenadas[i][1]);
    }
        this.maxY = mayor;
    }
    void setMaxX(){
        int mayor = coordenadas[0][0];
        for(int i = 1;i<4;i++){
            mayor = Math.max(mayor, coordenadas[i][0]);
    }
        //System.out.println(mayor);
        this.maxX = mayor;
    }
    void setMinY(){
        int menor = coordenadas[0][0];
        for(int i = 1;i<4;i++){
            menor = Math.min(menor, coordenadas[i][0]);
    }
        //System.out.println(menor);
        this.minX = menor;
    }
    
    void imprimirCoordenadas(){
            for(int i = 0;i<4;i++){
            for(int j = 0;j<2;j++){
                System.out.println(coordenadas[i][j]);
            }
        }
}
}
    


enum TipoFiguras{
    FIGURAZ,FIGURAS,FIGURAI,FIGURAT,FIGURAO,FIGURAL,
    FIGURAJ;
}


class FiguraL extends Figura{

    public FiguraL() {
        nuevaFigura();
    }
    
    @Override
    void nuevaFigura(){
        this.tipo = TipoFiguras.FIGURAL;
        this.color = Color.ORANGE;
        original(tipo.ordinal()); 
        setMaxX();
        setMaxY();
        setMinY();
    }
    
    @Override
    void rotar(){
        int s = 0;
        int rotacionesL[][][] = {
            { { 0, -1 }, { 0, 0 }, { 0, 1 }, { 1, 1 } }, //L normal
            { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 1, -1 } }, //Segunda rotación a la izquierda
            { { 0, 1 }, { 0, 0 }, { 0, -1 }, { -1, -1 } }, //Tercera rotación a la izquierda
            { { 1, 0 }, { 0, 0 }, { -1, 0 }, { -1, 1 } }//Cuarta rotación a la izquierda
             };
        }

    
    @Override
    void impresion(int x,int y){
        this.ejeDeRotacion.x = x;
        this.ejeDeRotacion.y = y;
        this.setCoordenadas(x, y);
}

}

class FiguraT extends Figura{

    @Override
    void rotar() {
        int s = 0;
        int rotacionesT[][][] = {
            { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, -1 } }, //T normal
            { { 0, -1 }, { 0, 0 }, { 0, 1 }, { -1, 0 } }, //Segunda rotación a la izquierda
            { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } }, //Tercera rotación a la izquierda
            { { 0, -1 }, { 0, 0 }, { 0, 1 }, { -1, 0 } }//Cuarta rotación a la izquierda
             };
        }
    

    @Override
    void impresion(int x, int y) {
        this.ejeDeRotacion.x = x;
        this.ejeDeRotacion.y = y;
        this.setCoordenadas(x, y);
    }

    @Override
    void nuevaFigura() {
        this.tipo = TipoFiguras.FIGURAT;
        this.color = Color.YELLOW;
        original(tipo.ordinal()); 
        setMaxX();
        setMaxY();
        setMinY();
    }
}
    
    class FiguraZ extends Figura{

    @Override
    void rotar() {
        int s = 0;
        int rotacionesZ[][][] = {
            { { -1, -1 }, { 0, -1}, { 0, 0 }, {1, 0 } }, //Z normal
            { { 1, -1 }, { 1, 0 }, { 0, 0 }, { 0, 1 } }, //Segunda rotación a la izquierda
            { { -1, -1 }, { 0, -1}, { 0, 0 }, {1, 0 } }, //Tercera rotación a la izquierda
            { { 1, -1 }, { 1, 0 }, { 0, 0 }, { 0, 1 } }//Cuarta rotación a la izquierda
             };
        }
    
    

    @Override
    void impresion(int x, int y) {
        this.ejeDeRotacion.x = x;
        this.ejeDeRotacion.y = y;
        this.setCoordenadas(x, y);
    }

    @Override
    void nuevaFigura() {
        this.tipo = TipoFiguras.FIGURAZ;
        this.color = Color.MAGENTA;
        original(tipo.ordinal()); 
        setMaxX();
        setMaxY();
        setMinY();
    }
    }
    
 class FiguraJ extends Figura{

    @Override
    void rotar() {
        int s = 0;
        int rotacionesJ[][][] = {
            { { 0, -1 }, { 0, 0 }, { 0, 1 }, { -1, 1 } }, //J normal
            { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 1, 1 } }, //Segunda rotación a la izquierda
            { { 0, 1 }, { 0, 0 }, { 0, -1 }, { 1, -1 } }, //Tercera rotación a la izquierda
            { { 1, 0 }, { 0, 0 }, { -1, 0 }, { -1, -1 } }//Cuarta rotación a la izquierda
             };
    }
    
    @Override
    void impresion(int x, int y) {
        this.ejeDeRotacion.x = x;
        this.ejeDeRotacion.y = y;
        this.setCoordenadas(x, y);
    }

    @Override
    void nuevaFigura() {
        this.tipo = TipoFiguras.FIGURAJ;
        this.color = Color.BLUE;
        original(tipo.ordinal()); 
        setMaxX();
        setMaxY();
        setMinY();
    }
 }   
    
     class FiguraO extends Figura{

    @Override
    void rotar() {
        int s = 0;
        int rotacionesO[][][] = {
            { { -1, -1 }, { 0, -1}, { -1, 0 }, { 0, 0 } }, //O normal
            { { -1, -1 }, { 0, -1}, { -1, 0 }, { 0, 0 } }, //Segunda rotación a la izquierda
            { { -1, -1 }, { 0, -1}, { -1, 0 }, { 0, 0 } }, //Tercera rotación a la izquierda
            { { -1, -1 }, { 0, -1}, { -1, 0 }, { 0, 0 } }//Cuarta rotación a la izquierda
             };
    }
    
    @Override
    void impresion(int x, int y) {
        this.ejeDeRotacion.x = x;
        this.ejeDeRotacion.y = y;
        this.setCoordenadas(x, y);
    }

    @Override
    void nuevaFigura() {
        this.tipo = TipoFiguras.FIGURAO;
        this.color = Color.PINK;
        original(tipo.ordinal()); 
        setMaxX();
        setMaxY();
        setMinY();
    }
     }

     class FiguraI extends Figura{

    @Override
    void rotar() {
        int s = 0;
        int rotacionesI[][][] = {
            { { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } }, //I normal
            { { -2, 0 }, { -1, 0 }, { 0, 0 }, { 1, 0 } }, //Segunda rotación a la izquierda
            { { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } }, //Tercera rotación a la izquierda
            { { -2, 0 }, { -1, 0 }, { 0, 0 }, { 1, 0 } }//Cuarta rotación a la izquierda
             };
    }
    
    @Override
    void impresion(int x, int y) {
        this.ejeDeRotacion.x = x;
        this.ejeDeRotacion.y = y;
        this.setCoordenadas(x, y);
    }

    @Override
    void nuevaFigura() {
        this.tipo = TipoFiguras.FIGURAI;
        this.color = Color.LIGHT_GRAY;
        original(tipo.ordinal()); 
        setMaxX();
        setMaxY();
        setMinY();
    }
     }
 
        class FiguraS extends Figura{

    @Override
    void rotar() {
        int s = 0;
        int rotacionesS[][][] = {
            { { 1, -1 }, { 0, -1 }, { 0, 0 }, { -1, 0 } }, //S normal
            { { -1, -1 }, { -1, 0 }, { 0, 0 }, { 0, 1 } }, //Segunda rotación a la izquierda
            { { 1, -1 }, { 0, -1 }, { 0, 0 }, { -1, 0 } }, //Tercera rotación a la izquierda
            { { -1, -1 }, { -1, 0 }, { 0, 0 }, { 0, 1 } }//Cuarta rotación a la izquierda
             };
    }
    
    @Override
    void impresion(int x, int y) {
        this.ejeDeRotacion.x = x;
        this.ejeDeRotacion.y = y;
        this.setCoordenadas(x, y);
    }

    @Override
    void nuevaFigura() {
        this.tipo = TipoFiguras.FIGURAS;
        this.color = Color.GREEN;
        original(tipo.ordinal()); 
        setMaxX();
        setMaxY();
        setMinY();
    }
     }
 
        


