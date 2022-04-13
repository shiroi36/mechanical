/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.IO.INPUT;
import SA_LIB.IO.INPUT.panel.DrawingFramePanel;
import SA_LIB.IO.INPUT.panel.DrawingFrame;
import SA_LIB.IO.INPUT.panel.DrawingFrameControlPanel;
import SA_LIB.IO.INPUT.InputData;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.DefaultDesktopManager;
/**
 *
 * @author Araki Keita
 */
public class InputDestopFrame extends JFrame{
    DrawingFramePanel panelF;
    JInternalFrame frame1;
    public static void main(String[] args) {
        new InputDestopFrame(100);
    }
    public InputDestopFrame(double Scale){
        JDesktopPane desktop=new JDesktopPane();
        this.setTitle("main");
        this.getContentPane().add(desktop,BorderLayout.CENTER);

        InputData in=new InputData(Scale);
        in.addNode(1000, 1000);
        in.addNode(-1000, -1000);
        in.addNode(-2000, -2000);
        DrawingFrame f=new DrawingFrame(in);
        f.setLocation(100, 0);
        DrawingFrameControlPanel cp=new DrawingFrameControlPanel(in,f);
        desktop.add(f);
        desktop.add(cp);
        desktop.setDesktopManager(new MyDesktopManager());
        desktop.setBackground(new Color(234,224,213));



        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,600);
        this.setVisible(true);
    }
    public JInternalFrame InternalFrameTemplate(String title,int x,int y,int w,int h){
        JInternalFrame f=new JInternalFrame(title);
        f.setMaximizable(true);
        f.setClosable(false);
        f.setIconifiable(true);
        f.setFocusable(true);
        f.setResizable(true);
        f.setBounds(x,y,w,h);
        f.setVisible(true);
        return f;
    }
    class MyDesktopManager extends DefaultDesktopManager{
        public void minimizeFrame(JInternalFrame f){
            super.minimizeFrame(f);
            if(f instanceof DrawingFrame){
                DrawingFrame frame=(DrawingFrame)f;
                frame.resetLocation();
                frame.repaint();
            }
        }
    }
}
