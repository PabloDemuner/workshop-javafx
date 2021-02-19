package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {

	void insert (Seller obj);
	void update (Seller obj);
	void deleteById (Integer id);
	
	/*Operação que retorna Departmen
	 * Faz a consulta do id no BD
	 * Se existir ele retorna o valor do Department
	 * Caso não exista retorna Null
	 */
	Seller findById(Integer id);
	
	//Retorna todos os Departments (findAll)
	List <Seller> findAll();
	//Retorna apenas um Department
	List <Seller>findByDepartment(Department department);
}
