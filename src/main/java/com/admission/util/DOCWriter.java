package com.admission.util;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;

public class DOCWriter {
	public static void searchAndReplace(String srcPath, String destPath,
			Map<String, String> map) {
		try {
			XWPFDocument document = new XWPFDocument(
					POIXMLDocument.openPackage(srcPath));
			List<XWPFHeader> headList = document.getHeaderList();
			if (headList != null) {
				for (XWPFHeader th : headList) {
					Iterator<XWPFParagraph> pi = th.getListParagraph()
							.iterator();
					replaceParagraph(pi, map);
				}
			}
			// 替换段落中的指定文字
			Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
			replaceParagraph(itPara, map);

			// 替换表格中的指定文字
			Iterator<XWPFTable> itTable = document.getTablesIterator();
			replaceTable(itTable, map);

			FileOutputStream outStream = null;
			outStream = new FileOutputStream(destPath);
			document.write(outStream);
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.gc();
	}

	private static void replaceParagraph(Iterator<XWPFParagraph> itPara,
			Map<String, String> map) {
		// 替换段落中的指定文字
		while (itPara.hasNext()) {
			XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
			// String s = paragraph.getParagraphText();
			List<XWPFRun> run = paragraph.getRuns();
			for (XWPFRun tr : run) {
				String text = tr.getText(tr.getTextPosition());
				if (text != null && text.length() > 0) {
					for (String key : map.keySet()) {
						String value = map.get(key);
						if (text.length() >= key.length())
							text = text.replaceAll(key, value);
					}
					tr.setText(text, 0);
				}
			}
		}
	}

	public static void replaceTable(Iterator<XWPFTable> itTable,
			Map<String, String> map) {
		while (itTable.hasNext()) {
			XWPFTable table = (XWPFTable) itTable.next();
			int rcount = table.getNumberOfRows();
			for (int i = 0; i < rcount; i++) {
				XWPFTableRow row = table.getRow(i);
				List<XWPFTableCell> cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					Iterator<XWPFParagraph> it = cell.getParagraphs()
							.iterator();
					replaceParagraph(it, map);

					replaceTable(cell.getTables().iterator(), map);
				}
			}
		}
	}

	private static XWPFRun createXWPFRun(XWPFParagraph paragraph,
			boolean isBold, String fontFamily, int fontSize, int textPos,
			String txt) {
		XWPFRun run = paragraph.createRun();
		CTRPr rpr = run.getCTR().addNewRPr();
		CTFonts rfonts = rpr.addNewRFonts();
		rfonts.setAscii(fontFamily);
		rfonts.setEastAsia(fontFamily);
		rpr.addNewSz().setVal(BigInteger.valueOf(21));// 5号字体
		rpr.addNewSzCs().setVal(BigInteger.valueOf(21));
		run.setBold(isBold);
		run.setFontFamily(fontFamily);
		run.setFontSize(fontSize);
		if (textPos > 0)
			run.setTextPosition(textPos);
		run.setText(txt);

		return run;
	}

	private static XWPFParagraph createXWPFParagraph(XWPFDocument doc,
			ParagraphAlignment align, boolean isBold, String fontFamily,
			int fontSize, int textPos, String txt) {
		XWPFParagraph p = doc.createParagraph();
		p.setAlignment(align);
		p.setVerticalAlignment(TextAlignment.TOP);

		createXWPFRun(p, isBold, fontFamily, fontSize, textPos, txt);

		return p;
	}

	public static void buildDoc() throws Exception {
		XWPFDocument doc = new XWPFDocument();

		// ///////////////////////////////////////////////////////////////////////////
		// build title content
		// ///////////////////////////////////////////////////////////////////////////
		XWPFParagraph titleParagraph = doc.createParagraph();
		titleParagraph.setAlignment(ParagraphAlignment.CENTER);
		titleParagraph.setVerticalAlignment(TextAlignment.TOP);

		createXWPFRun(titleParagraph, true, "宋体", 16, 0, "上海闵行区启英幼儿园报名表");

		// ///////////////////////////////////////////////////////////////////////////
		// build hello words
		// ///////////////////////////////////////////////////////////////////////////
		XWPFParagraph helloParagraph = doc.createParagraph();
		helloParagraph.setAlignment(ParagraphAlignment.LEFT);
		helloParagraph.setVerticalAlignment(TextAlignment.TOP);

		createXWPFRun(helloParagraph, false, "楷体", 11, 0, "各位家长：");

		// ///////////////////////////////////////////////////////////////////////////
		// build instruction words
		// ///////////////////////////////////////////////////////////////////////////
		XWPFParagraph instructionParagraph = doc.createParagraph();
		instructionParagraph.setAlignment(ParagraphAlignment.LEFT);
		instructionParagraph.setVerticalAlignment(TextAlignment.TOP);

		createXWPFRun(
				instructionParagraph,
				false,
				"楷体",
				11,
				0,
				"    你好！家是孩子幸福的港湾，是孩子成长的重要土壤。您的孩子不久将成为一名小学生了，很荣幸您有意向送您的孩子来我校求学。为了让我们彼此增进了解，以便更有针对性地研究学校教育，指导家庭教育，促进家校合作，使孩子健康成长，希望您配合我们如实填写这份登记表。");

		// ///////////////////////////////////////////////////////////////////////////
		// build table title
		// ///////////////////////////////////////////////////////////////////////////
		XWPFParagraph ttp = doc.createParagraph();
		ttp.setAlignment(ParagraphAlignment.LEFT);
		ttp.setVerticalAlignment(TextAlignment.TOP);

		createXWPFRun(ttp, false, "楷体", 11, 20, "表一：");

		// ///////////////////////////////////////////////////////////////////////////
		// build table content
		// ///////////////////////////////////////////////////////////////////////////
		XWPFTable table = doc.createTable(12, 16);
		table.setCellMargins(20, 20, 20, 20);
		XWPFTableRow row = table.getRow(0);
		row.getCell(0).setParagraph(
				createXWPFParagraph(doc, ParagraphAlignment.LEFT, false, "楷体",
						11, 20, "姓名"));

		// ///////////////////////////////////////////////////////////////////////////
		// save file
		// ///////////////////////////////////////////////////////////////////////////
		FileOutputStream out = new FileOutputStream("c:\\simple.docx");
		doc.write(out);
		out.close();
	}

	public static void testReplaceMethod() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("${what}", "fesfdsfasda");
		map.put("${title}", "aaaaaa");
		System.out.println(map.get("${title}"));
		String srcPath = "c:\\applicationtemp.docx";
		String destPath = "c:\\2.doc";
		searchAndReplace(srcPath, destPath, map);
	}

	public static void testBuildMethod() {
		try {
			buildDoc();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		testBuildMethod();
	}
}
