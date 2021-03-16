package Simulation;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import Clients.Client;

public class ConcreteStrategyTime implements Strategy {

	public void addClient(ArrayList<Server> servers, Client t) {
		Server dest = null;
		int min = Integer.MAX_VALUE;
		for(Server s: servers)
		{
			if(s.getClienti().isEmpty()) {
				dest = s;
				break;
			}
			if(s.getWaitTime().intValue() < min) {
				min = s.getWaitTime().intValue();
			    dest = s;
			}
		}
		dest.adaugaClient(t);
		
	}
}
