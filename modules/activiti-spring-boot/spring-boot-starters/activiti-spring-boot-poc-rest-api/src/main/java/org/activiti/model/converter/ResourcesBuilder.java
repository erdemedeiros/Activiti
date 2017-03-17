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

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Elias Ricken de Medeiros
 */
@Component
public class ResourcesBuilder {

    public <S, T> Resources<Resource<T>> build(Collection<S> content, ModelConverter<S, T> converter, Link ... links) {
        ArrayList<Resource<T>> resources = new ArrayList<>();
        for (S element : content) {
            resources.add(new Resource<>(converter.from(element)));
        }
        return new Resources<>(resources, links);
    }

}
