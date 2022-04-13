/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IO_LIB;

/**
 *
 * @author araki keita
 */
public class Pixel_mm {
    public static final double DISPLAY_DPI=96;//大概のディスプレイは96dpiだが、違うものもあるので確認すべし
    private final double mmPerInch=26.5;//1インチは25.4ミリメートル
    double DPI;
    public Pixel_mm(double DPI){
        this.DPI=DPI;
    }
    public int setmmtoPixel(double mm){
        double pixel=DPI/mmPerInch*mm;
//        if((pixel-(int)pixel)>=0.5){
//            pixel=1+(int)pixel;
//        }
        return (int)pixel;
    }
    public double setPixeltomm(int pixel){
        return pixel/DPI*mmPerInch;
    }
//    public static void main(String[] args) {
//        Pixel_mm dpimm=new Pixel_mm(Pixel_mm.DISPLAY_DPI);
//        System.out.println(dpimm.setDPItomm(1)+"  "+dpimm.setmmtoDPI(1));
//    }
}
