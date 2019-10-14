/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;


public class Tablero extends javax.swing.JFrame {

    // cambiar este valor para dimensiones
    public static int COLUMNAS_X = 10;
    public static int FILAS_Y = 23;
    public static int DIMENSION = 25;
    private int index_x;
    private int index_y;
    ShowShape figura1,figura2;
    Figura piezas[]; //Hacer arayList y add 3 piezas nuevas
    MyJLabel[][] tableroLabels = new MyJLabel[COLUMNAS_X][FILAS_Y];
    ThreadFigura threadFigura;
    Figura figuraActual; 
    int score,totalscore;
    Partida partida;
    boolean vaARotar;
    int lineas;
    int level;
    int mejoresPuntuaciones[];
    
    public Tablero() {
        this.index_x = COLUMNAS_X/2;
        this.index_y = 0;
        this.piezas = new Figura[3];
        this.totalscore = 0;
        this.score = 1;
        this.level = 1;
        lineas = 0;
        initComponents();
        generarTablero();
        threadFigura = new ThreadFigura(this);
        threadFigura.setPiezas();
        crearPartida();
        this.labelLevel.setText(String.valueOf(level));
        this.labelLineas.setText(String.valueOf(lineas));
        this.labelScore.setText(String.valueOf(totalscore));
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(new TAdapter());
        
    
    }
    
    public void generarTablero(){
    for(int i=0;i<COLUMNAS_X;i++)
        for(int j=0;j<FILAS_Y;j++)
        {
                // coloca imagen a todos vacio
                tableroLabels[i][j] = new MyJLabel();
                tableroLabels[i][j].setEmpty(true);
                

                panelTablero.add(tableroLabels[i][j].label);
                // coloca dimensiones y localidad
                tableroLabels[i][j].label.setBounds(DIMENSION*i, DIMENSION*j, DIMENSION, DIMENSION);

                panelTablero.setSize(DIMENSION*COLUMNAS_X, DIMENSION*FILAS_Y);
                
        }
        tableroInvisible();
        figura1 = new ShowShape(DIMENSION, panelFigura1);
        figura2 = new ShowShape(DIMENSION, panelFigura2);
    }
    public void tableroInvisible(){
        for (int i = 0; i < COLUMNAS_X; i++) {
            for (int j = 0; j < 4; j++) {
                tableroLabels[i][j].label.setVisible(false);
            }
        }
    }
    
    public void setLEVEL(String text){
        this.labelLevel.setText(text);
    }
    public void setIndex_y(int filaActual){
        this.index_y = filaActual;
    }
    public int getIndex_y(){
        return this.index_y;
    }
    public int getIndex_x (){
        return this.index_x;
    }    
    public int decrementIndex_x(){
        if (index_x > 0+Math.abs(figuraActual.minX)&&!choquePorLaIzquierda()&&index_y >2)
            --index_x;
        return index_x;
    }
    public int incrementIndex_x(){
        if (index_x < COLUMNAS_X-1-Math.abs(figuraActual.maxX)&&!choquePorLaDerecha()&&index_y >2)
            ++index_x;
        return index_x;
    }
    
    public void showPieza(){
        figura1.mostrar(piezas[0]);
        figura2.mostrar(piezas[1]);
    }
    
    public void nextPieza(){
        figuraActual = piezas[0];
        piezas[0] = piezas[1];
        piezas[1] = piezas[2];
        piezas[2] = null;
        //return temp;
    }
    
    public boolean choquePorLaDerecha(){//Revisar la condicion con movimientos muy rapidos se come una figura
            Figura pieza = figuraActual;
            pieza.getCoordenadas();
            for(int i = 0;i<4;i++){
                 if(pieza.coordenadas[i][0]==pieza.maxX)  
                     System.out.println(pieza.coordenadas[i][0]+1);
                     if (!tableroLabels[pieza.coordenadas[i][0]+1][pieza.coordenadas[i][1]].isEmpty()){
                        return true;
                     }
            }
            return false;
    }
    public boolean choquePorLaIzquierda(){
            Figura pieza = figuraActual;
            pieza.getCoordenadas();
            for(int i = 0;i<4;i++){
                 if(pieza.coordenadas[i][0]==pieza.minX)  
                     System.out.println(pieza.coordenadas[i][0]-1);
                     if (!tableroLabels[pieza.coordenadas[i][0]-1][pieza.coordenadas[i][1]].isEmpty()){
                        return true;
                     }
            }
            return false;
    }
            

    
    public void checkTablero(){
        for (int i = COLUMNAS_X-1; i > -1; i--) {
            for (int j = FILAS_Y-1; j > -1; j--) {
                if (tableroLabels[i][j].label.getBackground()!= Color.DARK_GRAY){ 
                    tableroLabels[i][j].setEmpty(false);
                    }
                else
                    tableroLabels[i][j].setEmpty(true); 
            }}

                index_x = COLUMNAS_X/2;
    }
    
    void gravedad(int fila){
        limpiarLinea(fila);
        repintar(fila);
        
    }
    void addToScore(int lineas){
        this.lineas += lineas;
        if(lineas == 1)
        totalscore += 1; 
        else if(lineas == 3)
            totalscore += 4;
        else if(lineas ==4)
            totalscore += 5;    
    }
    void limpiarLinea(int fila){
        for(int i = 0;i<10;i++){//Esto puede ser funcion limpiarFila
            tableroLabels[i][fila].setEmpty(true);
            tableroLabels[i][fila].label.setBackground(Color.DARK_GRAY);
        }
    }
    
    void repintar(int fila){
        for(int i = fila;i>0;i--){
            for (int j = 0; j < 10; j++) {
                tableroLabels[j][i].label.setBackground(tableroLabels[j][i-1].label.getBackground());
            }
        }
        checkTablero();
    }
    
    
    private void crearPartida(){
        this.partida = new Partida();
    }
    
    private void cargaPartida(){
    for (int i = 0; i < COLUMNAS_X; i++) {
            for (int j = 0; j < FILAS_Y; j++) {
                this.tableroLabels[i][j].label.setBackground(partida.tablero[i][j].label.getBackground());
            }
        }
        for (int i = 0; i < 3; i++) {
            this.piezas = partida.piezas;
        }
        this.index_x = partida.index_x;
        this.index_y = partida.index_y;
        this.totalscore = partida.score;
        this.figuraActual = partida.figuraActual;
        this.lineas = partida.lineas;
        this.level = partida.level;
    }
    
    void saveGame(String nombre){
        partida.nombre = nombre;
        dataToSave();
        partida.saveData(partida);
    }
    
    void dataToSave(){
        //Show ventana de escribir el nombre de la partida
        //this.partiada.nombre = ventana.getText();
        for (int i = 0; i < COLUMNAS_X; i++) {
            for (int j = 0; j < FILAS_Y; j++) {
                System.out.println(this.partida.tablero[i][j]+"1");
                System.out.println(tableroLabels[i][j]+"2");
                this.partida.tablero[i][j] = tableroLabels[i][j];
            }
        }
        for (int i = 0; i < 3; i++) {
                this.partida.piezas[i] = piezas[i];  
        }
        this.partida.index_x = this.index_x;
        this.partida.index_y =  this.index_y;
        this.partida.piezas = this.piezas;
        this.partida.score = this.totalscore;
        this.partida.level = this.level;
        this.partida.lineas = this.lineas;
    }
    
    void loadGame(String nombre){
        this.partida = partida.loadData(nombre);
        System.out.println(partida);
        cargaPartida();
        this.setVisible(true);
        this.threadFigura.start();
    }
    
    void fullRows(){
        boolean fullRow = true;
        int cantidadDeLineas = 0;
        for(int i = FILAS_Y-1;i>-1;i--){
          for(int j = COLUMNAS_X-1;j>-1;j--){
            if(tableroLabels[j][i].isEmpty()){
                  fullRow = false;
                  break;
              }
          }
            if(fullRow == false){
                fullRow = true;
            }
            else{
                cantidadDeLineas ++;
                gravedad(i);
                fullRow = true;
            }
         
        }
        addToScore(cantidadDeLineas);
        labelLineas.setText(String.valueOf(lineas));
        labelScore.setText(String.valueOf(totalscore));
    }
    
    void playMusic(){
        try{
         File musicPath = new File("tetris.wav");
         if(musicPath.exists()){
            AudioInputStream audioinput = AudioSystem.getAudioInputStream(musicPath);
            Clip clip = AudioSystem.getClip();
            clip.open(audioinput);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
         }
         else
             System.out.println("No se encuentra archivo");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
class TAdapter extends KeyAdapter {
         @Override
         public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();  
        switch(keyCode){
            case KeyEvent.VK_LEFT:
                decrementIndex_x();
                break;
            case KeyEvent.VK_RIGHT:
                incrementIndex_x();
                break;
           case KeyEvent.VK_DOWN:
                threadFigura.decrementVelocidad();
                break;
           case KeyEvent.VK_UP:
               vaARotar();     
               break; 

    }
         }
}

void vaARotar(){
    this.vaARotar = true;
}

void rotar(){
    
   figuraActual.rotar();
   
    
}


//Luego que se encargue de repintar el tablero
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        panelTablero = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        panelFigura1 = new javax.swing.JPanel();
        panelFigura2 = new javax.swing.JPanel();
        label1 = new java.awt.Label();
        labelScore = new java.awt.Label();
        label2 = new java.awt.Label();
        labelLevel = new java.awt.Label();
        label3 = new java.awt.Label();
        labelLineas = new java.awt.Label();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        panelTablero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                panelTableroKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelTableroLayout = new javax.swing.GroupLayout(panelTablero);
        panelTablero.setLayout(panelTableroLayout);
        panelTableroLayout.setHorizontalGroup(
            panelTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 301, Short.MAX_VALUE)
        );
        panelTableroLayout.setVerticalGroup(
            panelTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        jButton2.setText("Pause");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        panelFigura1.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout panelFigura1Layout = new javax.swing.GroupLayout(panelFigura1);
        panelFigura1.setLayout(panelFigura1Layout);
        panelFigura1Layout.setHorizontalGroup(
            panelFigura1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 124, Short.MAX_VALUE)
        );
        panelFigura1Layout.setVerticalGroup(
            panelFigura1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 125, Short.MAX_VALUE)
        );

        panelFigura2.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout panelFigura2Layout = new javax.swing.GroupLayout(panelFigura2);
        panelFigura2.setLayout(panelFigura2Layout);
        panelFigura2Layout.setHorizontalGroup(
            panelFigura2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 124, Short.MAX_VALUE)
        );
        panelFigura2Layout.setVerticalGroup(
            panelFigura2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 125, Short.MAX_VALUE)
        );

        label1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label1.setText("SCORE:");

        label2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label2.setText("LEVEL:");

        label3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label3.setText("LINEAS:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(panelFigura2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(panelFigura1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(26, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelLevel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelLineas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelScore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelLineas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(panelFigura1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelFigura2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        threadFigura.pause();
        new formSave (this).setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void panelTableroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_panelTableroKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_panelTableroKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Tablero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tablero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tablero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tablero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>max

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tablero().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label labelLevel;
    private java.awt.Label labelLineas;
    private java.awt.Label labelScore;
    private javax.swing.JPanel panelFigura1;
    private javax.swing.JPanel panelFigura2;
    private javax.swing.JPanel panelTablero;
    // End of variables declaration//GEN-END:variables

    
}

class ShowShape{
    
    MyJLabel[][] grid = new MyJLabel[5][5];
    Figura figura;
    int dimension;

    public ShowShape(int dimension,JPanel panel) {
        this.dimension = dimension;
        createGrid(panel);
    }
    
    void createGrid(JPanel panel){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                
                grid[i][j] = new MyJLabel();
                grid[i][j].setEmpty(true);
                

                panel.add(grid[i][j].label);
                // coloca dimensiones y localidad
                grid[i][j].label.setBounds(dimension*i, dimension*j, dimension, dimension);

                panel.setSize(dimension*3, dimension*3);
            }
        }
    }
    
    void mostrar(Figura pieza){
        for (int i = 0; i < 4; i++) {
            grid[pieza.coordenadas[i][0]+2][pieza.coordenadas[i][1]+2].label.setBackground(pieza.color);
        }
    }
    
    void clearGrid(){
               for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j].label.setBackground(Color.DARK_GRAY);
            }
            } 
    }
    
}