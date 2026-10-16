package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Product;
import model.Employee;

public class DaoImplJDBC implements Dao {
	Connection connection;
	//aqui declaro el metodo de abajo para que funcione: 
	private static final ArrayList<Employee> SEED_EMPLOYEES = new ArrayList<>();

	static {
        SEED_EMPLOYEES.add(new Employee(1, "Tamara", "1234"));
        SEED_EMPLOYEES.add(new Employee(2, "Blanca", "5678"));
        SEED_EMPLOYEES.add(new Employee(3, "Yessamin", "9999"));
    }
		
	@Override
	public void connect() {
		// Define connection parameters
		String url = "jdbc:mysql://localhost:3306/shop";
		String user = "root";
		String pass = "";
		try {
			this.connection = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		Employee employee = null;
		String query = "select * from employee where employeeId= ? and password = ? ";
		
		try (PreparedStatement ps = connection.prepareStatement(query)) { 
    		ps.setInt(1,employeeId);
    	  	ps.setString(2,password);
    	  	//System.out.println(ps.toString());
            try (ResultSet rs = ps.executeQuery()) {
            	if (rs.next()) {
            		employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3));
            	}
            }
        } catch (SQLException e) {
			// in case error in SQL
			e.printStackTrace();
		}
    	return employee;
	}
	
	//Buscar por id
	public Employee getEmployeeById(int employeeId) {

        Employee employee = null;

        String query = "SELECT * FROM employee WHERE employeeId = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, employeeId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    employee = new Employee(
                        rs.getInt("employeeId"),
                        rs.getString("name"),
                        rs.getString("password")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }
	
	// Crear
	public void addEmployee(Employee employee) {

        String query = "INSERT INTO employee (employeeId, name, password) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, employee.getEmployeeId());
            ps.setString(2, employee.getName());
            ps.setString(3, employee.getPassword());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	// Update
	public void updateEmployee(Employee employee) {

        String query = "UPDATE employee SET name = ?, password = ? WHERE employeeId = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, employee.getName());
            ps.setString(2, employee.getPassword());
            ps.setInt(3, employee.getEmployeeId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	// Eliminar 
	public void deleteEmployee(int employeeId) {

        String query = "DELETE FROM employee WHERE employeeId = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, employeeId);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	// el Seed
	public ArrayList<Employee> seedEmployee() throws SQLException {

        ArrayList<Employee> created = new ArrayList<>();

        String createTable = "CREATE TABLE IF NOT EXISTS employee ("
                + "employeeId INT NOT NULL PRIMARY KEY, "
                + "name VARCHAR(100) NOT NULL, "
                + "password VARCHAR(100) NOT NULL)";

        String insert = "INSERT IGNORE INTO employee "
                + "(employeeId, name, password) VALUES (?, ?, ?)";

        // Crear tabla
        try (Statement st = connection.createStatement()) {
            st.executeUpdate(createTable);
        }

        // Insertar empleados
        try (PreparedStatement ps = connection.prepareStatement(insert)) {

            for (Employee employee : SEED_EMPLOYEES) {

                ps.setInt(1, employee.getEmployeeId());
                ps.setString(2, employee.getName());
                ps.setString(3, employee.getPassword());

                if (ps.executeUpdate() == 1) {
                    created.add(getEmployeeById(employee.getEmployeeId()));
                }
            }
        }

        return created;
    }
	
	// Los metodos de el archivo Dao.java
	@Override
	public List<Product> getInventory() {
	    return new ArrayList<>();
	} 
	
	@Override
	public boolean writeInventory(List<Product> inventory) {
	    return false;
	}
	
	
}

