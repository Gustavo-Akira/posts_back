package br.com.posts.controllers.helpers;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.posts.models.User;
import br.com.posts.repositories.UsersRepository;
@Service
public class Helper {
	@Autowired
	private UsersRepository repository;
	
	public User getUserActive() {
		return repository.findUserByLogin((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}
	
	public static void saveFile(String path, MultipartFile file) {

		File saveFile = new File(path);
		try {
			FileUtils.writeByteArrayToFile(saveFile, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
