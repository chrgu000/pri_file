package com.start.situ.tool;

import groovy.util.logging.Slf4j;
import org.htmlparser.filters.StringFilter;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.BitSet;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * Created by Administrator on 2018/3/15.
 */
@Slf4j
public class ConvertFileUtil {
    private static final int BYTE_SIZE = 8;
    public static String CODE_UTF8 = "UTF-8";
    public static String CODE_UTF8_BOM = "UTF-8_BOM";
    public static String CODE_GBK = "GBK";
    public static String sourceFileRoot = "D:\\CodeCreate\\spring_boot\\0315"; // 将要转换文件所在的根目录
    public static void main(String[] args) throws IOException {
        String file11="D:\\CodeCreate\\spring_boot\\ub_measurementdeclaration\\ub_measurementdeclarationDao.java";
        String ret=  "";
//        ret=    getTxtEncode(file11);
        System.out.println("Total Dealed : " + ret);
        getFile(sourceFileRoot,3);
//        File file=new File("D:\\CodeCreate\\spring_boot\\ub_measurementdeclaration\\Ub_measurementdeclarationController.java");

//
//
//        StringBuffer stringBuffer=new StringBuffer();
//        FileInputStream br=  new FileInputStream(file);
//        try {
//            String ret=  getTxtEncode(br);
//         String ret=   getEncode(br,false);
//            System.out.println("Total Dealed : " + ret);
//        }catch (Exception e){
//
//        }
//        br.close();
//        getFile(sourceFileRoot,1);

    }
    /*
    * 函数名：getFile
    * 作用：使用递归，输出指定文件夹内的所有文件
    * 参数：path：文件夹路径   deep：表示文件的层次深度，控制前置空格的个数
    * 前置空格缩进，显示文件层次结构
    */
    private static void getFile(String path,int deep){
        // 获得指定文件对象
        File file = new File(path);
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();

        for(int i=0;i<array.length;i++)
        {
            if(array[i].isFile())//如果是文件
            {
                for (int j = 0; j < deep; j++)//输出前置空格
                    System.out.print(" ");
                // 只输出文件名字
//                System.out.println( array[i].getName());
                try {
                    String fileFUll=path+"\\"+array[i].getName();
                        String ret = getTxtEncode(fileFUll);
                        System.out.println(fileFUll+"----" + ret);
                    if (ret.equals("BOM"))
                    {
                        editUtf8Not_BOM(fileFUll);
//                        System.out.printf(ret);
                    }else
                    {
                        System.out.printf(ret);
                    }
                }catch (Exception e)
                {
                    e.fillInStackTrace();
                }
                // 输出当前文件的完整路径
                // System.out.println("#####" + array[i]);
                // 同样输出当前文件的完整路径   大家可以去掉注释 测试一下
                // System.out.println(array[i].getPath());
            }
            else if(array[i].isDirectory())//如果是文件夹
            {
                for (int j = 0; j < deep; j++)//输出前置空格
                    System.out.print(" ");

//                System.out.println( array[i].getName());
                //System.out.println(array[i].getPath());
                //文件夹需要调用递归 ，深度+1
                getFile(array[i].getPath(),deep+1);
            }
        }
    }

    public static void editUtf8Not_BOM(String path) throws IOException {
        File file=new File(path);
        StringBuffer stringBuffer=new StringBuffer();
        BufferedReader br=null;
        br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
        int i=0;
        String str="";
        while((str=br.readLine())!=null)
        {
            if(i==0)//读取第一行，将前三个字节去掉，重新new个String对象
            {
                byte[] bytes=str.getBytes("UTF-8");
                str=new String(bytes,3,bytes.length-3);
                stringBuffer.append(str);
                i++;
            }else
            {
                stringBuffer.append(br.readLine()).append("\r\n");
            }
        }
        br.close();
//        System.out.printf(stringBuffer.toString());
        BufferedWriter bw=null;
        bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
        bw.write(stringBuffer.toString());
        bw.close();
    }

    public static String getTxtEncode(String path) throws IOException{
        FileInputStream in =new FileInputStream(path);
        byte[] head = new byte[3];
        in.read(head);
        String code = "NOT";
        if(head[0]==-17 && head[1]==-69 && head[2] ==-65)
            code = "BOM";
        return code;
    }
    /**
     * 通过文件缓存流获取编码集名称，文件流必须为未曾
     *
     * @param bis
     * @return
     * @throws Exception
     */
    public static String getEncode( BufferedInputStream bis, boolean ignoreBom) throws Exception {
        bis.mark(0);

        String encodeType = "";
        byte[] head = new byte[3];
        bis.read(head);
        if (head[0] == -1 && head[1] == -2) {
            encodeType = "UTF-16";
        } else if (head[0] == -2 && head[1] == -1) {
            encodeType = "Unicode";
        } else if (head[0] == -17 && head[1] == -69 && head[2] == -65) { //带BOM
            if (ignoreBom) {
                encodeType = CODE_UTF8;
            } else {
                encodeType = CODE_UTF8_BOM;
            }
        } else if ("Unicode".equals(encodeType)) {
            encodeType = "UTF-16";
        } else if (isUTF8(bis)) {
            encodeType = CODE_UTF8;
        } else {
            encodeType = CODE_GBK;
        }
        return encodeType;
    }
    /**
     * 是否是无BOM的UTF8格式，不判断常规场景，只区分无BOM UTF8和GBK
     *
     * @param bis
     * @return
     */
    private static boolean isUTF8( BufferedInputStream bis) throws Exception {
        bis.reset();

        //读取第一个字节
        int code = bis.read();
        do {
            BitSet bitSet = convert2BitSet(code);
            //判断是否为单字节
            if (bitSet.get(0)) {//多字节时，再读取N个字节
                if (!checkMultiByte(bis, bitSet)) {//未检测通过,直接返回
                    return false;
                }
            } else {
                //单字节时什么都不用做，再次读取字节
            }
            code = bis.read();
        } while (code != -1);
        return true;
    }
    /**
     * 检测多字节，判断是否为utf8，已经读取了一个字节
     *
     * @param bis
     * @param bitSet
     * @return
     */
    private static boolean checkMultiByte( BufferedInputStream bis,  BitSet bitSet) throws Exception {
        int count = getCountOfSequential(bitSet);
        byte[] bytes = new byte[count - 1];//已经读取了一个字节，不能再读取
        bis.read(bytes);
        for (byte b : bytes) {
            if (!checkUtf8Byte(b)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检测单字节，判断是否为utf8
     *
     * @param b
     * @return
     */
    private static boolean checkUtf8Byte(byte b) throws Exception {
        BitSet bitSet = convert2BitSet(b);
        return bitSet.get(0) && !bitSet.get(1);
    }

    /**
     * 检测bitSet中从开始有多少个连续的1
     *
     * @param bitSet
     * @return
     */
    private static int getCountOfSequential( BitSet bitSet) {
        int count = 0;
        for (int i = 0; i < BYTE_SIZE; i++) {
            if (bitSet.get(i)) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }


    /**
     * 将整形转为BitSet
     *
     * @param code
     * @return
     */
    private static BitSet convert2BitSet(int code) {
        BitSet bitSet = new BitSet(BYTE_SIZE);

        for (int i = 0; i < BYTE_SIZE; i++) {
            int tmp3 = code >> (BYTE_SIZE - i - 1);
            int tmp2 = 0x1 & tmp3;
            if (tmp2 == 1) {
                bitSet.set(i);
            }
        }
        return bitSet;
    }


}
