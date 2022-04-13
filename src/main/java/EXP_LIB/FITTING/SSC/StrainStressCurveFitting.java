/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EXP_LIB.FITTING.SSC;
import EXP_LIB.FITTING.RegLine;
import EXP_LIB.FITTING.RegLogLine;
import java.util.ArrayList;
import MATH_LIB.EQUATION_CALC.NIBUNHOU;
import java.text.DecimalFormat;
/**
 *
 * @author keita
 */
public class StrainStressCurveFitting {
    private ArrayList<double[]> K,Y,P,fit;
    private double stiff;

    public StrainStressCurveFitting(){
        K=new ArrayList<double[]>();
        Y=new ArrayList<double[]>();
        P=new ArrayList<double[]>();
        fit=new ArrayList<double[]>();
    }
    public void setValue(double[][] TSSC,int StartIndex,int KIndex,int YieldIndex,int BeginPlasticIndex){
        for(int i=0;i<TSSC[0].length;i++){
            if(i<=KIndex&&i>=StartIndex){
                K.add(new double[]{TSSC[0][i]-TSSC[0][StartIndex]
                        ,TSSC[1][i]-TSSC[1][StartIndex]});
            }else if((i>YieldIndex)&&(i<=BeginPlasticIndex)){
                Y.add(new double[]{TSSC[0][i],TSSC[1][i]});
            }else if(i>=BeginPlasticIndex){
//                System.out.println(TSSC[0][i]+"  "+TSSC[1][i]);
                P.add(new double[]{TSSC[0][i],TSSC[1][i]});
            }
        }
    }
    public void setValue(double[][] TSSC,int StartIndex,int KIndex,int YieldIndex,int BeginPlasticIndex,int end){
        System.out.println("BeginPlasticIndex = " + BeginPlasticIndex);
        System.out.println("end = " + end);
        
        for(int i=0;i<TSSC[0].length;i++){
            if(i<=KIndex&&i>=StartIndex){
                K.add(new double[]{TSSC[0][i]-TSSC[0][StartIndex]
                        ,TSSC[1][i]-TSSC[1][StartIndex]});
            }else if((i>YieldIndex)&&(i<=BeginPlasticIndex)){
                Y.add(new double[]{TSSC[0][i],TSSC[1][i]});
            }else if(i>=BeginPlasticIndex && i<end){
                System.out.println(i+"\t"+TSSC[0][i]+"  "+TSSC[1][i]);
                P.add(new double[]{TSSC[0][i],TSSC[1][i]});
            }
        }
    }
    public void Fitting(){
        RegLine kr=new RegLine();
        double ym=0;
        RegLogLine pr=new RegLogLine();
        double[][] k=new double[2][K.size()];
        for(int i=0;i<K.size();i++){
            k[0][i]=K.get(i)[0];
            k[1][i]=K.get(i)[1];
        }
        for(int i=0;i<Y.size();i++){
            ym+=Y.get(i)[1]/Y.size();
        }
        kr.setvalue(k[0], k[1]);
        double[] a=new double[3];
        double[] b=new double[3];
        stiff=kr.getGraditent();
        a[0]=kr.getGraditent();
        a[1]=0;
        System.out.println(kr.getGraditent());
        b[0]=kr.getIntercept();
        b[1]=ym;
        
        double[][] p=new double[2][P.size()];
        for(int i=0;i<P.size();i++){
            p[0][i]=P.get(i)[0];
            p[1][i]=P.get(i)[1];
        }
        pr.setvalue(p[0],p[1]);
        a[2]=pr.getGraditent();
        b[2]=pr.getIntercept();
        
        fit.add(new double[]{-b[0]/a[0],0});
        double w=(b[1]-b[0])/(a[0]-a[1]);
////        System.out.println(w);
        w=(a[0]*b[1]-a[1]*b[0])/(a[0]-a[1]);
//        System.out.println(w);
//        System.out.println(K.size()+"  "+Y.size()+" "+P.size());
        fit.add(new double[]{(b[1]-b[0])/(a[0]-a[1]),(a[0]*b[1]-a[1]*b[0])/(a[0]-a[1])});
        SSCFunction f=new SSCFunction(a[1],a[2],b[1],b[2]);
        NIBUNHOU nib=new NIBUNHOU();
//        System.out.println(a[1]+" "+a[2]+" "+b[1]+" "+b[2]);
        double x=nib.getnumber(0.2, 0.0001, f, 0.0001);
        
        double[][] pline=pr.getLine(x, 0.2, 0.01);
        for(int i=0;i<pline[0].length;i++){
            fit.add(new double[]{pline[0][i],pline[1][i]});
        }
    }
    public double[][] getSSCFitting(){
        double[][] ans=new double[2][fit.size()];
        System.out.println("\nアバカス入力用材料特性\n");
        DecimalFormat df1 = new DecimalFormat("0.000");
        DecimalFormat df2 = new DecimalFormat("0.0");
        for(int i=0;i<fit.size();i++){
            ans[0][i]=fit.get(i)[0];
            ans[1][i]=fit.get(i)[1];
//            System.out.println(ans[0][i]+"  "+ans[1][i]);
            if(i>=1){
                System.out.println("("+df2.format(ans[1][i])+","
                        +df1.format((ans[0][i]-ans[0][1]))+"),");
            }
        }
        return ans;
    }
    public double[][] getSSCFittingPlastic(){
        double[][] ans=new double[2][fit.size()-1];
//        System.out.println("\nアバカス入力用材料特性\n");
        double[] a=fit.get(1);
        for(int i=1;i<fit.size();i++){
            ans[0][i-1]=fit.get(i)[0]-a[0];
            ans[1][i-1]=fit.get(i)[1];
//            System.out.println(ans[0][i]+"  "+ans[1][i]);
            if(i>=1){
//                System.out.println(ans[1][i-1]+","+ans[0][i-1]);
            }
        }
        return ans;
    }
    public double getStiff(){return stiff;}
}
