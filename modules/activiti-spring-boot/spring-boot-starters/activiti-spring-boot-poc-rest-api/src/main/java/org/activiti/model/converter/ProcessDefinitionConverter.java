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

package org.activiti.model.converter;

import org.activiti.client.model.ProcessDefinition;
import org.springframework.stereotype.Component;

/**
 * @author Elias Ricken de Medeiros
 */
@Component
public class ProcessDefinitionConverter implements ModelConverter<org.activiti.engine.repository.ProcessDefinition, ProcessDefinition> {

    @Override
    public ProcessDefinition from(org.activiti.engine.repository.ProcessDefinition source) {
        ProcessDefinition processDefinition = new ProcessDefinition();
        processDefinition.setId(source.getId());
        processDefinition.setName(source.getName());
        processDefinition.setCategory(source.getCategory());
        processDefinition.setVersion(source.getVersion());
        processDefinition.setDeploymentId(source.getDeploymentId());
        return processDefinition;
    }

}
