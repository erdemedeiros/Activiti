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
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Elias Ricken de Medeiros
 */
@Component
public class ListResourceBuilder {

    public <RESOURCE_TYPE, PARAM_TYPE> List<Resource<RESOURCE_TYPE>> buildResourceList(List<PARAM_TYPE> elements, ResourceBuilder<RESOURCE_TYPE, PARAM_TYPE> resourceBuilder) {
        List<Resource<RESOURCE_TYPE>> resources = new ArrayList<>();
        for (PARAM_TYPE element : elements) {
            resources.add(resourceBuilder.build(element));
        }
        return resources;
    }

}
