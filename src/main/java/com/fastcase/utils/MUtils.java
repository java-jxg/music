package com.fastcase.utils;

import com.fastcase.entity.Singer;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class MUtils {

    public static boolean uploadFile(MultipartFile file,String fileName,String filePath){
        if(file.isEmpty()) return false;
        File f = new File(filePath);
        if(!f.exists()){
            f.mkdir();
        }
        //实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        try {
            file.transferTo(dest);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
