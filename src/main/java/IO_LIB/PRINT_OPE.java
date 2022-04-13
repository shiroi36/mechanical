/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IO_LIB;

/**
 *
 * @author araki keita
 */
public class PRINT_OPE {
    public void PrintArray(double[][] array){
        for(int i=0;i<array[0].length;i++){
            for(int s=0;s<array.length;s++){
                System.out.print(array[s][i]+"\t");
            }
            System.out.println();
        }
    }
    public void PrintArray(int[][] array){
        for(int i=0;i<array[0].length;i++){
            for(int s=0;s<array.length;s++){
                System.out.print(array[s][i]+"\t");
            }
            System.out.println();
        }
    }
    public void PrintArray(double[] array){
        for(int i=0;i<array.length;i++){
            System.out.println(array[i]);
        }
    }
    public void PrintArray(int[] array){
        for(int i=0;i<array.length;i++){
            System.out.println(array[i]);
        }
    }
    public void PrintMatrix(double[][] a){
        for(int i=0;i<a.length;i++){
            for(int s=0;s<a[i].length;s++){
                System.out.print(a[i][s]+"\t");
            }
            System.out.println();
        }
    }
}
