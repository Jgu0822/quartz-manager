package it.fabioformosa.quartzmanager.security;

import it.fabioformosa.quartzmanager.security.controllers.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static it.fabioformosa.quartzmanager.security.WebSecurityConfigJWT.LOGIN_PATH;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
  "quartz-manager.security.jwt.enabled=true",
  "quartz-manager.security.jwt.secret=bibidibobidiboo",
  "quartz-manager.security.jwt.expiration-in-sec=28800",
  "quartz-manager.security.jwt.header-strategy.enabled=false",
  "quartz-manager.security.jwt.header-strategy.header=Authorization",
  "quartz-manager.security.jwt.cookie-strategy.enabled=true",
  "quartz-manager.security.jwt.cookie-strategy.cookie=AUTH-TOKEN",
  "quartz-manager.accounts.in-memory.enabled=true",
  "quartz-manager.accounts.in-memory.users[0].name=foo",
  "quartz-manager.accounts.in-memory.users[0].password=bar",
  "quartz-manager.accounts.in-memory.users[0].roles[0]=admin",
})
public class SecurityControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void givenAnAnonymousUser_whenCalledATestController_thenShouldRaiseForbidden() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/test"))
      .andExpect(status().isUnauthorized());
  }

  @Test
  void givenAnAnonymousUser_whenCalledATestScheduler_thenShouldRaiseForbidden() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(TestController.QUARTZ_MANAGER + "/scheduler"))
      .andExpect(status().isUnauthorized());
  }

  @Test
  void givenAnAnonymousUser_whenRequestedSwaggerResource_thenShouldReturn2xx() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/swagger-ui.html"))
      .andExpect(status().isOk());
  }

  @Test
  @WithMockUser("admin")
  void givenAnUser_whenCalledATestScheduler_thenShouldReturn2xx() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(TestController.QUARTZ_MANAGER + "/scheduler"))
      .andExpect(status().isOk());
  }

  @Test
  void givenAnAnonymousUser_whenCalledTheLoginPath_thenShouldReturn2xx() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_PATH)
        .contentType("application/x-www-form-urlencoded")
        .accept("application/json")
        .param("username", "foo")
        .param("password", "bar"))
      .andExpect(status().isOk());
  }

}
