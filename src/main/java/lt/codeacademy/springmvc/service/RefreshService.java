package lt.codeacademy.springmvc.service;

import lt.codeacademy.springmvc.model.StockWrapper;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class RefreshService {
    private  final Map<StockWrapper, Boolean> stockRefresh;
    private static final ScheduledExecutorService sheduler = Executors.newScheduledThreadPool(1);
    private static final Duration refreshPeriod = Duration.ofSeconds(15);


    public RefreshService(Map<StockWrapper, Boolean> stockRefresh) {
        this.stockRefresh = stockRefresh;
    }

    public boolean refresher(final  StockWrapper stockWrapper){
        if(!stockRefresh.containsKey(stockWrapper)){
            stockRefresh.put(stockWrapper, false);
            return true;
        }
        return stockRefresh.get(stockWrapper);
    }


    private void setStockRefresh(){
        sheduler.scheduleAtFixedRate(() ->
            stockRefresh.forEach((stock, value) -> {
                if (stock.getLocalDateTime().isBefore(LocalDateTime.now().minus(refreshPeriod))){
                    System.out.println("Refresh" + stock.getStock().getSymbol());
                    stockRefresh.remove(stock);
                    stockRefresh.put(stock.withLocalDateTime(LocalDateTime.now()), true);
                }
            }), 0, 15, TimeUnit.SECONDS);

    }

}
