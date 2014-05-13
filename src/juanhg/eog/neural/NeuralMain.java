package juanhg.eog.neural;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class NeuralMain {
	
	private final String verticalFile = "D:\\lFile.txt";
	private final String horizontalFile = "D:\\uFile.txt";
	private final String GTFile = "D:\\lROI.txt";
	private final String inputs = "D:\\inputs.txt";
	private final String outputs = "D:\\outputs.txt";
	private final int GTMeasureSize = 5;
	private final int neurobitMeasureSize = 5;
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
		double eyeX, eyeY;
		int vTendency, hTendency;
		double vMeasure, hMeasure;
		String aux;
		
		for(int measure = 0; measure < n; ++measure){
			vNeurobitMeasure = 0.0;
			hNeurobitMeasure = 0.0;
			vTendency = hTendency = 0;
			eyeY = eyeX = 0;
			vMeasure = hMeasure = 0;
			
			int x = 0;
			while(x < neurobitMeasureSize){
				aux = verticalReader.readLine();
				if(aux != null){
					vMeasure = Double.parseDouble(aux);
					vNeurobitMeasure += vMeasure;
					x++;
				}
				aux = horizontalReader.readLine();
				if(aux != null){
					hMeasure = Double.parseDouble(aux);
					hNeurobitMeasure += hMeasure;
				}
				
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
			
			int p = 0;
			while(p < GTMeasureSize){
				String aux1 = GTReader.readLine();
				String aux2 = GTReader.readLine();
				if(aux1 != null && aux2 != null){
					p++;
					if(p == GTMeasureSize/2){
						eyeX = Double.parseDouble(aux1);
						eyeY = Double.parseDouble(aux2);
					}
				}
			}
			System.out.println(vTendency + " " + vNeurobitMeasure + " " 
	                + hTendency + " " + hNeurobitMeasure);
			System.out.println(eyeX + " " + eyeY);
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
		neural.genNeuralFiles(10);
		neural.close();
	}
}
