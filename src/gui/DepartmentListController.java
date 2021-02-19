package gui;

import java.net.URL;
import java.nio.channels.IllegalSelectorException;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable{
	
	private DepartmentService service;
	
	
	@FXML
	 private TableView<Department> tableViewDepartment;
	 
	 @FXML
	 private TableColumn<Department, Integer> tableColumnId;
	 
	 @FXML
	 private TableColumn<Department, String> tableColmunName;
	 
	 @FXML
	 private Button buttonNew;
	 
	 private ObservableList<Department> observableList;
	 
	 @FXML
	 public void onButtonNewAction() {
		 System.out.println("Click on Button");
	 }
	 
	 //Injeção de dependencia do DepartmentService (Principios S.O.L.I.D)
	 public void setDepartmentService(DepartmentService service) {
		 this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		initializeNodes();
		
	}

	/*
	 * metodo de comandos que inicia apropriadamente 
	 * o comportamento das colunas da tabela
	 */
	private void initializeNodes() {
		
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColmunName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		//Faz o tableView acompanhar o tamanho da Janela
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	/*Métodos que acessa os serviços, carregar os departamentos
	 * e aponta os departamentos para o metodo ( ObservableList) 
	 */
	public void updateTableView() {
		
		if (service == null) {
			throw new IllegalStateException("O Serviço está Nulo!");
		}
		
		List<Department> list = service.findAll();
		observableList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(observableList);
	}

}
