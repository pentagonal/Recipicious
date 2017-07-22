package org.pentagonal.android.recipicious.http;

import android.support.annotation.Nullable;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.pentagonal.android.recipicious.util.JsonReader;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created on Linux User : (snsv) on 7/16/17.
 * <p>
 * - Package : org.pentagonal.android.recipicious.http
 * - Project : Recipicious
 *
 * @author pentagonal
 * @since July, 16 2017
 */
@SuppressWarnings({
    "WeakerAccess",
    "unused"
})

public class GSONHttpApi extends HttpApi
{
    public GSONHttpApi(String url)
    {
        super(url);
    }

    public GSONHttpApi(Request.Builder requestUri)
    {
        super(requestUri);
    }

    public GSONHttpApi(Request.Builder requestUri, RequestBody requestBody)
    {
        super(requestUri, requestBody);
    }

    public GSONHttpApi(String url, RequestBody requestBody)
    {
        super(url, requestBody);
    }


    @Override
    public GSONHttpApi withUrl(String url)
    {
        return create(url, body);
    }

    @Override
    public GSONHttpApi withBuilderUrl(Request.Builder url)
    {
        return create(url, body);
    }

    @Override
    public GSONHttpApi removeHeader(String name)
    {
        super.removeHeader(name);
        return this;
    }

    @Override
    public GSONHttpApi setHeader(String name, String value)
    {
        super.setHeader(name, value);
        return this;
    }

    @Override
    public GSONHttpApi addHeader(String name, String value)
    {
        builder.addHeader(name, value);
        return this;
    }



    @Override
    public GSONHttpApi setUserAgent(String userAgent)
    {
        return setHeader("User-Agent", userAgent);
    }

    @Override
    public GSONHttpApi setContentType(String contentType)
    {
        return setHeader("Content-Type", contentType);
    }

    @Override
    public GSONHttpApi setHeaders(Headers headers)
    {
        super.setHeaders(headers);
        return this;
    }

    @Override
    public GSONHttpApi setBody(RequestBody requestBody)
    {
        super.setBody(requestBody);

        return this;
    }

    @Override
    public GSONHttpApi setAccept(String name)
    {
        super.setAccept(name);
        return this;
    }

    public static GSONHttpApi create(String url)
    {
        return new GSONHttpApi(url);
    }

    public static GSONHttpApi create(Request.Builder url)
    {
        return new GSONHttpApi(url);
    }

    public static GSONHttpApi create(Request.Builder url, RequestBody requestBody)
    {
        return new GSONHttpApi(url, requestBody);
    }

    public static GSONHttpApi create(String url, RequestBody requestBody)
    {
        return new GSONHttpApi(url, requestBody);
    }

    public <T> T send(Class<T> cls, Object t)
        throws IOException
    {
        return send(cls, t, "GET");
    }

    public <T> T send(Class<T> cls, Object t, String method)
        throws IOException
    {
        return send(cls, t, method, body);
    }

    public <T> T send(Class<T> cls, Object t, String method, @Nullable PostParam postParam)
        throws IOException
    {
        if (postParam == null) {
            return send(cls, t, method);
        }

        return send(cls, t, method, postParam.build());
    }

    public <T> T send(Class<T> cls, Object t, String method, RequestBody requestBody)
        throws IOException
    {
        return send(cls, t, getRequest(method, requestBody));

    }

    public <T> T send(Class<T> cls, Object t, Request request)
        throws IOException
    {
        JsonReader data = new JsonReader(new StringReader(Direct.ResponseBodyString(request)));

        // @todo tracing data output debug
        System.out.println(request.headers());
        System.out.println(data);

        return new GsonBuilder()
            .registerTypeAdapter(cls, t)
            .create()
            .fromJson(
                data,
                cls
            );
    }

}
