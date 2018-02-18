package se.parthenope.se.parthenope.rest.util;

import com.fasterxml.uuid.Generators;

/**
 * Created by luigi.ferrara on 2018-02-17.
 */
public class UidUtil {

        public static String uuid(String name) {
            return Generators.nameBasedGenerator().generate(name).toString();
        }

}
