import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.stream.Stream;

public class DirectoryHandlerJava {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);// Scanner for taking input
        System.out.print("Source Path: ");
        String source = in.nextLine().strip(); // Taking source path as input and also removing extra spaces/
        System.out.print("Destination Path: ");
        String destination = in.nextLine().strip();// Taking Destination path as input and also removing extra spaces.
        Path sourcePath = Paths.get(source); // getting path of source
        Path destinationPath = Paths.get(destination); // getting path of destination
        copyContent(sourcePath, destinationPath);
    }

    public static void copyContent(Path sourcePath, Path destinationPath) {
        
        try {
            if (Files.isDirectory(sourcePath)) {
                // if directory doesn't exist
                if (Files.notExists(destinationPath)) {
                    // create it
                    Files.createDirectories(destinationPath);
                    // Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                }
                // Copy all files recursively
              /*
              In the next try state we first find list of paths of elements present in the directory using Files.list function.
              Then program will loop over this list and recursively copy the inner elements.
              If statement inside loop helps program to avoid the condition of infinite recursion   
              */
                try (Stream<Path> paths = Files.list(sourcePath)) {
                    for (Object temp : paths.toArray()) {
                        Path nextSourcePath = (Path) temp;    
                        if (nextSourcePath.equals(destinationPath)==false) {
                            copyContent(nextSourcePath, destinationPath.resolve(sourcePath.relativize(nextSourcePath)));
                            
                        }
                    }

                }

            } else {
                // We came here if file given path results in file.
                try {
                    Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

                } catch (NoSuchFileException e) { // If source path is wrong or if there is no such file or //
                                                  // directory.
                    System.out.println("Source path is wrong");
                }
            }

        } catch (IOException e) {
            System.out.println("Error while reading occur");
            
        }


    }

}