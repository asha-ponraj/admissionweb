package com.admission.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

public class BarCodeUtil {
	public static void main(String args[]) {
		String code = "230000198";
		File outfile = new File("/Users/steven/barcode.jpg");
		
		try {
			build(code, outfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized String generateBarcode() {
		StringBuffer sb = new StringBuffer(TimeUtil.getCurTimeStringEx());
		sb.append((int)(Math.random() * 10));
		
		return sb.toString();
	}
	
	public static void build(String code, File outputFile) throws Exception {
        Code39Bean bean = new Code39Bean();
        
        final int dpi = 150;
        
        //Configure the barcode generator
        bean.setModuleWidth(UnitConv.in2mm(0.7f / dpi)); //makes the narrow bar 
                                                         //width exactly one pixel
        bean.setBarHeight(7);
        bean.setWideFactor(3);
        bean.doQuietZone(false);
        
        //Open output file
        OutputStream out = null;
        try {
            out = new FileOutputStream(outputFile);
            
            //Set up the canvas provider for monochrome JPEG output 
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                    out, "image/jpeg", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        
            //Generate the barcode
            bean.generateBarcode(canvas, code);
        
            //Signal end of generation
            canvas.finish();
        } finally {
        	if(out != null)
        		out.close();
        }

		
//		JBarcode localJBarcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());
//		BufferedImage localBufferedImage = localJBarcode.createBarcode(code);
//		FileOutputStream localFileOutputStream = new FileOutputStream(outputFile);
//		ImageUtil.encodeAndWrite(localBufferedImage, "jpeg", localFileOutputStream, 128, 64);
//		localFileOutputStream.close();
	}
}
