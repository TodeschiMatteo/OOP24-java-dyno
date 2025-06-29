package it.unibo.javadyno.view.impl.component;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.ChartViewer;

import it.unibo.javadyno.model.graph.api.ChartsFactory;
import it.unibo.javadyno.model.graph.api.ChartsManager;
import it.unibo.javadyno.model.graph.impl.ChartsManagerImpl;
import it.unibo.javadyno.model.graph.impl.DefaultChartsFactory;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

/**
 * ChartsPanel class for displaying charts in the JavaDyno application.
 */
public final class ChartsPanel extends VBox {
    private static final String CSS_CHARTS_PANEL_TAG = "charts-panel";
    private static final String BG_IMAGE = "/images/logo_no_bg.png";
    private static final double CHART_HEIGH_FACTOR = 0.7;
    private static final double CHART_WIDTH_FACTOR = 0.6;
    private static final double CHART_MINIMUM_FACTOR = 0.5;

    private static final String CHARTS_NAME = "RPM vs Power";
    private static final String X_AXIS_LABEL = "RPM (Revolutions Per Minute)";
    private static final String Y_AXIS_LABEL = "Horsepower (HP)";
    private static final String Y2_AXIS_LABEL = "Torque (Nm)";
    private static final String SERIES_NAME = "Power";

    private final JFreeChart lineChart;
    private final ChartsFactory chartsFactory = new DefaultChartsFactory();
    private final ChartsManager chartManager = new ChartsManagerImpl();

    /**
     * Default constructor for ChartsPanel.
     */
    public ChartsPanel() {
        this.setAlignment(Pos.TOP_RIGHT);
        this.getStyleClass().add(CSS_CHARTS_PANEL_TAG);
        final Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        this.lineChart = chartsFactory.createEmptyCharts(
            CHARTS_NAME,
            X_AXIS_LABEL,
            Y_AXIS_LABEL
        );
        final ChartViewer viewer = new ChartViewer(this.lineChart);
        viewer.setPrefSize(screenBounds.getWidth() * CHART_WIDTH_FACTOR, screenBounds.getHeight() * CHART_HEIGH_FACTOR);
        viewer.setMinSize(screenBounds.getWidth() * CHART_MINIMUM_FACTOR, screenBounds.getHeight() * CHART_MINIMUM_FACTOR);
        chartManager.addNewSeries(this.lineChart, SERIES_NAME, ChartsManager.YAxisLevel.FIRST);
        chartManager.addYAxis(this.lineChart, Y2_AXIS_LABEL);
        chartManager.addNewSeries(this.lineChart, SERIES_NAME, ChartsManager.YAxisLevel.SECOND);
        chartManager.setDarkTheme(this.lineChart);
        chartManager.setBackgroundImage(this.lineChart, BG_IMAGE);
        this.getChildren().add(viewer);
    }

    /**
     * Adds a point to the speed chart.
     *
     * @param xValue the x-axis value
     * @param yValue the y-axis value
     * @param y2Value the second y-axis value
     */
    public void addPointToChart(final Number xValue, final Number yValue, final Number y2Value) {
        chartManager.addPointToSeries(
            this.lineChart,
            SERIES_NAME,
            ChartsManager.YAxisLevel.FIRST,
            xValue,
            yValue
        );
        chartManager.addPointToSeries(
            this.lineChart,
            SERIES_NAME,
            ChartsManager.YAxisLevel.SECOND,
            xValue,
            y2Value
        );
    }

    /**
     * Sets the visibility of the background image.
     *
     * @param visible true to show the background image, false to hide it
     */
    public void setBackgroundVisible(final boolean visible) {
        if (visible) {
            chartManager.setBackgroundImage(this.lineChart, BG_IMAGE);
        } else {
            chartManager.resetBackgroundImage(this.lineChart);
        }
    }
}
