package juanhg.eog.machine;

import java.io.IOException;

import juanhg.eog.machine.listener.IEOG;

public class EOGBasicApplication implements IEOG {

	EOGModel model;

	public EOGBasicApplication(String rFile, String lFile, String dFile, String uFile ) throws IOException{
		model = new EOGModel(rFile, lFile, dFile, uFile);	
	}
	
	public void run() throws IOException{		
		while(true){
			model.simulate(this);
		}
	}
	
	@Override
	public void rightEvent() {
		System.out.println("Derecha");
	}

	@Override
	public void leftEvent() {
		System.out.println("Izquierda");
	}

	@Override
	public void upEvent() {
		System.out.println("Up");
	}

	@Override
	public void downEvent() {
		System.out.println("Down");
	}

	@Override
	public void blinkEvent() {
		System.out.println("Parpadeo");
	}

}
