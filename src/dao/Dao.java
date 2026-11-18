package dao;

import model.Employee;
import model.Product;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;


public interface Dao {
	
	public void connect();

	public void disconnect();

	public Employee getEmployee(int employeeId, String password);
	
	ArrayList<Employee> seedEmployee() throws SQLException;
	
	//Metodo getInventory que devuelve la lista de los productos 
	public List<Product> getInventory();
	
	//Metodo writeInventory recibe el inventario como una lista de productos osea recibe lo de arriba
	//da true si se pudo hacer, false si hubo algun problema
	//recibe una lista de product llamada inventory 
	public boolean writeInventory(List<Product> inventory);
		
}
