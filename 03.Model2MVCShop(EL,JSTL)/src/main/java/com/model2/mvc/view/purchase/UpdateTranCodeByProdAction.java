package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends Action{

	public UpdateTranCodeByProdAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		String tranCode = request.getParameter("tranCode");
		
		System.out.println("상품번호:: "+prodNo+", code:: "+tranCode);
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		Purchase purchase = new Purchase();
		Product product = new Product();
		product.setProdNo(prodNo);
		purchase.setTranCode(tranCode);
		purchase.setPurchaseProd(product);
		purchaseService.updateTranCode(purchase);
		
		if("002".equals(tranCode)) {
			return "forward:/listProduct.do?menu=manage";
		}
		
		return "forward:/listPurchase.do";
	}
	
}
