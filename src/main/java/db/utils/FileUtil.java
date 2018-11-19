/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import db.bean.Attribute;
import db.save_and_load.projecte.SaveProject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public static void writeObjectInFiles(String mPathFile, Object object) {                    
        try (FileOutputStream fileOutputStream=new FileOutputStream(mPathFile);
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);){                        
            objectOutputStream.writeObject(object);            
        } catch (FileNotFoundException ex) {
            System.err.println("db.utils.FileUtil.writeObjectInFiles() 1");
            Logger.getLogger(SaveProject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println("db.utils.FileUtil.writeObjectInFiles() 2");
            Logger.getLogger(SaveProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Object readFilesObjec(String mPathFile) {
        Object result=null;
        
        try (FileInputStream fileInputStream =new FileInputStream(mPathFile);                                
            ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);){                        
            result=objectInputStream.readObject();            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaveProject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaveProject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
