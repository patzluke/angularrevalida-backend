package org.ssglobal.training.codes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.training.codes.service.CategoryService;
import org.ssglobal.training.codes.tables.pojos.Category;

@RestController
@RequestMapping(value = "/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping(value = "/get")
	public ResponseEntity<List<Category>> selectAllProducts() {
		return new ResponseEntity<>(categoryService.selectAllCategory(), HttpStatus.OK);
	}
}
