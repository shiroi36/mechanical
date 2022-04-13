/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.IO.OUTPUT;
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
public class DesktopFrame extends JFrame{
    panelF panelF;
    JInternalFrame frame1;
    public DesktopFrame(outputData out){
        JDesktopPane desktop=new JDesktopPane();
        this.setTitle("main");
        this.getContentPane().add(desktop,BorderLayout.CENTER);
        frame1=this.InternalFrameTemplate("FRAME", 0, 0, 400, 400);
       
        panelF=new panelF(out);
        frame1.setBackground(new Color(34,53,70));
        frame1.getContentPane().add(panelF);
        frameFMouseLisner mouse=new frameFMouseLisner(frame1,panelF,out);
        frame1.addMouseListener(mouse);
        frame1.addMouseMotionListener(mouse);
        frame1.addMouseWheelListener(mouse);
        desktop.add(frame1);
        desktop.setDesktopManager(new MyDesktopManager());
        desktop.setBackground(new Color(234,224,213));
        new frameFkeyevent(frame1,panelF,out);


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
            if(f==frame1){
                panelF.resetLocation();
                panelF.repaint();
            }
        }
    }
}
