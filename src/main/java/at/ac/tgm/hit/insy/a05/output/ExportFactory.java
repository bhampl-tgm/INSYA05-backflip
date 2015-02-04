package at.ac.tgm.hit.insy.a05.output;

/**
 * Returns the correct exporting object depending on the chosen format.
 *
 * @author Martin Kritzl [mkritzl@student.tgm.ac.at]
 */
public class ExportFactory {

    /**
     * Returns the correct exporting object depending on the chosen format.
     *
     * @param format the required name of the format
     * @return an exportable Object depending on the format
     *
     * @throws java.lang.IllegalArgumentException When the given format is not available
     */
    public static Exportable chooseExport(String format) throws IllegalArgumentException{
        if (format.equalsIgnoreCase("EER"))
            return new ExportEERDotfile();
        else if(format.equalsIgnoreCase("RM"))
            return new ExportRMHTML();
        throw new IllegalArgumentException("The format \"" + format + "\" is not supported");
    }

}
