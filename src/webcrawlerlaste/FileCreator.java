/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawlerlaste;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author G50
 */
public class FileCreator {

    private String filepath;

    public FileCreator(String rootpath, String url) {
        try {
            String name = url;
            if (url.contains("https://")) {
                name = url.replace("https://", "");
            }
            if (url.contains("http://")) {
                name = url.replace("http://", "");
            }
            name = name.replace("/", "-");
            name = name.replace(" ", "");
            name = name.replace("?", "");
            name = name.replace("*", "");
            name = name.replace("\\", "");
            name = name.replace("|", "");
            name = name.replace(":", "");
            name = name.replace("\"", "");
            name = name.replace("<", "");
            name = name.replace(">", "");
            this.filepath = rootpath + "/" + name;
            Files.createDirectories(Paths.get(this.filepath));
        } catch (Exception ex) {
            return;
        }
    }

    public String getPath() {
        return this.filepath;
    }

}
