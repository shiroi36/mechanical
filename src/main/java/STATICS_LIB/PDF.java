/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package STATICS_LIB;
/**
 *
 * @author kato
 */
public class PDF{
    double[] PDF;
    double[] x;
    int step;
    double dx;
    public PDF(double xmin,double xmax,double dx,function f){
        this.dx=dx;
        this.step=(int)((xmax-xmin)/dx+1);
        PDF=new double[step];
        x=new double[step];
        PDF[0]=0;
        x[0]=xmin;
        double norm=0;
        for(int i=1;i<step;i++){
            x[i]=xmin+dx*i;
            PDF[i]=PDF[i-1]+f.getValue(x[i])*dx;
            norm+=f.getValue(x[i])*dx;
        }
        for(int i=0;i<step;i++){
            PDF[i]/=norm;
        }
    }
    public double random(){
        double r=Math.random();
        double xd;
        int i;
        for(i=1;i<step-1;i++){
            if(r<PDF[i]){break;}
        }
        return (x[i-1]+dx*(r-PDF[i-1])/(PDF[i]-PDF[i-1]));
    }
    public double[] getx(){return x;}
    public double[] getPDF(){return PDF;}
}
