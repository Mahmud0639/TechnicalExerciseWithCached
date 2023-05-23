package com.manuni.androidtechnicalexercisewithcached;

import android.graphics.Bitmap;
import android.util.LruCache;

public class CacheData {

    private LruCache<String, Bitmap> cache;
    private static CacheData instance;


    public CacheData() {
        int memoryForUse = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheToUse = memoryForUse / 8;

        cache = new LruCache<String, Bitmap>(cacheToUse) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public static synchronized CacheData getInstance() {
        if (instance == null) {
            instance = new CacheData();
        }
        return instance;
    }

    public void addBitmapToCache(String key, Bitmap bitmap) {
        if (getBitmapFromCache(key) == null)
            cache.put(key, bitmap);
    }

    public Bitmap getBitmapFromCache(String key) {
        return cache.get(key);
    }

    public void clearCachedData() {
        cache.evictAll();
    }
}
