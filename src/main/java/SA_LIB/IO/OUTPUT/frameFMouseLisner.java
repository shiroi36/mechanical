/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.IO.OUTPUT;
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
public class frameFMouseLisner extends MouseAdapter{
    JInternalFrame frame;
    panelF panel;
    outputData outputdata;
    int difx=5;//contentpane座標とframeF座標とのずれ
    int dify=33;
    Point p0;
    double mag;
    int margin=30;
    public frameFMouseLisner(JInternalFrame frame,panelF panel, outputData outputdata){
        mag=outputdata.getMag();
        this.frame=frame;
        this.panel=panel;
        this.outputdata=outputdata;
    }

    public void mousePressed(MouseEvent e){
            int button=e.getModifiersEx();
            if((button&MouseEvent.BUTTON1_DOWN_MASK)!=0){
                return;
            }
            if((button&MouseEvent.BUTTON2_DOWN_MASK)!=0){
                JComponent source=(JComponent)e.getSource();
                p0=SwingUtilities.convertPoint(source, e.getPoint(), panel);
                return;
            }
            if((button&MouseEvent.BUTTON3_DOWN_MASK)!=0){
                panel.resetLocation();
                mag=1;
                panel.setMag(mag);
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
            Point p2=SwingUtilities.convertPoint(source, e.getPoint(), frame);
            int x=p2.x-difx-p0.x;
            int y=p2.y-dify-p0.y;//フレーム分として4,30が余計にp2にある
            int Pwidth=panel.getWidth();//おそらくこのパネルサイズ
            int Pheight=panel.getHeight();
            if((x+Pwidth)<=margin){
                x=margin-Pwidth;
            }
            else if(x>=(Fwidth-margin)){
                x=Fwidth-margin;
            }
            if((y+Pheight)<=(margin)){
                y=margin-Pheight;
            }
            else if(y>=(Fheight-margin-dify)){
                y=Fheight-margin-dify;
            }
            panel.setLocale(x, y);//フレーム分として4,30が余計にp2にある

            panel.repaint();
        }
    }
    public void mouseWheelMoved(MouseWheelEvent e){
        int x=0;
        int y=0;
        int Fwidth=frame.getWidth();
        int Fheight=frame.getHeight();
        int Pwidth=panel.getWidth();//おそらくこのパネルサイズ
        int Pheight=panel.getHeight();
        int wr=e.getWheelRotation();

        double dmag=0.2;
        //ここで変形後の拡大率を求める
        mag+=-wr*dmag;
        BigDecimal gd=new BigDecimal(mag);
        mag=gd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
        double mag2=mag/(mag+wr*dmag);
        double limit=2*dmag;
        if(mag<limit){
            mag=limit;
            mag2=1;
        }
        JComponent source=(JComponent)e.getSource();
        Point p1=SwingUtilities.convertPoint(source, e.getPoint(), panel);
        Point p2=SwingUtilities.convertPoint(source, e.getPoint(), frame);
        if(mag<=1){
            int clear=outputdata.getClearance();
            if(p1.x<=clear){
                x=(int)(p2.x-difx-p1.x);
            }
            else if(p1.x>=(Pwidth-clear)){
                x=(int)(p2.x-difx-mag2*(Pwidth-2*clear)-(p1.x-Pwidth+2*clear));
            }
            else{
                x=(int)(p2.x-difx-clear-mag2*(p1.x-clear));
            }
            if(p1.y<=clear){
                y=(int)(p2.y-dify-p1.y);
            }
            else if(p1.y>=(Pheight-clear)){
                y=(int)(p2.y-dify-mag2*(Pheight-2*clear)-(p1.y-Pheight+2*clear));
            }
            else{
                y=(int)(p2.y-dify-clear-mag2*(p1.y-clear));
            }
        }
        else{
            x=(int)(p2.x-difx-mag2*p1.x);//フレーム分として4,30が余計にp2にある
            y=(int)(p2.y-dify-mag2*p1.y);//フレーム分として4,30が余計にp2にある
        }
        if(mag==1&&mag2<1){
            x=(int)(p2.x-difx-mag2*p1.x);
            y=(int)(p2.y-dify-mag2*p1.y);
        }
        if((x+Pwidth)<=margin){
            x=margin-Pwidth;
        }
        else if(x>=(Fwidth-margin)){
            x=Fwidth-margin;
        }
        if((y+Pheight)<=(margin)){
            y=margin-Pheight;
        }
        else if(y>=(Fheight-margin-dify)){
            y=Fheight-margin-dify;
        }
        panel.setMag(mag);
        panel.setLocale(x,y);
        panel.repaint();
    }
}
