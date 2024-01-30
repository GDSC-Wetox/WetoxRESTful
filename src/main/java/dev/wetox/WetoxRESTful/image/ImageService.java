package dev.wetox.WetoxRESTful.image;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import dev.wetox.WetoxRESTful.exception.ProfileImageIOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final Storage storage;

    private String bucketName = "wetox-bucket";

    public String uploadImage(MultipartFile file){
        String imageUuid = UUID.randomUUID().toString();
        BlobId blobId = BlobId.of(bucketName, imageUuid);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();
        try {
            storage.create(blobInfo, file.getBytes());
        } catch (IOException e) {
            throw new ProfileImageIOException();
        }
        return imageUuid;
    }

    public String getImageUrl(String uuid) {
        return "https://storage.googleapis.com/" + bucketName + "/" + uuid;
    }
}
