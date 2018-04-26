package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class Main {

    public static void main(String[] args) {

        //// Filter is created to get specific files.
//        DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
//            @Override
//            public boolean accept(Path path) throws IOException {
//                return (Files.isRegularFile(path));
//            }
//        };


        DirectoryStream.Filter<Path> filter = path -> (Files.isRegularFile(path));

        Path directory = FileSystems.getDefault().getPath("FileTree/Dir2");
//        try(DirectoryStream<Path> contents = Files.newDirectoryStream(directory)){
          try(DirectoryStream<Path> contents = Files.newDirectoryStream(directory, filter)){
            for (Path file: contents){
                System.out.println(file.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get File Separator for any System. WIndows\ and Linux/MAC /

        String seperator = File.separator;
        System.out.println(seperator);
        seperator = FileSystems.getDefault().getSeparator();
        System.out.println(seperator);

        try{
            Path tempFile = Files.createTempFile("myapp",".appext");        //      Create Temp file in temp folder
            System.out.println("Temporary file path: "+ tempFile.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Iterable<FileStore> stores = FileSystems.getDefault().getFileStores();          //      System Storege Information
        for(FileStore store:stores){
            System.out.println("Volume Name: " + store);
            System.out.println("FileStore: "+store.name());
        }

        Iterable<Path> rootPaths = FileSystems.getDefault().getRootDirectories();       //      Root Directory
        for(Path path: rootPaths){
            System.out.println(path);
        }

        //  Getting information about tree of Dir2 folder.

        Path path = FileSystems.getDefault().getPath("FileTree" + File.separator + "Dir2");
        try {
            Files.walkFileTree(path, new PrintName());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
