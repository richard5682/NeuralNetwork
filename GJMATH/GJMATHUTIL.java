package GJMATH;

public class GJMATHUTIL {
	public static float sigmoid(float a){
		if(Float.isInfinite(a)){
			if(a>0){
				return 1;
			}else{
				return 0;
			}
		}
		//return ((a/(1+Math.abs(a))+1)/2);
		return (float) (1/(1+Math.exp(-a)));
	}
	public static float RelU(float a){
		if(a < 0){
			return 0;
		}else if(a > 1){
			return 1;
		}
		return a;
	}
	public static float[] SoftMax(float[] values){
		float buffer=0;
		float[] return_buffer = new float[values.length];
		for(int i=0;i<values.length;i++){
			buffer += Math.exp(values[i]);
		}
		for(int i=0;i<values.length;i++){
			return_buffer[i] = (float) (Math.exp(values[i])/buffer);
		}
		return return_buffer;
	}
	public static float GetSoftmaxAdjustment(float zn,float an,float dn,float sum){
		float a = (float) ((sum*Math.exp(an))/Math.pow((sum+Math.exp(an)),2));
		float b = (float) (-(dn-zn)/(Math.log(10)*Math.abs(dn-zn)*(1-Math.abs(dn-zn))));
		float c = (dn-zn)/Math.abs(dn-zn);
		return a*b*c;
	}
}
