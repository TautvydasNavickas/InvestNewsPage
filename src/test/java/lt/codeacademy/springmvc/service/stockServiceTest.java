package lt.codeacademy.springmvc.service;


import lombok.extern.slf4j.Slf4j;
import lt.codeacademy.springmvc.model.StockWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootTest
 class stockServiceTest {

    @Autowired
    private StockService stockService;


    @Test
    void callingStock() throws IOException {

        final StockWrapper stockWrapper = stockService.findStock("UU.L");
        System.out.println(stockWrapper.getStock());

        final BigDecimal price = stockService.findPrice(stockWrapper);
        System.out.println(price);
    }

    @Test
    void multiple() throws  IOException, InterruptedException{
        final List<StockWrapper> stocks = stockService.findStocks(Arrays.asList("GOOG", "AMZM"));
        stockService.findPrice((StockWrapper) stocks);

        Thread.sleep(16000);

        final StockWrapper stockWrapper = stockService.findStock("AA.L");
        stocks.add(stockWrapper);

        System.out.println(stockService.findPrice(stockWrapper));

    }
}
