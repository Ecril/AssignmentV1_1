package com.example.assignmentv1_1;



import java.io.*;

public class SearchWord {
    public static String filepath=".\\src\\dict.txt";
    public static String LookItUp(String wd) {
        try {
            String encoding = "UTF-8"; //For a universal coding usage
            File file=new File(filepath);

            if(file.isFile() && file.exists()) {

                InputStreamReader read= new InputStreamReader(new FileInputStream(file), encoding); //Read the dict via InputStream
                BufferedReader bufferedReader = new BufferedReader(read);//Put the InputStream into a Buffer for readline()
                String rl = null;//String to load buffer line by line
                boolean flag = false;//To flag if a word exists in the dict, set as true when find it.


                while((rl = bufferedReader.readLine())!=null) {//Read the buffer line by line
                    if(rl.indexOf(wd)!=-1) {//If the word do exist in the dict
                        System.out.println("Congratulations! The word you search exists!\n");
                        String Meaning = rl.substring(wd.length());//Extract the meaning out of the read line.
                        System.out.println("Its meaning includes:"+Meaning);//Show the meaning of the searched word
                        flag = true;
                        return ("Congratulations! The word you search exists! Its meaning: "+Meaning);


                    }


                }read.close();

                if(!flag) {
                    System.out.println("Sorry, not found!\n");//Show the user the word is not found
                    return "Sorry, not found!\r\n";
                }
            }
        } catch(Exception e) {
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
            File file=new File(filepath);

            if(file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                String str = Meaning;
                BufferedReader bufferedReader = new BufferedReader(read);
                StringBuffer bf = new StringBuffer();//String buffer to load string that needs further load
                String rl = null;
                String wl = "";

                boolean flag = false;


                while((rl = bufferedReader.readLine())!=null) {
                    if(rl.indexOf(wd)!=-1) {
                        System.out.println("Sorry, the word already exists!\n");
                        outcome="Sorry, the word already exists!\n";
                        flag = true;
                        wl=wl+rl;
                        bf.append(wl+"\r\n");

                    }
                }read.close();

                if(!flag) {
                    System.out.println("Congratulations! You can add it to the dictionary!\n");
                    outcome = "Congratulations! You can add it to the dictionary!\n";
                    bf.append(wl+wd+" "+Meaning+"\r\n");
                    FileWriter out =new FileWriter(file,true);
                    out.write(bf.toString());
                    out.flush();
                    out.close();
                }

            }
            return outcome;
        } catch(Exception e) {
            System.out.println("Your operation goes wrong!");
            e.printStackTrace();
            return ("Your operation goes wrong!\r\n");
        }

    }
    public static String RemoveAWord(String wd) {
        String str=null;
        try {

            String encoding = "UTF-8";

            File file=new File(filepath);

            if(file.isFile() && file.exists()) {


                InputStreamReader read= new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String rl = null;
                String wl="";
                boolean flag = false;

                while((rl = bufferedReader.readLine())!=null) { //To find if the word already exists to be deleted
                    if(rl.indexOf(wd)!=-1) {
                        System.out.println("Congratulations! You can delete the existing word!\r\n");
                        //String Meaning = rl.substring(wd.length());
                        //System.out.println("Its meaning includes:"+Meaning);
                        flag = true;

                    }else {
                        wl=wl+rl+"\r\n";
                    }


                }read.close();
                FileWriter out =new FileWriter(file,false);
                System.out.println(wl);
                out.write(wl);
                out.flush();
                out.close();

                str="Successfully removed a word, you can check the dictionary.";

                if(!flag) {
                    System.out.println("Sorry, the word doesn't exist so can't be deleted!");
                    str="Sorry, the word doesn't exist so can't be deleted!";
                }
            }return str;

        }
        catch(Exception e) {
            System.out.println("Your operation goes wrong!");
            e.printStackTrace();
            return ("Your operation goes wrong!");
        }
    }
    public static String UpdateAWord(String wd, String NewMeaning) {

        try { String str=null;

            String encoding = "UTF-8";

            File file=new File(filepath);

            if(file.isFile() && file.exists()) {
                InputStreamReader read= new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);

                String rl = null;
                String wl ="";
                boolean flag = false;

                while((rl = bufferedReader.readLine())!=null) { //To find if the word already exists to be updated
                    if(rl.indexOf(wd)!=-1) {
                        System.out.println("Congratulations! You can update the existing word!\r\n");
                        String OldMeaning = rl.substring(wd.length()+1); //Extract the old meaning of word to be replaced
                        rl = rl.replace(OldMeaning, NewMeaning); //Update the word by replacing its old meaning with a new
                        System.out.println("The word is updated as: "+rl);
                        flag = true;

                    }wl=wl+rl+"\r\n";
                }

                read.close();

                OutputStreamWriter outStream= new OutputStreamWriter(new FileOutputStream(file),encoding);
                BufferedWriter writer = new BufferedWriter(outStream);
                writer.write(wl);
                writer.flush();
                writer.close();
                str="Updated successfully! You can check the dictionary now.";


                if(!flag) {
                    System.out.println("Sorry, since the word doesn't exist, you can't update it");
                    str="Sorry, since the word doesn't exist, you can't update it";
                }
            } return str;


        }catch(Exception e) {
            System.out.println("Your operation goes wrong!");
            e.printStackTrace();
            return "operation goes wrong!";

        }

    }
}
