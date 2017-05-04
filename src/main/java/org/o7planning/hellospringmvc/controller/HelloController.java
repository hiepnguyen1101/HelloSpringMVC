package org.o7planning.hellospringmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {
	@RequestMapping(value={"/","home"}, method=RequestMethod.GET)
	public String homePage(ModelMap model){
		model.addAttribute("greeting","Hi, Welcome to mysite");
		System.out.println("Home");
		return "welcome";		
	}
	@RequestMapping(value="/admin",method=RequestMethod.GET)
	public String adminPage(ModelMap model){
		model.addAttribute("user",this.getPrinciple());
		return "admin";
	}
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response){
		Authentication auth= SecurityContextHolder.getContext().getAuthentication();
		if(auth!=null){
			new SecurityContextLogoutHandler().logout(request,response,auth);		}
		return "welcome";		
	}
	@RequestMapping(value="/Access_Denied",method =RequestMethod.GET)
	public String accessDeniedPage(ModelMap model){
		model.addAttribute("user",getPrinciple());
		return "accessDenied";
	}
	private String getPrinciple(){
		String userName = null;
		Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principle instanceof UserDetails){
			userName = ((UserDetails)principle).getUsername();
		}
		else {
			userName = principle.toString();
		}
		return userName;
	}
}
