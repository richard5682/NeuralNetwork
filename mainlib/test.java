package mainlib;


import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.lang.Object;

import GJFILE.GJFileLoader;
import GJMATH.GJMATHUTIL;
import GJNEURAL.GJNeural;
import GJNEURAL.GJNeural.NeuralNetwork;
import GJNEURAL.GJNeural.Organism;
import GJNEURAL.GJTrainer;
import GJNEURAL.GJTrainerUtil;
import GJNEURAL.GJTrainerUtil.Filter;
import GJSWING.GJAction;
import GJSWING.GJButton;
import GJSWING.GJGraphics;
import GJSWING.GJText;
import GJSWING.GJWindow;
public class test {
	
	public static GJWindow window1 = new GJWindow(600,500,Color.black,new render1());
	public static GJButton button1 = new GJButton(0,0,"PLAY",Color.green,new buttonAction1());
	public static GJButton button2 = new GJButton(0,50,"STOP",Color.red,new buttonAction2());
	public static GJButton button3 = new GJButton(0,100,"TEST",Color.gray,new buttonAction3());
	public static GJButton button4 = new GJButton(0,150,"SAVE",Color.CYAN,new buttonAction4());
	public static GJButton button5 = new GJButton(0,200,"LOAD",Color.CYAN,new buttonAction5());
	public static GJButton button6 = new GJButton(0,350,"<",Color.RED,new buttonAction6());
	public static GJButton button7 = new GJButton(0,400,">",Color.RED,new buttonAction7());
	public static GJText text1 = new GJText(100,400,485,70,Color.white,Color.black);
	public static GJText text2 = new GJText(0,300,100,50,Color.white,Color.black);
	static String DIRECTORY = "C:\\Users\\GJ TECSON\\Desktop\\Training Data";
	static String SAMPLE = "C:\\Users\\GJ TECSON\\Desktop\\Training Data\\Sample\\sample.png";
	
	static GJNeural.Organism organism1;
	
	static float[][] verticalfilter = {{1,0,-1},{1,0,-1},{1,0,-1}};
	static float[][] horizontalfilter = {{1,1,1},{0,0,0},{-1,-1,-1 }};
	static float[][] verticalfilter1 = {{-1,0,1},{-1,0,1},{-1,0,1}};
	static float[][] horizontalfilter1 = {{-1,-1,-1},{0,0,0},{1,1,1 }};
	static float[][] leftdiagonalfilter = {{1,-1.2f,-1.2f},{0,1,-1.2f},{0,0,1}};
	static float[][] rightdiagonalfilter = {{-1.2f,-1.2f,1},{-1.2f,1,0},{1,0,0}};
	
	static float[][] thicknessfilter = {{0,1,0},{1,1,1},{0,1,0}};
	
	static Filter VERTICALFILTER = new Filter(verticalfilter,0f);
	static Filter HORIZONTALFILTER = new Filter(horizontalfilter,0f);
	static Filter VERTICALFILTER1 = new Filter(verticalfilter1,0f);
	static Filter HORIZONTALFILTER1 = new Filter(horizontalfilter1,0f);
	
	static Filter LEFTDIAGONALFILTER = new Filter(leftdiagonalfilter,0);
	static Filter RIGHTDIAGONALFILTER = new Filter(rightdiagonalfilter,0f);
	
	static Filter DIAGONALTHICKNESSFILTER = new Filter(thicknessfilter,-1f);
	static Filter LATERALTHICKNESSFILTER = new Filter(thicknessfilter,-1.5f);
	
	static float[][] desired_output = {
			{1f,0f,0f,0f,0f,0f,0f,0f,0f,0f},
			{0f,1f,0f,0f,0f,0f,0f,0f,0f,0f},
			{0f,0f,1f,0f,0f,0f,0f,0f,0f,0f},
			{0f,0f,0f,1f,0f,0f,0f,0f,0f,0f},
			{0f,0f,0f,0f,1f,0f,0f,0f,0f,0f},
			{0f,0f,0f,0f,0f,1f,0f,0f,0f,0f},
			{0f,0f,0f,0f,0f,0f,1f,0f,0f,0f},
			{0f,0f,0f,0f,0f,0f,0f,1f,0f,0f},
			{0f,0f,0f,0f,0f,0f,0f,0f,1f,0f},
			{0f,0f,0f,0f,0f,0f,0f,0f,0f,1f}};
//	static float[][] desired_output = {{1f,0f,0f},{0f,1f,0f},{0f,0f,1f}};
	static GJTrainer trainer;
	
	static boolean UPDATEGUI = true;

	@SuppressWarnings("deprecation")
	public static void main(String args[]){
		window1.AddComponent(button1);
		window1.AddComponent(button2);
		window1.AddComponent(button3);
		window1.AddComponent(button4);
		window1.AddComponent(button5);
		window1.AddComponent(button6);
		window1.AddComponent(button7);
		window1.AddComponent(text1);
		window1.AddComponent(text2);
		window1.render();
		
		window1.show();
		int[] no_layernode = {32,32};
		organism1 = new GJNeural.Organism(2*15*15,no_layernode,no_layernode.length,10,0.001f);
		trainer = new GJTrainer(organism1);
		LoadTrainingData(false);
		text2.ChangeString(Float.toString(organism1.neuralnetwork.learningrate));
		trainer.Initialize(50);
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			trainer.Train();
		}
//		float[] input = {0f,1f,1f,0f,1f};
//		float[] desired = {1f,0f,1f,0f,1f};
//		float[][] tvalues = new float[1][];
//		float[][] tdesired = new float[1][];
//		tvalues[0] = input;
//		tdesired[0] = desired;
//		System.out.println(" Input Data : ");
//		for(int i=0;i<input.length;i++){
//			System.out.print(input[i]+", ");
//		}
//		System.out.println("");
//		System.out.println(" Desired Output Data : ");
//		for(int i=0;i<input.length;i++){
//			System.out.print(desired[i]+", ");
//		}
//		System.out.println("");
//		System.out.println("=====RESULT====");
//		System.out.println("First Training");
//		organism1.ComputeOutput(input);
//		organism1.PrintOutput();
//		for(int i=0;i<100000;i++){
//			organism1.Train(tvalues, tdesired);
//		}
//		System.out.println("Last Training");
//		organism1.ComputeOutput(input);
//		organism1.PrintOutput();
	}
	static void LoadTrainingData(boolean doubleprocess){
		int i=0;
		File f = new File(DIRECTORY+"//"+i);
		while(f.exists()){
			int v=0;
			f = new File(DIRECTORY+"//"+i+"//"+v+".png");
			while(f.exists()){
				float[][] raw_image = GJTrainerUtil.CenterImage(GJFileLoader.LoadImage(DIRECTORY+"//"+i+"//"+v+".png"));
				float[][] image = GJTrainerUtil.MaxPooling(GJTrainerUtil.FilterImage(VERTICALFILTER, raw_image));
				float[][] image1 = GJTrainerUtil.MaxPooling(GJTrainerUtil.FilterImage(HORIZONTALFILTER, raw_image));
//				float[][] image2 = GJTrainerUtil.MaxPooling(GJTrainerUtil.FilterImage(VERTICALFILTER1, raw_image));
//				float[][] image3 = GJTrainerUtil.MaxPooling(GJTrainerUtil.FilterImage(HORIZONTALFILTER1, raw_image));
//				float[][] image2 = GJTrainerUtil.MeanPooling(GJTrainerUtil.FilterImage(LEFTDIAGONALFILTER, raw_image));
//				float[][] image3 = GJTrainerUtil.MeanPooling(GJTrainerUtil.FilterImage(RIGHTDIAGONALFILTER, raw_image));
				if(doubleprocess){
					image = GJTrainerUtil.FilterImage(LATERALTHICKNESSFILTER, image);
					image1 = GJTrainerUtil.FilterImage(LATERALTHICKNESSFILTER, image1);
//					image2 = GJTrainerUtil.FilterImage(DIAGONALTHICKNESSFILTER, image2);
//					image3 = GJTrainerUtil.FilterImage(DIAGONALTHICKNESSFILTER, image3);
				}
				
//				float[][][] images = {image,image1,image2,image3};
				float[][][] images = {image,image1};
				trainer.AddTrainingData(trainer.ConvertImage(images)
						,desired_output[i]);
				v++;
				f = new File(DIRECTORY+"//"+i+"//"+v+".png");
			}
			i++;
			f = new File(DIRECTORY+"//"+i);
		}
	}
	static class render1 extends GJGraphics{
		float[][] raw_image;
		float[][] image;
		float[][] image1;
		float[][] image2;
		float[][] image3;
		public void draw(Graphics2D g2d) {
			if(UPDATEGUI){
				UPDATEGUI = false;
				raw_image = GJTrainerUtil.CenterImage(GJFileLoader.LoadImage(SAMPLE));
				image = GJTrainerUtil.MaxPooling(GJTrainerUtil.FilterImage(VERTICALFILTER, raw_image));
				image1 = GJTrainerUtil.MaxPooling(GJTrainerUtil.FilterImage(HORIZONTALFILTER, raw_image));
//				image2 = GJTrainerUtil.MaxPooling(GJTrainerUtil.FilterImage(VERTICALFILTER1, raw_image));
//				image3 = GJTrainerUtil.MaxPooling(GJTrainerUtil.FilterImage(HORIZONTALFILTER1, raw_image));
				
//				float[][][] images = {image,image1,image2,image3};
				float[][][] images = {image,image1};;
				test.organism1.ComputeOutput(GJTrainer.ConvertImage(images));
				test.organism1.neuralnetwork.output_layer.printValues();
				float value=0;
				int index=0;
				for(int i=0;i<test.organism1.neuralnetwork.output_layer.nodes.length;i++){
					float buffer = test.organism1.neuralnetwork.output_layer.nodes[i].value;
					if(buffer > value){
						value = buffer;
						index = i;
					}
				}
				System.out.println(index);
				text1.ChangeString("ITS "+index);
			}
			drawPicture(raw_image,4,20,120,g2d);
			drawPicture(image,10,170,40,g2d);
			drawPicture(image1,10,330,40,g2d);
//			drawPicture(image2,10,170,200,g2d);
//			drawPicture(image3,10,330,200,g2d);
		}
		public void drawPicture(float[][] image,int pwidth,int xc,int yc,Graphics2D g2d){
			
			for(int x=0;x<image.length;x++){
				for(int y=0;y<image[0].length;y++){
					g2d.setColor(Color.blue);
					int color = (int) Math.floor(image[x][y]*255);
					g2d.setColor(new Color(255-color,255-color,255-color));
					g2d.fillRect(xc+(x*pwidth), yc+(y*pwidth), pwidth, pwidth);
				}
			}
			if(pwidth > 8){
				g2d.setColor(Color.red);
				for(int x=0;x<image.length+1;x++){
					g2d.drawLine(xc+x*pwidth, yc, xc+x*pwidth, yc+image[0].length*pwidth);
					g2d.drawLine(xc+x*pwidth+1, yc, xc+x*pwidth+1, yc+image[0].length*pwidth);
				}
				for(int y=0;y<image[0].length+1;y++){
					g2d.drawLine(xc, yc+y*pwidth, xc+image.length*pwidth, yc+y*pwidth);
					g2d.drawLine(xc, yc+y*pwidth+1, xc+image.length*pwidth, yc+y*pwidth+1);
				}
			}
			
		}
	}
	public static class buttonAction1 implements GJAction{

		@Override
		public void Clicked() {
			// TODO Auto-generated method stub
			trainer.Training = true;
		}

		@Override
		public void Pressed() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void Release() {
			// TODO Auto-generated method stub
			
		}
		
	}
	public static class buttonAction3 implements GJAction{

		@Override
		public void Clicked() {
			// TODO Auto-generated method stub
			
			UPDATEGUI = true;
			window1.render();
		}

		@Override
		public void Pressed() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void Release() {
			// TODO Auto-generated method stub
			
		}
		
	}
	public static class buttonAction2 implements GJAction{

		@Override
		public void Clicked() {
			// TODO Auto-generated method stub
			trainer.Training = false;
		}

		@Override
		public void Pressed() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void Release() {
			// TODO Auto-generated method stub
			
		}
		
	}
	public static class buttonAction4 implements GJAction{

		@Override
		public void Clicked() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void Pressed() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void Release() {
			// TODO Auto-generated method stub
			
		}
		
	}
	public static class buttonAction5 implements GJAction{

		@Override
		public void Clicked() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void Pressed() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void Release() {
			// TODO Auto-generated method stub
			
		}
		
	}
	public static class buttonAction6 implements GJAction{

		@Override
		public void Clicked() {
			// TODO Auto-generated method stub
			organism1.ScaleLearningRate(0.5f);
			text2.ChangeString(Float.toString(organism1.neuralnetwork.learningrate));
		}

		@Override
		public void Pressed() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void Release() {
			// TODO Auto-generated method stub
			
		}
		
	}
	public static class buttonAction7 implements GJAction{

		@Override
		public void Clicked() {
			// TODO Auto-generated method stub
			organism1.ScaleLearningRate(2f);
			text2.ChangeString(Float.toString(organism1.neuralnetwork.learningrate));
		}

		@Override
		public void Pressed() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void Release() {
			// TODO Auto-generated method stub
			
		}
		
	}
}


