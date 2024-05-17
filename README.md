# Automation Test Project

## Overview
This project automates the process of testing the GoSell Pricing and feature limitations for different shops using Selenium WebDriver and TestNG. The automation script handles login actions, fetches access tokens, parses JWT, and compares data between various JSON files generated during the test run.

## Steps

### I. Process Excel Sheet for P.O.
1. **Extract and Convert Excel Data:**
    - Extract data from the GoSell Pricing Excel sheet.
    - Normalize values (e.g., x, ✓, true, false, nolimit) into `true`, `false`, or numbers.
    - Save the processed data into `Excel.json` at `/src/test/java/files/Excel.json`.

### II. Login and Fetch Access Token
#### A. New Shop (Purchased New Package)
1. **Login with Owner Account:**
    - Use one of the owner accounts from 18 shops with feature limitations.
    - Fetch the access token after successful login and store it in `accessToken`.
2. **Parse and Save JWT:**
    - Parse the `accessToken` to JWT and save the content into `infoPackageShop.json` at `/src/test/java/files/infoPackageShop.json`.
3. **Print Key Values:**
    - Display key-value pairs from `infoPackageShop.json` to show package information:
        - `bundleFeatureName: value`
        - `bundleFeatureCode: value`
        - `yearsOfFeatureLimitation: value`
        - `labelFeatureLimitation: value`

#### B. Old Shop (Using Old Package)
1. **Login with Owner Account:**
    - Use the owner account of the old shop to log in to the Dashboard.
2. **Handle Old Shop:**
    - Print "Shop cũ" if it is an old shop and log out the owner account.
3. **Login with Staff Account:**
    - Login with a staff account having full permission for necessary feature checks.
4. **Fetch and Parse Access Token:**
    - Fetch the access token, parse it to JWT, and save the content into `infoPackageShop.json` at `/src/test/java/files/infoPackageShop.json`.
5. **Skip Comparisons:**
    - Do not print package information or compare files for old shops.

### III. Fetch API Limit Information
- Call the API to get feature configuration information and save the response into `Api.json` at `/src/test/java/files/Api.json`.

### IV. Fetch and Save Local Storage Data
- Retrieve the value of the key `featureLimit` from the browser's Local Storage and save it into `localStore.json` at `/Users/tranbao/limitationProject/src/test/java/files/localStore.json`.

### V. Connect to Database and Fetch Data
- Connect to the database and fetch the following columns from the `feature_limitation_config` table:
    - `feature_code`
    - `label`
    - `value_config`
- Save the fetched data into `database.json`.

### VI. Compare JSON Files
1. **Compare Excel.json with Database.json:**
    - If the data does not match, print "not match" along with the object, key, value, and mismatched values from both files.
    - If the data matches, proceed to the next comparison.
2. **Compare Database.json with Api.json:**
    - If the data does not match, print "not match" along with the object, key, value, and mismatched values from both files.
    - If the data matches, proceed to the next comparison.
3. **Compare Api.json with localStore.json:**
    - If the data does not match, print "not match" along with the object, key, value, and mismatched values from both files.
    - If the data matches, print "match" and complete the comparison.

**Note:** If any comparison fails (i.e., data does not match), the process stops, and further comparisons are not performed.

## Additional Information
- Ensure all necessary libraries and dependencies are installed.
- Update any paths or configuration details as required.

"KHÔNG BẬT 2FA-DO NOT ENABLE 2FA"

---

# Dự Án Kiểm Thử Tự Động

## Tổng Quan
Dự án này tự động hóa quá trình kiểm thử giá cả và giới hạn tính năng của GoSell cho các cửa hàng khác nhau bằng cách sử dụng Selenium WebDriver và TestNG. Kịch bản tự động xử lý các hành động đăng nhập, lấy mã thông báo truy cập, phân tích JWT và so sánh dữ liệu giữa các tệp JSON khác nhau được tạo trong quá trình chạy thử nghiệm.

## Các Bước

### I. Xử Lý Bảng Excel cho P.O.
1. **Trích Xuất và Chuyển Đổi Dữ Liệu Excel:**
    - Trích xuất dữ liệu từ bảng Excel GoSell Pricing.
    - Chuẩn hóa các giá trị (ví dụ: x, ✓, true, false, nolimit) thành `true`, `false`, hoặc số.
    - Lưu dữ liệu đã xử lý vào `Excel.json` tại `/src/test/java/files/Excel.json`.

### II. Đăng Nhập và Lấy Mã Truy Cập
#### A. Cửa Hàng Mới (Đã Mua Gói Mới)
1. **Đăng Nhập Với Tài Khoản Chủ Sở Hữu:**
    - Sử dụng một trong các tài khoản chủ sở hữu từ 18 cửa hàng có giới hạn tính năng.
    - Lấy mã thông báo truy cập sau khi đăng nhập thành công và lưu vào biến `accessToken`.
2. **Phân Tích và Lưu JWT:**
    - Phân tích `accessToken` thành JWT và lưu nội dung vào `infoPackageShop.json` tại `/src/test/java/files/infoPackageShop.json`.
3. **In Các Giá Trị Khóa:**
    - Hiển thị các cặp khóa-giá trị từ `infoPackageShop.json` để hiển thị thông tin gói của cửa hàng:
        - `bundleFeatureName: value`
        - `bundleFeatureCode: value`
        - `yearsOfFeatureLimitation: value`
        - `labelFeatureLimitation: value`

#### B. Cửa Hàng Cũ (Sử Dụng Gói Cũ)
1. **Đăng Nhập Với Tài Khoản Chủ Sở Hữu:**
    - Sử dụng tài khoản chủ sở hữu của cửa hàng cũ để đăng nhập vào Dashboard.
2. **Xử Lý Cửa Hàng Cũ:**
    - In "Shop cũ" nếu là cửa hàng cũ và đăng xuất tài khoản chủ sở hữu.
3. **Đăng Nhập Với Tài Khoản Nhân Viên:**
    - Đăng nhập với tài khoản nhân viên có toàn quyền để kiểm tra các tính năng cần thiết.
4. **Lấy và Phân Tích Mã Truy Cập:**
    - Lấy mã thông báo truy cập, phân tích nó thành JWT và lưu nội dung vào `infoPackageShop.json` tại `/src/test/java/files/infoPackageShop.json`.
5. **Bỏ Qua So Sánh:**
    - Không in thông tin gói hoặc so sánh các tệp đối với cửa hàng cũ.

### III. Lấy Thông Tin Giới Hạn API
- Gọi API để lấy thông tin cấu hình tính năng và lưu phản hồi vào `Api.json` tại `/src/test/java/files/Api.json`.

### IV. Lấy và Lưu Dữ Liệu Từ Local Storage
- Lấy giá trị của khóa `featureLimit` từ Local Storage của trình duyệt và lưu vào `localStore.json` tại `/Users/tranbao/limitationProject/src/test/java/files/localStore.json`.

### V. Kết Nối với Cơ Sở Dữ Liệu và Lấy Dữ Liệu
- Kết nối với cơ sở dữ liệu và lấy các cột sau từ bảng `feature_limitation_config`:
    - `feature_code`
    - `label`
    - `value_config`
- Lưu dữ liệu đã lấy vào `database.json`.

### VI. So Sánh Các Tệp JSON
1. **So Sánh Excel.json với Database.json:**
    - Nếu dữ liệu không khớp, in "not match" cùng với đối tượng, khóa, giá trị, và các giá trị không khớp từ cả hai tệp.
    - Nếu dữ liệu khớp, tiếp tục so sánh tiếp theo.
2. **So Sánh Database.json với Api.json:**
    - Nếu dữ liệu không khớp, in "not match" cùng với đối tượng, khóa, giá trị, và các giá trị không khớp từ cả hai tệp.
    - Nếu dữ liệu khớp, tiếp tục so sánh tiếp theo.
3. **So Sánh Api.json với localStore.json:**
    - Nếu dữ liệu không khớp, in "not match" cùng với đối tượng, khóa, giá trị, và các giá trị không khớp từ cả hai tệp.
    - Nếu dữ liệu khớp, in "match" và hoàn tất so sánh.

**Lưu ý:** Nếu bất kỳ so sánh nào thất bại (tức là dữ liệu không khớp), quá trình dừng lại và không thực hiện các so sánh tiếp theo.

## Thông Tin Bổ Sung
- Đảm bảo tất cả các thư viện và phụ thuộc cần thiết đã được cài đặt.
- Cập nhật các đường dẫn hoặc chi tiết cấu hình nếu cần thiết.

"KHÔNG BẬT 2FA-DO NOT ENABLE 2FA"
