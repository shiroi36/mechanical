/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MATH_LIB.utilities;
import java.math.BigDecimal;
/**
 *
 * @author araki keita
 */
public class RoundHalfUp {
    public double RoundHalfUp(int digit,double value){
        BigDecimal bd=new BigDecimal(value);
        return bd.setScale(digit, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
