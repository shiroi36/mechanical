/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IMAGE_LIB;

import java.io.File;
import java.util.Arrays;

/**
 *
 * @author keita
 */
public class FILE_OPE {

    File[] files;
    int ind;
    private final File dir;
//    public static void main(String[] args) {
//        FILE_OPE file=new FILE_OPE("C:\\Users\\keita\\Pictures\\Eye-Fi-A\\2011-11-16");
//        System.out.println(file.getLatestFilePath());
////        System.out.println("nanakahara-mentabetaiyo!");
//    }

    public FILE_OPE(String path) {
        int ind = 0;
        dir = new File(path);
        files = dir.listFiles();
//        for (int i = 0; i < files.length; i++) {
//            File file = files[i];
//            System.out.println((i + 1) + ":    " + file.getName());
//            System.out.println((i + 1) + ":    " + file.getAbsolutePath());
//            System.out.println((i + 1) + ":    " + file.lastModified());
//        }
//    System.out.println("nanakadayo!");

    }

    public String UP() {
        ind++;
        if (ind == files.length) {
            ind = 0;
        }
        return files[ind].getAbsolutePath();
    }

    public String DOWN() {
        ind--;
        if (ind == -1) {
            ind = files.length - 1;
        }
        return files[ind].getAbsolutePath();
    }

    public String[] getAllAbsolutePath() {
        String[] path = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            path[i] = files[i].getAbsolutePath();
        }
        return path;
    }

    public String[] getAllFileName() {
        String[] path = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            path[i] = files[i].getName();
        }
        return path;
    }

    public String getLatestFilePath() {
        long max = 0;
        int maxind = 0;
        files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            long time = files[i].lastModified();
            if (time > max) {
                max = time;
                maxind = i;
            }
//            System.out.println("nanakakawaii");
        }
        ind = maxind;
        return files[maxind].getAbsolutePath();
    }

    public File[] SortedFiles() {
        FileWrapper[] fileWrappers = new FileWrapper[files.length];
        for (int i = 0; i < files.length; i++) {
            fileWrappers[i] = new FileWrapper(files[i]);
        }

        Arrays.sort(fileWrappers);

        File[] sortedFiles = new File[files.length];
        for (int i = 0; i < files.length; i++) {
            sortedFiles[i] = fileWrappers[i].getFile();
        }
        return sortedFiles;
    }

    class FileWrapper implements Comparable {

        /**
         * File
         */
        private File file;

        public FileWrapper(File file) {
            this.file = file;
        }

        public int compareTo(Object obj) {
            assert obj instanceof FileWrapper;

            FileWrapper castObj = (FileWrapper) obj;

            if (this.file.getName().compareTo(castObj.getFile().getName()) > 0) {
                return 1;
            } else if (this.file.getName().compareTo(castObj.getFile().getName()) < 0) {
                return -1;
            } else {
                return 0;
            }
        }

        public File getFile() {
            return this.file;
        }
    }
}
