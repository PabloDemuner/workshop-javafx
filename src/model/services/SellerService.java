package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class SellerService {

	// Injeção de dependencia usando o padrão Factory
	private SellerDao sellerDao = DaoFactory.createSellerDao();

	// Metodo que efetua a busca de todos os Sellers no BD
	public List<Seller> findAll() {

		return sellerDao.findAll();
	}

	// Metodo que insere um novo department ou atualiza.
	public void saveOrUpdate(Seller obj) {
		if (obj.getId() == null) {
			sellerDao.insert(obj);
		} else {
			sellerDao.update(obj);
		}
	}

	// Metodo para remover um departamento do BD
	public void remove(Seller obj) {
		sellerDao.deleteById(obj.getId());
	}

}
