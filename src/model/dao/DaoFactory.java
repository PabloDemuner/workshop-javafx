/*Classe que expõe o metódo que retorna o tipo da interface (SellerDao)
 * Mas que internamente ela instancia uma implementação (SellerDaoJDBC() e DepartmentDao())
 */

package model.dao;

import db.DB;
import model.dao.implementacao.DepartmentDaoJDBC;
import model.dao.implementacao.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
}
