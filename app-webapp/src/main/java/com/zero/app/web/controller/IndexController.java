package com.zero.app.web.controller;

import com.zero.constants.RespCode;
import com.zero.constants.RespMSG;
import com.zero.service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * Created by zero on 16/2/29.
 */
@Controller
public class IndexController extends AbstractAppController{

	@Autowired
	private IndexService indexService;

	@RequestMapping(value = {"","/"})
	public ModelAndView index() {

		ModelAndView view=new ModelAndView("/index");
		view.addObject("success", RespCode.SUCCESS);
		view.addObject("message", RespMSG.SUCCESS);

		List<Map<String, Object>> datas = this.indexService.index();

		for (Map<String, Object> data:datas)
			LOG.info(data.toString());
		return view;
	}
}
