package com.imran.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imran.model.Product;
import com.imran.service.JewelleryShopService;

@RestController
public class JewelleryShopController {

	@Autowired
	private  JewelleryShopService jewelleryShopService;


	@RequestMapping(value = "/getDiscount", method = RequestMethod.GET, produces = "application/json")
	public Product getDiscount(@RequestParam(name="type",required = true) String type) {
		Product product = new Product();
		product.setType(type);	
		product = jewelleryShopService.getProductDiscount(product);	
		return product;
	}
	
}
