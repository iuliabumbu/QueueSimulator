package Simulation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import Clients.Client;

public class Server implements Runnable{
	private BlockingQueue<Client> clienti;
	private AtomicInteger waitTime;

	public Server(int maxClientsPerServer) {
		waitTime = new AtomicInteger(0);
		clienti = new ArrayBlockingQueue<Client>(maxClientsPerServer);
		
	}
	
	public BlockingQueue<Client> getClienti() {
		return clienti;
	}

	public void setClienti(BlockingQueue<Client> clienti) {
		this.clienti = clienti;
	}

	public AtomicInteger getWaitTime() {
		return waitTime;
	}


	public void setWaitTime(AtomicInteger waitTime) {
		this.waitTime = waitTime;
	}
	

	public void adaugaClient(Client c) {
		clienti.add(c);
		c.setFinishTime(c.getServTime()+waitTime.intValue());
		waitTime.getAndAdd(c.getServTime());
	}
	
	
	public void run() {	 
		while(true) {
		   try {
			     if(clienti.peek() != null) {
			          int serviceTime = clienti.peek().getServTime();
                      for(int i = 0; i< serviceTime;i++) {
                      waitTime.decrementAndGet();
                      Iterator iterator = clienti.iterator();
                      Client client = (Client) iterator.next();
                      int val = client.getServTime()-1;
                      client.setServTime(val);
                      if(client.getServTime() == 0)
                      clienti.remove(client);
                      Thread.sleep(1000);
                      }
		      	  }
			      else {
			    	  Thread.sleep(1000);
		       	  }	
			     
		 } catch (InterruptedException e) {
				System.out.println("Exceptie in thread coada\n");
				e.printStackTrace();
		   }
	  }	 
	}

	public String afiseazaClienti() {
		String s="";
		for(Client x: clienti) {
			s = s + x.toString();
		}
		
		return s;
	}
		
	}
	


