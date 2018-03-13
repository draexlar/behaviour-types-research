import mungo.lib.Typestate;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;

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
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getCause());
			System.exit(-1);
		}
		catch (IOException e) {
			System.out.println("Input/output error");
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getCause());
			System.exit(-1);
		}
	}

	public int receive_paymentintFromC() {
		String line = "";
		try {
			line  = this.socketCIn.readLine();
		}

		catch(IOException e) {
			System.out.println("Input/Outpur error.");
			System.exit(-1);
		}
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

	public void send_approveBooleanToCA(Boolean payload) {
		this.socketCOut.println(payload);
		this.socketAOut.println(payload);
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

	public void send_refuseBooleanToCA(Boolean payload) {
		this.socketCOut.println(payload);
		this.socketAOut.println(payload);
	}
}
