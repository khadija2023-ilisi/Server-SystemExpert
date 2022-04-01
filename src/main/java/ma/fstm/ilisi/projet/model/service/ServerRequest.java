package ma.fstm.ilisi.projet.model.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import ma.fstm.ilisi.projet.model.bo.Diagnostic;
import ma.fstm.ilisi.projet.model.bo.Symptom;
import ma.fstm.ilisi.projet.model.dao.DAOSymptom;

public class ServerRequest extends Thread {
	int nbClients;

	@Override
	public void run() {
		try {
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(234);
			while (true) {

				Socket s = ss.accept();
				++nbClients;
				new Conversation(s, nbClients).start();
			}
		} catch (IOException e) {
			e.getMessage();
		}
	}

	class Conversation extends Thread {
		private Socket socket;
		private int numeroClient;

		Conversation(Socket socket, int num) {
			this.socket = socket;
			this.numeroClient = num;
		}

		public void run() {
			try {
				// pour la lecture des objets
				InputStream is = socket.getInputStream();
				ObjectInputStream objectInputStream = new ObjectInputStream(is);
				// pour envoyer la reponse
				OutputStream os = socket.getOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
				PrintWriter pw = new PrintWriter(os, true);
				// avoir l'adreese ip du client
				String IP = socket.getRemoteSocketAddress().toString();
				System.out.println(IP + " connected / numero  : " + numeroClient);
				// reception d'objet
				
					Request req = (Request) objectInputStream.readObject();
					switch (((Request) req).getType()) {
					//Cas ou l'utilisateur demande un diagnostique
					case 1:
						System.out.println("Demande de diagnostique :");
						Diagnostic dgstq = (Diagnostic) req.getObjet();
						//Execution de Drools
						dgstq.fireAll();
						System.out.println("Envoi de reponse vers : " + IP);

						System.out.println(dgstq);
						
						pw.println(dgstq.getPossi_presence());
						
						break;
					case 7:
						System.out.println("Requet de type 7");
						//envoie liste des symptomes
						List<Symptom> sympt=(List<Symptom>) new DAOSymptom().retreive();
						objectOutputStream.writeObject(sympt);
						break;

					default:
						break;
					}
				
			} catch (IOException e) {
				e.getMessage();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("En attente d'une requete ...");

		 new ServerRequest().start();
		 
	}

}
