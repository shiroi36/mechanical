/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 実験データはＣＳＶ形式で保存されるということなので
 * このクラスでＣＳＶを取り込むこととしようと思い立つ
 */

package IO_LIB;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.System;
import java.util.Arrays;
/**
 *
 * @author keita
 */
public class CSV_OPE {
    String pass;
    public void setCSV(String pass){
        this.pass=pass;
    }
    public double[][] getMatrix(int InitialRow,int InitialShort){
        ArrayList<String[]> element=new ArrayList<String[]>();
        try{
            BufferedReader file=new BufferedReader(new FileReader(pass));
            for(int i=0;i<InitialRow;i++){
                file.readLine();
            }
            String line;
            while((line=file.readLine())!=null){
                String[] input=line.split(",");//コンマ以外で区別するならここを区切る。
                String[] input2=new String[input.length-InitialShort];
                for(int i=0;i<input2.length;i++){
                    input2[i]=input[i+InitialShort];
                }
                element.add(input2);
            }
        }catch(IOException e){e.printStackTrace();}
        double[][] out=new double[element.get(0).length][element.size()];
        try{
            for(int i=0;i<element.size();i++){
                String[] inputed=element.get(i);
                for(int s=0;s<inputed.length;s++){
                    System.out.println(inputed[s]+"e");
                    out[s][i]=Double.parseDouble(inputed[s]);
                }
            }
        }catch(NumberFormatException e){e.printStackTrace();}
        return out;
    }
    public String[][] getMatrixString(int InitialRow,int InitialShort){
        ArrayList<String[]> element=new ArrayList<String[]>();
        try{
            BufferedReader file=new BufferedReader(new FileReader(pass));
            for(int i=0;i<InitialRow;i++){
                file.readLine();
            }
            String line;
            while((line=file.readLine())!=null){
                String[] input=line.split(",");//コンマ以外で区別するならここを区切る。
                String[] input2=new String[input.length-InitialShort];
                for(int i=0;i<input2.length;i++){
                    input2[i]=input[i+InitialShort];
                }
                element.add(input2);
            }
        }catch(IOException e){e.printStackTrace();}
        
        return (String[][])element.toArray(new String[0][]);
    }
    public double[] getSequence(int InitialRow,int InitialShort){
        ArrayList<String> element=new ArrayList<String>();
        try{
            BufferedReader file=new BufferedReader(new FileReader(pass));
            for(int i=0;i<InitialRow;i++){
                file.readLine();
            }
            String line;
            while((line=file.readLine())!=null){
                String[] input=line.split(",");
//                System.out.println(Arrays.toString(input));
                String input2=input[InitialShort];
                element.add(input2);
            }
        }catch(IOException e){e.printStackTrace();}
        double[] out=new double[element.size()];
        try{
            for(int i=0;i<element.size();i++){
                out[i]=Double.parseDouble(element.get(i));
            }
        }catch(NumberFormatException e){e.printStackTrace();}
        return out;
    }
    
}
