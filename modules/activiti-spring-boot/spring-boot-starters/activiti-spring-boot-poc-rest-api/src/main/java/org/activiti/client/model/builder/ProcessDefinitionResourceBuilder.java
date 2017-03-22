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

import org.activiti.client.model.ProcessDefinition;
import org.activiti.services.ProcessDefinitionController;
import org.activiti.services.ProcessInstanceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Elias Ricken de Medeiros
 */
@Component
public class ProcessDefinitionResourceBuilder implements ResourcesBuilder<ProcessDefinition, ProcessDefinition> {

    private final ListResourceBuilder listResourceBuilder;

    @Autowired
    public ProcessDefinitionResourceBuilder(ListResourceBuilder listResourceBuilder) {
        this.listResourceBuilder = listResourceBuilder;
    }

    @Override
    public Resources<Resource<ProcessDefinition>> build(List<ProcessDefinition> processDefinitions) {
        Link selfRel = linkTo(methodOn(ProcessDefinitionController.class).getProcesses()).withSelfRel();
        return new Resources<>(listResourceBuilder.buildResourceList(processDefinitions, this), selfRel);
    }

    @Override
    public Resource<ProcessDefinition> build(ProcessDefinition processDefinition) {
        Link selfRel = linkTo(methodOn(ProcessDefinitionController.class).getProcesses()).withSelfRel();
        Link startProcessLink = linkTo(methodOn(ProcessInstanceController.class).startProcess(null)).withRel("startProcess");

        return new Resource<>(processDefinition, selfRel, startProcessLink);
    }

}
