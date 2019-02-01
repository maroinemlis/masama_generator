/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.main;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 *
 * @author tamac
 */
public class TaskAsync {

    public TaskAsync(Consumer<Integer> c) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                c.accept(null);
            }
        });
    }

}
