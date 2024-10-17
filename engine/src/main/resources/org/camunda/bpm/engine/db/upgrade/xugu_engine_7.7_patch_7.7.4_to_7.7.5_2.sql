--
-- Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
-- under one or more contributor license agreements. See the NOTICE file
-- distributed with this work for additional information regarding copyright
-- ownership. Camunda licenses this file to you under the Apache License,
-- Version 2.0; you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- increase the field length https://app.camunda.com/jira/browse/CAM-8177 --
DROP INDEX ACT_UNIQ_AUTH_USER;
DROP INDEX ACT_UNIQ_AUTH_GROUP;

ALTER TABLE ACT_RU_AUTHORIZATION 
  MODIFY RESOURCE_ID_ nvarchar2(255);

CREATE UNIQUE INDEX ACT_UNIQ_AUTH_USER on ACT_RU_AUTHORIZATION(USER_ID_,TYPE_,RESOURCE_TYPE_,RESOURCE_ID_);

CREATE UNIQUE INDEX ACT_UNIQ_AUTH_GROUP on ACT_RU_AUTHORIZATION(GROUP_ID_,TYPE_,RESOURCE_TYPE_,RESOURCE_ID_);
