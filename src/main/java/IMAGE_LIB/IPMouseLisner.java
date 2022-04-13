/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IMAGE_LIB;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.SwingUtilities;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import java.math.BigDecimal;
/**
 *
 * @author keita
 */
public class IPMouseLisner extends MouseAdapter{
    JInternalFrame frame;
    ImagePanel panel;
    int difx=5;//contentpane座標とframeF座標とのずれ
    int dify=33;
    Point p0;
    Point panel0;
    double mag;
    int margin=30;
    public IPMouseLisner(JInternalFrame frame,ImagePanel panel){
        mag=panel.getMag();
        this.frame=frame;
        this.panel=panel;
    }

    public void mousePressed(MouseEvent e){
            int button=e.getModifiersEx();
            if((button&MouseEvent.BUTTON1_DOWN_MASK)!=0){
                JComponent source=(JComponent)e.getSource();
                Point p00=SwingUtilities.convertPoint(source, e.getPoint(), panel);
                Point p01=panel.getImageLocation();
                double s=panel.getMag();
                int x2=(int)((p00.x-p01.x)/s);
                int y2=(int)((p00.y-p01.y)/s);                
                System.out.println(x2+"\t"+y2);
                return;
            }
            if((button&MouseEvent.BUTTON2_DOWN_MASK)!=0){
                JComponent source=(JComponent)e.getSource();
                p0=SwingUtilities.convertPoint(source, e.getPoint(), panel);
                panel0=panel.getImageLocation();
//                System.out.println(p0.x+"   "+p0.y);
                return;
            }
            if((button&MouseEvent.BUTTON3_DOWN_MASK)!=0){
                panel.resetLocation();
                panel.setFitSize();
                mag=panel.getMag();
                panel.repaint();
                return;
            }
        }
    public void mouseDragged(MouseEvent e){
        int button=e.getModifiersEx();
        int Fwidth=frame.getWidth();
        int Fheight=frame.getHeight();
        if((button&MouseEvent.BUTTON2_DOWN_MASK)!=0){
            JComponent source=(JComponent)e.getSource();
            Point p2=SwingUtilities.convertPoint(source, e.getPoint(), panel);
            int x=panel0.x+p2.x-p0.x;
            int y=panel0.y+p2.y-p0.y;//フレーム分として4,30が余計にp2にある
            panel.setLocale(x, y);//フレーム分として4,30が余計にp2にある
            panel.repaint();
        }
    }
    public void mouseWheelMoved(MouseWheelEvent e){
        int x=0;
        int y=0;
        int wr=e.getWheelRotation();

        double dmag=0.05;
        //ここで変形後の拡大率を求める
        mag+=-wr*dmag;
        BigDecimal gd=new BigDecimal(mag);
        mag=gd.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
        double mag2=mag/(mag+wr*dmag);
        double limit=2*dmag;
        if(mag<limit){
            mag=limit;
            mag2=1;
        }
        JComponent source=(JComponent)e.getSource();
        Point p1=panel.getImageLocation();
        Point p2=SwingUtilities.convertPoint(source, e.getPoint(), panel);

        x=(int)(p2.x-mag2*(p2.x-p1.x));
        y=(int)(p2.y-mag2*(p2.y-p1.y));

        panel.setMag(mag);
        panel.setLocale(x,y);
        panel.repaint();
    }
}
