package automation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

public class TokenManager {
    public static String decodeToken(String accessToken) {
        return new String(Base64.getUrlDecoder().decode(accessToken.split("\\.")[1]));
    }

    public static void writeTokenParseToFile(String tokenParse, String filePath) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(tokenParse);
            System.out.println("a. AccessToken parsed and saved to file infoPackageShop.");
        }
    }
}
