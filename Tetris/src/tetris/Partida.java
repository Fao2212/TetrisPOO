/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.io.Serializable;

/**
 *
 * @author ferol
 */
public class Partida implements Serializable{
    
    String nombre;//Para el nombre un default con un contador
    int index_y;
    int index_x;
    Figura figuraActual;
    int score;
    Figura piezas[];
    
    Partida(){

    }
    
    void saveData(Partida partida){
        System.out.println(partida);
        String path = "C:\\Users\\ferol\\Documents\\NetBeansProjects\\Tetris\\SavedGames"+"\\"+partida.nombre+".txt";
        FileManager.writeObject(partida, path);      
    }
    
    Partida loadData(String nombre){
        String path = "C:\\Users\\ferol\\Documents\\NetBeansProjects\\Tetris\\SavedGames\\"+nombre;
        Partida partida = (Partida)FileManager.readObject(path);
        return partida;   
    }
}
