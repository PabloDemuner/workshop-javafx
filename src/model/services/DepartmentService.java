package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {

	//Injeção de dependencia usando o padrão Factory
	private DepartmentDao departmentDao  = DaoFactory.createDepartmentDao();

	//Metodo que efetua a busca de todos os Departments no BD
	public List<Department> findAll() {

		return departmentDao.findAll();
	}
	
	//Metodo que insere um novo department ou atualiza.
	public void saveOrUpdate (Department obj) {
		if (obj.getId() == null) {
			departmentDao.insert(obj);
		}
		else {
			departmentDao.update(obj);
		}
	}

}
