package com.jbb.mgt.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import com.jbb.mgt.helipay.vo.TransferRspVo;

import net.sf.json.JSONObject;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException {
        
        
        Object o = null;
        TransferRspVo vo = (TransferRspVo)o;
        
        JSONObject xyrs = new JSONObject();
        xyrs.element("logo", "https://jiebangbang.cn/config/logo/iou-logo7.png");
        xyrs.element("url", "http://xyrsapi1.fengyjf.com/h5/invite.jsp?invitationCode=null&channelCode=jiebangbang1");
        xyrs.element("name", "信用人生");
        xyrs.element("shortName", "xyrs");
        xyrs.element("desc1", "无视黑白");
        xyrs.element("desc2", "2分钟认证");
        xyrs.element("desc3", "10秒到账");

        JSONObject xnqb = new JSONObject();
        xnqb.element("logo", "https://jiebangbang.cn/config/logo/iou-logo8.png");
        xnqb.element("url", "https://api.9maibei.com/Mall/public/user/promotion-register.html?channel=jiebangbang");
        xnqb.element("name", "犀牛钱包");
        xnqb.element("shortName", "xiniuqianbao");
        xnqb.element("desc1", "额度：最高5000");
        xnqb.element("desc2", "有身份证就能申请下款");
        xnqb.element("desc3", "");

        JSONObject mlrs = new JSONObject();
        mlrs.element("logo", "https://jiebangbang.cn/config/logo/iou-logo11.png");
        mlrs.element("url", "http://mlrsapi1.jrwljd.com/h5/invite.jsp?invitationCode=null&channelCode=jiebangbang1");
        mlrs.element("name", "美丽人生");
        mlrs.element("shortName", "mlrs");
        mlrs.element("desc1", "额度：200-6000");
        mlrs.element("desc2", "秒下款");
        mlrs.element("desc3", "");

        JSONObject hyqb = new JSONObject();
        hyqb.element("logo", "https://jiebangbang.cn/config/logo/iou-logo12.png");
        hyqb.element("url", "http://register.volsier.cn/HY_Mall/public/user/promotion_register.html?channel=jiebangbang");
        hyqb.element("name", "红叶钱包");
        hyqb.element("shortName", "hyqb");
        hyqb.element("desc1", "额度：600-5000");
        hyqb.element("desc2", "3分钟下款");
        hyqb.element("desc3", "");
        
        JSONObject xlqb = new JSONObject();
        xlqb.element("logo", "https://jiebangbang.cn/config/logo/iou-logo21.png");
        xlqb.element("url", "http://recycle.volsier.cn/Recovery/promotion_register?channel=jiebangbang001");
        xlqb.element("name", "小鹿钱包");
        xlqb.element("shortName", "xlqb");
        xlqb.element("desc1", "额度：600-5000");
        xlqb.element("desc2", "秒下款");
        xlqb.element("desc3", "");

        /// Users/VincentTang/ws/jbb/jbb-h5-singup/config
        File file = new File("/Users/VincentTang/Downloads/version6.csv");

        BufferedReader br = new BufferedReader(new FileReader(file));
        String s = null;
        int index = 0;
        while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
            System.out.println(s);
            if (index++ == 0)
                continue;

            String[] arr = s.split(",");
            
            String str = arr[0];
            int sourceId = Integer.valueOf(arr[0]);
            if(sourceId<100){
                str ="0" + arr[0];
            }

            File dir = new File("/Users/VincentTang/ws/jbb/config/" + str);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File configFile = new File("/Users/VincentTang/ws/jbb/config/" + str + "/config.json");
            configFile.createNewFile();
            FileOutputStream os = new FileOutputStream(configFile);

            JSONObject configO = new JSONObject();
            
           
           
            configO.element("sourceId", str);
            configO.element("name", arr[1]);
            
         
            
            
            configO.element("redirectUrl", "https://jiebangbang.cn/" + arr[4] + "/?sourceId=" + str);

            if ("xiniuqianbao".equals(arr[5])) {
                configO.element("h5", JSONObject.toBean(xnqb));
            } else if ("xyrs".equals(arr[5])) {
                configO.element("h5", JSONObject.toBean(xyrs));
            } else if ("mlrs".equals(arr[5])) {
                configO.element("h5", JSONObject.toBean(mlrs));
            } else if ("hyqb".equals(arr[5])) {
                configO.element("h5", JSONObject.toBean(hyqb));
            }else if ("xlqb".equals(arr[5])) {
                configO.element("h5", JSONObject.toBean(xlqb));
            }

            os.write(configO.toString(4).getBytes());
            os.close();

        }
        br.close();;

    }
}
