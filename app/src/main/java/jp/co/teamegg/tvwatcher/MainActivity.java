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
package jp.co.teamegg.tvwatcher;

import java.util.Calendar;

import jp.co.teamegg.tvwatcher.io.GsonRequest;
import jp.co.teamegg.tvwatcher.io.VolleyRequestHolder;
import jp.co.teamegg.tvwatcher.model.nhk.OnAirResult;
import android.os.Bundle;
import android.preview.support.v4.app.NotificationManagerCompat;
import android.preview.support.wearable.notifications.WearableNotifications;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * NHKの番組表をAndroidWearに送信するアプリです。<br/>
 * NHKの番組表APIはこちらから登録してください。<br/>
 * <a href="http://api-portal.nhk.or.jp/">http://api-portal.nhk.or.jp/</a>
 * 
 * @author yoshihide-sogawa
 * 
 */
public class MainActivity extends FragmentActivity implements Response.Listener<OnAirResult>, Response.ErrorListener {

    /** NHK番組表のグループ */
    private static final String KEY_PROGRAM_GROUP_NHK = "program_group_nhk";

    /** サンプルURL(東京のEテレ) */
    private static final String SAMPLE_URL = "http://api.nhk.or.jp/v1/pg/now/130/e1.json?key=";
    /** APIキー(自分のキーを使用してください) */
    private static final String API_KEY = "pdVasbCxkmjHar88ITIKL0LcgZRey2mS";

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // APIキーの変更要求
        if (API_KEY.startsWith("YD5XcCG3W1dUJtNB04Ng8LMv74IPuGE")) {
            Toast.makeText(this, "Use your API Key", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // 更新ボタン
        findViewById(R.id.refresh_program).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearch();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onPause() {
        VolleyRequestHolder.newRequestQueue(this).cancelAll(this);
        super.onPause();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResponse(final OnAirResult response) {
        // キャンセル
        NotificationManagerCompat.from(this).cancel(0);

        // 現在時刻の取得
        final Calendar calendar = Calendar.getInstance();
        final String currentTime = (getString(R.string.time_format, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
        // タイトル
        final String[] titles = new String[] { "", getString(R.string.tv_program_title_current, currentTime), getString(R.string.tv_program_title_next) };
        // 番組内容
        final String[] tvPrograms = new String[] { "", response.nowonair_list.e1.present.title, response.nowonair_list.e1.following.title };
        // グループ化(GROUP_ORDER_SUMMARYがないと通知されない)
        final int[] groupOrders = new int[] { WearableNotifications.GROUP_ORDER_SUMMARY, 1, 2 };
        final int length = groupOrders.length;

        NotificationCompat.Builder builder;
        WearableNotifications.Builder wearableBuilder;
        // スタックする数だけ通知を行う
        for (int i = 0; i < length; i++) {
            // 番組表の通知作成
            builder = new NotificationCompat.Builder(this);
            builder.setContentTitle(titles[i]);
            builder.setContentText(tvPrograms[i]);

            // ウェアラブル用通知の作成
            wearableBuilder = new WearableNotifications.Builder(builder);
            wearableBuilder.setMinPriority();
            wearableBuilder.setGroup(KEY_PROGRAM_GROUP_NHK, groupOrders[i]);
            wearableBuilder.setHintHideIcon(true);
            NotificationManagerCompat.from(this).notify(i, wearableBuilder.build());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onErrorResponse(final VolleyError error) {
        // キャンセル
        NotificationManagerCompat.from(this).cancel(0);

        // 番組表の通知を行う
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(getString(R.string.tv_program_response_error));
        // ウェアラブル用
        final WearableNotifications.Builder wearableBuilder = new WearableNotifications.Builder(builder);
        wearableBuilder.setMinPriority();
        NotificationManagerCompat.from(this).notify(0, wearableBuilder.build());
    }

    /**
     * 検索を開始します。
     */
    private void startSearch() {
        // 通信開始
        final GsonRequest<OnAirResult> request = new GsonRequest<OnAirResult>(SAMPLE_URL + API_KEY, OnAirResult.class, null, MainActivity.this, MainActivity.this);
        request.setTag(this);
        VolleyRequestHolder.newRequestQueue(MainActivity.this).add(request);
    }
}
