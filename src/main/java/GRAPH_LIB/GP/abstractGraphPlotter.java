/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GRAPH_LIB.GP;

import GRAPH_LIB.*;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

/**
 *
 * @author ななか
 */
public abstract class abstractGraphPlotter extends JFrame implements ActionListener{
    public abstract void ArrangePanel(int num);
    public abstract void screenshot(String path);
    public abstract void setGraphListVisible();
    public abstract JLabel getStatusLabel();
    public abstract void ApplyFrame(JInternalFrame internalframe);
}
