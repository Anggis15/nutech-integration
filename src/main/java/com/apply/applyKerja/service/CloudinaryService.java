package com.apply.applyKerja.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public static final String BASE_URL_IMAGE = System.getProperty("user.dir")+"/image-saved";
    private static Path rootPath;

    public String uploadFile(MultipartFile file){
        Map map ;
        Map<String,Object> mapResponse ;
        try{
            rootPath = Paths.get(BASE_URL_IMAGE);
            String strPathz = rootPath.toAbsolutePath().toString();
            saveToDirectory(file);
            map = cloudinary.uploader().upload(strPathz+"/"+file.getOriginalFilename(), ObjectUtils.asMap("public_id",file.getOriginalFilename()));
            mapResponse = convertResponseCloudinary(map);
            File fileToDelete = new File(strPathz+"/"+file.getOriginalFilename());
            if (fileToDelete.delete()) {
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("Failed to delete file.");
            }
            return String.valueOf(mapResponse.get("secure_url"));
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