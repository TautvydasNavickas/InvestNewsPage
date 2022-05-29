package lt.codeacademy.springmvc.service;


import lombok.AllArgsConstructor;
import lt.codeacademy.springmvc.model.StockWrapper;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StockService {

        private final RefreshService refreshService;
    public StockWrapper findStock(String ticker) {


        try{
            return new StockWrapper(YahooFinance.get(ticker));
        }
        catch (IOException e){

        }
        return null;
    }

    public List<StockWrapper> findStocks(List<String> tickers){
        return tickers.stream().map(this::findStock).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public BigDecimal findPrice (StockWrapper stockWrapper) throws  IOException{
        return stockWrapper.getStock().getQuote(refreshService.refresher(stockWrapper)).getPrice();
    }




}
