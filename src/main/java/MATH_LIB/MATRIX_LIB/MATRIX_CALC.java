/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MATH_LIB.MATRIX_LIB;
import EXP_LIB.ARRAY.ArrayOPE;

/**
 *ここでは配列のことを行列と表記する。縦方向が行で横方向が列である。
 * @author araki keita
 */
public class MATRIX_CALC extends ArrayOPE {
    /**
     * 二つの行列が同じ行と列を有しているかどうかをチェックする
     * @param array1　比べる行列
     * @param array2
     */
    public void equal(double[][] array1,double[][] array2){
        if(array1.length==array2.length){
            for(int i=0;i<array1.length;i++){
                if(array1[i].length!=array2[i].length){
                    System.out.println("NG!");
                }
            }
        }else{System.out.println("NG!");}
    }
    /**
     * n次元の単位行列を返すメソッド
     * @param n　単位行列の次元
     * @return
     */
    public double[][] getI(int n){
        double[][] I=new double[n][n];
        for(int i=0;i<n;i++){
            I[i][i]=1;
        }
        return I;
    }
    /**
     * 二つの行列を足し算するメソッド
     * @param m　足し合わせる行列
     * @param s　足し合わせる行列
     * @return
     */
    public double[][] plus(double[][] m,double[][] s){
        double[][] f=new double[m.length][];
        for(int i=0;i<m.length;i++){
            f[i]=new double[m[i].length];
            for(int t=0;t<m[i].length;t++){
                f[i][t]=m[i][t]+s[i][t];
            }
        }
        return f;
    }
    public void seikika(double[] vector){
        double a=vector[0];
        for(int i=0;i<vector.length;i++){
            vector[i]/=a;
        }
    }
    /**
     * ある行列にある行列を加える
     * @param a 加えられる行列
     * @param b　加える行列
     */
    public void add(double[][] a,double[][] b){
        for(int i=0;i<a.length;i++){
            for(int s=0;s<a[i].length;s++){
                a[i][s]+=b[i][s];
            }
        }
    }
    /**
     * 二つの行列を引くメソッド　m-s
     * @param m　引かれる行列
     * @param s　引く行列
     * @return
     */
    public double[][] minus(double[][] m,double[][] s){
        double[][] f=new double[m.length][];
        for(int i=0;i<m.length;i++){
            f[i]=new double[m[i].length];
            for(int t=0;t<m[i].length;t++){
                f[i][t]=m[i][t]-s[i][t];
            }
        }
        return f;
    }
    /**
     * 二つの行列をかけるメソッドa*b
     * @param a　かける行列
     * @param b　かける行列
     * @return　掛け合わせた行列
     */
    public double[][] multiply(double[][] a,double[][] b){
        int t1=a.length;
        int t2=b[0].length;
        int j=b.length;
        if(a[0].length!=j){
            System.out.println("MATRIX_CALC_multiply_impossible!");
            System.out.println(a[0].length+"  "+j);
        }
        double[][] c=new double[t1][t2];
        for(int i=0;i<t1;i++){
            for(int s=0;s<t2;s++){
                for(int t=0;t<j;t++){
                    c[i][s]+=a[i][t]*b[t][s];
                }
            }
        }
        return c;
    }
    /**
     * 行列と列をかけるメソッド
     * @param a　かける行列
     * @param b　かけるベクトル
     * @return　掛け合わせたベクトル
     */
    public double[] multiply(double[][] a,double[] b){
        int t1=a.length;
        int j=b.length;
        if(a[0].length!=j){
            System.out.println("impossible!");
        }
        double[] c=new double[t1];
        for(int i=0;i<t1;i++){
                for(int t=0;t<j;t++){
                    c[i]+=a[i][t]*b[t];
                }
        }
        return c;
    }
    public double[] multiply(double[] a,double[][] b){
        int t1=a.length;
        int j=b.length;
        if(b.length!=j){
            System.out.println("impossible!");
        }
        double[] c=new double[t1];
        for(int i=0;i<t1;i++){
                for(int t=0;t<j;t++){
                    c[i]+=a[t]*b[t][i];
                }
        }
        return c;
    }
    public double multiply(double[] a,double[] b){
        int t1=a.length;
        int j=b.length;
        if(a.length!=j){
            System.out.println("impossible!");
        }
        double c=0;
        for(int i=0;i<t1;i++){
            c+=a[i]*b[i];
        }
        return c;
    }
    public double taikakuka(double[] u,double[][] A){
        double c=0;
        c=this.multiply(this.multiply(u, A), u);
        return c;
    }
    /**
     * 行列を転置するメソッド
     * @param a　転置する行列
     * @return　転置した行列
     */
    public double[][] Transpose(double[][] a){
        double[][] b=new double[a[0].length][a.length];
        for(int i=0;i<a.length;i++){
            for(int s=0;s<a[0].length;s++){
                b[s][i]=a[i][s];
            }
        }
        return b;
    }
    /**
     * 逆行列を求めるメソッド
     * @param a　逆行列を求めたい行列nn
     * @return 逆行列nn
     */
    public double[][] getInverse(double[][] a){
        int n=a.length;
        double[][] I=this.getI(n);
        double[][] inv=this.LU_bunkai(a, I);
        return inv;
    }
    /**
     * LU分解により連立方程式を解くプログラム
     * @param modulus　解の行列にかかっている係数行列nn
     * @param b　Ａｘの行列
     * @return　解の行列
     */
    public double[][] LU_bunkai(double[][] modulus,double[][] b){
        int n=modulus.length;
        int m=b[0].length;
        double[][] x=new double[n][m];
        int[] ipiv=new int[n];
        double[] winv=new double[n];
        int ip, ipw, iw;
        double w;
        for(int k=0;k<n;k++){
        ipiv[k]=k;
        w=0.0;
        for(int i=0;i<n;i++){
        if(Math.abs(modulus[k][i])>w){
        w=Math.abs(modulus[k][i]);
        }
        winv[k]=1/w;
        }
        }
        for(int k=0;k<n;k++){
        w=0.0;
        ip=0;
        for(int i=k;i<n;i++){
            ipw=ipiv[i];
            if(Math.abs(modulus[ipw][k])*winv[ipw]>w){
                w=Math.abs(modulus[ipw][k])*winv[ipw];
                ip=i;
            }
        }////ピポットの選択終了///
        ipw=ipiv[ip];
        if(ip!=k){
            ipiv[ip]=ipiv[k];
            ipiv[k]=ipw;
        }///ipiv[]を入れ替えることで結果としてa[ipiv[]][]は行の入れ替えをしたこととなる。////

        double aipwk=1.0/modulus[ipw][k];
        double aipk;
        for(int i=k+1;i<n;i++){
            ip=ipiv[i];
            modulus[ip][k]*=aipwk;
            aipk=modulus[ip][k];
            for(int j=k+1;j<n;j++){
                modulus[ip][j]-=aipk*modulus[ipw][j];
            }
        }
        }///LU分解完了。a[][]は上三角＋下三角の線形結合の行列となっている。///

        for(int k=0;k<m;k++){
            for(int i=0;i<n;i++){
                ip=ipiv[i];
                w=b[ip][k];
                for(int j=0;j<i;j++){
                    w-=modulus[ip][j]*x[j][k];
                }
                x[i][k]=w;
            }///下三角の順次代入///
            for(int i=n-1;i>=0;i--){
                w=x[i][k];
                ip=ipiv[i];
                for(int j=i+1;j<n;j++){
                    w-=modulus[ip][j]*x[j][k];
                }
                x[i][k]=w/modulus[ip][i];
            }
        }
        return x;
    }
    /**
     * ガウス消去法により解を求めるメソッド
     * @param a　nn係数行列
     * @param b　Ａｘの行列
     * @return　解の行列
     */
    public double[] GAUSS_SOKYO_HOU(double[][] a,double[] b){
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
    /**
     * 行列式の値を計算するメソッド
     * @param a 行列式を求めるnnの行列
     * @return　行列式の値
     */
    public double DET(double[][] a){
        int n=a.length;
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
