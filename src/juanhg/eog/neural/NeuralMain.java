package juanhg.eog.neural;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class NeuralMain {
	
	private final String verticalFile = "pahtfichero";
	private final String horizontalFile = "PathHorizontal";
	private final String GTFile = "PathGazeTrackerFile";
	private final String inputs = "path";
	private final String outputs = "path";
	private final int GTMeasureSize = 100;
	private final int neurobitMeasureSize = 1000;
	private final int topLimit = 400;
	private final int botLimit = -400; 
	
	BufferedReader GTReader, verticalReader, horizontalReader;
	BufferedWriter inputWriter, outputWriter;
	String verticalInput, HorizontalInput, GTInput;
	
	public NeuralMain() throws IOException{
		verticalReader = new BufferedReader(new FileReader(verticalFile));
		horizontalReader = new BufferedReader(new FileReader(horizontalFile));
		GTReader = new BufferedReader(new FileReader(GTFile));
		inputWriter = new BufferedWriter(new FileWriter(inputs));
		outputWriter = new BufferedWriter(new FileWriter(outputs));
	}
	
	private void synchronize() throws IOException{
		boolean isSynchronized = false;

		System.out.println("Sincronizando...");

		while(!isSynchronized){
			verticalInput = verticalReader.readLine();
			HorizontalInput = horizontalReader.readLine();
			GTInput = GTReader.readLine();

			if(verticalInput == null && HorizontalInput == null && GTInput == null){
				isSynchronized = true;
			}
		}
		System.out.println("¡¡SINCRONIZADO!!");
	}
	
	private void genNeuralFiles(int n) throws NumberFormatException, IOException{
		double vNeurobitMeasure = 0.0;
		double hNeurobitMeasure = 0.0;
		int eyeX, eyeY;
		int vTendency, hTendency;
		double vMeasure, hMeasure;
		
		for(int measure = 0; measure < n; ++measure){
			vNeurobitMeasure = 0.0;
			hNeurobitMeasure = 0.0;
			vTendency = hTendency = 0;
			eyeY = eyeX = 0;
			
			for(int j = 0; j < neurobitMeasureSize; ++j){
				vMeasure = Double.parseDouble(verticalReader.readLine());
				hMeasure = Double.parseDouble(horizontalReader.readLine());
				vNeurobitMeasure += vMeasure;
				hNeurobitMeasure += hMeasure;
				
				if(vTendency == 0 && vMeasure >= topLimit){
					vTendency = 1;
				}
				if(vTendency == 0 && vMeasure <= botLimit){
					vTendency = -1;
				}
				if(hTendency == 0 && hMeasure >= topLimit){
					hTendency = 1;
				}
				if(hTendency == 0 && hMeasure <= botLimit){
					hTendency = -1;
				}
			}
			vNeurobitMeasure /= neurobitMeasureSize;
			hNeurobitMeasure /= neurobitMeasureSize;
			
			for(int j = 0; j < GTMeasureSize; j++){
				if(j == GTMeasureSize/2){
					eyeX = Integer.parseInt(GTReader.readLine());
					eyeY = Integer.parseInt(GTReader.readLine());
				}
				else{
					GTReader.readLine();
					GTReader.readLine();
				}
			}
			inputWriter.write(vTendency + " " + vNeurobitMeasure + " " 
			                + hTendency + " " + hNeurobitMeasure);
			outputWriter.write(eyeX + " " + eyeY);
		}
	}
	
	private void close() throws IOException{
		GTReader.close(); 
		verticalReader.close(); 
		horizontalReader.close();
		inputWriter.close(); 
		outputWriter.close();;
	}
	
	public static void main (String [ ] args) throws IOException{
		NeuralMain neural = new NeuralMain();
		neural.synchronize();
		neural.close();
	}
}
