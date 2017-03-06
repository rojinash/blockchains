import java.util.Arrays;

public class Hash {

	private byte[] data;
	
	/**
	 * Constructor method
	 * @param data byte array to put into the Hash
	 */
	public Hash(byte[] data) {
		this.data = data;
	}
	
	/**
	 * @return data inside the Hash
	 */
	public byte[] getData() {
		return data;
	}
	
	/**
	 * @return whether or not the Hash starts with 3 zeroes
	 */
	public boolean isValid() {
		for (int i = 0; i < 3; i++) {
			if (data[i] != 0x0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @return a hexadecimal representation of the hash, in a string
	 */
	public String toString() {
		String ret = "";
		for (int i = 0; i < data.length; i++) {
			int d = Byte.toUnsignedInt(data[i]);
			ret = String.format("%s%02x", ret, d);
		}

		return ret;
	}

	/**
	 * @return whether or not the supplied object has the same bytes as the current hash
	 */
	public boolean equals(Object other) {
		if (other.getClass().toString().equals("Hash")) {
			
			Hash o = (Hash) other;
			
			if (Arrays.equals(o.data, this.data)) {
				return true;
			}
		}
	
		return false;
	}
}
