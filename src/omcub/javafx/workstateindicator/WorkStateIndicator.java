package omcub.javafx.workstateindicator;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andy Moore
 */
public class WorkStateIndicator
  extends StackPane
  {
  private final ObjectProperty<Worker.State> workerStateProperty = new SimpleObjectProperty<>();

  private static final double DEFAULT_SIZE = 48.0;
  private static final double DEFAULT_SIZE_PRE = DEFAULT_SIZE * 0.7;
  private static final Paint DEFAULT_STROKE = Color.BLACK;

  private final Node DEFAULT_NODE_CANCELLED = createCircle(DEFAULT_SIZE,     Color.YELLOW,    DEFAULT_STROKE);
  private final Node DEFAULT_NODE_FAILED    = createCircle(DEFAULT_SIZE,     Color.RED,       DEFAULT_STROKE);
  private final Node DEFAULT_NODE_READY     = createSquare(DEFAULT_SIZE_PRE, Color.BLUE,      DEFAULT_STROKE);
  private final Node DEFAULT_NODE_RUNNING   = createArrow( DEFAULT_SIZE,     Color.GREEN,     DEFAULT_STROKE);
  private final Node DEFAULT_NODE_SCHEDULED = createSquare(DEFAULT_SIZE_PRE, Color.CYAN,      DEFAULT_STROKE);
  private final Node DEFAULT_NODE_SUCCEEDED = createCircle(DEFAULT_SIZE,     Color.LIMEGREEN, DEFAULT_STROKE);

  protected Map<Worker.State, Node> stateNodeMap = new HashMap<>();

  public WorkStateIndicator()
    {
    super();

    putStateNode(Worker.State.CANCELLED, DEFAULT_NODE_CANCELLED);
    putStateNode(Worker.State.FAILED, DEFAULT_NODE_FAILED);
    putStateNode(Worker.State.READY, DEFAULT_NODE_READY);
    putStateNode(Worker.State.RUNNING, DEFAULT_NODE_RUNNING);
    putStateNode(Worker.State.SCHEDULED, DEFAULT_NODE_SCHEDULED);
    putStateNode(Worker.State.SUCCEEDED, DEFAULT_NODE_SUCCEEDED);

    workerStateProperty.addListener((observable, oldState, newState) ->
      {
      if (oldState != null)
        {
        Node oldNode = stateNodeMap.get(oldState);
        oldNode.setVisible(false);
        }
      Node newNode = stateNodeMap.get(newState);
      newNode.setVisible(true);
      });

    }

  public ObjectProperty<Worker.State> workerStateProperty()
    {
    return workerStateProperty;
    }

  // >>> cancelled <<<
  public Node getCancelled()
    {
    return getStateNode(Worker.State.CANCELLED);
    }

  public void setCancelled(Node node)
    {
    putStateNode(Worker.State.CANCELLED, node);
    }

  // >>> failed <<<
  public Node getFailed()
    {
    return getStateNode(Worker.State.FAILED);
    }

  public void setFailed(Node node)
    {
    putStateNode(Worker.State.FAILED, node);
    }

  // >>> ready <<<
  public Node getReady()
    {
    return getStateNode(Worker.State.READY);
    }

  public void setReady(Node node)
    {
    putStateNode(Worker.State.READY, node);
    }

  // >>> running <<<
  public Node getRunning()
    {
    return getStateNode(Worker.State.RUNNING);
    }

  public void setRunning(Node node)
    {
    putStateNode(Worker.State.RUNNING, node);
    }

  // >>> scheduled <<<
  public Node getScheduled()
    {
    return getStateNode(Worker.State.SCHEDULED);
    }

  public void setScheduled(Node node)
    {
    putStateNode(Worker.State.SCHEDULED, node);
    }

  // >>> succeeded <<<
  public Node getSucceeded()
    {
    return getStateNode(Worker.State.SUCCEEDED);
    }

  public void setSucceeded(Node node)
    {
    putStateNode(Worker.State.SUCCEEDED, node);
    }

  private Node getStateNode(Worker.State state)
    {
    return this.stateNodeMap.get(state);
    }

  private void putStateNode(Worker.State state, Node node)
    {
    Node oldNode = this.stateNodeMap.get(state);
    if (oldNode != null)
      {
      getChildren().remove(oldNode);
      }
    node.setVisible(false);
    getChildren().add(node);
    this.stateNodeMap.put(state, node);
    }

  @Override
  public String toString()
    {
    return "WorkStateIndicator{" +
      "stateNodeMap=" + id(this.stateNodeMap) +
      '}';
    }

//.... static shape helper methods for defaults ....

  private static Rectangle createSquare(double size, Paint fill, Paint stroke)
    {
    Rectangle rectangle = new Rectangle(size, size);
    return applyPaint(rectangle, fill, stroke);
    }

  private static Polygon createArrow(double size, Paint fill, Paint stroke)
    {
    double xInset = size * 0.1;
    double xMin = xInset;
    double xMax = size - xInset;
    double yMin = 0.0;
    double yMax = size;
    double yMid = ((yMax - yMin)/2.0)+yMin;
    Polygon polygon = new Polygon();
    polygon.getPoints().addAll(
      xMin, yMin,
      xMax, yMid,
      xMin, yMax
      );
    return applyPaint(polygon, fill, stroke);
    }

  private static Circle createCircle(double size, Paint fill, Paint stroke)
    {
    double radius = size / 2.0;
    Circle circle = new Circle(radius);
    return applyPaint(circle, fill, stroke);
    }

  private static <T extends Shape> T applyPaint(T shape, Paint fill, Paint stroke)
    {
    shape.setFill(fill);
    shape.setStroke(stroke);
    return shape;
    }

  /*===== for testing =====*/

  public static void main(String[] args)
    {
    Application.launch(TestAppFixed.class, args);
    }

  public static class TestAppFixed
    extends Application
    {
    @Override
    public void start(Stage stage)
      throws Exception
      {
      VBox vBox = new VBox();
      vBox.setPadding(new Insets(10.0));
      vBox.setPrefSize(400.0, 300.0);

      Label label = new Label("Test App for WorkStateIndicator");
      vBox.getChildren().add(label);

      for (Worker.State state: Worker.State.values())
        {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10.0));
        vBox.getChildren().add(hBox);

        WorkStateIndicator indicator = new WorkStateIndicator();
        indicator.workerStateProperty().setValue(state);
        hBox.getChildren().add(indicator);

        Label stateLabel = new Label(state.name());
        stateLabel.setTranslateX(15.0);
        stateLabel.setTranslateY(15.0);
        hBox.getChildren().add(stateLabel);
        }

      Scene scene = new Scene(vBox);
      stage.setScene(scene);
      stage.show();
      }
    }

  private static String id(Object object)
    {
    if (object == null) return "(null)";
    return Integer.toHexString(System.identityHashCode(object));
    }

  }