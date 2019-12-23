package ru.npptmk.uray_pressure_reg.gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author RazumnovAA
 */
public class ChartPanel_RealTimePressureGraph extends ChartPanel {

    public void setNewDataset(XYDataset xYDataset) {
        this.setChart(configureRealTimeGraph(
                xYDataset,
                "График набора и выдержки давления",
                "Время в секундах",
                "Давление в МПа",
                100f));
    }

    
    public ChartPanel_RealTimePressureGraph(JFreeChart chart) {
        super(chart);
    }

    public ChartPanel_RealTimePressureGraph() {
        super(configureRealTimeGraph(
                createEmptyDataSet(),
                "График набора и выдержки давления",
                "Время в секундах",
                "Давление в МПа",
                100f));
    }

    private static JFreeChart configureRealTimeGraph(
            XYDataset dataset,
            String Caption,
            String xCaption,
            String yCaption,
            Float testTime) {
        // create the chart...
        JFreeChart chart = ChartFactory.createXYLineChart(
                Caption, // chart title
                xCaption, // x axis label
                yCaption, // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                false, // include legend
                true, // tooltips
                false // urls
        );
        // get a reference to the plot for further customisation...
        /*XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer
                = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseToolTipGenerator(new XYToolTipGenerator() {
            @Override
            public String generateToolTip(XYDataset xyd, int i, int i1) {
                return (String) ((XYSeries) ((XYSeriesCollection) xyd).getSeries().get(i)).getKey() + " на " + xyd.getXValue(i, i1);
            }

        });
        //Устанавливаем длину оси х
        plot.getDomainAxis().setRange(0, testTime / 100 * 100);
        renderer.setSeriesShape(0, new Rectangle2D.Double(0, 0, 1, 100));
        renderer.setSeriesShape(1, new Rectangle2D.Double(0, 0, 1, 100));
        renderer.setSeriesShape(2, new Rectangle2D.Double(0, 0, 1, 100));
        renderer.setBaseShapesVisible(true);
        renderer.setBaseLinesVisible(false);*/
        return chart;
    }

    private static XYDataset createEmptyDataSet() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        return dataset;

    }
}
