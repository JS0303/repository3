<%@ page contentType="text/html; charset=EUC-KR" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.service.domain.Purchase"%>
<%@ page import="com.model2.mvc.common.*" %>
<%@ page import="com.model2.mvc.service.domain.User" %>
<%@ page import="com.model2.mvc.service.domain.Product" %>
<%@ page import="com.model2.mvc.service.purchase.dao.*" %>

<%-- <%
	User user = (User)request.getAttribute("user");
		System.out.println(getClass().getName()+"addPurchase.jsp���� �ҷ��� userVO : "+user);
		
	Product product = (Product)request.getAttribute("product");
		System.out.println(getClass().getName()+"addPurchase.jsp���� �ҷ��� productVO : "+product);
		
	Purchase purchase=(Purchase)request.getAttribute("purchase");
		System.out.println(getClass().getName()+"addPurchase.jsp���� �ҷ��� purchaseVO : "+purchase);
		
	String payment = "�ſ뱸��";
%> --%>




<html>
<head>
<title>Insert title here</title>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=${purchase.tranNo}" method="post">

������ ���� ���Ű� �Ǿ����ϴ�.

<table border=1>
	<tr>
		<td>��ǰ��ȣ</td>
		<td>${purchase.purchaseProd.prodNo}</td>
		<td></td>
	</tr>
	<tr>
		<td>�����ھ��̵�</td>
		<td>${purchase.buyer.userId}</td>
		<td></td>
	</tr>
	<tr>
		<td>���Ź��</td>
		<td>
		
			${purchase.paymentOption}
		
		</td>
		<td></td>
	</tr>
	<tr>
		<td>�������̸�</td>
		<td>${purchase.receiverName}</td>
		<td></td>
	</tr>
	<tr>
		<td>�����ڿ���ó</td>
		<td>${purchase.receiverPhone}</td>
		<td></td>
	</tr>
	<tr>
		<td>�������ּ�</td>
		<td>${purchase.dlvyAddr}</td>
		<td></td>
	</tr>
		<tr>
		<td>���ſ�û����</td>
		<td>${purchase.dlvyRequest}</td>
		<td></td>
	</tr>
	<tr>
		<td>����������</td>
		<td>${purchase.dlvyDate}</td>
		<td></td>
	</tr>
	<tr>
		<td>�ֹ������ڵ�</td>
		<td>${purchase.tranCode}</td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>