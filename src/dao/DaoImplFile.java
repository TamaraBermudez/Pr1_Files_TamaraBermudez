package dao;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import main.Shop;
import model.Employee;
import model.Product;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


import model.Amount;


public class DaoImplFile implements Dao {

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Employee> seedEmployee() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	//No todos los estoy cambiando, en este si escribo: 
	//Esto hace que lea el inventario y lo devuelva, asi se puede ver
	//Los carga, los lee y los añade.
	
	// Vale eso lo hice mal, en el paso 3 pone que tengo que cambiarlo para q DaoImplFile.getInventory()  
	//directamente haga lo que quite antes de codigo en el shop, 1 busca el txt 2 lee las lineas 3 separa los datos
	//4 crea cada product q le pedimos 5 los mete en inventory y ahi devuelve la lista
	//OSEA QUE AHORA TENGO QUE CAMBIAR EL CODIGO TOTALMENTE el autogenerado lo cambie
	
	@Override
	public List<Product> getInventory() {
		//creamos la lista vacia 
		ArrayList<Product> inventory = new ArrayList<>();
		//con esto le decimos dnd esta el archivo 
		File f = new File(System.getProperty("user.dir") + File.separator + "files/inputInventory.txt");
		
		//comenzamos a leer el archivo 
		try { 
		//lee el archivo 
		FileReader fr = new FileReader(f); 
		BufferedReader br = new BufferedReader(fr);
		
		//lo guardamos en la variable line
		String line = br.readLine();
		
		while (line != null) { 
			String[] sections = line.split(";");
			
			String name = ""; 
			double wholesalerPrice = 0.0; 
			int stock = 0;
		
			for (int i = 0; i < sections.length; i++) { 
				String[] data = sections[i].split(":");
			
		switch (i) { 
		case 0: name = data[1]; 
		break; 
		
		case 1: wholesalerPrice = Double.parseDouble(data[1]); 
		break; 
		
		case 2: stock = Integer.parseInt(data[1]); 
		break; 
			} 
		}
		
		inventory.add(new Product( name, new Amount(wholesalerPrice), true, stock));
		
		// esto no se si iba dentro o fuera del while
		line = br.readLine(); 
		}
		
		br.close(); 
		fr.close(); 
		
		} catch (IOException e) { 
			e.printStackTrace(); 
			} 
		
		return inventory; 
			
	}
	
	

	@Override
	public boolean writeInventory(List<Product> inventory) {
		
		//Para que ponga la fecha de hoyy
		LocalDate myObj = LocalDate.now();
		String fileName = "inventory_" + myObj.toString() + ".txt";
		
		//creamos el archivo separado tal cual como lo pide el ejercicio (el separator es para q funcione en windows y todos)
	    File f = new File(System.getProperty("user.dir") + File.separator + "files" + File.separator + fileName);
		
		try {
			FileWriter fw;
			fw = new FileWriter(f, false);
			PrintWriter pw = new PrintWriter(fw);
			
			for(Product product : inventory) {
				//StringBuilder escribe la linea exactamente como quieras
				StringBuilder line = new StringBuilder("nombre: " + product.getName() + ";precio:" + product.getWholesalerPrice() + ";stock:" + product.getStock() + ";");
				pw.write(line.toString());
				fw.write("\n");
				
			}
			pw.close();
			fw.close();
			return true;
			
		} catch (IOException e){
			e.printStackTrace();
			return false;
			
		}
		
	}
	
	
	
	
}