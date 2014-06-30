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
package jp.co.teamegg.tvwatcher.model.nhk;

import java.io.Serializable;

public class TVProgram implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = -6168203842996346357L;

    public String id;
    public String event_id;
    public String start_time;
    public String end_time;
    public Area area;
    public Service service;
    public String title;
    public String subtitle;
    // ココは自力でやらなきゃならないので省略
    // http://stackoverflow.com/questions/20348438/create-jsonarray-without-key-value
    // public Genres[] genres;
}
