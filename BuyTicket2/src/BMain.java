import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class BMain {

	public static String safeRead(BufferedReader readerB) {
		String readline = "";
		try {
			readline = readerB.readLine();
		}
		catch(IOException e) {
			System.out.println("Input/Output error, unable to read");
			System.exit(-1);
		}
		return readline;
	}

	public static void main(String[] args) {
		// Create the current role
		BRole currentB =  new BRole();
		// readerB can be used to input strings, and then use them in send method invocation
		BufferedReader readerB = new BufferedReader(new InputStreamReader(System.in));
		// Method invocation follows the B typestate
		int payload1 = currentB.receive_paymentintFromC();
		System.out.println("Received from C: " + payload1);
		System.out.print("Choose a label among [APPROVE, REFUSE]: ");
		int label1 = safeRead(readerB).matches("APPROVE") ? 1 : 2;
		switch(label1) {
			case 1 /*APPROVE*/:
				currentB.send_APPROVEToCA();
				System.out.print("Send to CA: ");
				Boolean payload2 = /* parse me! */ Boolean.parseBoolean(safeRead(readerB));
				currentB.send_approveBooleanToCA(payload2);
				String payload3 = currentB.receive_invoiceStringFromA();
				System.out.println("Received from A: " + payload3);
				break;
			case 2 /*REFUSE*/:
				currentB.send_REFUSEToCA();
				System.out.print("Send to CA: ");
				Boolean payload4 = /* parse me! */ Boolean.parseBoolean(safeRead(readerB));
				currentB.send_refuseBooleanToCA(payload4);
				break;
		}
	}
}
