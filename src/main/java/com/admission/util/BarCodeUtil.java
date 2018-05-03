package com.admission.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.impl.upcean.UPCABean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class BarCodeUtil {
	public static void main(String args[]) {
		String code = "230000000198";
		File outfile = new File("/Users/steven/barcode.jpg");
		
		try {
	        MultiFormatWriter barcodeWriter = new MultiFormatWriter();
	        BitMatrix barcodeBitMatrix = barcodeWriter.encode(code, BarcodeFormat.CODE_128, 180, 48);
	        MatrixToImageWriter.writeToPath(barcodeBitMatrix, "PNG", outfile.toPath());
//			build(BarCodeType.CODE128, code, outfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized String generateBarcode() {
		StringBuffer sb = new StringBuffer(TimeUtil.getCurTimeStringEx());
		sb.append((int)(Math.random() * 10));
		
		return sb.toString();
	}
	
	public static void build(BarCodeType codeType, String code, File outputFile) throws Exception {
        final int dpi = 150;
        AbstractBarcodeBean bean = null;
        switch(codeType) {
        case EAN13:
        	bean = new EAN13Bean();
        	break;
        case CODE128:
        	bean = new Code128Bean();
        	break;
        case UPCA:
    	default:
        	bean = new UPCABean();
        	break;
        }
        
//        MultiFormatWriter barcodeWriter = new MultiFormatWriter();
//        BitMatrix barcodeBitMatrix = barcodeWriter.encode(code, BarcodeFormat.QR_CODE, 64, 64);
//        MatrixToImageWriter.writeToPath(barcodeBitMatrix, "PNG", outputFile.toPath());
        
        //Configure the barcode generator
        bean.setHeight(10d);
    	bean.setModuleWidth(UnitConv.in2mm(1.0f / 100));
    	bean.setFontSize(2);
//        bean.setModuleWidth(UnitConv.in2mm(0.7f / dpi)); //makes the narrow bar 
//        bean.setBarHeight(7);
//        bean.setWideFactor(3);
//        bean.doQuietZone(false);
        
        //Open output file
        OutputStream out = null;
        try {
            out = new FileOutputStream(outputFile);
            
            //Set up the canvas provider for monochrome JPEG output 
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                    out, "image/jpeg", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        
            //Generate the barcode
            bean.generateBarcode(canvas, code);
//            gen.generateBarcode(canvas, code);
        
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
