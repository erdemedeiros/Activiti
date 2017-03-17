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

import org.activiti.client.model.Task;
import org.activiti.engine.TaskService;
import org.activiti.model.converter.ResourcesBuilder;
import org.activiti.model.converter.TaskConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Elias Ricken de Medeiros
 */
@RestController
@RequestMapping(value = "/api/runtime/tasks", produces = "application/hal+json")
public class TaskController {

    private final TaskService taskService;

    private final TaskConverter taskConverter;
    private ResourcesBuilder resourcesBuilder;

    @Autowired
    public TaskController(TaskService taskService, TaskConverter taskConverter, ResourcesBuilder resourcesBuilder) {
        this.taskService = taskService;
        this.taskConverter = taskConverter;
        this.resourcesBuilder = resourcesBuilder;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Resources<Resource<Task>> getTasks() {
        List<org.activiti.engine.task.Task> tasks = taskService.createTaskQuery().list();
        Link selfRel = linkTo(methodOn(getClass()).getTasks()).withSelfRel();
        return resourcesBuilder.build(tasks, taskConverter, selfRel);
    }

    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    public Resource<Task> getTask(@PathVariable String taskId) {
        org.activiti.engine.task.Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Link selfRel = buildTaskSelfLink(taskId);
        Link processInstanceRel = linkTo(methodOn(ProcessInstanceController.class).getProcessInstance(task.getProcessInstanceId())).withRel("processInstance");
        Link claimRel = linkTo(methodOn(getClass()).claimTask(taskId, null)).withRel("claim");
        return new Resource<>(taskConverter.from(task), selfRel, claimRel, processInstanceRel);
    }

    private Link buildTaskSelfLink(@PathVariable String taskId) {
        return linkTo(methodOn(getClass()).getTask(taskId)).withSelfRel();
    }

    @RequestMapping(value = "/{taskId}/claim", method = RequestMethod.POST)
    public Resource<Task> claimTask(@PathVariable String taskId, @RequestParam("assignee") String assignee) {
        taskService.claim(taskId, assignee);
        Link taskSelfLink = buildTaskSelfLink(taskId);
        Task task = taskConverter.from(taskService.createTaskQuery().taskId(taskId).singleResult());
        return  new Resource<>(task, taskSelfLink);
    }

}
