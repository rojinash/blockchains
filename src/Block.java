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

		// Special case when we do not have a previous Hash, i.e. the first block
		if (prevHash == null) { prevHash = new Hash(new byte[8]); }
		
		// Mines for nonce; loops until it finds a nonce which generates a valid hash
		System.out.println("Mining...");
		Random r = new Random(); // Create random generator
		while (true) {
			long l = r.nextLong();

			// Try calculating hash
			byte[] b = ByteBuffer.allocate(48)
					.putInt(num)
					.putInt(amount)
					.put(prevHash.getData())
					.putLong(l)
					.array();
			Hash h = new Hash(calculateHash(b));
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
		byte[] b = ByteBuffer.allocate(48)
				.putInt(num)
				.putInt(amount)
				.put(prevHash.getData())
				.putLong(nonce)
				.array();
		this.currHash = new Hash(calculateHash(b));
	}
	
	public int getNum() { return num; }
	public int getAmount() { return amount; }
	public long getNonce() { return nonce; }
	public Hash getPrevHash() { return prevHash; }
	public Hash getHash() { return currHash; }
	
	public String toString() {
		if (prevHash == null) {
			return String.format("Block %d (Amount: %d, Nonce: %d, prevHash: %s, hash: %s)", 
					num, amount, nonce, "none", currHash.toString());
		}
		return String.format("Block %d (Amount: %d, Nonce: %d, prevHash: %s, hash: %s)", 
				num, amount, nonce, prevHash.toString(), currHash.toString());
	}
	
	public static byte[] calculateHash(byte[] byteArray) throws NoSuchAlgorithmException {
	    MessageDigest md = MessageDigest.getInstance("sha-256");
	    md.update(byteArray);
	    byte[] hash = md.digest();
	    return hash;
	}
	
	
}