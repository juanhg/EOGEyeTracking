package juanhg.eog.machine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import juanhg.eog.machine.listener.IEOG;
import juanhg.util.Time;

public class EOGModel {

	final int STATUS_REST = 0;
	final int STATUS_RIGHT = 1;
	final int STATUS_LEFT = 2;
	final int STATUS_DOWN = 3;
	final int STATUS_UP_BLINK = 4;
	final int STATUS_UP = 5;
	final int STATUS_BLINK = 6;

	final int RIGHT_SIGNAL = 1;
	final int LEFT_SIGNAL = 2;
	final int DOWN_SIGNAL = 3;
	final int UP_SIGNAL = 4;
	final int BLINK_SIGNAL = 5;
	final int NULL_SIGNAL = 0;

	public int upLimit = 100;
	public int downLimit = -300;
	public int leftLimit = -600;
	public int rightLimit = 600;
	
	int status;
	BufferedReader brR, brL, brD, brU;
	String RInput, LInput, UInput, DInput;
	int rValue, lValue, uValue, dValue;
	int signal;

	Time t;
	final double tLimit = 1000;
	final double itLimit = 400;

	IEOG listener;

	public EOGModel(String rightFile, String leftFile, String upFile, String downFile) throws IOException{
		boolean isSynchronized = false;

		brR = new BufferedReader(new FileReader(rightFile));
		brL = new BufferedReader(new FileReader(leftFile));
		brU = new BufferedReader(new FileReader(upFile));
		brD = new BufferedReader(new FileReader(downFile));

		System.out.println("Sincronizando...");

		while(!isSynchronized){
			RInput = brR.readLine();
			LInput = brL.readLine();
			UInput = brU.readLine();
			DInput = brD.readLine();

			if(RInput == null && LInput == null && UInput == null && DInput == null){
				isSynchronized = true;
			}
		}

		System.out.println("¡¡SINCRONIZADO!!");

		t = new Time();
		t.start();
	}

	public EOGModel(String rightFile, String leftFile, String upFile, String downFile,
			int rightLimit, int leftLimit, int upLimit, int downLimit) throws IOException{
		boolean isSynchronized = false;

		brR = new BufferedReader(new FileReader(rightFile));
		brL = new BufferedReader(new FileReader(leftFile));
		brU = new BufferedReader(new FileReader(upFile));
		brD = new BufferedReader(new FileReader(downFile));
		
		this.rightLimit = rightLimit;
		this.leftLimit = leftLimit;
		this.upLimit = upLimit;
		this.downLimit = downLimit;

		System.out.println("Sincronizando...");

		while(!isSynchronized){
			RInput = brR.readLine();
			LInput = brL.readLine();
			UInput = brU.readLine();
			DInput = brD.readLine();

			if(RInput == null && LInput == null && UInput == null && DInput == null){
				isSynchronized = true;
			}
		}

		System.out.println("¡¡SINCRONIZADO!!");

		t = new Time();
		t.start();
	}
	public void simulate(IEOG listener) throws IOException{

		this.listener = listener;
		if(brR.ready()){
			RInput = brR.readLine();
		}
		if(brL.ready()){
			LInput = brL.readLine();
		}
		if(brU.ready()){
			UInput = brU.readLine();
		}
		if(brD.ready()){
			DInput = brD.readLine();
		}

		//		System.out.println(t.getTime());

		if(RInput != null ||LInput != null ||UInput != null ||DInput != null ){

			signal = NULL_SIGNAL;

			if(isNumeric(RInput)){
				rValue = Integer.parseInt(RInput);
				if(rValue > rightLimit){
					signal = RIGHT_SIGNAL;
					//					System.out.println("Derecha");
				}
			}
			if(isNumeric(LInput)){
				lValue = Integer.parseInt(LInput);
				if(lValue != -32767 && lValue < leftLimit){
					signal = LEFT_SIGNAL;
					//					System.out.println("Izquierda");
				}
			}
			if(isNumeric(UInput)){
				uValue = Integer.parseInt(UInput);
				if(uValue > upLimit){
					signal = UP_SIGNAL;
					//System.out.println("Arriba");
				}
			}
			if(isNumeric(DInput)){
				dValue = Integer.parseInt(DInput);
				if(dValue != -32767 && dValue < downLimit){
					signal = DOWN_SIGNAL;
					//System.out.println("Abajo");
				}
			}
			//			System.out.println(DInput);

			evolve(signal);
		}
	}

	private void evolve(int currentSignal){
		t.pause();
		double time = t.getTime();
		t.start();

		switch (status){
		case STATUS_REST:
			switch(currentSignal){
			case RIGHT_SIGNAL:
				changeStatusTo(STATUS_RIGHT);
				break;
			case LEFT_SIGNAL:
				changeStatusTo(STATUS_LEFT);
				break;
			case DOWN_SIGNAL:
				changeStatusTo(STATUS_DOWN);
				break;
			case UP_SIGNAL:
				changeStatusTo(STATUS_UP_BLINK);
				break;
			case BLINK_SIGNAL:
				changeStatusTo(STATUS_BLINK);
				System.out.println(uValue);
				break;
			}
			break;
		case STATUS_UP_BLINK:
			if(currentSignal == DOWN_SIGNAL && time < itLimit){
				changeStatusTo(STATUS_UP);
			}
			if(time > itLimit){
				changeStatusTo(STATUS_BLINK);
			}
			break;
		default:
			if(time > tLimit){
				changeStatusTo(STATUS_REST);
			}
			break;
		}
	}

	private void changeStatusTo(int status){
		//		System.out.println("status:" +  status);
		t = new Time();
		t.start();
		this.status = status;

		switch(status){
		case STATUS_RIGHT:
			listener.rightEvent();
			break;
		case STATUS_LEFT:
			listener.leftEvent();
			break;
		case STATUS_UP:
			listener.upEvent();
			break;
		case STATUS_DOWN:
			listener.downEvent();
			break;
		case STATUS_BLINK:
			listener.blinkEvent();
			break;
		}
	}

	public static boolean isNumeric(String str)  
	{  
		try  
		{  
			if(str != null){
				@SuppressWarnings("unused")
				double d = Double.parseDouble(str);
			}
			else{
				return false;
			}
		}  
		catch(NumberFormatException nfe)  
		{  
			return false;  
		}  
		return true;  
	}
}
