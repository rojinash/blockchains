import java.util.Arrays;

public class Hash {

	private byte[] data;
	
	public Hash(byte[] data) {
		this.data = data;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public boolean isValid() {
		for (int i = 0; i < 3; i++) {
			if (data[i] != 0x0) {
				return false;
			}
		}
		return true;
	}
	
	public String toString() {
		
		String ret = "";
		for (int i = 0; i < data.length; i++) {
			int d = Byte.toUnsignedInt(data[i]);
			ret = String.format("%s%02X", ret, d);
		}

		return ret;
	}

	public boolean equals(Object other) {
		if (other.getClass().toString().equals("Hash")) {
			
			Hash o = (Hash) other;
			
			if (Arrays.equals(o.data, this.data)) {
				return true;
			}
		}
	
		return false;
	}
	
	public static void main(String[] args) {
		byte[] data = {0x00, 0x00, 0x00, 0x2f, 0x21, 0x03};
		Hash h = new Hash(data);
		if (h.isValid()) {
			System.out.println("valid!");
			System.out.println("hash: " + h.toString());
		} else {

			System.out.println("nope!");
			System.out.println("hash: " + h.toString());
		}
	}
}
