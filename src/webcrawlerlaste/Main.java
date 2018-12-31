/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawlerlaste;

import com.sun.tracing.dtrace.ArgsAttributes;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author G50
 */
public class Main {

    protected static Tree tree;
    private static Node root;
    protected static ArrayList<Node> allnodes = new ArrayList<>();
    protected static ArrayList<String> alllinks = new ArrayList<>();
    protected static ArrayList<Downloader> downloads = new ArrayList<>();
    private static String url , path;
    
    public Main(String url , String path) throws IOException{
        this.url = url;
        this.path = path;
        String[] str = {};
        main(str);
    }
    
    public static void main(String[] args) throws IOException {
        System.setProperty("http.agent", "Chrome");
        int depth = 0;
        root = new Node(url, path);
        tree = new Tree(root);
        LinkFinder lf = new LinkFinder(root, depth);
        try {
            lf.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        //new CreatingTreeModel(path);
        System.out.println(allnodes.size());

    }

}
