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
import javax.swing.JComponent;
import javax.swing.Action;

/**
 *
 * @author araki keita
 */
public class IPKeyEvent {

    ImagePanel panel;
    FILE_OPE fo;
    public IPKeyEvent(JInternalFrame frame,ImagePanel panel,FILE_OPE fo){
        InputMap imap=frame.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap amap=frame.getActionMap();
        this.panel=panel;
        this.fo=fo;
        Action[] keyevent={new UP(),new DOWN()};
        int[] keycode={KeyEvent.VK_UP,KeyEvent.VK_DOWN};
        for(int i=0;i<keyevent.length;i++){
            imap.put(KeyStroke.getKeyStroke(keycode[i], 0), keyevent[i]);
            amap.put(keyevent[i], keyevent[i]);
        }
    }
    class UP extends AbstractAction{
        public void actionPerformed(ActionEvent e){
            panel.setpass(fo.UP());
        }
    }
    class DOWN extends AbstractAction{
        public void actionPerformed(ActionEvent e){
            panel.setpass(fo.DOWN());
        }
    }
    
}
