package com.juanhg.eog;

import java.io.IOException;

public class Main{
	
	public static void main(String [] args) throws IOException, InterruptedException{
		String rFile = args[0];
		String lFile = args[1];
		String uFile = args[2];
		String dFile = args[3];
		
		EOGBasicApplication application = new EOGBasicApplication(rFile, lFile, uFile, dFile);
		application.run();
	}
}
