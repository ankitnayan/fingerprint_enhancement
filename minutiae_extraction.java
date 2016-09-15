import java.io.*;
import java.applet.*;
import java.awt.*;
import javax.imageio.*;
import java.awt.image.BufferedImage; 
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException; 
import javax.imageio.ImageIO; 
import java.awt.Graphics;
import java.util.Arrays;
import java.lang.Object;
import java.lang.Math;
import java.lang.*;
import java.lang.reflect.Array;
import java.util.Calendar;
//import org.apache.commons.lang.ArrayUtils;


public class minutiae_extraction extends Applet{

	public static void main(String[] args) {
	BufferedImage img = null; 

	try {
		img = ImageIO.read(new File("gabor_output.bmp"));
		int width = img.getWidth(null);
		int height = img.getHeight(null);

		int[][] img_arr = new int[height][width];
		int[][] arr_bin = new int[height][width];
		int[][] array_thin = new int[height][width];
		int[][] arrthin1 = new int[height][width];
		int[][] arrthin2 = new int[height][width];


		imgoperations operation= new imgoperations();
		help shar = new help();
		

		img_arr=shar.img_arr(img);
		long starttime = System.currentTimeMillis();

		arr_bin=operation.binarizing(img_arr,width,height);
		long endtime = System.currentTimeMillis();
		
		System.out.println("runtime :Binarizing    "+(endtime-starttime)); 
		//long process = System.currentTimeMillis();
		//BufferedImage imgin=operation.inverseimage(imgbin);
		//endtime = System.currentTimeMillis();

		//System.out.println("runtime :Inversion     "+(endtime-starttime));


		long process = System.currentTimeMillis();
		array_thin=operation.thinning(arr_bin,width,height);

		endtime = System.currentTimeMillis();
		System.out.println("runtime :Thinning      "+(endtime-process));
		BufferedImage imgthin = shar.arr_img(array_thin,width,height);
		File outputfile1 = new File("Thinned.bmp");
		ImageIO.write(imgthin, "bmp", outputfile1);


		process = System.currentTimeMillis();

		arrthin1=operation.applydialationsq(array_thin,8,width,height);
		BufferedImage imgthin1 = shar.arr_img(arrthin1,width,height);

		endtime = System.currentTimeMillis();
		System.out.println("runtime :dilation      "+(endtime-process));
		File outputfile2 = new File("dilated.bmp");
		ImageIO.write(imgthin1, "bmp", outputfile2);

		

		process = System.currentTimeMillis();
		
		arrthin2=operation.applyerosionsq(arrthin1,7,width,height);
		BufferedImage imgthin2 = shar.arr_img(arrthin2,width,height);

		endtime = System.currentTimeMillis();
		System.out.println("runtime :erosionsq     "+(endtime-process));
		File outputfile10 = new File("erodedsq.bmp");
		ImageIO.write(imgthin2, "bmp", outputfile10);

	/*	process = System.currentTimeMillis();

		BufferedImage imgthin2=operation.removehole(imgthin2);
//		imgthin2 = shar.arr_img(arrthin2,width,height);

		endtime = System.currentTimeMillis();
		System.out.println("runtime :remove hole   "+(endtime-process));
		
		process = System.currentTimeMillis();

		arrthin2=operation.applyerosiono(arrthin2,3,width,height);
	//	BufferedImage imgthin2 = shar.arr_img(arrthin2,width,height);

		endtime = System.currentTimeMillis();
		System.out.println("runtime :erosion       "+(endtime-process));

		File outputfile = new File("roi11.bmp");
		ImageIO.write(imgthin2, "bmp", outputfile);*/
	/*	int numwhite=operation.normal(imgthin);
		int[][] termitn= new int[numwhite][2];
		int[][] bifurtn= new int[numwhite][2];
		int[][] termnew= new int[numwhite][2];
		int[][] bifurnew= new int[numwhite][2];
		termitn =operation.termination(imgthin);
		bifurtn=operation.bifurcation(imgthin);
		endtime = System.currentTimeMillis();

		System.out.println("runtime"+(endtime-starttime));

		int numtermination=termitn[0][0];
		int numbifurcation=bifurtn[0][0];
		int[][] term=new int[numwhite][4];

		for(int t=1;t<=numtermination;t++){
			term[t][0]=termitn[t][0];
			term[t][1]=termitn[t][1];
		}
		for(int w=1;w<=numbifurcation;w++){
			term[w][2]=bifurtn[w][0];
			term[w][3]=bifurtn[w][1];
		}
		term[0][0]=numtermination;
		term[0][2]=numbifurcation;
		term=operation.removefalse1(term);
		endtime = System.currentTimeMillis();
		
		System.out.println("runtime"+(endtime-starttime));

		int numnewterm=term[0][0];
		int numnewbifur=term[0][2];

		for(int i=1;i<=numnewterm;i++){
			termnew[i][0]=term[i][0];
			termnew[i][1]=term[i][1];
		}
		for(int i=1;i<=numnewbifur;i++){
			bifurnew[i][0]=term[i][2];
			bifurnew[i][1]=term[i][3];
		}
		termnew[0][0]=numnewterm;
		bifurnew[0][0]=numnewbifur;
		termnew=operation.removefalse2(termnew);
		bifurnew=operation.removefalse2(bifurnew);
		endtime = System.currentTimeMillis();

		System.out.println("runtime"+(endtime-starttime));

		int termtrue=termnew[0][0];
		int bifurtrue=bifurnew[0][0];
		termnew=operation.removefalse3(termnew,imgthin2);
		bifurnew=operation.removefalse3(bifurnew,imgthin2);
		int[][] termorient=operation.orientterm(imgthin,termnew);
		int[][] bifurorient=operation.orientbifur(imgthin,bifurnew);
		termorient=operation.removefalse4(termorient);
		 bifurorient=operation.removefalse4(bifurorient);


		 //termtrue=termnew[0][0];
		 //bifurtrue=bifurnew[0][0];
		int termfinal=termorient[0][0];
		int bifurfinal=bifurorient[0][0];
		//System.out.println(termfinal);
		//System.out.println(bifurfinal);
		//for(int i=1;i<=termfinal;i++){
		//System.out.println(Math.round(termorient[i][0])+"\t"+Math.round(termorient[i][1])+"\t"+termorient[i][2]);
		//}
		//for(int i=1;i<=bifurfinal;i++){
		//System.out.println(Math.round(bifurorient[i][0])+"\t"+Math.round(bifurorient[i][1])+"\t"+bifurorient[i][2]);
		//}
		int[][] term1=new int[numwhite][6];
		for(int t=1;t<=termorient[0][0];t++){
			term1[t][0]=termorient[t][0];
			term1[t][1]=termorient[t][1];
			term1[t][2]=termorient[t][2];
		}
		for(int w=1;w<=bifurorient[0][0];w++){
			term1[w][3]=bifurorient[w][0];
			term1[w][4]=bifurorient[w][1];
			term1[w][5]=bifurorient[w][2];
		}
		term1[0][0]=termorient[0][0];
		term1[0][3]=bifurorient[0][0];
		//BufferedImage imgthin5=operation.place1(imgthin,termorient,2);
		// imgthin5=operation.place2(imgthin5,bifurorient,2);
		operation.writefile("James",term1);
		//File outputfile9 = new File("roi.bmp");
		//ImageIO.write(imgthin5, "bmp", outputfile9);
		endtime = System.currentTimeMillis();
		System.out.println("runtime"+(endtime-starttime));*/

	}catch (IOException e) {}

	}
}

