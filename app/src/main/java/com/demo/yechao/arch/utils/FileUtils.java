package com.demo.yechao.arch.utils;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzlihe on 2016/9/23.
 */
public class FileUtils {

    static Boolean flag;
    static File file;


    public static boolean deleteFile(String fPath) {
        flag = false;
        file = new File(fPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }


    public static boolean deleteFile(File file) {
        flag = false;
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    public static boolean isExist(String fPath) {
        flag = false;
        file = new File(fPath);
        // 路径为文件且不为空返回true
        if (file.isFile() && file.exists()) {
//            file.delete();
            flag = true;
        }
        return flag;
    }


    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    public static List<String> readAllFileInDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return null;
        }
        //读取目录下所有文件
        File[] files = dirFile.listFiles();
        List<String> fileList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                fileList.add(files[i].getAbsolutePath());
            }
        }
        return fileList;
    }


    public static boolean DeleteFolder(String sPath) {
        flag = false;
        file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }


    }


    public static byte[] File2byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static void writeFile(String pathName, String context) throws IOException {
        File filename = new File(pathName);
        filename.createNewFile();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, false)));
        out.write(context + "\n");
        out.flush();
        out.close();
    }

    public static void writeFile(File filename, String context) throws IOException {
        filename.createNewFile();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, false)));
        out.write(context + "\n");
        out.flush();
        out.close();
    }

    public static void writeFileByAppend(String pathName, String context) throws IOException {
        File filename = new File(pathName);
        filename.createNewFile();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, true)));
        out.write(context + "\n");
        out.flush();
        out.close();
    }

    public static void deleteLine(String pathName, String context) throws IOException {
        List<String> list = FileUtils.readFile(pathName);
        File filename = new File(pathName);
        int a = list.indexOf(context);
        System.out.println(a);
        list.remove(context);
        filename.createNewFile();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, false)));
        for (int i = 0; i < list.size(); i++) {
            out.write(list.get(i) + "\n");
            out.flush();
        }
        out.close();
    }


    @Test
    public void testDeleteLine() throws IOException {
        FileUtils.deleteLine("src/test/resources/read.txt", "1234,url");
    }


    public static List<String> readFile(String pathName) throws IOException {
        File filename = new File(pathName);
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
        List<String> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(reader);
        String line = br.readLine();
        while (line != null) {
            try {
                list.add(line);
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static List<String> readFile(File filename) throws IOException {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
        List<String> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(reader);
        String line = br.readLine();
        while (line != null) {
            try {
                list.add(line);
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    @Test
    public void test() throws IOException {
//        FileUtils.readFile("src/test/resources/read.txt");
//        FileUtils.writeFileByAppend("src/test/resources/readq.txt", "1234");
        List<String> list = FileUtils.readFile("src/test/resources/read.txt");
        System.out.println(JSON.toJSONString(list));

    }


}
