package org.pentagonal.android.recipicious.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.pentagonal.android.recipicious.http.GSONHttpApi;
import org.pentagonal.android.recipicious.http.HttpApi;

import java.io.IOException;

/**
 * Created on Linux User : (snsv) on 7/17/17.
 * <p>
 * - Package : org.pentagonal.android.recipicious.api
 * - Project : Recipicious
 *
 * @author pentagonal
 * @since July, 17 2017
 */

@SuppressWarnings({
    "WeakerAccess",
    "UnusedReturnValue",
    "unused"
})

final public class HttpApiFactory
{
    private TargetWithGSON targetWithGSON;

    final public static String BASE_URL = "https://inforiatif.com";

    public static class Path
    {
        protected String path = "";

        public Path(String path)
        {
            this.setPath(path);
        }

        public static Path create(String path)
        {
            return new Path(path);
        }

        public static String stringify(String path)
        {
            return create(path).toString();
        }

        private String normalizePath(String path)
        {
            if (! path.startsWith("/") && ! path.startsWith("?")) {
                path = "/" + path;
            }

            return path;
        }

        public Path withPath(String path)
        {
            return create(path);
        }

        public Path setPath(String path)
        {
            this.path = this.normalizePath(path);

            return this;
        }

        public String toString()
        {
            return BASE_URL + this.path;
        }
    }

    public static class TargetWithGSON
    {
        final Class<?> cls;
        final Object t;
        final Gson gson;

        String method = "GET";
        GSONHttpApi httpApi;
        HttpApi.PostParam postParam;

        public <T> TargetWithGSON(Class<T> cls, Object t)
        {
            this.cls = cls;
            this.t = t;
            this.gson = new GsonBuilder()
                .registerTypeAdapter(cls, t)
                .create();
            this.httpApi = new GSONHttpApi(Path.stringify("/"));
            this.setMethod("GET");
        }

        public TargetWithGSON setPath(String path)
        {
            this.httpApi = new GSONHttpApi(Path.stringify(path));
            return this;
        }

        public Gson getGson()
        {
            return this.gson;
        }

        public TargetWithGSON setMethod(String method)
        {
            this.method = method.toUpperCase().trim();
            if (this.method.equals("")) {
                this.method = "GET";
            }

            return this;
        }

        public GSONHttpApi getHttp()
        {
            return this.httpApi;
        }

        public TargetWithGSON setPostParam(
          HttpApi.PostParam postParam
        ) {
            this.postParam = postParam;
            return this;
        }

        public TargetWithGSON removePostParam()
        {
            this.postParam = null;
            return this;
        }

        public TargetWithGSON setParam(String paramName, String paramValue)
        {
            if (this.postParam == null) {
                this.postParam = new HttpApi.PostParam();
            }
            this.postParam.setParam(paramName, paramValue);

            return this;
        }
        public TargetWithGSON setParam(String paramName, int paramValue)
        {
            if (this.postParam == null) {
                this.postParam = new HttpApi.PostParam();
            }
            this.postParam.setParam(paramName, paramValue);

            return this;
        }

        public TargetWithGSON removeParam(String name)
        {
            if (this.postParam != null) {
                this.postParam.removeParam(name);
            }

            return this;
        }

        @SuppressWarnings("unchecked")
        public <T> T send()
            throws IOException
        {
            String method = this.method.toUpperCase().trim();
                method = method.equals("") ? "GET" : method;
            return getHttp()
                .send(
                    (Class<? extends T>) this.cls,
                    this.t,
                    method,
                    (method.equals("GET") ? null: this.postParam)
                );
        }
    }

    @SuppressWarnings("unused")
    public static Path buildForPath(String path)
    {
        return new Path(path);
    }

    public static <T> T send(
        Class<T> cls,
        Object t,
        String path
    ) throws IOException {
        return new TargetWithGSON(cls, t).setPath(path).send();
    }

    public static <T> HttpApiFactory post(
        Class<T> cls,
        Object t,
        String path
    ) {
        return custom(cls, t, path, "POST");
    }

    public static <T> HttpApiFactory get(
        Class<T> cls,
        Object t,
        String path
    ) {
        return custom(cls, t, path, "GET");
    }

    public static <T> HttpApiFactory put(
        Class<T> cls,
        Object t,
        String path
    ) {
        return custom(cls, t, path, "GET");
    }

    public static <T> HttpApiFactory custom(
        Class<T> cls,
        Object t,
        String path,
        String method
    ) {
        HttpApiFactory obj = new HttpApiFactory();
        obj.targetWithGSON = new TargetWithGSON(cls, t)
            .setPath(path)
            .setMethod(method);
        return obj;
    }

    public HttpApiFactory setParam(String paramName, String paramValue)
    {
        targetWithGSON.setParam(paramName, paramValue);

        return this;
    }

    public HttpApiFactory setParam(String paramName, int paramValue)
    {
        targetWithGSON.setParam(paramName, paramValue);

        return this;
    }

    public HttpApiFactory removeParam(String paramName)
    {
        targetWithGSON.removeParam(paramName);

        return this;
    }

    public HttpApiFactory setWithoutPostParam()
    {
        targetWithGSON.removePostParam();
        return this;
    }

    public <T> T send()
        throws IOException
    {
        return targetWithGSON.send();
    }
}
