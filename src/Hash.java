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
			ret = String.format("%s%02x", ret, d);
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
}
