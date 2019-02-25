/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Contains the methods used to manipulate with the file object.
 *
 * @author amirouche
 */
public class FileUtil {

    /**
     * Write String passed in parameter in a new File.
     *
     * @param nameFile name file to write
     * @param strToWrite
     */
    public static void writeFiles(String nameFile, String strToWrite) {
        FileWriter file;
        try {
            file = new FileWriter(new File(nameFile));
            file.write(strToWrite);
            file.close();
        } catch (IOException ex) {

        }
    }

    /**
     * Serialize the object in the file.
     *
     * @param mPathFile path of file
     * @param object to serialize
     * @throws Exception if cannot be opened the file
     */
    public static void writeObjectInFile(String mPathFile, Object object) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(mPathFile);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(object);
    }

    /**
     * Deserialize the Object from file. return the object deserialized
     *
     * @param mPathFile path of file
     * @return object deserialized
     * @throws Exception
     */
    public static Object readFileObject(String mPathFile) throws Exception {
        Object result = null;
        FileInputStream fileInputStream = new FileInputStream(mPathFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        return result = objectInputStream.readObject();
    }
}
