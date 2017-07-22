package org.pentagonal.android.recipicious.http;

import okhttp3.*;
import okhttp3.Request.Builder;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


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
    "UnusedReturnValue",
    "unused"
})

public class HttpApi
{
    protected Builder builder;

    protected RequestBody body = MultipartBody.create(null, new byte[0]);

    final public String DEFAULT_METHOD = "GET";

    final public String DEFAULT_USER_AGENT = "Pentagonal/5 (Android) Recipicious/1.0.0";

    /*
    final public String CHROME_USER_AGENT
        = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 "
        +"(KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36";
    */

    public HttpApi(String url)
    {
        builder = new Request
            .Builder()
            .url(url)
            .header("User-Agent", DEFAULT_USER_AGENT);
    }

    public HttpApi(Builder requestUri)
    {
        builder = requestUri;
    }

    public HttpApi(Builder requestUri, RequestBody requestBody)
    {
        builder = requestUri;
        body    = requestBody;
    }

    public HttpApi(String url, RequestBody requestBody)
    {
        builder = new Request
            .Builder()
            .url(url)
            .header("User-Agent", DEFAULT_USER_AGENT);
        body = requestBody;
    }

    public static HttpApi create(String url)
    {
        return new HttpApi(url);
    }

    public static HttpApi create(Builder url)
    {
        return new HttpApi(url);
    }

    public static HttpApi create(Builder url, RequestBody requestBody)
    {
        return new HttpApi(url, requestBody);
    }
    public static HttpApi create(String url, RequestBody requestBody)
    {
        return new HttpApi(url, requestBody);
    }

    public HttpApi withUrl(String url)
    {
        return create(url, body);
    }

    public HttpApi withBuilderUrl(Builder url)
    {
        return create(url, body);
    }

    public HttpApi removeHeader(String name)
    {
        builder.removeHeader(name);
        return this;
    }

    public HttpApi setHeader(String name, String value)
    {
        builder.header(name, value);
        return this;
    }

    public HttpApi addHeader(String name, String value)
    {
        builder.addHeader(name, value);
        return this;
    }

    public HttpApi setAccept(String accepted)
    {
        return setHeader("Accept", accepted);
    }

    public HttpApi setUserAgent(String userAgent)
    {
        return setHeader("User-Agent", userAgent);
    }

    public HttpApi setContentType(String contentType)
    {
        return setHeader("Content-Type", contentType);
    }

    public HttpApi setHeaders(Headers headers)
    {
        builder.headers(headers);
        return this;
    }

    public HttpApi setBody(RequestBody requestBody)
    {
        body = requestBody;
        return this;
    }

    public Request getRequest(String method)
    {
        return this.getRequest(method, body);
    }

    public Request getRequest(String method, RequestBody body)
    {
        method = method.toUpperCase().trim();
        method = method.equals("") ? DEFAULT_METHOD : method;
        return builder
            .method(
                method,
                method.equals("GET") ? null : body
            ).build();
    }

    public Response send()
        throws IOException
    {
        return send("GET");
    }

    public Response send(String method)
        throws IOException
    {
        return send(method, body);
    }

    public Response send(String method, PostParam postParam)
        throws IOException
    {
        return send(method, postParam.build());
    }

    public Response send(String method, RequestBody requestBody)
        throws IOException
    {
        return send(getRequest(method, requestBody));
    }

    public Response send(Request request)
        throws IOException
    {
        System.out.println(request.method());
        OkHttpClient client = new OkHttpClient();
        return client
            .newCall(request)
            .execute();
    }

    public static class PostParam
    {
        private List paramName  = new LinkedList();
        private List paramValue = new LinkedList();
        private List paramType  = new LinkedList();
        private MultipartBody.Builder builder;
        public PostParam()
        {
            this.builder = new MultipartBody
                .Builder()
                .setType(MultipartBody.FORM);
        }

        @SuppressWarnings("unchecked")
        public PostParam setParam(String name, String value)
        {
            int index = paramName.indexOf(name);
            if (index > -1) {
                paramValue.set(index, value);
                paramType.set(index, value);
            } else {
                paramType.add(value);
                paramName.add(name);
                paramValue.add(value);
            }
            return this;
        }

        public PostParam setParam(String name, int value)
        {
            String paramValue = Integer.toString(value);
            return setParam(name, paramValue);
        }

        @SuppressWarnings("unchecked")
        public PostParam setParam(String name, String value, RequestBody body)
        {
            int index = paramName.indexOf(name);
            if (index > -1) {
                paramValue.set(index, value);
                paramType.set(index, value);
                paramType.set(index, body);
            } else {
                paramType.add(body);
                paramName.add(name);
                paramValue.add(value);
            }

            return this;
        }

        public PostParam removeParam(String name)
        {
            int index = paramName.indexOf(name);
            if (index > -1) {
                paramName.remove(index);
                paramType.remove(index);
                paramType.remove(index);
            }

            return this;
        }

        public RequestBody build()
        {
            /*String param = "";*/
            for (int i = 0; paramName.size() > i; i++) {
                if (paramType.get(i) instanceof RequestBody) {
                    builder.addFormDataPart(
                        (String) paramName.get(i),
                        (String) paramValue.get(i),
                        (RequestBody) paramType.get(i)
                    );
                    /*param += paramName.get(i) + "=" + URLEncoder.encode((String)paramValue.get(i)) + "&";*/
                    continue;
                }
                /* param += paramName.get(i) + "=" + URLEncoder.encode((String)paramValue.get(i)) + "&";*/
                builder.addFormDataPart((String) paramName.get(i), (String) paramValue.get(i));
            }

            /*
            if (param.endsWith("&")) {
                param = param.substring(0, param.length() -1);
            }
            RequestBody body =  MultipartBody.create(
                MediaType.parse("application/x-www-form-urlencoded"),
                param
            );
            */
            return builder.build();


        }
    }

    /* --------------------------------------
     * WORKER
     * ------------------------------------- */

    public static class Direct
    {

        public static Response Response(String url)
            throws IOException
        {
            return create(url).send();
        }

        public static Response Response(Request requestUrl)
            throws IOException
        {
            return create(requestUrl.url().toString()).send(requestUrl);
        }

        public static Response Response(Builder url)
            throws IOException
        {
            return create(url).send();
        }

        public static Response Response(String url, RequestBody requestBody)
            throws IOException
        {
            return create(url, requestBody).send();
        }

        public static Response Response(Builder url, RequestBody requestBody)
            throws IOException
        {
            return create(url, requestBody).send();
        }

        public static Response Response(String url, String method)
            throws IOException
        {
            return create(url).send(method);
        }

        public static Response Response(Builder url, String method)
            throws IOException
        {
            return create(url).send(method);
        }

        public static Response Response(String url, RequestBody requestBody, String method)
            throws IOException
        {
            return create(url, requestBody).send(method);
        }

        public static Response Response(Builder url, RequestBody requestBody, String method)
            throws IOException
        {
            return create(url, requestBody).send(method);
        }


        // -------------------------------------------------------------------

        public static ResponseBody ResponseBody(String url)
            throws IOException
        {
            return Response(url).body();
        }

        public static ResponseBody ResponseBody(Builder url)
            throws IOException
        {
            return Response(url).body();
        }

        public static ResponseBody ResponseBody(Request requestUrl)
            throws IOException
        {
            return Response(requestUrl).body();
        }

        public static ResponseBody ResponseBody(String url, RequestBody requestBody)
            throws IOException
        {
            return Response(url, requestBody).body();
        }

        public static ResponseBody ResponseBody(Builder url, RequestBody requestBody)
            throws IOException
        {
            return Response(url, requestBody).body();
        }

        public static ResponseBody ResponseBody(String url, String method)
            throws IOException
        {
            return Response(url, method).body();
        }

        public static ResponseBody ResponseBody(Builder url, String method)
            throws IOException
        {
            return Response(url, method).body();
        }

        public static ResponseBody ResponseBody(String url, RequestBody requestBody, String method)
            throws IOException
        {
            return Response(url, requestBody, method).body();
        }

        public static ResponseBody ResponseBody(Builder url, RequestBody requestBody, String method)
            throws IOException
        {
            return Response(url, requestBody, method).body();
        }

        // -------------------------------------------------------------------

        // -------------------------------------------------------------------

        public static String ResponseBodyString(String url)
            throws IOException
        {
            return ResponseBody(url).string();
        }

        public static String ResponseBodyString(Builder url)
            throws IOException
        {
            return ResponseBody(url).string();
        }

        public static String ResponseBodyString(Request requestUrl)
            throws IOException
        {
            return ResponseBody(requestUrl).string();
        }

        public static String ResponseBodyString(String url, RequestBody requestBody)
            throws IOException
        {
            return ResponseBody(url, requestBody).string();
        }

        public static String ResponseBodyString(Builder url, RequestBody requestBody)
            throws IOException
        {
            return ResponseBody(url, requestBody).string();
        }

        public static String ResponseBodyString(String url, String method)
            throws IOException
        {
            return ResponseBody(url, method).string();
        }

        public static String ResponseBodyString(Builder url, String method)
            throws IOException
        {
            return ResponseBody(url, method).string();
        }

        public static String ResponseBodyString(String url, RequestBody requestBody, String method)
            throws IOException
        {
            return ResponseBody(url, requestBody, method).string();
        }

        public static String ResponseBodyString(Builder url, RequestBody requestBody, String method)
            throws IOException
        {
            return ResponseBody(url, requestBody, method).string();
        }
    }
}
