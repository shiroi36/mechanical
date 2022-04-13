/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO_LIB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.BreakClear;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblLayoutType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblLayoutType;

/**
 *
 * @author keita
 */
public class WordWriter {

    private final String path;
    private  FileOutputStream out;
    private final XWPFDocument document;

    public WordWriter(String path)  {
        this.path = path;
        try {
            this.out = new FileOutputStream(new File(path));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WordWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
//        RecordInputStream ris=new 
        this.document = new XWPFDocument();

        CTDocument1 doc1 = document.getDocument();
        CTBody body = doc1.getBody();
        CTSectPr section = (body.isSetSectPr() ? body.getSectPr() : body.addNewSectPr());
        CTPageMar pageMar = (section.isSetPgMar() ? section.getPgMar() : section.addNewPgMar());
        CTPageSz pageSize = (section.isSetPgSz() ? section.getPgSz() : section.addNewPgSz());

////        マージン、ヘッダ・フッタは単位がmmではなくポイント×20
        int top = 34 * 20;
        int bot = 34 * 20;
        int l = 57 * 20;
        int r = 57 * 20;
        pageMar.setTop(BigInteger.valueOf(top));
        pageMar.setBottom(BigInteger.valueOf(bot));
        pageMar.setLeft(BigInteger.valueOf(l));
        pageMar.setRight(BigInteger.valueOf(r));

        // 用紙サイズ
        pageSize.setOrient(STPageOrientation.PORTRAIT);
        pageSize.setW(BigInteger.valueOf(595 * 20));
        pageSize.setH(BigInteger.valueOf(842 * 20));

    }

    public void setImage(String path, double w_mm, double Lx, double Ly) {
        //メインフォントサイズ
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = paragraph.createRun();
        run.setFontSize(8);
        run.setFontFamily("Osaka");
        String imgFile = "0.png";
        FileInputStream is;
        try {
            is = new FileInputStream(imgFile);
            run.addPicture(is, XWPFDocument.PICTURE_TYPE_PNG,
                    imgFile, Units.toEMU(2.83 * w_mm), Units.toEMU(2.83 * w_mm / Lx * Ly)); // 200x200 pixels
            is.close();
        } catch (Exception ex) {
            Logger.getLogger(WordWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
//        run.addBreak();
//        run.addBreak();
    }
    
    public void setTable(String[][] val){
        
        XWPFTable table = document.createTable(val.length,val[0].length);
        //中央そろえ
        CTTblPr pr       = table.getCTTbl().getTblPr();
        CTJc jc = pr.addNewJc();
        jc.setVal(STJc.CENTER);
        pr.setJc(jc);

        for (int i = 0; i < val.length; i++) {
            String[] vals = val[i];
            for (int j = 0; j < vals.length; j++) {
                String val1 = vals[j];
                this.setText(val1, table.getRow(i).getCell(j).getParagraphs().get(0));
            }
        }
        
    }
    
    public void setTable(String[] header,String[][] val,double w_mm){
        this.setTable(header, val, w_mm, true);
    }
    public void setTable(String[] header,String[][] val,double w_mm,boolean flag){
        int num=55;//現状レイアウト(余白12-12-20-20, フォント8pt)で1ページに入るぎりぎり
        this.setTable(num,header,val,w_mm,flag);
    }    
    public void setTable(int num,String[] header,String[][] val,double w_mm,boolean flag){
        
        int page=(int)(val.length/num)+1;
        int lnum=val.length-num*(int)(val.length/num);
        
        String[][][] val2=new String[page][][];
        
        int p=0;
        int i0=0;
        for (int i = 0; i < val.length; i++) {
            String[] val00 = val[i];
            if(i==0){
                i0=0;
                int len=0;
                if(p==page-1){
                    len=lnum+1;
                }else{
                    len=num+1;
                }
                System.out.println("p = " + p);
                System.out.println("len = " + len);
                val2[p]=new String[len][];
                val2[p][0]=header;
                val2[p][1]=val00;
                System.out.println(Arrays.toString(val00));
                i0++;
            }else if(i0==num-1){
                val2[p][i0+1]=val00;
                i0=0;
                p++;
                int len=0;
                if(p==page-1){
                    len=lnum+1;
                }else{
                    len=num+1;
                }
                System.out.println("p = " + p);
                System.out.println("len = " + len);
                val2[p]=new String[len][];
                val2[p][0]=header;
            }else if(i0<num-1){
                val2[p][i0+1]=val00;
                i0++;
            }
        }
        
        for (int i = 0; i < val2.length; i++) {
//            System.out.println("i = " + i);
            String[][] val00 = val2[i];
//            for (int j = 0; j < val00.length; j++) {
//                String[] strings = val00[j];
//                System.out.println(Arrays.toString(strings));
//            }
//            System.out.println("");
            this.setTable(val00, w_mm);
            if(flag)this.PAGE_RETURN();
        }
        
    }
    
    public void setTable(String[][] val,double w_mm){
        int w=(int)(w_mm*2.90*20);
        XWPFTable table = document.createTable(val.length,val[0].length);
        CTTblLayoutType type = table.getCTTbl().getTblPr().addNewTblLayout();
        type.setType(STTblLayoutType.FIXED);  // 固定幅を設定
        for (int j = 0; j < val[0].length; j++) {
            CTTc ctTc = table.getRow(0).getCell(j).getCTTc();
            CTTcPr tcPr = (ctTc.isSetTcPr() ? ctTc.getTcPr() : ctTc.addNewTcPr());
            CTTblWidth tblWidth = (tcPr.isSetTcW() ? tcPr.getTcW() : tcPr.addNewTcW());
            tblWidth.setW(BigInteger.valueOf(w));
        }
        
        //中央そろえ
        CTTblPr pr       = table.getCTTbl().getTblPr();
        CTJc jc = pr.addNewJc();
        jc.setVal(STJc.CENTER);
        pr.setJc(jc);
        
        for (int i = 0; i < val.length; i++) {
            String[] vals = val[i];
            for (int j = 0; j < vals.length; j++) {
                String val1 = vals[j];
                this.setText(val1, table.getRow(i).getCell(j).getParagraphs().get(0),1);
                
                //線種の設定？
                int lw=4;
                CTTc ctTc = table.getRow(i).getCell(j).getCTTc();
                CTTcPr tcPr = (ctTc.isSetTcPr() ? ctTc.getTcPr() : ctTc.addNewTcPr());
                CTTcBorders tblBorders = (tcPr.isSetTcBorders() ? tcPr.getTcBorders() : tcPr.addNewTcBorders());
                CTBorder ctbTop = (tblBorders.isSetTop() ? tblBorders.getTop() : tblBorders.addNewTop());
                ctbTop.setVal(STBorder.SINGLE);
                ctbTop.setSz(BigInteger.valueOf(lw));
                CTBorder ctbBot = (tblBorders.isSetBottom() ? tblBorders.getBottom() : tblBorders.addNewBottom());
                ctbBot.setVal(STBorder.SINGLE);
                ctbBot.setSz(BigInteger.valueOf(lw));
                CTBorder ctbRig = (tblBorders.isSetRight() ? tblBorders.getRight() : tblBorders.addNewRight());
                ctbRig.setVal(STBorder.SINGLE);
                ctbRig.setSz(BigInteger.valueOf(lw));
                CTBorder ctbLef = (tblBorders.isSetLeft() ? tblBorders.getLeft() : tblBorders.addNewLeft());
                ctbLef.setVal(STBorder.SINGLE);
                ctbLef.setSz(BigInteger.valueOf(lw));
                
            }
        }
        
    }
    
    
    

    public void RETURN() {
        //メインフォントサイズ
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = paragraph.createRun();
        run.setFontSize(8);
        run.setFontFamily("Osaka");
    }

    public void PAGE_RETURN() {
        //メインフォントサイズ
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = paragraph.createRun();
        run.setFontSize(8);
        run.setFontFamily("Osaka");
        run.addBreak(BreakType.PAGE);
    }

    private void init(XWPFRun run, boolean supersub) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run = paragraph.createRun();
        run.setFontSize(8);
        run.setFontFamily("Osaka");
        if (supersub) {
            run.setSubscript(VerticalAlign.SUPERSCRIPT);
        }
    }

    public void setText(String text,XWPFParagraph para) {
        this.setText(text, para, 0);
    }
    public void setText(String text,XWPFParagraph para,int align) {
        int num = 30;
        XWPFRun[] run = new XWPFRun[num];
        boolean flag = true;
        String input = "";
        int i0 = 0;
//        XWPFParagraph para = document.createParagraph();
        if(align==0){
            para.setAlignment(ParagraphAlignment.LEFT);
        }else if(align==1){
            para.setAlignment(ParagraphAlignment.CENTER);
        }else{
            para.setAlignment(ParagraphAlignment.RIGHT);
        }
        para.setSpacingAfter(5);
        para.setSpacingBefore(5);
        para.setIndentFromLeft(0);
        run[i0] = para.createRun();
        run[i0].setFontSize(8);
        run[i0].setFontFamily("Osaka");

        for (int i = 0; i < text.length(); i++) {
            String a0 = text.substring(i, i + 1);

            if (a0.trim().equals("^") && i == text.length() - 1) {
                if (flag) {
                    run[i0] = para.createRun();
                    run[i0].setFontSize(8);
                    run[i0].setFontFamily("Osaka");
                    run[i0].setText(input);
                } else {
                    run[i0] = para.createRun();
                    run[i0].setFontSize(8);
                    run[i0].setFontFamily("Osaka");
                    run[i0].setSubscript(VerticalAlign.SUPERSCRIPT);
                    run[i0].setText(input);
                }
                continue;
            } else if (a0.trim().equals("^")) {
                if (flag) {
                    run[i0] = para.createRun();
                    run[i0].setFontSize(8);
                    run[i0].setFontFamily("Osaka");
                    run[i0].setText(input);
                    i0++;
                    flag = false;
                    input = "";
                } else {
                    run[i0] = para.createRun();
                    run[i0].setFontSize(8);
                    run[i0].setFontFamily("Osaka");
                    run[i0].setSubscript(VerticalAlign.SUPERSCRIPT);
                    run[i0].setText(input);
                    i0++;
                    flag = true;
                    input = "";
                }
                continue;
            } else if (i == text.length() - 1) {
                if (flag) {
                    input += a0;
                    run[i0] = para.createRun();
                    run[i0].setFontSize(8);
                    run[i0].setFontFamily("Osaka");
                    run[i0].setText(input);
                } else {
                    input += a0;
                    run[i0] = para.createRun();
                    run[i0].setFontSize(8);
                    run[i0].setFontFamily("Osaka");
                    run[i0].setSubscript(VerticalAlign.SUPERSCRIPT);
                    run[i0].setText(input);
                }
                continue;
            }
            input += a0;
        }
    }

    public void setText(String text) {
        this.setText(text, document.createParagraph());
    }

    public void finish()  {
        try {
            document.write(out);
//            out.close();
        } catch (IOException ex) {
            Logger.getLogger(WordWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Done. See " + path);
    }

}
