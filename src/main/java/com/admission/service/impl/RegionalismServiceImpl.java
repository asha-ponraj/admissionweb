package com.admission.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admission.entity.City;
import com.admission.entity.District;
import com.admission.entity.Province;
import com.admission.service.RegionalismService;

import com.admission.dao.CityDao;
import com.admission.dao.DistrictDao;
import com.admission.dao.ProvinceDao;

@Transactional
@Service("regionalismService")
public class RegionalismServiceImpl implements RegionalismService {

	@Autowired
	private ProvinceDao provinceDao;
	
	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private DistrictDao districtDao;

	@Override
	public void updateRegionalism(InputStream excelInputStream)
			throws Exception {
		districtDao.deleteAll();
		cityDao.deleteAll();
		provinceDao.deleteAll();
		
		Workbook book = WorkbookFactory.create(excelInputStream);
		
		Sheet sheet = book.getSheetAt(0);
		if(sheet == null)
			throw new Exception("行政区域信息文件内容为空");
		
		Iterator<Row> rowIt = sheet.iterator();
		if(rowIt == null || !rowIt.hasNext())
			throw new Exception("行政区域信息文件内容为空");

		rowIt.next();

		Province province = null;
		City city = null;
		District district = null;
		
		while(rowIt.hasNext()) {
			Row row = rowIt.next();
			Iterator<Cell> cellIt = row.iterator();
			List<String> nameList = new ArrayList<String>();
			while(cellIt != null && cellIt.hasNext()) {
				Cell cell = cellIt.next();
				switch(cell.getCellType()) {
				case Cell.CELL_TYPE_BLANK:
					nameList.add("");
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					nameList.add(String.valueOf(cell.getBooleanCellValue()));
					break;
				case Cell.CELL_TYPE_ERROR:
					nameList.add(String.valueOf(cell.getErrorCellValue()));
					break;
				case Cell.CELL_TYPE_FORMULA:
					nameList.add(cell.getCellFormula());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					BigDecimal big = new BigDecimal(cell.getNumericCellValue());
					nameList.add(big.toString());
					break;
				case Cell.CELL_TYPE_STRING:
					nameList.add(cell.getStringCellValue());
					break;
				}
			}
			
			if(nameList.size() >= 3) {
				String tp = nameList.get(0);
				String tc = nameList.get(1);
				String td = nameList.get(2);
				
				if(tp != null)
					tp = tp.trim();
				if(tp != null && tp.length() > 0) {
					province = new Province();
					province.setName(tp);
					
					provinceDao.save(province);
				}
				if(province != null) {
					if(tc != null)
						tc = tc.trim();
					if(tc != null && tc.length() > 0) {
						city = new City();
						city.setName(tc);
						city.setProvince(province);
						
						cityDao.save(city);
					}
					
					if(city != null) {
						if(td != null)
							td = td.trim();
						if(td != null && td.length() > 0) {
							district = new District();
							district.setName(td);
							district.setCity(city);
							
							districtDao.save(district);
						}
					}
				}
			}
		}
		
		book = null;
		System.gc();
	}

	@Override
	public List<Province> getProvinceList() {
		return provinceDao.getAll();
	}

	@Override
	public List<City> getCityListByProvince(int provinceId) throws Exception {
		Province p = provinceDao.findById(provinceId);
		if(p == null)
			throw new Exception("查找的省份不存在");
		
		return cityDao.getByProvince(p);
	}

	@Override
	public List<District> getDistrictListByCity(int cityId) throws Exception {
		City c = cityDao.findById(cityId);
		if(c == null)
			throw new Exception("查找的市不存在");
		
		return districtDao.getByCity(c);
	}

}
