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

package org.dromara.soul.plugin.sofa;

import org.dromara.soul.common.constant.Constants;
import org.dromara.soul.common.dto.MetaData;
import org.dromara.soul.common.dto.RuleData;
import org.dromara.soul.common.dto.SelectorData;
import org.dromara.soul.common.enums.PluginEnum;
import org.dromara.soul.common.enums.RpcTypeEnum;
import org.dromara.soul.plugin.api.SoulPluginChain;
import org.dromara.soul.plugin.api.context.SoulContext;
import org.dromara.soul.plugin.sofa.proxy.SofaProxyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * SofaPluginTest.
 *
 * @author tydhot
 */
@RunWith(MockitoJUnitRunner.class)
public final class SofaPluginTest {
    private SofaPlugin sofaPlugin;

    private MetaData metaData;

    private ServerWebExchange exchange;

    @Mock
    private SoulPluginChain chain;

    @Before
    public void setUp() {
        exchange = MockServerWebExchange.from(MockServerHttpRequest.get("localhost").build());
        metaData = new MetaData();
        metaData.setId("1332017966661636096");
        metaData.setAppName("sofa");
        metaData.setPath("/sofa/findAll");
        metaData.setServiceName("org.dromara.soul.test.dubbo.api.service.DubboTestService");
        metaData.setMethodName("findAll");
        metaData.setRpcType(RpcTypeEnum.SOFA.getName());
        SofaProxyService sofaProxyService = mock(SofaProxyService.class);
        when(sofaProxyService.genericInvoker(null, metaData, exchange)).thenReturn(Mono.empty());
        sofaPlugin = new SofaPlugin(sofaProxyService);
    }

    @Test
    public void testSofaPlugin() {
        RuleData data = mock(RuleData.class);
        SoulContext context = mock(SoulContext.class);
        exchange.getAttributes().put(Constants.CONTEXT, context);
        exchange.getAttributes().put(Constants.META_DATA, metaData);
        when(chain.execute(exchange)).thenReturn(Mono.empty());
        SelectorData selectorData = mock(SelectorData.class);
        StepVerifier.create(sofaPlugin.doExecute(exchange, chain, selectorData, data)).expectSubscription().verifyComplete();
    }

    @Test
    public void testNamed() {
        final String result = sofaPlugin.named();
        assertEquals(PluginEnum.SOFA.getName(), result);
    }

    @Test
    public void testSkip() {
        final ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("localhost").build());
        SoulContext context = mock(SoulContext.class);
        when(context.getRpcType()).thenReturn(RpcTypeEnum.SOFA.getName());
        exchange.getAttributes().put(Constants.CONTEXT, context);
        exchange.getAttributes().put(Constants.META_DATA, metaData);
        final Boolean result = sofaPlugin.skip(exchange);
        assertFalse(result);
    }

    @Test
    public void testGetOrder() {
        final int result = sofaPlugin.getOrder();
        assertEquals(PluginEnum.SOFA.getCode(), result);
    }
}