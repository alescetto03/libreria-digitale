package GUI;

import Controller.AppController;

import javax.swing.*;
import java.awt.*;

/**
 * Superclasse rappresentante una View dell'applicativo
 */
public abstract class AppView {
    AppController controller;
    private Dimension dimension = null;
    private String title = "Libreria Digitale";

    public AppView(AppController appController) {
        this.controller = appController;
    }

    /**
     * Metodo che setta la dimensione della finestra
     * @param dimension
     */
    protected void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    /**
     * Funzione che setta il titolo della view
     * @param title
     */
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

    /**
     * Funzione che restituisce il content pane della view.
     * Il content pane è il JPanel che contiene tutti gli elementi presenti all'interno della view
     * @return
     */
    public abstract JPanel getContentPane();
}
