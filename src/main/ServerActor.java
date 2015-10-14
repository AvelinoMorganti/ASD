package main;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;
import utils.Read;
import utils.Tag;
import utils.Write;

/**
 * Server object -> implemented as an akka actor
 * 
 * @author Joao
 *
 */
public class ServerActor extends UntypedActor{

	/**
	 * Used to create the actor
	 * 
	 * In akka, actors are not created as new instances. Instead, they use 
	 * the Props, along side with the Creator class.
	 * 
	 * @param magicNumber - ID of the actor
	 * @return Props object with the created actor
	 */
	public static Props props(final int magicNumber){
		return Props.create(new Creator<ServerActor>(){
			private static final long serialVersionUID = 1L;

			@Override
			public ServerActor create() throws Exception {
				return new ServerActor(magicNumber);
			}

		});
	}


	private final int magicNumber;
	private int tag;
	private String value;

	/**
	 * Constructor of ServerActor.
	 * 
	 * Do not use this constructor to create new instances of this object!!!!!
	 * Instead use system.actorOf(ServerActor.props(42), "server42");
	 * 
	 * @param magicNumber - id of the actor
	 */
	public ServerActor(int magicNumber){
		super();

		this.magicNumber = magicNumber;
		this.tag = 0;
		this.value = "";
	}


	@Override
	public void onReceive(Object message) throws Exception {
		LoggingAdapter log = Logging.getLogger(getContext().system(), this);

		if (message instanceof Read) {//read
			log.info("Received read request");
			getSender().tell(new Object[]{tag, value}, getSelf());
		}
		else if(message instanceof Tag){//read-tag
			log.info("Received read tag request");
			getSender().tell(new Tag(tag), getSelf());
		}
		else if(message instanceof Write){//write
			final int tag = ((Write) message).getTag();
			final String value = ((Write) message).getValue();
			
			log.info("Received write request: {}", tag + " -> " + value);
			
			if(tag > this.tag){
				this.tag = tag;
				this.value = value;
			}
			
		}
		else{
			unhandled(message);
		}
	}
}
