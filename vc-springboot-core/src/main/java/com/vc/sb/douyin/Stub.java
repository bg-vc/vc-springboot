package com.vc.sb.douyin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author:       VinceChen
 * Date:         2020-06-14 23:37
 * Description:
 */


public class Stub {
    public static void main(String[] args) throws Exception {
        String k = "aweme_id=6825803160485973248&text=%E7%B2%BE%E8%BE%9F&text_extra=%5B%5D&is_self_see=0&sticker_id=&sticker_source=0&sticker_width=0&sticker_height=0&channel_id=0&city=610700&action_type=0";
        String STUB = encryption(k).toUpperCase();

        System.out.println("STUB:"+STUB);

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

}
