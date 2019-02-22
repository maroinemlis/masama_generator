/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.main;

import java.util.concurrent.CompletableFuture;

/**
 *
 * @author Maroine
 */
public class TaskAsync {

    public TaskAsync(Runnable c) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(c);
    }

}
