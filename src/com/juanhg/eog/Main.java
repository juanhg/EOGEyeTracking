package com.juanhg.eog;

import java.io.IOException;

public class Main{
	
	public static void main(String [] args) throws IOException, InterruptedException{
		String rFile = args[0];
		String lFile = args[1];
		String uFile = args[2];
		String dFile = args[3];
		
		EOGBasicAplication aplication = new EOGBasicAplication(rFile, lFile, uFile, dFile);
		aplication.run();
	}
}
