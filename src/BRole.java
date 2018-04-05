import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import mungo.lib.Typestate;

@Typestate("BProtocol")
public class BRole{
	
	private BufferedReader socketCIn = null;
	private PrintWriter socketCOut = null;
	private BufferedReader socketAIn = null;
	private PrintWriter socketAOut = null;
	
	public BRole() {
		// Connect to the other participants in the protocol
		try {
			// Create the sockets
			Socket socketC = new Socket("localhost", 20004);
			Socket socketA = new Socket("localhost", 20005);
			socketCIn = new BufferedReader(new InputStreamReader(socketC.getInputStream()));
			socketCOut = new PrintWriter(socketC.getOutputStream(), true);
			socketAIn = new BufferedReader(new InputStreamReader(socketA.getInputStream()));
			socketAOut = new PrintWriter(socketA.getOutputStream(), true);
		}
		catch(UnknownHostException e) {
			System.out.println("Unable to connect to the remote host");
			System.exit(-1);}
		catch (IOException e) {
			System.out.println("Input/output error");
			System.exit(-1);
		}
	}
	
	public int receive_checkPriceFromC() {
		String line = "";
		try {
			line  = this.socketCIn.readLine();
		}

		catch(IOException e) {
			System.out.println("Input/Outpur error.");
			System.exit(-1);}
		// Perform a cast of line to the appropriate type and then return it
		return Integer.parseInt(line);
	}
	
	public void send_APPROVEToCA() {
		this.socketCOut.println("APPROVE");
		this.socketAOut.println("APPROVE");
	}
	
	public void send_REFUSEToCA() {
		this.socketCOut.println("REFUSE");
		this.socketAOut.println("REFUSE");
	}
	
	public void send_approveCodeToCA(String payload) {
		this.socketCOut.println(payload);
		this.socketAOut.println(payload);
	}
	
	public void send_paymentPriceToA(int payload) {
		this.socketAOut.println(payload);
	}
	
	public void send_refuseCodeToAC(String payload) {
		this.socketAOut.println(payload);
		this.socketCOut.println(payload);
	}
}
