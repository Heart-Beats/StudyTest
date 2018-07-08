package HL.util;

import java.io.File;
import java.util.Comparator;

public class CompareUtil<T> implements Comparator<T> {


    @Override
    public  int compare(T o1, T o2) {
        if (o1 instanceof File) {
            String filePath = ((File) o1).getAbsolutePath();
            String anoFilePath = ((File) o2).getAbsolutePath();
            return filePath.compareTo(anoFilePath);
        }
        return 0;
    }
}
