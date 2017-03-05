import java.security.NoSuchAlgorithmException;

public class BlockChain {

	private Node first;
	private Node last;
	
	private int sumAlice;
	private int sumBob;
	
	private static class Node {
		public Block block;
		public Node next;
		
		public Node(Block block, Node next) {
			this.block = block;
			this.next = next;
		}
		
	}
	
	public BlockChain(int initial) throws NoSuchAlgorithmException {
		Block newBlock = new Block(0, initial, null);
		Node newNode = new Node(newBlock, null);
		
		first = newNode;
		last = newNode;
		
		sumAlice = initial;
		sumBob = 0;
	}
	
	public Block mine(int amount) throws NoSuchAlgorithmException {
		return new Block(last.block.getNum() + 1, amount, last.block.getHash());
	}
	
	public int getSize() { return last.block.getNum() + 1; }
	
	public void append(Block blk) {
		Node newNode = new Node(blk, null);
		
		if (!blk.getHash().isValid()) { throw new IllegalArgumentException("Invalid hash!"); }
		if (blk.getPrevHash() != last.block.getHash()) { throw new IllegalArgumentException("Previous hash does not match"); }
		
		updateSums(blk.getAmount());
		last.next = newNode;
	}

	public boolean removeLast() {
		if (last == first) { return false; }
		
		// Traverse through list
		Node tmp = first;
		while (tmp.next != last) {
			tmp = tmp.next;
		}
		updateSums(-tmp.next.block.getAmount());
		tmp.next = null; // Remove reference to last node
		return true;
	}
	
	public Hash getHash() { return last.block.getHash(); }
	
	public boolean isValidBlockChain() {
		Node tmp = first;
		while (tmp.next != null) {
			if (!tmp.block.getHash().isValid() || // Valid hash
					tmp.next.block.getPrevHash() != tmp.block.getHash() || // Matching hashes
					sumAlice < 0 || sumBob < 0) { // Valid amounts
				return false;
			}
			tmp = tmp.next;
		}
		
		// Does the same check to the last node
		if (!last.block.getHash().isValid()) { return false; }
		
		return true;
	}
	
	public void printBalances() {
		Node tmp = first.next;
		int sumAlice = first.block.getAmount();
		int sumBob = 0;
		while (tmp != null) {
			sumAlice += tmp.block.getAmount();
			sumBob -= tmp.block.getAmount();
		}
		
		System.out.println("Alice: " + sumAlice + ", " + "Bob: " + sumBob);
	}
	
	public String toString() {
		String ret = "";
		
		Node tmp = first;
		while (tmp != null) {
			ret += tmp.block.toString() + "\n";
		}
		return ret;
	}
	
	private void updateSums(int amount) {
		sumAlice += amount;
		sumBob -= amount;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
