import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import mungo.lib.Typestate;

@Typestate("AProtocol")
public class ARole{
	
	private BufferedReader socketCIn = null;
	private PrintWriter socketCOut = null;
	private BufferedReader socketBIn = null;
	private PrintWriter socketBOut = null;
	
	public ARole() {
		// Bind the sockets
		ServerSocket serverB = null;
		try {// Create the sockets
			serverB = new ServerSocket(20005);
		}
		catch (IOException e) {
			System.out.println("Unable to listen on port");
			System.exit(-1);
		}
		// Accept a client connection
		Socket socketB = null;
		try {
			System.out.println("Accepting...");
			socketB = serverB.accept();
		}

		catch (IOException e) {

			System.out.println("Accept failed");
			System.exit(-1);
		}
		// Create the read and write streams
		try {
			socketBIn = new BufferedReader(new InputStreamReader(socketB.getInputStream()));
			socketBOut = new PrintWriter(socketB.getOutputStream(), true);
		}
		catch (IOException e) {
			System.out.println("Read failed");
			System.exit(-1);
		}
		System.out.println("Accepted connection");
		// Connect to the servers
		try {// Create the sockets
			Socket socketC = new Socket("localhost", 20003);
			// Create the read and write streams
			socketCIn = new BufferedReader(new InputStreamReader(socketC.getInputStream()));
			socketCOut = new PrintWriter(socketC.getOutputStream(), true);
		}
		catch(UnknownHostException e) {
			System.out.println("Unable to connect to the remote host");
			System.exit(-1);}
		catch (IOException e) {
			System.out.println("Input/output error, unable to connect");
			System.exit(-1);
		}
	}
	
	public String receive_bookFlightFromC() {
		String line = "";
		try {
			line  = this.socketCIn.readLine();
		}

		catch(IOException e) {
			System.out.println("Input/Outpur error.");
			System.exit(-1);}
		// Perform a cast of line to the appropriate type and then return it
		return line;
	}
	
	public void send_farePriceToC(int payload) {
		this.socketCOut.println(payload);
	}
	
	public Choice1 receive_Choice1LabelFromB() {
		String stringLabelChoice1 = "";
		try {
			stringLabelChoice1 = this.socketBIn.readLine();
		}
		catch(IOException e) {
			System.out.println("Input/Outpur error, unable to get label");
			System.exit(-1);
		}
		int intLabelChoice1 = stringLabelChoice1.matches("APPROVE") ? 1 : 2;
		switch(intLabelChoice1) {
			case 1:
				return Choice1.APPROVE;
			case 2:
			default:
				return Choice1.REFUSE;
		}
	}
	
	public String receive_approveCodeFromB() {
		String line = "";
		try {
			line  = this.socketBIn.readLine();
		}

		catch(IOException e) {
			System.out.println("Input/Outpur error.");
			System.exit(-1);}
		// Perform a cast of line to the appropriate type and then return it
		return line;
	}
	
	public int receive_paymentPriceFromB() {
		String line = "";
		try {
			line  = this.socketBIn.readLine();
		}

		catch(IOException e) {
			System.out.println("Input/Outpur error.");
			System.exit(-1);}
		// Perform a cast of line to the appropriate type and then return it
		return Integer.parseInt(line);
	}
	
	public void send_invoiceInvoiceToC(String payload) {
		this.socketCOut.println(payload);
	}
	
	public void send_ticketTicketToC(String payload) {
		this.socketCOut.println(payload);
	}
	
	public String receive_refuseCodeFromB() {
		String line = "";
		try {
			line  = this.socketBIn.readLine();
		}

		catch(IOException e) {
			System.out.println("Input/Outpur error.");
			System.exit(-1);}
		// Perform a cast of line to the appropriate type and then return it
		return line;
	}
	
}
