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
		System.out.print("What is your wanted destination? ");
		String payload1 = /* parse me! */ safeRead(readerC);
		currentC.send_bookFlightToA(payload1);
		int payload2 = currentC.receive_farePriceFromA();
		System.out.println("The flight will cost you: " + payload2 + "€");
		System.out.println("Requesting approval from the bank for " + payload2 + "€");	//alterar
		//int payload3 = /* parse me! */ Integer.parseInt(safeRead(readerC));
		currentC.send_checkPriceToB(payload2);
		switch(currentC.receive_Choice1LabelFromB()) {
			case APPROVE:
				String payload4 = currentC.receive_approveCodeFromB();
				System.out.println("Received approval code from the bank: " + payload4);
				String payload5 = currentC.receive_invoiceInvoiceFromA();
				System.out.println("Received the payment invoice from the agent: " + payload5);
				String payload6 = currentC.receive_ticketTicketFromA();
				System.out.println("Received ticket from the agent: " + payload6);
				break;
			case REFUSE:
				String payload7 = currentC.receive_refuseCodeFromB();
				System.out.println("Received refusal code from the bank: " + payload7);
				break;
		}
	}
}
