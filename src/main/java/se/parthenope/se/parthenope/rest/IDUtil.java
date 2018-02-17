package se.parthenope.se.parthenope.rest;

import com.fasterxml.uuid.Generators;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by luigi.ferrara on 2018-02-17.
 */
public class IDUtil {


        public static String uuid(String name) {
            return Generators.nameBasedGenerator().generate(name).toString();
        }

}
