/*
 * Copyright 2014 Team EGG. Co.ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.co.teamegg.tvwatcher.io;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Gsonのリクエストを扱うクラスです。
 * 
 * @param <T>
 * @see https://gist.github.com/ficusk/5474673
 */
public class GsonRequest<T> extends Request<T> {
    /** {@link Gson} */
    final Gson mGson;
    /** {@link Gson}で扱う{@link Class} */
    final Class<T> mClazz;
    /** リクエストヘッダの{@link Map} */
    final Map<String, String> mHeaders;
    /** {@link Listener} */
    final Listener<T> mListener;

    /**
     * GETリクエストでリクエストを生成します。
     * 
     * @param url
     *            リクエストするURL
     * @param clazz
     *            Gsonが扱うクラス
     * @param headers
     *            リクエストヘッダの{@link Map}
     * @param listener
     *            {@link Listener}
     * @param errorListener
     *            {@link ErrorListener}
     */
    public GsonRequest(final String url, final Class<T> clazz, final Map<String, String> headers, final Listener<T> listener, final ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        mGson = new Gson();
        mClazz = clazz;
        mHeaders = headers;
        mListener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void deliverResponse(final T response) {
        mListener.onResponse(response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Response<T> parseNetworkResponse(final NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mGson.fromJson(json, mClazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}