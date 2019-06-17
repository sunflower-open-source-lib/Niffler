package cn.gavinwang.student.test;

import cn.gavinwang.student.utils.QrCodeUtil;
import cn.gavinwang.student.utils.UpLoadUtil;

public class Test {
    public static void main(String[] args) {
        //生成家庭二维码
        QrCodeUtil qrCodeUtil = new QrCodeUtil();
        String fileName = "111111.jpg";
        String url = "deviceId="+111111;
        String path = "D://testQrcode//";
        qrCodeUtil.createQrCode(url, path, fileName);
        //存储到七牛云
        UpLoadUtil upLoadUtil = new UpLoadUtil();
        upLoadUtil.setKey("111111.jpg");
        upLoadUtil.setLocalFilePath("D://testQrcode//"+fileName);
        System.out.println("D://testQrcode//"+fileName);
        upLoadUtil.upload();

    }
}
