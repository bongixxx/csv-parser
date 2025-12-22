import model.SaleRecord;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DataBase {
    private static Connection conn;
    private static Statement statmt;
    private static ResultSet resSet;
    public static void connection(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:Sales.db3");
            System.out.println("База подключена");
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static void createDb(){
        try{
            statmt=conn.createStatement();
            statmt.execute("CREATE TABLE IF NOT EXISTS 'Sales'" +
                    "('id' INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "'Region' text,"+
                    "'Country' text,"+
                    "'ItemType' text,"+
                    "'SalesChannel' text,"+
                    "'OrderPriority' text,"+
                    "'OrderDates' DATE,"+
                    "'UnitsSold' int,"+
                    "'TotalProfit' float)");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        System.out.println("Таблица создана или уже существует");
    }
    public static void writeDB(List<SaleRecord> sales){
        for (SaleRecord sale : sales){
            try {
                statmt.execute(String.format(Locale.ENGLISH, "INSERT INTO 'Sales' " +
                                "(Region, Country, ItemType, SalesChannel, OrderPriority, OrderDates, UnitsSold, TotalProfit)" +
                                "VALUES ('%s','%s','%s','%s','%s','%s',%d,%f);",
                        sale.getRegion(), sale.getCountry(), sale.getItemType(),sale.getSalesChannel(),
                        sale.getOrderPriority(),sale.getOrderDate(),sale.getUnitsSold(),sale.getTotalProfit()));
            } catch (SQLException e){
                throw new RuntimeException(e);
            }

        }
        System.out.println("Таблица заполнена");
    }
    public static Map<String, Integer> getUnitsSoldByRegion() {

        Map<String, Integer> result = new HashMap<>();

        String sql = "SELECT Region, SUM(UnitsSold) AS total " +
                "FROM Sales GROUP BY Region";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String region = rs.getString("Region");
                int total = rs.getInt("total");
                result.put(region, total);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
    public static void printTopCountryByProfitEuropeAsia() {

        String sql =
                "SELECT Country, SUM(TotalProfit) AS total_profit " +
                        "FROM Sales " +
                        "WHERE Region IN ('Europe', 'Asia') " +
                        "GROUP BY Country " +
                        "ORDER BY total_profit DESC " +
                        "LIMIT 1";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                String country = rs.getString("Country");
                double profit = rs.getDouble("total_profit");

                System.out.printf(
                        "Страна с самым высоким доходом (Europe + Asia): %s — %, .2f%n",
                        country,
                        profit
                );

            } else {
                System.out.println("Данные не найдены");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void printTopCountryByProfitInRangeAfrica() {

        String sql =
                "SELECT Country, SUM(TotalProfit) AS total_profit " +
                        "FROM Sales " +
                        "WHERE Region IN ('Middle East and North Africa', 'Sub-Saharan Africa') " +
                        "GROUP BY Country " +
                        "HAVING total_profit BETWEEN 420000 AND 440000 " +
                        "ORDER BY total_profit DESC " +
                        "LIMIT 1";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                String country = rs.getString("Country");
                double profit = rs.getDouble("total_profit");

                System.out.printf(
                        "Страна с максимальным доходом (420 000 – 440 000) " +
                                "в регионах Middle East and North Africa и Sub-Saharan Africa: " +
                                "%s — %, .2f%n",
                        country,
                        profit
                );
            } else {
                System.out.println(
                        "Страна с доходом в диапазоне 420k–440k не найдена"
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void clearSalesTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Sales");
            stmt.execute("DELETE FROM sqlite_sequence WHERE name='Sales'");
            System.out.println("Таблица очищена, автоинкремент сброшен");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
