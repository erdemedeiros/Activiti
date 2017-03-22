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

import org.activiti.client.model.Task;
import org.activiti.services.ProcessInstanceController;
import org.activiti.services.TaskController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Elias Ricken de Medeiros
 */
@Component
public class TaskResourceBuilder implements ResourcesBuilder<Task, Task> {

    private final ListResourceBuilder listResourceBuilder;

    @Autowired
    public TaskResourceBuilder(ListResourceBuilder listResourceBuilder) {
        this.listResourceBuilder = listResourceBuilder;
    }

    @Override
    public Resource<Task> build(Task element) {
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(TaskController.class).getTask(element.getId())).withSelfRel());
        if (!element.isClaimed()) {
            links.add(linkTo(methodOn(TaskController.class).claimTask(element.getId(), null)).withRel("claim"));
        } else {
            links.add(linkTo(methodOn(TaskController.class).completeTask(element.getId(), null)).withRel("complete"));
        }
        links.add(linkTo(methodOn(ProcessInstanceController.class).getProcessInstance(element.getProcessInstanceId())).withRel("processInstance"));
        return new Resource<>(element, links);
    }


    @Override
    public Resources<Resource<Task>> build(List<Task> elements) {
        Link selfRel = linkTo(methodOn(TaskController.class).getTasks()).withSelfRel();
        return new Resources<>(listResourceBuilder.buildResourceList(elements, this), selfRel);
    }
}
