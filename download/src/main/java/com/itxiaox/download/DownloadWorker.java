package com.itxiaox.download;

import android.content.Context;
import android.graphics.Interpolator;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DownloadWorker extends Worker {

    private static final String TAG = "DownloadWorker";
    public DownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String baseUrl = getInputData().getString(Constants.KEY_BASE_URL);
        String url = getInputData().getString(Constants.KEY_URL);
        String path =getInputData().getString(Constants.KEY_SAVE_FILE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
//                //通过线程池获取一个线程，指定callback在子线程中运行。
//                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();
        DownloadService service = retrofit.create(DownloadService.class);
        //
        final Data.Builder build = new Data.Builder();
        try {
            Response<ResponseBody> responseBody = service.download(url).execute();
            //将文件保存到磁盘
            DownLoadUtils.writeResponseToDisk(path, responseBody, new DownloadListener() {
                @Override
                public void onStart() {
                }

                @Override
                public void onProgress(int progress) {
//                    Result.success(build.putString("result",String.valueOf(progress))
//                            .build());
                }

                @Override
                public void onFinish(String path) {
//
//                    Result.success(build.putString("result",path)
//                            .build());
                    File zipFile = new File(path);
                    if(!zipFile.exists()){
                        Log.e(TAG, "onFinish: zipFile not exist" );
                        return;
                    }
                    File dir = new File(zipFile.getParent(),"models/");
                    if (!dir.exists()){
                        dir.mkdirs();
                    }
                    try {
                        upZipFile(zipFile,dir.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail(String errorInfo) {
//                    Result.failure(build.putString("result",errorInfo)
//                            .build());
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
//            Result.failure(build.putString("result",e.getMessage())
//                    .build());
        }


        //执行下载任务
        return Result.success();
    }


    /**
     * 解压缩一个文件
     *
     * @param zipFile 压缩文件
     * @param folderPath 解压缩的目标目录
     * @throws IOException 当解压缩过程出错时抛出
     */
    public static void upZipFile(File zipFile, String folderPath) throws ZipException, IOException {
        File desDir = new File(folderPath);
        if (!desDir.exists()) {
            desDir.mkdirs();
        }
        ZipFile zf = new ZipFile(zipFile);
        for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements();) {
            ZipEntry entry = ((ZipEntry)entries.nextElement());
            InputStream in = zf.getInputStream(entry);
            String str = folderPath + File.separator + entry.getName();
            str = new String(str.getBytes("8859_1"), "GB2312");
            File desFile = new File(str);
            if (!desFile.exists()) {
                File fileParentDir = desFile.getParentFile();
                if (!fileParentDir.exists()) {
                    fileParentDir.mkdirs();
                }
                desFile.createNewFile();
            }
            OutputStream out = new FileOutputStream(desFile);
            byte[] buffer = new byte[1024];
            int realLength;
            while ((realLength = in.read(buffer)) > 0) {
                out.write(buffer, 0, realLength);
            }
            in.close();
            out.close();
        }
    }

}
