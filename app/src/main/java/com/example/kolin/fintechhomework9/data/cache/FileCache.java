package com.example.kolin.fintechhomework9.data.cache;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.kolin.fintechhomework9.data.model.NewsPojo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by kolin on 27.11.2017.
 */

public class FileCache implements ICache {

    public static final String TAG = FileCache.class.getSimpleName();

    public static final String NEWS_FILE_NAME = "news_cache_json";

    private File cacheFile;
    private Gson gson;
    private Type listType = new TypeToken<ArrayList<NewsPojo>>(){}.getType();

    public FileCache(Context context) {
        cacheFile = new File(context.getCacheDir(), NEWS_FILE_NAME);
        gson = new Gson();
    }


    @Override
    public void putToCache(List<NewsPojo> newsPojos) {
        deleteCacheFile();
        writeToFile(gson.toJson(newsPojos));
    }

    @Override
    public Observable<List<NewsPojo>> getFromCache() {
        return Observable.fromCallable(() -> gson.fromJson(readFromFile(), listType));
    }


    private void writeToFile(String content) {
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(cacheFile))) {
                bos.write(content.getBytes());
                bos.flush();
                bos.close();
            } catch (IOException e) {
                Log.e(TAG, "Failed to write cache", e);
                e.printStackTrace();
            }
    }

    private String readFromFile() {
            int length = (int) cacheFile.length();
            byte[] bytes = new byte[length];

            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(cacheFile))) {
                bis.read(bytes, 0, bytes.length);
                bis.close();
            } catch (IOException e){
                Log.e(TAG, "Failed to read from cache", e);
                e.printStackTrace();
            }

            return new String(bytes);
    }

    public boolean deleteCacheFile() {
        return cacheFile.exists() && cacheFile.delete();
    }
}
