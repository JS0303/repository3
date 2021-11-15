package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class AddPurchaseAction extends Action{
	
	///Constructor
	public AddPurchaseAction() {
		
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		User user = new User();
		user = (User) session.getAttribute("user");
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		Product product = new Product();
		product.setProdNo(prodNo);
		product.setProdTranCode("001");
		
		
		System.out.println(":: ::AddPurchaseAtion prodNo"+prodNo);
		Purchase purchase = new Purchase();
		System.out.println("::AddPurchaseAtion userVO ::"+user);
		System.out.println("::AddPurchaseAtion productVO ::"+product);
		
		purchase.setBuyer(user);
		purchase.setDlvyAddr(request.getParameter("receiverAddr"));
		purchase.setDlvyDate(request.getParameter("receiverDate"));
		purchase.setDlvyRequest(request.getParameter("receiverRequest"));
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setPurchaseProd(product);
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setTranCode("001");
		//purchaseVO.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
		
		System.out.println(":: AddPurchaseAction¿« purchase ::"+purchase);
		
		//System.out.println(":: AddPurchaseAction¿« tranNo ::"+request.getParameter("tranNo"));
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		purchaseService.addPurchase(purchase);
		
		request.setAttribute("purchase", purchase);
		
		return "forward:/purchase/addPurchase.jsp";
	}

}
