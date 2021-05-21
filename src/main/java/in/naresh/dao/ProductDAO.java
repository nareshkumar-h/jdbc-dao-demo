package in.naresh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import in.naresh.model.Product;
import in.naresh.util.ConnectionUtil;

public class ProductDAO {

	/**
	 * Add Product 
	 * @param productName
	 * @param price
	 * @throws Exception
	 * @throws SQLException
	 */
	//public static void save(String productName, int price) throws Exception, SQLException {
	public static void save(Product product) throws Exception, SQLException {
		// Step 1: Get connection
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = ConnectionUtil.getConnection();

			// Step 2: Prepare data
			String sql = "insert into products(name, price) values ( ?,? )";
			pst = con.prepareStatement(sql);
			pst.setString(1, product.name);//pst.setString(1, productName);
			pst.setInt(2, product.price);//pst.setInt(2, price);
			

			// Step 3: Execute Query ( insert/update/delete - call executeUpdate() )
			int rows = pst.executeUpdate();
			boolean inserted = rows == 1 ? true : false;

			System.out.println("No of rows inserted :" + rows);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Unable to add product");
		} finally {

			ConnectionUtil.close(pst, con);
		}
	}
	

	public static void save(List<Product> products) throws Exception, SQLException {
		for (Product product : products) {
			ProductDAO.save(product);	
		}
	}


}
