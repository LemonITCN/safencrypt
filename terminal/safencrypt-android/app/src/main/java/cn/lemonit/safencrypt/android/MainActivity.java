package cn.lemonit.safencrypt.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.lemonit.safencrypt.android.safencrypt.AesUtil;
import cn.lemonit.safencrypt.android.safencrypt.RsaUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String pwd = "1234567890123456";
        String encodedData = AesUtil.encrypt_CBC_ISO10126Padding("hello", pwd);
        System.out.println("en = " + encodedData);
        System.out.println("tx = " + AesUtil.decrypt_CBC_ISO10126Padding(encodedData, pwd));

        String modulus = "00ab36585724640855ddb0cbccd82640d895bfac8b4568eadda41ee690a265c1870c4eaf649af0ec496efe693696f1e393a94decedd0047c3d6fc13328e60509a3b2ae9547c9b7fbfa1ae12e6081513aa1469a45bb0e8b06308f40053b1283bc50ad58fa7b38c1389f32405d8cb206c3ef0aa68a3149d589b570a61b610b0c091d";
        String exponent = "010001";
        String rsaEnData = RsaUtil.encryptString(modulus, exponent, "DLFCDLFCDLFCDLFC");
        System.out.println("RSA EN DATA = " + rsaEnData);
    }
}
