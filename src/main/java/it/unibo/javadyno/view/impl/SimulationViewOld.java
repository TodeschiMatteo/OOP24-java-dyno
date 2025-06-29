package it.unibo.javadyno.view.impl;

import it.unibo.javadyno.controller.api.Controller;
import it.unibo.javadyno.model.data.api.ElaboratedData;
import it.unibo.javadyno.view.api.View;
import it.unibo.javadyno.view.impl.component.ChartsPanel;
import it.unibo.javadyno.view.impl.component.GaugePanel;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Simulation view class for the JavaDyno application.
 */
public class SimulationViewOld extends Application implements View {
    private static final int CONTAINER_SPACING = 20;
    private static final int COLUMN_SPACING = 5;
    private static final String CSS_FILE = "/css/simulationStyle.css";
    private static final String CSS_SETTINGS_PANEL_TAG = "left-column";
    private static final double WIDTH_RATIO = 0.8; //percentage of screen width
    private static final double HEIGHT_RATIO = 0.8; //percentage of screen height

    private final Controller controller;
    private final VBox leftColumn = new VBox(COLUMN_SPACING);
    private final ChartsPanel centerColumn = new ChartsPanel();
    private final GaugePanel rightColumn = new GaugePanel();

    /**
     * Constructor for SimulationView that imports the controller.
     *
     * @param controller the controller to be used
     */
    public SimulationViewOld(final Controller controller) {
        this.controller = controller;
    }

    /**
     * Draw the simulation view interface.
     */
    @Override
    public void start(final Stage primaryStage) {
        final Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        final double width = screenBounds.getWidth() * WIDTH_RATIO;
        final double height = screenBounds.getHeight() * HEIGHT_RATIO;

        // Create left columns with buttons and settings
        leftColumn.setAlignment(Pos.CENTER);
        HBox.setHgrow(leftColumn, Priority.ALWAYS);
        leftColumn.getStyleClass().add(CSS_SETTINGS_PANEL_TAG);

        // Create center column for charts
        HBox.setHgrow(centerColumn, Priority.ALWAYS);

        // Create right column for gauges
        HBox.setHgrow(rightColumn, Priority.ALWAYS);

        // Setting up buttons for the left column
        final Button startSimulationButton = new Button("Start Simulation");
        startSimulationButton.setId("start-button");
        final Button stopSimulationButton = new Button("Stop Simulation");
        stopSimulationButton.setId("stop-button");
        final Button saveDataButton = new Button("Save datas");
        final Button backToMenuButton = new Button("Back to menu");
        final Button reloadButton = new Button("Reload simulation");
        stopSimulationButton.setDisable(true);
        saveDataButton.setDisable(true);
        startSimulationButton.setOnAction(e -> {
            controller.startSimulation();
            startSimulationButton.setDisable(true);
            stopSimulationButton.setDisable(false);
        });
        stopSimulationButton.setOnAction(e -> {
            controller.stopSimulation();
            stopSimulationButton.setDisable(true);
            saveDataButton.setDisable(false);
            leftColumn.getChildren().removeAll(startSimulationButton, stopSimulationButton);
            leftColumn.getChildren().add(0, reloadButton);
        });
        reloadButton.setOnAction(e -> {
            controller.showView(primaryStage, new SimulationView(controller));
        });
        backToMenuButton.setOnAction(e -> {
            controller.showMainMenu(primaryStage);
        });
        leftColumn.getChildren().addAll(startSimulationButton, stopSimulationButton, saveDataButton, backToMenuButton);

        // Create the main container
        final HBox mainContainer = new HBox();
        mainContainer.setSpacing(CONTAINER_SPACING);
        mainContainer.getStyleClass().add("main-container");
        mainContainer.getChildren().addAll(leftColumn, centerColumn, rightColumn);

        final Scene scene = new Scene(mainContainer, width, height);
        scene.getStylesheets().add(SimulationViewOld.class.getResource(CSS_FILE).toExternalForm());
        primaryStage.setTitle("JavaDyno - Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    /**
     * Updates the graph with new data points.
     *
     * @param xValue the x-axis value to be added to the graph
     * @param yValue the y-axis value to be added to the graph
     * @param y2Value the second y-axis value to be added to the graph
     */
    public void updateGraph(final Number xValue, final Number yValue, final Number y2Value) {
        this.centerColumn.addPointToChart(xValue, yValue, y2Value);
    }

    /**
     * Updates the gauges with new values.
     *
     * @param rpm the current RPM value
     * @param speed the current speed value
     * @param temperature the current temperature value
     */
    public void updateGauges(final int rpm, final int speed, final int temperature) {
        this.rightColumn.updateGauges(rpm, speed, temperature);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        controller.closeApp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final ElaboratedData data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void begin(final Stage primaryStage) {
        this.start(primaryStage);
    }
}
