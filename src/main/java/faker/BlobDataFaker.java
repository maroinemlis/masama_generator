package faker;

import beans.Attribute;
import static utils.Shared.faker;

/**
 * Generate values for Blob type
 *
 * @author Amirouche
 */
public class BlobDataFaker extends DataFaker {

    public BlobDataFaker(Attribute att) {
        super(att);
    }

    /**
     * Generate values for Blob type. With the size between the from and to
     * value
     *
     * @return
     */
    @Override
    public String generateNewValue() {
        return faker.regexify("[a-f0-9]{" + between() + "}");
    }
}
