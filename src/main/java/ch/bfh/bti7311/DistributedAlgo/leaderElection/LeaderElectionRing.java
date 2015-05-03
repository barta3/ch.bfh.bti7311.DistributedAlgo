package ch.bfh.bti7311.DistributedAlgo.leaderElection;

import java.util.ArrayList;
import java.util.List;

public class LeaderElectionRing {

	static int size = 100;
	static int[] testdata = new int[] {29, 74, 14, 55, 58};
//	static int size = testdata.length;
	public static void main(String[] args) throws InterruptedException {
		
		List<Node> ring = createRing();
		
		for(Node n : ring) {
			System.out.println(n.toString());
			new Thread(n).start();
		}
	}
	
	private static List<Node> createRing() {
		List<Node> ring = new ArrayList<Node>();
		
		for(int i = 0; i < size; i++) {
			Node n = new Node((int) (Math.random() * size * 10));
//			Node n = new Node(testdata[i]);
			ring.add(n);
		}
		
		for(int i = 0; i < ring.size(); i++) {
			Node n = ring.get(i);
			Node next = ring.get(i < size-1 ? i+1 : 0);
			n.setNext(next);
		}
		
		return ring;
	}

}
