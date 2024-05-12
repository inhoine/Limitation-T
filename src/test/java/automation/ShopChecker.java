package automation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ShopChecker {
    public static boolean isOldShop(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(new File(filePath));
        // Định nghĩa các key quan trọng
        String[] importantKeys = {
                "nextExpiryPackageTime", "bundleFeatureName", "bundleFeatureCode",
                "nextExpiryBundlePackageTime", "yearsOfFeatureLimitation",
                "labelFeatureLimitation", "partnerEnabled", "hasPartnerPackage", "exp"
        };

        for (String key : importantKeys) {
            if (!jsonNode.has(key)) {
                return true; // Nếu bất kỳ key nào không tồn tại, coi đó là shop cũ
            }
        }
        return false; // Tất cả các key quan trọng đều có mặt
    }
}
