package org.ssglobal.training.codes.repositories;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ssglobal.training.codes.tables.pojos.Category;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
public class CategoryRepository {
	private final org.ssglobal.training.codes.tables.Category CATEGORY = org.ssglobal.training.codes.tables.Category.CATEGORY;

	@Autowired
	private final DSLContext dslContext;
	
	public List<Category> selectAllCategory() {
		List<Category> products = dslContext.selectFrom(CATEGORY).fetchInto(Category.class);
		return products;
	}
}
