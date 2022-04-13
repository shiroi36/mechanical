/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.IO.OUTPUT;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JInternalFrame;
import javax.swing.JComponent;
import javax.swing.Action;

/**
 *
 * @author araki keita
 */
public class frameFkeyevent {
    outputData out;
    panelF panel;
    public frameFkeyevent(JInternalFrame frame,panelF panel,outputData out){
        InputMap imap=frame.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap amap=frame.getActionMap();
        this.out=out;
        this.panel=panel;
        Action[] keyevent={new NodeFlag(),new NodeValueFlag(),
                            new AxisFlag(),new AxisValueFlag(),
                            new ShearFlag(),new ShearValueFlag(),
                            new MomentFlag(),new MomentValueFlag()};
        int[] keycode={KeyEvent.VK_Q,KeyEvent.VK_W,
                        KeyEvent.VK_Z,KeyEvent.VK_A,
                        KeyEvent.VK_X,KeyEvent.VK_S,
                        KeyEvent.VK_C,KeyEvent.VK_D};
        for(int i=0;i<keyevent.length;i++){
            imap.put(KeyStroke.getKeyStroke(keycode[i], 0), keyevent[i]);
            amap.put(keyevent[i], keyevent[i]);
        }
    }
    class NodeFlag extends AbstractAction{
        boolean flag;
        public NodeFlag(){
            flag=false;
        }
        public void actionPerformed(ActionEvent e){
            if(flag==false){flag=true;}
            else{flag=false;}
            out.setNodeFlag(flag);
            panel.repaint();
        }
    }
    class NodeValueFlag extends AbstractAction{
        boolean flag;
        public NodeValueFlag(){
            flag=false;
        }
        public void actionPerformed(ActionEvent e){
            if(flag==false){flag=true;}
            else{flag=false;}
            out.setNodeValueFlag(flag);
            panel.repaint();
        }
    }
    class AxisFlag extends AbstractAction{
        boolean flag;
        public AxisFlag(){
            flag=false;
        }
        public void actionPerformed(ActionEvent e){
            if(flag==false){flag=true;}
            else{flag=false;}
            out.setAxisFlag(flag);
            panel.repaint();
        }
    }
    class AxisValueFlag extends AbstractAction{
        boolean flag;
        public AxisValueFlag(){
            flag=false;
        }
        public void actionPerformed(ActionEvent e){
            if(flag==false){flag=true;}
            else{flag=false;}
            out.setAxisValueFlag(flag);
            panel.repaint();
        }
    }
    class ShearFlag extends AbstractAction{
        boolean flag;
        public ShearFlag(){
            flag=false;
        }
        public void actionPerformed(ActionEvent e){
            if(flag==false){flag=true;}
            else{flag=false;}
            out.setShearFlag(flag);
            panel.repaint();
        }
    }
    class ShearValueFlag extends AbstractAction{
        boolean flag;
        public ShearValueFlag(){
            flag=false;
        }
        public void actionPerformed(ActionEvent e){
            if(flag==false){flag=true;}
            else{flag=false;}
            out.setShearValueFlag(flag);
            panel.repaint();
        }
    }
    class MomentFlag extends AbstractAction{
        boolean flag;
        public MomentFlag(){
            flag=false;
        }
        public void actionPerformed(ActionEvent e){
            if(flag==false){flag=true;}
            else{flag=false;}
            out.setMomentFlag(flag);
            panel.repaint();
        }
    }
    class MomentValueFlag extends AbstractAction{
        boolean flag;
        public MomentValueFlag(){
            flag=false;
        }
        public void actionPerformed(ActionEvent e){
            if(flag==false){flag=true;}
            else{flag=false;}
            out.setMomentValueFlag(flag);
            panel.repaint();
        }
    }
}
