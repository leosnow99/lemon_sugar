package com.sugar.client;

import com.sugar.client.scanner.Scan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author bytedance
 */
@SpringBootApplication
public class ClientApplication  implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scan scan = new Scan() ;
        Thread thread = new Thread(scan);
        thread.setName("scan-thread");
        thread.start();
    }
}
