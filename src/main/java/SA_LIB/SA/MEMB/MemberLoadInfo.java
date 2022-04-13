/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.SA.MEMB;

/**
 *
 * @author araki keita
 */
public class MemberLoadInfo {
    int LT;//0集中荷重１分布荷重
    int member;
    double LV;
    double LD;
    public MemberLoadInfo(int member,int LoadType,double LoadValue,double LoadDistance){
        LT=LoadType;
        LV=LoadValue;
        LD=LoadDistance;
        this.member=member;
    }
    public MemberLoadInfo(int member,int LoadType,double LoadValue){
        LT=LoadType;
        LV=LoadValue;
        LD=0;
        this.member=member;
    }
    public int getLoadType(){return LT;}
    public double getLoadValue(){return LV;}
    public double getLoadDistance(){return LD;}
    public double gerLoadedMember(){return member;}
}
