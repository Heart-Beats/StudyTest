package main;

import com.HL.RedGreenLight;

import HL.util.FileUtil;
import HL.util.MyArrayList;

/**
 * @author 淡然爱汝不离
 */
public class ProgramStart {

    public static void myArrayListStart() {
        MyArrayList myArrayList = new MyArrayList(2);
        myArrayList.add("张磊");
        myArrayList.add("张欢");
        myArrayList.add("我爱你");
        myArrayList.set(1, "很爱张欢");
        // myArrayList.remove("很爱张欢");
        myArrayList.remove("我爱你");
        for (int i = 0; i < myArrayList.getSize(); i++) {
            System.out.println(myArrayList.get(i));
        }
        System.out.println(myArrayList.indexOf("我爱你"));
        System.out.println(myArrayList.getSize());
    }

    public static void fileCopyStart() {
        //String srcPath = "C:/Users/HL_913305160/Desktop/第一行代码2/booksource-master/chapter14/CoolWeather";
        String srcPath = "C:/Users/HL_913305160/Desktop/付费音乐1";
        String targetPath = "C:/Users/HL_913305160/Desktop/付费音乐1";
        //FileUtil.copyToFile(srcPath,targetPath);
        try {
            FileUtil.copyToDir(srcPath, targetPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fileSplitStart(String srcPath, int blockSize) {
        try {
            FileUtil.fileSplit(srcPath, blockSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void fileMergeStart() {
        try {
            FileUtil.mergeSplitFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void redGreenLightStart() {
        RedGreenLight rgl = new RedGreenLight(5 * 1000);
        RedGreenLight.PasserBy passerBy = rgl.new PasserBy();
        RedGreenLight.Driver driver = rgl.new Driver();
        rgl.changeLight();
        new Thread(passerBy).start();
        new Thread(driver).start();
    }

    private static void writeObjectStart() {
        FileUtil.writeObject("C:/Users/HL_913305160/Desktop/1.txt");
    }

    public static void main(String[] args) {
        myArrayListStart();
        //fileCopyStart();
        //fileSplitStart("C:/Users/HL_913305160/Desktop/新建文本文档.txt",5);
        //fileMergeStart();
        // FileTree.printFileTree(new File("E:\\程序开发软件安装包"), 0);
        // redGreenLightStart();
        //writeObjectStart();
    }
}

