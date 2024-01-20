package GUI;

import Controller.AppController;

import javax.swing.*;
import java.awt.*;

public abstract class AppView {
    AppController controller;
    private Dimension dimension = null;
    private String title = "Libreria Digitale";

    public AppView(AppController appController) {
        this.controller = appController;
    }

    protected void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    protected void setTitle(String title) {
        this.title = title;
    }


    /**
     * Funzione che restituisce l'AppController che gestisce questa view.
     * @see AppController
     */
    public AppController getAppController() {
        return this.controller;
    }

    /**
     * Funzione che restituisce la dimensione definita dalla view per il suo frame.
     */
    public Dimension getDimension() {
        return dimension;
    }

    /**
     * Funzione che restituisce il titolo della view.
     */
    public String getTitle() {
        return title;
    }

    public abstract JPanel getContentPane();
}
