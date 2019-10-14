/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import tetris.ThreadFigura;

/**
 *
 * @author ferol
 */
public abstract class Figura implements Serializable{
    
    Color color;
    Point ejeDeRotacion = new Point();
    int coordenadas [][];
    int valores [][][];
    TipoFiguras tipo;
    int maxY,maxX,minX;
    Figura figuraActualll;
    int rot[][];
    int rotaciones[][][];
    int valor[][];
    int pos;
    
    
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
        pos = 0;
    }
    
    abstract void rotar();
    abstract void impresion(int x,int y);
    abstract void nuevaFigura();
    void reSize(){
        setMinY();
        setMaxX();
        setMaxY();
        System.out.println(maxY);
    }
    void aumentarPos(){
        if( pos<3)
            pos += 1;
        else
            pos = 0;
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
                    coordenadas[i][j] = valor[i][j];
            }
        }
    }
    
    int[][] getCoordenadas(){
        return coordenadas;
    }
    
    void setMaxY(){
        int mayor = valor[0][1];
        for(int i = 1;i<4;i++){
            mayor = Math.max(mayor, valor[i][1]);
            System.out.println(mayor);
    }
        this.maxY = mayor;
    }
    void setMaxX(){
        int mayor = valor[0][0];
        for(int i = 1;i<4;i++){
            mayor = Math.max(mayor, valor[i][0]);
    }
        //System.out.println(mayor);
        this.maxX = mayor;
    }
    void setMinY(){
        int menor = valor[0][0];
        for(int i = 1;i<4;i++){
            menor = Math.min(menor, valor[i][0]);
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
        valor = valores[tipo.ordinal()];
        original(tipo.ordinal()); 
        setMaxX();
        setMaxY();
        setMinY();
    }
    
    @Override
    void rotar(){
        aumentarPos();
       rotaciones = new int [][][]{
            { { 0, -1 }, { 0, 0 }, { 0, 1 }, { 1, 1 } }, //L normal
            { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 1, -1 } }, //Segunda rotación a la izquierda
            { { 0, 1 }, { 0, 0 }, { 0, -1 }, { -1, -1 } }, //Tercera rotación a la izquierda
            { { 1, 0 }, { 0, 0 }, { -1, 0 }, { -1, 1 } }//Cuarta rotación a la izquierda
             };
        for(int i = 0;i<4;i++){
            for(int j = 0;j<2;j++){
                    //coordenadas[i][j] = rotaciones[pos][i][j];
                    valor[i][j] = rotaciones[pos][i][j];
    }
        }
        reSize();
    }
    
    @Override
    void impresion(int x,int y){
        this.ejeDeRotacion.x = x;
        this.ejeDeRotacion.y = y;
        this.setCoordenadas(x, y);
}

}

class FiguraT extends Figura{

        public FiguraT() {
        nuevaFigura();
    }
    @Override
    void rotar() {
        aumentarPos();
        int rotaciones[][][] = {
            { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, -1 } }, //T normal
            { { 0, -1 }, { 0, 0 }, { 0, 1 }, { -1, 0 } }, //Segunda rotación a la izquierda
            { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } }, //Tercera rotación a la izquierda
            { { 0, -1 }, { 0, 0 }, { 0, 1 }, { -1, 0 } }//Cuarta rotación a la izquierda
             };
                for(int i = 0;i<4;i++){
            for(int j = 0;j<2;j++){
                    //coordenadas[i][j] = rotaciones[pos][i][j];
                    valor[i][j] = rotaciones[pos][i][j];
    }
        }
                reSize();
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
        valor = valores[tipo.ordinal()];
        original(tipo.ordinal()); 
        setMaxX();
        setMaxY();
        setMinY();
    }
}
    
    class FiguraZ extends Figura{
        
            public FiguraZ() {
        nuevaFigura();
    }
    @Override
    void rotar() {
        aumentarPos();
       rotaciones = new int [][][]{
            { { -1, -1 }, { 0, -1}, { 0, 0 }, {1, 0 } }, //Z normal
            { { 1, -1 }, { 1, 0 }, { 0, 0 }, { 0, 1 } }, //Segunda rotación a la izquierda
            { { -1, -1 }, { 0, -1}, { 0, 0 }, {1, 0 } }, //Tercera rotación a la izquierda
            { { 1, -1 }, { 1, 0 }, { 0, 0 }, { 0, 1 } }//Cuarta rotación a la izquierda
             };
      for(int i = 0;i<4;i++){
            for(int j = 0;j<2;j++){
                    //coordenadas[i][j] = rotaciones[pos][i][j];
                    valor[i][j] = rotaciones[pos][i][j];
    }
        }
      reSize();
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
        valor = valores[tipo.ordinal()];
        original(tipo.ordinal()); 
        setMaxX();
        setMaxY();
        setMinY();
    }
    }
    
 class FiguraJ extends Figura{

         public FiguraJ() {
        nuevaFigura();
    }
    @Override
    void rotar() {
        aumentarPos();
       rotaciones = new int [][][]{
            { { 0, -1 }, { 0, 0 }, { 0, 1 }, { -1, 1 } }, //J normal
            { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 1, 1 } }, //Segunda rotación a la izquierda
            { { 0, 1 }, { 0, 0 }, { 0, -1 }, { 1, -1 } }, //Tercera rotación a la izquierda
            { { 1, 0 }, { 0, 0 }, { -1, 0 }, { -1, -1 } }//Cuarta rotación a la izquierda
             };
       for(int i = 0;i<4;i++){
            for(int j = 0;j<2;j++){
                    //coordenadas[i][j] = rotaciones[pos][i][j];
                    valor[i][j] = rotaciones[pos][i][j];
    }
        }
       reSize();
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
        valor = valores[tipo.ordinal()];
        original(tipo.ordinal()); 
        setMaxX();
        setMaxY();
        setMinY();
    }
 }   
    
     class FiguraO extends Figura{

             public FiguraO() {
        nuevaFigura();
    }
    @Override
    void rotar() {
        aumentarPos();
       rotaciones = new int [][][]{
            { { -1, -1 }, { 0, -1}, { -1, 0 }, { 0, 0 } }, //O normal
            { { -1, -1 }, { 0, -1}, { -1, 0 }, { 0, 0 } }, //Segunda rotación a la izquierda
            { { -1, -1 }, { 0, -1}, { -1, 0 }, { 0, 0 } }, //Tercera rotación a la izquierda
            { { -1, -1 }, { 0, -1}, { -1, 0 }, { 0, 0 } }//Cuarta rotación a la izquierda
             };
         for(int i = 0;i<4;i++){
            for(int j = 0;j<2;j++){
                    //coordenadas[i][j] = rotaciones[pos][i][j];
                    valor[i][j] = rotaciones[pos][i][j];
    }
        }
         reSize();
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
        valor = valores[tipo.ordinal()];
        original(tipo.ordinal()); 
        setMaxX();
        setMaxY();
        setMinY();
    }
     }

     class FiguraI extends Figura{

             public FiguraI() {
        nuevaFigura();
    }
    @Override
    void rotar() {
        aumentarPos();
        rotaciones = new int [][][]{
            { { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } }, //I normal
            { { -2, 0 }, { -1, 0 }, { 0, 0 }, { 1, 0 } }, //Segunda rotación a la izquierda
            { { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } }, //Tercera rotación a la izquierda
            { { -2, 0 }, { -1, 0 }, { 0, 0 }, { 1, 0 } }//Cuarta rotación a la izquierda
             };
       for(int i = 0;i<4;i++){
            for(int j = 0;j<2;j++){
                    //coordenadas[i][j] = rotaciones[pos][i][j];
                    valor[i][j] = rotaciones[pos][i][j];
    }
        }
       reSize();
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
        valor = valores[tipo.ordinal()];
        original(tipo.ordinal()); 
        setMaxX();
        setMaxY();
        setMinY();
    }
     }
 
        class FiguraS extends Figura{

                public FiguraS() {
        nuevaFigura();
    }
    @Override
    void rotar() {
        aumentarPos();
        rotaciones = new int [][][]{
            { { 1, -1 }, { 0, -1 }, { 0, 0 }, { -1, 0 } }, //S normal
            { { -1, -1 }, { -1, 0 }, { 0, 0 }, { 0, 1 } }, //Segunda rotación a la izquierda
            { { 1, -1 }, { 0, -1 }, { 0, 0 }, { -1, 0 } }, //Tercera rotación a la izquierda
            { { -1, -1 }, { -1, 0 }, { 0, 0 }, { 0, 1 } }//Cuarta rotación a la izquierda
             };
        for(int i = 0;i<4;i++){
            for(int j = 0;j<2;j++){
                    //coordenadas[i][j] = rotaciones[pos][i][j];
                    valor[i][j] = rotaciones[pos][i][j];
    }
        }
        reSize();
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
        valor = valores[tipo.ordinal()];
        original(tipo.ordinal()); 
        setMaxX();
        setMaxY();
        setMinY();
    }
     }
 
        


