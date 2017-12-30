package io.github.taowang0622;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        String result = mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .param("userName", "tao")
//                .param("page", "2")
//                .param("size", "17")
//                .param("sort", "age asc")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }

    @Test
    public void whenGetInfoSuccess() throws Exception {
        String result = mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("kevin"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }

    @Test
    public void whenGetInfoFailure() throws Exception {
        mockMvc.perform(get("user/a"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenCreateSuccess() throws Exception {
        Date now = new Date();
        String content = "{\"userName\":\"kevin\",\"password\":null, \"birthday\":"+now.getTime()+"}";
        String result = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }

    @Test
    public void whenUpdateSuccess() throws Exception {
        long timestamp = LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        String content = "{\"id\":1,\"userName\":\"kevin\",\"password\":null, \"birthday\":"+timestamp+"}";
        String result = mockMvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }

    @Test
    public void whenDeleteSuccess() throws Exception {
        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenUploadSuccess() throws Exception {
        String result = mockMvc.perform(fileUpload("/file")
                //Within request headers, file=test.txt(a key-value pair!!)
                .file(new MockMultipartFile("file",
                        "test.txt",
                        "multipart/form-data",
                        "Hello Upload".getBytes("UTF-8"))))
                //***************request headers****************//
                //Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryAJZ6eKlJ0fSEBRln
                //Content-Length: 940
                //**************request body/payload******************//
                //----WebKitFormBoundaryAJZ6eKlJ0fSEBRln
                //Content-Disposition: form-data; name="file"; filename="test.txt"
                //Content-Type: text/plain
                //67
                //----WebKitFormBoundaryAJZ6eKlJ0fSEBRln
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }
}
