/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IO_LIB;
import java.util.ArrayList;
/**
 *
 * @author keita
 */
public class DoubleLogger {
    ArrayList<Double>[] list;
    int ln;
    public DoubleLogger(int listNumber){
        for(int i=0;i<listNumber;i++){
            list[i]=new ArrayList<Double>();
        }
        ln=listNumber;
    }
    public void add(int listNumberIndex,double value){
        list[listNumberIndex].add(value);
    }
    public void add(double[] value){
        for(int i=0;i<ln;i++){
            list[i].add(value[i]);
        }
    }
}
