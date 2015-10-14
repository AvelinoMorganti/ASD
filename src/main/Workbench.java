package main;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * Class that generates the workbench
 * 
 * @author Joao
 *
 */
public class Workbench {
	
	private static int N_SERVERS = 10;
	private static int N_REPLICATION = 3;

	
	/**
	 * Workbench creator
	 * 
	 * @param args- no arguments needed (yet).
	 */
	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("ActorSystem");
		final List<ActorRef> actors = new ArrayList<ActorRef>();
		
		for(int i = 0; i < N_SERVERS; i++){
			actors.add(system.actorOf(ServerActor.props(i), "Server" + (i+1)));
		}
	}

}
