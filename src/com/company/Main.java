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

        //  Copy Dir2 to Dir4

        Path copyPath = FileSystems.getDefault().getPath("FileTree" + File.separator + "Dir4" + File.separator + "Dir2Copy");
        try {
            Files.walkFileTree(path, new CopyFiles(path,copyPath));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //

        Path parentPath = Paths.get("/Examples");
        Path childRelativePath = Paths.get("dir/file.txt");
        System.out.println(parentPath.resolve(childRelativePath));

        //

        // Getting information on how to call directories

        File resolvedFile = new File("/Examples","dir/file.txt");
        System.out.println(resolvedFile);

        // Getting information of Dir2 using file.io
        File workingDirectory = new File("").getAbsoluteFile();
        System.out.println("Working Directory: " + workingDirectory.getAbsolutePath());

        System.out.println();
        File dir2File = new File(workingDirectory,"/FileTree/Dir2");
        String[] dir2Contents = dir2File.list();
        for(int i=0; i<dir2Contents.length; i++){
            System.out.println("i: " + i + ": " + dir2Contents[i]);
        }

        System.out.println();
        File[] dir2Files = dir2File.listFiles();
        for (int i=0; i<dir2Files.length; i++){
            System.out.println("i: "+ i + ": " + dir2Contents[i]);
        }
    }
}
