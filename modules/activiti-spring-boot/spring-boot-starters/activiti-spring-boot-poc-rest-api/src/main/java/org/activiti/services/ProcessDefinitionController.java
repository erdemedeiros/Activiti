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

package org.activiti.services;

import org.activiti.client.model.ProcessDefinition;
import org.activiti.engine.RepositoryService;
import org.activiti.model.converter.ProcessDefinitionConverter;
import org.activiti.model.converter.ResourcesBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Elias Ricken de Medeiros
 */
@RestController
@RequestMapping(value = "/api/repository/process-definitions", produces = "application/hal+json")
public class ProcessDefinitionController {

    private final RepositoryService repositoryService;

    private final ProcessDefinitionConverter processDefinitionConverter;

    private final ResourcesBuilder resourcesBuilder;

    @Autowired
    public ProcessDefinitionController(RepositoryService repositoryService, ProcessDefinitionConverter processDefinitionConverter, ResourcesBuilder resourcesBuilder) {
        this.repositoryService = repositoryService;
        this.processDefinitionConverter = processDefinitionConverter;
        this.resourcesBuilder = resourcesBuilder;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Resources<Resource<ProcessDefinition>> getProcesses() {
        Link selfRel = linkTo(methodOn(getClass()).getProcesses()).withSelfRel();
        return resourcesBuilder.build(repositoryService.createProcessDefinitionQuery().list(), processDefinitionConverter, selfRel);
    }

}
