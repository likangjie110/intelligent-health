
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

/**
 * @Auther: 等星星溺水
 * @Date: 2022/10/16 09:57
 * @Description:
 */

public class QiNiuTest {
    @Test
    public void test01(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
       //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
         //...生成上传凭证，然后准备上传
        String accessKey = "c9zKLZJ6NbmJZmf0Vfh2Dql1VIWX_DMUjFSgvh4B";
        String secretKey = "A5bD3Tip4DKyFhbrvH4BBdS0B76LSOQTMgNBydKo";
        String bucket = "cjl-czhealthspace-01";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:\\Users\\28198\\Pictures\\Saved Pictures\\wallhaven-2yg3z6.jpg";
         //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "wallhaven-2yg3z6.jpg";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }
    @Test
    public void test02(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
      //...其他参数参考类注释
        String accessKey = "c9zKLZJ6NbmJZmf0Vfh2Dql1VIWX_DMUjFSgvh4B";
        String secretKey = "A5bD3Tip4DKyFhbrvH4BBdS0B76LSOQTMgNBydKo";
        String bucket = "cjl-czhealthspace-01";
        String key = "wallhaven-2yg3z6.jpg";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
}
