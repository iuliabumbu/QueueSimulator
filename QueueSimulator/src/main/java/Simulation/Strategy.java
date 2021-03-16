package Simulation;
import java.util.ArrayList;

import Clients.Client;

public interface Strategy {
	
	public void addClient(ArrayList<Server> servers, Client t);

}
