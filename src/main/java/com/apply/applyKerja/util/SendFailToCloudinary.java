package com.apply.applyKerja.util;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 04/12/24 21.33
@Last Modified 04/12/24 21.33
Version 1.0
*/

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SendFailToCloudinary {

    final private Cloudinary cloudinary = new Cloudinary();
    public static final String BASE_URL_IMAGE = System.getProperty("user.dir")+"/image-saved";
    private static Path rootPath;

    public String uploadFile(MultipartFile file){
        Map map ;
        Map<String,Object> mapResponse ;
        try{
            rootPath = Paths.get(BASE_URL_IMAGE+"/"+new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date()));
            String strPathz = rootPath.toAbsolutePath().toString();
            System.out.println("STR PATHZ : "+strPathz);
            saveToDirectory(file);
            map = cloudinary.uploader().upload(strPathz+"\\"+file.getOriginalFilename(), ObjectUtils.asMap("public_id",file.getOriginalFilename()));
//            https://res.cloudinary.com/dt4ldbmza/image/upload/v1728145401/norilisa.jpg.png
            mapResponse = convertResponseCloudinary(map);
            for (Map.Entry<String,Object> entry : mapResponse.entrySet()) {
                System.out.println("key : "+entry.getKey()+" value : "+entry.getValue() );
            }
            return String.valueOf(mapResponse.get("secure_url"));
            //SAAT DISINI SIMPAN SEMUA INFORMASI YANG DIBUTUHKAN KE TABLE DI DATABASE
            //LALU SAAT USER MELAKUKAN REQUEST NANTI LANGKAH-LANGKAH NYA CEK TERLEBIH DAHULU KE DATABASE DAN BERIKAN URL YANG SUDAH KITA RESPONSE TADI KE CLIENT
            // JADI PIHAK CLIENT AKAN MELAKUKAN CALL URL YANG SUDAH KITA BERIKAN TADI
            // JIKA SAAT KLIEN MENDAPATI ERROR DARI CLOUDINARY BERDASARKAN URL YANG SUDAH KITA BERIKAN TADI, OTOMATIS KLIEN AKAN MELAKUKAN HIT KE URL REUPLOAD
            // KEMUDIAN KITA AKAN MELAKUKAN SEPERTI STEP AWAL SAMPAI LANGKAH SAAT INI
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public void saveToDirectory(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Gagal Untuk menyimpan File kosong !!");
            }
            Path destinationFile = this.rootPath.resolve(Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootPath.toAbsolutePath())) {
                // This is a security check
                throw new IllegalArgumentException(
                        "Tidak Dapat menyimpan file diluar storage yang sudah ditetapkan !!");
            }
            Files.createDirectories(this.rootPath);
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("Failed to store file.", e);
        }
    }

    private Map<String,Object> convertResponseCloudinary(Map m){
        Map<String,Object> map = new HashMap<>();
        map.put("secure_url",m.get("secure_url"));
        map.put("created_at",m.get("created_at"));
        map.put("resource_type",m.get("resource_type"));
        map.put("type",m.get("type"));
        m.clear();
        return map;
    }
}
