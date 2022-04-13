/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IMAGE_LIB;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.DefaultDesktopManager;
import javax.swing.JLayeredPane;
import java.util.Timer;
import java.util.TimerTask;

import java.util.ArrayList;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
/**
 *
 * @author Araki Keita
 */
public class DesktopFrame extends TimerTask implements ActionListener{
    public static void main(String[] args) {
        Timer t=new Timer();
        DesktopFrame df=new DesktopFrame();

        df.setImage("C:\\Users\\ななか"
                + "\\Documents\\NetBeansProjects\\test\\faceDetection.png", "aaa");
//        df.setLatestUpdatingImage("C:\\Users\\keita\\Pictures\\Eye-Fi-A\\2011-11-16","eye-fi-A");
//        df.setImageDirectory("C:\\Users\\keita\\Pictures\\Eye-Fi-A\\2011-11-16","eye-fi-A");
//        df.setLatestUpdatingImage("C:\\Users\\keita\\Pictures\\Eye-Fi-B\\2011-11-16","eye-fi-B");
//        df.setLatestUpdatingImage("C:\\Users\\keita\\Pictures\\Eye-Fi-C\\2011-11-16","eye-fi-C");
//        df.setLatestUpdatingImage("C:\\Users\\keita\\Pictures\\Eye-Fi-D\\2011-11-16","eye-fi-D");
//        this.setImage("IMG_1182.JPG","test");
//        t.schedule(df, 0,3000);
        System.out.println("START");
    }
    ArrayList<ImagePanel> im=new ArrayList<ImagePanel>();
    ArrayList<FILE_OPE> fo=new ArrayList<FILE_OPE>();
    JFrame f;
    private final JDesktopPane desktop;
    int iniw,inih;

    ArrayList<JInternalFrame> iframe;
    ArrayList<String> name;
    ArrayList<JToggleButton> button;
    JButton on,off;
    JInternalFrame list;
    boolean listflag;
    private boolean listvisible;
    public DesktopFrame(){
        this("main");
    }
    public DesktopFrame(String s){
        iniw=400;
        inih=400;
        listflag=false;
        f=new JFrame();
        desktop=new JDesktopPane();
        DFKeyEvent dFKeyEvent = new DFKeyEvent(f, this);
        desktop.setDesktopManager(new MyDesktopManager());
        desktop.setBackground(new Color(234,224,213));

        iframe=new ArrayList<JInternalFrame>();
        name=new ArrayList<String>();
        button=new ArrayList<JToggleButton>();
        on=new JButton("ALL-ON");
        off=new JButton("ALL-OFF");
        on.addActionListener(this);
        off.addActionListener(this);

        f.setTitle(s);
        f.getContentPane().add(desktop, BorderLayout.CENTER);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800,600);
        f.setLocation(10, 10);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.getContentPane().setBackground(Color.gray);
        f.setTitle("タイトル");
        f.setVisible(true);
        this.initList();
    }
    public final void setLatestUpdatingImage(String DirectoryPath,String title){
        JInternalFrame frame=this.InternalFrameTemplate("FRAME", 0, 0, iniw, inih);
        FILE_OPE file=new FILE_OPE(DirectoryPath);
        ImagePanel imagepanel=new ImagePanel(file.getLatestFilePath());
        frame.setBackground(new Color(34,53,70));
        frame.getContentPane().add(imagepanel);
        IPMouseLisner mouse=new IPMouseLisner(frame,imagepanel);
        frame.addMouseListener(mouse);
        frame.addMouseMotionListener(mouse);
        frame.addMouseWheelListener(mouse);
        frame.setTitle("LATEST_UPDATING→"+title);

        frame.setMaximizable(true);
        frame.setResizable(true);
        frame.setClosable(false);
        frame.setVisible(false);
        frame.setIconifiable(false);
        desktop.add(frame);
        this.iframe.add(frame);
        this.name.add("L_U→"+title);
        JToggleButton button=new JToggleButton("L_U→"+title);
        button.addActionListener(this);
        this.button.add(button);
        this.updateList();
        im.add(imagepanel);
        fo.add(file);
    }
    public final void setImageDirectory(String DirectoryPath,String title){
        JInternalFrame frame=this.InternalFrameTemplate("FRAME", 0, 0, iniw, inih);
        FILE_OPE file=new FILE_OPE(DirectoryPath);
        ImagePanel imagepanel=new ImagePanel(file.getLatestFilePath());
        frame.setBackground(new Color(34,53,70));
        frame.getContentPane().add(imagepanel);
        IPMouseLisner mouse=new IPMouseLisner(frame,imagepanel);
        frame.addMouseListener(mouse);
        frame.addMouseMotionListener(mouse);
        frame.addMouseWheelListener(mouse);
        IPKeyEvent iPKeyEvent = new IPKeyEvent(frame, imagepanel,file);
        frame.setTitle("IMAGE_DIRECTORY→"+title);

        frame.setMaximizable(true);
        frame.setResizable(true);
        frame.setClosable(false);
        frame.setVisible(false);
        frame.setIconifiable(false);
        desktop.add(frame);
        this.iframe.add(frame);
        this.name.add("L_U→"+title);
        JToggleButton button=new JToggleButton("I_D→"+title);
        button.addActionListener(this);
        this.button.add(button);
        this.updateList();
    }
    public final void setImage(String FilePath,String title){
        JInternalFrame frame=this.InternalFrameTemplate("FRAME", 0, 0, iniw, inih);
        ImagePanel imagepanel=new ImagePanel(FilePath);
        frame.setBackground(new Color(34,53,70));
        frame.getContentPane().add(imagepanel);
        IPMouseLisner mouse=new IPMouseLisner(frame,imagepanel);
        frame.addMouseListener(mouse);
        frame.addMouseMotionListener(mouse);
        frame.addMouseWheelListener(mouse);
        frame.setTitle("IMAGE→"+title);

        frame.setMaximizable(true);
        frame.setResizable(true);
        frame.setClosable(false);
        frame.setVisible(false);
        frame.setIconifiable(false);
        desktop.add(frame);
        this.iframe.add(frame);
        this.name.add("IMG→"+title);
        JToggleButton button=new JToggleButton("IMG→"+title);
        button.addActionListener(this);
        this.button.add(button);
        this.updateList();
    }
    public final void setImage(BufferedImage bi,String title){
        JInternalFrame frame=this.InternalFrameTemplate("FRAME", 0, 0, iniw, inih);
        ImagePanel imagepanel=new ImagePanel(bi);
        frame.setBackground(new Color(34,53,70));
        frame.getContentPane().add(imagepanel);
        IPMouseLisner mouse=new IPMouseLisner(frame,imagepanel);
        frame.addMouseListener(mouse);
        frame.addMouseMotionListener(mouse);
        frame.addMouseWheelListener(mouse);
        frame.setTitle("IMAGE→"+title);

        frame.setMaximizable(true);
        frame.setResizable(true);
        frame.setClosable(false);
        frame.setVisible(false);
        frame.setIconifiable(false);
        desktop.add(frame);
        this.iframe.add(frame);
        this.name.add("IMG→"+title);
        JToggleButton button=new JToggleButton("IMG→"+title);
        button.addActionListener(this);
        this.button.add(button);
        this.updateList();
    }
    public void run(){
        for(int i=0;i<im.size();i++){
            im.get(i).setpass(fo.get(i).getLatestFilePath());
        }
    }
    public JInternalFrame InternalFrameTemplate(String title,int x,int y,int w,int h){
        JInternalFrame f=new JInternalFrame(title);
        f.setMaximizable(true);
        f.setClosable(false);
        f.setIconifiable(true);
        f.setFocusable(true);
        f.setResizable(true);
        f.setBounds(x,y,w,h);
        f.setVisible(true);
        return f;
    }
    public final void initList(){
        int x;
        int y;
        if(listflag){
            x=list.getX();
            y=list.getY();
        }else{
            x=0;
            y=300;
        }
        list=new JInternalFrame();
        int row,col;
        row=(button.size()+2)/4;
        if((button.size()+2)%4>0){
            row++;
        }
        col=4;
        list.setSize(200*col, 30*(row+1));
        list.setLayout(new GridLayout(0,col));
        list.add(on);
        list.add(off);
        for(int i=0;i<button.size();i++){
            list.add(button.get(i));
        }
        list.setLocation(x,y);
        list.setTitle("写真リスト");
        list.setMaximizable(false);
        list.setResizable(false);
        list.setClosable(false);
        list.setIconifiable(true);
        list.setVisible(true);
        desktop.add(list, Integer.valueOf(JLayeredPane.MODAL_LAYER+1));
    }
    public void updateList(){
        desktop.remove(list);
        listflag=true;
        this.initList();
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==on){
            for(int i=0;i<button.size();i++){
                if(!iframe.get(i).isVisible()){
                    button.get(i).doClick();
                }
                this.ArrangePanel(2);
            }
            return;
        }
        if(e.getSource()==off){
            for(int i=0;i<button.size();i++){
                if(iframe.get(i).isVisible()){
                    button.get(i).doClick();
                    iframe.get(i).setVisible(false);
                }
            }
            return;
        }
        for(int i=0;i<button.size();i++){
            if(e.getSource()==button.get(i)){
                if(button.get(i).isSelected()){
                    iframe.get(i).setLocation(0, 0);
                    iframe.get(i).setVisible(true);
                }else{
                    iframe.get(i).setVisible(false);
                }
            }
        }
    }
    public void ArrangePanel(int row){
//        int row=2;
        ArrayList<JInternalFrame> arr=new ArrayList<JInternalFrame>();
        for(int i=0;i<iframe.size();i++){
            if(iframe.get(i).isVisible()){
                arr.add(iframe.get(i));
            }
        }
        int dw=desktop.getWidth();
        int dh=desktop.getHeight();
        int num=arr.size();
        int sh;
        if(num%row==0){
            sh=arr.size()/row;
        }else{
            sh=arr.size()/row+1;
        }
        for(int i=0;i<row;i++){
            for(int j=0;j<sh;j++){
                if(j+sh*i<arr.size()){
                    arr.get(j+sh*i).setLocation(j*(dw/sh), i*(dh/row));
                    arr.get(j+sh*i).setSize(dw/sh, dh/row);
                }
            }
        }
    }
    public void setGraphListVisible(){
        if(listvisible){
            listvisible=false;
        }else{
            listvisible=true;
        }
        list.setVisible(listvisible);
    }
    class MyDesktopManager extends DefaultDesktopManager{
        @Override
        public void endDraggingFrame(JComponent f) {
            if(!f.equals(list)){
                super.endDraggingFrame(f);
                int right=f.getX();
                int left=f.getX()+f.getWidth();
                if(right<0){
                    f.setLocation(0, 0);
                    f.setSize(desktop.getWidth()/2, desktop.getHeight());
                }else if(left>desktop.getWidth()){
                    f.setLocation(desktop.getWidth()/2, 0);
                    f.setSize(desktop.getWidth()/2, desktop.getHeight());
                }
            }
        }
        public void minimizeFrame(JInternalFrame f){
            f.setSize(iniw, inih);
        }
    }
}
