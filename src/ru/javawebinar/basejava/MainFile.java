package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

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

        File file = new File("E:\\Java\\basejava\\src");
        File[] fileList = file.listFiles();
        RecursivePrint(fileList, 0, 0);
    }

    static void RecursivePrint(File[] arr, int index, int level) {
        if (index == arr.length) return;
        for (int i = 0; i < level; i++) {
            System.out.print(" ");
        }
        if (arr[index].isFile()) {
            System.out.println(arr[index].getName());
        } else if (arr[index].isDirectory()) {
            System.out.println(arr[index].getName());
            RecursivePrint(Objects.requireNonNull(arr[index].listFiles()), 0, level + 1);
        }
        RecursivePrint(arr, ++index, level);
    }
}
