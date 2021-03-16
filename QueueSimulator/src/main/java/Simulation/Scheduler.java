package Simulation;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Clients.Client;

public class Scheduler {
	private ArrayList<Server> servers;
	private int maxServers;
	private int maxClientsPerServer;
	private Strategy strategy;
	private ArrayList<Client> list= new ArrayList<Client>();
	
	public Scheduler(int maxServers, int maxClientsPerServer) {

		servers = new ArrayList<Server>(maxServers);
		Thread[] t = new Thread[maxServers];
		
		for( int i=0; i < maxServers; i++) {
			Server s =new Server(maxClientsPerServer);
			servers.add(s);
			t[i] = new Thread(servers.get(i));
		    t[i].start();
		}
		
		this.maxClientsPerServer = maxClientsPerServer;
		this.maxServers = maxServers;
	}
	
	public void setStrategy() {
			strategy = new ConcreteStrategyTime();
		}
		
	public void dispatchClient(Client t) {
		strategy.addClient(servers, t);
	}
	
	public ArrayList<Server> getServers(){
		return servers;
	}
	
	public int lookupClient(int id) {
	    for (Client client : list) {
	        if (client.getId() == id) {
	            return 1;
	        }
	    }
	    return 0;
	}
	
	public void addAllClients(){
		for(Server s : servers)
		for(Client x : s.getClienti()){
			if(lookupClient(x.getId()) == 0) {
		    	list.add(x);
			}
		}
		
	}
	
	public ArrayList<Client> getAllClients(){
		return list;
	}

}
