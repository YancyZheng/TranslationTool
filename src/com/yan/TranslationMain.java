package com.yan;

import java.io.IOException;

public class TranslationMain {

	public static void main(String[] args) throws IOException, InterruptedException {
		Renamer namer = new Renamer("d:\\audios\\");
		namer.RenameAll();
	}

}
