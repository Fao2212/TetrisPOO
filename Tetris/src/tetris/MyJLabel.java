/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.Color;
import java.io.Serializable;
import javax.swing.JLabel;

/**
 *
 * @author diego
 */
public class MyJLabel implements  Serializable{
    public static final Color DEFAULT_COLOR = new Color(25, 10, 51);
    public JLabel label;
    private boolean empty = false;

    public MyJLabel() {
        label = new JLabel();
        label.setOpaque(true);
        label.setBackground(Color.DARK_GRAY);
        label.setText("");
        label.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }    
}
