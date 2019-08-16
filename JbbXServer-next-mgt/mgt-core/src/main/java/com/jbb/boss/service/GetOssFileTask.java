 package com.jbb.boss.service;

import java.io.File;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;

public class GetOssFileTask implements Runnable {
    private OSSClient ossClient;
    private String bucketName;
    private String objectName;
    private String file;
    
    public GetOssFileTask(OSSClient ossClient , String bucketName, String objectName, String file) {
        this.ossClient= ossClient;
        this.bucketName=bucketName;
        this.objectName=objectName;
        this.file=file;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName());
        ossClient.getObject(new GetObjectRequest(bucketName, objectName),
            new File(file));
    }
}
