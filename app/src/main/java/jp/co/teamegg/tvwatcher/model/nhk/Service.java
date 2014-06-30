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

public class Service implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 7816742792041534013L;

    public String id;
    public String name;
    public Logo logo_s;
    public Logo logo_m;
    public Logo logo_l;
}
