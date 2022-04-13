/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 実験データはＣＳＶ形式で保存されるということなので
 * このクラスでＣＳＶを取り込むこととしようと思い立つ
 */

package IO_LIB;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author keita
 */
public class SPACE_OPE {
    String pass;
    public void setCSV(String pass){
        this.pass=pass;
    }
    public double[][] getMatrix(int InitialRow){
        ArrayList<String[]> element=new ArrayList<String[]>();
        try{
            BufferedReader file=new BufferedReader(new FileReader(pass));
            for(int i=0;i<InitialRow;i++){
                file.readLine();
            }
            String line;
            boolean indflag=true;
            int indnum=0;
            while((line=file.readLine())!=null){
                String[] input=line.split(" ");//コンマ以外で区別するならここを区切る。
                ArrayList<String> input2=new ArrayList<String>();
                for(int i=0;i<input.length;i++){
                    if(input[i].length()==0){
                        continue;
                    }
                    else{
                        input2.add(input[i]);
                    }
                }
                String[] input3=new String[input2.size()];
                if(indflag==true){
                    indnum=input2.size();
                    indflag=false;
                }
                if(indnum!=input2.size()){
                    break;
                }
                for(int i=0;i<input2.size();i++){
                    input3[i]=input2.get(i);
                }
                element.add(input3);
            }
        }catch(IOException e){e.printStackTrace();}
        double[][] out=new double[element.get(0).length][element.size()];
            for(int i=0;i<out.length;i++){
                for(int s=0;s<out[i].length;s++){
                    try{

                        out[i][s]=Double.parseDouble(element.get(s)[i]);

                    }catch(NumberFormatException e){
                        System.out.println(i);
                        System.out.println(element.get(s)[i]);
                        out[i][s]=out[i][s-1];
                    }
                }
            }
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
                String[] input=line.split(" ");//コンマ以外で区別するならここを区切る。
                String[] input2=new String[input.length-InitialShort];
                System.out.println(input.length);
                for(int i=0;i<input2.length;i++){
                    input2[i]=input[i+InitialShort];
                }
                element.add(input2);
            }
        }catch(IOException e){e.printStackTrace();}
        String[][] out=new String[element.get(0).length][element.size()];
        try{
            for(int i=0;i<element.size();i++){
                String[] inputed=element.get(i);
                for(int s=0;s<inputed.length;s++){
                    out[s][i]=inputed[s];
                }
            }
        }catch(NumberFormatException e){e.printStackTrace();}
        return out;
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
                String[] input=line.split(" ");
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
