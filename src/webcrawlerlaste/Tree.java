/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawlerlaste;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pgr0101
 */
public class Tree {

    private Node root;
    private ArrayList<Node> allnodes;

    public Tree(Node root) {
        this.root = root;
        this.allnodes = null;

    }

    public String getPath() {
        return this.root.getPath();
    }

    public void visitAll(Node node) {
        try {
            this.allnodes.addAll(allnodes.size(), root.getChildren());
            this.allnodes.forEach((el) -> {
                visitAll(el);
            });
        } catch (Exception e) {
            return;
        }
    }
    /**
     * returning all nodes as a list
     * 
     */
    public ArrayList<Node> getAllNodes() {
        allnodes.add(root);
        visitAll(root);
        return allnodes;
    }
    
    public Node getRoot(){
        return this.root;
    }
}
