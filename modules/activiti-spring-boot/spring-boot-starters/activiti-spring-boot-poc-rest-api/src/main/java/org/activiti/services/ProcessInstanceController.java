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

import org.activiti.client.model.ExtendedProcessInstance;
import org.activiti.client.model.ProcessInstance;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstanceBuilder;
import org.activiti.model.converter.ProcessInstanceConverter;
import org.activiti.model.converter.ResourcesBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Elias Ricken de Medeiros
 */
@RestController
@RequestMapping(value = "/api/runtime/process-instances", produces = "application/hal+json")
public class ProcessInstanceController {

    private final ResourcesBuilder resourcesBuilder;

    private final ProcessInstanceConverter processInstanceConverter;

    private final RuntimeService runtimeService;

    @Autowired
    public ProcessInstanceController(ResourcesBuilder resourcesBuilder, ProcessInstanceConverter processInstanceConverter, RuntimeService runtimeService) {
        this.resourcesBuilder = resourcesBuilder;
        this.processInstanceConverter = processInstanceConverter;
        this.runtimeService = runtimeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Resources<Resource<ProcessInstance>> getProcessInstances(){
        List<org.activiti.engine.runtime.ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().list();
        Link selfRel = linkTo(methodOn(getClass()).getProcessInstances()).withSelfRel();
        return resourcesBuilder.build(processInstances, processInstanceConverter, selfRel);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Resource<ProcessInstance> startProcess(@RequestBody ExtendedProcessInstance processInstance) {
        ProcessInstanceBuilder builder = runtimeService.createProcessInstanceBuilder();
        builder.processDefinitionKey(processInstance.getProcessDefinitionKey());
        builder.variables(processInstance.getVariables());
        org.activiti.engine.runtime.ProcessInstance instance = builder.start();

        Link processInstancesRel = linkTo(methodOn(getClass()).getProcessInstances()).withRel("processInstances");

        return new Resource<>(processInstanceConverter.from(instance), processInstanceSelfLink(instance.getProcessInstanceId()), linkToVariables(instance), processInstancesRel);
    }

    private Link linkToVariables(org.activiti.engine.runtime.ProcessInstance instance) {
        return linkTo(methodOn(ProcessInstanceVariableController.class).getVariables(instance.getProcessInstanceId())).withRel("variables");
    }

    @RequestMapping(value = "/{processInstanceId}", method = RequestMethod.GET)
    public Resource<ProcessInstance> getProcessInstance(@PathVariable String processInstanceId){
        org.activiti.engine.runtime.ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        Link selfRel = processInstanceSelfLink(processInstanceId);
        return new Resource<>(processInstanceConverter.from(processInstance), selfRel, linkToVariables(processInstance));
    }

    private Link processInstanceSelfLink(String processInstanceId) {
        return linkTo(methodOn(getClass()).getProcessInstance(processInstanceId)).withSelfRel();
    }

}
