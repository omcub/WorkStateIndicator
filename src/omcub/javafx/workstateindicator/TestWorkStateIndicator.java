package omcub.javafx.workstateindicator;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Andy Moore
 */
public class TestWorkStateIndicator
  extends Application
  {
  @Override
  public void start(Stage stage)
    throws Exception
    {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("testWorkStateIndicator.fxml"));
    Parent root = fxmlLoader.load();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    }

  public static void main(String[] args)
    {
    launch(args);
    }

  public static class Controller
    implements Initializable
    {
    @FXML private WorkStateIndicator test_WorkStateIndicator;
    @FXML private ChoiceBox test_stateChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources)
      {
      this.test_stateChoiceBox.getItems().addAll(Worker.State.values());
      this.test_stateChoiceBox.getSelectionModel().selectFirst();
      this.test_WorkStateIndicator.workerStateProperty().bind(this.test_stateChoiceBox.valueProperty());
      }
    }

  }