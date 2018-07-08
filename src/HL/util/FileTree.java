package HL.util;

import java.io.File;

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
}
