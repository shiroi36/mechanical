/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GRAPH_LIB.GP.SGP.XYZ;

//import Jama.Matrix;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.event.*;
import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import org.apache.commons.math3.linear.BlockRealMatrix;

/**
 *
 * @author ななか
 */
public class KCanvas3D extends Canvas3D {

    float sens;
    float cd;
    float inicd;
    float phi = 0;
    float theta = 0;
    float gamma = 0;
    BlockRealMatrix center;
    public SimpleUniverse universe;
    TransformGroup camera;
    Transform3D cpos;
    Transform3D cphi;
    Transform3D cgamma;
    Transform3D ctheta;
    Vector3f ccoord;
    private final boolean perse;

    public KCanvas3D(float distance, float sensitivity, 
            GraphicsConfiguration config,boolean perspectivetrue,Color3f bgcolor) {
        super(config);
//        super.setRightManualEyeInImagePlate(new Point3d(10, 0, 0));
//        super.setLeftManualEyeInImagePlate(new Point3d(-10, 0, 0));
//        super.setMonoscopicViewPolicy(View.RIGHT_EYE_VIEW);
        
        cd = distance;
        inicd=distance;
        sens = sensitivity;
        center=new BlockRealMatrix(4,1);
        center.setEntry(3, 0, 1);
        universe = new SimpleUniverse(this);
        this.perse=perspectivetrue;
//        System.out.println("radius r="+universe
//                .getViewingPlatform().getViewPlatform().getActivationRadius());
//        universe.getViewingPlatform().getViewPlatform().setActivationRadius(1f);
//        universe.getViewingPlatform().getViewPlatform().setViewAttachPolicy(View);
        if (perse) {
            universe.getViewer().getView().setProjectionPolicy(View.PERSPECTIVE_PROJECTION);
        } else {
            universe.getViewer().getView().setProjectionPolicy(View.PARALLEL_PROJECTION);
            universe.getViewer().getView().setScreenScalePolicy(View.SCALE_EXPLICIT);
            universe.getViewer().getView().setScreenScale(1 / cd);
        }
        
//        universe.getViewer().getView().setFrontClipPolicy(View.PHYSICAL_SCREEN);
//        universe.getViewer().getView().setBackClipPolicy(View.PHYSICAL_SCREEN);
        
        universe.getViewer().getView().setBackClipDistance(Math.pow(10, 5));
        universe.getViewer().getView().setFrontClipDistance(0);
        BranchGroup objRoot = new BranchGroup();
        // 背景色の設定
        BoundingSphere bounds = new BoundingSphere();
        bounds.setRadius(1000000);
//        Background bg = new Background( new Color3f( 0.133f,0.208f,0.275f ) );
        Background bg = new Background( bgcolor );
        bg.setApplicationBounds(bounds);
        objRoot.addChild(bg);
        universe.getLocale().addBranchGraph(objRoot);
        ViewingPlatform vp = universe.getViewingPlatform();        
        camera = vp.getViewPlatformTransform();
        this.init();
        
//        System.out.println("getCenterEyeInImagePlate: "+super.get);
        
    }
    public KCanvas3D(float distance, float sensitivity, 
            GraphicsConfiguration config,boolean perspectivetrue){
        this(distance,sensitivity,config,perspectivetrue,new Color3f( 0.133f,0.208f,0.275f ) );
    } 
    private void init(){
        //<editor-fold defaultstate="collapsed" desc="カメラ位置に関する初期設定">
        //カメラ位置に関する初期設定
        BlockRealMatrix y = this.getAffineRotY(phi);
        BlockRealMatrix x = this.getAffineRotX(-theta);
        BlockRealMatrix z = this.getAffineRotX(gamma);
        BlockRealMatrix l = new BlockRealMatrix(new double[][]{{0}, {0}, {cd}, {1}});
        BlockRealMatrix b = z.multiply(y).multiply(x).multiply(l);
        BlockRealMatrix a = this.getAffineParallelMove(center).multiply(b);
        ccoord = new Vector3f((float) a.getEntry(0, 0),
                (float) a.getEntry(1, 0), (float) a.getEntry(2, 0));
        cpos = new Transform3D();
        cpos.setTranslation(ccoord);
        cphi = new Transform3D();
        ctheta = new Transform3D();
        cgamma = new Transform3D();
        ctheta.rotX(-theta);
        cphi.rotY(phi);
        cgamma.rotZ(gamma);
        cphi.mul(cgamma);
        ctheta.mul(cphi);
        cpos.mul(ctheta);
        camera.setTransform(cpos);
        mouse m = new mouse(this);
        this.addMouseListener(m);
        this.addMouseMotionListener(m);
        this.addMouseWheelListener(m);
        key k = new key(this);
        this.addKeyListener(k);
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="座標軸の描画">
        //座標軸の設定
        BranchGroup root=new BranchGroup(); 
        //線の頂点座標・色設定
        Point3f[] vertex = new Point3f[6];
        Color3f[] color = new Color3f[6];
        float length=100f;
        vertex[0] = new Point3f(0f,  0f, 0f);
        vertex[1] = new Point3f( length,  0f, 0f);
        vertex[2] = new Point3f(0f, 0f,0f);
        vertex[3] = new Point3f( 0f, length, 0f);
        vertex[4] = new Point3f( 0f, 0f, 0f);
        vertex[5] = new Point3f( 0f, 0f, length);
        color[0] = new Color3f( 1.0f,  0.0f, 0.0f);    //赤
        color[1] = new Color3f( 1.0f,  0.0f, 0.0f);    //赤
        color[2] = new Color3f( 0f,  1f, 0.0f);   
        color[3] = new Color3f( 0f,  1f, 0.0f);  
        color[4] = new Color3f( 0f,  0.0f,1f);   
        color[5] = new Color3f( 0f,  0.0f, 1f);  
        //線の生成
        LineArray lineA
            = new LineArray(vertex.length,
                GeometryArray.COORDINATES | GeometryArray.COLOR_3);
        lineA.setCoordinates(0, vertex);
        lineA.setColors(0, color);
        Shape3D line3D = new Shape3D(lineA);
        root.addChild(line3D);
        //</editor-fold>
//        
//        //<editor-fold defaultstate="collapsed" desc="座標軸ラベル">
//        //文字は白色
//        Material mat_text = new Material();
////        mat_text.setDiffuseColor(1.0f, 1.0f, 1.0f);
//        mat_text.setEmissiveColor(1.0f,  0.0f, 0.0f);
//        Appearance ap_text = new Appearance();
//        ap_text.setMaterial(mat_text);
//        //フォントはArial，太字，大きさ
//        Font basic_font = new Font("Konatu 標準", java.awt.Font.PLAIN, (int)length/5);
//        //3Dフォントを作成
//        Font3D font = new Font3D(basic_font, new FontExtrusion());
//
//        //X軸のテキスト（位置はX軸の先端）
//        Text3D x_text = new Text3D(font, "X", new Point3f(length+length/20, 0.0f, 0.0f));
//        Shape3D x_label = new Shape3D();
//        x_label.setGeometry(x_text);
//        x_label.setAppearance(ap_text);
//        root.addChild(x_label);
//        //X軸のテキスト（位置はY軸の先端）
//        mat_text = new Material();
//        mat_text.setEmissiveColor(0.0f,  1.0f, 0.0f);
//        ap_text = new Appearance();
//        ap_text.setMaterial(mat_text);
//        Text3D y_text = new Text3D(font, "Y", new Point3f(0.0f,length+length/20, 0.0f));
//        Shape3D y_label = new Shape3D();
//        y_label.setGeometry(y_text);
//        y_label.setAppearance(ap_text);
//        root.addChild(y_label);
//        //X軸のテキスト（位置はZ軸の先端）
//        mat_text = new Material();
//        mat_text.setEmissiveColor(0.0f,  0.0f, 1.0f);
//        ap_text = new Appearance();
//        ap_text.setMaterial(mat_text);
//        Text3D z_text = new Text3D(font, "Z", new Point3f(0.0f, 0.0f,length+length/20));
//        Shape3D z_label = new Shape3D();
//        z_label.setGeometry(z_text);
//        z_label.setAppearance(ap_text);
//        root.addChild(z_label);
//        //</editor-fold>
//        
        universe.addBranchGraph(root);
    }
    private void clear(){
        center=new BlockRealMatrix(new double[][]{{0},{0},{0},{1}});
        cd=inicd;
        this.rotate(0, 0,0);
    }
    private void rotateXY(float theta,float phi){
        this.theta=theta;
        this.phi=phi;
        BlockRealMatrix y=this.getAffineRotY(phi);
        BlockRealMatrix x=this.getAffineRotX(-theta);
        BlockRealMatrix l=new BlockRealMatrix(new double[][]{{0},{0},{cd},{1}});
        BlockRealMatrix b=y.multiply(x).multiply(l);
        BlockRealMatrix a=this.getAffineParallelMove(center).multiply(b);
        ccoord.x = (float)a.getEntry(0, 0);
        ccoord.y = (float)a.getEntry(1, 0);
        ccoord.z = (float)a.getEntry(2, 0);
        cpos.setIdentity();
        cpos.setTranslation(ccoord);
        ctheta.rotX(-theta);
        cphi.rotY(phi);
        cphi.mul(ctheta);
        cpos.mul(cphi);
        camera.setTransform(cpos);
    }
    private void rotate(float theta,float phi,float gamma){
        this.theta=theta;
        this.phi=phi;
        this.gamma=gamma;
        BlockRealMatrix z=this.getAffineRotZ(gamma);
        BlockRealMatrix y=this.getAffineRotY(phi);
        BlockRealMatrix x=this.getAffineRotX(-theta);
        BlockRealMatrix l=new BlockRealMatrix(new double[][]{{0},{0},{cd},{1}});
        BlockRealMatrix b=z.multiply(y).multiply(x).multiply(l);
        BlockRealMatrix a=this.getAffineParallelMove(center).multiply(b);
        ccoord.x = (float)a.getEntry(0, 0);
        ccoord.y = (float)a.getEntry(1, 0);
        ccoord.z = (float)a.getEntry(2, 0);
        
        cpos.setIdentity();
        cgamma.rotZ(gamma);
        cpos.mul(cgamma);
        
        cpos.setTranslation(ccoord);
        ctheta.rotX(-theta);
        cphi.rotY(phi);
        cphi.mul(ctheta);
        cpos.mul(cphi);
        camera.setTransform(cpos);
    }
    private void zoom(int wheelRotation,boolean isCTRL){
        if(wheelRotation>0){
            cd-=3*sens*inicd;
            if(isCTRL){
                cd-=15*sens*inicd;
            }
            if(cd/inicd<0.0000001){
                cd=(float)0.0000001*inicd;
            }
        }else{
            cd+=3*sens*inicd;
            if(isCTRL){
                cd+=15*sens*inicd;
            }
        }
        
        
        if (perse) {
            //140429以前のプログラム
            BlockRealMatrix y = this.getAffineRotY(phi);
            BlockRealMatrix x = this.getAffineRotX(-theta);
            BlockRealMatrix l = new BlockRealMatrix(new double[][]{{0}, {0}, {cd}, {1}});
            BlockRealMatrix b = y.multiply(x).multiply(l);
            BlockRealMatrix a = this.getAffineParallelMove(center).multiply(b);
            ccoord.x = (float) a.getEntry(0, 0);
            ccoord.y = (float) a.getEntry(1, 0);
            ccoord.z = (float) a.getEntry(2, 0);
            cpos.setIdentity();
            cpos.setTranslation(ccoord);
            ctheta.rotX(-theta);
            cphi.rotY(phi);
            cphi.mul(ctheta);
            cpos.mul(cphi);
            camera.setTransform(cpos);
        } else {
            universe.getViewer().getView().setScreenScale(1 / cd);
        }
        
    }
    private void Move(double dx,double dy,double dz){
//        System.out.println("dx:"+dx+"\tdy:"+dy);
        BlockRealMatrix ex=new BlockRealMatrix(new double[][]{{1},{0},{0},{1}});
        BlockRealMatrix ey=new BlockRealMatrix(new double[][]{{0},{1},{0},{1}});
        BlockRealMatrix ez=new BlockRealMatrix(new double[][]{{0},{0},{1},{1}});
        BlockRealMatrix z=this.getAffineRotZ(gamma);
        BlockRealMatrix y=this.getAffineRotY(phi);
        BlockRealMatrix x=this.getAffineRotX(-theta);
//        System.out.println(y.toString());
        BlockRealMatrix ex2=(BlockRealMatrix)z.multiply(y).multiply(x).multiply(ex).scalarMultiply(dx);
        BlockRealMatrix ey2=(BlockRealMatrix)z.multiply(y).multiply(x).multiply(ey).scalarMultiply(dy);
        BlockRealMatrix ez2=(BlockRealMatrix)z.multiply(y).multiply(x).multiply(ez).scalarMultiply(dz);
//        System.out.println("ex2:"+ex2.toString());
//        System.out.println("ey2:"+ey2.toString());
        center=center.add(ex2.scalarMultiply(-1)).add(ey2).add(ez2);
        center.setEntry(3, 0, 1);
//        
        BlockRealMatrix l=new BlockRealMatrix(new double[][]{{0},{0},{cd},{1}});
        BlockRealMatrix b=z.multiply(y).multiply(x).multiply(l);
        BlockRealMatrix a=this.getAffineParallelMove(center).multiply(b);
        ccoord.x = (float)a.getEntry(0, 0);
        ccoord.y = (float)a.getEntry(1, 0);
        ccoord.z = (float)a.getEntry(2, 0);
        cpos.setIdentity();
        cgamma.rotZ(gamma);
        cpos.mul(cgamma);
                
        cpos.setTranslation(ccoord);
        ctheta.rotX(-theta);
        cphi.rotY(phi);
        cphi.mul(ctheta);
        cpos.mul(cphi);
        camera.setTransform(cpos);
//        System.out.println(dx+"\t"+dy);
//        center.print(4, 4);
//        System.out.println("ex");
//        ex2.print(4, 4);
//        System.out.println("ey");
//        ey2.print(4, 4);
    }
    private float getPhi(){return phi;}
    private float getTheta(){return theta;}
    private float getGamma(){return gamma;}
    private void setPhi(float phi){this.phi=phi;}
    private void setTheta(float theta){this.theta=theta;}
    private void setGamma(float gamma){this.gamma=gamma;}

    class mouse extends MouseAdapter {

        private KCanvas3D c;
        private int newx, newy;
        private int prex, prey;

        public mouse(KCanvas3D c) {
            this.c = c;
        }

        public void mouseMoved(MouseEvent e) {
            int button=e.getModifiersEx();
            prex = e.getX();
            prey = e.getY();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int button=e.getModifiersEx();
            newx = e.getX();
            newy = e.getY();
            if(((button&InputEvent.CTRL_DOWN_MASK)!=0)&
                    ((button&MouseEvent.BUTTON2_DOWN_MASK)!=0)){
                float t=c.getTheta();
                float p=c.getPhi();
                float g=c.getGamma();
                g += sens * (newy - prey);
                c.rotate(t, p, g);
            }else if((button&MouseEvent.BUTTON2_DOWN_MASK)!=0){
                float t=c.getTheta();
                float p=c.getPhi();
                float g=c.getGamma();
                t += sens * (newy - prey);
                p -= sens * (newx - prex);
                c.rotate(t, p,g);
            }
            else if((button&MouseEvent.BUTTON3_DOWN_MASK)!=0){
                double dy=  inicd*0.001*(newy - prey);
                double dx=  inicd*0.001*(newx - prex);   
                c.Move(dx, dy,0);
            }
            prex = newx;
            prey = newy;
        }
        public void mouseWheelMoved(MouseWheelEvent e) {
            int button=e.getModifiersEx();
            int x=0;
            int y=0;
            int wr = e.getWheelRotation();
            if ((button & InputEvent.CTRL_DOWN_MASK) != 0) {
//                System.out.println("ctrl_down");
                c.zoom(wr,true);
            } else {
                c.zoom(wr,false);
            }
        }
    }
    class key extends KeyAdapter{
        KCanvas3D c;
        public key(KCanvas3D c){
            this.c=c;
        }
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()){
                case KeyEvent.VK_Q:
                    c.rotate(0, (float)Math.PI/2,0);
                    break;
                case KeyEvent.VK_W:
                    c.rotate(-(float)Math.PI/2, 0,0);
                    break;
                case KeyEvent.VK_E:
                    c.rotate(0, 0,0);
                    break;
                case KeyEvent.VK_R:
                    c.rotate((float)Math.PI/4,(float)Math.PI/4,0);
                    break;
                case KeyEvent.VK_A:
                    c.clear();
                    break;
            }
        }
    }
    public BlockRealMatrix getAffineParallelMove(BlockRealMatrix xyz){
//        System.out.println("parallelMove: "+xyz.toString());
        BlockRealMatrix y=new BlockRealMatrix(4,4);
        y.setEntry(0, 3, xyz.getEntry(0, 0));
        y.setEntry(1, 3, xyz.getEntry(1, 0));
        y.setEntry(2, 3, xyz.getEntry(2, 0));
        y.setEntry(3, 3, xyz.getEntry(3, 0));
        y.setEntry(0, 0, 1);
        y.setEntry(1, 1, 1);
        y.setEntry(2, 2, 1);
//        y.print(4, 4);
        return y;
    }
    public BlockRealMatrix getAffineRotX(double theta){
        BlockRealMatrix y=new BlockRealMatrix(new double[][]{
            {1,0,0,0},
            {0,Math.cos(theta),-Math.sin(theta),0},
            {0,Math.sin(theta),Math.cos(theta),0},
            {0,0,0,1}
        });
        return y;
    }
    public BlockRealMatrix getAffineRotY(double theta){
        BlockRealMatrix y=new BlockRealMatrix(new double[][]{
            {Math.cos(theta),0,Math.sin(theta),0},
            {0,1,0,0},
            {-Math.sin(theta),0,Math.cos(theta),0},
            {0,0,0,1}
        });
        return y;
    }
    public BlockRealMatrix getAffineRotZ(double theta){
        BlockRealMatrix y=new BlockRealMatrix(new double[][]{
            {Math.cos(theta),-Math.sin(theta),0,0},
            {Math.sin(theta),Math.cos(theta),0,0},
            {0,0,1,0},
            {0,0,0,1}
        });
        return y;
    }
}
