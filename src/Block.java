import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Block {

	private int num;
	private int amount;
	private Hash prevHash;
	private long nonce;
	private Hash currHash;
	
	public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
		this.num = num;
		this.amount = amount;
		this.prevHash = prevHash;

		// Mine for valid nonce
		Random r = new Random(); // Create random generator
		while (true) {
			long l = (long) r.nextLong(); // New random long to try

			byte[] b = ByteBuffer.allocate(8).putInt((int) amount + (int) l).array();
			Hash h = new Hash(calculateHash(b.toString()));
			
			if (h.isValid()) {
				nonce = l;
				currHash = h;
				break;
			}
		}
	}

	public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
		this.num = num;
		this.amount = amount;
		this.prevHash = prevHash;
		this.nonce = nonce;
		
		// Calculate hash
		byte[] b = ByteBuffer.allocate(8).putInt((int) amount + (int) nonce).array();
		this.currHash = new Hash(calculateHash(b.toString()));
	}
	
	public int getNum() { return num; }
	public int getAmount() { return amount; }
	public long getNonce() { return nonce; }
	public Hash getPrevHash() { return prevHash; }
	public Hash getHash() { return currHash; }
	
	public String toString() {
		return String.format("Block %d (Amount: %d, Nonce: %d, prevHash: %s, hash: %s)", 
				num, amount, nonce, prevHash.toString(), currHash.toString());
	}
	
	public static byte[] calculateHash(String msg) throws NoSuchAlgorithmException {
	    MessageDigest md = MessageDigest.getInstance("sha-256");
	    md.update(msg.getBytes());
	    byte[] hash = md.digest();
	    return hash;
	}
	
	
}
