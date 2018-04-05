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
		int price = currentB.receive_checkPriceFromC();
		System.out.println("Received request from the client: " + price);
		System.out.print("Approve payment? (yes/no): ");
		int label1 = safeRead(readerB).matches("yes") ? 1 : 2;
		String code;
		switch(label1) {
			case 1 /*APPROVE*/:
				currentB.send_APPROVEToCA();
				code = "A12345";
				System.out.println("Sending approval code to the client and the agent: " + code);	//alterar -> sending approval code...
				//String payload2 = /* parse me! */ safeRead(readerB);	//alterar
				currentB.send_approveCodeToCA(code);
				System.out.println("Making transaction for flight payment to the agent: " + price + "€");	//alterar -> making payment of...
				//int payload3 = /* parse me! */ Integer.parseInt(safeRead(readerB));	//alterar
				currentB.send_paymentPriceToA(price);
				break;
			case 2 /*REFUSE*/:
				currentB.send_REFUSEToCA();
				code = "R12345";
				System.out.println("Sending refusal code to the client and the agent: " + code);	//alterar -> sending refusal code...
				//String payload4 = /* parse me! */ safeRead(readerB);
				currentB.send_refuseCodeToAC(code);
				break;
		}
	}
}
