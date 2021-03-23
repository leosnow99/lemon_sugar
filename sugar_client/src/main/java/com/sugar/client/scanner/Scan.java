package com.sugar.client.scanner;

/**
 * @author bytedance
 */

import com.sugar.client.util.HandleCommand;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

import static com.sugar.client.util.HandleCommand.help;

@Slf4j
public class Scan implements Runnable {
    @Override
    public void run() {
        help();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();

            HandleCommand.handle(command);
        }

    }


}
