package net.daw.helper;

/**
 *
 * @author Yolanda
 */
public class ValidateData {

    /**
     *
     * @param entero
     * @return
     */
    public static boolean validateId(String entero) {
        return entero.matches("^[1-9][0-9]*$");
    }
}
