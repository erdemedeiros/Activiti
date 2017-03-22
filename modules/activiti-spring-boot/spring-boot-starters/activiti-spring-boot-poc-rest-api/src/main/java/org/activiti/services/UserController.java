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

import org.activiti.client.model.User;
import org.activiti.client.model.builder.UserResourceBuilder;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.IdentityService;
import org.activiti.model.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Elias Ricken de Medeiros
 */

@RestController
@RequestMapping(value = "/api/identity/users", produces = "application/hal+json")
public class UserController {

    private final IdentityService identityService;

    private final UserConverter userConverter;

    private final UserResourceBuilder userResourceBuilder;

    @Autowired
    public UserController(IdentityService identityService, UserConverter userConverter, UserResourceBuilder userResourceBuilder) {
        this.identityService = identityService;
        this.userConverter = userConverter;
        this.userResourceBuilder = userResourceBuilder;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Resource<User> createUser(@RequestBody User user) {
        if (user.getUsername() == null) {
            throw new ActivitiIllegalArgumentException("username cannot be null.");
        }

        org.activiti.engine.identity.User userDAO = identityService.newUser(user.getUsername());
        userDAO.setEmail(user.getEmail());
        userDAO.setFirstName(user.getFirstName());
        userDAO.setLastName(user.getLastName());
        userDAO.setPassword(user.getPassword());
        identityService.saveUser(userDAO);

        return userResourceBuilder.build(userConverter.from(userDAO));
    }

    @RequestMapping(method = RequestMethod.GET)
    public Resources<Resource<User>> getUsers() {
        return userResourceBuilder.build(userConverter.from(identityService.createUserQuery().list()));
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public Resource<User> getUser(@PathVariable String username) {
        org.activiti.engine.identity.User user = identityService.createUserQuery().userId(username).singleResult();

        if (user == null) {
            throw new ActivitiObjectNotFoundException("Could not find a user with id '" + username + "'.", org.activiti.engine.identity.User.class);
        }
        return userResourceBuilder.build(userConverter.from(user));
    }

}
