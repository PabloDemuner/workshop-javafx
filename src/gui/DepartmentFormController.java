package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable{
	
	private DepartmentService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	private Department entity;

	@FXML
	private TextField textId;
	
	@FXML
	private TextField textName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button buttonSave;
	
	@FXML
	private Button buttonCancel;
	
	public void setDepartmentService (DepartmentService service) {
		this.service = service;
	}
	
	//Inst�ncia do controlador Department
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	public void subscribeDataChangeListener (DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		/*Exce��es est� sendo criadas devido as inje��es de dependencias
		 * Estarem sendo construidas manualmente e n�o com Container (Framework)
		 */
		if (entity == null) {
			throw new IllegalStateException("Entity was Null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was Null");
		}
		try {
		entity = getFormData();
		service.saveOrUpdate(entity);
		notifyDataChangeListeners();
		//Referencia para fechar a janela ap�s salvo ou atualizado o evento
		Utils.currentStage(event).close();
	}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}

	private Department getFormData() {
		Department obj = new Department();
		
		obj.setId(Utils.tryParseToInt(textId.getText()));
		obj.setName(textName.getText());
		
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		//Referencia para fechar a janela ap�s salvo ou atualizado o evento
				Utils.currentStage(event).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	
		initializeNode();
	}
	
	//Controlador de caractres dos campos
	private void initializeNode() {
		Constraints.setTextFieldInteger(textId);
		Constraints.setTextFieldMaxLength(textName, 40);
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was Null");
		}
		textId.setText(String.valueOf(entity.getId()));
		textName.setText(entity.getName());
	}

}
