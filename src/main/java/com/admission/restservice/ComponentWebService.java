package com.admission.restservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admission.dto.JsonResponse;
import com.admission.dto.OptionItemTo;
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
	public List<OptionTO> getOptionItems(@RequestParam String parentComKey, @RequestParam String parentItemValue, @RequestParam String comKey) {
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
	
	@RequestMapping(value="/findoptionitems", method= {RequestMethod.GET, RequestMethod.POST}, headers="Accept=application/json")
	@ResponseBody 
	public List<OptionItemTo> findOptionItems(@RequestParam Map<String, String> paramMap) {
		List<OptionItemTo> items = new ArrayList<OptionItemTo>();
		
		Integer parentId = null;
		String id = paramMap.get("id");
		if(id != null) {
			try {
				parentId = Integer.parseInt(id);
			} catch (Exception e) {}
		}
		try {
			List<OptionItem> optionItems = componentService.findOptionItemsByParent(parentId);
			for(OptionItem item : optionItems) {
				items.add(OptionItemTo.from(item));
			}
		} catch (Exception e) {
			log.debug("fetch optionitem fail", e);
		}

		return items;
	}

	@RequestMapping(value="/createoptionitem", method=RequestMethod.POST, headers="Accept=application/json")
	@ResponseBody 
	public JsonResponse createOptionItem(@RequestBody OptionItem optionItem) {
		JsonResponse res = new JsonResponse();
		log.debug("parent id: " + optionItem.getParentId());
		try {
			optionItem = componentService.createOptionItem(optionItem);
			res.setResult("ok");
			res.setData(OptionItemTo.from(optionItem));
		} catch (Exception e) {
			log.debug("create optionitem fail", e);
			res.setResult("添加候选项失败: " + e.getMessage());
		}

		return res;
	}

	@RequestMapping(value="/updateoptionitem", method=RequestMethod.POST, headers="Accept=application/json")
	@ResponseBody 
	public JsonResponse updateOptionItem(@RequestBody OptionItem optionItem) {
		JsonResponse res = new JsonResponse();
		
		try {
			optionItem = componentService.updateOptionItem(optionItem);
			res.setResult("ok");
			res.setData(OptionItemTo.from(optionItem));
		} catch (Exception e) {
			log.debug("update optionitem fail", e);
			res.setResult("更新候选项失败: " + e.getMessage());
		}

		return res;
	}

	@RequestMapping(value="/deleteoptionitem/{id}", method=RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody 
	public JsonResponse deleteOptionItem(@PathVariable int id) {
		JsonResponse res = new JsonResponse();
		
		try {
			componentService.deleteOptionItem(id);
			res.setResult("ok");
		} catch (Exception e) {
			log.debug("delete optionitem fail", e);
			res.setResult("删除候选项失败: " + e.getMessage());
		}

		return res;
	}
}
