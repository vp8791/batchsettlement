package com.mkyong.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BatchFileUtils {
	public static String FILE_PREFIX = "Acquirer";

	public static String ACQUIRER_NAME_KEY = "Acquirer";
	public static String FILE_NAME_KEY = "file";

	public static List<String> getFileNamesToBeProcessed(String sourceDirectory, String destDirectory,
			String fileSuffix) {
		List<String> fileNames = new ArrayList<String>();
		boolean haveFilesArrived = isFileLanded(sourceDirectory, fileSuffix);
		if (haveFilesArrived) {
			File dir = new File(sourceDirectory);
			File[] files = dir
					.listFiles((d, name) -> (name.endsWith("." + fileSuffix) && name.startsWith(FILE_PREFIX)));
			for (File file : files) {
				if (!file.isDirectory()) {
					boolean mvFile = moveFile(sourceDirectory, file.getName(), destDirectory,
							file.getName() );
					if (mvFile) {
						fileNames.add(destDirectory + "/" + file.getName());
					}
				}
			}
		}
		return fileNames;
	}

	public static boolean moveFile(String sourceDir, String sourceFile, String destDirectory, String destinationFile) {
		File renameFileFullPath = new File(destDirectory + "/" + destinationFile);
		File sourceFileFullPath = new File(sourceDir + "/" + sourceFile);
		if (sourceFileFullPath.renameTo(renameFileFullPath)) {
			sourceFileFullPath.delete();
			// System.out.println("File moved successfully(" + sourceFileFullPath.getName()
			// + ")");
			return true;
		} else {
			System.out.println("Failed to move the file(" + sourceFileFullPath.getAbsolutePath() + ")" + "to (" + renameFileFullPath.getAbsolutePath() + ")");
			return false;
		}
	}
	
	public static boolean moveFile(String source, String destination) {
		File sourceFileFullPath = new File(source);
		File destinationFilePath = new File(destination);
		if (sourceFileFullPath.renameTo(destinationFilePath)) {
			sourceFileFullPath.delete();
			 System.out.println("File moved successfully(" + sourceFileFullPath.getName()
			+ ")");
			return true;
		} else {
			System.out.println("Failed to move the file(" + sourceFileFullPath.getAbsolutePath() + ") to (" + destinationFilePath.getAbsolutePath() + ")");
			return false;
		}
		
	}
	

	public static boolean isFileLanded(String sourceDirectory, String fileSuffix) {
		File dir = new File(sourceDirectory);
		File[] files = dir.listFiles((d, name) -> (name.endsWith("." + fileSuffix) && name.startsWith(FILE_PREFIX)));
		if (files.length > 0)
			return true;

		return false;

	}

}
