/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package faker;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * A singoton class that defines the all possible type and its references of the
 * java faker api
 *
 * @author Maroine
 */
public class Types {

    public TreeMap<String, List<String>> TYPES_MAPPING = new TreeMap();
    private static Types types = null;

    /**
     *
     * @return the singloton instance
     */
    public static Types getInstances() {
        if (types == null) {
            types = new Types();
        }
        return types;
    }

    private Types() {
        TYPES_MAPPING.put("'system", Arrays.asList("'system"));
        TYPES_MAPPING.put("address", Arrays.asList("buildingNumber", "city", "cityName",
                "cityPrefix", "citySuffix", "country", "countryCode", "firstName", "lastName", "latitude",
                "longitude", "secondaryAddress", "state", "stateAbbr", "streetAddress",
                "streetAddressNumber", "streetName", "streetSuffix", "timeZone"));
        TYPES_MAPPING.put("app", Arrays.asList("author", "name", "version"));
        TYPES_MAPPING.put("beer", Arrays.asList("hop", "malt", "name", "style", "yeast"));
        TYPES_MAPPING.put("book", Arrays.asList("author", "genre", "publisher", "title"));
        TYPES_MAPPING.put("business", Arrays.asList("creditCardExpiry", "creditCardNumber", "creditCardType"));
        TYPES_MAPPING.put("color", Arrays.asList("name"));
        TYPES_MAPPING.put("code", Arrays.asList("isbn10", "isbn13"));
        TYPES_MAPPING.put("commerce", Arrays.asList("color", "department", "material", "productName"));
        TYPES_MAPPING.put("company", Arrays.asList("bs", "buzzword", "catchPhrase", "industry", "logo", "name",
                "profession", "suffix"));
        TYPES_MAPPING.put("crypto", Arrays.asList("md5", "sha1", "sha256", "sha512"));
        TYPES_MAPPING.put("educator", Arrays.asList("campus", "course", "secondarySchool", "university"));
        TYPES_MAPPING.put("finance", Arrays.asList("bic", "creditCard", "iban"));
        TYPES_MAPPING.put("hacker", Arrays.asList("bbreviation", "adjective", "ingverb", "noun", "verb"));
        TYPES_MAPPING.put("idNumber", Arrays.asList("invalid", "ssnValid", "valid"));
        TYPES_MAPPING.put("internet", Arrays.asList("avatar", "domainName", "domainSuffix", "domainWord",
                "emailAddress", "image", "password", "url"));
        TYPES_MAPPING.put("lorem", Arrays.asList("character", "characters", "paragraph", "sentence", "word", "words"));
        TYPES_MAPPING.put("name", Arrays.asList("firstName", "fullName", "lastName", "name",
                "nameWithMiddle", "prefix", "suffix", "title"));
        TYPES_MAPPING.put("phoneNumber", Arrays.asList("cellPhone", "phoneNumber"));
        TYPES_MAPPING.put("superhero", Arrays.asList("descriptor", "name", "power", "prefix", "suffix"));
        TYPES_MAPPING.put("team", Arrays.asList("creature", "name", "sport", "state"));
        TYPES_MAPPING.put("university", Arrays.asList("name", "prefix", "suffix"));

    }
}
