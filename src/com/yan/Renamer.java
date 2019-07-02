package com.yan;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

public class Renamer {
	private String path;
	Translation trans = new Translation();

	public Renamer(String path) {
		super();
		this.path = path;
	}

	public ArrayList<File> GetFiles() {
		ArrayList<File> result = new ArrayList<File>();
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				result.add(listOfFiles[i]);
			}
		}

		return result;
	}
	
	public void RenameAll() throws JsonParseException, JsonMappingException, IOException, InterruptedException {
		for (File file : GetFiles()) {
			String oldName = FilenameUtils.getBaseName(file.getName());
			String extName = FilenameUtils.getExtension(file.getName());
			String newName = trans.GetTransResult(trans.Translate(oldName, LanguageCode.Chinese, LanguageCode.English));
			File newFile = new File(path + newName + "." + extName);
			file.renameTo(newFile);
			Thread.sleep(1000);
		}
	}
}
