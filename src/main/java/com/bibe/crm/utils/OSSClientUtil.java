//package com.bibe.crm.utils;
//
//
//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClientBuilder;
//import com.aliyun.oss.model.GeneratePresignedUrlRequest;
//import com.aliyun.oss.model.GetObjectRequest;
//import com.aliyun.oss.model.OSSObject;
//import com.aliyun.oss.model.ObjectMetadata;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.Date;
//
///**
// * Created by wang on 2019/11/5
// */
//
//@Component
//@Slf4j
//public class OSSClientUtil {
//
//    /**
//     * 阿里云API的密钥Access Key ID
//     */
//    private static String accessKeyId;
//    /**
//     * 阿里云API的密钥Access Key Secret
//     */
//    private static String accessKeySecret;
//    /**
//     * 阿里云API的内或外网域名
//     */
//    private static String endpoint;
//    /**
//     * 阿里云API的bucket名称
//     */
//    private static String bucketName;
//    /**
//     * 阿里云API的文件夹名称
//     */
//    private static String folder;
//
//
//    @Value("${aliyun.accessKeyId}")
//    public void setAccessKeyId(String accessKeyId) {
//        com.ibout.lora.admin.util.OSSClientUtil.accessKeyId = accessKeyId;
//    }
//
//    @Value("${aliyun.accessKeySecret}")
//    public void setAccessKeySecret(String accessKeySecret) {
//        com.ibout.lora.admin.util.OSSClientUtil.accessKeySecret = accessKeySecret;
//    }
//
//    @Value("${aliyun.oss.endpoint}")
//    public void setEndpoint(String endpoint) {
//        com.ibout.lora.admin.util.OSSClientUtil.endpoint = endpoint;
//    }
//
//    @Value("${aliyun.oss.bucketName}")
//    public void setBucketName(String bucketName) {
//        com.ibout.lora.admin.util.OSSClientUtil.bucketName = bucketName;
//    }
//
//    @Value("${aliyun.oss.folder}")
//    public void setFolder(String folder) {
//        com.ibout.lora.admin.util.OSSClientUtil.folder = folder;
//    }
//
//
//    private OSSClientUtil() {
//
//    }
//
//
//    /**
//     * 获取阿里云OSS客户端对象
//     *
//     * @return ossClient
//     */
//    public static OSS getOSSClient() {
//        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//    }
//
//
//    /**
//     * 文件预览
//     *
//     * @param oss
//     * @param objectKey
//     * @return
//     */
//    public String previewDoc(OSS oss, String objectKey) {
//        String process = "imm/previewdoc";
//        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, folder + objectKey);
//        getObjectRequest.setProcess(process);
//        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, folder + objectKey);
//        request.setProcess(process);
//        request.setExpiration(new Date(new Date().getTime() + 3600 * 1000));
//        return oss.generatePresignedUrl(request).toString();
//    }
//
//
//    /**
//     * 验证文件是否存在
//     *
//     * @param oss
//     * @param fileName
//     * @return
//     */
//    public Boolean checkFileObject(OSS oss, String fileName) {
//        return oss.doesObjectExist(bucketName, folder + fileName);
//    }
//
//
//    /**
//     * 上传图片至OSS
//     *
//     * @param file 上传文件
//     * @return String 返回的唯一MD5数字签名
//     */
//    public String uploadObj(MultipartFile file, OSS oss) {
//        String resultStr = null;
//        try {
//            // 以输入流的形式上传文件
//            InputStream is = file.getInputStream();
//            // 文件名
//            String fileName = file.getOriginalFilename();
//            File a = new File("");
//            // 文件大小
//            Long fileSize = file.getSize();
//            // 创建上传Object的Metadata
//            ObjectMetadata metadata = new ObjectMetadata();
//            // 上传的文件的长度
//            metadata.setContentLength(is.available());
//            // 指定该Object被下载时的网页的缓存行为
//            metadata.setCacheControl("no-cache");
//            // 指定该Object下设置Header
//            metadata.setHeader("Pragma", "no-cache");
//            // 指定该Object被下载时的内容编码格式
//            metadata.setContentEncoding("utf-8");
//            // 文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
//            // 如果没有扩展名则填默认值application/octet-stream
//            metadata.setContentType(getContentType(fileName));
//            // 指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
//            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
//            // 上传文件 (上传文件流的形式)
//            oss.putObject(bucketName, folder + fileName, is, metadata);
//            // 解析结果
//            //resultStr = putResult.getETag();
//            resultStr = getImgUrl(bucketName + folder + fileName, oss);
//        } catch (IOException e) {
//            log.error(" oss uploadObj exception{}", e);
//        } finally {
//            oss.shutdown();
//        }
//        return resultStr;
//    }
//
//
//    /**
//     * 上传图片至OSS
//     *
//     * @param file     上传文件
//     * @param oss
//     * @param fileName 生成的唯一文件名
//     * @return String 返回的唯一MD5数字签名
//     */
//    public String uploadObj(MultipartFile file, OSS oss, String fileName) {
//        String resultStr = null;
//        try {
//            // 以输入流的形式上传文件
//            InputStream is = file.getInputStream();
//            // 文件大小
//            Long fileSize = file.getSize();
//            // 创建上传Object的Metadata
//            ObjectMetadata metadata = new ObjectMetadata();
//            // 上传的文件的长度
//            metadata.setContentLength(is.available());
//            // 指定该Object被下载时的网页的缓存行为
//            metadata.setCacheControl("no-cache");
//            // 指定该Object下设置Header
//            metadata.setHeader("Pragma", "no-cache");
//            // 指定该Object被下载时的内容编码格式
//            metadata.setContentEncoding("utf-8");
//            // 文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
//            // 如果没有扩展名则填默认值application/octet-stream
//            metadata.setContentType("application/octet-stream");
//            // 指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
//            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
//            // 上传文件 (上传文件流的形式)
//            oss.putObject(bucketName, folder + fileName, is, metadata);
//            // 解析结果
//            //resultStr = putResult.getETag();
//            resultStr = getImgUrl(bucketName + folder + fileName, oss);
//        } catch (IOException e) {
//            log.error(" oss uploadObj exception{}", e);
//        } finally {
//            oss.shutdown();
//        }
//        return resultStr;
//    }
//
//
//    /**
//     * 流式下载
//     *
//     * @param objName
//     * @param oss
//     * @param response
//     * @throws IOException
//     */
//    public void download(String objName, OSS oss, HttpServletResponse response) {
//        try {
//            OSSObject object = getOSSClient().getObject(bucketName, folder + objName);
//
//            BufferedInputStream in = new BufferedInputStream(object.getObjectContent());
//            // 缓冲文件输出流
//            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
//            // 通知浏览器以附件形式下载
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(objName, "UTF-8"));
//            byte[] car = new byte[1024];
//            int L = 0;
//            while ((L = in.read(car)) != -1) {
//                out.write(car, 0, L);
//            }
//            if (out != null) {
//                out.flush();
//                //关闭流
//                out.close();
//            }
//            if (in != null) {
//                in.close();
//            }
//        } catch (IOException e) {
//            log.error("oss download exception{}", e);
//        } finally {
//            oss.shutdown();
//        }
//    }
//
//
//    public String getImgUrl(String fileUrl, OSS oss) {
//        if (!StringUtils.isEmpty(fileUrl)) {
//            String[] split = fileUrl.split("/");
//            return this.getUrl(this.folder + split[split.length - 1], oss);
//        }
//        return null;
//    }
//
//
//    /**
//     * 获得url链接
//     *
//     * @param key
//     * @return
//     */
//    public String getUrl(String key, OSS oss) {
//        // 设置URL过期时间为10年  3600l* 1000*24*365*10
//        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
//        // 生成URL
//        URL url = oss.generatePresignedUrl(bucketName, key, expiration);
//        if (url != null) {
//            return url.toString();
//        }
//        return null;
//    }
//
//
//    /**
//     * 删除文件
//     *
//     * @param oss
//     * @param fileName
//     */
//    public void deleteFile(OSS oss, String fileName) {
//        try {
//            oss.deleteObject(bucketName, folder + fileName);
//            log.info("删除" + folder + "下的文件" + "" + fileName + "成功");
//        } catch (Exception e) {
//            log.error("oss deleteFile exception {}", e);
//        } finally {
//            oss.shutdown();
//            ;
//        }
//    }
//
//
//    /**
//     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
//     *
//     * @param fileName 文件名
//     * @return 文件的contentType
//     */
//    public String getContentType(String fileName) {
//        // 文件的后缀名
//        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
//        if (".bmp".equalsIgnoreCase(fileExtension)) {
//            return "image/bmp";
//        }
//        if (".gif".equalsIgnoreCase(fileExtension)) {
//            return "image/gif";
//        }
//        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)
//                || ".png".equalsIgnoreCase(fileExtension)) {
//            return "image/jpeg";
//        }
//        if (".png".equalsIgnoreCase(fileExtension)) {
//            return "image/png";
//        }
//        if (".html".equalsIgnoreCase(fileExtension)) {
//            return "text/html";
//        }
//        if (".txt".equalsIgnoreCase(fileExtension)) {
//            return "text/plain";
//        }
//        if (".vsd".equalsIgnoreCase(fileExtension)) {
//            return "application/vnd.visio";
//        }
//        if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
//            return "application/vnd.ms-powerpoint";
//        }
//        if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
//            return "application/msword";
//        }
//        if (".xml".equalsIgnoreCase(fileExtension)) {
//            return "text/xml";
//        }
//        // 默认返回类型
//        return "";
//    }
//}
