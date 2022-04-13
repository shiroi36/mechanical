/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.SA.Model.InputInfo;

/**
 *
 * @author araki keita
 */
public class LoadInfo {
    int LT;
    double LV;
    double LD;
    public LoadInfo(int LoadType,double LoadValue,double LoadDistance){
        LT=LoadType;
        LV=LoadValue;
        LD=LoadDistance;
    }
    public int getLoadType(){return LT;}
    public double getLoadValue(){return LV;}
    public double getLoadDistance(){return LD;}
}
