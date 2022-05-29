package lt.codeacademy.springmvc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import yahoofinance.Stock;

import java.time.LocalDateTime;

@Getter
@With
@AllArgsConstructor
public class StockWrapper {

    private final Stock stock;
    private final LocalDateTime localDateTime;

    public StockWrapper(Stock stock){
        this.stock = stock;
        localDateTime = LocalDateTime.now();
    }

}
