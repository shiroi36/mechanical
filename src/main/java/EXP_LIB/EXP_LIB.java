/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EXP_LIB;
import java.util.ArrayList;
import MATH_LIB.MATRIX_LIB.MATRIX_CALC;
import EXP_LIB.FITTING.RegLine;
import IO_LIB.SQL_OPE;
import java.util.Arrays;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
/**
 *
 * @author keita
 */
public class EXP_LIB extends MATRIX_CALC{
    public static void main(String[] args) {
        EXP_LIB exp=new EXP_LIB();
//        System.out.println(Arrays.toString(exp.getTriAxis(10, 4, -4, 5, -2, -4)));;
        System.out.println(Arrays.toString(exp.getTriAxis(10, 10, 9.9, 0, 0, 0)));
    }
    public double[] getMeanArray(double[][] array){
        int num=array[0].length;
        int num2=array.length;
        for(int i=0;i<num2;i++){
            if(num>array[i].length){
                num=array[i].length;
            }
        }
        double[] a=new double[num];
        for(int i=0;i<num;i++){
            for(int s=0;s<num2;s++){
                a[i]+=array[s][i]/num2;
            }
        }
        return a;
    }
    public double[] getGravityPoint(double[][] array){
        int num2=array.length;
        double[] a=new double[num2];
        for(int i=0;i<num2;i++){
            for(int s=0;s<array[i].length;s++){
                a[i]+=array[i][s];
            }
            a[i]/=array[i].length;
        }
        return a;
    }
    public double[][] getTrueStrainAndStress(double[][] StrainStress){
        double[][] TSS=new double[2][StrainStress[0].length];
        for(int i=0;i<StrainStress[0].length;i++){
            TSS[1][i]=StrainStress[1][i]*(1+StrainStress[0][i]);
            TSS[0][i]=Math.log(1+StrainStress[0][i]);
//            System.out.println(TSS[0][i]+"   "+TSS[1][i]);
        }
        return TSS;
    }
    public double[] getYValueFromConstX(double XValue,double[][] XY,boolean OnlyFirstTrue){
        ArrayList<Double> ans=new ArrayList<Double>();
        boolean flag=false;
        for(int i=1;i<XY[0].length;i++){
            if((XY[0][i]>XValue)&&(XY[0][i-1]<=XValue)){
                double dx=XY[0][i]-XY[0][i-1];
                double dy=XY[1][i]-XY[1][i-1];
                double an=XY[1][i-1]+(XValue-XY[0][i-1])*dy/dx;
                ans.add(an);
                System.out.println("GET Y FROM constX "+XValue+" →"+an);
                if(OnlyFirstTrue==true){
                    flag=true;
                }
            }
            if((flag==true)&&(OnlyFirstTrue==true)){
                System.out.println("一番早いindexについて求めて終了");
                break;
            }
        }
        double[] b=new double[ans.size()];
        for(int i=0;i<ans.size();i++){
            b[i]=ans.get(i);
        }
        return b;
    }
    public double[] getXValueFromConstY(double YValue,double[][] XY,boolean OnlyFirstTrue){
        ArrayList<Double> ans=new ArrayList<Double>();
        boolean flag=false;
        for(int i=1;i<XY[0].length;i++){
            if(((XY[1][i]>YValue)&&(XY[1][i-1]<=YValue))
                    ||((XY[1][i]<YValue)&&(XY[1][i-1]>=YValue))){
                double dx=XY[0][i]-XY[0][i-1];
                double dy=XY[1][i]-XY[1][i-1];
                double an=XY[0][i-1]+(YValue-XY[1][i-1])*dx/dy;
                ans.add(an);
                System.out.println("GET X FROM constY "+YValue+" →"+an);
                if(OnlyFirstTrue==true){
                    flag=true;
                }
            }
            if((flag==true)&&(OnlyFirstTrue==true)){
                System.out.println("一番早いindexについて求めて終了");
                break;
            }
        }
        double[] b=new double[ans.size()];
        for(int i=0;i<ans.size();i++){
            b[i]=ans.get(i);
        }
        return b;
    }
    public double[][] getValuesFromConstValue(double[][] Value,double[] constArray,double ConstValue,boolean OnlyFirstTrue){
        ArrayList<double[]> ans=new ArrayList<double[]>();
        boolean flag=false;
        for(int i=1;i<constArray.length;i++){
            if(((constArray[i]>ConstValue)&&(constArray[i-1]<=ConstValue))||
                    ((constArray[i]<ConstValue)&&(constArray[i-1]>=ConstValue))){
                double dx=constArray[i]-constArray[i-1];
                double[] an=new double[Value.length];
//                System.out.print("GET VALUES FROM constValue of index "+" ; "+ConstValue+" →");
                for(int s=0;s<Value.length;s++){
                    double dy=Value[s][i]-Value[s][i-1];
                    an[s]=Value[s][i-1]+(ConstValue-constArray[i-1])*dy/dx;
                    System.out.print(an[s]+", ");
                }
                System.out.println();
                ans.add(an);
                if(OnlyFirstTrue==true){
                    flag=true;
                }
            }
            if((flag==true)&&(OnlyFirstTrue==true)){
                System.out.println("一番早いindexについて求めて終了");
                break;
            }
        }
        if(ans.isEmpty()){
            System.out.println("can't get from constarray...adapting maxvalue ");
            double max=0;
            int ind=0;
            for (int i = 0; i < constArray.length; i++) {
                double d = constArray[i];
                if(d>max){
                    max=d;
                    ind=i;
                }
            }
            double[] an=new double[Value.length];
            for (int i = 0; i < an.length; i++) {
                an[i]=Value[i][ind];
            }
            ans.add(an);
        }
        double[][] b=new double[Value.length][ans.size()];
        for(int i=0;i<ans.size();i++){
            b[i]=ans.get(i);
        }
        return b;
        
    }

    public double[][] getValuesFromConstValue(double[][] Value,int ConstIndex,double ConstValue,boolean OnlyFirstTrue){
        ArrayList<double[]> ans=new ArrayList<double[]>();
        boolean flag=false;
        for(int i=1;i<Value[ConstIndex].length;i++){
            if((Value[ConstIndex][i]>ConstValue)&&(Value[ConstIndex][i-1]<=ConstValue)){
                double dx=Value[ConstIndex][i]-Value[ConstIndex][i-1];
                double[] an=new double[Value.length];
                System.out.print("GET VALUES FROM constValue of index "+ConstIndex+" ; "+ConstValue+" →");
                for(int s=0;s<Value.length;s++){
                    double dy=Value[s][i]-Value[s][i-1];
                    an[s]=Value[s][i-1]+(ConstValue-Value[ConstIndex][i-1])*dy/dx;
                    System.out.print(an[s]+", ");
                }
                System.out.println();
                ans.add(an);
                if(OnlyFirstTrue==true){
                    flag=true;
                }
            }
            if((flag==true)&&(OnlyFirstTrue==true)){
                System.out.println("一番早いindexについて求めて終了");
                break;
            }
        }
        double[][] b=new double[Value.length][ans.size()];
        for(int i=0;i<ans.size();i++){
            b[i]=ans.get(i);
        }
        return b;
    }
    public double[] getSlidedSlipValues(double criterion,double[] removed){
        double[] ans=new double[removed.length];
        double b=0;
        ans[0]=removed[0];
        for(int i=1;i<removed.length;i++){
            if(Math.abs(removed[i]-removed[i-1])>criterion){
                b+=removed[i]-removed[i-1];
            }
            ans[i]=removed[i]-b;
        }
        return ans;
    }
    //戻り値　load-recno-dispの順
    public double[][] getRemovedSlipValues(double[] recno,double[] load,double[][] disp,int startind,int endind){
        ArrayList<double[]> out=new ArrayList<double[]>();
        boolean flag=false;
        double max=load[0];
        double[] b=new double[disp.length];
        double thre=10;
        for(int i=startind;i<endind;i++){
            if(load[i]>=max){
                max=load[i];
                if(flag){ 
                    flag=false;
                    System.out.println(i);
//                    b+=val[0][i]-val[0][i-1];
                    
                    double[] a=new double[disp.length+2];
                    a[0]=load[i];
                    a[1]=recno[i];
                    for(int j=0;j<b.length;j++){
                        a[j+2]=disp[j][i]-b[j];
                    }
                    out.add(a);
                }else{
                    double[] a=new double[disp.length+2];
                    a[0]=load[i];
                    a[1]=recno[i];
                    for(int j=0;j<b.length;j++){
                        a[j+2]=disp[j][i]-b[j];
                    }
                    out.add(a);
                }
            }else if(load[i]<max&&flag==false){
                if(Math.abs(load[i]-load[i-1])>thre){
                    flag=true;
                    for(int j=0;j<b.length;j++){
                        b[j]+=disp[j][i]-disp[j][i-1];
                    }
                    continue;
                }else{
                    double[] a=new double[disp.length+2];
                    a[0]=load[i];
                    a[1]=recno[i];
                    for(int j=0;j<b.length;j++){
                        a[j+2]=disp[j][i]-b[j];
                    }
                    out.add(a);
                    continue;
                }
            }else{
                for(int j=0;j<b.length;j++){
                    b[j]+=disp[j][i]-disp[j][i-1];
                }
                continue;
            }
        }
        for(int i=endind;i<load.length;i++){
            double[] a=new double[disp.length+2];
            a[0]=load[i];
            a[1]=recno[i];
            for(int j=0;j<b.length;j++){
                a[j+2]=disp[j][i]-b[j];
            }
            out.add(a);
        }
        double[][] ans=new double[disp.length+2][out.size()];
        for(int i=0;i<out.size();i++){
            for(int j=0;j<disp.length+2;j++){
                ans[j][i]=out.get(i)[j];
            }
        }
        return ans;
    }
    public double[][] getRemovedSlipValues(double[] load,double[][] disp,int startind,int endind){
        ArrayList<double[]> out=new ArrayList<double[]>();
        boolean flag=false;
        double max=load[0];
        double[] b=new double[disp.length];
        double thre=10;
        for(int i=startind;i<endind;i++){
            if(load[i]>=max){
                max=load[i];
                if(flag){ 
                    flag=false;
                    System.out.println(i);
//                    b+=val[0][i]-val[0][i-1];
                    
                    double[] a=new double[disp.length+1];
                    a[0]=load[i];
                    for(int j=0;j<b.length;j++){
                        a[j+1]=disp[j][i]-b[j];
                    }
                    out.add(a);
                }else{
                    double[] a=new double[disp.length+1];
                    a[0]=load[i];
                    for(int j=0;j<b.length;j++){
                        a[j+1]=disp[j][i]-b[j];
                    }
                    out.add(a);
                }
            }else if(load[i]<max&&flag==false){
                if(Math.abs(load[i]-load[i-1])>thre){
                    flag=true;
                    for(int j=0;j<b.length;j++){
                        b[j]+=disp[j][i]-disp[j][i-1];
                    }
                    continue;
                }else{
                    double[] a=new double[disp.length+1];
                    a[0]=load[i];
                    for(int j=0;j<b.length;j++){
                        a[j+1]=disp[j][i]-b[j];
                    }
                    out.add(a);
                    continue;
                }
            }else{
                for(int j=0;j<b.length;j++){
                    b[j]+=disp[j][i]-disp[j][i-1];
                }
                continue;
            }
        }
        for(int i=endind;i<load.length;i++){
            double[] a=new double[disp.length+1];
            a[0]=load[i];
            for(int j=0;j<disp.length;j++){
                a[j+1]=disp[j][i]-b[j];
            }
            out.add(a);
        }
        double[][] ans=new double[disp.length+1][out.size()];
        for(int i=0;i<out.size();i++){
            for(int j=0;j<disp.length+1;j++){
                ans[j][i]=out.get(i)[j];
            }
        }
        return ans;
    }
    public double[][] getSkeletenCurve(double[][] loaddispcurve,double[][] disp,int startind,int endind){
        ArrayList<double[]> out=new ArrayList<double[]>();
        boolean flag=false;
        double currentdisp=loaddispcurve[0][0];
        double currentload=loaddispcurve[1][0];
        double b0=0;
        double[] b=new double[disp.length];
        double thre=10;
        
        for(int i=startind;i<endind;i++){
            if(loaddispcurve[0][i]>=currentdisp){
                currentdisp=loaddispcurve[0][i];
                currentload=loaddispcurve[1][i];
                double[] a=new double[disp.length+2];
                a[0]=loaddispcurve[0][i]+b0;
                a[1]=loaddispcurve[1][i];
                for(int j=0;j<disp.length;j++){
                    a[j+2]=disp[j][i]+b[j];
                }
                out.add(a);
                continue;
            }else if(loaddispcurve[0][i]<currentdisp&&loaddispcurve[1][i]>=currentload){
                double[] c=out.get(out.size() - 1);
                b0 += loaddispcurve[0][i] - c[0];
                for (int j = 0; j < b.length; j++) {
                    b[j] += c[i + 2]-disp[j][i] ;
                }
                currentdisp = loaddispcurve[0][i];
                currentload = loaddispcurve[1][i];
                double[] a = new double[disp.length + 2];
                a[0] = loaddispcurve[0][i] + b0;
                a[1] = loaddispcurve[1][i];
                for (int j = 0; j < disp.length; j++) {
                    a[j + 2] = disp[j][i] + b[j];
                }
                out.add(a);
                continue;
            }
        }
        double[][] ans=new double[disp.length+2][out.size()];
        for(int i=0;i<out.size();i++){
            for(int j=0;j<disp.length+2;j++){
                ans[j][i]=out.get(i)[j];
            }
        }
        return ans;
    }
    public double[][] getRemovedSlipValues(double[][] val){
        return this.getRemovedSlipValues(val, 0, val[0].length);
    }
    public double[][] getRemovedSlipValues(double[][] val,int startind,int endind){
        ArrayList<double[]> out=new ArrayList<double[]>();
        boolean flag=false;
        double max=val[1][0];
        double b=0;
        double thre=10;
        for(int i=startind;i<endind;i++){
            if(val[1][i]>=max){
                max=val[1][i];
                if(flag){
                    flag=false;
                    System.out.println(i);
//                    b+=val[0][i]-val[0][i-1];
                    out.add(new double[]{val[0][i]-b,val[1][i]});
                }else{
                    out.add(new double[]{val[0][i]-b,val[1][i]});
                }
            }else if(val[1][i]<max&&flag==false){
                if(Math.abs(val[1][i]-val[1][i-1])>thre){
                    flag=true;
                    b+=val[0][i]-val[0][i-1];
                    continue;
                }else{
                    out.add(new double[]{val[0][i]-b,val[1][i]});
                    continue;
                }
            }else{
                b+=val[0][i]-val[0][i-1];
                continue;
            }
        }
        for(int i=endind;i<val[0].length;i++){
            out.add(new double[]{val[0][i]-b,val[1][i]});
        }
        double[][] ans=new double[2][out.size()];
        for(int i=0;i<out.size();i++){
            ans[0][i]=out.get(i)[0];
            ans[1][i]=out.get(i)[1];
        }
        return ans;
    }
    public double[] getFittingGradient(double[][] val,int start,int goal){
        int num=goal-start;
        double[][] val2=new double[val.length][num];
        for(int i=0;i<num;i++){
            for(int j=0;j<val.length;j++){
                val2[j][i]=val[j][i+start];
            }
        }
        RegLine rl=new RegLine();
        rl.setvalue(val2[0], val2[1]);
        return new double[]{rl.getGraditent(),rl.getIntercept()};
    }
    public double getTriAxis(double s1,double s2,double s3){
        if(s1==s2&&s2==s3){
//            System.out.println("zero");
            return 0;
        }
        double sigh=(s1+s2+s3)/3;
        double sigeq=Math.sqrt((Math.pow(s1-s2, 2)+Math.pow(s2-s3, 2)+Math.pow(s3-s1, 2))/2);
        return sigh/sigeq;
    }
    public double[] getTriAxis(double s11,double s22,double s33
             ,double s12,double s23,double s13){
        RealMatrix m=MatrixUtils.createRealMatrix(
                new double[][]{
                    {s11,s12,s13},
                    {s12,s22,s23},
                    {s13,s23,s33}
                });
        EigenDecomposition ed=new EigenDecomposition(m,1.0);
        double[] eigens=ed.getRealEigenvalues();
        double sigeq=Math.sqrt((Math.pow(eigens[0]-eigens[1], 2)
                +Math.pow(eigens[1]-eigens[2], 2)+Math.pow(eigens[2]-eigens[0], 2))/2);
        
        return new double[]{this.getTriAxis(eigens[0], eigens[1], eigens[2]),sigeq,eigens[0],eigens[1],eigens[2]};
    }
    //列名について，recno,disp,loadがあればオッケーで正側のみのスケルトンカーブをDBに保存する
    public void setSKRTNintoDB(SQL_OPE sql,String tbname){
        double[][] value=sql.getQueryData("select recno,disp,load from "+tbname+" order by recno");
        double[][] rv=new EXP_LIB().getRemovedSlipValues(value[0],value[2],new double[][]{value[1]},0,value[2].length);
        sql.executeUpdate("drop table "+tbname+"_skrtn if exists");
        sql.executeUpdate("create table "+tbname+"_skrtn (id int,recno int, disp double, load double)");
        sql.executeUpdate("create index on "+tbname+"_skrtn (recno)");
        for(int i=0;i<rv[0].length;i++){
            sql.executeUpdate("insert into "+tbname+"_skrtn(id,recno,disp,load)"
                    + " values("+i+","+rv[1][i]+","+rv[2][i]+","+rv[0][i]+")");
        }
    }
    //単なる直線をつくる
    public double[][] getLine(double gradient,double intersept,double[] y){
        double[][] ans=new double[2][y.length];
        for(int i=0;i<y.length;i++){
            ans[1][i]=y[i];
            ans[0][i]=(y[i]-intersept)/gradient;
        }
        return ans;
    }
    public double[][] getLine(double gradient,double intersept, double[] y,double dy){
        int num=(int)((y[1]-y[0])/dy);
        double[] yval=new double[num+1];
        for(int i=0;i<=num;i++){
            yval[i]=y[0]+dy*i;
        }
        return this.getLine(gradient, intersept, yval);
    }
    public double[][] getLine(double[] p1,double[] p2, double[] y){
        double gradient=(p1[1]-p2[1])/(p1[0]-p2[0]);
        double intersept=p1[1]-gradient*p1[0];
        return this.getLine(gradient, intersept, y);
    }
    public double[][] getLine(double[] p1,double[] p2, double[] y,double dy){
        int num=(int)((y[1]-y[0])/dy);
        double[] yval=new double[num+1];
        for(int i=0;i<=num;i++){
            yval[i]=y[0]+dy*i;
        }
        double gradient=(p1[1]-p2[1])/(p1[0]-p2[0]);
        double intersept=p1[1]-gradient*p1[0];
        return this.getLine(gradient, intersept, yval);
    }
    public double getSquareArea(double[][] xy){
        double[][] a=new double[2][3];
        double[][] b=new double[2][3];
        double[][] xy2=new double[2][4];
        for(int i=0;i<4;i++){
            xy2[0][i]=xy[0][i]-xy[0][0];
            xy2[1][i]=xy[1][i]-xy[1][0];
            switch (i){
                case 0:
                    a[0][0]=xy2[0][i];
                    a[1][0]=xy2[1][i];
                    b[0][0]=xy2[0][i];
                    b[1][0]=xy2[1][i];
                    break;
                case 1:
                    a[0][1]=xy2[0][i];
                    a[1][1]=xy2[1][i];
                    break;
                case 2:
                    a[0][2]=xy2[0][i];
                    a[1][2]=xy2[1][i];
                    b[0][2]=xy2[0][i];
                    b[1][2]=xy2[1][i];
                    break;
                case 3:
                    b[0][1]=xy2[0][i];
                    b[1][1]=xy2[1][i];
                    break;
            }
        }
        double s1=Math.abs((a[0][1]*a[1][2]-a[1][1]*a[0][2])/2);
        double s2=Math.abs((b[0][1]*b[1][2]-b[1][1]*b[0][2])/2);
        return s1+s2;
    }
    public double[][] getDispNoiseRemoved(double threshould,double[][] val){
        double[][] ans=new double[val.length][val[0].length];
        for(int i=1;i<val[0].length;i++){
            if(Math.abs(val[0][i]-ans[0][i-1])>threshould){
                ans[0][i]=ans[0][i-1];
//                ans[0][i]=0;
                ans[1][i]=val[1][i];
//                System.out.println(ans[0][i]+"\t"+ans[1][i]);
                continue;
            }
            ans[0][i]=val[0][i];
            ans[1][i]=val[1][i];
        }
        return ans;
    }
    public double[] getNormalizationValue(double[] val){
        double[] val2=new double[val.length];
        double ini=val[0];
        for (int i = 0; i < val2.length; i++) {
            val2[i]=val[i]-ini;
        }
        return val2;
    }
}
