package faker;

import beans.Attribute;

/**
 * Generate values for Boolean type
 *
 * @author Amirouche
 */
public class BooleanDataFaker extends DataFaker {

    /**
     *
     *
     * @param att
     */
    public BooleanDataFaker(Attribute att) {
        super(att);
    }

    /**
     * Generate boolean value between. 1 for true and O for false
     *
     * @return O or 1
     */
    @Override
    protected String generateNewValue() {
        return between(0, 1) + "";
    }

}
