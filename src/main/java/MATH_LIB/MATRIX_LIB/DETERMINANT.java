/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MATH_LIB.MATRIX_LIB;

/**
 *100604MATRIX_CALCに統合
 * @author araki keita
 */
public class DETERMINANT {
    
    public double DET(int n,double[][] a){
    double det=1.0;
    int[] ipiv=new int[n];
    double[] winv=new double[n];
    int ip, ipw;
    double w;
    for(int k=0;k<n;k++){
        ipiv[k]=k;
        w=0.0;
        for(int i=0;i<n;i++){
            if(Math.abs(a[k][i])>w){
            w=Math.abs(a[k][i]);
        }
        winv[k]=1/w;
        }
     }
     for(int k=0;k<n;k++){
        w=0.0;
        ip=0;
        for(int i=k;i<n;i++){
            ipw=ipiv[i];
            if(Math.abs(a[ipw][k])*winv[ipw]>w){
                w=Math.abs(a[ipw][k])*winv[ipw];
                ip=i;
            }
        }////ピポットの選択終了///
        ipw=ipiv[ip];
        if(ip!=k){
            ipiv[ip]=ipiv[k];
            ipiv[k]=ipw;
            det=-det;
        }///ipiv[]を入れ替えることで結果としてa[ipiv[]][]は行の入れ替えをしたこととなる。////

        double aipwk=1.0/a[ipw][k];
        det/=aipwk;
        double aipk;
        for(int i=k+1;i<n;i++){
            ip=ipiv[i];
            a[ip][k]*=aipwk;
            aipk=a[ip][k];
            for(int j=k+1;j<n;j++){
                a[ip][j]-=aipk*a[ipw][j];
            }
        }
    }///LU分解完了。a[][]は上三角＋下三角の線形結合の行列となっている。///

    return det;


    }
}
