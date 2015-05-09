package ch.bfh.bti7311.DistributedAlgo.leaderElection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import ch.bfh.bti7311.DistributedAlgo.leaderElection.Message.Type;

public class Node implements Runnable {

	private int id;
	private int currLeader;
	private Node next;

	private BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();

	public Node(int value) {
		this.id = value;
		this.currLeader = value;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void putInQueue(Message m) {
		try {
			queue.put(m);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return String.format("ID %d next: %d", id, next.getId());
	}

	public void run() {

		this.getNext().putInQueue(new Message(Type.CANDIDATE_IS, this.id));
		boolean done = false;

		Message msgArr;
		Message msgSend = null;
		try {
			while (!done) {
				msgArr = queue.take();
				System.out.println(id + " Received " + msgArr);

				if (msgArr.getType().equals(Type.CANDIDATE_IS)) {
					if (msgArr.getId() == this.id) {
						done = true;
						msgSend = new Message(Type.LEADER_IS, this.currLeader);
					} else {
						currLeader = Math.max(id, msgArr.getId());
						msgSend = new Message(Type.CANDIDATE_IS, currLeader);
					}
				} else {
					done = true;
					this.currLeader = msgArr.getId();
					msgSend = new Message(Type.LEADER_IS, msgArr.getId());
				}
				System.out.println(id + " Send " + msgSend);
				this.getNext().putInQueue(msgSend);

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(id + " FINISHED - Leader is " + currLeader);
	}

}
