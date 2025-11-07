package com.buy01.media.service;

import com.buy01.media.client.ProductClient;
import com.buy01.media.exception.ForbiddenException;
import com.buy01.media.exception.NotFoundException;
import com.buy01.media.repository.MediaRepository;
import com.buy01.media.exception.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import com.buy01.media.model.Media;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class MediaService {
    private final MediaRepository mediaRepository;
    private final ProductClient productClient;
    private final Path storagePath;
    private final Path avatarPath;

    private static final String UPLOAD_DIR = "uploads";
    private static final String AVATAR_DIR = "avatar";

    public MediaService(MediaRepository mediaRepository, ProductClient productClient) throws IOException {
        this.mediaRepository = mediaRepository;
        this.productClient = productClient;

        this.storagePath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
        Files.createDirectories(storagePath);

        this.avatarPath = storagePath.resolve(AVATAR_DIR);
        Files.createDirectories(avatarPath);
    }

    // creates path and saves the image to uploads
    public Media saveImage(MultipartFile file, String productId, String userId,  boolean isAdmin) {

        authorizeUser(productId, userId, isAdmin);

        String extension = Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID() + "." + extension;
        String path = storeFile(file, storagePath.toString(), fileName); // making sure all unique filenames

        Media media = new Media();
        media.setPath(path);
        media.setProductId(productId);

        return mediaRepository.save(media);


    }

    public void deleteMedia(@PathVariable String id, String userId, boolean isAdmin) {
        // validate that media exists
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Media not found"));

        authorizeUser(media.getProductId(), userId, isAdmin);

        // delete from repository after validation
        mediaRepository.deleteById(id);
    }

    public String saveUserAvatar(MultipartFile file) {
        String extension = Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID() + "." + extension;
        return storeFile(file, avatarPath.toString(), fileName);
    }

    // validating the file before storing
    private String storeFile(MultipartFile file, String directory, String filename) {
        validateFile(file);
        try {
            Files.createDirectories(Paths.get(directory));
            Path filePath = Paths.get(directory).resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (IOException e) {
            throw new FileUploadException("Failed to store file", e);
        }
    }


    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileUploadException("File is empty");
        }
        if (file.getSize() > 2 * 1024 * 1024) {
            throw new FileUploadException("File too large, max 2MB");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new FileUploadException("Invalid file type");
        }
    }

    // throw Forbidden if user is not admin nor productOwner
    private void authorizeUser(String productId, String userId, boolean isAdmin) {
        // validate that the user has right to delete the media
        boolean isOwner = productClient.isOwner(productId, userId);

        if (!isAdmin || !isOwner) {
            throw new ForbiddenException("Only admin or owner can delete media");
        }
    }

}
