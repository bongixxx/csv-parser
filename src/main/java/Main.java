import model.SaleRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main{
    public static void main(String[] args) {
        List<SaleRecord> list = new ArrayList<>();
        list = CsvSaleParser.getSalesFromCsv("C:\\java\\csv-parser\\src\\main\\resources\\data.csv");
        DataBase.connection();
        DataBase.createDb();
        DataBase.clearSalesTable();
        DataBase.writeDB(list);
        // Агрегация
        Map<String, Integer> stats =
                DataBase.getUnitsSoldByRegion();
        // График
        SalesChart.showUnitsSoldByRegion(stats);
        // Вторая задача
        System.out.printf("2 задача.\nВыведите в консоль страну с самым высоким общим доходом среди регионов Европы и Азии\n");
        DataBase.printTopCountryByProfitEuropeAsia();
        // Третья задача
        System.out.printf("3 задача.\nНайдите страну, у которой общий доход от 420 тыс до 440 тыс,\nсреди регионов Ближний Восток и Северная Африка(Middle East and North Africa) и Субсахарская Африка(Sub-Saharan Africa), с самым высоким общим доходом.\n");
        DataBase.printTopCountryByProfitInRangeAfrica();
    }
}