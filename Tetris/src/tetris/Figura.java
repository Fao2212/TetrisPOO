/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

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

    //Color color;
    
   /* void selecFigura(TipoFiguras figura){
        switch(figura){
            case FIGURAL:
               color = Color.ORANGE;
               break;
            case FIGURAT:
                color = Color.MAGENTA;
               break;       
            case FIGURAJ:
                color = Color.BLUE;
               break;
            case FIGURAZ:
                color = Color.RED;
               break;
            case FIGURAS:
                color = Color.GREEN;
               break;
            case FIGURAO:
                color = Color.YELLOW;
               break;
            case FIGURAI:
                color = Color.CYAN;
               break;
        }
    }
}*/

class FiguraL extends Figura{

    public FiguraL() {//choque lateral
        nuevaFigura();
    }
    
    @Override
    void nuevaFigura(){
        this.tipo = TipoFiguras.FIGURAL;
        this.color = Color.RED;
        original(tipo.ordinal()); 
        setMaxX();
        setMaxY();
        setMinY();
    }
    
    @Override
    void rotar(){
        int s = 0;
}
    
    @Override
    void impresion(int x,int y){
        this.ejeDeRotacion.x = x;
        this.ejeDeRotacion.y = y;
        this.setCoordenadas(x, y);
}

}
