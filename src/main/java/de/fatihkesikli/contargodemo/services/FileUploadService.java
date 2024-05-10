package de.fatihkesikli.contargodemo.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileUploadService {

	@Value("${config.aws.s3.bucket-name}")
	private String bucketName;

	private final AmazonS3 amazonS3;

	@Autowired
	public FileUploadService(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}

	public boolean uploadAndDeleteFile(File file, String bucketName) {
		amazonS3.putObject(new PutObjectRequest(bucketName, file.getName(), file));
		return file.delete();
	}

	public void checkAndCreateBucket() {
		if (!amazonS3.doesBucketExistV2(bucketName)) {
			amazonS3.createBucket(bucketName);
		}
	}
}
