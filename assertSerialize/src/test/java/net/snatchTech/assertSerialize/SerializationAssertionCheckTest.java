package net.snatchTech.assertSerialize;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SerializationAssertionCheckTest {

    private final String fileName = "/tmp/check.ser";
    private final Path filePath = Paths.get(fileName);

    private void checkAndDeleteTmpFile() throws IOException {
        Files.deleteIfExists(filePath);
    }

    // Try this one method first
    @Test
    public void shouldSuccessSerializeToFile() throws IOException, ClassNotFoundException {

        checkAndDeleteTmpFile();

        assertFalse(Files.exists(filePath));

        // step 1 - create and serialize an object
        SerializationAssertionCheck check = new SerializationAssertionCheck();

        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(check);
        }

        assertTrue(Files.exists(filePath));

        // step 2 - deserialize the object
        SerializationAssertionCheck check2;
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            check2 = (SerializationAssertionCheck) in.readObject();
        }

        assertEquals(check, check2);

    }

    // Try this method after commenting 'assert' command in the SerializationAssertionCheck class
    @Test
    public void shouldFailDuringDeserialization() throws IOException {

        Exception exception = assertThrows(InvalidClassException.class,
            () -> {
                try (FileInputStream fileIn = new FileInputStream(fileName);
                        ObjectInputStream in = new ObjectInputStream(fileIn)) {

                    SerializationAssertionCheck check = (SerializationAssertionCheck) in.readObject();
                    System.out.println(check.getSomeNumber());
                }
            }
        );

        // java.io.InvalidClassException: net.snatchTech.assertSerialize.SerializationAssertionCheck;
        // local class incompatible: stream classdesc serialVersionUID = 7171967375950265774,
        // local class serialVersionUID = 6475217375515940924

        assertTrue(exception.getMessage().contains("local class incompatible"));

        checkAndDeleteTmpFile();

    }
}
