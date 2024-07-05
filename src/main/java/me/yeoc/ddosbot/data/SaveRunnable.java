package me.yeoc.ddosbot.data;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class SaveRunnable extends Thread{

    IDatabase database;
    @Override @SneakyThrows
    public void run() {
        while (true){
            Thread.sleep(5000);
            database.saveData();

        }
    }
}
