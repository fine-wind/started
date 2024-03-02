package com.example.common.v0.utils;

import com.example.common.v0.exception.ServerException;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件处理工具
 *
 * @author yqw
 */
@Log4j2
public class FileUtils {
    /**
     * 读取文件到字节数组
     *
     * @param filePath .
     * @return .
     * @throws IOException .
     */
    public byte[] getContent(String filePath) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        fi.close();
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        return buffer;
    }

    /**
     * 读取文件到字节数组 the traditional io way
     *
     * @param filename .
     * @return .
     * @throws IOException .
     */
    public static byte[] toByteArray(String filename) throws IOException {

        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length())) {
            BufferedInputStream in = null;
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            log.error(e);
            throw e;
        }
    }

    /**
     * 读取文件到字节数组 NIO way
     *
     * @param f .
     * @return .
     * @throws IOException .
     */
    public static byte[] toByteArray2(File f) throws IOException {

        if (!f.exists()) {
            throw new FileNotFoundException(f.getName());
        }

        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                assert channel != null;
                channel.close();
            } catch (IOException e) {
                log.error(e);
            }
            try {
                fs.close();
            } catch (IOException e) {
                log.error(e);
            }
        }
    }

    /**
     * 读取文件到字节数组 Mapped File way MappedByteBuffer*
     *
     * @param filename .
     * @return .
     * @throws IOException .
     */
    @SuppressWarnings("resource")
    public static byte[] toByteArray3(String filename) throws IOException {

        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(filename, "r").getChannel();
            MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size()).load();
            System.out.println(byteBuffer.isLoaded());
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                // System.out.println("remain");
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            log.error(e);
            throw e;
        } finally {
            try {
                assert fc != null;
                fc.close();
            } catch (IOException e) {
                log.error(e);
            }
        }
    }

    /**
     * 新建一个文件夹
     *
     * @param folderPath 路径
     * @return 是否成功
     */
    public static boolean createFolders(String folderPath) {
        String filePath;
        filePath = folderPath;
        File myFilePath = new File(filePath);
        try {
            // 如果该文件夹不存在，则生成新的空文件夹
            if (!myFilePath.exists()) {
                return myFilePath.mkdirs();
            }
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    /**
     * 新建一个文件
     *
     * @param filePath 文件路径
     * @return 是否成功
     */
    public static boolean createFile(String filePath) {
        File myFilePath = new File(filePath);
        try {
            // 如果该文件不存在，则生成新的空文件
            if (!myFilePath.exists()) {
                return myFilePath.createNewFile();
            }
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    /**
     * 遍历删除已存在文件夹
     *
     * @param folderPath .
     * @return 是否成功
     */
    public static boolean deleteFolder(String folderPath) {
        boolean trueflag = false;
        try {
            String filePath;
            filePath = folderPath;
            File myFilePath = new File(filePath);
            // 判断原文件是否存在
            if (myFilePath.exists()) {
                DeleteFile(myFilePath);
                trueflag = true;
            } else {
                trueflag = true;
            }
        } catch (Exception e) {
            log.error(e);
        }
        return trueflag;
    }

    /**
     * 删除文件
     *
     * @param file .
     */
    public static void DeleteFile(String file) {
        DeleteFile(new File(file));
    }

    /**
     * 删除文件或文件夹
     *
     * @param file .
     */
    public static void DeleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File file2 : files) {
                    DeleteFile(file2);
                }
                file.delete();
            }
        }
    }

    /**
     * 判断某文件或文件夹是否存在
     *
     * @param fileStr .
     * @return .
     */
    public static boolean FileExist(String fileStr) {
        try {
            File file = new File(fileStr);
            return file.exists() && file.isFile();
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    /**
     * 文件夹是否存在
     *
     * @param folderStr .
     * @return .
     */
    public static boolean FolderExist(String folderStr) {
        try {
            File file = new File(folderStr);
            return file.exists() && file.isDirectory();
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    /**
     * 根据条件枚举文件
     *
     * @param folder      .
     * @param pattern     .
     * @param isRecursive .
     * @return .
     */
    public static List<String> EnumFile(String folder, final String pattern, final boolean isRecursive) {
        final Stack<String> dirStack = new Stack<>();
        final List<String> fileList = new ArrayList<>();

        if (!FolderExist(folder))
            return fileList;

        dirStack.push(folder);
        while (!dirStack.isEmpty()) {
            String dirStr = dirStack.pop();
            File dir = new File(dirStr);
            dir.listFiles((dir1, name) -> {
                File findFile = new File(dir1, name);
                if (findFile.isDirectory() && isRecursive) {
                    dirStack.push(findFile.getAbsolutePath());
                    return false;
                }

                if (name.matches(pattern) && findFile.isFile()) {
                    fileList.add(findFile.getAbsolutePath());
                }

                return false;
            });
        }

        return fileList;
    }

    public static List<String> EnumFile(File folder, final String pattern, final boolean isRecursive) {
        return EnumFile(folder.getAbsolutePath(), pattern, isRecursive);
    }

    /**
     * copy File
     *
     * @param srcPath .
     * @param destPath .
     * @return .
     */
    public static boolean copyFile(String srcPath, String destPath) {
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        // 如果目标文件所在目录不存在，则创建一下
        createFolders(destFile.getParent());

        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(srcFile);
            os = new FileOutputStream(destFile);
            byte[] bt = new byte[1024];
            int count = 0;
            while ((count = is.read(bt)) != -1) {
                os.write(bt, 0, count);
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (Exception e) {
            log.error(e);
            return false;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    log.error(e);
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error(e);
                }
            }
        }
    }

    /**
     * 重命名文件
     *
     * @param from 源文件名
     * @param to   目标文件名
     */
    public static boolean rename(String from, String to) {
        File file = new File(from);
        return file.renameTo(new File(to));
    }

    public static List<String> loadTextInLineFromStream(InputStream stream, String encoding) {
        try {

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, encoding))) {
                List<String> lst = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    lst.add(line);
                }

                return lst;
            }
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * 逐行加载文本文件
     */
    public static List<String> loadTextInLineFromFile(File path, String encoding) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            return loadTextInLineFromStream(fileInputStream, encoding);
        } catch (Exception e) {
            log.error(e);
            return null;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }
    }

    /**
     * 加载二进制文件
     *
     * @param file 文件路径
     */
    public static byte[] LoadBinaryFile(File file) {
        BufferedInputStream in = null;

        try {
            in = new BufferedInputStream(new FileInputStream(file));
            byte[] buf = new byte[in.available()];

            in.read(buf);
            return buf;
        } catch (Exception e) {
            log.error(e);
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }
    }

    public static String LoadTextFromStream(InputStream stream, String encoding) {
        List<String> lines = loadTextInLineFromStream(stream, encoding);
        if (lines == null)
            return null;

        StringBuilder str = new StringBuilder();
        for (String line : lines) {
            str.append(line).append("\n");
        }
        return str.toString();
    }

    public static String LoadTextFile(File path, String encoding) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            return LoadTextFromStream(fileInputStream, encoding);
        } catch (Exception e) {
            log.error(e);
            return null;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }
    }

    /**
     * 将字符串输出到文件
     *
     * @param str      字符串
     * @param encoding 编码
     * @return 成功返回true，失败返回false
     */
    public static boolean writeToFile(File file, String str, String encoding) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
            writer.write(str);
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    /**
     * 保存二进制数据到文件
     *
     * @param destFile 文件路径
     * @param data     数据
     */
    public static boolean writeToFile(File destFile, byte[] data) {
        BufferedOutputStream out = null;

        try {
            out = new BufferedOutputStream(new FileOutputStream(destFile));
            out.write(data);

            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }
    }

    /**
     * 用于处理字符串的回调
     */
    public interface stringOperator {
        void One(int idx, String str);
    }

    /**
     * 枚举目录
     *
     * @param dir 目录路径
     * @param pat 过滤
     * @return .
     */
    public static List<String> enumFolder(String dir, final String pat) {
        final List<String> fileList = new ArrayList<>();

        if (!FolderExist(dir))
            return fileList;

        new File(dir).listFiles((dir1, name) -> {
            File findFile = new File(dir1, name);
            if (findFile.isDirectory()) {
                if (pat != null && !findFile.getName().matches(pat)) {
                    // 不符合要求的过滤掉
                    return false;
                }

                fileList.add(findFile.getAbsolutePath());
            }

            return false;
        });

        return fileList;
    }

    /**
     * 枚举目录
     *
     * @param dir 目录路径
     * @param pat 过滤
     * @return .
     */
    public static List<String> enumFile(String dir, final String pat) {
        final List<String> fileList = new ArrayList<>();

        if (!FolderExist(dir))
            return fileList;

        new File(dir).listFiles((dir1, name) -> {
            File findFile = new File(dir1, name);
            if (findFile.isFile()) {
                if (pat != null && !findFile.getName().matches(pat)) {
                    // 不符合要求的过滤掉
                    return false;
                }

                fileList.add(findFile.getAbsolutePath());
            }

            return false;
        });

        return fileList;
    }

    /**
     * 获取文件的后缀名
     */
    public static String getExtName(String name) {
        int pos = name.indexOf('.');
        if (pos == -1) {
            return "";
        } else {
            return name.substring(pos + 1, name.length());
        }
    }

    /**
     * 保存输入流中的数据到文件
     * save
     *
     * @param file   文件
     * @param stream 输入流
     */
    public static void writeStreamToFile(File file, InputStream stream) {
        // 如果父目录不存在，就先创建一个
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        byte[] buf = new byte[1024 * 1024];
        int funcRet = 0;

        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(file));

            while ((funcRet = stream.read(buf)) > 0) {
                out.write(buf, 0, funcRet);
            }
        } catch (IOException e) {
            log.error(e);
            throw new ServerException("文件io异常");
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }

    }

    /**
     * 判断文件夹是否有效 尝试创建一下即可
     */
    public static boolean isValidFolder(String folder) {
        // 如果是windows过滤一下windows保留字
        if (System.getProperty("os.name").matches(".*Window.*")) {
            if (winReserved.matcher(folder).find()) {
                return false;
            }
        }

        if (FileUtils.FolderExist(folder)) {
            return true;
        } else {
            File file = new File(folder);
            return file.mkdirs();
        }
    }

    /**
     * 当前目录
     */
    public static String wordDir() {
        return System.getProperty("user.dir").replace("\\", "/");
    }

    /**
     * 家目录
     */
    public static String homeDir() {
        return System.getProperty("user.home").replace("\\", "/");
    }

    /**
     * 根据常见的目录缩写进行展开
     *
     * @param input 原始目录
     * @return 展开后的目录
     */
    public static String expandPath(String input) {
        Matcher mat = pathExpandPat.matcher(input);

        if (!mat.matches()) {
            return new File(input).getAbsolutePath();
        }

        String prefix = mat.group(1);
        String sub = mat.group(2);

        switch (prefix) {
            case "~" -> {
                return CombinePath(homeDir(), sub);
            }
            case "." -> {
                return CombinePath(wordDir(), sub);
            }
            case ".." -> {
                // 可能存在../../../的情况，这里做一下处理
                Pattern pattern = Pattern.compile("(\\..)");
                Matcher matcher = pattern.matcher(sub);
                int count = 1;
                while (matcher.find()) {
                    count++;
                }
                String dir = wordDir();
                for (int i = 0; i < count; i++) {
                    String parent = new File(dir).getParent();
                    if (parent != null) {
                        dir = parent;
                    }
                }
                sub = sub.substring(sub.lastIndexOf("/"));
                if (dir.length() == 1 && dir.indexOf("/") == 0) { // linux环境下的根目录
                    return sub;
                }
                return CombinePath(dir, sub);
            }
            default -> {
                return new File(input).getAbsolutePath();
            }
        }
    }

    /**
     * 生成组合路径
     *
     * @param base 父目录
     * @param sub  子路径
     */
    public static String CombinePath(String base, String sub) {
        // 如果是根目录,直接合并
        if (base.equals("/")) {
            return base + sub;
        }
        return combineWithSplitter(base, sub, "/");
    }

    /**
     * 使用指定的分隔符合并两个字符串 分隔符会和第一个字符串末尾、第二个字符串的开头出现的对应字符进行合并
     *
     * @param one      第一个字符串
     * @param two      第二个字符串
     * @param splitter 分隔符
     * @return 合并后的字符串
     */
    public static String combineWithSplitter(String one, String two, String splitter) {
        StringBuilder finalStr = new StringBuilder();
        int splitterPos = one.lastIndexOf(splitter);
        if ((splitterPos != -1) && (splitterPos + splitter.length() == (one.length()))) {
            finalStr.append(one.substring(0, splitterPos));
        } else {
            finalStr.append(one);
        }

        if (!finalStr.isEmpty())
            finalStr.append(splitter);

        splitterPos = two.indexOf(splitter);
        if (splitterPos == 0) {
            finalStr.append(two.substring(splitterPos + splitter.length()));
        } else {
            finalStr.append(two);
        }

        return finalStr.toString();
    }

    /**
     * 判断一个目录是否是绝对路径 绝对目录：Unix/Linux下以/开头；windows下以盘符开头
     */
    public static boolean isAbsolutePath(String path) {
        Matcher mat = absPathPat.matcher(path);
        return mat.matches();
    }

    private static final Pattern absPathPat = Pattern.compile("^\\w:[/\\\\].+|^/.+");
    private static final Pattern winReserved = Pattern.compile("(^|[\\\\/])(aux|com1|com2|prn|con|nul)($|[\\\\/])");
    private static final Pattern pathExpandPat = Pattern.compile("^(~|\\.{1,2})(.+)");
}
