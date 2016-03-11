package com.gitrose.mobile.download.video;

//import com.alibaba.sdk.android.oss.config.HttpHeaderField;
import com.gitrose.mobile.model.VideoDownLoad;
import com.umeng.socialize.common.SocializeConstants;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.litepal.crud.DataSupport;

public class DownLoadRunnableImpl extends DownLoadRunnable<VideoDownLoad> {
    private static final int BUFFER_SIZE = 20480;

    public DownLoadRunnableImpl(VideoDownLoad video) {
        super(video);
    }

    public void run() {
        Exception e;
        Throwable th;
        VideoDownLoad videoDownLoad;
        String[] strArr;
        if (!isCancelled()) {
            File file = new File(this.bean.getRealPath());
            BufferedInputStream bis = null;
            RandomAccessFile fos = null;
            byte[] buf = new byte[BUFFER_SIZE];
            String[] strArr2;
            VideoDownLoad videoDownLoad2;
            String[] strArr3;
            try {
                long contentLength = this.bean.getEndPos();
                if (contentLength < 0) {
                    contentLength = getContetnLengteh(this.bean.getUrl(), this.client);
                    if (contentLength < 0) {
                        DownLoadClientImpl.getInstance().notifyData(this.bean, 5);
                        this.isFinished = true;
                        if (bis != null) {
                            try {
                                bis.close();
                            } catch (IOException e2) {
                                if (fos != null) {
                                    try {
                                        fos.close();
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        }
                        if (fos != null) {
                            fos.close();
                        }
                        strArr2 = new String[1];
                        strArr2[0] = "select * from VideoDownLoad where videoId =" + this.bean.getVideoId() + " and uid=" + this.bean.getUid();
//                        if (DataSupport.findBySQL(strArr2).moveToFirst()) {
//                            videoDownLoad2 = this.bean;
//                            strArr3 = new String[3];
//                            strArr3[1] = this.bean.getVideoId();
//                            strArr3[2] = this.bean.getUid();
//                            videoDownLoad2.updateAll(strArr3);
//                            return;
//                        }
//                        this.bean.save();
                        return;
                    }
                    this.bean.setEndPos(contentLength);
                    this.endPos = contentLength;
                }
                if (file.exists() && contentLength != file.length()) {
                    file.delete();
                }
                if (!file.exists()) {
                    DownLoadClientImpl.getInstance().notifyData(this.bean, 2);
                    String str = "Range";
                    long j = this.startPos;
                    Header header_size = new BasicHeader("Range", (new StringBuilder("bytes=")).append(startPos).append("-").append(endPos).toString());
                    this.get.addHeader(header_size);
                    if (isCancelled()) {
                        this.isFinished = true;
                        if (bis != null) {
                            try {
                                bis.close();
                            } catch (IOException e3) {
                                if (fos != null) {
                                    try {
                                        fos.close();
                                    } catch (IOException e12) {
                                        e12.printStackTrace();
                                    }
                                }
                            }
                        }
                        if (fos != null) {
                            fos.close();
                        }
                        strArr2 = new String[1];
                        strArr2[0] = "select * from VideoDownLoad where videoId =" + this.bean.getVideoId() + " and uid=" + this.bean.getUid();
//                        if (DataSupport.findBySQL(strArr2).moveToFirst()) {
//                            videoDownLoad2 = this.bean;
//                            strArr3 = new String[3];
//                            strArr3[1] = this.bean.getVideoId();
//                            strArr3[2] = this.bean.getUid();
//                            videoDownLoad2.updateAll(strArr3);
//                            return;
//                        }
//                        this.bean.save();
                        return;
                    }
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = this.get;
                    HttpResponse response = client.execute(request);

//                    HttpResponse response = new DefaultHttpClient().execute(this.get);
//                    HttpResponse response = HttpClientBuilder.create().build().execute(this.get);
//                    int statusCode = response.getStatusLine().getStatusCode();
                    RandomAccessFile fos2 = new RandomAccessFile(this.file, "rw");
                    try {
                        fos2.seek(this.startPos);
                        HttpEntity resEntity = response.getEntity();
                        InputStream iStream = resEntity.getContent();
                        BufferedInputStream bis2 = new BufferedInputStream(iStream);
                        try {
                            this.curPosition = this.bean.getStartPos();
                            while (true) {
                                if (this.curPosition < this.endPos) {
                                    int len = bis2.read(buf, 0, BUFFER_SIZE);
                                    if (len == -1) {
                                        break;
                                    }
                                    fos2.write(buf, 0, len);
                                    this.curPosition += (long) len;
                                    this.bean.setCurPos(this.curPosition);
                                    this.bean.setStartPos(this.curPosition);
                                    DownLoadClientImpl.getInstance().notifyData(this.bean, 1);
                                } else {
                                    break;
                                }
                            }
                            this.file.renameTo(new File(this.bean.getRealPath()));
                            fos = fos2;
                            bis = bis2;
                        } catch (Exception e4) {
                            e = e4;
                            fos = fos2;
                            bis = bis2;
                        } catch (Throwable th2) {
                            th = th2;
                            fos = fos2;
                            bis = bis2;
                        }
                    } catch (Exception e5) {
                        e = e5;
                        fos = fos2;
                        try {
                            e.printStackTrace();
                            if (isCancelled()) {
                                DownLoadClientImpl.getInstance().notifyData(this.bean, 6);
                            } else if (this.isPause) {
                                DownLoadClientImpl.getInstance().notifyData(this.bean, 5);
                            } else {
                                DownLoadClientImpl.getInstance().notifyData(this.bean, 4);
                            }
                            this.isFinished = true;
                            if (bis != null) {
                                try {
                                    bis.close();
                                } catch (IOException e6) {
                                    if (fos != null) {
                                        try {
                                            fos.close();
                                        } catch (IOException e122) {
                                            e122.printStackTrace();
                                        }
                                    }
//                                    strArr2 = new String[1];
//                                    strArr2[0] = "select * from VideoDownLoad where videoId =" + this.bean.getVideoId() + " and uid=" + this.bean.getUid();
//                                    if (DataSupport.findBySQL(strArr2).moveToFirst()) {
//                                        videoDownLoad2 = this.bean;
//                                        strArr3 = new String[3];
//                                        strArr3[1] = this.bean.getVideoId();
//                                        strArr3[2] = this.bean.getUid();
//                                        videoDownLoad2.updateAll(strArr3);
//                                        return;
//                                    }
//                                    this.bean.save();
                                }
                            }
                            if (fos != null) {
                                fos.close();
                            }
//                            strArr2 = new String[1];
//                            strArr2[0] = "select * from VideoDownLoad where videoId =" + this.bean.getVideoId() + " and uid=" + this.bean.getUid();
//                            if (DataSupport.findBySQL(strArr2).moveToFirst()) {
//                                this.bean.save();
//                            }
//                            videoDownLoad2 = this.bean;
//                            strArr3 = new String[3];
//                            strArr3[1] = this.bean.getVideoId();
//                            strArr3[2] = this.bean.getUid();
//                            videoDownLoad2.updateAll(strArr3);
                            return;
                        } catch (Throwable th3) {
                            th = th3;
                            this.isFinished = true;
                            if (bis != null) {
                                try {
                                    bis.close();
                                } catch (IOException e7) {
                                    if (fos != null) {
                                        try {
                                            fos.close();
                                        } catch (IOException e1222) {
                                            e1222.printStackTrace();
                                        }
                                    }
//                                    strArr3 = new String[1];
//                                    strArr3[0] = "select * from VideoDownLoad where videoId =" + this.bean.getVideoId() + " and uid=" + this.bean.getUid();
//                                    if (DataSupport.findBySQL(strArr3).moveToFirst()) {
//                                        videoDownLoad = this.bean;
//                                        strArr = new String[3];
//                                        strArr[1] = this.bean.getVideoId();
//                                        strArr[2] = this.bean.getUid();
//                                        videoDownLoad.updateAll(strArr);
//                                    } else {
//                                        this.bean.save();
//                                    }
//                                    throw th;
                                }
                            }
                            if (fos != null) {
                                fos.close();
                            }
//                            strArr3 = new String[1];
//                            strArr3[0] = "select * from VideoDownLoad where videoId =" + this.bean.getVideoId() + " and uid=" + this.bean.getUid();
//                            if (DataSupport.findBySQL(strArr3).moveToFirst()) {
//                                videoDownLoad = this.bean;
//                                strArr = new String[3];
//                                strArr[1] = this.bean.getVideoId();
//                                strArr[2] = this.bean.getUid();
//                                videoDownLoad.updateAll(strArr);
//                            } else {
//                                this.bean.save();
//                            }
//                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        fos = fos2;
                        this.isFinished = true;
                        if (bis != null) {
                            bis.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
//                        strArr3 = new String[1];
//                        strArr3[0] = "select * from VideoDownLoad where videoId =" + this.bean.getVideoId() + " and uid=" + this.bean.getUid();
//                        if (DataSupport.findBySQL(strArr3).moveToFirst()) {
//                            this.bean.save();
//                        } else {
//                            videoDownLoad = this.bean;
//                            strArr = new String[3];
//                            strArr[1] = this.bean.getVideoId();
//                            strArr[2] = this.bean.getUid();
//                            videoDownLoad.updateAll(strArr);
//                        }
//                        throw th;
                    }
                }
                this.bean.setCurPos(this.endPos);
                this.bean.setStartPos(this.endPos);
                DownLoadClientImpl.getInstance().notifyData(this.bean, 9);
                this.isFinished = true;
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e8) {
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException e12222) {
                                e12222.printStackTrace();
                            }
                        }
                    }
                }
                if (fos != null) {
                    fos.close();
                }
                strArr2 = new String[1];
                strArr2[0] = "select * from VideoDownLoad where videoId =" + this.bean.getVideoId() + " and uid=" + this.bean.getUid();
//                if (DataSupport.findBySQL(strArr2).moveToFirst()) {
//                    videoDownLoad2 = this.bean;
//                    strArr3 = new String[3];
//                    strArr3[1] = this.bean.getVideoId();
//                    strArr3[2] = this.bean.getUid();
//                    videoDownLoad2.updateAll(strArr3);
//                    return;
//                }
//                this.bean.save();
            } catch (Exception e9) {
                e = e9;
                e.printStackTrace();
                if (isCancelled()) {
                    DownLoadClientImpl.getInstance().notifyData(this.bean, 6);
                } else if (this.isPause) {
                    DownLoadClientImpl.getInstance().notifyData(this.bean, 5);
                } else {
                    DownLoadClientImpl.getInstance().notifyData(this.bean, 4);
                }
                this.isFinished = true;
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                strArr2 = new String[1];
                strArr2[0] = "select * from VideoDownLoad where videoId =" + this.bean.getVideoId() + " and uid=" + this.bean.getUid();
//                if (DataSupport.findBySQL(strArr2).moveToFirst()) {
//                    videoDownLoad2 = this.bean;
//                    strArr3 = new String[3];
//                    strArr3[1] = this.bean.getVideoId();
//                    strArr3[2] = this.bean.getUid();
//                    videoDownLoad2.updateAll(strArr3);
//                    return;
//                }
//                this.bean.save();
            }
        }
    }
}
