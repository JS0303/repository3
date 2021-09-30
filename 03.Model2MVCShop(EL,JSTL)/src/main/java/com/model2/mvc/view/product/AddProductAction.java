package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;

public class AddProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		System.out.println(":: AddProductAction.execute() start..");
		
		HttpSession session = request.getSession(true);
		System.out.println(":: AddProductAction :: getSession(true)");
		if(session.isNew()||session.getAttribute("product")==null) {
			session.setAttribute("product", new Product());
			System.out.println("session�� ���Ӱ� �����Ǿ��ų� product�� null�̸� ���Ӱ� product�� ����");
		}
		
		Product product=(Product)session.getAttribute("product");
		
		
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(request.getParameter("fileName"));
				
		System.out.println(":: AddProductAction�� ����� product :: "+product);
		
		ProductService service=new ProductServiceImpl();
		service.insertProduct(product);
	
		request.setAttribute("product", product);
		
		return "forward:/product/addProduct.jsp";
		
	}
}