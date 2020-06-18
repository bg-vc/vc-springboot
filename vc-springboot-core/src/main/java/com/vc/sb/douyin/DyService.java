package com.vc.sb.douyin;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:       VinceChen
 * Date:         2020-06-14 23:35
 * Description:
 */



public class DyService {

    public static String byteTable1 =
            "D6 28 3B 71 70 76 BE 1B A4 FE 19 57 5E 6C BC 21 B2 14 37 " +
                    "7D 8C A2 FA 67 55 6A 95 E3 FA 67 78 ED 8E 55 33 " +
                    "89 A8 CE 36 B3 5C D6 B2 6F 96 C4 34 B9 6A EC 34 " +
                    "95 C4 FA 72 FF B8 42 8D FB EC 70 F0 85 46 D8 B2 " +
                    "A1 E0 CE AE 4B 7D AE A4 87 CE E3 AC 51 55 C4 36 " +
                    "AD FC C4 EA 97 70 6A 85 37 6A C8 68 FA FE B0 33 " +
                    "B9 67 7E CE E3 CC 86 D6 9F 76 74 89 E9 DA 9C 78 " +
                    "C5 95 AA B0 34 B3 F2 7D B2 A2 ED E0 B5 B6 88 95 " +
                    "D1 51 D6 9E 7D D1 C8 F9 B7 70 CC 9C B6 92 C5 FA " +
                    "DD 9F 28 DA C7 E0 CA 95 B2 DA 34 97 CE 74 FA 37 " +
                    "E9 7D C4 A2 37 FB FA F1 CF AA 89 7D 55 AE 87 BC " +
                    "F5 E9 6A C4 68 C7 FA 76 85 14 D0 D0 E5 CE FF 19 " +
                    "D6 E5 D6 CC F1 F4 6C E9 E7 89 B2 B7 AE 28 89 BE " +
                    "5E DC 87 6C F7 51 F2 67 78 AE B3 4B A2 B3 21 3B " +
                    "55 F8 B3 76 B2 CF B3 B3 FF B3 5E 71 7D FA FC FF " +
                    "A8 7D FE D8 9C 1B C4 6A F9 88 B5 E5";

    public static String[] byteTable2 = byteTable1.split(" ");
    private static final String NULL_MD5_STRING = "00000000000000000000000000000000";

    public static String cookies = "install_id=3931504500825581; " +
            "ttreq=1$c0b4ee69e6ea724b1fbbc1c75a26450250e21ada; " +
            "qh[360]=1; " +
            "odin_tt=077dd7dccadf6e34e12ac7b3fdd390275ab6b1ded95062f16f9f526b3f67ba37189a780277644f4624e1d139654c01f67acd6dbe4c55d74e6582c301a28820b7";

    public static void main(String[] args) {
        int successCount = 0;
        int failCount = 0;
        for (int m=0; m<20; m++) {
            for (int i= 0; i<1; i++) {
                int ts = (int) (System.currentTimeMillis() / 1000);
                String _rticket = System.currentTimeMillis() + "";

                // 3737927633602936 848456505900387


                String deviceId = "641760368533752";
                String iid = "3931504500825581";

                // 用户基本信息
                //String url = "https://aweme-eagle-lq.snssdk.com/aweme/v1/user/?user_id=72673737181&retry_type=no_retry&iid=" +iid + "&device_id=" +deviceId +  "&ac=wifi&channel=douyin_huitou_and23&aid=2329&app_name=aweme&version_code=880&version_name=8.8.0&device_platform=android&ssmix=a&device_type=Redmi+Note+7+Pro&device_brand=xiaomi&language=zh&os_api=28&os_version=9&openudid=6f3cd6f182e899f1&manifest_version_code=880&resolution=1080*2131&dpi=440&update_version_code=880&_rticket=1592296195608&ts=1592296194&as=aac8eb8b185ee88303c8eb&cp=8b68c8eb8b18c8eb8b6030&mas=01999323b37923799923f9b9b94528feff799923f9f333f3232399";
                String url = "https://aweme-eagle-lq.snssdk.com/aweme/v1/user/?user_id=72673737181&retry_type=no_retry&iid=" +iid + "&device_id=" +deviceId +  "&ac=wifi&channel=tengxun&aid=1128&app_name=aweme&version_code=890&version_name=8.9.0&device_platform=android&ssmix=a&device_type=MT2-L05&device_brand=HUAWEI&language=zh&os_api=21&os_version=5.1.1&openudid=6f3cd6f182e899f1&manifest_version_code=890&resolution=720*1280&dpi=160&update_version_code=890&_rticket=1592296195608&ts=1592296194&as=aac8eb8b185ee88303c8eb&cp=8b68c8eb8b18c8eb8b6030&mas=01999323b37923799923f9b9b94528feff799923f9f333f3232399";


                String params = url.substring(url.indexOf("?") + 1);
                String STUB = "";
                String s = getXGon(params, STUB, cookies);
                System.out.println("xGon:" + s);
                String Gorgon = xGorgon(ts, StrToByte(s));

                System.out.println("Gorgon:" + Gorgon);
                Map<String, Object> headers = new HashMap<>();
                // 036141108000f6cea0cea0098ed04ad1ef87c949db1250753ceb
                headers.put("X-Gorgon", Gorgon);
                headers.put("X-Khronos", ts);
                //headers.put("sdk-version", "1");
                headers.put("Accept-Encoding", "");
                headers.put("X-SS-REQ-TICKET", _rticket);
                headers.put("User-Agent", "com.ss.android.ugc.aweme/100501 (Linux; U; Android 9; zh_CN; Redmi Note 7 Pro; Build/PKQ1.181203.001; Cronet/58.0.2991.0)");
                headers.put("Host", "aweme-lq.snssdk.com");
                headers.put("Cookie", cookies);
                headers.put("Connection", "Keep-Alive");
                String result = DyHttpUtil.doGet(url, headers);
                if (result.length() < 100) {
                    failCount++;
                } else {
                    successCount++;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("total:20, success:" + successCount + ", fail:" + failCount);

    }


    public static String ByteToStr(byte[] bArr) {

        int i = 0;

        char[] toCharArray = "0123456789abcdef".toCharArray();
        char[] cArr = new char[(bArr.length * 2)];
        while (i < bArr.length) {
            int i2 = bArr[i] & 255;
            int i3 = i * 2;
            cArr[i3] = toCharArray[i2 >>> 4];
            cArr[i3 + 1] = toCharArray[i2 & 15];
            i++;
        }
        return new String(cArr);

    }

    public static byte[] StrToByte(String str) {
        String str2 = str;
       //Object[] objArr = new Object[1];
        int i = 0;
        //objArr[0] = str2;

        int length = str.length();
        byte[] bArr = new byte[(length / 2)];
        while (i < length) {
            bArr[i / 2] = (byte) ((Character.digit(str2.charAt(i), 16) << 4) + Character.digit(str2.charAt(i + 1), 16));
            i += 2;
        }
        return bArr;
    }

    public static String encryption(String str) {
        String re_md5 = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5.toUpperCase();
    }

    public static String getXGon(String url, String stub, String cookies) {
        StringBuilder sb = new StringBuilder();
        if (TextUtils.isEmpty(url)) {
            sb.append(NULL_MD5_STRING);
        } else {
            sb.append(encryption(url).toLowerCase());
        }
        if (TextUtils.isEmpty(stub)) {
            sb.append(NULL_MD5_STRING);
        } else {
            sb.append(stub);
        }


        if (TextUtils.isEmpty(cookies)) {
            sb.append(NULL_MD5_STRING);
        } else {
            sb.append(encryption(cookies).toLowerCase());
        }

        int index = cookies.indexOf("sessionid=");
        if (index == -1) {
            sb.append(NULL_MD5_STRING);
        } else {
            String sessionid = StringUtils.substringBetween(cookies, "sessionid=", ";");
            if (sessionid == null) {//sessionid在cookie字符串中的最后
                sessionid = cookies.substring(index + 10);
            }
            //System.out.println("sessionid:" + sessionid);
            sb.append(encryption(sessionid).toLowerCase());
        }

        return sb.toString();
    }

    public static ArrayList<String> input(int timeMillis, byte[] inputBytes) {


        ArrayList<String> byteTable = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            if (inputBytes[i] < 0) {
                byteTable.add(Integer.toHexString(inputBytes[i]).substring(6));
            } else {
                byteTable.add(Integer.toHexString(inputBytes[i]));
            }
        }
        for (int i = 0; i < 4; i++) {
            byteTable.add("0");
        }
        for (int i = 32; i < 36; i++) {
            if (inputBytes[i] < 0) {
                byteTable.add(Integer.toHexString(inputBytes[i]).substring(6));
            } else {
                byteTable.add(Integer.toHexString(inputBytes[i]));
            }
        }
        for (int i = 0; i < 4; i++) {
            byteTable.add("0");
        }
        String timeByte = Integer.toHexString(timeMillis);
        for (int i = 0; i < 4; i++) {
            byteTable.add(timeByte.substring(2 * i, 2 * i + 2));
        }
        return byteTable;
    }

    private static ArrayList<String> initialize(ArrayList<String> data) {
        int hex = 0;
        byteTable2 = byteTable1.split(" ");
        for (int i = 0; i < data.size(); i++) {
            int hex1 = 0;
            if (i == 0) {
                hex1 = Integer.valueOf(byteTable2[Integer.valueOf(byteTable2[0], 16) - 1], 16);
                byteTable2[i] = Integer.toHexString(hex1);
            } else if (i == 1) {
                int temp = Integer.valueOf("D6", 16) + Integer.valueOf("28", 16);
                if (temp > 256) {
                    temp -= 256;
                }
                hex1 = Integer.valueOf(byteTable2[temp - 1], 16);
                hex = temp;
                byteTable2[i] = Integer.toHexString(hex1);
            } else {
                int temp = hex + Integer.valueOf(byteTable2[i], 16);
                if (temp > 256) {
                    temp -= 256;
                }
                hex1 = Integer.valueOf(byteTable2[temp - 1], 16);
                hex = temp;
                byteTable2[i] = Integer.toHexString(hex1);
            }
            if (hex1 * 2 > 256) {
                hex1 = hex1 * 2 - 256;
            } else {
                hex1 = hex1 * 2;
            }

            String hex2 = byteTable2[hex1 - 1];
            int result = Integer.valueOf(hex2, 16) ^ Integer.valueOf(data.get(i), 16);
            data.set(i, Integer.toHexString(result));
        }
        return data;
    }

    public static ArrayList<String> handle(ArrayList<String> data) {

        for (int i = 0; i < data.size(); i++) {
            String byte1 = data.get(i);
            if (byte1.length() < 2) {
                byte1 += "0";
            } else {
                byte1 = data.get(i).split("")[1] + data.get(i).split("")[0];
            }
            if (i < data.size() - 1) {
                byte1 = Integer.toHexString(Integer.valueOf(byte1, 16) ^ Integer.valueOf(data.get(i + 1), 16));
            } else {
                byte1 = Integer.toHexString(Integer.valueOf(byte1, 16) ^ Integer.valueOf(data.get(0), 16));
            }
            int byte2 = ((Integer.valueOf(byte1, 16) & Integer.valueOf("55", 16)) * 2) | ((Integer.valueOf(byte1, 16) & Integer.valueOf("AA", 16)) / 2);
            byte2 = ((byte2 & Integer.valueOf("33", 16)) * 4) | ((byte2 & Integer.valueOf("CC", 16)) / 4);
            String byte3 = Integer.toHexString(byte2);
            if (byte3.length() > 1) {
                byte3 = byte3.split("")[1] + byte3.split("")[0];
            } else {
                byte3 += "0";
            }
            int byte4 = Integer.valueOf(byte3, 16) ^ Integer.valueOf("FF", 16);
            byte4 = byte4 ^ Integer.valueOf("14", 16);
            data.set(i, Integer.toHexString(byte4));
        }

        return data;
    }

    public static String xGorgon(int timeMillis, byte[] inputBytes) {
        System.out.println("inputBytes:" + inputBytes);
        ArrayList<String> data1 = new ArrayList<>();
        data1.add("3");
        data1.add("61");
        data1.add("41");
        data1.add("10");
        data1.add("80");
        data1.add("0");

        ArrayList<String> data2 = input(timeMillis, inputBytes);
        data2 = initialize(data2);
        //System.out.println("111:" + data2.toString());
        data2 = handle(data2);
        //System.out.println("222:" + data2.toString());
        data1.addAll(data2);
        //System.out.println("333:" + data1.toString());


        String xGorgonStr = "";
        for (int i = 0; i < data1.size(); i++) {
            String temp = String.valueOf(data1.get(i));
            if (temp.length() > 1) {
                xGorgonStr += temp;
            } else {
                xGorgonStr += "0";
                xGorgonStr += temp;
            }
        }
        //System.out.println("444:" + xGorgonStr);
        return xGorgonStr;
    }

}


