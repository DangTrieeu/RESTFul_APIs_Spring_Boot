package vn.hoidanit.jobhunter.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import vn.hoidanit.jobhunter.domain.response.file.ResUploadFileDTO;
import vn.hoidanit.jobhunter.service.FileService;
import vn.hoidanit.jobhunter.util.annotation.APIMessage;
import vn.hoidanit.jobhunter.util.error.StorageException;

@RestController
@RequestMapping("/api/v1")
public class FileController {
    private final FileService fileService;
    @Value("${hoidanit.upload-file.base-uri}")
    private String baseUri;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("files")
    @APIMessage("Upload file to server")
    public ResponseEntity<ResUploadFileDTO> uploadFile(
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestParam("folder") String folder

    ) throws URISyntaxException,
            IOException, StorageException {
        // Validate the file
        if (file == null || file.isEmpty()) {
            throw new StorageException("File is empty. Please select a file to upload.");
        }
        // check file extension
        String fileName = file.getOriginalFilename();
        List<String> allowedExtensions = Arrays.asList("pdf", "jpg", "jpeg", "png", "doc", "docx");
        boolean isValidExtension = allowedExtensions.stream()
                .anyMatch(ext -> fileName.toLowerCase().endsWith(ext));
        if (!isValidExtension) {
            throw new StorageException("Invalid file extension. Just allow " + allowedExtensions.toString());
        }

        // Validate the folder name
        this.fileService.createDirectory(baseUri + folder);

        // store file
        String uploadFile = this.fileService.store(file, folder, baseUri);
        // create response
        ResUploadFileDTO uploadFileResponse = new ResUploadFileDTO(uploadFile, Instant.now());

        return ResponseEntity.ok().body(uploadFileResponse);
    }
}
