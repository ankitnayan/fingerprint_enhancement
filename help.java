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


public class help{

public int[] angles(BufferedImage img ,int i ,int j){
	int t=0;
	int[] angle=new int[15];
	int red;
	if(getred(img,i+2,j)==255){
		t=t+1;
		angle[t]=0;
	} 
	if(getred(img,i+2,j-1)==255){
		t=t+1;
		angle[t]=21;
	} 
	if(getred(img,i+2,j-2)==255){
		t=t+1;
		angle[t]=32;
	} 
	if(getred(img,i+1,j-2)==255){
		t=t+1;
		angle[t]=42;
	} 
	if(getred(img,i,j-2)==255){
		t=t+1;
		angle[t]=64;
	} 
	if(getred(img,i-1,j-2)==255){
		t=t+1;
		angle[t]=85;
	} 
	if(getred(img,i-2,j-2)==255){
		t=t+1;
		angle[t]=96;
	} 
	if(getred(img,i-2,j-1)==255){
		t=t+1;
		angle[t]=106;
	} 
	if(getred(img,i-2,j)==255){
		t=t+1;
		angle[t]=128;
	} 
	if(getred(img,i-2,j+1)==255){
		t=t+1;
		angle[t]=149;
	} 
	if(getred(img,i-2,j+2)==255){
		t=t+1;
		angle[t]=160;
	} 
	if(getred(img,i-1,j+2)==255){
		t=t+1;
		angle[t]=170;
	} 
	if(getred(img,i,j+2)==255){
		t=t+1;
		angle[t]=192;
	} 
	if(getred(img,i+1,j+2)==255){
		t=t+1;
		angle[t]=213;
	} 
	if(getred(img,i+2,j+2)==255){
		t=t+1;
		angle[t]=224;
	} 
	if(getred(img,i+2,j+1)==255){
		t=t+1;
		angle[t]=234;
	} 

	angle[0]=t;
	return angle;
}

public int[][] holearray(BufferedImage img){
int width = img.getWidth(null);
int height = img.getHeight(null);
int[] black = new int[]{0,0,0};
help help1=new help();
int[][] array = new int[height][width];
int r1;
int r2;
int temp;
int num,i;
WritableRaster raster = img.getRaster(); 
for(int j=0;j<height;j++){
raster.setPixel(0,j,black);
raster.setPixel(width-1,j,black);
}
for(int j=0;j<height;j++){
num=0;
array[j][0]=num;
r1=255;
r2=0;
for(i=0;i<width;i++){
int red=help1.getred(img,i,j);
if(red==r1){
temp=r1;
r1=r2;
r2=temp;
array[j][num+1]=i;
//array[j][2*num+2]=j;
num=num+1;
array[j][0]=num;
}
}}
return array;
}



public int getred(BufferedImage img ,int i,int j){
	int width = img.getWidth(null);
	int height = img.getHeight(null);

	if(i<0||i>=width||j<0||j>=height)
	return 0;
	else {
		int c=  img.getRGB(i,j); 
		int red= (c >> 16) & 0xFF;
		return red;
	}

}

public int getgreen(BufferedImage img ,int i,int j){
	int width = img.getWidth(null);
	int height = img.getHeight(null);

	if(i<0||i>=width||j<0||j>=height)
	return 0;
	else {
		int c=  img.getRGB(i,j); 
		int green= (c >> 8) & 0xFF;
		return green;
	}

}

public int getblue(BufferedImage img ,int i,int j){
	int width = img.getWidth(null);
	int height = img.getHeight(null);

	if(i<0||i>=width||j<0||j>=height)
	return 0;
	else {
		int c=  img.getRGB(i,j); 
		int blue= c & 0xFF;
		return blue;
	}

}

public int[][] remove3(int[][] array, int index){
	int length=Array.getLength(array);

	if (index <= 0 || index >= length) {
		throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
	}

	int[][] result = new int[length-1][3];

	for(int i=1;i<index;i++){
		result[i][0]=array[i][0];
		result[i][1]=array[i][1];
		result[i][2]=array[i][2];
	}

	if (index < length - 1){
		for(int i=index;i<length-1;i++){
			result[i][0]=array[i+1][0];
			result[i][1]=array[i+1][1];
			result[i][2]=array[i+1][2];

		}

	}

	result[0][0]=length-2;
	return result;

}

public int[][] remove2(int[][] array, int index){
	int length=array[0][0]+1;

	if (index <= 0 || index >= length) {
		throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
	}
	int[][] result = new int[length-1][2];

	for(int i=1;i<index;i++){
		result[i][0]=array[i][0];
		result[i][1]=array[i][1];
	}

	if (index < length - 1){
		for(int i=index;i<length-1;i++){
			result[i][0]=array[i+1][0];
			result[i][1]=array[i+1][1];
		}

	}

	result[0][0]=length-2;
	return result;

}

public int[] remove(int[] array, int index) {
      int length = getLength(array);
      if (index < 0 || index >= length) {
          throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
      }
      
      int[] result = new int[length-1];
      System.arraycopy(array, 0, result, 0, index);
      if (index < length - 1) {
          System.arraycopy(array, index + 1, result, index, length - index - 1);
      }
      
      return result;
  }
 public static int getLength(int[] array) {
      if (array == null) {
          return 0;
      }
      return Array.getLength(array);
  }

public int[][] img_arr(BufferedImage img) {
	int width = img.getWidth(null);
	int height = img.getHeight(null);
	int[][] array = new int[height][width];

	for (int i=0;i<width;i++) {
		for (int j=0;j<height;j++) {
		int clr = img.getRGB(i,j);	
		int red = (clr & 0x00ff0000) >> 16;
		array[j][i] = red;
		}
	}
	return array;
}

public BufferedImage arr_img(int[][] array,int width,int height) {

	BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	WritableRaster raster = img.getRaster(); 


	for (int i=0;i<width;i++) {
		for (int j=0;j<height;j++) {
		int[] color = new int[]{array[j][i],array[j][i],array[j][i]};
		raster.setPixel(i,j,color);
		}
	}
	return img;
}

public int[] neighbours(int[][] arra,int i,int j){

	int[] value= new int[10];

	    if(arra[j][i]>0) value[0]=1;
	    else value[0]=0; 

	    if(arra[j][i+1]>0) value[1]=1;
	    else value[1]=0; 


	    if(arra[j-1][i+1]>0) value[2]=1;
	    else value[2]=0;


	    if(arra[j-1][i]>0) value[3]=1;
	    else value[3]=0; 


	    if(arra[j-1][i-1]>0) value[4]=1;
	    else value[4]=0;


	    if(arra[j][i-1]>0) value[5]=1;
	    else value[5]=0; 


	    if(arra[j+1][i-1]>0) value[6]=1;
	    else value[6]=0;


	    if(arra[j+1][i]>0) value[7]=1;
	    else value[7]=0; 


	    if(arra[j+1][i+1]>0) value[8]=1;
	    else value[8]=0;
	    value[9]=value[1];

	return value;

}

public int numneighone(int[] x){
	int j=0;
	for(int i=1;i<=8;i++){
		if(x[i]==1) j=j+1;
	}

	return j;
}

public int condn1(int[] x){
	int[] b= new int[5];
	int i;

	for(i=1;i<=4;i++){
		if((x[(2*i)-1]==0)&&((x[2*i]==1)||(x[(2*i)+1]==1))) b[i]=1;
		else b[i]=0;
		if(b[i]==1 && b[i-1]==1) break;
	}

	int xph=b[1]+b[2]+b[3]+b[4];
	if(xph==1) return (1);
	else return (0);

}

public int condn2(int[] y){
	int[] w= new int[5];
	int i;
	for(i=1;i<=4;i++){
		if((y[2*i]==1)||(y[(2*i)-1]==1)) w[i]=1;
		else w[i]=0;
	}

	int[] t= new int[5];
	for(i=1;i<=4;i++){
		if((y[2*i]==1)||(y[(2*i)+1]==1)) t[i]=1;
		else t[i]=0;
	}

	int n1p=w[1]+w[2]+w[3]+w[4];
	int n2p=t[1]+t[2]+t[3]+t[4];
	if(n1p<=n2p){
		if(n1p==2||n1p==3) return 1;
		else return 0;
	} 
	else{
		if(n2p==2||n2p==3) return 1;
		else return 0;
	}
}

public int condn3(int[] x){
	if((x[1]==0)||((x[2]==0)&&(x[3]==0)&&(x[8]!=0))) return (1);
	else return (0);
}
public int condn3dash(int[] x){
	if((x[5]==0)||((x[6]==0)&&(x[7]==0)&&(x[4]!=0))) return (1);
	else return (0);
}


}
