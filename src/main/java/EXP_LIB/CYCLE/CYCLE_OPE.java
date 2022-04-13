/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EXP_LIB.CYCLE;
import java.util.ArrayList;

/**
 *
 * @author araki keita
 */
public class CYCLE_OPE {
    public double[][] getMaxNunAndValue(double[] array){
        double[][] maxload;
        boolean sign;//符号正true符号負false
        double max=0;
        int maxnum=0;
        double threshold=0.4;//最大値の閾値。これより小さい最大値は捨てる。
        ArrayList<Integer> maxnumber=new ArrayList<Integer>();

        for(int i=0;i<array.length;i++){
            if(array[i]>0){
                if(max>0){
                    if(max<=array[i]){
                        maxnum=i;
                        max=array[i];
                    }
                    if(i==array.length-1){
                        if(maxnum>threshold){
                          maxnumber.add(maxnum);
                        }
                    }
                    continue;
                }
                if(max<0){
                    if(Math.abs(max)>threshold){
                        maxnumber.add(maxnum);
                    }
                    max=array[i];
                    maxnum=i;
                    continue;
                }
                else{
                    max=array[i];
                    maxnum=i;
                    continue;
                }
            }
            else if(array[i]<0){
                if(max<0){
                    if(max>=array[i]){
                        maxnum=i;
                        max=array[i];
                    }
                    if(i==array.length-1){
                        if(max<threshold){
                            maxnumber.add(maxnum);
                        }
                    }
                    continue;
                }
                if(max>0){
                    if(Math.abs(max)>threshold){
                        maxnumber.add(maxnum);
                    }
                    max=array[i];
                    maxnum=i;
                    continue;
                }
                else{
                    max=array[i];
                    maxnum=i;
                    continue;
                }
            }
            else{continue;}
        }
        maxload=new double[2][maxnumber.size()];
        for(int i=0;i<maxnumber.size();i++){
            maxload[0][i]=maxnumber.get(i);
            maxload[1][i]=array[maxnumber.get(i)];
        }
        return maxload;
    }
    public double[] getMaxValue(double[] array){
        double[] maxload=this.getMaxNunAndValue(array)[1];
        return maxload;
    }
}
