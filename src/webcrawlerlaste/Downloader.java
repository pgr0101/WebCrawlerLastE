/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawlerlaste;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import javax.swing.JProgressBar;

/**
 *
 * @author G50
 */
public class Downloader extends Thread {

    private JProgressBar jp;
    private String url;
    private String rootpath;
    private String name;
    private int size;
    private String cp;
    private boolean status = false;

    public Downloader(String url, String rootpath) {
        super();
        this.rootpath = rootpath;
        if (!url.contains("https") && url.contains("http")) {
            this.url = url.replace("http", "https");
        } else if (!url.contains("https") && !url.contains("http")) {
            this.url = "http:" + url;
        } else {
            this.url = url;
        }
        if (this.url.contains("https://")) {
            this.name = this.url.replace("https://", "");
        }
        if (this.url.contains("http://")) {
            this.name = this.url.replace("http://", "");
        }
        this.name = this.name.replace("/", "-");

    }

    @Override
    public void run() {
        try {
            // downlaod the files and save them
            download();
        } catch (Exception ex) {
            try {
                this.cp = this.rootpath + this.name;
                URL df = new URL(this.url);
                ReadableByteChannel rbc = Channels.newChannel(df.openStream());
                File file = new File(this.rootpath + this.name);
                FileOutputStream fos = new FileOutputStream(file);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                this.status = true;
            } catch (Exception ex1) {
            }
        }
    }

    public void download() throws Exception {
        URLConnection uc = URI.create(this.url).toURL().openConnection();
        uc.addRequestProperty("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        HttpURLConnection http = null;
        http = (HttpURLConnection) uc;
        double filesize = (double) http.getContentLength();
        BufferedInputStream in = new BufferedInputStream(http.getInputStream());
        FileOutputStream fos = new FileOutputStream(new File(this.rootpath + this.name));
        BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
        byte[] buffer = new byte[1024];
        double downloaded = 0.00;
        int read = 0;
        double percentDownloaded = 0.00;
        while ((read = in.read(buffer, 0, 1024)) >= 0) {
            bos.write(buffer, 0, read);
            downloaded += read;
            percentDownloaded += (downloaded * 100 / filesize);
            this.jp.setMaximum((int) filesize);
            this.jp.setValue((int) downloaded);
            this.jp.setString(percentDownloaded + "%");
        }
        this.status = true;
        bos.close();
        in.close();
    }

    public void startDownload(JProgressBar jp) {
        this.start();
        this.jp = jp;
    }

    public void openFile() throws IOException {
        Desktop desktop = Desktop.getDesktop();
        desktop.open(new File(this.cp));

    }

    public String getURL() {
        return this.url;
    }

    public double getFileSize() throws MalformedURLException {
        return this.getFileSize(new URL(this.url));
    }

    private int getFileSize(URL url) {
        URLConnection conn = null;
        try {
            conn = url.openConnection();
            if (conn instanceof HttpURLConnection) {
                ((HttpURLConnection) conn).setRequestMethod("HEAD");
            }
            conn.addRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            conn.getInputStream();
            return conn.getContentLength();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn instanceof HttpURLConnection) {
                ((HttpURLConnection) conn).disconnect();
            }
        }
    }

    public String getFileName() {
        return this.name;
    }

    public boolean getStatus() {
        return this.status;
    }
}
