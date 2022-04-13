/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.IO.INPUT.panel;

import java.awt.Color;
import javax.swing.JInternalFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import java.awt.GridLayout;
import java.awt.Font;

import SA_LIB.IO.INPUT.InputData;
/**
 *
 * @author keita
 */
public class DrawingFrameControlPanel extends JInternalFrame implements ActionListener,KeyListener{
    JButton[] b;
    Font font;
    JLabel[] l;
    JTextField[] field;
    JToggleButton[] tb;
    InputData in;
    DrawingFrame df;
    JPanel nodepane;
    JPanel ctrlpane;
    public DrawingFrameControlPanel(InputData in,DrawingFrame df){
        this.setTitle("DrawingFrameControlPanel");
        this.setMaximizable(true);
        this.setClosable(false);
        this.setIconifiable(true);
        this.setFocusable(true);
        this.setResizable(true);
        this.setBounds(0, 0, 400, 150);
        this.setBackground(new Color(34,53,70));
        this.setVisible(true);
        this.in=in;
        this.df=df;
        font=new Font("KonatuTohaba",0,12);
        b=new JButton[1];
        String[] label={"VERIFY"};
        String[] id={"node"};
        for(int i=0;i<b.length;i++){
            b[i]=new JButton(label[i]);
            b[i].setName(id[i]);
            b[i].setFont(font);
            b[i].addActionListener(this);
            b[i].addKeyListener(this);
        }
        l=new JLabel[3];
        String[] Lid={"NODE","X","Y"};
        for(int i=0;i<l.length;i++){
            l[i]=new JLabel(Lid[i]);
            l[i].setFont(font);
            l[i].setBackground(new Color(34,53,70));
            l[i].setForeground(Color.WHITE);
            l[i].setHorizontalAlignment(SwingConstants.CENTER);
        }
        field=new JTextField[2];
        String[] TFid={"nx","ny"};
        for(int i=0;i<field.length;i++){
            field[i]=new JTextField();
            field[i].setName(TFid[i]);
            field[i].setFont(font);
            field[i].addKeyListener(this);
        }
        tb=new JToggleButton[1];
        String[] tbid={"NODE"};
        for(int i=0;i<tb.length;i++){
            tb[i]=new JToggleButton();
            tb[i].setText(tbid[i]);
            tb[i].setFont(font);
            tb[i].addActionListener(this);
            tb[i].addKeyListener(this);
        }
        nodepane=new JPanel();
        nodepane.setBackground(new Color(34,53,70));
        nodepane.setLayout(new GridLayout(1,5));
        nodepane.add(l[1]);
        nodepane.add(field[0]);
        nodepane.add(l[2]);
        nodepane.add(field[1]);
        nodepane.add(b[0]);
        ctrlpane=new JPanel();
        ctrlpane.setBackground(new Color(34,53,70));
        ctrlpane.setLayout(new GridLayout(1,3));
        ctrlpane.add(tb[0]);
        ctrlpane.add(new JPanel());
        ctrlpane.add(new JPanel());
        this.setPanel();
    }
    public void setPanel(){
        GroupLayout layout=new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        /**
         * 垂直方向
         */
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                    .addComponent(ctrlpane))
                .addGroup(layout.createParallelGroup()
                    .addComponent(nodepane)));
        /**
         * 鉛直方向
         */
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                    .addComponent(ctrlpane)
                    .addComponent(nodepane)));
    }
    public void actionPerformed(ActionEvent e){
        Object o=e.getSource();
        if(o instanceof JButton){
            JButton button=(JButton)o;
            String name=button.getName();
            if(name.matches("node")){
                try{
                    double x=Double.parseDouble(field[0].getText());
                    double y=Double.parseDouble(field[1].getText());
                    in.addNode(x, y);
                    df.update();
                }catch(NumberFormatException nfe){
                    System.out.println("Please Type Number!!");
                }
                field[0].setText("");
                field[1].setText("");
            }
        }else if(o instanceof JToggleButton){
            JToggleButton toggle=(JToggleButton) o;
            if(toggle==tb[0]){
                if(tb[0].isSelected()){
                    System.out.println("toggle");
            }else{System.out.println("none");}
            }
        }
    }
    public void keyPressed(KeyEvent e){
        if(e.getModifiers() == KeyEvent.CTRL_MASK && e.getKeyCode() == KeyEvent.VK_R){
        }
    }
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(e.getSource() instanceof JButton){
                JButton b=(JButton)e.getSource();
                b.doClick();
            }
        }
    }
    public void keyTyped(KeyEvent e){}
}
