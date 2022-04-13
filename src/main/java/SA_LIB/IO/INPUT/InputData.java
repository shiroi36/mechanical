/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.IO.INPUT;
import java.awt.Graphics;
import java.awt.Dimension;
import SA_LIB.SA.WholeFrame;
import SA_LIB.SA.MEMB.AbstractMember;
import java.awt.Point;
import java.awt.Color;
import IO_LIB.Pixel_mm;
import MATH_LIB.MATRIX_LIB.MATRIX_CALC;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author araki keita
 */
public class InputData {
    private double S=1;//縮尺の大きさ1/50なら50と打つ.縮尺の倍率
    private Dimension Size;
    private Point O;
    private Point clearance1;
    private Point clearance2;
    private Pixel_mm pm=new Pixel_mm(Pixel_mm.DISPLAY_DPI);//自分のディスプレイを確認すべし
    private double mag=1;
    private Color frameColor,zeroColor;
    private int clear;//クリアランスの幅
    private AbstractMember[] member;
    private boolean n,nv,a,av,s,sv,m,mv;
    private ArrayList<double[]> node;
    private double maxwidth,minwidth,maxheight,minheight;
    public InputData(double Scale){
        n=false;
        nv=false;
        node=new ArrayList<double[]>();
        frameColor=Color.white;
        zeroColor=Color.ORANGE;
        S=Scale;
        maxwidth=0;
        minwidth=0;
        maxheight=0;
        minheight=0;
        O=new Point((int)-minwidth,(int)-minheight);
        Size=new Dimension((int)(pm.setmmtoPixel((maxwidth-minwidth)/S)), (int)(pm.setmmtoPixel((maxheight-minheight)/S)));
//        if((int)(pm.setmmtoPixel((maxwidth-minwidth)/S))<(int)(pm.setmmtoPixel((maxheight-minheight)/S))){
//            clear=(int)(pm.setmmtoPixel((maxheight-minheight)/S));
//        }else{
//            clear=(int)(pm.setmmtoPixel((maxwidth-minwidth)/S));
//        }
//        if(clear<100){
//            clear=100;
//        }
//        clear=0;
        clear=150;
        clearance1=new Point(2*clear,2*clear);
        clearance2=new Point(clear,clear);
    }
    public void setMag(double mag){
        this.mag=mag;
    }
    public double getMag(){
        return mag;
    }
    public void setFrameColor(Color frameColor, Color zerolebelColor){
        this.frameColor=frameColor;
        this.zeroColor=zerolebelColor;
    }
//    private double[] getCoordinateMCtoSC(double[] pointMC,double[] p0,AbstractMember am){
//        double[][] T=am.getC()[1];
//        double[][] T2={{T[0][0],T[0][1]},{T[1][0],T[1][1]}};
//        double[] point=super.multiply(T2, pointMC);
//        super.add(point, p0);
//        return point;
//    }
    public void draw(Graphics g){
//        this.drawModelFrame(g);
        this.drawOriginalPoint(g);
        this.drawNode(g);
        if(nv){this.drawNodesValue(g);}
    }
    public void addNode(double x,double y){
        boolean flag=false;
        if(maxwidth<x){
            maxwidth=x;
            flag=true;
        }else if(minwidth>x){
            minwidth=x;
            flag=true;
        }
        if(maxheight<y){
            maxheight=y;
            flag=true;
        }else if(minheight>y){
            minheight=y;
            flag=true;
        }
        if(flag){
            O=new Point((int)-minwidth,(int)-minheight);
            Size=new Dimension((int)(pm.setmmtoPixel((maxwidth-minwidth)/S)), (int)(pm.setmmtoPixel((maxheight-minheight)/S)));
        }
        node.add(new double[] {x,y});
    }
    private void drawNode(Graphics g){
        int diameter=8;
        g.setColor(frameColor);
        for(int i=0;i<node.size();i++){
            Point p=this.setPoint(node.get(i)[0], node.get(i)[1]);
            g.fillOval(p.x-diameter/2,p.y-diameter/2, diameter,diameter);
        }
    }
    private void drawNodesValue(Graphics g){
        g.setColor(frameColor);
        for(int i=0;i<node.size();i++){
            Point plot=this.setPoint(node.get(i)[0], node.get(i)[1]);
            g.drawString("("+node.get(i)[0]+" ,"+node.get(i)[0]+" )", plot.x+4, plot.y-4);
        }
    }
    public void setNodeValueFlag(boolean flag){nv=flag;}
    public boolean getNodeValueFlag(){return nv;}

    private void drawOriginalPoint(Graphics g){
        int diameter=8;
        int direction=50;
        int yazi=8;
        g.setColor(Color.yellow);
        Point O2=this.setPoint(0, 0);
        g.fillOval(O2.x-diameter/2,O2.y-diameter/2, diameter,diameter);
        g.fillPolygon(new int[] {O2.x,(O2.x-yazi),(O2.x+yazi)},new int[] {(O2.y-direction),(O2.y-direction+yazi),(O2.y-direction+yazi)},3);
        g.fillPolygon(new int[] {(O2.x+direction),(O2.x+direction-yazi),(O2.x+direction-yazi)},new int[] {O2.y,(O2.y-yazi),(O2.y+yazi)},3);
        g.drawString("X", O2.x+direction+3, O2.y+5);
        g.drawString("Y", O2.x-3, O2.y-direction-3);
        g.drawLine(O2.x, O2.y, (O2.x+direction), O2.y);
        g.drawLine(O2.x, O2.y, O2.x, (O2.y-direction));
//        g.drawString("S=1/"+S+" "+"現在のズームの倍率は　"+(int)(mag*100)+"%", 50, this.getPreferedSize().height-clear+20);
    }
    private Point setPoint(double x_mm,double y_mm){
        int x_pic=(int)(pm.setmmtoPixel((x_mm+O.x)/S*mag));
        int y_pic=(int)(pm.setmmtoPixel((y_mm+O.y)/S*mag));
        return new Point((int)(clearance2.x+x_pic),(int)(this.getPreferedSize().height-clearance2.y-y_pic));
    }
    public Dimension getPreferedSize(){
        if(mag<=1){
            clearance1.setLocation(2*clear, 2*clear);
            clearance2.setLocation(clear, clear);
        }else{
            clearance1.setLocation((int)(2*mag*clear), (int)(2*mag*clear));
            clearance2.setLocation((int)(mag*clear),(int)( mag*clear));
        }
        int width=(int)(Size.getWidth()*mag)+clearance1.x;
        int height=(int)(Size.getHeight()*mag)+clearance1.y;
        return new Dimension(width,height);
    }
    public int getClearance(){
        return clear;
    }
}
