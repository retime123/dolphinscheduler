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

package org.apache.dolphinscheduler.plugin.datasource.hive.param;

import org.apache.dolphinscheduler.plugin.datasource.api.utils.CommonUtils;
import org.apache.dolphinscheduler.plugin.datasource.api.utils.PasswordUtils;
import org.apache.dolphinscheduler.spi.enums.DbType;
import org.apache.dolphinscheduler.spi.utils.Constants;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HiveDataSourceProcessorTest {

    private HiveDataSourceProcessor hiveDatasourceProcessor = new HiveDataSourceProcessor();

    @Test
    public void testCreateConnectionParams() {
        Map<String, String> props = new HashMap<>();
        props.put("serverTimezone", "utc");
        HiveDataSourceParamDTO hiveDataSourceParamDTO = new HiveDataSourceParamDTO();
        hiveDataSourceParamDTO.setHost("localhost1,localhost2");
        hiveDataSourceParamDTO.setPort(5142);
        hiveDataSourceParamDTO.setUserName("default");
        hiveDataSourceParamDTO.setDatabase("default");
        hiveDataSourceParamDTO.setOther(props);
        Mockito.mockStatic(PasswordUtils.class);
        Mockito.when(PasswordUtils.encodePassword(Mockito.anyString())).thenReturn("test");
        Mockito.mockStatic(CommonUtils.class);
        Mockito.when(CommonUtils.getKerberosStartupState()).thenReturn(false);
        HiveConnectionParam connectionParams = (HiveConnectionParam) hiveDatasourceProcessor
                .createConnectionParams(hiveDataSourceParamDTO);
        Assert.assertNotNull(connectionParams);
        Assert.assertEquals("jdbc:hive2://localhost1:5142,localhost2:5142", connectionParams.getAddress());
    }

    @Test
    public void testCreateConnectionParams2() {
        String connectionParam = "{\"user\":\"default\",\"address\":\"jdbc:hive2://localhost1:5142,localhost2:5142\""
                + ",\"jdbcUrl\":\"jdbc:hive2://localhost1:5142,localhost2:5142/default\"}";
        HiveConnectionParam connectionParams = (HiveConnectionParam) hiveDatasourceProcessor
                .createConnectionParams(connectionParam);
        Assert.assertNotNull(connectionParam);
        Assert.assertEquals("default", connectionParams.getUser());
    }

    @Test
    public void testGetDatasourceDriver() {
        Assert.assertEquals(Constants.ORG_APACHE_HIVE_JDBC_HIVE_DRIVER, hiveDatasourceProcessor.getDatasourceDriver());
    }

    @Test
    public void testGetJdbcUrl() {
        HiveConnectionParam connectionParam = new HiveConnectionParam();
        connectionParam.setJdbcUrl("jdbc:hive2://localhost1:5142,localhost2:5142/default");
        Assert.assertEquals("jdbc:hive2://localhost1:5142,localhost2:5142/default",
                hiveDatasourceProcessor.getJdbcUrl(connectionParam));
    }

    @Test
    public void testGetDbType() {
        Assert.assertEquals(DbType.HIVE, hiveDatasourceProcessor.getDbType());
    }

    @Test
    public void testGetValidationQuery() {
        Assert.assertEquals(Constants.HIVE_VALIDATION_QUERY, hiveDatasourceProcessor.getValidationQuery());
    }
}
