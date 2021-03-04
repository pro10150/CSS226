package test;

import java.util.StringTokenizer;
import java.util.logging.Logger;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;


public class Test {
    static void threadMessage1(String message) {
        String threadName = Thread.currentThread().getName();
        int word = word_count(message);
        System.out.format("%s: %s%n",threadName,word);
    }

    static void threadMessage2(String message){
        String threadName = Thread.currentThread().getName();
        System.out.format("%s: %s%n",threadName,message);
    }

    static int word_count(String message){
        int count = new StringTokenizer(message).countTokens();
        return count;
    }
    
    public static ArrayList read(String file){
        try{
            File f = new File(file);
            Scanner s = new Scanner(f);
            ArrayList List = new ArrayList();
            while(s.hasNext()){
                List.add(s.nextLine());
            }
            return List;
        }catch(FileNotFoundException e){
        }
        return null;
    }

    
    private static class MessageLoop implements Runnable { 
        @Override
        public void run() {
            ArrayList importantInfo = read("C:\\Users\\66931\\Desktop\\CSS226-master\\Task1\\test1.txt");
            try {
                Iterator itr = importantInfo.iterator();
                while (itr.hasNext()) {
                    Thread.sleep(500);
                    threadMessage1(itr.next().toString());
                }
            } catch (InterruptedException e) {
                threadMessage2("I wasn't done!");
            }
        }
    } 
    
    public static void main(String[] args) {
        //variable for the main thread to wait for another thread
        long patience = 900 * 10;
        if (args.length > 0) {
            try {
                patience = Long.parseLong(args[0]) * 1000;
            } catch (NumberFormatException e) {
                System.err.println("Argument must be an integer.");
                System.exit(1);
            }
        }

        
        //main event
        threadMessage2("Starting MessageLoop thread");
        //Get the time that this program started
        long startTime = System.currentTimeMillis();
        //create a new MessageLoop class thread
        Thread t = new Thread(new MessageLoop());
        //star the thread
        t.start();
        threadMessage2("Waiting for MessageLoop thread to finish");
        //counter to count how loang that the main thread wait
        int count = 1;
        while (t.isAlive()) {
            try {
                ArrayList words = read("C:\\Users\\66931\\Desktop\\CSS226-master\\Task1\\test1.txt");
                Iterator itr = words.iterator();
                while (itr.hasNext()) {
                    Thread.sleep(550);
                    threadMessage1(itr.next().toString());
                }

                //wait for the mesagaloop to end upto 1 second then not wit anymore
                t.join(1000);
                if (((System.currentTimeMillis() - startTime) > patience)
                        && t.isAlive()) {
                    try {
                        threadMessage2("Tired of waiting!");
                        t.interrupt();
                        t.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SimpleThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(SimpleThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        threadMessage2("Finally!");
    
    }
}