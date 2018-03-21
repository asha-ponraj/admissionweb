package com.admission.resource.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.jws.WebService;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.admission.dto.JsonResponse;
import com.admission.dto.OptionTO;
import com.admission.entity.City;
import com.admission.entity.District;
import com.admission.entity.Province;
import com.admission.resource.RegionalismResource;
import com.admission.service.RegionalismService;

@WebService(endpointInterface = "com.admission.resource.RegionalismResource")
public class RegionalismResourceImpl implements RegionalismResource {
	private static Logger log = Logger.getLogger(RegionalismResourceImpl.class);

	@Autowired
	private RegionalismService regionalismService;

	@Override
	public JsonResponse getProvinceListForSelect() {
		List<OptionTO> ps = new LinkedList<OptionTO>();
		List<Province> provinces = regionalismService.getProvinceList();
		if (provinces != null) {
			for (Province tp : provinces) {
				OptionTO to = new OptionTO();
				to.setId(String.valueOf(tp.getId()));
				to.setText(tp.getName());

				ps.add(to);
			}
		}

		JsonResponse res = new JsonResponse("ok", ps);
		return res;
	}

	@Override
	public JsonResponse getCityListForSelect(int provinceId) {
		JsonResponse res = new JsonResponse();

		try {
			List<OptionTO> cs = new LinkedList<OptionTO>();
			List<City> cities = regionalismService
					.getCityListByProvince(provinceId);
			if (cities != null) {
				for (City tc : cities) {
					OptionTO to = new OptionTO();
					to.setId(String.valueOf(tc.getId()));
					to.setText(tc.getName());

					cs.add(to);
				}
			}

			res.setResult("ok");
			res.setData(cs);
		} catch (Exception e) {
			log.debug("get city list fail", e);
			res.setResult("查询市列表失败: " + e.getMessage());
		}

		return res;
	}

	@Override
	public JsonResponse getDistrictListForSelect(int cityId) {
		JsonResponse res = new JsonResponse();

		try {
			List<OptionTO> cs = new LinkedList<OptionTO>();
			List<District> districts = regionalismService
					.getDistrictListByCity(cityId);
			if (districts != null) {
				for (District td : districts) {
					OptionTO to = new OptionTO();
					to.setId(String.valueOf(td.getId()));
					to.setText(td.getName());

					cs.add(to);
				}
			}

			res.setResult("ok");
			res.setData(cs);
		} catch (Exception e) {
			log.debug("get district list fail", e);
			res.setResult("查询区(县)列表失败: " + e.getMessage());
		}

		return res;
	}

	@Override
	public JsonResponse updateRegionalism(MultipartBody body) {
		JsonResponse res = new JsonResponse();
		InputStream is = null;
		try {
			Attachment a = body.getAttachment("regionalismfile");
			is = a.getDataHandler().getInputStream();
			regionalismService.updateRegionalism(is);
			res.setResult("ok");
		} catch (Throwable e) {
			log.debug("update regionalism fail", e);
			res.setResult(e.getMessage());
		} finally {
			if(is != null)
				try {
					is.close();
				} catch (IOException e) {
				}
		}

		return res;
	}

}
