/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EXP_LIB.ARRAY;
import IO_LIB.PRINT_OPE;

/**
 *
 * @author araki keita
 */
public class ArrayOPE extends PRINT_OPE{

    public double[] getInitializedArray(double[] array){
        double[] a=new double[array.length];
        double ini=array[0];
        for (int i = 0; i < a.length; i++) {
            a[i]=array[i]-ini;
        }
        return a;
    }
    /**
     * 配列をshortnum列に振り分ける
     * @param array2　　振り分け対象となる配列。一次元配列データ
     * @param shortnum　　何列に並ばせるかの配列
     * @return　　array[]をshortnum列にした多次元配列。
     */
    public double[][]  SPLIT(double[] array,int shortnum){
        double[] x=new double[shortnum];
        int rownum=array.length/shortnum+array.length%shortnum;
        double[][] result=new double[shortnum][rownum];
        int xnum=0,x2num=0;
        for(int i=0;i<array.length;i++){
            result[xnum][x2num]=array[i];
            xnum++;
            if(xnum==shortnum){
                x2num++;
                xnum=0;
            }
        }
        return result;
    }
    public double[] SPLIT(double[] array,int start,int goal){
        int num=goal-start;
        double[] a=new double[num];
        for(int i=0;i<num;i++){
            a[i]=array[start+i];
        }
        return a;
    }
    /**
     * 配列arrayを、インデックスstartnumからインデックスendnumまでで区切る
     * @param array2  区切る対象の配列
     * @param startnum 配列arrayにおいて区切り始めのインデックス
     * @param endnum　配列arrayにおいて区切り終わりのインデックス
     * @return　区切った配列を返す
     */
    public double[][] limitArray(double[][] array,int startnum,int endnum){
        int num=endnum-startnum+1;
//        System.out.println(num+"    "+array.length);
        double array2[][]=new double[array.length][num];
        for(int i=0;i<num;i++){
            for(int s=0;s<array.length;s++){
                array2[s][i]=array[s][startnum+i];
            }
        }
        return array2;
    }
    public double[] limitArray(double[] array,int startnum,int endnum){
        int num=endnum-startnum+1;
//        System.out.println(num+"    "+array.length);
        double array2[]=new double[num];
        for(int i=0;i<num;i++){
            array2[i]=array[startnum+i];
        }
        return array2;
    }
    public double[] limitArray(double[] array,int length){
//        System.out.println(num+"    "+array.length);
        double array2[]=new double[length];
        System.arraycopy(array, 0, array2, 0, length);
        return array2;
    }
    /**
     * 列を足し合わせるメソッド
     * @param added　足し合わせられる列
     * @param add　足し合わせる列
     */
    public void add(double[] added,double[] add){
        for(int i=0;i<added.length;i++){
            added[i]+=add[i];
        }
    }
    /**
     * arrayの全データをvalueで引く
     * @param array2　　valueで引く配列
     * @param value　　一律引く値
     */
    public void minus(int[][] array,int value){
        for(int i=0;i<array.length;i++){
            for(int s=0;s<array[i].length;s++){
                array[i][s]-=value;
            }
        }
    }
     public void plus(int[][] array,int value){
        for(int i=0;i<array.length;i++){
            for(int s=0;s<array[i].length;s++){
                array[i][s]+=value;
            }
        }
    }
     public void multiply(int[][] array,int value){
        for(int i=0;i<array.length;i++){
            for(int s=0;s<array[i].length;s++){
                array[i][s]=array[i][s]*value;
            }
        }
    }
/**
     * arrayの全データをvalueで引く
     * @param array2　　valueで引く配列
     * @param value　　一律引く値
     */
    public double[] minus(double[] array,double value){
        for(int i=0;i<array.length;i++){
                array[i]-=value;
        }
        return array;
    }
    /**
     * arrayの全データをvalueで引く
     * @param array2　　valueで引く配列
     * @param value　　一律引く値
     */
    public double[][] minus(double[][] array,double value){
        for(int i=0;i<array.length;i++){
            for(int s=0;s<array[i].length;s++){
                array[i][s]-=value;
            }
        }
        return array;
    }
    public void plus(double[][] array,double value){
        for(int i=0;i<array.length;i++){
            for(int s=0;s<array[i].length;s++){
                array[i][s]+=value;
            }
        }
    }
    public double[][] multiply(double[][] array,double value){
        double[][] array2=array;
        for(int i=0;i<array2.length;i++){
            for(int s=0;s<array2[i].length;s++){
                array2[i][s]=array2[i][s]*value;
            }
        }
        return array2;
    }
    
    public double[] multiplyarray(double[] array,double[] value){
        for(int i=0;i<array.length;i++){
            array[i]*=value[i];
        }
        return array;
    }
    public double[] minus(double[] array,double[] value){
        for(int i=0;i<array.length;i++){
            array[i]-=value[i];
        }
        return array;
    }
    public double[] plus(double[] array,double value){
        for(int i=0;i<array.length;i++){
            array[i]+=value;
        }
        return array;
    }
    public double[] multiply(double[] array,double value){
        for(int i=0;i<array.length;i++){
            array[i]=array[i]*value;
        }
        return array;
    }
    public double[] getWaru(double[] array1,double[] array2){
        double[] array=new double[array1.length];
        for(int i=0;i<array1.length;i++){
            array[i]=array1[i]/array2[i];
        }
        return array;
    }
    public double[] getWaru(double[] array1,double value){
        double[] array=new double[array1.length];
        for(int i=0;i<array1.length;i++){
            array[i]=array1[i]/value;
        }
        return array;
    }
    public double[] Waru(double[] array1,double value){
        double[] c=new double[array1.length];
        for(int i=0;i<array1.length;i++){
            c[i]=array1[i]/value;
        }
        return c;
    }
    public double[] plus(double[] a,double[] b){
        double[] c=new double[a.length];
        for(int i=0;i<c.length;i++){
            c[i]=a[i]+b[i];
        }
        return c;
    }
    public double[] plus(double[][] a){
        double[] c=new double[a[0].length];
        for(int i=0;i<a.length;i++){
            c=this.plus(c, a[i]);
        }
        return c;
    }
    public double[] Divide(double[] a,double value){
        double[] c=new double[a.length];
        for(int i=0;i<c.length;i++){
            c[i]=a[i]/value;
        }
        return c;
    }
    public double[] uniteARRAY(double[] array1,double[] array2){
        int num=array1.length+array2.length;
        double[] ret=new double[num];
        for(int i=0;i<array1.length;i++){
            ret[i]=array1[i];
        }
        for(int i=0;i<array2.length;i++){
            ret[array1.length+i]=array2[i];
        }
        return ret;
    }
    public double[] arrangeArray(int length,double[] array){
        double[] a=new double[length];
        for(int i=0;i<length;i++){
            if(i>=array.length){
                a[i]=0;
            }else{
                a[i]=array[i];
            }
        }
        return a;
    }
}
