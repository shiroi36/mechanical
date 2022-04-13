/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.IO.OUTPUT;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JComponent;
/**
 *
 * @author araki keita
 */
public class panelF extends JPanel{
    private double mag;
    private Point p0;
    private Point locale0=new Point(0,0);//ここで親パネルにたいする初期位置を決める。
    private Point locale=new Point();
    private int Fwidth;
    private int Fheight;
    private outputData outputdata;
    private JComponent component;
         public panelF(outputData out){
             locale.setLocation(locale0);
             outputdata=out;
            this.setSize(outputdata.getPreferedSize());
            this.setLocation(locale);
            mag=out.getMag();
         }
        public void paintComponent(Graphics g){
            System.out.println("現在のズームの倍率は　"+(int)(mag*100)+"%"+locale);
            Color bgColor=new Color(34,53,70);
//            Color frameColor=Color.white;
//            Color zeroColor=Color.GREEN;
//            Color AxisColor=Color.MAGENTA;
//            Color ShearColor=Color.YELLOW;
//            Color MomentColor=Color.cyan;
            outputdata.setMag(mag);
//            outputdata.setFrameColor( frameColor, zeroColor);
//            outputdata.setStressColor(AxisColor, ShearColor, MomentColor);
            this.setSize(outputdata.getPreferedSize());
            this.setLocation(locale);
            g.setColor(bgColor);
            g.fillRect(0, 0, this.getWidth(), this.getWidth());
            g.setColor(Color.yellow);
            g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
            outputdata.draw(g);
        }
        public Dimension getPreferredSize(){
            return new Dimension(this.getWidth(),this.getHeight());
        }
        public void resetLocation(){
            locale.setLocation(locale0);
        }
        public void setLocale(int x, int y){
            locale.setLocation(x, y);
        }
        public void setMag(double mag){
            this.mag=mag;
        }
}
