package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;

public class ProductDao {
	
	///Constructor
	public ProductDao(){
	}

	public void insertProduct(Product product) throws Exception {
		
		Connection con = DBUtil.getConnection();
		System.out.println("Connection �Ϸ�");
		
		String str= product.getManuDate();
		
		//�������ڸ� "-"�� �����ϰ� split
		String[] md=str.split("-");
		String y = md[0];
		String m = md[1];
		String d = md[2];
		
		
		String sql = "INSERT "+
							"INTO product "+
							"VALUES (seq_product_prod_no.nextval,?,?,?,?,?,SYSDATE)";
		
		System.out.println("������ �����Ϸ�");
		
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		
		pStmt.setString(1, product.getProdName());
		pStmt.setString(2, product.getProdDetail());
		pStmt.setString(3, y+m+d);
		pStmt.setInt(4, product.getPrice());
		pStmt.setString(5, product.getFileName());
		pStmt.executeUpdate();
		System.out.println(":: insertProduct method�� sql ::"+sql);
		System.out.println("������ ���ۿϷ�");
		
		pStmt.close();
		con.close();
	}

	public Product findProduct(int prodNo) throws Exception {
		
		System.out.println("ProductDao�� findProduct Method ����..");
		
		Connection con = DBUtil.getConnection();

		String sql = "SELECT * "+
						"FROM product WHERE prod_no=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, prodNo);
		System.out.println("ProductDao���� �ҷ��� prodNo : "+prodNo);
		
		ResultSet rs = pStmt.executeQuery();

		Product product = null;
		
		while (rs.next()) {
			product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setManuDate(rs.getString("manufacture_day"));
			product.setPrice(rs.getInt("price"));
			product.setFileName(rs.getString("image_file"));
			product.setRegDate(rs.getDate("reg_date"));
			
		}
		
		rs.close();
		pStmt.close();
		con.close();

		return product;
	}

	public Map<String, Object> getProductList(Search search) throws Exception {
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT prod_no, prod_name, prod_detail, manufacture_day, price, image_file FROM product ";
		
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0") && !search.getSearchKeyword().equals("")) {
				sql += " WHERE prod_no='" + search.getSearchKeyword()+ "'";
				
			} else if (search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {
				sql += " WHERE prod_name LIKE '%" + search.getSearchKeyword()+ "%'";
				
			}else if (search.getSearchCondition().equals("2") && !search.getSearchKeyword().equals("")) {
				sql += " WHERE price='" + search.getSearchKeyword()+ "'";
			}
		}
		sql += " ORDER BY prod_no";
		
		System.out.println("ProductDao::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDao :: totalCount  :: " + totalCount);
		
		//==> CurrentPage �Խù��� �޵��� Query �ٽñ���
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		System.out.println(search);
		
		List<Product> list = new ArrayList<Product>();
		
		while(rs.next()){
			Product product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setManuDate(rs.getString("manufacture_day"));
			product.setPrice(rs.getInt("price"));
			product.setFileName(rs.getString("image_file"));
			list.add(product);
		}
		
		//==> totalCount ���� ����
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage �� �Խù� ���� ���� List ����
		map.put("list", list);

		rs.close();
		pStmt.close();
		con.close();
					
		return map;
	}

	public void updateProduct(Product vo) throws Exception {
		
		Connection con = DBUtil.getConnection();
		System.out.println("ProductDao�� updateProduct Method ����..");
		System.out.println("ProductDao���� updateProduct DB�� Connection..");
		
		String sql = "UPDATE product "+
							"SET prod_name = ?, prod_detail = ?, manufacture_day = ?, price = ?, image_file = ? "+
							"WHERE prod_no = ?";
		System.out.println("ProductDao : ������ �غ�Ϸ�");
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, vo.getProdName());
		pStmt.setString(2, vo.getProdDetail());
		pStmt.setString(3, vo.getManuDate());
		pStmt.setInt(4, vo.getPrice());
		pStmt.setString(5, vo.getFileName());
		pStmt.setInt(6, vo.getProdNo());
		pStmt.executeUpdate();
		System.out.println(":: ProductDao : ������ ���ۿϷ�");
		System.out.println(":: ProductDao�� updateProduct Method ����..");
		
		pStmt.close();
		con.close();
	}
	
	// �Խ��� Page ó���� ���� ��ü Row(totalCount)  return
	private int getTotalCount(String sql) throws Exception {
			
	sql = "SELECT COUNT(*) "+
			"FROM ( " +sql+ ") countTable";
			
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
			
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
			
		pStmt.close();
		con.close();
		rs.close();
			
		return totalCount;
	}
	
	// �Խ��� currentPage Row ��  return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
										" 	FROM (	"+sql+" ) inner_table "+
										"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
			
		System.out.println("ProductDAO :: make SQL :: "+ sql);	
			
		return sql;
	}
}
