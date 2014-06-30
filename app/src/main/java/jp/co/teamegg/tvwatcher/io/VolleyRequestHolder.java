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

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Volleyを保持しておくクラスです。
 * 
 * @author yoshihide-sogawa
 */
public final class VolleyRequestHolder {

    /** {@link RequestQueue} */
    private static RequestQueue sRequestQueue;

    /**
     * 非公開のコンストラクタ
     */
    private VolleyRequestHolder() {
        // no instances
    }

    /**
     * {@link RequestQueue}を取得します。
     * 
     * @param context
     *            {@link Context}
     * @return {@link RequestQueue}
     */
    public static RequestQueue newRequestQueue(final Context context) {
        if (sRequestQueue == null) {
            sRequestQueue = Volley.newRequestQueue(context);
        }
        return sRequestQueue;
    }
}
