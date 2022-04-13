/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.SA.Model.MEMB;
import SA_LIB.SA.Model.InputInfo.SectionInfo;
import SA_LIB.SA.Model.InputInfo.EndInfo;
import SA_LIB.SA.Model.InputInfo.LoadInfo;
import MATH_LIB.MATRIX_LIB.MATRIX_CALC;
import java.util.ArrayList;

/**
 *0端→１端で統一している．
 * point[0] xaxis
 * point[1] yaxis
 * @author ArakiKeita
 */
public class StraightMember extends MATRIX_CALC{
    double[] L=new double[4];//L[0]全体の長さL[1]0端の剛域の長さL[2]変形を生じる部材長さ[3]1端の剛域の長さL
    double s,c,E,I,A,gamma;
    String end0,end1;
    ArrayList<LoadInfo> load=new ArrayList<LoadInfo>();

    public StraightMember(EndInfo info,SectionInfo cs){
        double[] p0=info.getPoint0();
        double[] p1=info.getPoint1();
        double[] rigid=info.getRigid();
        L[0]=Math.sqrt(Math.pow(p1[0]-p0[0], 2)+Math.pow(p1[1]-p0[1], 2));
        L[1]=rigid[0];
        L[2]=L[0]-(rigid[0]+rigid[1]);
        L[3]=rigid[1];
        s=(p1[1]-p0[1])/L[0];
        c=(p1[0]-p1[0])/L[0];
        E=cs.getE();
        I=cs.getI();
        A=cs.getA();
        gamma=cs.getGamma();
        end0=info.getCondition()[0];
        end1=info.getCondition()[1];
    }
    private double[][] C0(){
        double[][] C0=new double[6][];
        C0[0]=new double[]{-1,0,0};
        C0[1]=new double[]{0,-1,0};
        C0[2]=new double[]{0,-(L[0]-L[3]),-1};
        C0[3]=new double[]{1,0,0};
        C0[4]=new double[]{0,1,0};
        C0[5]=new double[]{0,-L[3],1};
        return C0;
    }
    private double[][] C(double sin,double cos){
        double[][] C=new double[6][];
        C[0]=new double[]{-cos,sin,0};
        C[1]=new double[]{-sin,-cos,0};
        C[2]=new double[]{0,-(L[0]-L[3]),-1};
        C[3]=new double[]{cos,-sin,0};
        C[4]=new double[]{sin,-cos,0};
        C[5]=new double[]{0,-L[3],1};
        return C;
    }
    public double[][] H(double L){
        double[][] H=super.getI(3);
        H[2][1]=L;
        return H;
    }
    public double[][] Hinv(double L){
        double[][] H=super.getI(3);
        H[2][1]=-L;
        return H;
    }
    private double[][] getK(double L){
        double[][] K=new double[3][3];
        if(end0.contains("p")==true){
            if(end1.contains("p")==true){K[0][0]=E*A/L;}
            else if(end1.contains("r")==true){
                K[0][0]=E*A/L;
                K[1][1]=3*I*E/((1+gamma/2)*Math.pow(L, 3));
                K[1][2]=-3*I*E/((1+gamma/2)*Math.pow(L, 2));
                K[2][1]=K[1][2];
                K[2][2]=3*I*E/((1+gamma/2)*L);
            }
            else{
                double k=Double.parseDouble(end1);
                K[0][0]=E*A/L;
                K[1][1]=12*E*I*k/(Math.pow(L, 3)*(2*(2+gamma)*k+3));
                K[1][2]=-6*E*I*k/(Math.pow(L, 2)*(k*(2+gamma)+1.5));
                K[2][1]=K[1][2];
                K[2][2]=12*E*I*k/(L*(2*(2+gamma)*k+3));
            }
        }
        else if(end0.contains("r")==true){
            if(end1.contains("p")==true){
                K[0][0]=E*A/L;
                K[1][1]=3*I*E/((1+gamma/2)*Math.pow(L, 3));
            }
            else if(end1.contains("r")==true){
                K[0][0]=E*A/L;
                K[1][1]=12*I*E/((1+gamma*2)*Math.pow(L, 3));
                K[1][2]=-6*I*E/((1+gamma*2)*Math.pow(L, 2));
                K[2][1]=K[1][2];
                K[2][2]=(1+gamma/2)*4*E*I/((1+2*gamma)*L);
            }
            else{
                double k1=Double.parseDouble(end1);
                double k=2*(1+2*gamma)*k1+2+gamma;
                K[0][0]=E*A/L;
                K[1][1]=6*E*I*(4*k1+1)/(Math.pow(L,3)*k);
                K[1][2]=-12*E*I*k1/(Math.pow(L,2)*k);
                K[2][1]=K[1][2];
                K[2][2]=8*E*I*k1*(1+0.5*gamma)/(L*k);
            }
        }
        else{
            double k0=Double.parseDouble(end0);
            if(end1.contains("p")==true){
                K[0][0]=E*A/L;
                K[1][1]=12*E*I*k0/(Math.pow(L, 3)*(2*(2+gamma)*k0+3));
            }
            else if(end1.contains("r")==true){
                double k=2*(1+2*gamma)*k0+2+gamma;
                K[0][0]=E*A/L;
                K[1][1]=6*E*I*(4*k0+1)/(Math.pow(L,3)*k);
                K[1][2]=-6*E*I*(2*k0+1)/(Math.pow(L,2)*k);
                K[2][1]=K[1][2];
                K[2][2]=2*E*I*(4*k0*(1+0.5*gamma)+3)/(L*k);
            }
            else{
                double k1=Double.parseDouble(end1);
                double k3=2*(k0+1)*(k1+1)-0.5+gamma*(4*k0*k1+k0+k1);
                K[0][0]=E*A/L;
                K[1][1]=12*E*I*(4*k0*k1+k0+k1)/(Math.pow(L, 3)*2*k3);
                K[1][2]=-6*E*I*(2*k0*k1+k1)/(Math.pow(L, 2)*k3);
                K[2][1]=K[1][2];
                K[2][2]=4*E*I*(4*k0*k1*(1+0.5*gamma)+3*k1)/(L*k3);
            }
        }
        return K;
    }
    public void setLoad(LoadInfo load){
        this.load.add(load);
    }
    public double[][] calcEqLoad(){
        double[][] p=new double[2][3];
        double[][] peq=new double[4][3];
        for(int i=0;i<load.size();i++){
            LoadInfo li=load.get(i);
            //0 集中荷重　1分布荷重
            if(li.getLoadType()==0){
                double dist=li.getLoadDistance();
                if(dist<L[1]){
                    double a=li.getLoadDistance();
                    double b=L[1]-a;
                    peq[0]=this.ConsentrateEqLoad(a, b, L[1], 0, li.getLoadValue())[0];
                    peq[1]=this.ConsentrateEqLoad(a, b, L[1], 0, li.getLoadValue())[1];
                }
                else if(L[1]<=dist&&dist<=(L[0]-L[3])){
                    double a=dist-L[1];
                    double b=L[2]-a;
                    peq[1]=this.ConsentrateEqLoad(a, b, L[2], 0, li.getLoadValue())[0];
                    peq[2]=this.ConsentrateEqLoad(a, b, L[2], 0, li.getLoadValue())[1];
                }
                else if((L[0]-L[3])<dist){
                    double a=dist-L[1]-L[2];
                    double b=L[3]-a;
                    peq[2]=this.ConsentrateEqLoad(a, b, L[3], 0, li.getLoadValue())[0];
                    peq[3]=this.ConsentrateEqLoad(a, b, L[3], 0, li.getLoadValue())[1];
                }
            }
            else if(li.getLoadType()==1){

            }
        }
        return p;
    }
    private double[][] ConsentrateEqLoad(double a,double b,double L,double gamma,double LoadValue){
        double[][] p=new double [2][3];
        p[0][0]=0;
        p[0][1]=(b*b*(3*a+b)/(Math.pow(L, 3))+2*b/L*gamma)/(1+2*gamma)*LoadValue;
        p[0][2]=a*b/L*(b/L+gamma)/(1+2*gamma)*LoadValue;
        p[1][0]=0;
        p[1][1]=(a*a*(a+3*b)/Math.pow(L, 3)+2*a/L*gamma)/(1+2*gamma)*LoadValue;
        p[1][2]=-a*b/L*(a/L+gamma)/(1+2*gamma)*LoadValue;
        return p;
    }
    private double[][] DistributeEqLoad(double L,double LoadValue){
        double[][] p=new double[2][3];
        p[0][0]=0;
        p[0][1]=LoadValue*L/2;
        p[0][2]=LoadValue*L*L/12;
        p[1][0]=0;
        p[1][1]=LoadValue*L/2;
        p[1][2]=-LoadValue*L*L/12;
        return p;
    }
}
