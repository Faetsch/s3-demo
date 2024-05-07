package de.fatihkesikli.contargodemo.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileUploadService {
	private final AmazonS3 amazonS3;

	@Autowired
	public FileUploadService(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}

	public boolean uploadFile(File file, String bucketName) {
		if (!amazonS3.doesBucketExistV2("contargo-bucket")) {
			amazonS3.createBucket("contargo-bucket");
		}
		amazonS3.putObject(new PutObjectRequest(bucketName, file.getName(), file));
//		return file.delete();
		return true;
	}

}
