/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.IO.INPUT;
import org.sourceforge.jlibeps.epsgraphics.EpsGraphics2D;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.awt.Color;
/**
 *
 * @author kato
 */
public class outputEPS {
    public outputEPS(String pass,InputData out){
        try{
            String epstitle="panel";
            OutputStream os=new FileOutputStream(pass);
            BufferedOutputStream bos=new BufferedOutputStream(os);
            int width=out.getPreferedSize().width;
            int height=out.getPreferedSize().height;
            EpsGraphics2D g=new EpsGraphics2D(epstitle,bos,0,0,width,height);
            Color bgColor=Color.white;
            Color frameColor=Color.black;
            Color zeroColor=Color.GREEN;
            g.setColor(bgColor);
            g.fillRect(0, 0, width,height);
            out.setFrameColor( frameColor, zeroColor);
            out.draw(g);
            g.setColor(frameColor);
            g.drawRect(0, 0, width-1, height-1);
            g.close();
            bos.close();
        }catch(IOException ioe){ioe.printStackTrace();}
    }
}
