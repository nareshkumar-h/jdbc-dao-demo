package in.naresh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.naresh.model.Product;
import in.naresh.util.ConnectionUtil;

public class ProductDAO2 {

	public static List<Product> getAllProducts() throws Exception {
		List<Product> productList = new ArrayList<Product>();

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {

			// Step 1: Get the connection
			con = ConnectionUtil.getConnection();

			// Step 2: Query
			String sql = "select id,name,price from products";
			pst = con.prepareStatement(sql);
			// Step 3: execute query

			rs = pst.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int price = rs.getInt("price");

				// Store the data in model
				Product product = new Product(id, name, price);
				// Store all products in list
				productList.add(product);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Unable to fetch products");
		} finally {
			ConnectionUtil.close(rs, pst, con);
		}
		return productList;
	}

}
