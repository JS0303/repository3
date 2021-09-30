package com.model2.mvc.service.product.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDao;
import com.model2.mvc.service.domain.Product;

public class ProductServiceImpl implements ProductService{
	
	///Field
	private ProductDao productDao;
	
	public ProductServiceImpl() {
		productDao=new ProductDao();
	}

	public void insertProduct(Product product) throws Exception {
		productDao.insertProduct(product);
	}

	public Product getProduct(int prodNo) throws Exception {
		return productDao.findProduct(prodNo);
	}

	public Map<String,Object> getProductList(Search search) throws Exception {
		return productDao.getProductList(search);
	}

	public void updateProduct(Product product) throws Exception {
		productDao.updateProduct(product);
	}

	public boolean checkDuplication(int prodNo) throws Exception {
		boolean result=true;
		Product product=productDao.findProduct(prodNo);
		if(product != null) {
			result=false;
		}
		return result;
	}
		
}