package faker;

import beans.Attribute;
import static utils.Shared.faker;

/**
 *
 * @author Amirouche
 */
public class BlobDataFaker extends DataFaker {

    public BlobDataFaker(Attribute att) {
        super(att);
    }

    @Override
    public String generateNewValue() {
        return faker.regexify("[a-f0-9]{" + between() + "}");
    }
}
