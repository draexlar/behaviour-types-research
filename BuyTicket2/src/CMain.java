
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class CMain {

	public static String safeRead(BufferedReader readerC) {
		String readline = "";
		try {
			readline = readerC.readLine();
		}
		catch(IOException e) {
			System.out.println("Input/Output error, unable to read");
			System.exit(-1);
		}
		return readline;
	}

	public static void main(String[] args) {
		// Create the current role
		CRole currentC =  new CRole();
		// readerC can be used to input strings, and then use them in send method invocation
		BufferedReader readerC = new BufferedReader(new InputStreamReader(System.in));
		// Method invocation follows the C typestate
		System.out.print("Send to A: ");
		String payload1 = safeRead(readerC);
		currentC.send_requestStringToA(payload1);
		int payload2 = currentC.receive_dataintFromA();
		System.out.println("Received from A: " + payload2);
		System.out.print("Send to B: ");
		int payload3 = Integer.parseInt(safeRead(readerC));
		currentC.send_paymentintToB(payload3);
		switch(currentC.receive_Choice1LabelFromB()) {
			case APPROVE:
				Boolean payload4 = currentC.receive_approveBooleanFromB();
				System.out.println("Received from B: " + payload4);
				String payload5 = currentC.receive_invoiceStringFromA();
				System.out.println("Received from A: " + payload5);
				break;
			case REFUSE:
				Boolean payload6 = currentC.receive_refuseBooleanFromB();
				System.out.println("Received from B: " + payload6);
				break;
		}
	}
}
