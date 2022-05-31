package ru.meowland.discord.config;

import arc.files.Fi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ResourceUtil {
    public static void copy(String path, Fi to){
        try{
            final InputStream in = ResourceUtil.class.getClassLoader().getResourceAsStream(path);
            final OutputStream out = to.write();

            int data;
            while ((data = in.read()) != -1){
                out.write(data);
            }

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
