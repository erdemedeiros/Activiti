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

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.hateoas.Resource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Elias Ricken de Medeiros
 */
public class ListResourceBuilderTest {

    @InjectMocks
    private ListResourceBuilder listBuilder;

    @Mock
    private ResourceBuilder<String, String> resourceBuilder;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void buildResourceList_should_return_a_of_resources_wrapping_initial_content() throws Exception {
        //given
        String firstElement = "first";
        String secondElement = "second";
        List<String> rawElements = Arrays.asList(firstElement, secondElement);

        Resource<String> firstResource = new Resource<>(firstElement);
        Resource<String> secondResource = new Resource<>(secondElement);

        when(resourceBuilder.build(firstElement)).thenReturn(firstResource);
        when(resourceBuilder.build(secondElement)).thenReturn(secondResource);

        //when
        List<Resource<String>> resources = listBuilder.buildResourceList(rawElements, resourceBuilder);

        //then
        assertThat(resources).hasSize(2);
        assertThat(resources).containsExactly(firstResource, secondResource);

    }

}