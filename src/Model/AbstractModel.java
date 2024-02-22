package Model;

import java.util.Map;

/**
 * Superclasse rappresentante una Model dell'applicativo, ogni Model deve possedere il metodo getData();
 */
public abstract class AbstractModel {
    /**
     * Metodo che restituisce tutti gli attributi della Model come HashMap di coppie chiave-valore
     * @return
     */
    public abstract Map<String, Object> getData();
}
