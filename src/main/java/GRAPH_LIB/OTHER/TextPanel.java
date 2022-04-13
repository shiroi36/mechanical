/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GRAPH_LIB.OTHER;

import GRAPH_LIB.GP.GPInterface;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author keita
 */
public class TextPanel extends javax.swing.JPanel  implements GPInterface{

    public static void main(String[] args) {
        TextPanel tp=new TextPanel();
        tp.PLOT("");
    }
    private String gname;
    /**
     * Creates new form TextPanel2
     */
    public TextPanel() {
        this("");
    }

    public TextPanel(String text){
        this(new String[]{text},18);
    }
    public TextPanel(String text,int fontsize){
        this(new String[]{text},fontsize);
    }
    public TextPanel(String text[]){
        this(text,18);
    }
    public TextPanel(String[] text,int fontsize){
        initComponents();
        jTextArea1.setFont(new java.awt.Font("ゆたぽん（コーディング）", 0, fontsize)); // NOI18N
        for (int i = 0; i < text.length; i++) {
            jTextArea1.append(text[i]+"\n");     
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(253, 239, 242));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("ゆたぽん（コーディング）", 0, 18)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    public JPanel getPanel() {
        return this;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setStatuslabel(JLabel label) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void PLOT(String FrameTitle){
        JFrame jf = new JFrame();
        jf.getContentPane().add(this, BorderLayout.CENTER);
        jf.setTitle(FrameTitle);
        jf.setVisible(true);
        jf.setBounds(100, 100, 700, 300);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void setGraphName(String name){this.gname =name;}
    public String getName(){return gname;}
    
}
