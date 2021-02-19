package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
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
	 public void onButtonNewAction(ActionEvent event) {
		 
		 Stage parentStage = Utils.currentStage(event);
		 createDialogForm("/gui/DepartmentForm.fxml", parentStage);
				 
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
	
	/*Função para carregar a janela do form de preenchimento 
	 * de um novo Department
	 */
	private void createDialogForm(String absoluteName, Stage parentStage) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			Stage dialogStage = new Stage();
			
			dialogStage.setTitle("Enter Department Data");
			dialogStage.setScene( new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

}
