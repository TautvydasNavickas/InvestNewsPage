package lt.codeacademy.springmvc.service;


import lombok.AllArgsConstructor;
import lt.codeacademy.springmvc.model.StockWrapper;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class StockService {

    public StockWrapper findStock(String ticker) {


        try{
            return new StockWrapper(YahooFinance.get(ticker));
        }
        catch (IOException e){

        }
        return null;
    }

    public BigDecimal findPrice (StockWrapper stockWrapper) throws  IOException{
        return stockWrapper.getStock().getQuote(true).getPrice();
    }




}
