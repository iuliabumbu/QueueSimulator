package Simulation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;

import Clients.Client;

import java.io.*;

public class SimulationManager implements Runnable {
	public static int timeLimit; 
	public static int maxArrivalTime;
	public static int minArrivalTime;
	public static int maxProcessingTime;
	public static int minProcessingTime;
	public static int numberOfServers;
	public static int numberOfClients;
	private Scheduler scheduler; 
	private ArrayList<Client> generatedClients = new ArrayList<Client>();
	private String sirAfisare="Queues Simulator:\n";
	private static String pathOut;
	
	public SimulationManager() {
		scheduler = new Scheduler(numberOfServers, numberOfClients);
		scheduler.setStrategy();
		this.generateNRandomClients();
		
	}
	
	public void generateNRandomClients() {
	   for(int i= 0; i < numberOfClients; i++) {
			int tServ = ThreadLocalRandom.current().nextInt(minProcessingTime, maxProcessingTime+1);
			int tArr = ThreadLocalRandom.current().nextInt(minArrivalTime, maxArrivalTime+1);
			Client c = new Client(i, tArr, tServ);
			generatedClients.add(c);
		}
		

		Collections.sort(generatedClients);
		
	}

	public void run() {
		int currentTime = 0;	
		float avg = 0, nr = 0;
		while (currentTime < timeLimit && generatedClients.isEmpty() == false) {
			String s ="\r\nTime "+currentTime+"\r\n";
			this.sirAfisare = this.sirAfisare + s;
			ArrayList<Client> listaStergere= new ArrayList<Client>();
			for(Client x: generatedClients) {
				if(x.getArrTime() == currentTime) {
					scheduler.dispatchClient(x);
					listaStergere.add(x);
				}
			}
			generatedClients.removeAll(listaStergere);
			this.sirAfisare = this.sirAfisare + this.printGeneratedClients(generatedClients);
			this.sirAfisare = this.sirAfisare + this.afiseazaCozile(scheduler.getServers()); 	
			currentTime++;
			scheduler.addAllClients();	
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	
		nr = (generatedClients.isEmpty() == true) ? numberOfClients : numberOfClients - generatedClients.size();
		avg = this.computeWaitingTime() / nr;
		this.sirAfisare = this.sirAfisare +"\r\nAverage waiting time: "+avg;
		this.scriereFisier(pathOut, sirAfisare);
		
	}
	
	public String printGeneratedClients(ArrayList<Client> generatedClients) {
		String s = "Waiting clients:";
		for(Client x: generatedClients) {
			s = s + x.toString();
		}
		
		return s;	
	}
	
	public String afiseazaCozile(ArrayList<Server> s) {
		String sir="\r\n";
		int i = 1;
		for(Server x : s) {
			if(x.afiseazaClienti().equals("")) {
				sir = sir +"Queue"+i+": closed\r\n";
			}
			else {	
			sir = sir +"Queue"+i+": "+x.afiseazaClienti() + "\r\n";
			}
			i++;
			
		}	
		return sir;
	}
	
	public  float computeWaitingTime() {
		float sum = 0;
		
		for(Client y : scheduler.getAllClients()) {
				sum = sum + y.getFinishTime();
		}
		
		return sum;
	}

	
	public static  void setValues( String path, String path2) {
		FileInputStream f = null;
		try {
			f = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();	}
		InputStreamReader fchar=new InputStreamReader(f);
		BufferedReader buf=new BufferedReader(fchar);
		String linie = null, linie2 = null, linie3 = null, linie4 = null, linie5 = null;
		try {
			linie=buf.readLine(); linie2=buf.readLine(); linie3=buf.readLine(); linie4=buf.readLine(); linie5=buf.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int v[]=new int[7];
		numberOfClients = v[0] = Integer.parseInt(linie);  numberOfServers = v[1] = Integer.parseInt(linie2); timeLimit = v[2] = Integer.parseInt(linie3);
	    int i = 3;
		for(String val: linie4.split(",")) {
			v[i++] = Integer.parseInt(val);
		}
		for(String val: linie5.split(",")) {
			v[i++] = Integer.parseInt(val);
		}
		try {
			fchar.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 maxArrivalTime = v[4];  minArrivalTime = v[3];  maxProcessingTime =v[6]; minProcessingTime = v[5]; pathOut = path2;
	}
	

	public static  void scriereFisier( String path,String s) {
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(path);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		byte[] strToBytes = s.getBytes();
		 try {
			outputStream.write(strToBytes);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		 try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	
	}
	
	public static void main(String[] args) {
		setValues(args[0], args[1]);
		SimulationManager gen = new SimulationManager();
		Thread t = new Thread(gen); 
		t.start();	
	}
	
}
