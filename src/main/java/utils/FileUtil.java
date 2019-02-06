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
 *
 * @author amirouche
 */
public class FileUtil {

    public static void writeFiles(String nameFile, String strToWrite) {
        FileWriter file;
        try {
            file = new FileWriter(new File(nameFile));
            file.write(strToWrite);
            file.close();
        } catch (IOException ex) {

        }
    }

    public static void writeObjectInFile(String mPathFile, Object object) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(mPathFile);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(object);
    }

    public static Object readFileObject(String mPathFile) throws Exception {
        Object result = null;
        FileInputStream fileInputStream = new FileInputStream(mPathFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        return result = objectInputStream.readObject();
    }
}
