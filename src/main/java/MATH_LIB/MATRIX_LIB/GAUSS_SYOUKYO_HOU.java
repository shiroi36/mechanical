/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MATH_LIB.MATRIX_LIB;

/**
 *100604MATRIX_CALCに統合
 * @author araki keita
 */
public class GAUSS_SYOUKYO_HOU {
    public double[] getARRAY(double[][] a,double[] b){
        int n=b.length;
        double[] x =new double[n];
        double w;
        int ip=0;
        ////////////////////ピポットの選択を行う/////////////////////////////////////
        for(int k=0;k<n;k++){
        w=0.0;
        for(int i=k;i<n;i++){
            if(Math.abs(a[i][k])>w){
            w=Math.abs(a[i][k]);
            ip=i;
            }
        }
        if(ip !=k){
            for(int j=k; j<n; j++){
            w=a[k][j];
            a[k][j]=a[ip][j];
            a[ip][j]=w;
            }
            w=b[k];
            b[k]=b[ip];
            b[ip]=w;
        }
        ///////////////全身消去開始//////////////////////////////////////////////////
        double akk= 1.0/a[k][k];
        double aik;
        for(int j=k+1; j<n; j++){
            a[k][j]=a[k][j]*akk;
        }
        b[k]=b[k]*akk;
        for(int i=k+1; i<n; i++){
            aik=a[i][k];
            for(int j=k+1; j<n; j++){
                a[i][j]-=aik*a[k][j];
            }
            b[i]-=aik*b[k];
        }
    }
        ////////////////////////交代代入の式の処理/////////////////////////////////////
        for(int i=n-1; i>=0; i--){
            for(int j=i+1; j<n; j++){
                b[i]-=a[i][j]*b[j];
            }
            x[i]=b[i];
        }
        return x;
    }
}
