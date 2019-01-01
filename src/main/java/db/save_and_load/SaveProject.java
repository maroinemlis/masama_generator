/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.save_and_load;

import db.bean.SQLSchema;
import db.bean.Table;
import db.utils.FileUtil;
import java.io.*;

/**
 *
 * @author asma
 */
public class SaveProject {

    SQLSchema sqlSchema;

    /**
     * Constructor for class SaveProject
     *
     * @param sqlSchema The name of the attribute
     */
    public SaveProject(SQLSchema sqlSchema) {
        this.sqlSchema = sqlSchema;
    }

    /**
     * return true if it is saved successfully else false
     *
     * @param path
     * @return boolean
     */
    public boolean saveSQLSchema(String path) {
        boolean success = (new File(path)).mkdirs();
        if (!success) {
        }
        String mPathFile = path + "/txt.txt";
        FileUtil fileUtil = new FileUtil();

        fileUtil.writeObjectInFile(mPathFile, sqlSchema);

        SQLSchema sqlSchema1 = (SQLSchema) fileUtil.readFileObject(mPathFile);

        System.err.println("-----------------From file ");
        for (Table table1 : sqlSchema1.getTables()) {
            System.out.println(table1);

        }

        return true;
    }
}
