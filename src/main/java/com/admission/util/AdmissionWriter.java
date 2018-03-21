package com.admission.util;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

import com.admission.entity.Address;
import com.admission.entity.Application;
import com.admission.entity.FamilyMember;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.headerfooter.RtfHeaderFooter;
import com.lowagie.text.rtf.style.RtfFont;

public class AdmissionWriter {
	private static final char CHECKED = '\u2611';
	private static final char UNCHECKED = '\u2610';

	public static void main(String[] args) {
		try {
			Application app = new Application();
			app.addressMap().put(Address.TYPE_HUKOU, new Address());
			app.addressMap().put(Address.TYPE_PROPERTY, new Address());
			app.addressMap().put(Address.TYPE_RESIDENCE, new Address());
			app.memberMap().put(FamilyMember.TYPE_FATHER, new FamilyMember());
			app.memberMap().put(FamilyMember.TYPE_MOTHER, new FamilyMember());
			File barcodeFile = new File("C:\\barcode.jpg");
			File outFile = new File("C:\\admission.doc");
			buildRTFDoc(app, barcodeFile, outFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void buildRTFDoc(Application app, File barcodeFile,
			File outFile) throws Exception {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(outFile);

			Document doc = new Document(PageSize.A4);
			RtfWriter2 writer = RtfWriter2.getInstance(doc, out);

			// 设置标题字体样式，粗体、二号、华文中宋
			Font tfont = new RtfFont("宋 体", 16, Font.BOLD, Color.BLACK);

			// 设置正文内容的字体样式，常规、三号、仿宋_GB2312
			Font bfont = new RtfFont("宋 体", 11, Font.NORMAL, Color.BLACK);

			Font tableFont = bfont;

			Image barcodeImg = Image.getInstance(barcodeFile.getAbsolutePath());
			String number = "编号:" + app.getId();
//			String barcode = "条码:" + app.getBarcode();

			Table headerTable = new Table(2);
			headerTable.getDefaultCell().setBorder(Cell.NO_BORDER);
			headerTable.setWidth(100);
			
			Cell imgCell = new Cell();
			imgCell.add(barcodeImg);
			imgCell.setBorder(Cell.NO_BORDER);
			imgCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
			imgCell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
			headerTable.addCell(imgCell);

			Cell numberCell = new Cell();
			numberCell.setBorder(Cell.NO_BORDER);
			numberCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
			numberCell.setVerticalAlignment(Cell.ALIGN_BOTTOM);
			headerTable.addCell(numberCell);

//			RtfHeaderFooter header = new RtfHeaderFooter(barcodeImg);
//			writer.setHeader(header);

			Paragraph footPara = new Paragraph(
					"地址:上海市闵行区新镇路1085号  电话:021-54859690  网址:http://qyyey.age06.com");
			footPara.setFont(bfont);
			RtfHeaderFooter footer = new RtfHeaderFooter(footPara);
			footer.setAlignment(HeaderFooter.ALIGN_CENTER);
			writer.setFooter(footer);

			// 打开文档
			doc.open();
			// 设置页边距，上、下25.4毫米，即为72f，左、右31.8毫米，即为90f
			doc.setMargins(30f, 30f, 20f, 30f);
			
	        doc.add(headerTable);

			// 构建标题，居中对齐，12f表示单倍行距
			Paragraph title = RTFDocStyleUtils.setParagraphStyle(
					"2016年上海闵行区启英幼儿园报名表", tfont, 16f, Paragraph.ALIGN_CENTER);
			doc.add(title);

			Paragraph gradePar = RTFDocStyleUtils.setParagraphStyle("申请班级："
					+ Application.gradeDesc(app.getGrade())
					+ "        招生对象属性："
					+ (app.getCandidateType() == Application.CANDIDATE_TYPE_1?CHECKED:UNCHECKED)
					+ "人户一致 "
					+ (app.getCandidateType() == Application.CANDIDATE_TYPE_2?CHECKED:UNCHECKED)
					+ "户籍 "
					+ (app.getCandidateType() == Application.CANDIDATE_TYPE_3?CHECKED:UNCHECKED)
					+ "产证 "
					+ (app.getCandidateType() == Application.CANDIDATE_TYPE_4?CHECKED:UNCHECKED)
					+ "其他            "
					+ number, bfont, 0f, 18f);
			doc.add(gradePar);

			Table table = new Table(18);
			table.setBorderWidth(1);
			table.setWidth(100);
			table.setWidths(new float[] { 94, 86, 58, 129, 40, 228, 150, 140,
					142, 70, 90, 35, 50, 24, 32, 114, 78, 238 });
			table.setBorderColor(Color.black);
			table.setPadding(3);
			table.setSpacing(0);

			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第1行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			addTableCell(table, 1, 2, "姓名", tableFont);
			addTableCell(table, 1, 3, app.getName(), tableFont); // 姓名

			addTableCell(table, 1, 1, "性别", tableFont);
			addTableCell(table, 1, 1, app.getGender(), tableFont); // 性别

			addTableCell(table, 1, 1, "曾用名", tableFont);
			addTableCell(table, 1, 2, app.getFormerName(), tableFont); // 曾用名

			addTableCell(table, 1, 2, "籍贯", tableFont);
			addTableCell(table, 1, 4, app.getJiguan(), tableFont); // 籍贯

			addTableCell(table, 4, 2, "\n贴\n照\n片\n处", tableFont,
					Paragraph.ALIGN_CENTER); // 贴照片处

			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第2行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			addTableCell(table, 1, 2, "民族", tableFont);
			addTableCell(table, 1, 3, app.getEthnicity(), tableFont); // 民族

			addTableCell(table, 1, 1, "国别/地区", tableFont);
			addTableCell(table, 1, 2, app.getNation(), tableFont); // 国别

			addTableCell(table, 1, 2, "出生日期", tableFont);
			addTableCell(table, 1, 6, app.getBirthdayStr(), tableFont); // 出生日期

			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第3行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			addTableCell(table, 1, 2, "出生证", tableFont);
			StringBuffer s = new StringBuffer();
			if (app.isHasBirthCert()) {
				s.append(CHECKED);
				s.append("有  ");
				s.append(UNCHECKED);
				s.append("无");
			} else {
				s.append(UNCHECKED);
				s.append("有  ");
				s.append(CHECKED);
				s.append("无");
			}
			addTableCell(table, 1, 3, s.toString(), tableFont); // 出生证

			addTableCell(table, 1, 1, "出生地", tableFont);
			addTableCell(table, 1, 2, app.getBirthPlace(), tableFont); // 出生地

			addTableCell(table, 1, 2, "独生子女", tableFont);
			s = new StringBuffer();
			if (app.isOnlyChild()) {
				s.append(CHECKED);
				s.append("是  ");
				s.append(UNCHECKED);
				s.append("否");
			} else {
				s.append(UNCHECKED);
				s.append("是  ");
				s.append(CHECKED);
				s.append("否");
			}
			addTableCell(table, 1, 6, s.toString(), tableFont); // 出生地

			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第4行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			addTableCell(table, 1, 5, "是否上过幼儿园", tableFont);
			s = new StringBuffer();
			if (app.isKindergartened()) {
				s.append(CHECKED);
				s.append("是  ");
				s.append("(名称: ");
				s.append(app.getNursery());
				s.append(" )  ");
				s.append(UNCHECKED);
				s.append("否");
			} else {
				s.append(UNCHECKED);
				s.append("是  (名称:                         ) ");
				s.append(CHECKED);
				s.append("否");
			}
			addTableCell(table, 1, 11, s.toString(), tableFont); // 是否上过幼儿园
			
			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第5行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			addTableCell(table, 1, 3, "身份证件\n类别及号码", tableFont);
			s = new StringBuffer();
			s.append(app.getPidType() == Application.PID_TYPE_1?CHECKED:UNCHECKED);
			s.append("居民身份证  ");
			s.append(app.getPidType() == Application.PID_TYPE_2?CHECKED:UNCHECKED);
			s.append("港澳居民来往内地通行证  ");
			s.append(app.getPidType() == Application.PID_TYPE_3?CHECKED:UNCHECKED);
			s.append("台湾居民来往大陆通行证  ");
			s.append(app.getPidType() == Application.PID_TYPE_4?CHECKED:UNCHECKED);
			s.append("护照  ");
			s.append(app.getPidType() == Application.PID_TYPE_5?CHECKED:UNCHECKED);
			s.append("其他\n证件号码: ");
			s.append(app.getPidNumber());
			addTableCell(table, 1, 15, s.toString(), tableFont); // 身份证件类别及号码
			
			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第6行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			addTableCell(table, 1, 3, "户口性质", tableFont);
			s = new StringBuffer();
			s.append(app.getHkNature() == Application.HKNATURE_1?CHECKED:UNCHECKED);
			s.append("非农业家庭户口    ");
			s.append(app.getHkNature() == Application.HKNATURE_2?CHECKED:UNCHECKED);
			s.append("农业家庭户口    ");
			s.append(app.getHkNature() == Application.HKNATURE_3?CHECKED:UNCHECKED);
			s.append("非农业集体户口    ");
			s.append(app.getHkNature() == Application.HKNATURE_4?CHECKED:UNCHECKED);
			s.append("农业集体户口\n");
			s.append(app.getHkNature() == Application.HKNATURE_5?CHECKED:UNCHECKED);
			s.append("未落常住户口 ( 袋袋户口 )      ");
			s.append(app.getHkNature() == Application.HKNATURE_6?CHECKED:UNCHECKED);
			s.append("其他户口");
			addTableCell(table, 1, 15, s.toString(), tableFont); // 户口性质
			
			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第7行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			addTableCell(table, 1, 3, "户籍类别", tableFont);
			s = new StringBuffer();
			s.append(app.getHkType() == Application.HK_TYPE_1?CHECKED:UNCHECKED);
			s.append("本市本区户籍    ");
			s.append(app.getHkType() == Application.HK_TYPE_2?CHECKED:UNCHECKED);
			s.append("本市外区户籍    ");
			s.append(app.getHkType() == Application.HK_TYPE_3?CHECKED:UNCHECKED);
			s.append("香港    ");
			s.append(app.getHkType() == Application.HK_TYPE_4?CHECKED:UNCHECKED);
			s.append("澳门    ");
			s.append(app.getHkType() == Application.HK_TYPE_5?CHECKED:UNCHECKED);
			s.append("台湾    ");
			s.append(app.getHkType() == Application.HK_TYPE_6?CHECKED:UNCHECKED);
			s.append("外籍    ");
			s.append(app.getHkType() == Application.HK_TYPE_7?CHECKED:UNCHECKED);
			s.append("无户口\n");
			s.append(app.getHkType() >= Application.HK_TYPE_8 && app.getHkType() <= Application.HK_TYPE_11?CHECKED:UNCHECKED);
			s.append("外省市 (");
			s.append(app.getHkType() == Application.HK_TYPE_8?CHECKED:UNCHECKED);
			s.append("上海市居住证  ");
			s.append(app.getHkType() == Application.HK_TYPE_9?CHECKED:UNCHECKED);
			s.append("海外证  ");
			s.append(app.getHkType() == Application.HK_TYPE_10?CHECKED:UNCHECKED);
			s.append("上海市临时居住证  ");
			s.append(app.getHkType() == Application.HK_TYPE_11?CHECKED:UNCHECKED);
			s.append("无本市居住证)");
			addTableCell(table, 1, 15, s.toString(), tableFont); // 户籍类别
			
			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第8行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			addTableCell(table, 2, 3, "户籍地址", tableFont);
			Map<Integer, Address> addressMap = app.addressMap();
			addTableCell(table, 1, 15, addressMap.get(Address.TYPE_HUKOU).getContent(), tableFont);
			
			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第9行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			s = new StringBuffer();
			s.append("所属  ");
			s.append(addressMap.get(Address.TYPE_HUKOU).getTown());
			s.append("  街镇  ");
			s.append(addressMap.get(Address.TYPE_HUKOU).getResidentCouncil());
			s.append("  居委");
			addTableCell(table, 1, 5, s.toString(), tableFont);
			addTableCell(table, 1, 1, "邮编", tableFont);
			addTableCell(table, 1, 3, addressMap.get(Address.TYPE_HUKOU).getPostcode(), tableFont);
			addTableCell(table, 1, 4, "户籍登记日", tableFont);
			addTableCell(table, 1, 2, app.getHkRegDateStr(), tableFont);
			

			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第10行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			addTableCell(table, 1, 3, "住房性质", tableFont);
			s = new StringBuffer();
			s.append(app.getPropertyNature() == Application.PRO_NATURE_1?CHECKED:UNCHECKED);
			s.append("产权房/经适房 (产证编号: ");
			s.append(app.getPropertyNature() == Application.PRO_NATURE_1?app.getPropertyNumber():"          ");
			s.append("  登记日期: ");
			s.append(app.getPropertyNature() == Application.PRO_NATURE_1?app.getPropertyRegDateStr():"          ");
			s.append(" )\n");
			s.append(app.getPropertyNature() == Application.PRO_NATURE_2?CHECKED:UNCHECKED);
			s.append("公租房    ");
			s.append(app.getPropertyNature() == Application.PRO_NATURE_3?CHECKED:UNCHECKED);
			s.append("配住廉租房    ");
			s.append(app.getPropertyNature() == Application.PRO_NATURE_4?CHECKED:UNCHECKED);
			s.append("集体宿舍    ");
			s.append(app.getPropertyNature() == Application.PRO_NATURE_5?CHECKED:UNCHECKED);
			s.append("其他: ");
			s.append(app.getPropertyNature() == Application.PRO_NATURE_5?app.getPropertyOther():"");
			addTableCell(table, 1, 15, s.toString(), tableFont);
			
			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第11行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			addTableCell(table, 2, 3, "产权地址", tableFont);
			addTableCell(table, 1, 15, addressMap.get(Address.TYPE_PROPERTY).getContent(), tableFont);

			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第12行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			s = new StringBuffer();
			s.append("所属  ");
			s.append(addressMap.get(Address.TYPE_PROPERTY).getTown());
			s.append("  街镇  ");
			s.append(addressMap.get(Address.TYPE_PROPERTY).getResidentCouncil());
			s.append("  居委");
			addTableCell(table, 1, 4, s.toString(), tableFont);
			addTableCell(table, 1, 1, "邮编", tableFont);
			addTableCell(table, 1, 2, addressMap.get(Address.TYPE_PROPERTY).getPostcode(), tableFont);
			addTableCell(table, 1, 3, "产证类别", tableFont);
			s = new StringBuffer();
			s.append(app.getPropertyType() == Application.PRO_TYPE_1?CHECKED:UNCHECKED);
			s.append("本地段 ");
			s.append(app.getPropertyType() == Application.PRO_TYPE_2?CHECKED:UNCHECKED);
			s.append("本区 ");
			s.append(app.getPropertyType() == Application.PRO_TYPE_3?CHECKED:UNCHECKED);
			s.append("本市");
			addTableCell(table, 1, 5, s.toString(), tableFont);

			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第13行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			addTableCell(table, 2, 3, "现住地址", tableFont);
			addTableCell(table, 1, 15, addressMap.get(Address.TYPE_RESIDENCE).getContent(), tableFont);
			
			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第14行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			s = new StringBuffer();
			s.append("所属  ");
			s.append(addressMap.get(Address.TYPE_RESIDENCE).getTown());
			s.append("  街镇  ");
			s.append(addressMap.get(Address.TYPE_RESIDENCE).getResidentCouncil());
			s.append("  居委");
			addTableCell(table, 1, 5, s.toString(), tableFont);
			addTableCell(table, 1, 3, "邮编", tableFont);
			addTableCell(table, 1, 7, addressMap.get(Address.TYPE_RESIDENCE).getPostcode(), tableFont);

			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第15行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			addTableCell(table, 1, 3, "健康状况", tableFont);
			addTableCell(table, 1, 5, app.getHealth(), tableFont);
			addTableCell(table, 1, 3, "特殊病史", tableFont);
			addTableCell(table, 1, 7, app.getSpecificDisease(), tableFont);
			
			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第16行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			addTableCell(table, 1, 3, "有无过敏史", tableFont);
			s = new StringBuffer();
			if (app.isAllergic()) {
				s.append(CHECKED);
				s.append("有    ");
				s.append(UNCHECKED);
				s.append("无");
			} else {
				s.append(UNCHECKED);
				s.append("有    ");
				s.append(CHECKED);
				s.append("无");
			}
			addTableCell(table, 1, 5, s.toString(), tableFont);
			addTableCell(table, 1, 3, "计划免疫证", tableFont);
			s = new StringBuffer();
			if (app.isImmunityCert()) {
				s.append(CHECKED);
				s.append("有  ");
				s.append(UNCHECKED);
				s.append("无");
			} else {
				s.append(UNCHECKED);
				s.append("有  ");
				s.append(CHECKED);
				s.append("无");
			}
			addTableCell(table, 1, 7, s.toString(), tableFont);
			
			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第17行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			addTableCell(table, 1, 1, "称\n谓", tableFont);
			addTableCell(table, 1, 2, "姓\n名", tableFont);
			addTableCell(table, 1, 1, "文化\n程度", tableFont);
			addTableCell(table, 1, 2, "身份证号码", tableFont);
			addTableCell(table, 1, 2, "居住证号码\n(非沪籍填写)", tableFont);
			addTableCell(table, 1, 3, "工作单位", tableFont);
			addTableCell(table, 1, 4, "职务", tableFont);
			addTableCell(table, 1, 2, "固定\n电话", tableFont);
			addTableCell(table, 1, 1, "移动\n手机", tableFont);

			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第18行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			Map<Integer, FamilyMember> memberMap = app.memberMap();
			addTableCell(table, 1, 1, "父", tableFont);
			addTableCell(table, 1, 2, memberMap.get(FamilyMember.TYPE_FATHER)
					.getName(), tableFont);
			addTableCell(table, 1, 1, memberMap.get(FamilyMember.TYPE_FATHER)
					.getEducation(), tableFont);
			addTableCell(table, 1, 2, memberMap.get(FamilyMember.TYPE_FATHER)
					.getIdNumber(), tableFont);
			addTableCell(table, 1, 2, memberMap.get(FamilyMember.TYPE_FATHER)
					.getResidentPermit(), tableFont);
			addTableCell(table, 1, 3, memberMap.get(FamilyMember.TYPE_FATHER)
					.getCompany(), tableFont);
			addTableCell(table, 1, 4, memberMap.get(FamilyMember.TYPE_FATHER)
					.getPosition(), tableFont);
			addTableCell(table, 1, 2, memberMap.get(FamilyMember.TYPE_FATHER)
					.getPhone(), tableFont);
			addTableCell(table, 1, 1, memberMap.get(FamilyMember.TYPE_FATHER)
					.getMobile(), tableFont);

			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第19行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			addTableCell(table, 1, 1, "母", tableFont);
			addTableCell(table, 1, 2, memberMap.get(FamilyMember.TYPE_MOTHER)
					.getName(), tableFont);
			addTableCell(table, 1, 1, memberMap.get(FamilyMember.TYPE_MOTHER)
					.getEducation(), tableFont);
			addTableCell(table, 1, 2, memberMap.get(FamilyMember.TYPE_MOTHER)
					.getIdNumber(), tableFont);
			addTableCell(table, 1, 2, memberMap.get(FamilyMember.TYPE_MOTHER)
					.getResidentPermit(), tableFont);
			addTableCell(table, 1, 3, memberMap.get(FamilyMember.TYPE_MOTHER)
					.getCompany(), tableFont);
			addTableCell(table, 1, 4, memberMap.get(FamilyMember.TYPE_MOTHER)
					.getPosition(), tableFont);
			addTableCell(table, 1, 2, memberMap.get(FamilyMember.TYPE_MOTHER)
					.getPhone(), tableFont);
			addTableCell(table, 1, 1, memberMap.get(FamilyMember.TYPE_MOTHER)
					.getMobile(), tableFont);
			
			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第20行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			addTableCell(table, 1, 1, "", tableFont);
			addTableCell(table, 1, 2, "", tableFont);
			addTableCell(table, 1, 1, "", tableFont);
			addTableCell(table, 1, 2, "", tableFont);
			addTableCell(table, 1, 2, "", tableFont);
			addTableCell(table, 1, 3, "", tableFont);
			addTableCell(table, 1, 4, "", tableFont);
			addTableCell(table, 1, 2, "", tableFont);
			addTableCell(table, 1, 1, "", tableFont);
			
			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第21行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			addTableCell(table, 1, 4, "家长需要特别\n说明或解释的情况", tableFont);
			addTableCell(table, 1, 14, app.getRemark(), tableFont);
			
			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第22行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			addTableCell(table, 1, 4, "填表时间", tableFont);
			addTableCell(table, 1, 4,
					TimeUtil.getStringDate(app.getCreateTime()), tableFont);
			addTableCell(table, 1, 3, "填表人签名", tableFont);
			addTableCell(table, 1, 7, "", tableFont);
			
			// //////////////////////////////////////////////////////////////////////////////////////////
			// 第23行内容
			// //////////////////////////////////////////////////////////////////////////////////////////
			addTableCell(table, 1, 4, "核表人签名", tableFont);
			addTableCell(table, 1, 4, "", tableFont);
			addTableCell(table, 1, 3, "复核人签名", tableFont);
			addTableCell(table, 1, 7, "", tableFont);

			doc.add(table);

			doc.close();
		} finally {
			if (out != null)
				out.close();
		}
	}

	private static void addTableCell(Table table, int rowspan, int colspan,
			String text, Font font) {
		Paragraph p = RTFDocStyleUtils.setParagraphStyle(text, font, 0f, 18f);
		Cell cell;
		try {
			cell = new Cell(p);
		} catch (BadElementException e) {
			cell = new Cell(text);
		}
		if (colspan > 0)
			cell.setColspan(colspan);
		if (rowspan > 0)
			cell.setRowspan(rowspan);
		table.addCell(cell);
	}

	private static void addTableCell(Table table, int rowspan, int colspan,
			String text, Font font, int alignment) {
		Paragraph p = RTFDocStyleUtils.setParagraphStyle(text, font, 18f,
				alignment);
		Cell cell;
		try {
			cell = new Cell(p);
		} catch (BadElementException e) {
			cell = new Cell(text);
		}
		if (colspan > 0)
			cell.setColspan(colspan);
		if (rowspan > 0)
			cell.setRowspan(rowspan);
		table.addCell(cell);
	}

}
