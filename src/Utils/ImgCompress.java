package Utils;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;  
/**
 * 图片压缩处理类 网上的
 * @author Administrator
 *
 */
public class ImgCompress {  
    private Image img;  
    private int width;  
    private int height;  
    private File file =null;
    
   /* public static void main(String[] args) throws Exception {  120 220
        ImgCompress imgCom = new ImgCompress("C:\\Users\\Administrator\\Desktop\\IMG_20150416_183103.jpg");  
        imgCom.resizeFix(220,220,"C:\\Users\\Administrator\\Desktop\\new1.jpg");  
    } */
    /** 
     * 构造函数 
     */  
    public ImgCompress(String fileName) throws IOException {  
        file=new File(fileName);// 读入文件  
        img = ImageIO.read(file);      // 构造Image对象  
        width = img.getWidth(null);    // 得到源图宽  
        height = img.getHeight(null);  // 得到源图长  
    }  
    /** 
     * 按照宽度还是高度进行压缩 
     * @param w int 最大宽度 
     * @param h int 最大高度 
     */  
    public void resizeFix(int w,int h,String rname) throws IOException {  
        if (width / height > w / h) {  
            resizeByWidth(w,rname);  
        } else {  
            resizeByHeight(h,rname);  
        }  
    }  
    /** 
     * 以宽度为基准，等比例放缩图片 
     * @param w int 新宽度 
     */  
    private void resizeByWidth(int w,String rname) throws IOException {  
        int h = (int) (height * w / width);  
        resize(w, h,rname);  
    }  
    /** 
     * 以高度为基准，等比例缩放图片 
     * @param h int 新高度 
     */  
    private void resizeByHeight(int h,String rname) throws IOException {  
        int w = (int) (width * h / height);  
        resize(w, h,rname);  
    }  
    /** 
     * 强制压缩/放大图片到固定的大小 
     * @param w int 新宽度 
     * @param h int 新高度 
     */  
    private void resize(int w, int h,String rname){  
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢  
        BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB );   
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图  
        File destFile = new File(rname);  
        try {
        	ImageOutputStream out=ImageIO.createImageOutputStream(destFile);
//			FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流  
			// 可以正常实现bmp、png、gif转jpg  
			ImageIO.write(image, "jpg", out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }  
}  