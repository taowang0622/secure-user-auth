package io.github.taowang0622.web.controller;

import io.github.taowang0622.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

@RestController
@RequestMapping("/file")
public class FileController {
    private final String folder = "D:/JAVA_PROJECTS/secure-user-auth/security-demo/src/main/java/io/github/taowang0622/web/controller/";

    @PostMapping
    public FileInfo upload(@RequestParam("file") MultipartFile f) throws IOException {
        System.out.println(f.getName());
        System.out.println(f.getOriginalFilename());
        System.out.println(f.getSize());


        File localFile = new File(folder, new Date().getTime() + ".txt");
        f.transferTo(localFile);

        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(InputStream is = new FileInputStream(new File(folder, id + ".txt"));
        OutputStream os = response.getOutputStream();){
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");

            IOUtils.copy(is, os);
            os.flush();
        }
    }
}
