##### Task App 

##### Task 1: Create Table products
* products ( id,name)

```sql
create table products ( id serial primary key , name varchar(100) not null,
unique (name)
);
select * from products;

insert into products ( name) values ( 'Tomoto');
insert into products ( name) values ( 'Onion');
```

##### Task 2: Use Java, to add products

##### Task 2.1: Get the Database Connection Details
* database - postgres database
* machine - localhost, port: 5432
* username - postgres
* password - postgres
* database name - shoppingapp_db

#### Task 2.2:  Create a Maven Project 

* groupId : in.naresh
* artifactId : shoppingapp-dao
* pom.xml
```
<groupId>in.naresh</groupId>
  <artifactId>shoppingapp-dao</artifactId>
  <version>0.0.1-SNAPSHOT</version>
```

##### Task 2.3: Add postgres dependency
* https://mvnrepository.com/artifact/org.postgresql/postgresql/42.2.20

```
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.2.20</version>
</dependency>
```

##### Task 2.3: Write a method to connect database using Java
* ConnectionUtil

```java
package in.naresh.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtil {

	private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
	private static final String DATABASE_NAME = "shoppingapp_db";
	private static final String DB_USERNAME = "postgres";
	private static final String DB_PASSWORD = "postgres";
	private static final String HOST = "localhost";
	private static final int PORT = 5432;
	private static final String DB_URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE_NAME; // jdbc:postgres://localhost:5432/shoppingapp_db

	public static void main(String[] args) throws Exception {


		// Step 1: Load the database driver into memory ( ClassNotFoundException )
		Class.forName(DRIVER_CLASS_NAME); 
		
		// Step 2: Get the Database Connection (SQLException)
		Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		System.out.println(connection);

	}

}


```

##### Task 2.4 : Can you convert Connection to reusable method
```java
package in.naresh.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
	private static final String DATABASE_NAME = "shoppingapp_db";
	private static final String DB_USERNAME = "postgres";
	private static final String DB_PASSWORD = "postgres";
	private static final String HOST = "localhost";
	private static final int PORT = 5432;
	private static final String DB_URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE_NAME; // jdbc:postgres://localhost:5432/shoppingapp_db

	public static Connection getConnection() {

		Connection connection = null;
		try {
			// Step 1: Load the database driver into memory ( ClassNotFoundException )
			Class.forName(DRIVER_CLASS_NAME);

			// Step 2: Get the Database Connection (SQLException)
			connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			System.out.println(connection);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to get the database connection");
		}

		return connection;
	}

}

```

```java
package in.naresh.util;

import java.sql.Connection;

public class ConnectionUtilTest {

	public static void main(String[] args) {

		Connection connection = ConnectionUtil.getConnection();
		System.out.println(connection);
	}

}
```

* Note
```
org.postgresql.jdbc.PgConnection@48e4374
```


##### Task 4: Write Java code to Add Product and save it in database
* ProductDAO
```java
package in.naresh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import in.naresh.util.ConnectionUtil;

public class ProductDAO {

	public static void main(String[] args) throws Exception {

		// I want to add product name in database
		
		String productName = "Potato";
		
		
		//Step 1: Get connection
		Connection connection = ConnectionUtil.getConnection();
		
		
		//Step 2: Prepare data
		String sql = "insert into products(name) values ( ? )";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setString(1, productName);
		
		//Step 3: Execute Query ( insert/update/delete - call executeUpdate() )
		int rows = pst.executeUpdate(); 
		
		System.out.println("No of rows inserted :" + rows);
		
		
	}

}

```

##### Task 4.2: Can you close the connection resources



```java
package in.naresh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import in.naresh.util.ConnectionUtil;

public class ProductDAO {

	public static void main(String[] args) throws Exception {

		// I want to add product name in database

		String productName = "Potato";

		// Step 1: Get connection
		Connection connection = null;
		PreparedStatement pst = null;
		try {
			connection = ConnectionUtil.getConnection();

			// Step 2: Prepare data
			String sql = "insert into products(name) values ( ? )";
			pst = connection.prepareStatement(sql);
			pst.setString(1, productName);

			// Step 3: Execute Query ( insert/update/delete - call executeUpdate() )
			int rows = pst.executeUpdate();

			System.out.println("No of rows inserted :" + rows);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Unable to add product");
		} finally {

			// Null Check - to avoid Null Pointer Exception
			if (pst != null) {
				pst.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

	}

}
```

```java
public static void close(PreparedStatement pst, Connection con) throws Exception {
		// Null Check - to avoid Null Pointer Exception
		if (pst != null) {
			pst.close();
		}
		if (con != null) {
			con.close();
		}
	}
```


##### Task : Add price for new products

```java
package in.naresh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import in.naresh.util.ConnectionUtil;

public class ProductDAO {

	public static void main(String[] args) throws Exception {

		// I want to add product name in database

		String productName = "Chilli";
		int price = 10;

		// Step 1: Get connection
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = ConnectionUtil.getConnection();

			// Step 2: Prepare data
			String sql = "insert into products(name, price) values ( ?,? )";
			pst = con.prepareStatement(sql);
			pst.setString(1, productName); 
			pst.setInt(2, price);

			// Step 3: Execute Query ( insert/update/delete - call executeUpdate() )
			int rows = pst.executeUpdate();
			boolean inserted = rows == 1?true:false;

			System.out.println("No of rows inserted :" + rows);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Unable to add product");
		} finally {

			// Null Check - to avoid Null Pointer Exception
			if (pst != null) {
				pst.close();
			}
			if (con != null) {
				con.close();
			}
		}

	}

}
```

##### Can you separate business logic and test method
```java
package in.naresh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import in.naresh.util.ConnectionUtil;

public class ProductDAO {

	/**
	 * Add Product 
	 * @param productName
	 * @param price
	 * @throws Exception
	 * @throws SQLException
	 */
	public static void save(String productName, int price) throws Exception {
		// Step 1: Get connection
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = ConnectionUtil.getConnection();

			// Step 2: Prepare data
			String sql = "insert into products(name, price) values ( ?,? )";
			pst = con.prepareStatement(sql);
			pst.setString(1, productName);
			pst.setInt(2, price);

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

}

```
* Test Class
```java
package in.naresh.dao;

public class ProductDAOTest {

	public static void main(String[] args) throws Exception {

		// I want to add product name in database

		String productName = "Chilli";
		int price = 10;

		ProductDAO.save(productName, price);

	}

}

```

#### Add All Product
```java
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
	//public static void save(String productName, int price) throws Exception {
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
	

	public static void save(List<Product> products) throws Exception {
		for (Product product : products) {
			ProductDAO.save(product);	
		}
	}


}
```
* 
```java
package in.naresh.dao;

import java.util.ArrayList;
import java.util.List;

import in.naresh.model.Product;

public class ProductDAOTest {

	public static void main(String[] args) throws Exception {

		// I want to add product name in database

		List<Product> products = new ArrayList<Product>();

		Product product1 = new Product("Tomato", 20);
		Product product2 = new Product("Carrot", 10);

		products.add(product1);
		products.add(product2);

		// Add single product
		// ProductDAO.save(product1);

		// Add all products to db
		ProductDAO.save(products);

	}

}
```

##### Get all product details

```java
package in.naresh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import in.naresh.util.ConnectionUtil;

public class ProductDAO2 {


	public static void main(String[] args) throws Exception {
		
		
		
		//Step 1: Get the connection		
		Connection con = ConnectionUtil.getConnection();
		
		//Step 2: Query
		String sql = "select id,name,price from products";
		PreparedStatement pst = con.prepareStatement(sql);
		//Step 3: execute query
		
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			int price = rs.getInt("price");
			System.out.println(id + "-" + name + "-" + price);
		}
		
		ConnectionUtil.close(rs, pst,con);
		
	}


}

```

* Store the data in model

```java
while(rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			int price = rs.getInt("price");
			
			Product product = new Product(id,name,price);
			System.out.println(product);
		}
```

* Store the result in ArrayList


```java
List<Product> productList = new ArrayList<Product>();

while(rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			int price = rs.getInt("price");
			
			//Store the data in model
			Product product = new Product(id,name,price);
			//Store all products in list
			productList.add(product);
			
		}
```


#### Reusable Code

```
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
```

* Test Class
```java
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

```
