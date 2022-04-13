/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MATH_LIB.ODE;

/**
 *ルンゲ・クッタ・ジル法により常微分方程式を解くプログラム。
 * このクラスは方程式を決定するクラスです。
 * @author araki keita
 */
public abstract class ODE_FUNCTION {

    /**
     * 常微分方程式は、まず一階連立上微分方程式にばらして、その式の数だけの初期値を
     * mainメソッドにて定義する。
     * @param t 独立変数の値。
     * @param y    y1,y2,y3……の独立変数tの時の値。
     * @return f    y1,y2,y3……を独立変数ｔで微分した値。
     */
    public abstract double[] function(double time, double[] y);
}
