package utils;

import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class ConcurrentReturner {

    record Login(String login) {
    }

    public static void main(String[] args) throws InterruptedException {
        RestTemplate rt = new RestTemplate();

        ExecutorService e = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            CompletableFuture.runAsync(() -> {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    rt.postForEntity("http://localhost:8081/movies/{id}/return", new Login("gpiwowarek@gmail.com"), Void.class, new Random().nextInt(1000));
                }
            }, e);
        }

        e.shutdown();
        e.awaitTermination(1, TimeUnit.MINUTES);
    }

    /**
     *curl --header "Content-Type: application/json" \
     *   --request POST \
     *   --data '{ "login":"gpiwowarek@gmail.com"}' \
     *  http://localhost:8081/movies/2/return
     */
}
