package com.cdac.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileService {
  public List<File> filterFilesBySubstring(List<File> files, String substring) {
    List<File> filteredFiles = new ArrayList<>();
    for (File file : files) {
      if (file.getName().contains(substring)) {
        filteredFiles.add(file);
      }
    }
    return filteredFiles;
  }
  
  public List<File> getFilesInDirectory(String directoryPath) {
	    File directory = new File(directoryPath);
	    File[] files = directory.listFiles();
	    return Arrays.asList(files);
	  }
}
