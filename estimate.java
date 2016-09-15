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

public class estimate {

public int[][] orientation(int[][] arra,int width,int height,int block_size,int gabor_size){

	double[][] gx= new double[height+100][width+100];
	double[][] gy= new double[height+100][width+100];
	double[][] Vx= new double[height+100][width+100];
	double[][] Vy= new double[height+100][width+100];
	double[][] theta = new double[height+100][width+100];
	
	double[][] vecx = new double[height+100][width+100];
	double[][] vecy = new double[height+100][width+100];
	
	double[][] vecx_f = new double[height+100][width+100];
	double[][] vecy_f = new double[height+100][width+100];
	double[][] O_f = new double[height+100][width+100];
	double[][] ohm = new double[height+100][width+100];
	double[][] ohm1 = new double[height+100][width+100];
	double[][] freq = new double[height+100][width+100];
	int[][] E = new int[height+100][width+100];
	double[][] gauss = new double[][] {	{14,19,23,24,23,19,14},
					   	{19,23,30,32,30,23,19},
					   	{23,30,36,38,36,30,23},
					   	{24,32,38,40,38,32,24},
					   	{23,30,36,38,36,30,23},
					   	{19,23,30,32,30,23,19},
					   	{14,19,23,24,23,19,14}					  
					  };
	double[][] gauss_new = new double[][] {	{1,1,1},
					   	{1,1,1},
					   	{1,1,1},
					   				  
					  };

	double[][] gauss_lp = new double[][] {	{1,4,7,4,1},
					   	{4,16,26,16,4},
					   	{7,26,41,26,7},
					   	{4,16,26,16,4},
					   	{1,4,7,4,1}
				  
					  };
	double pi = 3.14;
	double max=0;
	l4: for (int r=50;r<height+50;r++) {
	
		l3: for(int c=50;c<width+50;c++) {
		
			gx[r][c]=(arra[r-1][c-1]-arra[r-1][c+1])+ 2*(arra[r][c-1]-arra[r][c+1])+(arra[r+1][c-1]-arra[r+1][c+1]);
			gy[r][c]=(arra[r-1][c-1]-arra[r+1][c-1])+ 2*(arra[r-1][c]-arra[r+1][c])+(arra[r-1][c+1]-arra[r+1][c+1]);
		

		}

	}

/*	 for (int r=50;r<height+50;r++) {
	
		 for(int c=50;c<width+50;c++) {

					int red = img.getRGB(c-50,r-50);
					red=(red & 0x00ff0000) >> 16 ;
					int[] color = new int[] {red,red,red};
					raster.setPixel(c-50,r-50,color);

		}
	}*/

	//////////////////////	GX AND GY ARE FULLY CALCULATED BY NOW 	//////////////////////

	for (int r1=(50+block_size/2);r1<height+(50-block_size/2);r1=r1+block_size) {
		for(int c1=(50+block_size/2);c1<width+(50-block_size/2);c1=c1+block_size) {

			for(int u=r1-block_size/2;u<=r1+block_size/2;u++)
			{	
			  	for(int v=c1-block_size/2;v<=c1+block_size/2;v++)
				{
					Vx[r1][c1]=Vx[r1][c1]+((gx[u][v]*gx[u][v])-(gy[u][v]*gy[u][v]));	
					Vy[r1][c1]=Vy[r1][c1]+(2*gx[u][v]*gy[u][v]);	
					
				}
			
			} 
	
		double an= Math.atan(Vy[r1][c1]/Vx[r1][c1]);

		/*	if(Vx[r1][c1]==0 && Vy[r1][c1]<0)  {theta[r1][c1]=0.7854;
								System.out.println("theta is 45");
							}


			if(Vx[r1][c1]==0 && Vy[r1][c1]>=0) {theta[r1][c1]=2.3562;
								System.out.println("theta is 135");
							}*/
			if(Vx[r1][c1]>0 && Vy[r1][c1]>=0) { 

					theta[r1][c1]=0.5*an;		

					}
			if(Vx[r1][c1]>0 && Vy[r1][c1]<0) { 
			
					theta[r1][c1]=0.5*an; 	

					}

			if(Vx[r1][c1]<0 && Vy[r1][c1]<0) {
		
							theta[r1][c1]=1.57+(0.5*an);

							}

			if(Vx[r1][c1]<0 && Vy[r1][c1]>=0) {
		
							theta[r1][c1]=1.57 + 0.5*(an);

							}
			vecx[r1][c1]=Math.cos(2*theta[r1][c1]);
			vecy[r1][c1]=Math.sin(2*theta[r1][c1]);
			
		
		}	// C1 ENDS

	}	// R1 ENDS

	for (int r2=50+block_size/2;r2<height+50-block_size/2;r2=r2+block_size) {
		for(int c2=50+block_size/2;c2<width+50-block_size/2;c2=c2+block_size) {
			
				double sum_fx=0,sum_fy=0;			
				for(int u2=-1;u2<=1;u2++){
				for(int v2=-1;v2<=1;v2++){
				sum_fx=sum_fx+vecx[r2-(17*u2)][c2-(17*v2)];  
				sum_fy=sum_fy+vecy[r2-(17*u2)][c2-(17*v2)];  

				}
				}

		vecx_f[r2][c2]=0.111*sum_fx;
		vecy_f[r2][c2]=0.111*sum_fy;
		
		double an1=Math.atan(vecy[r2][c2]/vecx[r2][c2]);
			if(vecx[r2][c2]>0 && vecy[r2][c2]>=0) { 
					O_f[r2][c2]=0.5*an1;		

					}
			if(vecx[r2][c2]>0 && vecy[r2][c2]<0) { 
					O_f[r2][c2]=0.5*an1; 	

					}

			if(vecx[r2][c2]<0 && vecy[r2][c2]<0) {
							O_f[r2][c2]=1.57+(0.5*an1);

							}

			if(vecx[r2][c2]<0 && vecy[r2][c2]>=0) {
							O_f[r2][c2]=1.57 + 0.5*(an1);

							}
//	System.out.println(O_f[r2][c2]*180/3.14);

	}	//c2 ends
	}	//r2 ends

////////////////////////////////////////////     Frequency Calculation	/////////////////////////////////////////////////////////////////////////////////////
		
/*		double[] x = new double[32];
		for (int k=0;k<32;k++) {
		int sum_n = 0;
	
		for (int d=0;d<16;d++) {
		int v = c2+(int)((d-8)*(Math.cos(O_f[r2][c2]))) + (int)((k-16)*(Math.sin(O_f[r2][c2])));////mistake ,u r confused in calculating u and v ////
		int u = r2+(int)((d-8)*(Math.sin(O_f[r2][c2]))) + (int)((16-k)*(Math.cos(O_f[r2][c2])));////do////
		sum_n = sum_n + arra[u][v];								
		}
		
		x[k] = 0.0625*sum_n;			//////////	x-signature of each pixel		//////////////    
		}
		
								////why are u calculating avg_pic here,it should be outside the loop////
		
		ohm[r2][c2] = peak_max(x);		/////////	frequency is calculated for each pixel	/////////////
		//System.out.println(ohm[r2][c2]);		
		

	}     // c2 ENDS

	}	// r2 ENDS		
	

/////////////////////////////////	INTERPOLATION BEGINS HERE and new loop started	//////////////////////////////////////////////////////
	int flag=1;	
	
	while(flag==1){
int count1=0;
	for (int i1=58;i1<height+42;i1=i1+17) {
	for (int j1=58;j1<width+42;j1=j1+17) {
		if(ohm[i1][j1] != -1) {ohm1[i1][j1] = ohm[i1][j1];}
			else {	
				double sum1=0,sum2=0;
				count1++;
				for(int u1=-3;u1<=3;u1++){
				for(int v1=-3;v1<=3;v1++){
				sum1=sum1+(0.0012)*gauss[3+u1][3+v1]*mu(ohm[i1-(17*u1)][j1-(17*v1)]);
				sum2=sum2+(0.0012)*gauss[3+u1][3+v1]*del(ohm[i1-(17*u1)][j1-(17*v1)]+1);

				}
				}
			if(sum1==0 && sum2==0) {ohm1[i1][j1]=-1;}
				else ohm1[i1][j1]=(sum1/sum2);
		


	}
//System.out.println(ohm1[i1][j1] + "  "+ohm[i1][j1]);
	}
	}	
	System.out.println(count1);
	l1: for (int i1=58;i1<height+42;i1=i1+17) {
	for (int j1=58;j1<width+42;j1=j1+17) {
	if(ohm1[i1][j1] == -1) {flag=1; break l1;}
				else flag=0;
	}
	}
	if (flag==1){
	System.out.println("freq is -1");
	double[][] a = new double[height][width];				////why not just use ohm=ohm1////
	a=ohm;
	ohm=ohm1;
	ohm1=a;
	}

	}	// while loop ends//

	for (int i4=58;i4<height+42;i4=i4+17) {			////////////////// DOUBT FOR PIXEL(I,J) AND ARRA[I][J] ////////////////// 
		for (int j4=58;j4<width+42;j4=j4+17) {
			double sum_ohm=0;
			for(int u4=-1;u4<=1;u4++) {
			for(int v4=-1;v4<=1;v4++) {
						
				sum_ohm = sum_ohm+0.1111*ohm1[i4-(17*u4)][j4-(17*v4)];
				
			}
			}

	freq[i4][j4]=sum_ohm;
	//System.out.println(freq[i4][j4]);
	}
	}

//////////////////////////////////////////	Frequency Calculation Ends	//////////////////////////////////////////////////////////////////////


*/
	for (int i=(50+block_size/2);i<(height+50-block_size/2);i=i+block_size) {
	for (int j=(50+block_size/2);j<(width+50-block_size/2);j=j+block_size) {
		
		for(int u=-block_size/2;u<=block_size/2;u++){
		for(int v=-block_size/2;v<=block_size/2;v++){
		//	freq[i-u][j-v] = freq[i][j];
			O_f[i-u][j-v] = O_f[i][j];
		//	theta[i-u][j-v] = theta[i][j];
		}
		}
			//System.out.println(freq[i][j]);

	}
	}

///////////////////////////////		final image formation	//////////////

	for (int i5=50;i5<height+30;i5++) {			
		for (int j5=50;j5<width+50;j5++) {
		double sum_e=0;
			for(int u5=-gabor_size/2;u5<=gabor_size/2 ;u5++) {
			for(int v5=-gabor_size/2;v5<=gabor_size/2 ;v5++) {
			
			sum_e = sum_e + h(u5,v5,O_f,freq,i5,j5)*arra[i5-v5][j5-u5];	

			}
			}

	E[i5][j5] = clamp((int)sum_e);
//	int[] color = new int[] {(int)E[i5][j5],(int)E[i5][j5],(int)E[i5][j5]};
//	raster.setPixel(j5-50,i5-50,color); 
	}
	}
	return E;

}	//	orienation() ends O_f


private final int clamp(int c) {
	return (c>255 ? 255:(c < 0 ? 0 : c));
}

public double exp(double x) {
	double sum=0;
	int num=1;
	double p=x;
	for(int i=2;i<4;i++) {
	p=p*x;
	num=num*i;
	sum=sum+(p/num);
	}
	return (1+x+sum);
}

public double cos(double x) {
	double sum=0;
	int num=1;
	double p=1;
	for(int i=2;i<10;i=i+2) {
	p=p*x*x;
	num=num*i*(i-1);
	if((i%4) == 0)	sum=sum+(p/num);
		else	sum=sum-(p/num);

	}
	return (1+sum);

}


public double sin(double x) {
	double sum=0;
	int num=1;
	int flag=1;
	double p=x;
	for(int i=3;i<7;i=i+2) {
	p=p*x*x;
	num=num*i*(i-1);
	if(flag == 0)	{sum=sum+(p/num); flag=1;}
		else	{sum=sum-(p/num); flag=0;}

	}
	return (x+sum);

}

/////////////////////////////////////////////////	gabor filter: h()	///////////////////////////////////////////////////

public double h(int x, int y, double[][] phi, double[][] f,int i,int j) {
	double ret=0,dX=4,dY=4;
	double freq=0;
	double cos = cos(phi[i][j]);
	double sin = sin(phi[i][j]);
	double x_phi =x*cos+ y*sin;
	double y_phi = -x*sin + y*cos;
	double power = -0.5*(((x_phi*x_phi)/(dX*dX)) + ((y_phi*y_phi)/(dY*dY)));
	double phase = 2*3.14*0.155*x_phi;	

	ret = exp(power)*cos(phase);
	return ret;
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
