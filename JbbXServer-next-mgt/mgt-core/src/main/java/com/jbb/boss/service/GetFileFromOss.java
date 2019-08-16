package com.jbb.boss.service;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

public class GetFileFromOss {

    private static ExecutorService pool;

    public static void main(String[] args) {

        pool = new ThreadPoolExecutor(10, 15, 3000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
            Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "";
        String accessKeySecret = "";
        String bucketName = "xjt-report";
        String filePath = "/Users/VincentTang/Downloads/xjt-report";

        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        final int maxKeys = 200;
        String nextMarker = "XY_BLACK_8235";
        ObjectListing objectListing;

        do {
            objectListing =
                ossClient.listObjects(new ListObjectsRequest(bucketName).withMarker(nextMarker).withMaxKeys(maxKeys));

            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
  
            for (OSSObjectSummary s : sums) {
                System.out.println("\t" + s.getKey());
                String objectName = s.getKey();
                // ossClient.getObject(new GetObjectRequest(bucketName, objectName),
                // new File(filePath + "/" + s.getKey()));
                String file = filePath + "/" + s.getKey();

                pool.execute(new GetOssFileTask(ossClient, bucketName, objectName, file));
            }
            
            nextMarker = objectListing.getNextMarker();
            
            try {
                Thread.sleep(60000L);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                 e.printStackTrace();
            }

        } while (objectListing.isTruncated());

        try {
            Thread.sleep(30000000l);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
             e.printStackTrace();
        }
        // 关闭OSSClient。
        ossClient.shutdown();

    }

}
