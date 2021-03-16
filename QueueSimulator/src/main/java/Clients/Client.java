package Clients;

public class Client implements Comparable<Client>{
	
	private int id;
	private int arrTime;
	private int servTime;
	private int finishTime;
	
	public Client(int id, int arrTime, int servTime) {
		super();
		this.id = id;
		this.arrTime = arrTime;
		this.servTime = servTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArrTime() {
		return arrTime;
	}

	public void setArrTime(int arrTime) {
		this.arrTime = arrTime;
	}

	public int getServTime() {
		return servTime;
	}

	public void setServTime(int servTime) {
		this.servTime = servTime;
	}
	

	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}

	public String toString() {
		return "(" + id +", " + arrTime +", " + servTime + ") ";
	}
	

	public int compareTo(Client o) {
		if (this.arrTime > o.arrTime)
			return 1;
		else if(this.arrTime == o.arrTime)
			return 0;
		else return -1;
	}

}

