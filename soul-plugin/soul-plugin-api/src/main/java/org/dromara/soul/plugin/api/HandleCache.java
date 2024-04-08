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

package org.dromara.soul.plugin.api;

/**
 * The selector or rule handle cache.
 */
public interface HandleCache<K, V> {

    /**
     * Obtain selector or rule handle.
     *
     * @param key key
     * @return v handle
     */
    V obtainHandle(K key);

    /**
     * Cached selector or rule handle.
     *
     * @param key   key
     * @param value value
     */
    void cachedHandle(K key, V value);

    /**
     * Remove selector or rule handle.
     *
     * @param key key
     */
    void removeHandle(K key);
}
