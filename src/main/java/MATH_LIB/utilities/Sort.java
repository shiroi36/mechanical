/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MATH_LIB.utilities;

/**
 *
 * @author Araki Keita
 */
public class Sort {
    private double a;
    private double[] row;
    private boolean[] flag;
    public Sort(int num,boolean DECorASC){
        if(DECorASC==true){
            a=1;
        }else{
            a=-1;
        }
        row=new double[num];
        flag=new boolean[num];
    }
    public void input(double b){
        for(int i=0;i<row.length;i++){
            if(flag[i]==false){
                row[i]=a*b;
                flag[i]=true;
                break;
            }
            if((a*b)>row[i]){
                for(int s=row.length-1;s>i;s--){
                    row[s]=row[s-1];
                    flag[s]=flag[s-1];
                }
                row[i]=a*b;
                break;
            }
        }
    }
    public void input(double[] b){
        for(int i=0;i<b.length;i++){
            this.input(b[i]);
        }
    }
    public double[] output(){
        for(int i=0;i<row.length;i++){
            row[i]=row[i]*a;
        }
        return row;
    }

}
