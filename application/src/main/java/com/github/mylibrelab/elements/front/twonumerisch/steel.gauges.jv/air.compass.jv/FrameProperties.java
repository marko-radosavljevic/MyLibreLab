/*
 * Copyright (C) 2020 MyLibreLab
 * Based on MyOpenLab by Carmelo Salafia www.myopenlab.de
 * Copyright (C) 2004  Carmelo Salafia cswi@gmx.de
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.github.mylibrelab.elements.front.twonumerisch.steel.gauges.jv.air.compass.jv;/*
                                                                                          * Copyright (C) 2020
                                                                                          * MyLibreLab
                                                                                          * Based on MyOpenLab by
                                                                                          * Carmelo Salafia
                                                                                          * www.myopenlab.de
                                                                                          * Copyright (C) 2004 Carmelo
                                                                                          * Salafia cswi@gmx.de
                                                                                          *
                                                                                          * This program is free
                                                                                          * software: you can
                                                                                          * redistribute it and/or
                                                                                          * modify
                                                                                          * it under the terms of the
                                                                                          * GNU General Public License
                                                                                          * as published by
                                                                                          * the Free Software
                                                                                          * Foundation, either version 3
                                                                                          * of the License, or
                                                                                          * (at your option) any later
                                                                                          * version.
                                                                                          *
                                                                                          * This program is distributed
                                                                                          * in the hope that it will be
                                                                                          * useful,
                                                                                          * but WITHOUT ANY WARRANTY;
                                                                                          * without even the implied
                                                                                          * warranty of
                                                                                          * MERCHANTABILITY or FITNESS
                                                                                          * FOR A PARTICULAR PURPOSE.
                                                                                          * See the
                                                                                          * GNU General Public License
                                                                                          * for more details.
                                                                                          *
                                                                                          * You should have received a
                                                                                          * copy of the GNU General
                                                                                          * Public License
                                                                                          * along with this program. If
                                                                                          * not, see
                                                                                          * <http://www.gnu.org/licenses
                                                                                          * />.
                                                                                          *
                                                                                          */

/**
 *
 * @author Homer
 */
public class FrameProperties extends javax.swing.JDialog {

    /** Creates new form FrameProperties */
    public FrameProperties(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridLayout(4, 2));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jLabel1.setText("Min");
        getContentPane().add(jLabel1);

        getContentPane().add(jTextField1);

        jLabel2.setText("Max");
        getContentPane().add(jLabel2);

        getContentPane().add(jTextField2);

        jLabel3.setText("Abschnitte");
        getContentPane().add(jLabel3);

        getContentPane().add(jTextField3);

        jButton1.setMnemonic('O');
        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        getContentPane().add(jButton1);

        jButton2.setMnemonic('b');
        jButton2.setText("Abbrechen");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        getContentPane().add(jButton2);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - 208) / 2, (screenSize.height - 134) / 2, 208, 134);
    }
    // </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        try {
            min = Double.parseDouble(jTextField1.getText());
            max = Double.parseDouble(jTextField2.getText());
            abschnitte = Double.parseDouble(jTextField3.getText());
            result = true;
            this.dispose();
        } catch (NumberFormatException ex) {
            System.out.println(ex);
            result = false;
        }

    }// GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
        result = false;
        this.dispose();
    }// GEN-LAST:event_jButton2ActionPerformed


    public static void execute(double min, double max, double abschnitte) {
        FrameProperties frm = new FrameProperties(null, true);

        frm.jTextField1.setText("" + min);
        frm.jTextField2.setText("" + max);
        frm.jTextField3.setText("" + abschnitte);

        frm.setVisible(true);
    }

    public static boolean result = false;
    public static double min = 0;
    public static double max = 100;
    public static double abschnitte = 3;


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

}
