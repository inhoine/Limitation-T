package automation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LocalFileWriter {

    // Unified method to write data to a file, allowing specification of file path
    public static void writeLocalToFile(String data, String filePath) {
        File file = new File(filePath);
        file.getParentFile().mkdirs(); // Ensure parent directories exist

        try (FileWriter fileWriter = new FileWriter(file, false)) { // Make sure to overwrite existing content
            fileWriter.write(data);
        } catch (IOException e) {
            System.err.println("II. Lỗi khi ghi vào file: " + e.getMessage());
        }
    }
}
