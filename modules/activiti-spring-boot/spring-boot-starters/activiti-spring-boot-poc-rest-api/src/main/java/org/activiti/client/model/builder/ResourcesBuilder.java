/*
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
 *
 */

package org.activiti.client.model.builder;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import java.util.List;

/**
 * @author Elias Ricken de Medeiros
 */
public interface ResourcesBuilder<RESOURCE_TYPE, PARAM_TYPE> extends ResourceBuilder<RESOURCE_TYPE, PARAM_TYPE> {

    Resources<Resource<RESOURCE_TYPE>> build(List<RESOURCE_TYPE> elements);

}
