/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IMAGE_LIB;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.Point;
import java.lang.String;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;



/**
 *
 * @author keita
 */
public class ImagePanel extends JPanel{
    BufferedImage im;
    int x,y;
    double scale;
    String path;
//    public static void main(String[] args) {
//        JFrame f=new JFrame();
//        f.add(new ImagePanel("C:\\Users\\ななか"
//                + "\\Documents\\NetBeansProjects\\test\\faceDetection.png"));
//        f.setTitle("イメージを拡大コピー");
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.setSize(400, 400);
//        f.setVisible(true);
//
//    }
    public ImagePanel(String pass) {
        this.path=pass;
        try{
        im=ImageIO.read(new File(pass));
        }catch(IOException ioe){ioe.printStackTrace();}
        x=0;
        y=0;
        scale=0.1;
    }
    public ImagePanel(BufferedImage bi){
        this.path="BUFFERED_IMAGE";
        im=bi;
        x=0;
        y=0;
    }
    public void setpass(String pass){
        this.path=pass;
        im=(BufferedImage)new ImageIcon(pass).getImage();
        this.repaint();
    }
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.drawImage(im, x,y,(int)(im.getWidth(this)*scale),(int)(im.getHeight(this)*scale), this);
        g.setColor(Color.white);
        g.drawString(path, 5, 15);
//        System.out.println(this.getWidth()+"    "+this.getHeight()+"     "+scale);
    }
    public void setLocale(int x,int y){
        this.x=x;
        this.y=y;
    }
    public void repaint(){
        this.removeAll();
        super.repaint();
    }
    public void setMag(double scale){
        this.scale=scale;
    }
    public double getMag(){
        return scale;
    }
    public void resetLocation(){
        x=0;
        y=0;
    }
    public Point getImageLocation(){
        return new Point(x,y);
    }
    public void setFitSize(){
        double Wmag=(double)this.getWidth()/im.getWidth(this);
        double Hmag=(double)this.getHeight()/im.getHeight(this);
        if(Wmag<=Hmag){
            scale=Wmag;
        }else{
            scale=Hmag;
        }
    }
}
