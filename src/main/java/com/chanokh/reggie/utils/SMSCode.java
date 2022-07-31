//package com.chanokh.reggie.utils;
//
//
//
//        import com.aliyun.tea.*;
//        import com.aliyun.dysmsapi20170525.models.*;
//        import com.aliyun.teaopenapi.models.*;
//
//public class SMSCode {
//
//    /**
//     * 使用AK&SK初始化账号Client
//     * @param accessKeyId
//     * @param accessKeySecret
//     * @return Client
//     * @throws Exception
//     */
//    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
//        Config config = new Config()
//                // 您的 AccessKey ID
//                .setAccessKeyId(accessKeyId)
//                // 您的 AccessKey Secret
//                .setAccessKeySecret(accessKeySecret);
//        // 访问的域名
//        config.endpoint = "dysmsapi.aliyuncs.com";
//        return new com.aliyun.dysmsapi20170525.Client(config);
//    }
//
//    public static void main(String[] args_) throws Exception {
//        java.util.List<String> args = java.util.Arrays.asList(args_);
//        com.aliyun.dysmsapi20170525.Client client = SMSCode.createClient("accessKeyId", "accessKeySecret");
//        AddShortUrlRequest addShortUrlRequest = new AddShortUrlRequest()
//                .setSourceUrl("your_value")
//                .setShortUrlName("your_value");
//        try {
//            // 复制代码运行请自行打印 API 的返回值
//            client.addShortUrl(addShortUrlRequest);
//        } catch (TeaException error) {
//            // 如有需要，请打印 error
//            com.aliyun.teautil.Common.assertAsString(error.message);
//        } catch (Exception _error) {
//            TeaException error = new TeaException(_error.getMessage(), _error);
//            // 如有需要，请打印 error
//            com.aliyun.teautil.Common.assertAsString(error.message);
//        }
//    }
//}
