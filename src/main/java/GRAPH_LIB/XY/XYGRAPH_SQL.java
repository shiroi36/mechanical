/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GRAPH_LIB.XY;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
//import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.jdbc.JDBCXYDataset;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.axis.*;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ChartPanel;
import javax.swing.JPanel;
import java.awt.Insets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.awt.BasicStroke;

import org.jfree.ui.RectangleEdge;
import java.text.DecimalFormat;
import org.jfree.chart.title.TextTitle;

import org.jfree.chart.ChartUtilities;
import java.io.File;
import javax.swing.JFrame;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.sourceforge.jlibeps.epsgraphics.EpsGraphics2D;
import java.awt.geom.Point2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.chart.event.PlotChangeEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *XY軸のグラフをプロットするクラスです。
 * 主に変更する数値はメソッドにて書き下しますが、めったに変更しないだろうと判断した設定事項については
 * result()メソッド内にて変更する(※印部分)という方法をとります。
 * @author keita
 */
public class XYGRAPH_SQL extends JFrame implements MouseListener{
    String title,Xlabel,Ylabel;//タイトルの大きさ等の設定はJAVADRIVEにて参照のこと
    JDBCXYDataset data;//addするには初期化しておくことが必要
    double Xinterval,Yinterval,Xmin,Xmax,Ymin,Ymax;
    int Xpartition,Ypartition;
    ArrayList<Integer> LineStrokeSeries=new ArrayList<Integer>();
    ArrayList<BasicStroke> LineStroke=new ArrayList<BasicStroke>();
    ArrayList<Integer> LineColorSeries=new ArrayList<Integer>();
    ArrayList<Color> LineColor=new ArrayList<Color>();
    boolean X1=false,Y1=false,X2=false,Y2=false,leg=false,LSS=false,LCS=false,T=false,LogX=false,LogY=false;
    XYLineAndShapeRenderer shape=new XYLineAndShapeRenderer();
    int ind=0;
    JFreeChart chart;
    ChartPanel cpanel;
    boolean mouseflag=true;
    boolean mouseVerify=true;
    boolean ticklabel=true;
    int Mnum;
    Connection con;
    String Query;
    public XYGRAPH_SQL(String pass,String username,String password,String Query)throws SQLException{
        con = DriverManager.getConnection(pass, username, password);
        data=new JDBCXYDataset(con,"select disp, load from kp1;select disp a,load l from kp2;");
        this.Query=Query;
        shape.setSeriesShapesVisible(ind, false);//※プロット点を表示するか否かということ。逆に線を表示するか否かというメソッドもXYLineAndShapeRendererに存在するので相関図とか書きたかったらそこらへんをいじるといいよ。
        shape.setSeriesLinesVisible(ind, true);
    }
    public void LineConfig(int ind,Boolean ShapeVisible,Boolean LineVisible){
            shape.setSeriesShapesVisible(ind,ShapeVisible);
            shape.setSeriesLinesVisible(ind, LineVisible);
            ind++;
    }

    /**
     * グラフのタイトルを設定するメソッド
     * @param title　タイトルの内容をstring型で。
     * @param T  タイトルを表示するか否か。trueで表示falseで非表示
     */
    public void settitle(String title,boolean T){
        this.title=title;
        this.T=T;
    }
    /**
     * 凡例を消したい時はここ。
     */
    public void removelegend(){
        leg=true;
    }
    /**
     * 軸を対数軸にしたい時はここ
     * @param LogX　X軸を対数表示するかどうかのフラグ
     * @param LogY　Y軸を対数表示するかどうかのフラグ
     */
    public void setXYLogarithmic(boolean LogX,boolean LogY){
        this.LogX=LogX;
        this.LogY=LogY;
    }
    /**
     *目盛りの間隔を設定するメソッド
     * @param Xinterval 大きな目盛の間隔をあらわすdouble型
     * @param Xpartition 大きなめもりの間を小さな目盛で何分割するかというint型
     */
    public void setXtick(double Xinterval,int Xpartition){
        this.Xinterval=Xinterval;
        this.Xpartition=Xpartition;
        X1=true;
    }
    /**
     *X軸の最大最小の範囲を設定するメソッド
     * @param Xmin　設定する範囲の最小値
     * @param Xmax　設定する範囲の最大値
     */
    public void setXrange(double Xmin,double Xmax){
        this.Xmin=Xmin;this.Xmax=Xmax;X2=true;
    }
    /**
     *X軸のラベルを表示するメソッド
     * @param Xlabel　ラベルの内容
     */
    public void setXlabel(String Xlabel){
        this.Xlabel=Xlabel;
    }
    /**
     *Y軸の最大最小の範囲を設定するメソッド
     * @param Ymin　設定する範囲の最小値
     * @param Ymax　設定する範囲の最大値
     */
    public void setYrange(double Ymin,double Ymax){
        this.Ymin=Ymin;this.Ymax=Ymax;Y2=true;
    }
    /**
     *目盛りの間隔を設定するメソッド
     * @param Yinterval 大きな目盛の間隔をあらわすdouble型
     * @param Ypartition 大きなめもりの間を小さな目盛で何分割するかというint型
     */
    public void setYtick(double Yinterval,int Ypartition){
        this.Yinterval=Yinterval;
        this.Ypartition=Ypartition;
        Y1=true;
    }
    /**
     *Y軸のラベルを表示するメソッド
     * @param Ylabel　ラベルの内容
     */
    public void setYlabel(String Ylabel){
        this.Ylabel=Ylabel;
    }
     public void removeAllValue(String pass,String username,String password,String Query)throws SQLException{
         con = DriverManager.getConnection(pass, username, password);
        data=new JDBCXYDataset(con,Query);
        this.Query=Query;
     }
    /**
     * 線の太さを決めるメソッド
     * @param Series　線の太さを設定するグラフの凡例。識別記号。
     * @param width　線の太さをfloat型で設定。
     */
    public void setLineWidth(int Series,float width){
        BasicStroke Line=new BasicStroke(width);
        LineStrokeSeries.add(Series);
        LineStroke.add(Line);
        LSS=true;
    }
    /**
     * オーバーライド。線の太さと色を一気に変えるメソッド。
     * @param Series　設定する線の指定記号。凡例。
     * @param width　設定する線の太さ。
     * @param c　設定する線の色
     */
    public void setLineWidth(int SeriesIndex,float width,Color c){
        BasicStroke Line=new BasicStroke(width);
        LineStrokeSeries.add(SeriesIndex);
        LineStroke.add(Line);
        LineColorSeries.add(SeriesIndex);
        LineColor.add(c);
        LSS=true;
        LCS=true;
    }
    /**
     * 点線を設定するためのメソッド。
     * @param Series　設定する線の識別記号。凡例。
     * @param width　設定する線の太さ。
     * @param dash　設定する線の破線パターン。詳しくはググレカス。
     */
    public void setDashLine(int SeriesIndex,float width, float[] dash){
        BasicStroke Line=new BasicStroke(width,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,1.0f,dash,0.0f);
        //※詳細に点線を変えたければ他の変数をここで変えるべし。
        LineStrokeSeries.add(SeriesIndex);
        LineStroke.add(Line);
        LSS=true;
    }
    /**
     * 点線を設定するためのメソッド。
     * @param Series　設定する線の識別記号。凡例。
     * @param width　設定する線の太さ。
     * @param dash　設定する線の破線パターン。詳しくはググレカス。
     * @param c　設定する線の色
     */
    public void setDashLine(int SeriesIndex,float width, float[] dash,Color c){
        BasicStroke Line=new BasicStroke(width,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,1.0f,dash,0.0f);
        //詳細に点線を変えたければ他の変数をここで変えるべし。
        LineStrokeSeries.add(SeriesIndex);
        LineStroke.add(Line);
        LineColorSeries.add(SeriesIndex);
        LineColor.add(c);
        LSS=true;
        LCS=true;
    }
    /**
     * 線の色を決めるメソッド
     * @param Series　色を変える線の識別記号。凡例。
     * @param C　設定する線の色。
     */
    public void setLineColor(int SeriesIndex,Color C){
        LineColorSeries.add(SeriesIndex);
        LineColor.add(C);
        LCS=true;
    }
    public void setTickLabel(boolean ticklabel){
        this.ticklabel=ticklabel;
    }
    public static void main(String[] args) {
        try{
            XYGRAPH_SQL plt=new XYGRAPH_SQL("jdbc:h2:tcp://localhost/C:/DB/111206coupontest/KP","junapp","","select load,strain1,strain2 from kp1");
            plt.PLOT();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    /**
     *設定結果をまとめて、PLOTクラスでプロットする内容を表すメソッドです。
     */
    public void result(){
        //////////////グラフに関する内容/////////////////////////////////////////////////
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        chart=ChartFactory.createXYLineChart(title, Xlabel, Ylabel, data, PlotOrientation.VERTICAL, true, true, true);
        chart.setBackgroundPaint(Color.white);//※外の背景の色を設定するときはこれ※
        chart.getXYPlot().setOutlineVisible(true); //※外枠を消したいときはこれ※
        chart.getXYPlot().setDomainGridlinePaint(Color.BLACK);//※X軸側のグリットライン の色を設定する時はこれ※
        chart.getXYPlot().setDomainGridlinesVisible(true);//※X軸側のグリットラインを付ける又は消す時はこれ※
        chart.getXYPlot().setRangeGridlinePaint(Color.BLACK);//※Y軸側のグリットライン の色を設定する時はこれ※
        chart.getXYPlot().setRangeGridlinesVisible(true);//※Y軸側のぐりっとラインを付ける又は消す時はこれ※
        chart.getXYPlot().setBackgroundPaint(Color.white);//※プロットする空間の背景の色を変更したい時はこれ※
        if(T==true){
            TextTitle title2=new TextTitle(title);
            Font titleFont=new Font("IPA モナー Pゴシック",Font.PLAIN,12);//※タイトルのフォント※
            title2.setFont(titleFont);
            chart.setTitle(title2);
        }
        Font legendFont=new Font("IPA モナー Pゴシック",Font.PLAIN,12);//※凡例のフォント※
        LegendTitle legend = chart.getLegend();
        legend.setItemFont(legendFont);
        legend.setPosition(RectangleEdge.BOTTOM);///※凡例の位置。top,bottom,left,rightから選ぶ※
        if(leg==true){
            chart.removeLegend();
            leg=false;
        }
        Font XYlabelFont=new Font("IPA モナー Pゴシック",Font.PLAIN,12);//XY軸のラベルのフォント※


        /////Ｘ軸に関する内容////////////////////////////////////////////////////////////
        NumberAxis xaxis;//domeinaxis…Ｘ軸
        if(LogX==true){xaxis=new LogarithmicAxis(Xlabel);}
        else{xaxis=new NumberAxis(Xlabel);}
        xaxis.setVisible(true);//軸を全て消すかどうか※
        xaxis.setTickLabelsVisible(true);//ラベルを表示するか否か
        xaxis.setMinorTickMarksVisible(true);//ちっちゃい目盛を表示するか否か※
        xaxis.setTickMarksVisible(true);//大きな目盛を表示するか否か※
        if(X1==true){
            DecimalFormat Xformat=new DecimalFormat();
            NumberTickUnit Xtick=new NumberTickUnit(Xinterval,Xformat,Xpartition);//目盛について。最初の引数は大きな目盛の間隔を表すdouble型。二つ目は意味不明で三つ目の引数は大きな目盛間で小さな目盛を何等分入れるかというint型※
            xaxis.setTickUnit(Xtick);//ここまでが目盛間隔についての考察
            X1=false;
        }
        if(X2==true){
            xaxis.setRange(Xmin, Xmax);
            X2=false;
        }else{
            xaxis.setAutoRange(true);
        }//上限下限を設定する。
        xaxis.setLabelFont(XYlabelFont);
        xaxis.setTickLabelsVisible(ticklabel);//目盛の数値を消すかどうかということ
        chart.getXYPlot().setDomainAxis(xaxis);
        /////Y軸に関する内容///////////////////////////////////////////////////////////////
        NumberAxis yaxis;//rangeaxis…Y軸
        if(LogY==true){yaxis=new LogarithmicAxis(Ylabel);}
        else{yaxis=new NumberAxis(Ylabel);}
        yaxis.setTickLabelsVisible(true);//ラベルを表示するか否か
        yaxis.setVisible(true);//軸を全て消すかどうか※
        yaxis.setMinorTickMarksVisible(true);//ちっちゃい目盛を表示するか否か※
        yaxis.setTickMarksVisible(true);//大きな目盛を表示するか否か※
        if(Y1==true){
            DecimalFormat Yformat=new DecimalFormat("");
            NumberTickUnit Ytick=new NumberTickUnit(Yinterval,Yformat,Ypartition);//目盛について。最初の引数は大きな目盛の間隔を表すdouble型。二つ目は意味不明で三つ目の引数は大きな目盛間で小さな目盛を何等分入れるかというint型※
            yaxis.setTickUnit(Ytick);//ここまでが目盛間隔についての考察
            Y1=false;
        }
        if(Y2==true){
            yaxis.setRange(Ymin,Ymax );//上限下限を設定する。
            Y2=false;
        }else{
            yaxis.setAutoRange(true);
        }
        yaxis.setLabelFont(XYlabelFont);
        yaxis.setTickLabelsVisible(ticklabel);//目盛の数値を消すかどうかということ
        chart.getXYPlot().setRangeAxis(yaxis);
        /////////////////線幅や線の形状に関する内容////////////////////////
        if(LSS==true){
            for(int i=0;i<LineStrokeSeries.size();i++){

                shape.setSeriesStroke(LineStrokeSeries.get(i), LineStroke.get(i));
            }
        }
        if(LCS==true){
            for(int i=0;i<LineColorSeries.size();i++){
                shape.setSeriesPaint(LineColorSeries.get(i), LineColor.get(i));
            }
        }
        chart.getXYPlot().setRenderer(shape);

        cpanel =new ChartPanel(chart);
        cpanel.addMouseListener(this);
        if(mouseVerify==false){
            cpanel.removeMouseListener(this);
        }
        this.getContentPane().add(cpanel,BorderLayout.CENTER);
    }
    public JPanel getChartPanel(){
        this.result();
        return cpanel;
    }
    public JFreeChart getChart(){
        this.result();
        return chart;
    }
    public void mouseClicked(MouseEvent e){
        cpanel.mouseClicked(e);
        int x = (int) ((e.getX()) / cpanel.getScaleX());
        int y = (int) ((e.getY()) / cpanel.getScaleY());
//        System.out.println("パネル上  "+x+"  "+y);
        Rectangle2D plotArea = cpanel.getScreenDataArea();
        XYPlot plot = (XYPlot) chart.getPlot(); // your plot
        double chartX = plot.getDomainAxis().java2DToValue(x, plotArea, plot.getDomainAxisEdge());
        double chartY = plot.getRangeAxis().java2DToValue(y, plotArea, plot.getRangeAxisEdge());
        if(mouseflag){
            mouseflag=false;
            Mnum=data.getSeriesCount();
        }
        double min=0;
        int minindex=0;
        double[] snapX=new double[Mnum];
        double[] snapY=new double[Mnum];
        for(int i=0;i<Mnum;i++){
            for(int s=0;s<data.getItemCount(i);s++){
                double a=plot.getDomainAxis().valueToJava2D(data.getXValue(i, s), plotArea, plot.getDomainAxisEdge());
                double b=plot.getRangeAxis().valueToJava2D(data.getYValue(i, s), plotArea, plot.getRangeAxisEdge());
                double length=Math.pow(a-x, 2)+Math.pow(b-y, 2);
                if(s==0){
                    min=length;
                    minindex=s;
                    continue;
                }else if(length<min){
                    min=length;
                    minindex=s;
                    continue;
                }
            }
            snapX[i]=data.getXValue(i, minindex);
            snapY[i]=data.getYValue(i, minindex);
            System.out.println("KEY INDEX   "+i+"; INDEX   "+minindex+"   X座標 "+snapX[i]+"    Y座標 "+snapY[i]);
        }
        System.out.println();
//        int pind=ind;
//        int pind=2;
//        try{
////            data.executeQuery(Query+";select"+snapX[0]+","+snapY[0]);
//        }catch(Exception ee){
//            ee.printStackTrace();
//        }
//        data.addSeries(pind, new double[][]{snapX,snapY});
//        shape.setSeriesShapesVisible(pind, true);//※プロット点を表示するか否かということ。逆に線を表示するか否かというメソッドもXYLineAndShapeRendererに存在するので相関図とか書きたかったらそこらへんをいじるといいよ。
//        shape.setSeriesLinesVisible(pind, false);
//        shape.setSeriesPaint(pind, Color.cyan);
//        this.update();
    }
    public void setMouseVerify(boolean trueVerify){
        mouseVerify=trueVerify;
    }

    public void mousePressed(MouseEvent e){
        cpanel.mousePressed(e);
    }
    public void mouseReleased(MouseEvent e){
        cpanel.mouseReleased(e);
    }
    public void mouseEntered(MouseEvent e){
        cpanel.mouseEntered(e);
    }
    public void mouseExited(MouseEvent e){
        cpanel.mouseExited(e);
    }
    public void update(){
        chart.getXYPlot().datasetChanged(new DatasetChangeEvent(data,data));
        chart.plotChanged(new PlotChangeEvent(chart.getXYPlot()));
    }
    public void OUTPUTasJPEG(String pass,float quality,int width,int height){
        this.result();
        File file=new File(pass);
        try{
            ChartUtilities.saveChartAsJPEG(file, quality, this.chart, width, height);
        }catch(IOException e){e.printStackTrace();}
    }
    public void OUTPUTasPNG(String pass,int width,int height){
        this.result();
        File file=new File(pass);
        try{
            ChartUtilities.saveChartAsPNG(file, this.chart, width, height);
        }catch(IOException e){e.printStackTrace();}
    }
    public void OUTPUTasSVG(String pass,int width,int height){
        this.result();
        DOMImplementation domImpl=GenericDOMImplementation.getDOMImplementation();// DOMImplementationの取得
        Document document=domImpl.createDocument(null, "svg", null);// XMLドキュメントの作成
        SVGGraphics2D svg2d=new SVGGraphics2D(document);// SVGジェネレータの作成
        Rectangle2D r2d=new Rectangle(width,height);
        this.chart.draw(svg2d, r2d);// Chartの描画
        boolean useCSS=true;//CSSを使用する
        try{
            OutputStream os=new FileOutputStream(pass);
            BufferedOutputStream bos=new BufferedOutputStream(os);
            Writer out=new OutputStreamWriter(bos,"UTF-8");//文字コードの指定
            svg2d.stream(out,useCSS);//出力
        }catch(UnsupportedEncodingException ue){ue.printStackTrace();}
        catch(SVGGraphics2DIOException se){se.printStackTrace();}
        catch(IOException ioe){ioe.printStackTrace();}
    }
    public void OUTPUTasEPS(String pass,int width,int height){
        this.result();
        try{
            String epstitle="graph";
            OutputStream os=new FileOutputStream(pass);
            BufferedOutputStream bos=new BufferedOutputStream(os);
            EpsGraphics2D eps2d=new EpsGraphics2D(epstitle,bos,0,0,width,height);
            Rectangle2D r2d=new Rectangle(width,height);
            this.chart.draw(eps2d, r2d);
            eps2d.close();
            bos.close();
        }catch(IOException ioe){ioe.printStackTrace();}
    }
    public void PLOT(){
        this.result();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(10, 10, 500, 500);
        this.setTitle("グラフサンプル");
        this.setVisible(true);
    }
}
