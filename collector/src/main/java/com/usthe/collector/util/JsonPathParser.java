/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.usthe.collector.util;

import com.jayway.jsonpath.*;
import com.jayway.jsonpath.spi.cache.CacheProvider;
import com.jayway.jsonpath.spi.cache.LRUCache;

import java.util.*;

/**
 * json path parser
 * @author tomsun28
 * @date 2021/11/20 10:16
 */
public class JsonPathParser {

    private static final ParseContext PARSER;

    static {
        Configuration conf = Configuration.defaultConfiguration()
                .addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL)
                .addOptions(Option.ALWAYS_RETURN_LIST);
        CacheProvider.setCache(new LRUCache(128));
        PARSER = JsonPath.using(conf);
    }

    /**
     * 使用jsonPath来解析json内容
     * @param content json内容
     * @param jsonPath jsonPath脚本
     * @return 解析后的内容 [{'name': 'tom', 'speed': '433'},{'name': 'lili', 'speed': '543'}]
     */
    public static List<Map<String, Object>> parseContentWithJsonPath(String content, String jsonPath) {
        if (content == null || jsonPath == null || "".equals(content) || "".equals(jsonPath)) {
            return Collections.emptyList();
        }
        return PARSER.parse(content).read(jsonPath);
    }

    /**
     * 使用jsonPath来解析json内容
     * @param content json内容
     * @param jsonPath jsonPath脚本
     * @return 解析后的内容 [{'name': 'tom', 'speed': '433'},{'name': 'lili', 'speed': '543'}]
     */
    public static <T> T parseContentWithJsonPath(String content, String jsonPath, TypeRef<T> typeRef) {
        if (content == null || jsonPath == null || "".equals(content) || "".equals(jsonPath)) {
            return null;
        }
        return PARSER.parse(content).read(jsonPath, typeRef);
    }

}
