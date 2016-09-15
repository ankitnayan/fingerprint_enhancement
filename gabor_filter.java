import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BandCombineOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ByteLookupTable;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
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

public class gabor_filter {
  public static void main(String[] args) {
	BufferedImage img = null; 

	try {	

		img = ImageIO.read(new File("1.bmp"));
		int width = img.getWidth(null);
		int height = img.getHeight(null);
		sharpen_image shar = new sharpen_image();

		int[][] array = new int[height+100][width+100];
		int[][] filter_arr = new int[height+100][width+100];
		int[][] norm_arr = new int[height+100][width+100];
		int[][] sharp_arr = new int[height+100][width+100];
		int[][] clear_arr = new int[height+100][width+100];
		int[][] foreground_arr = new int[height+100][width+100];

	
		/*long process = System.currentTimeMillis();		
		BufferedImage clear = shar.Clear(img);
		long endtime = System.currentTimeMillis();
		File outputfile0 = new File("cleared.bmp");
		ImageIO.write(clear, "bmp", outputfile0);
		System.out.println("runtime:filter   "+(endtime-process));*/

		estimate gf =new estimate();
		array = shar.img_arr(img);
		long process = System.currentTimeMillis();


	//	norm_arr=shar.Normalize(array,width,height);
	//	BufferedImage normalized = shar.arr_img(norm_arr,width,height);
		
	//	File outputfile2 = new File("normalized.bmp");
	//	ImageIO.write(normalized, "bmp", outputfile2);

/*		foreground_arr=shar.check_foreground(norm_arr,13,width,height);
		BufferedImage foreground = shar.arr_img(foreground_arr,width,height);
		File outputfile7 = new File("foreground.bmp");
		ImageIO.write(foreground, "bmp", outputfile7);*/

		clear_arr = shar.Clear(array,17,width,height);
//		BufferedImage clear = shar.arr_img(clear_arr,width,height);
//		File outputfile4 = new File("clear.bmp");
//		ImageIO.write(clear, "bmp", outputfile4);



	/*	sharp_arr=shar.processImage(norm_arr,width,height);
		BufferedImage sharp_norm = shar.arr_img(sharp_arr,width,height);
		
		File outputfile3 = new File("norm&sharp.bmp");
		ImageIO.write(sharp_norm, "bmp", outputfile3);*/




		filter_arr = gf.orientation(clear_arr,width,height,17,7);
		long endtime = System.currentTimeMillis();
		System.out.println("runtime:filter   "+(endtime-process));
		BufferedImage filtered1 = shar.arr_img(filter_arr,width,height);	
		File outputfile1 = new File("Gabor_output.bmp");
		ImageIO.write(filtered1, "bmp", outputfile1);
	



		/*BufferedImage sharpen11 = shar.binarizing(filtered);
		//BufferedImage sharpen1 = shar.inverseimage(sharpen11);
		File outputfile2 = new File("gabor_binar.bmp");
		ImageIO.write(sharpen11, "bmp", outputfile2);		
		
		BufferedImage sharpen2 = shar.binarizing(img);
		File outputfile3 = new File("only_binar.bmp");
		ImageIO.write(sharpen2, "bmp", outputfile3);*/
		
		//BufferedImage fil_sharp = shar.processImage(filtered);
		//File outputfile1 = new File("filtered.bmp");
		//ImageIO.write(filtered, "bmp", outputfile1);

		/*sharpen_new shar = new sharpen_new();
		process = System.currentTimeMillis();
		BufferedImage binarized = shar.binarizing(shar.sharpen(img,1));
		//BufferedImage sharpened = shar.processImage(binarized);
		//endtime = System.currentTimeMillis();
		
		//BufferedImage binarized_after = shar.binarizing(sharpened);
		
		//System.out.println("runtime :sharpen_net    "+(endtime-process));
		
		File outputfile1 = new File("binarized.bmp");
		ImageIO.write(binarized, "bmp", outputfile1);*/
		
		//File outputfile2 = new File("binarized_after.bmp");
		//ImageIO.write(binarized_after, "bmp", outputfile2);
		

	}catch (IOException e) {}

	}
}
