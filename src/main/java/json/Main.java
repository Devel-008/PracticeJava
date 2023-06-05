package json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Map<String,Object>> data = new ArrayList<>();
        // create key object
        Map<String, Object> key = new HashMap<>();
        key.put("value", "aute ex eu non");
        key.put("encoding", "base64");

        // create hash object
        Map<String, Object> hash = new HashMap<>();
        hash.put("digest", "md4");
        hash.put("value", "ea Excepteur mollit Lorem adipisicing");
        hash.put("encoding", "base64");
        hash.put("key", key);
        //customPasswordHash
        Map<String, Object> customPasswordHash = new HashMap<>();
        customPasswordHash.put("algorithm", "argon2");
        customPasswordHash.put("hash", hash);
        customPasswordHash.put("parallelization", 93549319);
        //data for maf_factor
        List<Map<String, Object>> mfaFactorsList = new ArrayList<>();
        Map<String, Object> totpMap1 = new HashMap<>();
        Map<String, Object> secretMap1 = new HashMap<>();
        secretMap1.put("secret", "36PW2G5");
        totpMap1.put("totp", secretMap1);
        mfaFactorsList.add(totpMap1);
        Map<String, Object> phoneMap = new HashMap<>();
        phoneMap.put("value", "+2518");
        Map<String, Object> phoneFactorMap = new HashMap<>();
        phoneFactorMap.put("phone", phoneMap);
        mfaFactorsList.add(phoneFactorMap);
        Map<String, Object> totpMap2 = new HashMap<>();
        Map<String, Object> secretMap2 = new HashMap<>();
        secretMap2.put("secret", "QS2");
        totpMap2.put("totp", secretMap2);
        mfaFactorsList.add(totpMap2);

        //map that collect data list
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("email","isha@hotmail.com");
        Map<String,Object> appData = new HashMap<>();
        appData.put("sunt_1",false);
        appData.put("sint01a",54704813);
        dataMap.put("email","isha@hotmail.com");
        dataMap.put("user_metadata","appData");
        dataMap.put("custom_password_hash",customPasswordHash);
        dataMap.put("mfa_factors", "mfaFactorsList");
        dataMap.put("email_verified","true");
        dataMap.put("name",false);
        dataMap.put("family_name",mfaFactorsList);
        dataMap.put("blocked",1);
        data.add(dataMap);
        Map<String,Object> dataMap2 = new HashMap<>();
        dataMap2.put("user_metadata",appData);
        dataMap2.put("custom_password_hash",customPasswordHash);
        dataMap2.put("mfa_factors", mfaFactorsList);
        dataMap2.put("email_verified",false);
        dataMap2.put("email","roman@hotmail.com");
        data.add(dataMap2);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File("src/main/resources/schema123.json"));
        List<List<Map<String,Object>>> dataList = new ArrayList<>();
        dataList.add(data);

        JsonNode node = JsonUtility.convertToJson(dataList.iterator(),jsonNode);
        //System.out.println(node.toPrettyString());
        DataLookupImpl dataLookup = new DataLookupImpl("org.postgresql.Driver","jdbc:postgresql://localhost:5432/test","postgres","isha@123");
        dataLookup.lookupInBatches("SELECT * FROM users",null,10);
    }
}
