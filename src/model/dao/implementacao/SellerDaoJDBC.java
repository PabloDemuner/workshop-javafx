package model.dao.implementacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
	
	//Método de conexão com o BD
	private Connection connection;
	public SellerDaoJDBC(Connection connection) {
	this.connection = connection;
	}

	@Override
	public void insert(Seller obj) {
		
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?) ",
					Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, obj.getName());
			statement.setString(2, obj.getEmail());
			statement.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			statement.setDouble(4, obj.getBaseSalary());
			statement.setInt(5, obj.getDepartment().getId());
			
			int rowsAffected = statement.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					int id = resultSet.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(resultSet);
			}
			else {
				throw new DbException("Erro inesperado! nenhuma linha foi afetada");
			}
		}
		
		catch (SQLException exception) {
			throw new DbException(exception.getMessage());
		}
		finally {
			DB.closeStatement(statement);
		}
	}

	@Override
	public void update(Seller obj) {
		
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(
					"UPDATE seller "
						+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
						+ "WHERE Id = ? ");
			
			statement.setString(1, obj.getName());
			statement.setString(2, obj.getEmail());
			statement.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			statement.setDouble(4, obj.getBaseSalary());
			statement.setInt(5, obj.getDepartment().getId());
			statement.setInt(6, obj.getId());
			
			 statement.executeUpdate();
			
		}
		
		catch (SQLException exception) {
			throw new DbException(exception.getMessage());
		}
		finally {
			DB.closeStatement(statement);
		}	
	}

	@Override
	public void deleteById(Integer id) {
	
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement("DELETE FROM seller WHERE Id = ? ");
			
			statement.setInt(1, id);
			statement.executeUpdate();
		}
		catch (SQLException exception) {
			throw new DbException(exception.getMessage());
		}
		finally {
			DB.closeStatement(statement);
		}
	}
	
	@Override
	public Seller findById(Integer id) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");
			
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				Department department = instantiateDepartment(resultSet);
				Seller obj = instantiateSeller(resultSet, department);
				return obj;
			}
			return null;
		}
		
		catch (SQLException exception) {
		throw new DbException(exception.getMessage());
		}
		
		finally {
			DB.closeStatement(statement);
			DB.closeResultSet(resultSet);
		}
	}
	
	private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
		Seller obj = new Seller();
		obj.setId(resultSet.getInt("Id"));
		obj.setName(resultSet.getString("Name"));
		obj.setEmail(resultSet.getString("Email"));
		obj.setBaseSalary(resultSet.getDouble("BaseSalary"));
		obj.setBirthDate(new java.util.Date(resultSet.getTimestamp("BirthDate").getTime()));
		obj.setDepartment(department);
		return obj;
	}

	private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
		Department department = new Department();
		department.setId(resultSet.getInt("DepartmentId"));
		department.setName(resultSet.getString("DepName"));
		return department;
	}

	@Override
	public List<Seller> findAll() {

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name ");
			
		
			resultSet = statement.executeQuery();
			
			//Array para fazer a busca de todos do Department invocado
			List <Seller> list = new ArrayList<Seller>();
			
			/*Map Vazio para guardar dentro qualquer Department
			 * que for instanciado */ 
			Map <Integer, Department> map = new HashMap<>();
			
			/*Estrutura While faz o teste se o Department ja existe*/
			while (resultSet.next()) {
				
				/* (map.get) efetua a busca no bd para verificar se ja existe o Id
				 * na coluna DepartmentId
				 * (map.put) Salva o DepartmentId dentro do map para não repitir o Id*/
				Department department1 = map.get(resultSet.getInt("DepartmentId"));
				
				if (department1 == null) {
					department1 = instantiateDepartment(resultSet);
					map.put(resultSet.getInt("DepartmentId"), department1);
				}
				
				Seller obj = instantiateSeller(resultSet, department1);
				
				list.add(obj);
			}
			return list;
		}
		
		catch (SQLException exception) {
		throw new DbException(exception.getMessage());
		}
		
		finally {
			DB.closeStatement(statement);
			DB.closeResultSet(resultSet);
		}

		
	}

	@Override
	public List <Seller> findByDepartment(Department department) {
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name ");
			
			statement.setInt(1, department.getId());
			resultSet = statement.executeQuery();
			
			//Array para fazer a busca de todos do Department invocado
			List <Seller> list = new ArrayList<Seller>();
			
			/*Map Vazio para guardar dentro qualquer Department
			 * que for instanciado */ 
			Map <Integer, Department> map = new HashMap<>();
			
			/*Estrutura While faz o teste se o Department ja existe*/
			while (resultSet.next()) {
				
				/* (map.get) efetua a busca no bd para verificar se ja existe o Id
				 * na coluna DepartmentId
				 * (map.put) Salva o DepartmentId dentro do map para não repitir o Id*/
				Department department1 = map.get(resultSet.getInt("DepartmentId"));
				
				if (department1 == null) {
					department1 = instantiateDepartment(resultSet);
					map.put(resultSet.getInt("DepartmentId"), department1);
				}
				
				Seller obj = instantiateSeller(resultSet, department1);
				
				list.add(obj);
			}
			return list;
		}
		
		catch (SQLException exception) {
		throw new DbException(exception.getMessage());
		}
		
		finally {
			DB.closeStatement(statement);
			DB.closeResultSet(resultSet);
		}
	}

}
