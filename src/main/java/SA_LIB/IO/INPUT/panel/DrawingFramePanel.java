/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.IO.INPUT.panel;
import SA_LIB.IO.INPUT.InputData;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JComponent;
import IO_LIB.Pixel_mm;
/**
 *
 * @author araki keita
 */
public class DrawingFramePanel extends JPanel{
    private double mag;
    private Point p0;
    private Point locale0=new Point(0,0);//ここで親パネルにたいする初期位置を決める。
    private Point locale=new Point();
    private Pixel_mm pm=new Pixel_mm(Pixel_mm.DISPLAY_DPI);//自分のディスプレイを確認すべし
    private int Fwidth;
    private int Fheight;
    private InputData in;
    private JComponent component;
         public DrawingFramePanel(InputData in){
             locale.setLocation(locale0);
             this.in=in;
            this.setSize(in.getPreferedSize());
            this.setLocation(locale);
            mag=in.getMag();
         }
        public void paintComponent(Graphics g){
            Color bgColor=new Color(34,53,70);
//            Color frameColor=Color.white;
//            Color zeroColor=Color.GREEN;
//            Color AxisColor=Color.MAGENTA;
//            Color ShearColor=Color.YELLOW;
//            Color MomentColor=Color.cyan;
            in.setMag(mag);
//            outputdata.setFrameColor( frameColor, zeroColor);
//            outputdata.setStressColor(AxisColor, ShearColor, MomentColor);
            this.setSize(in.getPreferedSize());
            this.setLocation(locale);
            g.setColor(bgColor);
            g.fillRect(0, 0, this.getWidth(), this.getWidth());
            g.setColor(Color.yellow);
            g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
            in.draw(g);
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
            System.out.println("現在のズームの倍率は　"+(int)(mag*100)+"%");
            this.mag=mag;
        }
        public InputData getInputData(){
            return in;
        }
}
