package com.example.assignmentv1_1.DictionaryServer;


import java.io.*;

public class SearchWord {
    public static String filepath = DictionaryServer.Filepath;

    public static String LookItUp(String wd) {
        try {
            String encoding = "UTF-8"; //For a universal coding usage
            File file = new File(filepath);
            //在DictionaryServer中已经检查过文件情况了 不再检查，出错直接丢exception
//            if (file.isFile() && file.exists()) {

            InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); //Read the dict via InputStream
            BufferedReader bufferedReader = new BufferedReader(read);//Put the InputStream into a Buffer for readline()
            String rl;//String to load buffer line by line
            boolean flag = false;//To flag if a word exists in the dict, set as true when find it.


            while ((rl = bufferedReader.readLine()) != null) {//Read the buffer line by line
                if (rl.contains(wd)) {//If the word do exist in the dict
                    System.out.println("Congratulations! The word you search exists!\n");
                    String Meaning = rl.substring(wd.length());//Extract the meaning out of the read line.
                    System.out.println("Its meaning includes:" + Meaning);//Show the meaning of the searched word
                    flag = true;
                    return ("Congratulations! The word " + wd + " you search exists! Its meaning: " + Meaning);


                }


            }
            read.close();

            if (!flag) {
                System.out.println("Sorry, not found!\n");//Show the user the word is not found
                return "Sorry, not found!\r\n";
            }
//            }
        } catch (Exception e) {
            System.out.println("Your operation goes wrong!");//Throw an exception if something goes Wrong
            e.printStackTrace();
            return "Your operation goes wrong!";
        }
        return "search is done\r\n";
    }

    public static String AddAWord(String wd, String Meaning) {
        try {
            String encoding = "UTF-8";
            String outcome = null;
            File file = new File(filepath);
            //同理 不检查了
//            if (file.isFile() && file.exists()) {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
            BufferedReader bufferedReader = new BufferedReader(read);
            StringBuilder bf = new StringBuilder();//String buffer to load string that needs further load
            String rl;
            StringBuilder wl = new StringBuilder();

            boolean flag = false;


            while ((rl = bufferedReader.readLine()) != null) {
                if (rl.contains(wd)) {
                    System.out.println("Sorry, the word already exists!\n");
                    outcome = "Sorry, the word already exists!\n";
                    flag = true;
                    wl.append(rl);
                    bf.append(wl).append("\r\n");

                }
            }
            read.close();

            if (!flag) {
                System.out.println("Congratulations! You can add it to the dictionary!\n");
                outcome = "Congratulations! You can add it to the dictionary!\n";
                bf.append(wl).append(wd).append(" ").append(Meaning).append("\r\n");
                FileWriter out = new FileWriter(file, true);
                out.write(bf.toString());
                out.flush();
                out.close();
            }

//            }
            return outcome;
        } catch (Exception e) {
            System.out.println("Your operation goes wrong!");
            e.printStackTrace();
            return ("Your operation goes wrong!\r\n");
        }

    }

    public static String RemoveAWord(String wd) {

        boolean flag = false;
        try {
            String str;
            String encoding = "UTF-8";

            File file = new File(filepath);
            // 不检查文件，出错丢异常
//            if (file.isFile() && file.exists()) {


            InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
            BufferedReader bufferedReader = new BufferedReader(read);
            String rl;
            StringBuilder wl = new StringBuilder();


            while ((rl = bufferedReader.readLine()) != null) { //To find if the word already exists to be deleted
                if (rl.contains(wd)) {
                    System.out.println("Congratulations! You can delete the existing word!\r\n");
                    //String Meaning = rl.substring(wd.length());
                    //System.out.println("Its meaning includes:"+Meaning);
                    flag = true;
                    System.out.println("flag1: " + flag);
                } else {
                    wl.append(rl).append("\r\n");
                }


            }
            read.close();

            FileWriter out = new FileWriter(file, false);
            out.write(wl.toString());
            out.flush();
            out.close();

            if (flag) str = "Successfully removed the word " + wd + ", you can check the dictionary.";
            else {
                str = "Sorry, the word doesn't exist so can't be deleted!";
            }
            return str;
//            }

        } catch (Exception e) {
            System.out.println("Your operation goes wrong!");
            e.printStackTrace();
            return ("Your operation goes wrong!");
        }
//        return "Weird, the program is not running";
    }


    public static String UpdateAWord(String wd, String NewMeaning) {

        try {
            String str;

            String encoding = "UTF-8";

            File file = new File(filepath);
            //不检查
//            if (file.isFile() && file.exists()) {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
            BufferedReader bufferedReader = new BufferedReader(read);

            String rl;
            StringBuilder wl = new StringBuilder();
            boolean flag = false;

            while ((rl = bufferedReader.readLine()) != null) { //To find if the word already exists to be updated
                if (rl.contains(wd)) {
                    System.out.println("Congratulations! You can update the existing word!\r\n");
                    String OldMeaning = rl.substring(wd.length() + 1); //Extract the old meaning of word to be replaced
                    rl = rl.replace(OldMeaning, NewMeaning); //Update the word by replacing its old meaning with a new
                    System.out.println("The word is updated as: " + rl);
                    flag = true;

                }
                wl.append(rl).append("\r\n");
            }

            read.close();

            OutputStreamWriter outStream = new OutputStreamWriter(new FileOutputStream(file), encoding);
            BufferedWriter writer = new BufferedWriter(outStream);
            writer.write(wl.toString());
            writer.flush();
            writer.close();
            str = "Updated successfully! You can check the dictionary now.";


            if (!flag) {
                System.out.println("Sorry, since the word doesn't exist, you can't update it");
                str = "Sorry, since the word doesn't exist, you can't update it";
            }
//            }
            return str;


        } catch (Exception e) {
            System.out.println("Your operation goes wrong!");
            e.printStackTrace();
            return "operation goes wrong!";

        }

    }
}
