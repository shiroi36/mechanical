/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.IO.OUTPUT;
import SA_LIB.IO.*;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Dimension;
import SA_LIB.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import SA_LIB.IO.OUTPUT.frameF;
import javax.swing.JScrollPane;
/**
 *
 * @author araki keita
 */
public class frameF extends JFrame{
//    public static void main(String[] args) {
//        double[][] node={{0,0},{0,1000},{1000,0},{1000,1000}};
//        int[][] memb={{0,1},{1,3},{3,2}};
//        double S=10;
//        WholeFrame model=new WholeFrame(node,memb);
//        outputData out=new outputData(model,S);
//        frameF frame=new frameF(out);
//        new outputEPS("abc.eps",out);
//    }
    panelF panel;
    public frameF(outputData out){
        panel=new panelF(out);
        this.setTitle("main");
        this.setVisible(true);
        this.setSize(400,400);
        this.getContentPane().setBackground(Color.black);
        this.getContentPane().add(panel);
//        this.addComponentListener(new compo(this));
//        frameFMouseLisner fml=new frameFMouseLisner(this,panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public panelF getpanelF(){
        return panel;
    }
    class compo extends ComponentAdapter{
        JFrame f;
        public compo(JFrame frame){
            f=frame;
        }
        public void componentResized(ComponentEvent e){
             Dimension d=e.getComponent().getSize();
        }
    }
}
