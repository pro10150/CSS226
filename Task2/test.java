
import java.util.StringTokenizer;
import java.util.logging.Logger;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
public class test{
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
			ArrayList<String> List = new ArrayList<String>();
			while(s.hasNext()){
				List.add(s.nextLine());
			}
			return List;
		}catch(FileNotFoundException e){
		}
		return null;
	}


	private static class MessageLoop implements Runnable {
		public void run() {
			String paragraph = null;
			ArrayList importantInfo = read("C:\\Users\\ME\\iCloudDrive\\class\\CSS226 OS\\CSS226\\CSS226\\Task2\\test.txt");
			try {
				Iterator itr = importantInfo.iterator();
				while (itr.hasNext()) {
					Thread.sleep(50);
					if (itr.next().toString().isEmpty() == true){
						//paragraph += itr.next().toString();
						//System.out.println(paragraph);
						if (paragraph!=null)threadMessage1(paragraph);
						paragraph = null;
					}
					else paragraph += itr.next().toString();
				}
			} catch (InterruptedException e) {
				threadMessage2("I wasn't done!");
			}
		}
	}

	public static void main(String[] args) {
		//variable for the main thread to wait for another thread
		long patience = 1000 * 20;
		String paragraph = null;
		if (args.length > 0) {
			try {
				patience = Long.parseLong(args[0]) * 1000;
			} catch (NumberFormatException e) {
				System.err.println("Argument must be an integer.");
				System.exit(1);
			}
		}


		//main event
		threadMessage1("Starting MessageLoop thread");
		//Get the time that this program started
		long startTime = System.currentTimeMillis();
		//create a new MessageLoop class thread
		Thread t = new Thread(new MessageLoop());
		//star the thread
		t.start();
		threadMessage1("Waiting for MessageLoop thread to finish");
		//counter to count how long that the main thread wait
		int count = 1;
		while (t.isAlive()) {
			try {
				ArrayList<String> words = read("C:\\Users\\ME\\iCloudDrive\\class\\CSS226 OS\\CSS226\\CSS226\\Task2\\test.txt");

				Iterator itr = words.iterator();
				while (itr.hasNext()) {
					Thread.sleep(50);
					if (itr.next().toString().isEmpty() == true){
						//paragraph += itr.next().toString();
						//System.out.println(paragraph);
						if (paragraph!=null)threadMessage1(paragraph);
						paragraph = null;
					}
					else paragraph += itr.next().toString();

				}

				//wait for the mesagaloop to end upto 1 second then not wit anymore
				t.join(50);
				if (((System.currentTimeMillis() - startTime) > patience)
						&& t.isAlive()) {
					try {
						threadMessage2("Tired of waiting!");
						t.interrupt();
						t.join();
					} catch (InterruptedException ex) {
						Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			} catch (InterruptedException ex) {
				Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		threadMessage1("Finally!");

	}
}
