package com.zero.app.web.controller;

import com.zero.constants.RespCode;
import com.zero.constants.RespMSG;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zero on 16/2/29.
 */
@Controller
public class IndexController {

	@RequestMapping(value = {"","/"})
	public ModelAndView index() {

		ModelAndView view=new ModelAndView("/index");
		view.addObject("success", RespCode.SUCCESS);
		view.addObject("message", RespMSG.SUCCESS);
		return view;
	}
}
