/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GRAPH_LIB.GP.SGP.XYZ;

import GRAPH_LIB.GP.GPInterface;
import GRAPH_LIB.GP.SGP.ScrollGraphPlotter;
import GRAPH_LIB.GP.SGP.StepCriterionGraph;
import IO_LIB.SQL_OPE;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.util.ArrayList;
import javax.media.j3d.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.vecmath.*;

/**
 *
 * @author ななか
 */
public class XYZStepSeriesContour extends javax.swing.JPanel implements GPInterface {

    private SimpleUniverse universe;
    private BranchGroup root;
    private ArrayList<double[][][]> val;
    private boolean flag;
    private int length;
    private double minz;
    private double maxz;
    private double point;
    private double[][] maxmin;
    private boolean pflag;

    /**
     * Creates new form sphere
     */
    public XYZStepSeriesContour() {
        this(20);
    }
    public XYZStepSeriesContour(double point) {
        this.point=point;
        initComponents();
        init();
        val=new ArrayList<double[][][]>();
    }
    public JPanel getPanel(){
        return this;
    }
    public void setStatuslabel(JLabel label){
        //GraphPlotterに表示させたかったらここになんか書けば？
    }

    private void init() {
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
//         color[i] = new Color3f(0.968f*c,0.988f*c,0.996f*c);    //赤
        KCanvas3D canvas = new KCanvas3D(10000f, 0.01f, config,false,new Color3f(0.968f,0.988f,0.996f));
        this.add(canvas, BorderLayout.CENTER);
        universe = canvas.universe;
        root = new BranchGroup();
        root.setCapability(BranchGroup.ALLOW_DETACH);
        universe.getLocale().addBranchGraph(root);
        flag=false;
    }
    public void setValue(double[][][] xyz){
        val.add(xyz);
    }
    public void setValue(double[][][] xyz,double maxz,double minz){
        val.add(xyz);
        this.minz=minz;
        this.maxz=maxz;
    }
    public void setMaxMin(double[][] maxmin){
            this.flag=true;
            this.maxmin = maxmin;        
    }
    public void setMaxMin(double max,double min){
        this.maxz=max;
        this.minz=min;
    }
    public void protectRange(boolean protecttrue){
        this.pflag=protecttrue;
    }
    public int getLength(){return length;}

    public void update(int step) {
        System.out.println("update");
        universe.getLocale().removeBranchGraph(root);
        root = new BranchGroup();
        root.setCapability(BranchGroup.ALLOW_DETACH);
        if(flag){maxz=maxmin[step][0];minz=maxmin[step][1];}
        for (int s = 0; s < val.size(); s++) {
            if (val.get(s)[step][0].length < 2) {
                continue;
            }
            double[][] points = val.get(s)[step];

            //<editor-fold defaultstate="collapsed" desc="点の描画">
            Point3d[] p3d = new Point3d[points[0].length];
            Color3f[] color = new Color3f[points[0].length];
            for (int i = 0; i < points[0].length; i++) {
                
                
                p3d[i] = new Point3d(points[0][i], points[1][i], points[2][i]);
//                float c = (float) (((points[2][i] - points[2][0]) - minz) / (maxz - minz));
                float c = (float) ((points[2][i] - minz) / (maxz - minz));
                
                if (pflag) {
                    if (c >= 1) {
                        c = 1;
                    }
                    if (c <= 0) {
                        c = 0;
                    }
                }
                
                System.out.println(p3d[i].getX() + "\t" + p3d[i].getY() + "\t" + p3d[i].getZ() + "\t" + c);
                
                    color[i] = new Color3f(1,0.8f*(1-c),0.8f*(1-c));    //赤
//                if (c <= 0.3) {
//                    color[i] = new Color3f(3.33f*c, 1 - 1f*c , 1 - 3.33f*c);    //赤
//                }else if(c>0.3){
//                    color[i] = new Color3f(1, 0.7f-0.7f*(c-0.3f)/0.7f, 0);    //赤
//                }
            }
            PointArray geometry = new PointArray(p3d.length,
                    GeometryArray.COORDINATES | GeometryArray.COLOR_3);
            geometry.setCoordinates(0, p3d);
            geometry.setColors(0, color);
            
            PointAttributes pleft = new PointAttributes();
//            ColoringAttributes cleft = new ColoringAttributes();
            pleft.setPointSize((float)point);      // Point の大きさ
//            cleft.setColor(1f, 1f, 1f);

            // Appearance にサイズを登録
            Appearance aleft = new Appearance();
            aleft.setPointAttributes(pleft);
//            aleft.setColoringAttributes(cleft);
            // BranchGroup に登録
            Shape3D shape = new Shape3D(geometry, aleft);
//            Shape3D shape = new Shape3D(geometry);
            root.addChild(shape);
            System.out.println("numChildren:"+root.numChildren());
            //</editor-fold>
        }
        System.out.println("numBranchGroup: "+universe.getLocale().numBranchGraphs());
        universe.getLocale().addBranchGraph(root);        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
