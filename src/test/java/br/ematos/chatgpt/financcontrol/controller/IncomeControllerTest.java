package br.ematos.chatgpt.financcontrol.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import br.ematos.chatgpt.financcontrol.entity.IncomeEntity;
import br.ematos.chatgpt.financcontrol.service.IncomeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class IncomeControllerTest {

  private static final String BASE_URL = "/income";

  @Mock private IncomeService incomeService;

  @MockBean private IncomeController incomeController;

  @Autowired private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    WireMock.reset();
    MockitoAnnotations.openMocks(this);
    objectMapper = new ObjectMapper();
  }

  @Test
  void testFindByDate() throws Exception {
    String dateString = "2023-03-17";
    Date date = new Date(dateString);

    List<IncomeEntity> expectedIncomeEntities =
        Collections.singletonList(new IncomeEntity(1, date, "From Who", "Comment", 100L, true));

    // Mocking service response
    when(incomeService.findByDate(any(Date.class))).thenReturn(expectedIncomeEntities);

    // Setting up wiremock to mock external API call
    stubFor(
        get(urlEqualTo("/externalAPI?date=" + dateString))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(objectMapper.writeValueAsString(expectedIncomeEntities))));

    // Making request to controller
    mockMvc
        .perform(MockMvcRequestBuilders.get(BASE_URL + "/date/" + dateString))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .json(objectMapper.writeValueAsString(expectedIncomeEntities)))
        .andDo(MockMvcResultHandlers.print());

    // Verifying service was called and request to external API was made
    verify(incomeService, times(1)).findByDate(date);
    verifyNoMoreInteractions(incomeService);
    WireMock.verify(1, getRequestedFor(urlEqualTo("/externalAPI?date=" + dateString)));
  }
}
