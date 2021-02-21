package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;
import model.services.SellerService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;

	@FXML
	private MenuItem menuItemDepartment;

	@FXML
	private MenuItem menuItemAbout;

	@FXML
	public void onMenuItemSellerAction() {
		loadView("/gui/SellerList.fxml", (SellerListController controller) -> {
			controller.setSellerService(new SellerService());
			controller.updateTableView();
		});
	}

	/*
	 * Express�o Lambda que efetua a a��o de inicializa��o do controller (DepartmentListController)
	 */
	@FXML
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});
	}

	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {

	}

	/*Fun��o gen�rica do tipo <T>
	 * Parametriza��o com o Consumer<T> para n�o ter que contruir
	 *  duas ou mais fun��es do loadView.
	 */
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {

		try {

			//Fun��o que carrega as janelas
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox vBox = loader.load();

			Scene mainScene = Main.getMainScene();
			/*Referencia para o ScrollPane
			 * (getContent) � referencia para o que estiver dentro do ScrollPane que � o VBox
			 */
			VBox mainVBox = (VBox) ((ScrollPane)mainScene.getRoot()).getContent();
			
			//Primeiro filho do VBox da janela principal (mainMenu)
			Node mainMenu = mainVBox.getChildren().get(0);
			
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(vBox.getChildren());
			
			T Controller = loader.getController();
			initializingAction.accept(Controller);

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);

		}
	}
	
	/*private synchronized void loadView2(String absoluteName) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox vBox = loader.load();

			Scene mainScene = Main.getMainScene();
			/*Referencia para o ScrollPane
			 * (getContent) � referencia para o que estiver dentro do ScrollPane que � o VBox
			 */
			/*VBox mainVBox = (VBox) ((ScrollPane)mainScene.getRoot()).getContent();
			
			//Primeiro filho do VBox da janela principal (mainMenu)
			Node mainMenu = mainVBox.getChildren().get(0);
			
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(vBox.getChildren());
			
			//Inje��o de dependencias do (DepartmentListController)
			DepartmentListController controller = loader.getController();
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);

		}
	}*/


}
