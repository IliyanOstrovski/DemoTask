package com.example.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.PersonRegisterDTO;
import com.example.demo.dto.PersonUpdateDTO;
import com.example.demo.models.Person;
import com.example.demo.repositories.PersonPagingRepository;
import com.example.demo.repositories.PersonRepository;
import com.example.demo.services.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {PersonController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class PersonControllerTest {
    @Autowired
    private PersonController personController;

    @MockBean
    private PersonService personService;


    @Test
    @DisplayName("Test search(String, Integer, String); when valueOf one; then status isOk()")
    void testSearch_whenValueOfOne_thenStatusIsOk() throws Exception {
        // Arrange
        when(personService.findAllFirstNameAndAgeAndEgn(Mockito.<String>any(), Mockito.<Integer>any(),
                Mockito.<String>any())).thenReturn(null);
        MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders.get("/api/persons/search").param("egn", "foo");
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("minAge", String.valueOf(1));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(personController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test search(String, Integer, String); when param(String, String[]) 'firstName' is 'foo'; then status isOk()")
    void testSearch_whenParamFirstNameIsFoo_thenStatusIsOk() throws Exception {
        // Arrange
        when(personService.findAllFirstNameAndAgeAndEgn(Mockito.<String>any(), Mockito.<Integer>any(),
                Mockito.<String>any())).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/persons/search")
                .param("egn", "foo")
                .param("firstName", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(personController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test search(String, Integer, String); then status isOk()")
    void testSearch_thenStatusIsOk() throws Exception {
        // Arrange
        when(personService.findAllFirstNameAndAgeAndEgn(Mockito.<String>any(), Mockito.<Integer>any(),
                Mockito.<String>any())).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/persons/search")
                .param("egn", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(personController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test search(String, Integer, String); given PersonService; when get(String, Object[]) '/api/persons/search'; then status four hundred")
    void testSearch_givenPersonService_whenGetApiPersonsSearch_thenStatusFourHundred() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/persons/search");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(personController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    @DisplayName("Test registerPerson(PersonRegisterDTO)")
    void testRegisterPerson() throws Exception {
        // Arrange
        PersonRegisterDTO personRegisterDTO = new PersonRegisterDTO();
        personRegisterDTO.setAge(1);
        personRegisterDTO.setEgn("Egn");
        personRegisterDTO.setFirstName("Jane");
        personRegisterDTO.setLastName("Doe");
        personRegisterDTO.setMiddleName("Middle Name");
        String content = (new ObjectMapper()).writeValueAsString(personRegisterDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(personController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    @DisplayName("Test deletePerson(Long); given PersonService deleteById(Long) return 'false'; then status isNotFound()")
    void testDeletePerson_givenPersonServiceDeleteByIdReturnFalse_thenStatusIsNotFound() throws Exception {
        // Arrange
        when(personService.deleteById(Mockito.<Long>any())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/persons/{id}", 1L);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(personController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Test quickSearchAction(String, Pageable); then calls findByFirstNameStartingWith(String, Pageable)")
    void testQuickSearchAction_thenCallsFindByFirstNameStartingWith() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        PersonPagingRepository personPagingRepository = mock(PersonPagingRepository.class);
        when(personPagingRepository.findByFirstNameStartingWith(Mockito.<String>any(), Mockito.<Pageable>any()))
                .thenReturn(null);

        // Act
        List<Person> actualQuickSearchActionResult = (new PersonController(
                new PersonService(mock(PersonRepository.class), personPagingRepository))).quickSearchAction("Jane", null);

        // Assert
        verify(personPagingRepository).findByFirstNameStartingWith(eq("Jane"), isNull());
        assertNull(actualQuickSearchActionResult);
    }

    @Test
    @DisplayName("Test quickSearchAction(String, Pageable); then calls findPersonByFirstNameStartWith(String, Pageable)")
    void testQuickSearchAction_thenCallsFindPersonByFirstNameStartWith() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        PersonService service = mock(PersonService.class);
        when(service.findPersonByFirstNameStartWith(Mockito.<String>any(), Mockito.<Pageable>any())).thenReturn(null);

        // Act
        List<Person> actualQuickSearchActionResult = (new PersonController(service)).quickSearchAction("Jane", null);

        // Assert
        verify(service).findPersonByFirstNameStartWith(eq("Jane"), isNull());
        assertNull(actualQuickSearchActionResult);
    }

    @Test
    @DisplayName("Test updateById(Long, PersonUpdateDTO)")
    void testUpdateById() throws Exception {
        // Arrange
        PersonUpdateDTO personUpdateDTO = new PersonUpdateDTO();
        personUpdateDTO.setAge(1);
        personUpdateDTO.setEgn("Egn");
        personUpdateDTO.setFirstName("Jane");
        personUpdateDTO.setLastName("Doe");
        personUpdateDTO.setMiddleName("Middle Name");
        String content = (new ObjectMapper()).writeValueAsString(personUpdateDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/persons/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(personController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    @DisplayName("Test deletePerson(Long); given PersonService deleteById(Long) return 'true'; then status isNoContent()")
    void testDeletePerson_givenPersonServiceDeleteByIdReturnTrue_thenStatusIsNoContent() throws Exception {
        // Arrange
        when(personService.deleteById(Mockito.<Long>any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/persons/{id}", 1L);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(personController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
