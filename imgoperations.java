import java.io.*;
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

public class imgoperations{
	


public void writefile(String s, int[][] array){
	System.out.println("inside write file");
	File outputfile = new File(s+".txt");
	Calendar cal = Calendar.getInstance();
	try{
		BufferedWriter writer1=new BufferedWriter(new FileWriter(s+"_"+cal.getTime()+".txt",true));
	 	writer1.write("------------------------------------------------------------\nName: " +s+"\nDate: "+cal.get(Calendar.YEAR)+"-"+(cal.get		(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE)+"\nNumber of Terminations: "+array[0][0]+"\nNumber of Bifurcations: "+array[0][3]+"\n------------------------------------------------------------\n------------------------------------------------------------\nTerminations:\n------------------------------------------------------------\nX\tY\tAngle\n");
	
	for(int i=1;i<=array[0][0];i++){
		writer1.write(array[i][0]+"\t"+array[i][1]+"\t"+array[i][2]+"\n");
	}

	writer1.write("------------------------------------------------------------\nBifurcations:\n------------------------------------------------------------\nX\tY\tAngle\n");

	for(int i=1;i<=array[0][3];i++){
		writer1.write(array[i][3]+"\t"+array[i][4]+"\t"+array[i][5]+"\n");
	}
	
	writer1.close();
	}

	catch (IOException e) {
	}


}

public int[][] removefalse4(int[][] array){

	int num=array[0][0];
	help help1=new help();

	f2:for(int i=1;i<=num;i++){
		if(array[i][2]==256){
			array=help1.remove3(array,i);
			array=removefalse4(array);
			break f2;
		}
	}

	return array;
}

public int[][] orientterm(BufferedImage image,int[][] term){

	int[] angle=new int[15];
	help help1=new help();
	int[][] terminations=new int[term[0][0]+1][3];
	terminations[0][0]=term[0][0];

	for(int i=1;i<=term[0][0];i++){
		terminations[i][0]=term[i][0];
		terminations[i][1]=term[i][1];
		angle=help1.angles(image,term[i][0],term[i][1]);
		if(angle[0]==1) terminations[i][2]=angle[1];
		else terminations[i][2]=256;

	}

	return terminations;
}

public int[][] orientbifur(BufferedImage image,int[][] term){
	int[] angle=new int[15];
	help help1=new help();
	int[][] terminations=new int[term[0][0]+1][3];
	terminations[0][0]=term[0][0];

	for(int i=1;i<=term[0][0];i++){
		terminations[i][0]=term[i][0];
		terminations[i][1]=term[i][1];
		angle=help1.angles(image,term[i][0],term[i][1]);
		if(angle[0]==3){ 
			if((angle[2]-angle[1]<=65)) terminations[i][2]=angle[3];
			else if((angle[3]-angle[2]<=65)) terminations[i][2]=angle[1];
			else if((256+angle[1]-angle[3]<=65)) terminations[i][2]=angle[2];
			else terminations[i][2]=256;
			}

		else terminations[i][2]=256;
		//System.out.println("the");}
	}

	return terminations;
}

private int maxAround(int[][] array, int row, int col,int radius) {
               
		 int maxR = 0;

		help help1= new help();
               // int radius2 = radius * radius;
               f1: for (int dRow = -radius/2; dRow <= radius/2; dRow++) {
                        for (int dCol = -radius/2; dCol <= radius/2; dCol++) {
                                //if (dRow * dRow + dCol * dCol <= radius2) {
                                      if(maxR> array[row + dRow][col + dCol]) maxR=maxR;
						else maxR=array[row + dRow][col + dCol];
					if(maxR==255)
					break f1;
					
                        }
                }
	
         return (maxR << 16) | (maxR << 8) | maxR;
}

private int maxAround2(BufferedImage image, int row, int col,int radius) {
                int maxR = 0;
               // int maxG = 0;
               // int maxB = 0;
		help help1= new help();
                int radius2 = radius * radius;
                for (int dRow = -radius; dRow <= radius; dRow++) {
                        for (int dCol = -radius; dCol <= radius; dCol++) {
                                if (dRow * dRow + dCol * dCol <= radius2) {
                                        int r = help1.getred(image,col + dCol, row + dRow);
					//int b = help1.getblue(image,col + dCol, row + dRow);
					//int g = help1.getgreen(image,col + dCol, row + dRow);
                                        maxR = Math.max(maxR, r);
                                        //maxG = Math.max(maxG, b);
                                        //maxB = Math.max(maxB, g);
                                }
                        }
                }
                return (maxR << 16) | (maxR << 8) | maxR;
        }
       // @Override

public int[][] applydialationsq(int[][] array,int radius,int width,int height) {
                
		int[][] array1 = new int[height][width];
                
                for (int row = 4; row < height-4; row++) {
                        for (int col = 4; col < width-4; col++) {
                                array1[row][col]= maxAround(array, row, col,radius);
                        }
                }
                
                return array1;
}
public BufferedImage dilate(int[][] image,BufferedImage imag){
		long process = System.currentTimeMillis();
 		int width = imag.getWidth();
                int height = imag.getHeight();
 BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

    for (int i=0; i<height; i++){
        for (int j=0; j<width; j++){
            if (image[i][j] == 1){
                if (i>0 && image[i-1][j]==0) image[i-1][j] = 2;
                if (j>0 && image[i][j-1]==0) image[i][j-1] = 2;
                if (i+1<height && image[i+1][j]==0) image[i+1][j] = 2;
                if (j+1<width && image[i][j+1]==0) image[i][j+1] = 2;
            }
        }
    }
    for (int i=0; i<height; i++){
        for (int j=0; j<width; j++){
	 if (image[i][j] == 1) img.setRGB(j,i,(255<<16|255<<8|255));
	 if (image[i][j] == 0) img.setRGB(j,i,0);
         if (image[i][j] == 2){
                image[i][j] = 1;
		img.setRGB(j,i,(255<<16|255<<8|255));
            }
        }
    }

		
		//BufferedImage imgthin1=operation.applydialationsq(imgthin,8);
		//BufferedImage imgthin1=operation.dilate(imgthin);
		long endtime = System.currentTimeMillis();
		System.out.println("runtime :dilation      "+(endtime-process));
		try{File outputfile2 = new File("dilated.bmp");
		ImageIO.write(img, "bmp", outputfile2);}catch (IOException e) {}
    return img;
}


private int minAround(int[][] array, int row, int col,int radius,int width,int height) {
                int maxR =255;

               f2: for (int dRow = -radius/2; dRow <= radius/2; dRow++) {
                        for (int dCol = -radius/2; dCol <= radius/2; dCol++) {

					if((col + dCol)<width && (row+dRow)<height && array[row+dRow][col+dCol]==0) {maxR=0;break f2;}
	
                        }
                }
	
        return (maxR << 16) | (maxR << 8) | maxR;
}

 private int minAround2(int[][] array, int row, int col,int radius) {
                int maxR =255;

		help help1= new help();
                int radius2 = radius * radius;
                for (int dRow = -radius; dRow <= radius; dRow++) {
                        for (int dCol = -radius; dCol <= radius; dCol++) {
                                if (dRow * dRow + dCol * dCol <= radius2) {
                                       if(array[row+dRow][col+dCol]>maxR) maxR=maxR;
						else maxR=array[row+dRow][col+dCol];

                                }
                        }
                }
                return (maxR << 16) | (maxR << 8) | maxR;
}
       // @Override

public int[][] applyerosionsq(int[][] array,int radius,int width,int height) {

		int[][] array1 = new int[height][width];
                
                for (int row = 0; row < height; row++) {
                      f1:  for (int col = 0; col < width; col++) {

				if (array[row][col]==0) array1[row][col]=0;
				else array1[row][col]= minAround(array, row, col,radius,width,height);
                                
                        }
                }
                
                return array1;
}

public int[][] applyerosiono(int[][] array,int radius,int width,int height) {
 
                int[][] array1 = new int[height][width];
             
                for (int row = radius; row < height-radius; row++) {
                        for (int col = radius; col < width-radius; col++) {
			
                               array1[row][col]= minAround2(array, row, col,radius);
                        }
                }
                
                return array1;
}

public int[][] removefalse1(int[][] term){
	int numterm=term[0][0];
	int numbifur=term[0][2];
	int[] termx=new int[numterm];
	int[] termy=new int[numterm];
	int[] bifurx=new int[numbifur];
	int[] bifury=new int[numbifur];

	imgoperations operation=new imgoperations();
	help help1=new help();
	for(int i=0;i<numterm;i++){
		termx[i]=term[i+1][0];
		termy[i]=term[i+1][1];
	}

	for(int i=0;i<numbifur;i++){
		bifurx[i]=term[i+1][2];
		bifury[i]=term[i+1][3];
	}

	process:

	for(int j=0;j<numbifur;j++){
		for(int i=0;i<numterm;i++){
			if(((termx[i]-bifurx[j])*(termx[i]-bifurx[j])+(termy[i]-bifury[j])*(termy[i]-bifury[j]))<36)
			{
				termx= help1.remove(termx,i);
				termy= help1.remove(termy,i); 
				bifurx= help1.remove(bifurx,j);
				bifury= help1.remove(bifury,j);
				numterm=numterm-1;
				numbifur=numbifur-1;

				for(int t=0;t<numterm;t++){
					term[t+1][0]=termx[t];
					term[t+1][1]=termy[t];
				}
				for(int w=0;w<numbifur;w++){
					term[w+1][2]=bifurx[w];
					term[w+1][3]=bifury[w];
				}

				term[0][0]=numterm;
				term[0][2]=numbifur;
				term=operation.removefalse1(term);
				break process;
			}

		}

	}
	
	return term;

}

public int[][] removefalse2(int[][] term){
	int numterm=term[0][0];
	imgoperations operation=new imgoperations();
	help help1=new help();
	process:

	for(int i=1;i<=numterm;i++){
		for(int j=1;j<=numterm;j++){
			if((((term[i][0]-term[j][0])*(term[i][0]-term[j][0])+(term[i][1]-term[j][1])*(term[i][1]-term[j][1]))<36)&&(((term[i][0]-term[j][0])*(term[i][0]-term[j][0])+(term[i][1]-term[j][1])*(term[i][1]-term[j][1]))!=0))
			{
				 term= help1.remove2(term,i);
				if(i>j){ 
					term= help1.remove2(term,j);
				}
				if(i<j){ 
					term= help1.remove2(term,j-1);
				} 
				term=operation.removefalse2(term);
				break process;
			}

		}

	}
	return term;

}

public int[][] removefalse3(int[][] term,BufferedImage roi){
	int numterm=term[0][0];
	imgoperations operation=new imgoperations();
	help help1=new help();
	process:

	for(int i=1;i<=numterm;i++){

		int clr=  roi.getRGB(term[i][0],term[i][1]); 
		int  red   = (clr & 0x00ff0000) >> 16;
		if(red==0)
		{
			term= help1.remove2(term,i);

			term=operation.removefalse3(term,roi);
			break process;
		}

	}


	return term;

}

public BufferedImage removehole(BufferedImage img){
help help1=new help();
int[][] array=help1.holearray(img);
int width = img.getWidth(null);
int height = img.getHeight(null);
BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
image=img;
    WritableRaster raster = image.getRaster(); 
    int[] white = new int[]{255,255,255};
int m,t,i;
for(int j=0;j<height;j++){
m=array[j][0];
if(m>2){
for(t=2;t<m;){
for(i=array[j][t];i<array[j][t+1];i++){
raster.setPixel(i,j,white);
}
t=t+2;
}
}
}
return image;
}


public BufferedImage place1(BufferedImage imgin,int[][] array,int radius){

	WritableRaster raster = imgin.getRaster(); 
	int radius2=radius*radius;
	int[] red = new int[]{255,0,0};  
	int t=array[0][0];
	for(int d=1;d<=t;d++){
		for (int dRow = -radius; dRow <= radius; dRow++) {
				        for (int dCol = -radius; dCol <= radius; dCol++) {
				                if (dRow * dRow + dCol * dCol <= radius2) {
							raster.setPixel(array[d][0]+dCol,array[d][1]+dRow,red);
						}
					}
		}	
	}

	return imgin;
}

public BufferedImage place2(BufferedImage imgin,int[][] array,int radius){

	WritableRaster raster = imgin.getRaster(); 
	int radius2=radius*radius;
	int[] red = new int[]{0,255,0};  
	int t=array[0][0];
	for(int d=1;d<=t;d++){
		for (int dRow = -radius; dRow <= radius; dRow++) {
				        for (int dCol = -radius; dCol <= radius; dCol++) {
				                if (dRow * dRow + dCol * dCol <= radius2) {
							raster.setPixel(array[d][0]+dCol,array[d][1]+dRow,red);
						}
					}
		}
	}

	return imgin;
}
public BufferedImage Normalize(BufferedImage img,int[][] array) {
	int width = img.getWidth(null);
	int height = img.getHeight(null);
	BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	WritableRaster raster = image.getRaster(); 
	int i,j;
	float mean1 = mean(array,width,height); 
	float variance1 = variance(img,mean1);
	for(i=0;i<width;i++){
		for(j=0;j<height;j++){
			int clr=  img.getRGB(i,j); 
			int  red   = (clr & 0x00ff0000) >> 16;
			float var0;
			if (variance1<1000) var0=1000; else var0=variance1;
			double d = Math.sqrt((var0*(red-mean1)*(red-mean1))/variance1);
			int money = (int)d;
			
			if (red>mean1) red = 150+money;
				else red = 150-money;
				
			int[] colour = new int[]{red,red,red};	
				raster.setPixel(i,j,colour);  
			//System.out.println("money:  "+money);	

		}
	}
		//System.out.println("mean:  "+mean1);
		//System.out.println("variance:  "+variance1);
	return image;


}
public float mean(int[][] array,int width,int height) {

	int sum=0;
	for(int i=0;i<width;i++){
		for(int j=0;j<height;j++){

				sum = sum+array[j][i];
		}
	}
	float mean=sum/(width*height);
	return mean;
}

public float variance(BufferedImage img,float mean) {
	int width = img.getWidth(null);
	int height = img.getHeight(null);
	int i,j;
	float sum=0;
	for(i=0;i<width;i++){
		for(j=0;j<height;j++){
			int clr=  img.getRGB(i,j); 
			    int  red   = (clr & 0x00ff0000) >> 16;
				sum = sum+((red-mean)*(red-mean));
		}
	}
	float var=sum/(width*height);
	return var;
}

private final int clamp(int c) {
	return (c>255 ? 255:(c < 0 ? 0 : c));
}


public BufferedImage sharpen(BufferedImage img,int max) {
	int r0=0;
	int width = img.getWidth(null);
	int height = img.getHeight(null);
	BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	WritableRaster raster = image.getRaster();
	int i=0,j=0;
	for (int run=1;run<=max;run++){
	for(i=1;i<width-1;i++){
		for(j=1;j<height-1;j++){
			int rs=0;
			for (int k=-1;k<=1;k++){
			 for (int p=-1;p<=1;p++){

				int clr=  img.getRGB(i+k,j+p); 
				int r = (clr & 0x00ff0000) >> 16;
				if(p==0 && k==0) r0=r; else rs=rs+r;
			
			 }
			}

	rs = (int)(rs/8);
	int red = clamp(r0+(r0-rs));
	int[] colour = new int[]{red,red,red};	
	raster.setPixel(i,j,colour);

		}
	}
}
	return image;
}

public int[][] binarizing(int[][] array,int width,int height){

	float mean1 = mean(array,width,height);
	for(int i=0;i<width;i++){
		for(int j=0;j<height;j++){

			    if (array[j][i]>mean1) array[j][i]=255; 
			    else array[j][i]=0; 
		}
	}

	return array;
}

public BufferedImage inverseimage(BufferedImage imgin){

    int width = imgin.getWidth(null);
    int height = imgin.getHeight(null);
    BufferedImage imagein = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    WritableRaster raster = imagein.getRaster(); 
    int[] black = new int[]{0,0,0};
    int[] white = new int[]{255,255,255};  
    int i,j;


    for(i=0;i<width;i++){
		for(j=0;j<height;j++){

		    int clr=  imgin.getRGB(i,j); 
		    int red= (clr & 0x00ff0000) >> 16;
		    if(red>0) raster.setPixel(i,j,black);
		    else raster.setPixel(i,j,white);
		}
	}

    return imagein;
}


public int[][] thinning(int[][] arra,int width,int height){

  
	help help1= new help();

	int[][] array = new int[height][width];
	int[][] arraythin = new int[height][width];
	
	array = arra;


	for(int g=1;g<=5;g++){ 
//long starttime = System.currentTimeMillis();
		for(int j=1;j<height-1;j++){
			for(int i=1;i<width-1;i++){
				int[] neigs = new int[10];
				
				neigs=help1.neighbours(array,i,j);

				if(neigs[0]==1)
				{
					int g3=help1.condn3(neigs);
					if(g3==1)
					{
						int g2=help1.condn2(neigs);
						if(g2==1) {
							int g1=help1.condn1(neigs);
							if(g1==1) arraythin[j][i]=0;
								else arraythin[j][i]=255;
						} else arraythin[j][i]=255;

					} else arraythin[j][i]=255;
				}
				else arraythin[j][i]=0;
			}
		}
				//long endtime = System.currentTimeMillis();
				//System.out.println("neighbour  "+(endtime-starttime)); 
		for(int j=1;j<height-1;j++){
			for(int i=1;i<width-1;i++){
				int[] neigs = new int[10];
				neigs=help1.neighbours(arraythin,i,j);

				if(neigs[0]==1)
				{
					int g3dash=help1.condn3dash(neigs);
					if(g3dash==1)
					{
						int g2=help1.condn2(neigs);
						if(g2==1) {
							int g1=help1.condn1(neigs);
							if(g1==1) array[j][i]=0;
								else array[j][i]=255;
						} else array[j][i]=255;

					} else array[j][i]=255;
				 

				}
				else array[j][i]=0;}}  
	} 

	return array;

}

/*public int[][] termination(BufferedImage imgin){
 
	imgoperations operation= new imgoperations();
	int numwhite=operation.normal(imgin);
	int[][] term = new int[numwhite][2]; 
	int width = imgin.getWidth(null);
	int height = imgin.getHeight(null);
	help help1= new help();
	int t=0;

	for(int i=0;i<width;i++){
		for(int j=0;j<height;j++){
			int [] neigs=help1.neighbours(imgin,i,j);
			if(neigs[0]==1){
				 int m= help1.numneighone(neigs);
				 if(m==1) {
					    t=t+1;
					    term[t][0]=i;
					    term[t][1]=j;
				}
			}
		}
	}

	term[0][0]=t;
	return term;

}

public int[][] bifurcation(BufferedImage imgin){
	imgoperations operation= new imgoperations();
	int numwhite=operation.normal(imgin); 
	int[][] term = new int[numwhite][2];
	int width = imgin.getWidth(null);
	int height = imgin.getHeight(null);
	help help1= new help();
	int t=0;

	for(int i=0;i<width;i++){
		for(int j=0;j<height;j++){
			int [] neigs=help1.neighbours(imgin,i,j);
			if(neigs[0]==1){
				int m= help1.numneighone(neigs);
				if(m==3) {
					t=t+1;
					term[t][0]=i;
					term[t][1]=j;
				}
			}
		}
	}
	
	term[0][0]=t;
	return term;

}

public int normal(BufferedImage imgin){
	int t=0; 
	int width = imgin.getWidth(null);
	int height = imgin.getHeight(null);
	help help1= new help();

	for(int i=0;i<width;i++){
		for(int j=0;j<height;j++){
			int [] neigs=help1.neighbours(imgin,i,j);

			if(neigs[0]==1){
				t=t+1;
			}
		}
	}

	return t;
}*/



}
