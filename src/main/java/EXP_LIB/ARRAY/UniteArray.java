/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EXP_LIB.ARRAY;
import java.util.ArrayList;

/**
 *
 * @author araki keita
 */
public class UniteArray {
    ArrayList<Double> value=new ArrayList<Double>();
    public void addARRAY(double[] array){
        for(int i=0;i<array.length;i++){
            value.add(array[i]);
        }
    }
    public double[] getDouble(){
        double array[]=new double[value.size()];
        for(int i=0;i<value.size();i++){
            array[i]=value.get(i);
        }
        return array;
    }
}
