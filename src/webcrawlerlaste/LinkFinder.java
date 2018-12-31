/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawlerlaste;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author G50
 */
public class LinkFinder extends Thread {

    private Node root;
    private int depth;

    public LinkFinder(Node root, int depth) {
        super();
        this.root = root;
        this.depth = depth;
        start();

    }

    public String get_HTML(String link) {
        try {
            String webPage = link;
            URL url = new URL(webPage);
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuffer sb = new StringBuffer();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            String result = sb.toString();
            return result;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return "";
    }

    @Override
    public void run() {
        if (this.depth < 0) {
            return;
        }
        if (this.root.getURL().contains(".rar") || this.root.getURL().contains(".png")
                || this.root.getURL().contains(".jpg")
                || this.root.getURL().contains(".zip") || this.root.getURL().contains(".gif")
                || this.root.getURL().contains(".mp4") || this.root.getURL().contains(".jpeg")) {
            Downloader dl = new Downloader(this.root.getURL(), this.root.getPath());
            Main.downloads.add(dl);
            return;
        }
        // in while check if downloadable or not add as child
        String input = get_HTML(this.root.getURL());
        String patternString = "\\s*(?i)(href|src)\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))";
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            try {
                String match = matcher.group();
                match = match.replace("href", "");
                match = match.replace("\"", "");
                match = match.replace("\'", "");
                match = match.replace("=", "");
                match = match.replace("src", "");
                if(Main.alllinks.contains(this.root.getURL())){
                    continue;
                }
                Main.alllinks.add(match);
                FileCreator fc = new FileCreator(this.root.getPath(), match);
                Node newnode = new Node(match, fc.getPath());
                newnode.setParent(this.root);
                this.root.addChild(newnode);
                Main.allnodes.add(newnode);
                System.out.println(match);
                LinkFinder lf = new LinkFinder(newnode, this.depth - 1);
                lf.join();
            } catch (Exception e) {
                continue;
            }
        }

    }
}
