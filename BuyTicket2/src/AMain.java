import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class AMain {

	public static String safeRead(BufferedReader readerA) {
		String readline = "";
		try {
			readline = readerA.readLine();
		}
		catch(IOException e) {
			System.out.println("Input/Output error, unable to read");
			System.exit(-1);
		}
		return readline;
	}

	public static void main(String[] args) {
		// Create the current role
		ARole currentA =  new ARole();
		// readerA can be used to input strings, and then use them in send method invocation
		BufferedReader readerA = new BufferedReader(new InputStreamReader(System.in));
		// Method invocation follows the A typestate
		String payload1 = currentA.receive_requestStringFromC();
		System.out.println("Received from C: " + payload1);
		System.out.print("Send to C: ");
		int payload2 = Integer.parseInt(safeRead(readerA));
		currentA.send_dataintToC(payload2);
		switch(currentA.receive_Choice1LabelFromB()) {
			case APPROVE:
				Boolean payload3 = currentA.receive_approveBooleanFromB();
				System.out.println("Received from B: " + payload3);
				System.out.print("Send to BC: ");
				String payload4 = safeRead(readerA);
				currentA.send_invoiceStringToBC(payload4);
				break;
			case REFUSE:
				Boolean payload5 = currentA.receive_refuseBooleanFromB();
				System.out.println("Received from B: " + payload5);
				break;
		}
	}
}
