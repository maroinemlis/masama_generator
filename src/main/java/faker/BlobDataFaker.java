/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package faker;

import bean.Attribute;
import static utils.Shared.faker;

/**
 *
 * @author Maroine
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
