import java.security.NoSuchAlgorithmException;

public class BlockChain {

	private Node first;
	private Node last;
	
	private int sumAlice;
	private int sumBob;
	
	// Wrapper Node class for each block
	private static class Node {
		public Block block;
		public Node next;
		
		/**
		 * Node constructor
		 * @param block the block for the node to wrap around
		 * @param next the next node
		 */
		public Node(Block block, Node next) {
			this.block = block;
			this.next = next;
		}
		
	}
	
	/**
	 * Constructor method for BlockChain
	 * @param initial the initial amount that Alice starts off with
	 * @throws NoSuchAlgorithmException
	 */
	public BlockChain(int initial) throws NoSuchAlgorithmException {
		Block newBlock = new Block(0, initial, null);
		Node newNode = new Node(newBlock, null);
		
		first = newNode;
		last = newNode;
		
		sumAlice = initial;
		sumBob = 0;
	}
	
	/**
	 * @param amount the amount involved in the transaction
	 * @return a Block object with the appropriate nonce and hash
	 * @throws NoSuchAlgorithmException
	 */
	public Block mine(int amount) throws NoSuchAlgorithmException {
		return new Block(last.block.getNum() + 1, amount, last.block.getHash());
	}
	
	/**
	 * @return the number of nodes in the BlockChain
	 */
	public int getSize() { return last.block.getNum() + 1; }
	
	/**
	 * Appends a new node encompassing a given block
	 * @param blk the block to append
	 */
	public void append(Block blk) {
		Node newNode = new Node(blk, null);
		
		if (!blk.getHash().isValid()) { throw new IllegalArgumentException("Invalid hash!"); }
		if (blk.getPrevHash() != last.block.getHash()) { throw new IllegalArgumentException("Previous hash does not match"); }
		
		updateSums(blk.getAmount());
		last.next = newNode;
		last = last.next;
	}

	/**
	 * Removes the last node in the BlockChain
	 * @return true if a node was removed, false if nothing was removed
	 */
	public boolean removeLast() {
		if (last == first) { return false; }
		
		// Traverse through list
		Node tmp = first;
		while (tmp.next.next != null) {
			tmp = tmp.next;
		}
		updateSums(-tmp.next.block.getAmount());
		tmp.next = null; // Remove reference to last node
		return true;
	}
	
	/**
	 * @return the Hash of the last Block in the BlockChain
	 */
	public Hash getHash() { return last.block.getHash(); }
	
	/**
	 * @return whether or not the BlockChain has valid hashes, matching prevHashes, and valid sums of money for both Alice and Bob
	 */
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
	
	/**
	 * print balance of Alice and Bob
	 */
	public void printBalances() {
		System.out.println("Alice: " + sumAlice + ", " + "Bob: " + sumBob);
	}
	
	/**
	 * @return string representation of the whole BlockChain, one node on one line
	 */
	public String toString() {
		String ret = "";
		
		Node tmp = first;
		while (tmp != null) {
			ret += tmp.block.toString() + "\n";
			tmp = tmp.next;
		}
		return ret;
	}
	
	/**
	 * Updates the total amounts of money that Alice and Bob has, given the amount transferred
	 * @param amount amount transferred from Bob to Alice
	 */
	private void updateSums(int amount) {
		sumAlice += amount;
		sumBob -= amount;
	}
	
}
