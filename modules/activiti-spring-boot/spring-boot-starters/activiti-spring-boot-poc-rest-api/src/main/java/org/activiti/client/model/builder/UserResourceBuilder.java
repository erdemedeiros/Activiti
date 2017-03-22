package org.activiti.client.model.builder;

import org.activiti.client.model.User;
import org.activiti.services.UserController;
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
public class UserResourceBuilder implements ResourcesBuilder<User, User> {

    private final ListResourceBuilder listResourceBuilder;

    @Autowired
    public UserResourceBuilder(ListResourceBuilder listResourceBuilder) {
        this.listResourceBuilder = listResourceBuilder;
    }

    @Override
    public Resource<User> build(User user) {
        Link selfRel = linkTo(methodOn(UserController.class).getUser(user.getUsername())).withSelfRel();
        return new Resource<>(user, selfRel);
    }

    @Override
    public Resources<Resource<User>> build(List<User> users) {
        Link selfRel = linkTo(methodOn(UserController.class).getUsers()).withSelfRel();
        return new Resources<>(listResourceBuilder.buildResourceList(users, this), selfRel);
    }
}
