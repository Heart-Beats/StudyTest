package HL.util;

import java.io.*;
import java.nio.file.FileSystemException;
import java.util.*;

public class FileUtil {

    public static void copyToFile(String srcPathname, String targetPathname) {
        File src = new File(srcPathname);
        File target = new File(targetPathname);
        copyToFile(src, target);
    }

    public static void copyToFile(File srcFile, File targetFile) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(srcFile);
            os = new FileOutputStream(targetFile);
            byte[] temp = new byte[1024];
            int len;
            while (-1 != (len = is.read(temp))) {
                os.write(temp, 0, len);
            }
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("没有发现文件");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("写入文件失败");
        } finally {
            CloseStreamUtil.closeAllStream(is, os);
        }
    }

    public static void copyToDir(String srcPathname, String targetDirPathname) throws Exception {
        File src = new File(srcPathname);
        File targetDir = new File(targetDirPathname);
        copyToDir(src, targetDir);
    }

    public static void copyToDir(File srcFile, File targetDirFile) throws Exception {
        if (targetDirFile.getAbsolutePath().contains(srcFile.getAbsolutePath().subSequence(0, srcFile.getAbsolutePath().length()))) {
            throw new Exception("不可父文件夹复制到子文件夹");
        }
        if (!targetDirFile.exists()) {
            targetDirFile.mkdirs();
        } else if (!targetDirFile.isDirectory()) {
            throw new FileSystemException(targetDirFile.getName() + "不是文件夹");
        }
        if (srcFile.isFile()) {
            File targetFile = new File(targetDirFile, srcFile.getName());
            copyToFile(srcFile, targetFile);
        } else if (srcFile.isDirectory()) {
            copyToDirDetail(srcFile, targetDirFile);
        }
    }

    private static void copyToDirDetail(File childDir, File parentDir) {
        File parentFile = new File(parentDir, childDir.getName());
        parentFile.mkdirs();
        File[] files = childDir.listFiles();
        if (files != null) {
            for (File srcFile : files) {
                if (srcFile.isFile()) {
                    File subFile = new File(parentFile, srcFile.getName());
                    copyToFile(srcFile, subFile);
                    return;
                } else if (srcFile.isDirectory()) {
                    copyToDirDetail(srcFile, parentFile);
                }
            }
        }
    }

    /**
     * 文件分割准备工作，根据块数确定每块的大小
     *
     * @param srcPath   分割的文件路径
     * @param blockSize 分割的块数
     * @throws Exception 自定义的文件异常
     */
    public static void fileSplit(String srcPath, int blockSize) throws Exception {
        File srcFile = new File(srcPath);
        if (!srcFile.exists() || srcFile.isDirectory()) {
            throw new Exception("文件选择有误，请重新选择");
        }
        long srcFileLongth = srcFile.length();
        if (srcFileLongth < blockSize) {
            throw new Exception("文件太小，无需分割");
        }
        long blockLenth;
        long lastBlockLength;
        if (0 == srcFileLongth % blockSize) {
            blockLenth = srcFileLongth / blockSize;
            lastBlockLength = blockLenth;
        } else {
            blockLenth = srcFileLongth / blockSize;
            lastBlockLength = srcFileLongth - blockLenth * (blockSize - 1);
        }
        //计算出每块大小后，开始向每一块中拷贝数据
        fileSplitDetail(srcFile, blockLenth, lastBlockLength, blockSize);

    }

    /**
     * 确定要分割的每一块文件名称和需要复制的大小
     *
     * @param srcFile         分割的源文件
     * @param blockLength     每块的长度
     * @param lastBlockLength 最后一块的长度
     * @param blockSize       块数
     */
    private static void fileSplitDetail(File srcFile, long blockLength, long lastBlockLength, int blockSize) {
        Properties properties = new Properties();
        properties.setProperty("srcFile", srcFile.getAbsolutePath());
        String destParentPath = srcFile.getParent();
        //获得文件名和文件的后缀名
        String[] splitSrcFileName = srcFile.getName().split("\\.");
        String fileName = "";
        for (int i = 0; i < splitSrcFileName.length - 1; i++) {
            fileName += splitSrcFileName[i];
        }
        String fileType = splitSrcFileName[splitSrcFileName.length - 1];
        //给每一块确定名称和大小
        long alreadyCopyLenth = 0;
        for (int i = 1; i <= blockSize; i++) {
            File destFile = new File(destParentPath, fileName + "_" + i + "." + fileType);
            properties.setProperty("splitFile" + i, destFile.getAbsolutePath());
            long eachBlockLength = blockLength;
            if (blockSize == i) {
                eachBlockLength = lastBlockLength;
            }
            fileSplitCopy(srcFile, destFile, alreadyCopyLenth, eachBlockLength);
            alreadyCopyLenth += blockLength;
        }
        try {
            properties.store(new BufferedWriter(new FileWriter("src/HL/util/splitFileList")), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据给每一块分配的名称和大小复制文件
     *
     * @param srcFile           分割的源文件
     * @param destFile          分割后的目标文件
     * @param alreadyCopyLength 已经复制的长度
     * @param eachBlockLongth   每一块的长度
     */
    private static void fileSplitCopy(File srcFile, File destFile, long alreadyCopyLength, long eachBlockLongth) {
        RandomAccessFile raf = null;
        BufferedOutputStream bos = null;
        try {
            raf = new RandomAccessFile(srcFile, "r");
            bos = new BufferedOutputStream(new FileOutputStream(destFile));
            raf.seek(alreadyCopyLength);
            byte[] temp = new byte[1024];
            int len;
            long alreadyReadLength = 0;
            while (-1 != (len = raf.read(temp))) {
                //当写入的数据超过块长度确定最后一次复制的大小，结束复制
                if ((alreadyReadLength += len) > eachBlockLongth) {
                    bos.write(temp, 0, (int) (eachBlockLongth - alreadyReadLength + len));
                    break;
                }
                bos.write(temp, 0, len);
                bos.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseStreamUtil.closeAllStream(raf, bos);
        }
    }


    /**
     *  从配置文件中读取到的分割文件是乱序的，需要通过键值遍历出每个文件，再将这些文件加入列表排序，按顺序将读取到的文件合并
     * @throws IOException 文件合并中可能发生的异常
     */
    public static void mergeSplitFile() throws Exception {
        BufferedInputStream bis ;
        BufferedOutputStream bos;
        Properties properties = new Properties();
        properties.load(new BufferedReader(new FileReader("src/HL/util/splitFileList")));
        File srcFile = new File(properties.getProperty("srcFile"));
        File mergeFile;
        if (srcFile.exists()) {
            String[] splitSrcFileName = srcFile.getName().split("\\.");
            String mergeFileName = "";
            for (int i = 0; i < splitSrcFileName.length - 1; i++) {
                mergeFileName += splitSrcFileName[i];
            }
            String fileType = splitSrcFileName[splitSrcFileName.length - 1];
            mergeFile = new File(srcFile.getParent(), mergeFileName + "_副本." + fileType);
        } else {
            mergeFile = new File(srcFile.getAbsolutePath());
        }
        if (mergeFile.exists()) {
            return;
        }
        List<File> splitFilesList = new ArrayList<>();
        //获得properties文件所有的键值
        Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String fileKey = enumeration.nextElement();
            if (fileKey.equals("srcFile")) {
                continue;
            }
            File splitFile = new File(properties.getProperty(fileKey));
            if (!splitFile.exists()) {
                throw new Exception("分割后的文件缺失，无法完成合并！");
            }
            //将键值对应的文件加入splitFileList中
            splitFilesList.add(splitFile);
        }
        //把列表中的文件排序
        splitFilesList.sort(new CompareUtil<>());
        //读取排序后文件的数据，合并
        for (File splitFile : splitFilesList) {
            bis = new BufferedInputStream(new FileInputStream(splitFile));
            bos = new BufferedOutputStream(new FileOutputStream(mergeFile, true));
            byte[] temp = new byte[1024];
            int len;
            while (-1 != (len = bis.read(temp))) {
                bos.write(temp, 0, len);
                bos.flush();
            }
            CloseStreamUtil.closeAllStream(bis, bos);
            //删除文件必须要在关闭流之后，否则会失败
            splitFile.delete();
        }
    }

    public static void  writeObject(String filePath) {
        //String object = "中国";
        String[] object = {"1", "2","3","4"};
        try {
           // byte[] temp = new byte[1024];
/*            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(
                    byteArrayOutputStream));*/
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            //byte[] temp = byteArrayOutputStream.toByteArray();
/*            System.out.println(temp.length);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new BufferedOutputStream(new  FileOutputStream(filePath)),"utf-8" );
            outputStreamWriter.write(new String(temp));
            outputStreamWriter.flush();*/
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
