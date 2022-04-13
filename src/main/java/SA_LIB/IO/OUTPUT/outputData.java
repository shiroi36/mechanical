/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.IO.OUTPUT;
import java.awt.Graphics;
import java.awt.Dimension;
import SA_LIB.SA.WholeFrame;
import SA_LIB.SA.MEMB.AbstractMember;
import java.awt.Point;
import java.awt.Color;
import IO_LIB.Pixel_mm;
import MATH_LIB.MATRIX_LIB.MATRIX_CALC;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * @author araki keita
 */
public class outputData extends MATRIX_CALC{
    private double[][] node;//
    private int[][] memb;
    private double S=1;//縮尺の大きさ1/50なら50と打つ.縮尺の倍率
    private Dimension Size;
    private Point O;
    private Point clearance1;
    private Point clearance2;
    private Pixel_mm pm=new Pixel_mm(Pixel_mm.DISPLAY_DPI);//自分のディスプレイを確認すべし
    private double mag=1;
    private Color frameColor,zeroColor,AxisCColor,AxisTColor,ShearColor,MomentColor;
    private int clear;//クリアランスの幅
    private AbstractMember[] member;
    private double FS,MS;
    private boolean n,nv,a,av,s,sv,m,mv;
    public outputData(WholeFrame model,double Scale,double ForceScale,double MomentScale){
        frameColor=Color.white;
        zeroColor=Color.ORANGE;
        AxisCColor=Color.MAGENTA;
        AxisTColor=Color.CYAN;
        ShearColor=Color.YELLOW;
        MomentColor=Color.GREEN;
        node=model.getNode();//mm
        memb=model.getMemb();
        member=model.getMemberInfo();
        S=Scale;
        FS=ForceScale;
        MS=MomentScale;
        double maxwidth=0;
        double minwidth=0;
        double maxheight=0;
        double minheight=0;
        for(int i=0;i<node.length;i++){
            if(maxwidth<node[i][0]){
                maxwidth=node[i][0];
            }
            if(minwidth>node[i][0]){
                minwidth=node[i][0];
            }
            if(maxheight<node[i][1]){
                maxheight=node[i][1];
            }
            if(minheight>node[i][1]){
                minheight=node[i][1];
            }
        }
        O=new Point((int)-minwidth,(int)-minheight);
        Size=new Dimension((int)(pm.setmmtoPixel((maxwidth-minwidth)/S)), (int)(pm.setmmtoPixel((maxheight-minheight)/S)));
        if((int)(pm.setmmtoPixel((maxwidth-minwidth)/S))<(int)(pm.setmmtoPixel((maxheight-minheight)/S))){
            clear=(int)(pm.setmmtoPixel((maxheight-minheight)/S));
        }
        if((int)(pm.setmmtoPixel((maxwidth-minwidth)/S))>=(int)(pm.setmmtoPixel((maxheight-minheight)/S))){
            clear=(int)(pm.setmmtoPixel((maxwidth-minwidth)/S));
        }
//        clear=0;
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
    public void setStressColor(Color AxisColor, Color ShearColor,Color MomentColor){
        this.AxisCColor=AxisColor;
        this.ShearColor=ShearColor;
        this.MomentColor=MomentColor;
    }
    private void setStressScale(double ForceScale,double MomentScale){
        FS=ForceScale;
        MS=MomentScale;
    }
    private double[] getCoordinateMCtoSC(double[] pointMC,double[] p0,AbstractMember am){
        double[][] T=am.getC()[1];
        double[][] T2={{T[0][0],T[0][1]},{T[1][0],T[1][1]}};
        double[] point=super.multiply(T2, pointMC);
        super.add(point, p0);
        return point;
    }
    public void draw(Graphics g){
        this.drawModelFrame(g);
        if(n==true){this.drawNodes(g);}
        if(nv==true){this.drawNodesValue(g);}
        if(a==true){this.drawAxisForce(g);}
        if(av==true){this.drawAxisForceValue(g);}
        if(s==true){this.drawShearForce(g);}
        if(sv==true){this.drawShearForceValue(g);}
        if(m==true){this.drawMoment(g);}
        if(mv==true){this.drawMomentValue(g);}
    }

    public void setNodeFlag(boolean flag){n=flag;}
    public void setNodeValueFlag(boolean flag){nv=flag;}
    public void setAxisFlag(boolean flag){a=flag;}
    public void setAxisValueFlag(boolean flag){av=flag;}
    public void setShearFlag(boolean flag){s=flag;}
    public void setShearValueFlag(boolean flag){sv=flag;}
    public void setMomentFlag(boolean flag){m=flag;}
    public void setMomentValueFlag(boolean flag){mv=flag;}

    private void drawModelFrame(Graphics g){
        g.setColor(zeroColor);
        g.drawLine(0, (int)(this.getPreferedSize().height-clearance2.y)
                , this.getPreferedSize().width, (int)(this.getPreferedSize().height-clearance2.y));
        g.setColor(frameColor);
        g.drawString("S=1/"+S+" "+"現在のズームの倍率は　"+(int)(mag*100)+"%", 50, this.getPreferedSize().height-clear+20);
        for(int i=0;i<memb.length;i++){
            Point start=this.setPoint((int)node[memb[i][0]][0], (int)node[memb[i][0]][1]);
            Point end=this.setPoint((int)node[memb[i][1]][0],(int)node[memb[i][1]][1]);
            g.drawLine(start.x, start.y, end.x, end.y);
        }
    }
    private void drawNodes(Graphics g){
        int diameter=8;
        g.setColor(frameColor);
        for(int i=0;i<node.length;i++){
            Point plot=this.setPoint(node[i][0], node[i][1]);
            g.fillRect(plot.x-diameter/2, plot.y-diameter/2, diameter, diameter);
        }
    }
    private void drawNodesValue(Graphics g){
        g.setColor(frameColor);
        for(int i=0;i<node.length;i++){
            Point plot=this.setPoint(node[i][0], node[i][1]);
            g.drawString("("+node[i][0]+" ,"+node[i][1]+" )", plot.x, plot.y);
        }
    }
    private void drawAxisForce(Graphics g){
//        g.setColor(AxisColor);
        for(int i=0;i<member.length;i++){
            double[] p0=node[memb[i][0]];
            double[][] stress={member[i].getStress()[0],
                                member[i].getStress()[1]};
            stress[1]=super.Waru(stress[1], FS);
            double[] SCstart=this.getCoordinateMCtoSC(new double[] {stress[0][0],stress[1][0]}, p0, member[i]);
            Point start=this.setPoint(SCstart[0], SCstart[1]);
            Point point=this.setPoint(p0[0], p0[1]);
            if(stress[1][0]<0){
                g.setColor(AxisCColor);
            }
            else{
                g.setColor(AxisTColor);
            }
            g.drawLine(point.x, point.y, start.x, start.y);
            for(int s=0;s<stress[0].length;s++){
                double[] SCend=this.getCoordinateMCtoSC(new double[] {stress[0][s],stress[1][s]}, p0, member[i]);
                Point end=this.setPoint(SCend[0], SCend[1]);
                if(stress[1][s]<0){
                    g.setColor(AxisCColor);
                }
                else{
                    g.setColor(AxisTColor);
                }
                g.drawLine(start.x, start.y, end.x, end.y);
                start=end;
            }
            point=this.setPoint(node[memb[i][1]][0], node[memb[i][1]][1]);
            g.drawLine(start.x, start.y, point.x, point.y);
        }
    }
    private void drawAxisForceValue(Graphics g){
        DecimalFormat df = new DecimalFormat("0.00E0");
        for(int i=0;i<member.length;i++){
            double[] p0=node[memb[i][0]];
            double[][] stress={member[i].getStress()[0],
                                member[i].getStress()[1]};
            int[] ind={0,stress[1].length-1};//表示したい地点を変えたければここ
            for(int s=0;s<ind.length;s++){
//                double value=new BigDecimal(stress[1][ind[s]]).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
//                System.out.println("bigdecimal"+value);
                double[] co=this.getCoordinateMCtoSC(new double[] {stress[0][ind[s]],stress[1][ind[s]]/FS}, p0, member[i]);
                Point co2=this.setPoint(co[0], co[1]);
                if(stress[1][s]<0){
                    g.setColor(AxisCColor);
                }
                else{
                    g.setColor(AxisTColor);
                }
                g.drawString(df.format(stress[1][ind[s]]), co2.x, co2.y);
            }
        }
    }
    private void drawShearForce(Graphics g){
        g.setColor(ShearColor);
        for(int i=0;i<member.length;i++){
            double[] p0=node[memb[i][0]];
            double[][] stress={member[i].getStress()[0],
                                member[i].getStress()[2]};
            stress[1]=super.Waru(stress[1], FS);
            double[] SCstart=this.getCoordinateMCtoSC(new double[] {stress[0][0],stress[1][0]}, p0, member[i]);
            Point start=this.setPoint(SCstart[0], SCstart[1]);
            Point point=this.setPoint(p0[0], p0[1]);
            g.drawLine(point.x, point.y, start.x, start.y);
            for(int s=0;s<stress[0].length;s++){
                double[] SCend=this.getCoordinateMCtoSC(new double[] {stress[0][s],stress[1][s]}, p0, member[i]);
                Point end=this.setPoint(SCend[0], SCend[1]);
                g.drawLine(start.x, start.y, end.x, end.y);
                start=end;
            }
            point=this.setPoint(node[memb[i][1]][0], node[memb[i][1]][1]);
            g.drawLine(start.x, start.y, point.x, point.y);
        }
    }
    private void drawShearForceValue(Graphics g){
        DecimalFormat df = new DecimalFormat("0.00E0");
        g.setColor(ShearColor);
        for(int i=0;i<member.length;i++){
            double[] p0=node[memb[i][0]];
            double[][] stress={member[i].getStress()[0],
                                member[i].getStress()[2]};
            int[] ind={0,stress[1].length-1};//表示したい地点を変えたければここ
            for(int s=0;s<ind.length;s++){
                double[] co=this.getCoordinateMCtoSC(new double[] {stress[0][ind[s]],stress[1][ind[s]]/FS}, p0, member[i]);
                Point co2=this.setPoint(co[0], co[1]);
                g.drawString(df.format(stress[1][ind[s]]), co2.x, co2.y);
            }
        }
    }
    private void drawMoment(Graphics g){
        g.setColor(MomentColor);
        for(int i=0;i<member.length;i++){
            double[] p0=node[memb[i][0]];
            double[][] stress={member[i].getStress()[0],
                                member[i].getStress()[3]};
            stress[1]=super.Waru(stress[1], -MS);
            double[] SCstart=this.getCoordinateMCtoSC(new double[] {stress[0][0],stress[1][0]}, p0, member[i]);
            Point start=this.setPoint(SCstart[0], SCstart[1]);
            Point point=this.setPoint(p0[0], p0[1]);
            g.drawLine(point.x, point.y, start.x, start.y);
            for(int s=0;s<stress[0].length;s++){
                double[] SCend=this.getCoordinateMCtoSC(new double[] {stress[0][s],stress[1][s]}, p0, member[i]);
                Point end=this.setPoint(SCend[0], SCend[1]);
                g.drawLine(start.x, start.y, end.x, end.y);
                start=end;
            }
            point=this.setPoint(node[memb[i][1]][0], node[memb[i][1]][1]);
            g.drawLine(start.x, start.y, point.x, point.y);
        }
    }
    private void drawMomentValue(Graphics g){
        DecimalFormat df = new DecimalFormat("0.00E0");
        g.setColor(MomentColor);
        for(int i=0;i<member.length;i++){
            double[] p0=node[memb[i][0]];
            double[][] stress={member[i].getStress()[0],
                                member[i].getStress()[3]};
            int[] ind={0,stress[1].length/2,stress[1].length-1};//表示したい地点を変えたければここ
            for(int s=0;s<ind.length;s++){
                double[] co=this.getCoordinateMCtoSC(new double[] {stress[0][ind[s]],-stress[1][ind[s]]/MS}, p0, member[i]);
                Point co2=this.setPoint(co[0], co[1]);
                g.drawString(df.format(stress[1][ind[s]]), co2.x, co2.y);
            }
        }
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
