<%@ page contentType="text/html; charset=EUC-KR" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="java.util.List"  %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>

<%@ page import="com.model2.mvc.service.domain.Purchase" %>
<%@ page import="com.model2.mvc.service.domain.Product" %>
<%@ page import="com.model2.mvc.service.domain.User" %>
<%@ page import="com.model2.mvc.common.Search" %>

<%-- <%
	HashMap<String,Object> map=(HashMap<String,Object>)request.getAttribute("map");
	
	Search search=(Search)request.getAttribute("search");
	
	User user = (User)session.getAttribute("user");
	
	int total=0;
	
	ArrayList<Purchase> list=null;
		if(map != null){
	
			total=((Integer)map.get("count")).intValue();
	
	list=(ArrayList<Purchase>)map.get("list");
	}
	
	int currentPage=search.getCurrentPage();
	
	int totalPage=0;
		
		if(total > 0) {
			totalPage= total / search.getPageSize() ;
		if(total%search.getPageSize() >0)
			totalPage += 1;
	}
			
%> --%>


<html>
<head>
<title>구매 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function fncGetList(currentPage) {
		console.log(currentPage);
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/listPurchase.do" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">구매 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">전체  ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">전화번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">정보수정</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<%-- <% 	
		int no=list.size();
		for(int i=0; i<list.size(); i++) {
			Purchase purchase = (Purchase)list.get(i);
	%> --%>
	<c:set var="i" value="0" />
				<c:forEach var="purchase" items="${list}">
					<c:set var="i" value="${ i+1 }" />
					<tr class="ct_list_pop">
						<td align="center"><a href="/getPurchase.do?tranNo=${purchase.tranNo}">${i}</a></td>
		<td></td>
		<td align="left">
			<a href="/getUser.do?userId=${user.userId}">${user.userId}</a>
		</td>
		<td></td>
		<td align="left">${user.userName}</td>
		<td></td>
		<td align="left">${user.phone}</td>
		<td></td>
		<c:choose>
			<c:when test="${purchase.tranCode == null}">
				<td align="left">판매중</td>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${purchase.tranCode eq '001'}">
						<td align="left">현재 구매완료상태 입니다. 
						<c:choose>
								<c:when test="${menu eq 'manage'}">
									<a href='updateTranCodeByProd.do?prodNo=${purchase.prodNo}&tranCode=002'>배송하기</a>
								</c:when>
						</c:choose>
						</td>
					</c:when>
								
								<c:when test="${purchase.tranCode eq '002'}">
									<td align="left">현재 배송중 입니다.</td>
								</c:when>
								<c:when test="${purchase.tranCode eq '003'}">
									<td align="left">배송이 완료되었습니다.</td>
								</c:when>
								<c:when test="${purchase.tranCode eq '004'}">
									<td align="left">코드 오류</td>
								</c:when>
				</c:choose>
		</c:otherwise>
		</c:choose>
						<td></td>
						<c:if test="${purchase.tranCode eq '002'}">
						<td align="left"><a href="/updateTranCodeByProd.do?prodNo=${purchase.purchaseProd.prodNo}&tranCode=003">배송완료</a></td>
						</c:if>
		
		<td align="left"></td>
			
	</tr>
	</c:forEach>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
		 <input type="hidden" id="currentPage" name="currentPage" value=""/>
			<jsp:include
							page="../common/pageNavigator.jsp" />
		</td>
	</tr>
</table>

<!--  페이지 Navigator 끝 -->
</form>

</div>

</body>
</html>