package sample;

import game.Boat;
import game.Game;
import game.GameImpl;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import units.Element;
import units.Position;
import units.TextUnit;
import units.Unit;

public class Main extends Application {

    private final DataFormat UNIT_DATA = new DataFormat("UNIT_DATA");
    private final DataFormat BOAT_DATA_FORMAT = new DataFormat("BOAT");

    @Override public void start(Stage stage) {
        stage.setTitle("GAME");

        Group root = new Group();
        Scene scene = new Scene(root, 800, 800);
        scene.setFill(Color.LIGHTGREEN);

        final TextUnit leftCoastSpace1 = new TextUnit(new Unit(Element.Name.GOAT), 50, 100);
        final TextUnit leftCoastSpace2 = new TextUnit(new Unit(Element.Name.WOLF), 50, 200);
        final TextUnit leftCoastSpace3 = new TextUnit(new Unit(Element.Name.CABBAGE), 50, 300);

        final TextUnit rightCoastSpace1 = new TextUnit(500, 100);
        final TextUnit rightCoastSpace2 = new TextUnit(500, 200);
        final TextUnit rightCoastSpace3 = new TextUnit(500, 300);


        leftCoastSpace3.setScaleX(2.0);
        leftCoastSpace3.setScaleY(2.0);

        leftCoastSpace2.setScaleX(2.0);
        leftCoastSpace2.setScaleY(2.0);

        leftCoastSpace1.setScaleX(2.0);
        leftCoastSpace1.setScaleY(2.0);

        rightCoastSpace1.setScaleX(2.0);
        rightCoastSpace1.setScaleY(2.0);

        rightCoastSpace2.setScaleX(2.0);
        rightCoastSpace2.setScaleY(2.0);

        rightCoastSpace3.setScaleX(2.0);
        rightCoastSpace3.setScaleY(2.0);

        final Boat boat = new Boat(250, 100);
        boat.setScaleX(2.0);
        boat.setScaleY(2.0);

        setUpOnDragDetectedFor(leftCoastSpace1);
        setUpOnDragDetectedFor(leftCoastSpace2);
        setUpOnDragDetectedFor(leftCoastSpace3);

        setUpOnDragDetectedFor(rightCoastSpace1);
        setUpOnDragDetectedFor(rightCoastSpace2);
        setUpOnDragDetectedFor(rightCoastSpace3);


        setUpOnDragDoneFor(leftCoastSpace1);
        setUpOnDragDoneFor(leftCoastSpace2);
        setUpOnDragDoneFor(leftCoastSpace3);
        setUpOnDragDoneFor(rightCoastSpace1);
        setUpOnDragDoneFor(rightCoastSpace2);
        setUpOnDragDoneFor(rightCoastSpace3);

        boat.setOnDragDetected(event -> {
            /* drag was detected, start drag-and-drop gesture*/
            System.out.println("onDragDetected");

            /* allow any transfer mode */
            Dragboard db = boat.startDragAndDrop(TransferMode.ANY);

            /* put a string on dragboard */
            ClipboardContent content = new ClipboardContent();
            content.put(BOAT_DATA_FORMAT, boat.getElement());

            db.setContent(content);

            event.consume();
        });

        boat.setOnDragOver(event -> {
            /* data is dragged over the target */
            System.out.println("onDragOver");

            /* accept it only if it is  not dragged from the same node
             * and if it has a string data */
            if (event.getGestureSource() != boat
                    && boat.getElement() == null
                    && (event.getDragboard().hasContent(UNIT_DATA))) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }

            event.consume();
        });

        rightCoastSpace1.setOnDragOver(event -> {
            /* data is dragged over the target */
            System.out.println("onDragOver");

            /* accept it only if it is  not dragged from the same node
             * and if it has a string data */
            if ( boat.getElement() != null
                    && (event.getDragboard().hasContent(BOAT_DATA_FORMAT))) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }

            event.consume();
        });

        boat.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (boat.getElement() == null) {
                    if (db.hasContent(UNIT_DATA)) {
                        final Element element = (Element) db.getContent(UNIT_DATA);

                        boat.setElement(element);
                        boat.setText(element.getName() + " ON BOAT");
                        success = true;
                    }
                }

                event.setDropCompleted(success);

                event.consume();
            }
        });

        rightCoastSpace1.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {

                Dragboard db = event.getDragboard();
                boolean success = false;
                if (boat.getElement() != null) {
                    if (db.hasContent(BOAT_DATA_FORMAT)) {
                        final Element element = (Element) db.getContent(BOAT_DATA_FORMAT);

                        rightCoastSpace1.setElement(element);
                        rightCoastSpace1.setText(element.getName() + " ON RIGHT COAST");
                        boat.setElement(null);
                        success = true;
                    }
                }

                event.setDropCompleted(success);

                event.consume();
            }
        });

        root.getChildren().add(leftCoastSpace1);
        root.getChildren().add(leftCoastSpace2);
        root.getChildren().add(leftCoastSpace3);
        root.getChildren().add(boat);

        root.getChildren().add(rightCoastSpace1);
        root.getChildren().add(rightCoastSpace2);
        root.getChildren().add(rightCoastSpace3);

        stage.setScene(scene);
        stage.show();
    }

    private void setUpOnDragDetectedFor(TextUnit unit) {
        unit.setOnDragDetected(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                System.out.println("onDragDetected");

                /* allow any transfer mode */
                final Dragboard db = unit.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.put(UNIT_DATA, unit.getElement());

                db.setContent(content);

                event.consume();
            }
        });
    }

    private void setUpOnDragDoneFor(TextUnit unit) {
        unit.setOnDragDone(event -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                unit.setText("EMPTY_SPACE");
            }

            event.consume();
        });
    }

    public static void main(String[] args) { Application.launch(args); }
}