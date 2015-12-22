package com.ajbecknerapps.photogallery;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AJ on 7/10/15.
 */
public class ThumbnailDownloader<Token> extends HandlerThread{
    private static final String TAG = "ThumbnailDownloader";
    private static int MESSAGE_DOWNLOAD = 0;

    Handler mHandler;
    Map<Token,String> requestMap = Collections.synchronizedMap(new HashMap<Token, String>());
    Handler mResponseHandler;
    Listener<Token> mListener;

    public interface Listener<Token> {
        void onThumbnailDownload(Token token, Bitmap thumbnail);

    }

    public void setListener(Listener<Token> listener){
        mListener = listener;
    }



    public ThumbnailDownloader(Handler responseHandler) {
        super(TAG);
        mResponseHandler = responseHandler;
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onLooperPrepared(){
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if (msg.what == MESSAGE_DOWNLOAD){
                    @SuppressWarnings("unchecked")
                    Token token = (Token)msg.obj;
                    Log.i(TAG,"Got request for a URl: " + requestMap.get(token));

                    handleRequest(token);
                }
            }


        };
    }

    public void queueThumbnail(Token token, String url) {
        Log.i(TAG, "Got an URL: " + url);
        requestMap.put(token,url);

        mHandler.obtainMessage(MESSAGE_DOWNLOAD,token).sendToTarget();
    }

    private void handleRequest(final Token token){
        try {
            final String url = requestMap.get(token);
            if (url == null){
                return;
            }
            byte[] bitmapBytes = new FlickrFetchr().getUrlBytes(url);
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes,0,bitmapBytes.length);
            Log.i(TAG,"bitmap created");

            mResponseHandler.post(new Runnable() {
                public void run() {
                    if (requestMap.get(token) != url)
                        return;

                    requestMap.remove(token);
                    mListener.onThumbnailDownload(token, bitmap);
                }
            });

        } catch (IOException ioe){
            Log.e(TAG,"Error downloading I=image", ioe);
        }
    }

    public void clearQueue() {
        mHandler.removeMessages(MESSAGE_DOWNLOAD);
        requestMap.clear();
    }
}
