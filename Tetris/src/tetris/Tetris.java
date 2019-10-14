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
public class Tetris {

    Tablero tablero;
    MainMenu menu;
   
    public static void main(String[] args) {
        int array[] = new int[10];
        FileManager.writeObject(array, "MejoresPuntuaciones.txt");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }
    
}
