import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class BlockChainDriver {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		if (args.length != 1) {
			System.exit(1);
		}

		int initial = Integer.parseInt(args[0]);

		BlockChain chain = new BlockChain(initial);
		
		while (true) {
			int amount;
			long nonce;
			System.out.println(chain.toString());
			Scanner in = new Scanner(System.in);
			
			System.out.print("Command? ");
			
			String command = in.nextLine();
			switch(command) {
			case "mine": 
				System.out.print("Amount transferred? ");
				amount = Integer.parseInt(in.nextLine());
				nonce = chain.mine(amount).getNonce();
				System.out.println("amount = " + amount + ", nonce = " + nonce);
				break;
			case "append":
				System.out.print("Amount transferred? ");
				amount = Integer.parseInt(in.nextLine());
				System.out.print("Amount transferred? ");
				nonce = Integer.parseInt(in.nextLine());
				
				chain.append(new Block(chain.getSize(), amount, chain.getHash(), nonce));
				break;
			case "remove":
				chain.removeLast();
				break;
			case "check":
				chain.isValidBlockChain();
				break;
			case "report":
				chain.printBalances();
				break;
			case "help":
				System.out.println("Valid commands:\n" + "mine: discovers the nonce for a given transaction\n"
						+ "append: appends a new block onto the end of the chain\n"
						+ "remove: removes the last block from the end of the chain\n"
						+ "check: checks that the block chain is valid\n"
						+ "report: reports the balances of Alice and Bob\n" + "help: prints this list of commands\n"
						+ "quit: quits the program");
				break;
			case "quit":
				in.close();
				System.exit(0);
			default:
				System.out.println("Invalid command. Type 'help' for valid commands.");
			}
		}
	}
}
