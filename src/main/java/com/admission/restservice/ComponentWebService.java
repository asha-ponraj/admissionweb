package com.admission.restservice;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admission.dto.OptionTO;
import com.admission.entity.OptionItem;
import com.admission.service.ComponentService;

@Controller
@RequestMapping("/component")
public class ComponentWebService {
	private static Logger log = Logger.getLogger(ComponentWebService.class);

	@Autowired
	private ComponentService componentService;


	@RequestMapping(value="/optionitems", method= {RequestMethod.GET, RequestMethod.POST}, headers="Accept=application/json")
	@ResponseBody 
	public List<OptionTO> get(@RequestParam String parentComKey, @RequestParam String parentItemValue, @RequestParam String comKey) {
		List<OptionTO> items = new ArrayList<OptionTO>();
		
		try {
			List<OptionItem> optionItems = componentService.findOptionItemByComKey(parentComKey, parentItemValue, comKey);

			for(OptionItem ti : optionItems) {
				items.add(OptionItem.from(ti));
			}
		} catch (Exception e) {
			log.debug("fetch optionitem fail", e);
		}

		return items;
	}
}
