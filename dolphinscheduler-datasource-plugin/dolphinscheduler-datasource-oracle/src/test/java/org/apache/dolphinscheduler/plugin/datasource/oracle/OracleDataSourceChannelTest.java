/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dolphinscheduler.plugin.datasource.oracle;

import org.apache.dolphinscheduler.plugin.datasource.oracle.param.OracleConnectionParam;
import org.apache.dolphinscheduler.spi.enums.DbType;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OracleDataSourceChannelTest {

    @Test
    public void testCreateDataSourceClient() {
        OracleDataSourceChannel sourceChannel = Mockito.mock(OracleDataSourceChannel.class);
        OracleDataSourceClient dataSourceClient = Mockito.mock(OracleDataSourceClient.class);
        Mockito.when(sourceChannel.createDataSourceClient(Mockito.any(), Mockito.any())).thenReturn(dataSourceClient);
        Assert.assertNotNull(sourceChannel.createDataSourceClient(new OracleConnectionParam(), DbType.ORACLE));
    }
}
