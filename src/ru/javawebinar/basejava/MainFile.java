package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws Exception {
        File filePath = new File(".\\.gitignore");
        try {
            System.out.println(filePath.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File dir = new File(".\\src\\ru\\javawebinar\\basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }


        try(FileInputStream fileInputStream = new FileInputStream(filePath)) {
            System.out.println(fileInputStream.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        dir = new File("E:\\Java\\basejava");
        showFilesAndDirectories(dir);
    }

    public static void showFilesAndDirectories(File f) throws Exception  {
        if (!f.isDirectory ()) {
            System.out.println (f.getName ());
        }
        if (f.isDirectory ()) {
            try {
                System.out.println(f.getName());
                File[] child = f.listFiles();
                if (child != null) {
                    for (File file : child) {
                        showFilesAndDirectories(file);
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
