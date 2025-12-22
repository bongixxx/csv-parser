import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import model.OrderPriority;
import model.SaleRecord;
import model.DateParser;
import model.SalesChannel;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsvSaleParser {

    public static List<SaleRecord> getSalesFromCsv(String path) {
        List<SaleRecord> result = new ArrayList<>();
        try (FileReader fileReader = new FileReader(path)) {
            CSVReader reader = new CSVReaderBuilder(fileReader)
                    .withCSVParser(
                            new CSVParserBuilder()
                                    .withSeparator(';')
                                    .build()
                    )
                    .build();
            String[] record;
            reader.readNext();
            while ((record = reader.readNext()) != null) {
                String region = record[0];
                String country = record[1];
                String itemType = record[2];
                SalesChannel salesChannel = SalesChannel.valueOf(record[3].trim().toUpperCase());
                OrderPriority orderPriority = OrderPriority.valueOf(record[4].trim().toUpperCase());
                LocalDate orderDate = DateParser.parse(record[5]);
                int unitsSold = Integer.parseInt(record[6]);
                double totalProfit = Double.parseDouble(record[7]);
                SaleRecord saleRecord = new SaleRecord(region,country,itemType,salesChannel,orderPriority,orderDate,unitsSold,totalProfit);
                result.add(saleRecord);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
