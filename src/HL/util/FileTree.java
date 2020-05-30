package HL.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FileTree {
    /**
     *
     * @param file  输入一个目录，按层次打印出该目录下所有的文件和目录
     * @param level  默认为0即可，不建议修改
     */
    public static void printFileTree(File file, int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("-");
        }
        String fileName = file.getName();
        System.out.println(fileName);
        if (file.isDirectory()) {
            File files[] = file.listFiles();
            for (File subFile : files) {
                printFileTree(subFile, level+1);
            }
        }
    }

    List<File> fileList = new ArrayList<>();
    //获取指定目录中的所有文件
    List<File> traverseFiles(File file) {
        if (!file.exists()) {
            try {
                throw new FileNotFoundException("指定的目录不存在");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        for (File tempFile : file.listFiles()) {
            if (tempFile.isDirectory()) {
                traverseFiles(tempFile);
            } else {
                fileList.add(tempFile);
            }
        }
        return fileList;
    }
    public static void main(String[] args) {
        FileTree fileTree = new FileTree();
        String pathname = "E:/Java程序/StudyTest/src/HL";
        List<File> files= fileTree.traverseFiles(new File(pathname));
        System.out.println(files);
    }
}

