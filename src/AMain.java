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
		String flight = currentA.receive_bookFlightFromC();
		System.out.println("Flight request from the client: " + flight);
		System.out.print("Send flight's price to the client: ");
		int price = /* parse me! */ Integer.parseInt(safeRead(readerA));
		currentA.send_farePriceToC(price);
		
		switch(currentA.receive_Choice1LabelFromB()) {
			case APPROVE:
				String payload3 = currentA.receive_approveCodeFromB();
				System.out.println("Received approved code from the bank: " + payload3);
				int payload4 = currentA.receive_paymentPriceFromB();
				System.out.println("Received the payment from the bank: " + payload4);
				String invoice = flight + " " + price;
				System.out.println("Sending invoice to the client: " + invoice);
				//String payload5 = /* parse me! */ safeRead(readerA);	//alterar
				currentA.send_invoiceInvoiceToC(invoice);
				String ticket = flight.substring(0, 1) + "8456923 " + flight;
				System.out.println("Sending ticket to the client: " + ticket);	//alterar
				//String payload6 = /* parse me! */ safeRead(readerA);
				currentA.send_ticketTicketToC(ticket);
				break;
			case REFUSE:
				String payload7 = currentA.receive_refuseCodeFromB();
				System.out.println("Received refuse code from the bank: " + payload7);
				break;
		}
	}
}
