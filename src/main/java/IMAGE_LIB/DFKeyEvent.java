/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IMAGE_LIB;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JComponent;
import javax.swing.Action;
import javax.swing.JFrame;

/**
 *
 * @author araki keita
 */
public class DFKeyEvent {
    DesktopFrame df;
    public DFKeyEvent(JFrame frame,DesktopFrame df){
        this.df=df;
        InputMap imap=frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap amap=frame.getRootPane().getActionMap();

        Action[] keyevent={new Arrange1(),new Arrange2(),new Arrange3(),new Arrange4(),new listvisible()};
        int[] keycode={KeyEvent.VK_1,KeyEvent.VK_2,KeyEvent.VK_3,KeyEvent.VK_4,KeyEvent.VK_L};
        for(int i=0;i<keyevent.length;i++){
            imap.put(KeyStroke.getKeyStroke(keycode[i], 0), keyevent[i]);
            amap.put(keyevent[i], keyevent[i]);
        }
    }
    class Arrange1 extends AbstractAction{
        public void actionPerformed(ActionEvent e){
            df.ArrangePanel(1);
            System.out.println("pressA");
        }
    }
    class Arrange2 extends AbstractAction{
        public void actionPerformed(ActionEvent e){
            df.ArrangePanel(2);
            System.out.println("pressA");
        }
    }
    class Arrange3 extends AbstractAction{
        public void actionPerformed(ActionEvent e){
            df.ArrangePanel(3);
            System.out.println("pressA");
        }
    }
    class Arrange4 extends AbstractAction{
        public void actionPerformed(ActionEvent e){
            df.ArrangePanel(4);
            System.out.println("pressA");
        }
    }
    class listvisible extends AbstractAction{
        public void actionPerformed(ActionEvent e){
            df.setGraphListVisible();
        }
    }
}
