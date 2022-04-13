package com.example.assignmentv1_1.DictionaryServer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

//Dictionary file to hashmap
public class Dictionary {
    private static HashMap<String, String> DicFile;

    private Dictionary() {
        //禁止实例化
    }

    public static HashMap<String, String> getDictionary() {
        return DicFile;
    }

    public static String FindWord(String word) {
        //存在则返回meaning，不存在则返回null
        return DicFile.get(word);
    }

    public static String AddWord(String word, String Meaning) {
        //如果word已经存在，返回原meaning，如果word不存在，添加并返回null
        return DicFile.putIfAbsent(word, Meaning);
    }

    public static String UpdateWord(String word, String meaning) {
        //替换成功则返回原meaning，替换失败则为null
        return DicFile.replace(word, meaning);
    }

    public static String DeleteWord(String word) {
        //删除成功则返回原meaning，删除失败则为null
        return DicFile.remove(word);
    }
    //同步写入文件
    public static synchronized void SavetoFile(String filepath) throws IOException {
        //将hashmap中的数据存入文件
        File file = new File(filepath);
        FileWriter out = new FileWriter(file, StandardCharsets.UTF_8, false);
        for (Map.Entry<String,String> entry :DicFile.entrySet()) {
            out.write(entry.getKey() + "," + entry.getValue() + "\r\n");
        }
        out.flush();
        out.close();
    }

//        private void readDicFile() throws IOException {
//            readDicFile(DictionaryServer.Filepath);
//        }

    public static void readDicFile(String filepath) {
        DicFile = new HashMap<>();
        String encoding = "UTF-8"; //For a universal coding usage
        File file = new File(filepath);
        String rl;
        BufferedReader bufferedReader;
        try {

            InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); //Read the dict via InputStream
            bufferedReader = new BufferedReader(read);
            while ((rl = bufferedReader.readLine()) != null) {//Read the buffer line by line
                int KeyIndex = rl.indexOf(",");//找到第一个逗号的位置
                if (KeyIndex == -1) {
                    throw new IOException("Illegal Dictionary File Format");
                }
                String Key = rl.substring(0, KeyIndex);//逗号前的为key
                String Value = rl.substring(KeyIndex + 1);//逗号后的为value
                DicFile.put(Key, Value);
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Illegal Dictionary File Format");
            System.exit(1);
        }

    }


}
