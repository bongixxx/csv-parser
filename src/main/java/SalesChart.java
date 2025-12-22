import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Map;

public class SalesChart {

    public static void showUnitsSoldByRegion(Map<String, Integer> data) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            dataset.addValue(
                    entry.getValue(),
                    "Units Sold",
                    entry.getKey()
            );
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Общее количество проданных товаров по регионам",
                "Регион",
                "Количество проданных товаров",
                dataset
        );

        ChartFrame frame = new ChartFrame("Sales Chart", chart);
        frame.pack();
        frame.setVisible(true);
    }
}
