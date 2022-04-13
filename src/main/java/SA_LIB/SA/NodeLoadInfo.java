/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.SA;

/**
 *
 * @author keita
 */
public class NodeLoadInfo {
    int node;
    double[] NQM;
    public NodeLoadInfo(int node,double[] NQM){
        this.node=node;
        this.NQM=NQM;
    }
    public int getNode(){return node;}
    public double[] getNQM(){return NQM;}
}
