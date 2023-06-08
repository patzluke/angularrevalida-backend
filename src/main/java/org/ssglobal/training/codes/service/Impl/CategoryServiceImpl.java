package org.ssglobal.training.codes.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repositories.CategoryRepository;
import org.ssglobal.training.codes.repositories.ProductRepository;
import org.ssglobal.training.codes.service.CategoryService;
import org.ssglobal.training.codes.service.ProductService;
import org.ssglobal.training.codes.tables.pojos.Category;
import org.ssglobal.training.codes.tables.pojos.Product;

import lombok.RequiredArgsConstructor;

@Primary
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private final CategoryRepository categoryRepository;
	
	
	@Override
	public List<Category> selectAllCategory() {
		return categoryRepository.selectAllCategory();
	}
}
