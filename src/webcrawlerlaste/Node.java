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
 * @author G50
 */
public class Node {
    
        private String url;
        private String path = null;
        private ArrayList<Node> children = new ArrayList<>();
        private Node parent = null;

        public Node(String url , String path) {
            this.url = url;
            this.path = path;
        }
        
        public void addChild(Node child) {
            child.setParent(this);
            this.children.add(child);
        }


        public ArrayList<Node> getChildren() {
            return children;
        }
        
        public String getPath(){
            return this.path;
        }

        public String getURL() {
            return this.url;
        }


        public void setParent(Node parent) {
            this.parent = parent;
        }

        public Node getParent() {
            return parent;
        }
        
        
        // we can add some methods to work with them
}
