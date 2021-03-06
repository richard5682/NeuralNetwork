package GJNEURAL;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import GJNEURAL.GJNeural.Organism;
import mainlib.test;

public class GJTrainer {
	Organism organism;
	ArrayList<float[]> data_values = new ArrayList<float[]>();
	ArrayList<float[]> data_desired = new ArrayList<float[]>();
	float[][][] values;
	float[][][] desired;
	public boolean Training=true;
	public GJTrainer(Organism organism){
		this.organism = organism;
	}
	public void AddTrainingData(float[] values,float[] desired){
		this.data_values.add(values);
		this.data_desired.add(desired);
	}
	public static float[] ConvertImage(float[][][] image){
		float[] buffervalues = new float[image.length*image[0].length*image[0][0].length];
		for(int v=0,i=0;v<image.length;v++){
			for(int x=0;x<image[0].length;x++){
				for(int y=0;y<image[0][0].length;y++,i++){
					buffervalues[i] = image[v][x][y];
				}
			}
		}
		return buffervalues;
	}
	public float[] ConvertImage(BufferedImage image){
		float[] buffervalues = new float[image.getWidth()*image.getHeight()];
		for(int x=0,i=0;x<image.getWidth();x++){
			for(int y=0;y<image.getHeight();y++,i++){
				buffervalues[i] = new Color(image.getRGB(x, y)).getRed()/255;
			}
		}
		return buffervalues;
	}
	public void Initialize(int no_data){
		int no_sector = (int) Math.ceil(data_values.size()/no_data);
		values = new float[no_sector][no_data][];
		desired = new float[no_sector][no_data][];
		if(data_values.size() < no_data){
			no_data = data_values.size();
		}
		Random r = new Random();
		int i=0,v=0;
		while(data_values.size()>0){
			int index = r.nextInt(data_values.size());
			values[i][v] = data_values.get(index);
			desired[i][v] = data_desired.get(index);
			data_values.remove(index);
			data_desired.remove(index);
			v++;
			if(v == no_data){
				v=0;
				i++;
			}
		}
	}
	public void Shuffle(){
		Random r = new Random();
		int index0 = r.nextInt(values.length);
		int index1 = r.nextInt(values[0].length);
		float[] buffer_value = values[index0][index1];
		float[] buffer_desired = desired[index0][index1];
		if(index0+1==values.length){
			index0 = 0;
		}
		values[index0][index1] = buffer_value;
	}
	float minimum_fitness = 10;
	public void Train(){
		int v=0;
		int minimacounter = 0;
		final int minima_thresh = 100;
		long start = System.currentTimeMillis();
		while(Training){
			v++;
			for(int i=0;i<values.length;i++){
				organism.Train(values[i], desired[i]);
			}
			for(int i=0;i<values[0].length;i++){
				this.Shuffle();
			}
			if(organism.neuralnetwork.fitness < 0.04f && organism.neuralnetwork.fitness-organism.neuralnetwork.past_fitness > 0){
				minimacounter++;
			}else{
				minimacounter--;
				if(minimacounter < 0){
					minimacounter = 0;
				}
			}
			if(minimacounter > minima_thresh){
				System.out.print("Fitness : "+organism.neuralnetwork.fitness);
				Training=false;
			}
			if(v > 10){
				v=0;
				this.organism.neuralnetwork.ReduceLearningRate();
				System.out.println("Fitness : "+organism.neuralnetwork.fitness);
				if(organism.neuralnetwork.fitness < this.minimum_fitness){
					this.minimum_fitness = organism.neuralnetwork.fitness;
				}
				System.out.println("Minimum Fitness : "+this.minimum_fitness);
				System.out.println(" DIFFERENCE : "+(organism.neuralnetwork.fitness-organism.neuralnetwork.past_fitness));
				System.out.println("Learning Rate : "+organism.neuralnetwork.learningrate);
				long end = System.currentTimeMillis();
				System.out.println("ELAPSED TIME : "+(end-start));
				start = System.currentTimeMillis();
			}
		}
	}
	public void StopTraining(){
		Training = false;
	}
}
