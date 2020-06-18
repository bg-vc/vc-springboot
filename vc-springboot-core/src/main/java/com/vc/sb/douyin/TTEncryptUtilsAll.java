package com.vc.sb.douyin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.ByteArrayOutputStream;
import java.util.Random;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;


/**
 * 设备注册算法
 */
public class TTEncryptUtilsAll {

    private static final long v10 = 0x033ef9d50L;
    private static final long v11 = 0x03f929618L;
    private static final long v12 = 0x0fde58f8fL;
    private static final long v13 = 0x0fdcf434dL;

    private static final int sbox0[] = {
            0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76,
            0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,
            0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,
            0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,
            0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,
            0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,
            0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,
            0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,
            0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
            0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,
            0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,
            0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,
            0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,
            0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,
            0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,
            0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16
    };


    private static long __ROR4__(long val, int size) {
        long res = val >>> size;
        res |= (val << (32 - size));
        return res & 0x0FFFFFFFFL;
    }

    /**
     * swapInt32 https://www.cnblogs.com/-sev-/p/5067132.html
     *
     * @param val
     * @return
     */
    private static long reverse(long val) {
        long v = val;
        v = ((v & 0x000000FF) << 24) | ((v & 0x0000FF00) << 8) |
                ((v & 0x00FF0000) >> 8) | ((v & 0xFF000000) >> 24);
        return v;
    }

    private static int ss_encrypted_size(int a1) {
        return ((16 - a1 % 16) & 0xF) + a1 + 4;
    }

//    private static long getLong(int[] cArr, int index){
//        int firstByte = (0xFFFFFFFF & cArr[index + 3]);
//        int secondByte = (0xFFFFFFFF & cArr[index + 2]);
//        int thirdByte = (0xFFFFFFFF & cArr[index + 1]);
//        int fourthByte = (0xFFFFFFFF & cArr[index]);
//        return (firstByte | secondByte << 8 | thirdByte << 16 | fourthByte << 24) & 0x0FFFFFFFFL;
//    }

    /**
     * http://www.darksleep.com/player/JavaAndUnsignedTypes.html
     * https://www.cnblogs.com/wikiki/p/5340925.html
     * 将一个4byte的数组转换成32位的int
     *
     * @param cArr
     * @param index
     * @return
     */
    private static long getLong(int[] cArr, int index) {

        int firstByte = (0xFFFFFFFF & cArr[index]);
        int secondByte = (0xFFFFFFFF & cArr[index + 1]);
        int thirdByte = (0xFFFFFFFF & cArr[index + 2]);
        int fourthByte = (0xFFFFFFFF & cArr[index + 3]);

        return (firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte) & 0x0FFFFFFFFL;
    }

    private static byte[] ss_encrypt(byte[] bArr, int new_len) {
        int index = 0;

        int[] a5 = new int[new_len];
        index = 4;
        for (byte c : bArr) {
            a5[index++] = c;
        }
        a5[0] = 116;
        a5[1] = 99;
        a5[2] = 2;
        a5[3] = (-bArr.length & 0xF);
        index = 4;// v16 = a5[index]
        do {
            a5[index] = sbox0[a5[index] & 0xFF];
            index++;
        } while (index != new_len);
        index = 4;

        for (int v17 = 0; ; v17++) {
            if (v17 >= (bArr.length + (-bArr.length & 0xF)) >> 4) {
                break;
            }
            long temp = getLong(a5, index);
            temp = reverse(temp ^ v12);

            for (int i = index; i < index + 4; i++) {
                a5[i] = (int) temp & 0xFF;
                temp = temp >>> 8;
            }

            index += 4;
            temp = getLong(a5, index);
            temp = reverse(v13 ^ __ROR4__(temp, 24));
            for (int i = index; i < index + 4; i++) {
                a5[i] = (int) temp & 0xFF;
                temp = temp >>> 8;
            }

            index += 4;
            temp = getLong(a5, index);
            temp = reverse(v10 ^ __ROR4__(temp, 16));
            for (int i = index; i < index + 4; i++) {
                a5[i] = (int) temp & 0xFF;
                temp = temp >>> 8;
            }

            index += 4;
            temp = getLong(a5, index);
            temp = reverse(v11 ^ __ROR4__(temp, 8));
            for (int i = index; i < index + 4; i++) {
                a5[i] = (int) temp & 0xFF;
                temp = temp >>> 8;
            }
            index += 4;
        }

        byte[] newArr = new byte[a5.length];
        for (int i = 0; i < a5.length; i++) {
            newArr[i] = (byte) a5[i];
        }
        return newArr;
    }

    public static byte[] ttEncrypt(byte[] bArr) {
        int old_str_len = bArr.length;
        int new_str_len = ss_encrypted_size(old_str_len);
        return ss_encrypt(bArr, new_str_len);
    }

    /*
    {
    "serialNumber":"33cc1f512380",
    "openudid":"fa24aa4abbf18607",
    "uuid":"868725522543838",
    "clientudid":"48bbd9ae-a555-4a34-94fb-4c94c6e8d09a",
    "deviceId":"39464175849",
    "mac":"10:2a:b3:2e:b3:fb",
    "retry_type":"no_retry",
    "ac":"4G",
    "channel":"tengxun",
    "aid":"1128",
    "host_abi":"armeabi-v7a",
    "app_name":"aweme",
    "version_code":"890",
    "version_name":"8.9.0",
    "device_platform":"android",
    "ssmix":"a",
    "device_type":"MT2-L05",
    "device_brand":"HUAWEI",
    "language":"zh",
    "os_api":"22",
    "os_version":"5.1.1",
    "manifest_version_code":"890",
    "resolution":"720*1280",
    "dpi":"160",
    "update_version_code":"8902"
}
     */
    public static void main(String[] arsg) throws Exception {
        String regInfo = "{\"display_name\":\"抖音短视频\",\"update_version_code\":8802,\"manifest_version_code\":880,\"aid\":1128,\"channel\":\"aweGW\",\"appkey\":\"57bfa27c67e58e7d920028d3\",\"package\":\"com.ss.android.ugc.aweme\",\"app_version\":\"8.8.0\",\"version_code\":880,\"sdk_version\":\"2.5.5.8\",\"os\":\"Android\",\"os_version\":\"5.1.1\",\"os_api\":22,\"device_model\":\"huawei nxt-al10\",\"device_brand\":\"huawei\",\"device_manufacturer\":\"huawei\",\"cpu_abi\":\"armeabi-v7a\",\"build_serial\":\"00e9ab7d\",\"release_build\":\"55fb587_20191121\",\"density_dpi\":240,\"display_density\":\"hdpi\",\"resolution\":\"960x540\",\"language\":\"zh\",\"mc\":\"00:81:47:7f:41:9f\",\"timezone\":8,\"access\":\"wifi\",\"not_request_sender\":0,\"carrier\":\"CHINA MOBILE\",\"mcc_mnc\":\"46000\",\"rom\":\"EMUI-8.3.19\",\"rom_version\":\"LMY47I\",\"sig_hash\":\"aea615ab910015038f73c47e45d21466\",\"openudid\":\"d33c340d7b911a1e\",\"udid\":\"865166029571224\",\"clientudid\":\"067d7728-3e89-4792-90d2-bdfc57b49397\",\"serial_number\":\"00e9ab7d\",\"sim_serial_number\":[],\"region\":\"CN\",\"tz_name\":\"Asia\\/Shanghai\",\"tz_offset\":28800,\"sim_region\":\"cn\"}";

        JSONObject jsonObject = JSON.parseObject(regInfo);

        String serial_number = UUID.randomUUID().toString().substring(24);
        String udid = "869" + getRanInt(12);
        String openudid = "fa25" + UUID.randomUUID().toString().substring(24);
        String clientudid = UUID.randomUUID().toString();
        String mc = randomMac4Qemu();
        System.out.println("mc:" + mc);


        //更新注册参数
        /*jsonObject.put("serial_number", "33cc1f512380");
        jsonObject.put("openudid", "fa24aa4abbf18607");
        jsonObject.put("udid", "868725522543838");
        jsonObject.put("clientudid", "48bbd9ae-a555-4a34-94fb-4c94c6e8d09a");
        jsonObject.put("mc", "10:2a:b3:2e:b3:fb");*/

        jsonObject.put("serial_number", serial_number);
        jsonObject.put("openudid", openudid);
        jsonObject.put("udid", udid);
        jsonObject.put("clientudid", clientudid);
       jsonObject.put("mc", mc);


        //jsonObject.put("",deviceInfoObject.getString("deviceId"));
        //jsonObject.put("",deviceInfoObject.getString("address_book_access"));
        jsonObject.put("access", "WIFI");
        jsonObject.put("channel", "tengxun");
        jsonObject.put("aid", "1128");
        //jsonObject.put("",deviceInfoObject.getString("app_name"));
        jsonObject.put("version_code", Integer.valueOf("890"));
        jsonObject.put("manifest_version_code", Integer.valueOf("890"));
        jsonObject.put("app_version", "8.9.0");
        //jsonObject.put("",deviceInfoObject.getString("device_platform"));
        //jsonObject.put("",deviceInfoObject.getString("ssmix"));
        jsonObject.put("device_model", "MT2-L05");
        jsonObject.put("device_brand", "HUAWEI");
        jsonObject.put("device_manufacturer", "HUAWEI");
        jsonObject.put("device_manufacturer", "HUAWEI");
        jsonObject.put("language", "zh");
        jsonObject.put("os_api", "22");
        //jsonObject.put("manifest_version_code",deviceInfoObject.getString("manifest_version_code"));
        jsonObject.put("resolution", "720*1280");
        jsonObject.put("density_dpi", "160");
        jsonObject.put("update_version_code", Integer.valueOf("8902"));

        JSONObject mainInfo = new JSONObject();
        mainInfo.put("magic_tag", "ss_app_log");
        mainInfo.put("header", jsonObject);
        mainInfo.put("_gen_time", System.currentTimeMillis());

        String result = JSON.toJSONString(mainInfo);
        //System.out.println(result);
        byte[] bArr2 = result.getBytes("UTF-8");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8192);
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gZIPOutputStream.write(bArr2);
        gZIPOutputStream.close();
        bArr2 = byteArrayOutputStream.toByteArray();
        byte[] bytes = TTEncryptUtilsAll.ttEncrypt(bArr2);

        try {
            TTEncryptUtilsAll.exec(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*public static void main(String[] args) throws Exception {
        String serial_number = UUID.randomUUID().toString().substring(24);
        String udid = "869" + getRanInt(12);
        String openudid = "fa25" + UUID.randomUUID().toString().substring(24);
        String clientudid = UUID.randomUUID().toString();
        String mc = getMc();

        int time = (int) (System.currentTimeMillis() / 1000);

        String _rticket = System.currentTimeMillis() + "";   //获取当前时间

        String url = "https://log.snssdk.com/service/2/device_register/?uuid=" + udid + "&openudid=" + openudid + "&_rticket=" + _rticket + "&retry_type=no_retry&ac=4G&channel=tengxun&aid=1128&host_abi=armeabi-v7a&app_name=douyin&version_code=930&version_name=9.3.0&device_platform=android&ssmix=a&device_type=MT2-L05&device_brand=HUAWEI&language=zh&os_api=22&os_version=5.1.1&manifest_version_code=930&resolution=720%2A1280&dpi=320&update_version_code=9302";

        System.out.println(url);

        String str = "{\"_gen_time\":" + time + ",\"header\":{\"manifest_version_code\":930,\"access\":\"4G\",\"app_version\":\"9.3.0\",\"device_model\":\"MT2-L05\",\"timezone\":8,\"channel\":\"tengxun\",\"build_serial\":\"00e9ab7d\",\"language\":\"zh\",\"resolution\":\"720*1280\",\"device_manufacturer\":\"HUAWEI\",\"openudid\":\"" + openudid + "\",\"update_version_code\":9302,\"rom\":\"EMUI-8.3.19\",\"density_dpi\":\"320\",\"os_api\":\"22\",\"cpu_abi\":\"armeabi-v7a\",\"tz_name\":\"Asia/Shanghai\",\"tz_offset\":28800,\"mc\":\"" + mc + "\",\"sdk_version\":\"2.5.5.8\",\"udid\":\"" + udid + "\",\"not_request_sender\":0,\"package\":\"com.ss.android.ugc.aweme\",\"os\":\"Android\",\"mcc_mnc\":\"46000\",\"os_version\":\"5.1.1\",\"version_code\":930,\"serial_number\":\"" + serial_number + "\",\"display_name\":\"抖音短视频\",\"display_density\":\"hdpi\",\"release_build\":\"55fb587_20191121\",\"clientudid\":\"" + clientudid + "\",\"carrier\":\"CHINA MOBILE\",\"device_brand\":\"HUAWEI\",\"sim_serial_number\":[],\"sig_hash\":\"aea615ab910015038f73c47e45d21466\",\"appkey\":\"57bfa27c67e58e7d920028d3\",\"sim_region\":\"cn\",\"region\":\"CN\",\"aid\":\"1128\",\"rom_version\":\"LMY47I\"},\"magic_tag\":\"ss_app_log\"}";

        System.out.println(url);

        System.out.println(JSON.toJSONString(JSON.parseObject(str)));
        byte[] bArr2 = str.getBytes("UTF-8");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8192);
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gZIPOutputStream.write(bArr2);
        gZIPOutputStream.close();
        bArr2 = byteArrayOutputStream.toByteArray();
        bArr2 = ttEncrypt(bArr2);

        System.out.println(JSON.toJSONString(bArr2));

        final RequestBody formBody = RequestBody.create(MediaType.parse("application/octet-stream;tt-data=a"), bArr2);
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .addHeader("X-SS-REQ-TICKET", System.currentTimeMillis() + "")
                .addHeader("X-Khronos", time + "")
                .addHeader("sdk-version", "1")
                .addHeader("Content-Type", "application/octet-stream;tt-data=a")
                .addHeader("Cookie", "")
                .addHeader("X-Pods", "")
                .addHeader("Connection", "Keep-Alive")
                .addHeader("User-Agent", "okhttp/3.10.0.1")
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Response execute = okHttpClient.newCall(request).execute();
        System.out.println(execute.body().string());
    }*/

    //生成随机数字和字母,
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(6) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    public static String getMc() {
        StringBuffer mac = new StringBuffer();
        for (int i = 1; i <= 6; i++) {
            String one = getStringRandom(1);
            String two = getStringRandom(1);
            mac.append(one).append(two);
            if (i != 6) mac.append(":");
        }
        return mac.toString().toLowerCase();
    }

    public static String getRanInt(int i) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < i; j++) {
            String s = Integer.toHexString(new Random().nextInt(10));
            sb.append(s);
        }
        return sb.toString();
    }


    public static void exec(byte[] bArr2) throws Exception {
        String serial_number = UUID.randomUUID().toString().substring(24);
        String udid = "869" + getRanInt(12);
        String openudid = "fa25" + UUID.randomUUID().toString().substring(24);
        String clientudid = UUID.randomUUID().toString();
        String mc = getMc();
        String _rticket = System.currentTimeMillis() + "";   //获取当前时间

        String url = "https://log.snssdk.com/service/2/device_register/?uuid=" + udid + "&openudid=" + openudid + "&_rticket=" + _rticket + "&retry_type=no_retry&ac=4G&channel=tengxun&aid=1128&host_abi=armeabi-v7a&app_name=douyin&version_code=930&version_name=9.3.0&device_platform=android&ssmix=a&device_type=MT2-L05&device_brand=HUAWEI&language=zh&os_api=22&os_version=5.1.1&manifest_version_code=930&resolution=720%2A1280&dpi=320&update_version_code=9302";
        int time = (int) (System.currentTimeMillis() / 1000);

        System.out.println(JSON.toJSONString(bArr2));

        final RequestBody formBody = RequestBody.create(MediaType.parse("application/octet-stream;tt-data=a"), bArr2);
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .addHeader("X-SS-REQ-TICKET", System.currentTimeMillis() + "")
                .addHeader("X-Khronos", time + "")
                .addHeader("sdk-version", "1")
                .addHeader("Content-Type", "application/octet-stream;tt-data=a")
                .addHeader("Cookie", "")
                .addHeader("X-Pods", "")
                .addHeader("Connection", "Keep-Alive")
                .addHeader("User-Agent", "okhttp/3.10.0.1")
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Response execute = okHttpClient.newCall(request).execute();
        System.out.println(execute.body().string());
    }


    private static String SEPARATOR_OF_MAC = ":";

    /**
     * Generate a random MAC address for qemu/kvm
     * 52-54-00 used by qemu/kvm
     * The remaining 3 fields are random,  range from 0 to 255
     *
     * @return MAC address string
     */
    public static String randomMac4Qemu() {
        Random random = new Random();
        String[] mac = {
                String.format("%02x", 0x10),
                String.format("%02x", 0x2a),
                String.format("%02x", 0xb3),
                String.format("%02x", random.nextInt(0x7f)),
                String.format("%02x", random.nextInt(0xff)),
                String.format("%02x", random.nextInt(0xff))
        };
        return String.join(SEPARATOR_OF_MAC, mac);
    }




}
