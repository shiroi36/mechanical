/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package STATICS_LIB;
import GRAPH_LIB.XY.XYGRAPH;
import GRAPH_LIB.*;
import java.util.ArrayList;
import MATH_LIB.MATRIX_LIB.GAUSS_SYOUKYO_HOU;
/**
 *
 * @author kato
 */
public class GaussianFitting {
    double[][] Fdata;
    double[] modulus;
    double [] N_S_M;
    public GaussianFitting(double[][] data){
        ArrayList<Double> ED=new ArrayList<Double>();
        ArrayList<Double> ED2=new ArrayList<Double>();
        for(int i=0;i<data[1].length;i++){
            if(data[1][i]==0){
                continue;
            }
            ED.add(data[0][i]);
            ED2.add(data[1][i]);
        }
        Fdata=new double[2][ED.size()];
        for(int i=0;i<ED.size();i++){
            Fdata[0][i]=ED.get(i);
            Fdata[1][i]=Math.log(ED2.get(i));
//            System.out.println(data[1][i]);
        }
        double[] V={0,0,0,0};
        double[] y={0,0,0};
        for(int i=0;i<Fdata[0].length;i++){
//            System.out.println(Fdata[0][i]+"  "+Fdata[1][i]);
            V[0]+=Fdata[0][i];
            V[1]+=Math.pow(Fdata[0][i], 2);
            V[2]+=Math.pow(Fdata[0][i], 3);
            V[3]+=Math.pow(Fdata[0][i], 4);
            y[0]+=Fdata[1][i]*Math.pow(Fdata[0][i], 2);
            y[1]+=Fdata[1][i]*Fdata[0][i];
            y[2]+=Fdata[1][i];
        }
        double[][] a={{V[3],V[2],V[1]},{V[2],V[1],V[0]},{V[1],V[0],Fdata[0].length}};
        GAUSS_SYOUKYO_HOU gs=new GAUSS_SYOUKYO_HOU();
        modulus=gs.getARRAY(a, y);
//        System.out.println(modulus.length);
        double out[][]=new double[2][Fdata[0].length];
        out[0]=Fdata[0];
        for(int i=0;i<Fdata[0].length;i++){
            out[1][i]=modulus[0]*Math.pow(Fdata[0][i], 2)
                    +modulus[1]*Fdata[0][i]
                    +modulus[2];
        }

        XYGRAPH plt=new XYGRAPH();
//        plt.setXrange(4.5, 4.7);
//        plt.setValue("plot", out, false, true);
//        plt.setValue("plot2", Fdata,true,false);
//        new PLOT(plt);
        N_S_M=new double[3];
        N_S_M[0]=Math.exp(modulus[2]-Math.pow(modulus[1], 2)/(4*modulus[0]));
        N_S_M[1]=Math.sqrt(-1/(2*modulus[0]));
        N_S_M[2]=-modulus[1]/(2*modulus[0]);
//        System.out.println(N_S_M[0]+"  "+N_S_M[1]+"  "+N_S_M[2]);
    }
    public double[] getStaticValue(){return N_S_M;}
    public double getGaussianValue(double x){
        double value=N_S_M[0]*Math.exp(-Math.pow((x-N_S_M[2])/N_S_M[1], 2)/2);
        return value;
    }
    public double[][] addGFLine(double[][] data){
        double[][] out=new double[3][data[0].length];
        out[0]=data[0];
        out[1]=data[1];
        for(int i=0;i<out[0].length;i++){
            out[2][i]=this.getGaussianValue(data[0][i]);
        }
        return out;
    }
}
