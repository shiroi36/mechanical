/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IO_LIB;

import java.io.*;
/**
 *gnuplotのpltファイルを生成するためのクラスです。
 * @author araki keita
 * @version 1.0
 */
public class PLT_OPE {
    PrintWriter pw;
    /**
     *pltファイルをセットするクラスまず最初にこれを宣言する
     * 最後に必ずfinishメソッドをすることを忘れずに
     *@param pass 出力するpltファイルのパス
     */
    public void setPltFile(String pass){
        try{
            pw = new PrintWriter(new BufferedWriter(new FileWriter(pass)));

        }
        catch(IOException e){
            System.out.println("入出力エラーです");///ちゃんとフォルダを作ったか？
        }
    }
    /**
     *セットしたpltファイルに一行書きこむ
     *@param obj pltファイルに書き込む内容
     */
    public void println(String obj){
        pw.println(obj);
    }
    /**
     *X軸の範囲を指定するクラス
     *@param ini 範囲の初めの数
     *@param fin 範囲の終わりの数
     */
    public void setXrange(double ini,double fin){
        pw.println("set xrange ["+ini+":"+fin+"]");
    }
    /**
     *Y軸の範囲を指定するクラス
     *@param ini 範囲の初めの数
     *@param fin 範囲の終わりの数
     */
    public void setYrange(double ini,double fin){
        pw.println("set yrange ["+ini+":"+fin+"]");
    }
    /**
     *XY軸の軸名を記述するためのクラス
     *@param X　X軸のラベル名
     *@param Y　X軸のラベル名
     */
    public void setXYlabel(String X,String Y){
        pw.println("set xlabel \""+X+"\"");
        pw.println("set ylabel \""+Y+"\"");

    }
    /**
     *凡例の位置を決めるためのクラス
     *@param position left.左側 right.右側 top.上側 bottom.下側　outside.図の右側の外 below.図の下側の外のどれか
     */
    public void setTitlePosition(String position){
        pw.println("set key "+position);
    }
    /**
     *凡例の位置を決めるためのクラス
     *@param linenum 線の番号
     * @param linetype linenumの線種を決める
     * @param linewidth　linenumの線の太さを決める
     */
    public void setLineStyle(int linenum,int linetype,int linewidth){
        pw.println("set style line "+linenum+" lt "+linetype+" lw "+linewidth);
    }
    /**
     *一種類のグラフを描くためのコマンド
     *@param filename datファイルのファイル名
     * @param x x軸の数値がある列の数
     * @param y y軸の数値がある列の数
     * @param title 凡例の名前
     * @param linetype linesなど、線やヒストグラムなどを指定するコマンド
     */
    public void drawGraph(String filename,int x,int y,String title,String linetype){
        pw.println("plot \""+filename+"\" using "+x+":"+y+" ti \""+title+"\" w "+linetype+" ls 1");
    }
    /**
     *複数のグラフを描くためのコマンド
     *@param filename datファイルのファイル名
     * @param x filename[i]のx軸の数値がある列の数
     * @param y filename[i]のy軸の数値がある列の数
     * @param title filename[i]の凡例の名前
     * @param linetype filename[i]のlinesなど、線やヒストグラムなどを指定するコマンド
     */
    public void drawPluralGraph(String[] filename,int[] x,int[] y,String[] title,String[] linetype){
        pw.println("plot \""+filename[0]+
                    "\" using "+x[0]+":"+y[0]+" ti \""+title[0]+
                    "\" w "+linetype[0]+" ls 1,\\");
        for(int i=1;i<filename.length-1;i++){
            int I=1+i;
            pw.println("\""+filename[i]+
                    "\" using "+x[i]+":"+y[i]+" ti \""+title[i]+
                    "\" w "+linetype[i]+" ls "+I+",\\");
        }
        pw.println("\""+filename[filename.length-1]+
                "\" using "+x[filename.length-1]+":"+y[filename.length-1]+
                " ti \""+title[filename.length-1]+"\" w "+linetype[filename.length-1]+" ls "+filename.length);
    }
    /**
     *グラフのファイルをどのようなファイル形式で出力するかを指定するクラス
     *@param filetype ファイル形式を指定gif,post eps enh 18,dxf
     * @param filename ファイル名を指定
     */
    public void outputPIC(String filetype,String filename){
        pw.println("set term "+filetype);
        pw.println("set output \""+filename+"\"");
        pw.println("replot");
    }
    /**
     *終わりにこのメソッドを必ずする
     */
    public void finish(){
        pw.close();
    }
}
