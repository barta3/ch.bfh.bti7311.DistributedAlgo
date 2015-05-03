package ch.bfh.bti7311.DistributedAlgo.leaderElection;

public class Message {
	
	// TODO: Simplify, remove enum
	private int id;
	private Type type;
	public enum Type {
			CANDIDATE_IS, LEADER_IS
	}
	
	public Message(Type type, int id) {
		this.setId(id);
		this.setType(type);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type + " " + id;
	}

}
