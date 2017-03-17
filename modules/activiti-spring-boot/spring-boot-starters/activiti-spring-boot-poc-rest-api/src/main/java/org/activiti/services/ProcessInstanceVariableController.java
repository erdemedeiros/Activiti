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

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Elias Ricken de Medeiros
 */
@RestController
@RequestMapping(value = "/api/runtime/process-instances/{processInstanceId}/variables", produces = "application/hal+json")
public class ProcessInstanceVariableController {

    private final RuntimeService runtimeService;

    @Autowired
    public ProcessInstanceVariableController(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Resource<Map<String, Object>> getVariables(@PathVariable String processInstanceId) {
        Link processInstanceRel = linkTo(methodOn(ProcessInstanceController.class).getProcessInstance(processInstanceId)).withRel("processInstance");
        return new Resource<>(runtimeService.getVariables(processInstanceId), processInstanceRel);
    }

}
