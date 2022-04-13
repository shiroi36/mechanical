/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * ここはPOIをつかってエクセルファイルを入出力するクラスです
 */

package IO_LIB;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
//import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class XLS_OPE {
///このクラスを入れる時にはjacarta-POIをライブラリに入れておくこと
/////////////////////////////インプットに関するメソッド///////////////////////////
    HSSFWorkbook workbookin;
    HSSFSheet sheetin;
    public String outputpath;
    private HSSFFont f;
    private int fontsize;
    private HSSFCellStyle style;
    private String fontname;
    public XLS_OPE(String outputpath){
        this.outputpath=outputpath;
    }
    public XLS_OPE(){}
    
    public static void main(String[] args) {
        XLS_OPE xls=new XLS_OPE();
        xls.setPageName("0");
        xls.setRow(0);
        xls.setCol(0);
        xls.writeString("test");
        xls.saveFile("test.xls");
    }
    
    
    /**
     *入力するファイルを指定するメソッドです。
     * @param pass xlsファイルがあるパスをストリング型で表します
     * @param pagenum 指定したファイルのシートナンバー(はじめは0～)です。
     */
    public void setInput(String pass,int pagenum){
        FileInputStream in = null;
        workbookin = null;
        try{
                  in = new FileInputStream(pass);
                  POIFSFileSystem fs = new POIFSFileSystem(in);
                  workbookin = new HSSFWorkbook(fs);
                }catch(IOException e){
                  System.out.println(e.toString());
                }finally{
                  try{
                    in.close();
                  }catch (IOException e){
                    System.out.println(e.toString());
                  }
                    }
        sheetin = workbookin.getSheetAt(pagenum);
    }
    /**
     *ある行に数列があった場合に、その数列が入っているセルの数を計算するメソッドです。
     * @param iniRow 数列が始まる行（縦）を指定する
     * @param iniShort 数列が始まるセルの列（横）を指定する
     */
    public int getRowNum(int iniRow,int iniShort){
        int num=0;
        try{
        while(num>=0){
            HSSFRow row = sheetin.getRow(num+iniRow);
            HSSFCell cell = row.getCell((short)iniShort);
            cell.getNumericCellValue();
            num++;
//            System.out.println(num);
            }
        }
        catch(Exception e){
        }
        return num;
    }

    /**
     *ある行に数列があった場合に、その数列が入っているセルの数を計算するメソッドです。
     * @param iniRow 数列が始まる行（縦）を指定する
     * @param iniShort 数列が始まるセルの列（横）を指定する
     */
    public double[][] getMatrix(int iniRow,int iniShort){
        int rnum=0;
        int snum=0;
        ArrayList<Double> s=new ArrayList<Double>();
//        ArrayList<double[]> r=new ArrayList<double[]>();
        ArrayList<ArrayList<Double>> r=new ArrayList<ArrayList<Double>>();
        boolean flag=true;
        int pres=0;
        try{
            while(flag){
                HSSFRow row = sheetin.getRow(rnum+iniRow);
                try{
                    while(true){
                        HSSFCell cell = row.getCell((short)(iniShort+snum));
                        s.add(cell.getNumericCellValue());
                        snum++;
                    }
                }catch(Exception e){
                    System.out.println(s.size());
                    if(rnum!=0&&s.size()==pres){
                        flag=false;
                    }else{
                        pres=s.size();
                        r.add(s);
                        s=new ArrayList<Double>();
                        snum=0;
                    }
                }
                rnum++;
    //            System.out.println(num);
            }
        }
        catch(Exception e){}
        System.out.println(r.size());
        double[][] r2=new double[r.get(0).size()][r.size()];
        for(int i=0;i<r.size();i++){
            for(int t=0;t<r2.length;t++){
                r2[t][i]=r.get(i).get(t);
            }
        }
        return r2;
    }
    /**
     *特定の行(row)列(short)のセルから始まる数列の数値を取得するメソッドです。
     * @param Row 数値配列の最初の値があるセルの行(縦)セルのカウントは上から、0から
     * @param Short 数値配列の最初の値があるセルの列(横)セルのカウントは上から、0から
     * @param imputnum　その数値配列を読み込む長さを表す数
     */
    public double[] getSequenceDouble(int Row,int Short,int inputnum){
        double N[]=new double [inputnum];
        HSSFRow row;
        HSSFCell cell;
        for(int i=0;i<inputnum;i++){
            row = sheetin.getRow(i+Row);
//            System.out.println(i);
            cell = row.getCell((short)Short);
//            System.out.println(cell.toString());
            N[i]=cell.getNumericCellValue();
        }
        return N;
    }
    /**
     *特定の行(row)列(short)のセルから始まる数列の数値を取得するメソッドです。
     * @param Row 数値配列の最初の値があるセルの行(縦)セルのカウントは上から、0から
     * @param Short 数値配列の最初の値があるセルの列(横)セルのカウントは上から、0から
     * @param imputnum　その数値配列を読み込む長さを表す数
     */
    public int[] getSequenceInt(int Row,int Short,int inputnum){
        int N[]=new int [inputnum];
        HSSFRow row;
        HSSFCell cell;
        for(int i=0;i<inputnum;i++){
            row = sheetin.getRow(i+Row);
            cell = row.getCell((short)Short);
            N[i]=(int)cell.getNumericCellValue();
        }
        return N;
    }
    /**
     *特定の行(row)列(short)のセルから始まる数列の数値を取得するメソッドです。
     * @param Row 数値配列の最初の値があるセルの行(縦)セルのカウントは上から、0から
     * @param Short 数値配列の最初の値があるセルの列(横)セルのカウントは上から、0から
     * @param imputnum　その数値配列を読み込む長さを表す数
     */
    public String[] getSequenceString(int Row,int Short,int inputnum){
        String N[]=new String [inputnum];
        HSSFRow row;
        HSSFCell cell;
        for(int i=0;i<inputnum;i++){
            row = sheetin.getRow(i+Row);
            cell = row.getCell((short)Short);
            try{
                N[i]=cell.getStringCellValue();
            }catch(NullPointerException e){
                N[i]="";
            }
//            System.out.println(i);
        }
        return N;
    }
    /**
     *ある特定のセルをstring型で取得するメソッド
     * @param Row 数値配列の最初の値があるセルの行(縦)セルのカウントは上から、0から
     * @param Short 数値配列の最初の値があるセルの列(横)セルのカウントは上から、0から
     */
    public String getString(int Row,int Short){
        String S;
        HSSFRow row;
        HSSFCell cell;
        row = sheetin.getRow(Row);
        cell = row.getCell((short)Short);
        S=cell.getRichStringCellValue().getString();
        return S;
    }
    public double getDouble(int Row,int Short){
        double S;
        HSSFRow row;
        HSSFCell cell;
        row = sheetin.getRow(Row);
        cell = row.getCell((short)Short);
        S=cell.getNumericCellValue();
        return S;
    }

    HSSFWorkbook workbookout;
//    HSSFWorkbook workbookout= new HSSFWorkbook();
    HSSFSheet sheetout;
    HSSFRow rowout;
    HSSFCell cellout;
    boolean pagenum=false;
    /**
     *出力するxlsファイルのシート名を指定するメソッドです。
     * @param Pagename　出力するxlsファイルのシート名
     */
    public void setPageName(String Pagename){
        this.setPageName(Pagename, 11,"ＭＳ 明朝");
    }
    
    public void setPageName(String Pagename,int fontsize){
        this.setPageName(Pagename,fontsize,"ＭＳ 明朝");
    }
    
    public void setPageName(String Pagename,int fontsize,String fontname){
        if(pagenum==false){
            workbookout=new HSSFWorkbook();
        }
        sheetout = workbookout.createSheet(Pagename);
        pagenum=true;
        this.f=workbookout.createFont();
        this.fontname=fontname;
        f.setFontName(fontname);
        f.setFontHeightInPoints((short)fontsize);
        this.fontsize=fontsize;
        f.setColor(HSSFColor.BLACK.index);
    }
    /**
     *書き込むセルの行(縦)を指定するメソッドです
     * @param i 書き込むセルの行(縦)カウントは上から0から
     */
    public void setRow(int i){
        rowout = sheetout.createRow(i);
//        rowout.setHeightInPoints(20);
    }
    public void setCol(int i){
        cellout=null;
        cellout=rowout.createCell(i);
        CellStyle style=workbookout.createCellStyle();
//        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
        style.setVerticalAlignment(VerticalAlignment.BOTTOM);
        style.setFont(f);
        cellout.setCellStyle(style);
    }
    public void setCol(int i,int align,int vertical){
        cellout=null;
        cellout=rowout.createCell(i);
        this.style=workbookout.createCellStyle();
//        System.out.println(align+"\t"+vertical);
        
        if(align==0) style.setAlignment(HorizontalAlignment.LEFT);
//        if(align==0) style.setAlignment(CellStyle.ALIGN_LEFT);
        else if(align==1) style.setAlignment(HorizontalAlignment.CENTER);
//        else if(align==1) style.setAlignment(CellStyle.ALIGN_CENTER);
        else if(align==2) style.setAlignment(HorizontalAlignment.RIGHT);
//        else if(align==2) style.setAlignment(CellStyle.ALIGN_RIGHT);
        
        if(vertical==0)style.setVerticalAlignment(VerticalAlignment.TOP);
//        if(vertical==0)style.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        else if(vertical==1)style.setVerticalAlignment(VerticalAlignment.CENTER);
//        else if(vertical==1)style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        else if(vertical==2)style.setVerticalAlignment(VerticalAlignment.BOTTOM);
//        else if(vertical==2)style.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
        
        style.setFont(f);
        
        cellout.setCellStyle(style);
    }
    
    public void setColumnWidth(int col,int breadth){
        sheetout.setColumnWidth(col, breadth);
    }
    
    /**
     *指定した列(横)にString型を書き込むメソッドです。
     * @param col　指定するセルの列。カウントは右から0から
     * @param s　指定したセルに書き込む内容
     */
    public void writeString(int col,String s){
        cellout = rowout.createCell(col);
        cellout.setCellValue(s);
    }
    public void writeString(String s){
        cellout.setCellValue(s);
    }
    public void writeString(String s,Color c){
        HSSFCellStyle style=cellout.getCellStyle();
        Font f2=workbookout.createFont();
        if(c==Color.red||c==Color.RED)f2.setColor(HSSFColor.RED.index);
        if(c==Color.blue||c==Color.BLUE)f2.setColor(HSSFColor.BLUE.index);
        if(c==Color.black||c==Color.BLACK)f2.setColor(HSSFColor.BLACK.index);
        if(c==Color.green||c==Color.GREEN)f2.setColor(HSSFColor.GREEN.index);
        f2.setFontHeightInPoints((short)fontsize);
        f2.setFontName(fontname);
        style.setFont(f2);
        cellout.setCellValue(s);
//        style.setFont(f);
    }
    public void writeString(String s,Color c,String fontname){
        HSSFCellStyle style=cellout.getCellStyle();
        Font f2=workbookout.createFont();
        if(c==Color.red||c==Color.RED)f2.setColor(HSSFColor.RED.index);
        if(c==Color.blue||c==Color.BLUE)f2.setColor(HSSFColor.BLUE.index);
        if(c==Color.black||c==Color.BLACK)f2.setColor(HSSFColor.BLACK.index);
        if(c==Color.green||c==Color.GREEN)f2.setColor(HSSFColor.GREEN.index);
        f2.setFontHeightInPoints((short)fontsize);
        f2.setFontName(fontname);
        style.setFont(f2);
        cellout.setCellValue(s);
//        style.setFont(f);
    }
    public void writeImage(String path,double scale,double Lx,double Ly) throws IOException{
        double w=sheetout.getColumnWidthInPixels(0);
        double r=sheetout.getDefaultRowHeightInPoints()*1.33;
        double val0=w/r;
        double val1=Ly/Lx;
        System.out.println(w+"\t"+r);
        this.writeImage(path, scale, scale*val0*val1);
    }
    public void writeImage(String path,double scalex,double scaley) throws IOException{
        
        // イメージファイル作成
//        BufferedImage img = ImageIO.read(file);
//        ImageIO.write(img, "png", byteArrayOut);
        FileInputStream img = new FileInputStream(path);
        byte[] bytes = IOUtils.toByteArray(img);
        
        int picIndex = workbookout.addPicture(bytes, HSSFWorkbook.PICTURE_TYPE_PNG);
        img.close();
        HSSFCreationHelper helper =(HSSFCreationHelper) workbookout.getCreationHelper();
        HSSFPatriarch patriarch = sheetout.createDrawingPatriarch();
        
        HSSFClientAnchor anchor = helper.createClientAnchor();
        anchor.setDx1(0);
        anchor.setDx2(0);
        anchor.setDy1(0);
        anchor.setDy2(0);
        anchor.setCol1(cellout.getColumnIndex());
        anchor.setCol2(cellout.getColumnIndex());
        anchor.setRow1(rowout.getRowNum());
        anchor.setRow2(rowout.getRowNum());
//        HSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0,
//                cellout.getColumnIndex(), rowout.getRowNum(), 
//                cellout.getColumnIndex(), rowout.getRowNum()
//        );
        anchor.setAnchorType(AnchorType.MOVE_AND_RESIZE);
//        anchor.setAnchorType(ClientAnchor.MOVE_AND_RESIZE);
        HSSFPicture pic = patriarch.createPicture(anchor, picIndex);
        //元の画像のサイズに合わせてリサイズ
//                    pic.resize();
        pic.resize(scalex, scaley);
//        pic.resize(scale);
    }
    /**
     *指定した列(横)にString型を書き込むメソッドです。
     * @param col　指定するセルの列。カウントは右から0から
     * @param s　指定したセルに書き込む内容
     */
    public void writeDouble(int col,double s){
        cellout = rowout.createCell(col);
//        cellout = rowout.createCell((short)Short);
        cellout.setCellValue(s);
    }
    public void writeDouble(double s){
//        cellout = rowout.createCell((short)Short);
        cellout.setCellValue(s);
    }
    
    public void setBorder(boolean top,boolean bottom,boolean left,boolean right){
        HSSFCellStyle style=cellout.getCellStyle();

        if(top)style.setBorderTop(BorderStyle.THIN);
//        if(top)style.setBorderTop(CellStyle.BORDER_THIN);
        if(bottom)style.setBorderBottom(BorderStyle.THIN);
//        if(bottom)style.setBorderBottom(CellStyle.BORDER_THIN);
        if(left)style.setBorderLeft(BorderStyle.THIN);
//        if(left)style.setBorderLeft(CellStyle.BORDER_THIN);
        if(right)style.setBorderRight(BorderStyle.THIN);
//        if(right)style.setBorderRight(CellStyle.BORDER_THIN);
    }
    public void setBorderTopBold(boolean top,boolean bottom,boolean left,boolean right){
        HSSFCellStyle style=cellout.getCellStyle();

        if(top)style.setBorderTop(BorderStyle.MEDIUM);
        if(bottom)style.setBorderBottom(BorderStyle.THIN);
        if(left)style.setBorderLeft(BorderStyle.THIN);
        if(right)style.setBorderRight(BorderStyle.THIN);
    }
    public void setBorderBottomBold(boolean top,boolean bottom,boolean left,boolean right){
        HSSFCellStyle style=cellout.getCellStyle();

        if(top)style.setBorderTop(BorderStyle.THIN);
        if(bottom)style.setBorderBottom(BorderStyle.MEDIUM);
        if(left)style.setBorderLeft(BorderStyle.THIN);
        if(right)style.setBorderRight(BorderStyle.THIN);
    }
    
    
    /**
     *指定したセルから下へ数列を書き込むメソッドです。
     * @param Row 数列のはじめの数値を書き込む行(縦)。カウントは上から0から
     * @param Short　数列の初めの数値を書き込む列(横)。カウントは上から0から
     * @param N 書き込む数列
     * @param num 数列を書き込む数
     */
    public void writeSequence(int Row,int Short,double[][] N,int num){
        for(int i=0;i<num;i++){
            rowout = sheetout.createRow(Row+i);
            for(int s=0;s<N.length;s++){
                int S=s+Short;
                if(i>N[s].length-1){
                    cellout = rowout.createCell((short)S);
                    cellout.setCellValue("");
                }
                else{
                    cellout = rowout.createCell((short)S);
                    cellout.setCellValue(N[s][i]);
                }
            }
        }
    }
    /**
     *指定したセルから下へ数列を書き込むメソッドです。
     * @param Row 数列のはじめの数値を書き込む行(縦)。カウントは上から0から
     * @param Short　数列の初めの数値を書き込む列(横)。カウントは上から0から
     * @param N 書き込む数列
     * @param num 数列を書き込む数
     */
    public void writeSequence(int Row,int Short,int[][] N,int num){
        for(int i=0;i<num;i++){
            rowout = sheetout.createRow(Row+i);
            for(int s=0;s<N.length;s++){
                int S=s+Short;
                if(i>N[s].length-1){
                    cellout = rowout.createCell((short)S);
                    cellout.setCellValue("");
                }
                else{
                    cellout = rowout.createCell((short)S);
                    cellout.setCellValue(N[s][i]);
                }
            }
        }
    }
    public void writeSequence(int Row,int Short,String[][] N,int num){
        for(int i=0;i<num;i++){
            rowout = sheetout.createRow(Row+i);
            for(int s=0;s<N.length;s++){
                int S=s+Short;
                if(i>N[s].length-1){
                    cellout = rowout.createCell((short)S);
                    cellout.setCellValue("");
                }
                else{
                    cellout = rowout.createCell((short)S);
                    cellout.setCellValue(N[s][i]);
                }
            }
        }
    }
    /**
     *指定したセルから下へ数列を書き込むメソッドです。
     * @param Row 数列のはじめの数値を書き込む行(縦)。カウントは上から0から
     * @param Short　数列の初めの数値を書き込む列(横)。カウントは上から0から
     * @param N 書き込む数列
     * @param num 数列を書き込む数
     */
    public void writeSequence(int Row,int Short,double[] N,int num){
        for(int i=0;i<num;i++){
            rowout = sheetout.createRow(Row+i);
            cellout = rowout.createCell((short)Short);
            cellout.setCellValue(N[i]);
        }
    }
    /**
     *指定したセルから下へ数列を書き込むメソッドです。
     * @param Row 数列のはじめの数値を書き込む行(縦)。カウントは上から0から
     * @param Short　数列の初めの数値を書き込む列(横)。カウントは上から0から
     * @param N 書き込む数列
     * @param num 数列を書き込む数
     */
    public void writeSequence(int Row,int Short,int[] N,int num){
        for(int i=0;i<num;i++){
            rowout = sheetout.createRow(Row+i);
            cellout = rowout.createCell((short)Short);
            cellout.setCellValue(N[i]);
        }
    }
    /**
     *指定したセルから下へ数列を書き込むメソッドです。
     * @param Row 数列のはじめの数値を書き込む行(縦)。カウントは上から0から
     * @param Short　数列の初めの数値を書き込む列(横)。カウントは上から0から
     * @param N 書き込む数列
     * @param num 数列を書き込む数
     */
    public void writeSequence(int Row,int Short,String[] N,int num){
        for(int i=0;i<num;i++){
            rowout = sheetout.createRow(Row+i);
            cellout = rowout.createCell((short)Short);
            cellout.setCellValue(N[i]);
        }
    }
    
//    public void Marge(int rows,int rowg,int cols,int colg){
//        sheetout.addMergedRegion(new CellRangeAddress(rows, rowg, cols, colg));
//    }
    
    
    /**
     *出力するxlsファイルをどこに保存するかを指定するメソッド
     * @param pass 保存するxlsファイルのパスをstring型で指定。
     */
    public void saveFile(String pass){
        FileOutputStream out = null;
            try{
              out = new FileOutputStream(pass);
              workbookout.write(out);
            }catch(IOException e){
              System.out.println(e.toString());
            }finally{
              try {
                out.close();
              }catch(IOException e){
                System.out.println(e.toString());
              }
            }
            pagenum=false;
    }
    public void clear(){
    }


}
