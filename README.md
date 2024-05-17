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
