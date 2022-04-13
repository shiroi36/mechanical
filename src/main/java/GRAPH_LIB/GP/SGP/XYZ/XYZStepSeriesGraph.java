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
public class XYZStepSeriesGraph extends javax.swing.JPanel implements GPInterface {

    public static void main(String[] args) throws InterruptedException {
        String[] secs = {"'%s01%'", "'%s02%'", "'%s03%'", "'%s04%'", "'%s05%'", "'%s06%'",
            "'%s07%'", "'%s08%'", "'%s09%'", "'%s10%'", "'%s11%'", "'%s12%'", "'%s13%'", "'%s14%'"};
//        int step=507;
//        int step=1359;
        XYZStepSeriesGraph j3d = new XYZStepSeriesGraph();
        String margename = "'marge'";
        SQL_OPE sql = new SQL_OPE("jdbc:h2:tcp://localhost/%DROPBOX%/DB/"
                + "lateralbuckling/130525surabu1600;ifexists=true", "junapp", "");
        double[][] step=sql.getQueryData("select 1,step "
                + "from public.organizedpoints group by step order by step");
        for(int s = 0; s < secs.length; s++) {
            double[][][] val=new double[step[1].length][][];
            for (int i = 0; i < step[1].length; i++) {
                val[i]=sql.getQueryData("select x,y,z from "
                        + "\"PUBLIC\".organizedPOINTS "
                        + "where margename=" + margename + " and step=" + (int)step[1][i] 
                        + " and pointname like "+ secs[s]+ "order by pointname;");
            }
            j3d.setValue(val);
        }
        StepCriterionGraph scg=new StepCriterionGraph("test",step);
        ScrollGraphPlotter sgp=new ScrollGraphPlotter(scg);
        sgp.setGraph("130525test", j3d);
    }
    private SimpleUniverse universe;
    private BranchGroup root;
    private ArrayList<double[][][]> val;
    private boolean flag;
    private int length;

    /**
     * Creates new form sphere
     */
    public XYZStepSeriesGraph() {
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
        KCanvas3D canvas = new KCanvas3D(10000f, 0.01f, config,false);
        this.add(canvas, BorderLayout.CENTER);
        universe = canvas.universe;
        root = new BranchGroup();
        root.setCapability(BranchGroup.ALLOW_DETACH);
        universe.getLocale().addBranchGraph(root);
        flag=true;
    }
    public void setValue(double[][][] xyz){
        val.add(xyz);
//        if(flag){
//            length=xyz.length;
//            flag=false;
//            val.add(xyz);
//        }else{
//            if(xyz.length==length){
//                val.add(xyz);
//            }else{
//                return;
//            }
//        }
    }
    public int getLength(){return length;}

    public void update(int step) {
        universe.getLocale().removeBranchGraph(root);
        root = new BranchGroup();
        root.setCapability(BranchGroup.ALLOW_DETACH);
        for (int s = 0; s < val.size(); s++) {
            if (val.get(s)[step][0].length < 2) {
                continue;
            }
            double[][] points = val.get(s)[step];

            //<editor-fold defaultstate="collapsed" desc="線の描画">
            //線の頂点座標・色設定
            Point3d[] vertex = new Point3d[2 * (points[0].length - 1)];
            Color3f[] color = new Color3f[2 * (points[0].length - 1)];

            for (int i = 0; i < points[0].length - 1; i++) {
                vertex[2 * i] = new Point3d(points[0][i], points[1][i], points[2][i]);
                vertex[2 * i + 1] = new Point3d(points[0][i + 1], points[1][i + 1], points[2][i + 1]);
                color[2 * i] = new Color3f(1f, 1f, 1f);    //赤
                color[2 * i + 1] = new Color3f(1f, 1f, 1f);    //赤
            }
            //線の生成
            LineArray lineA = new LineArray(vertex.length,
                    GeometryArray.COORDINATES | GeometryArray.COLOR_3);
            lineA.setCoordinates(0, vertex);
            lineA.setColors(0, color);
            Shape3D line3D = new Shape3D(lineA);
            root.addChild(line3D);
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="点の描画">
            Point3d[] p3d = new Point3d[points[0].length];
            for (int i = 0; i < points[0].length; i++) {
                p3d[i] = new Point3d(points[0][i], points[1][i], points[2][i]);
            }
            PointArray geometry = new PointArray(p3d.length, GeometryArray.COORDINATES);
            geometry.setCoordinates(0, p3d);
            PointAttributes pleft = new PointAttributes();
            ColoringAttributes cleft = new ColoringAttributes();
            pleft.setPointSize(5.0f);      // Point の大きさ
            cleft.setColor(1f, 1f, 1f);

            // Appearance にサイズを登録
            Appearance aleft = new Appearance();
            aleft.setPointAttributes(pleft);
            aleft.setColoringAttributes(cleft);
            // BranchGroup に登録
            Shape3D shape = new Shape3D(geometry, aleft);
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
