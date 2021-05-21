package in.naresh.model;

public class Product {

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + "]";
	}

	public int id;
	
	public Product(int id, String name, int price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public String name;
	
	public int price;
	
	public Product(String name, int price) {
		this.name= name;
		this.price = price;
	}
}
