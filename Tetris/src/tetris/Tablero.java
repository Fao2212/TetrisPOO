/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.Color;


public class Tablero extends javax.swing.JFrame {

    // cambiar este valor para dimensiones
    public static int COLUMNAS_X = 10;
    public static int FILAS_Y = 20;
    public static int DIMENSION = 30;
    //la columna posucionado
    //Aqui se puede llamar la distancia de la figura
    private int index_x;
    private final int index_y;
    Figura piezas[];
    // Tablero con objetos JButton
    MyJLabel[][] tableroLabels = new MyJLabel[COLUMNAS_X][FILAS_Y];
    ThreadFigura threadFigura;
    Figura figuraActual;
    int score,totalscore;
    
    public Tablero() {
        this.index_x = COLUMNAS_X/2;
        this.index_y = 0;
        this.piezas = new Figura[3];
        this.score = 10;
        this.score = 0;
        initComponents();
        generarTablero();
        threadFigura = new ThreadFigura(this);
        threadFigura.setPiezas();
    
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
    }
    
    public int getIndex_x (){
        return this.index_x;
    }    
    public int decrementIndex_x(){
        if (index_x > 0+Math.abs(figuraActual.minX)&&!choquePorLaIzquierda())
            --index_x;
        return index_x;
    }
    public int incrementIndex_x(){
        if (index_x < COLUMNAS_X-1-Math.abs(figuraActual.maxX)&&!choquePorLaDerecha())
            ++index_x;
        return index_x;
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
            }
        }
        index_x = COLUMNAS_X/2;
    }
    
    void gravedad(int fila){
        limpiarLinea(fila);
        repintar(fila);
        
    }
    void addToScore(int lineas){
        totalscore += score * lineas; 
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
    }//Luego que se encargue de repintar el tablero
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTablero = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btnLEFT = new javax.swing.JButton();
        brtRIGHT = new javax.swing.JButton();
        btnVelocidad = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelTablero.setBackground(new java.awt.Color(102, 102, 102));

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

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnLEFT.setText("<< LEFT");
        btnLEFT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLEFTActionPerformed(evt);
            }
        });

        brtRIGHT.setText("RIGHT >>");
        brtRIGHT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brtRIGHTActionPerformed(evt);
            }
        });

        btnVelocidad.setText("+");
        btnVelocidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVelocidadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(panelTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(btnLEFT)
                        .addGap(178, 178, 178)
                        .addComponent(brtRIGHT))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(203, 203, 203)
                        .addComponent(btnVelocidad, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(89, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jButton1)
                        .addGap(196, 196, 196)
                        .addComponent(btnVelocidad, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLEFT)
                            .addComponent(brtRIGHT))))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        threadFigura.start();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnLEFTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLEFTActionPerformed
        // TODO add your handling code here:
        decrementIndex_x();
    }//GEN-LAST:event_btnLEFTActionPerformed

    private void brtRIGHTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brtRIGHTActionPerformed
        // TODO add your handling code here:
        incrementIndex_x();
    }//GEN-LAST:event_brtRIGHTActionPerformed

    private void btnVelocidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVelocidadActionPerformed
        // TODO add your handling code here:
        threadFigura.decrementVelocidad();
    }//GEN-LAST:event_btnVelocidadActionPerformed

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
    private javax.swing.JButton brtRIGHT;
    private javax.swing.JButton btnLEFT;
    private javax.swing.JButton btnVelocidad;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel panelTablero;
    // End of variables declaration//GEN-END:variables

    
}
