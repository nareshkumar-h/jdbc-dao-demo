package in.naresh.dao;

import java.util.List;

import in.naresh.model.Product;

public class TestProductDAOSelect {

	public static void main(String[] args) throws Exception {

		List<Product> productList = ProductDAO2.getAllProducts();

		//Display all products
		for (Product product : productList) {
			System.out.println(product);
		}
		
	}
}
