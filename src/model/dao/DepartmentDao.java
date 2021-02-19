package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {

	void insert (Department obj);
	void update (Department obj);
	void deleteById (Integer id);
	
	/*Operação que retorna Departmen
	 * Faz a consulta do id no BD
	 * Se existir ele retorna o valor do Department
	 * Caso não exista retorna Null
	 */
	Department findById(Integer id);
	
	//Retorna todos os Departments (findAll)
	List <Department> findAll();
}
