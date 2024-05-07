package de.fatihkesikli.contargodemo.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchduledS3Uploader {

	@Scheduled(fixedDelay = 3600 * 3)
	public void upload() {}
}
