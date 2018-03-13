import mungo.lib.Typestate;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;

@Typestate("CProtocol")
public class CRole{

	private BufferedReader socketAIn = null;
	private PrintWriter socketAOut = null;
	private BufferedReader socketBIn = null;
	private PrintWriter socketBOut = null;

	public CRole() {
		// Bind the sockets
		ServerSocket serverA = null;
		ServerSocket serverB = null;
		// Connecting to the server
		try {// Create the sockets
			serverA = new ServerSocket(20003);
			serverB = new ServerSocket(20004);
		}
		catch(IOException e) {
			System.out.println("Unable to listen on ports");
			System.exit(-1);
		}
		// Accept a client connection
		Socket socketA = null;
		Socket socketB = null;
		try {
			System.out.println("Accepting...");
			socketA = serverA.accept();
			System.out.println("A accepted");
			System.out.println("Accepting...");
			socketB = serverB.accept();
			System.out.println("B accepted");
		}
		catch (IOException e) {
			System.out.println("Accept failed");
			System.exit(-1);
		}
		// Create the read and write streams
		try {
			socketAIn = new BufferedReader(new InputStreamReader(socketA.getInputStream()));
			socketAOut = new PrintWriter(socketA.getOutputStream(), true);
			socketBIn = new BufferedReader(new InputStreamReader(socketB.getInputStream()));
			socketBOut = new PrintWriter(socketB.getOutputStream(), true);
		}
		catch (IOException e) {
			System.out.println("Read failed");
			System.exit(-1);
		}
	}

	public void send_requestStringToA(String payload) {
		this.socketAOut.println(payload);
	}

	public int receive_dataintFromA() {
		String line = "";
		try {
			line  = this.socketAIn.readLine();
		}
		catch(IOException e) {
			System.out.println("Input/Outpur error.");
			System.exit(-1);
		}
		return Integer.parseInt(line);
	}

	public void send_paymentintToB(int payload) {
		this.socketBOut.println(payload);
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
		int intLabelChoice1 = stringLabelChoice1.matches("APPROVE") ? 1 : 2;;
		switch(intLabelChoice1) {
			case 1:
				return Choice1.APPROVE;
			case 2:
			default:
				return Choice1.REFUSE;
		}
	}

	public Boolean receive_approveBooleanFromB() {
		String line = "";
		try {
			line  = this.socketBIn.readLine();
		}
		catch(IOException e) {
			System.out.println("Input/Outpur error.");
			System.exit(-1);
		}
		// Perform a cast of line to the appropriate type and then return it
		return Boolean.parseBoolean(line);
	}

	public String receive_invoiceStringFromA() {
		String line = "";
		try {
			line  = this.socketAIn.readLine();
		}
		catch(IOException e) {
			System.out.println("Input/Outpur error.");
			System.exit(-1);
		}
		return line;
	}

	public Boolean receive_refuseBooleanFromB() {
		String line = "";
		try {
			line  = this.socketBIn.readLine();
		}
		catch(IOException e) {
			System.out.println("Input/Outpur error.");
			System.exit(-1);
		}
		// Perform a cast of line to the appropriate type and then return it
		return Boolean.parseBoolean(line);
	}
}
