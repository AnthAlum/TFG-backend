package backend.api.merchants;


import backend.merchants.Merchant;
import backend.merchants.MerchantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class MerchantApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private String jwt;

    private void checkEqualResponse(Merchant storedMerchant, Merchant receivedMerchant){
        assertThat(storedMerchant.getIdRole(), equalTo(receivedMerchant.getIdRole()));
        assertThat(storedMerchant.getName(), equalTo(receivedMerchant.getName()));
        assertThat(storedMerchant.getEmail(), equalTo(receivedMerchant.getEmail()));
        assertThat(storedMerchant.getPhone(), equalTo(receivedMerchant.getPhone()));
    }

    @Before //This method gets a JWT in order to make request.
    public void setUp() throws Exception {
        String username = "correo@example.com";
        String password = "password";
        String body = "{\n" +
                "  \"username\": \"" + username + "\",\n" +
                "  \"password\": \"" + password + "\"\n" +
                "}";
        MvcResult mvcResult = mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(body))
                .andReturn();
        String jwtResponse = mvcResult.getResponse().getContentAsString();
        this.jwt = jwtResponse.substring(8, jwtResponse.length() - 2);
    }

    @Test
    public void deleteMerchant() throws Exception{
        String email = "isabel.duranchu@alum.uca.es";
        String postBody = buildPostBody(email);
        try{
            postMerchant(postBody); //Post temp merchant.
            //Get the posted merchant ID and deletes it.
            Merchant merchant = merchantRepository.findMerchantByEmail(email);
            MockHttpServletRequestBuilder requestBuilder = delete("/merchants/" + merchant.getIdMerchant()).header("Authorization", this.jwt);
            mvc.perform(requestBuilder).andExpect(status().isOk());
            //Check if repository has deleted the given merchant.
            Merchant storedMerchant = merchantRepository.findMerchantByEmail(email);
            assertThat(storedMerchant, is(nullValue()));
        } finally {
            deleteMerchant(email);
        }
    }

    @Test
    public void getMerchant() throws Exception {
        String pageNumber = "0", pageSize = "25";
        String url = "/merchants?page=" + pageNumber + "&size=" + pageSize;
        String response = getContentFromUrl(url);
        List<MerchantResponse> pages = new ObjectMapper().readValue(response, MerchantPaginatedResponse.class).getPages();
        //Testing received merchant responses and the merchant in database.
        pages.forEach(merchantReponse -> {
            Long idMerchant = merchantReponse.getIdMerchant(); //Get response ID.
            String urlId = "/merchants/" + idMerchant; //Build a url request with that ID.
            try {
                String content = getContentFromUrl(urlId);//Get given information.
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            Merchant storedMerchant = merchantRepository.findById(idMerchant).orElse(null);
            Merchant receivedMerchant = merchantResponseToMerchant(merchantReponse);
            checkEqualResponse(storedMerchant, receivedMerchant);
        });
    }

    @Test
    public void modifyMerchantName() throws Exception {
        String email = "isabel.duranchu@alum.uca.es";
        String postBody = buildPostBody(email);
        String newName = "Name changed";
        String putBody = buildPutBody("newName", newName);
        try{
            postMerchant(postBody); //Post temp merchant.
            //Get the posted merchant ID and modify its name.
            Merchant merchant = merchantRepository.findMerchantByEmail(email);
            MockHttpServletRequestBuilder builder = put("/merchants/" + merchant.getIdMerchant() + "/name").contentType(MediaType.APPLICATION_JSON).content(putBody).header("Authorization", this.jwt);
            mvc.perform(builder).andExpect(status().isOk());
            //Check if sent phone has been saved in repository
            merchant = merchantRepository.findMerchantByEmail(email); //Gets same merchant with modified attribute.
            assertThat(merchant.getName(), equalTo(newName));
        } finally {
            deleteMerchant(email);
        }
    }

    @Test
    public void modifyMerchantEmail() throws Exception {
        String email = "isabel.duranchu@alum.uca.es";
        String postBody = buildPostBody(email);
        String newEmail = "isabel.duranchu2@alum.uca.es";
        String putBody = buildPutBody("newEmail", newEmail);
        try{
            postMerchant(postBody); //Post temp merchant.
            //Get the posted merchant ID and modify its email.
            Merchant merchant = merchantRepository.findMerchantByEmail(email);
            MockHttpServletRequestBuilder builder = put("/merchants/" + merchant.getIdMerchant() + "/email").contentType(MediaType.APPLICATION_JSON).content(putBody).header("Authorization", this.jwt);
            mvc.perform(builder).andExpect(status().isOk());
            //Check if sent phone has been saved in repository
            merchant = merchantRepository.findMerchantByEmail(newEmail); //Gets same merchant with modified attribute.
            assertThat(merchant.getEmail(), equalTo(newEmail));
        } finally {
            deleteMerchant(newEmail);
        }
    }

    @Test
    public void modifyMerchantPhone() throws Exception{
        String email = "isabel.duranchu@alum.uca.es";
        String postBody = buildPostBody(email);
        String newPhone = "123456789";
        String putBody = buildPutBody("newPhone", newPhone);
        try{
            postMerchant(postBody); //Post temp merchant.
            //Get the posted merchant ID and modify its phone.
            Merchant merchant = merchantRepository.findMerchantByEmail(email);
            MockHttpServletRequestBuilder builder = put("/merchants/" + merchant.getIdMerchant() + "/phone").contentType(MediaType.APPLICATION_JSON).content(putBody).header("Authorization", this.jwt);
            mvc.perform(builder).andExpect(status().isOk());
            //Check if sent phone has been saved in repository
            merchant = merchantRepository.findMerchantByEmail(email); //Gets same merchant with modified attribute.
            assertThat(merchant.getPhone(), equalTo(newPhone));
        } finally {
            deleteMerchant(email);
        }
    }

    @Test
    public void modifyMerchantRole() throws Exception{
        String email = "isabel.duranchu@alum.uca.es";
        String postBody = buildPostBody(email);
        String newIdRole = "0";
        String putBody = buildPutIdRoleBody(newIdRole);
        try{
            postMerchant(postBody); //Post temp merchant.
            //Get the posted merchant ID and modify its phone.
            Merchant merchant = merchantRepository.findMerchantByEmail(email);
            MockHttpServletRequestBuilder builder = put("/merchants/" + merchant.getIdMerchant() + "/role").contentType(MediaType.APPLICATION_JSON).content(putBody).header("Authorization", this.jwt);
            mvc.perform(builder).andExpect(status().isOk());
            //Check if sent idRole has been saved in repository(changed "1" to "0")
            merchant = merchantRepository.findMerchantByEmail(email); //Gets same merchant with modified attribute.
            assertThat(merchant.getIdRole(), equalTo(Integer.parseInt(newIdRole)));
        } finally {
            deleteMerchant(email); //Delete temp merchant
        }
    }

    @Test
    public void modifyMerchantPassword() throws Exception {
        String email = "isabel.duranchu@alum.uca.es";
        String postBody = buildPostBody(email);
        String newPassword = "passwordModified";
        String putBody = buildPutPasswordBody("password", newPassword);
        try{
            postMerchant(postBody); //Post temp merchant.
            //Get the posted merchant ID and modify its phone.
            Merchant merchant = merchantRepository.findMerchantByEmail(email);
            MockHttpServletRequestBuilder builder = put("/merchants/" + merchant.getIdMerchant() + "/password").contentType(MediaType.APPLICATION_JSON).content(putBody).header("Authorization", this.jwt);
            mvc.perform(builder).andExpect(status().isOk());
            //Check if sent password has been saved in repository
            merchant = merchantRepository.findMerchantByEmail(email); //Gets same merchant with modified attribute.
            assertThat(true, equalTo(passwordEncoder.matches(newPassword, merchant.getPassword())));
        } finally {
            deleteMerchant(email); //Delete temp merchant
        }
    }

    private void deleteMerchant(String email){
        Merchant merchant = merchantRepository.findMerchantByEmail(email);
        if(merchant != null)
            merchantRepository.delete(merchant);
    }

    private String getContentFromUrl(String url) throws Exception{
        MockHttpServletRequestBuilder builder = get(url).header("Authorization", this.jwt);
        MvcResult mvcResult = mvc.perform(builder).andExpect(status().isOk()).andReturn();
        return mvcResult.getResponse().getContentAsString();
    }

    private Merchant merchantResponseToMerchant(MerchantResponse merchantResponse){
        Merchant merchant = new Merchant(merchantResponse.getIdMerchant(), merchantResponse.getIdRole(), merchantResponse.getEmail(),
                merchantResponse.getName(), merchantResponse.getPhone(), null, null);
        return merchant;
    }

    private void postMerchant(String body) throws Exception{
        MockHttpServletRequestBuilder builder = post("/merchants").contentType(MediaType.APPLICATION_JSON).content(body).header("Authorization", this.jwt);
        mvc.perform(builder).andExpect(status().isOk());
    }

    private String buildPostBody(String email){
        int idRole = 0;
        String name = "Powered By Isa";
        String phone = "651825712";
        String password = "password";
        String postBody = "{\n" +
                "  \"idRole\":" + idRole + ",\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"phone\": \"" + phone + "\",\n" +
                "  \"password\": \"" + password + "\"\n" +
                "}";
        return postBody;
    }

    private String buildPutBody(String attribute, String newValue){
        String putBody = "{\n" +
                "  \"" + attribute +"\": \"" + newValue + "\"\n" +
                "}";
        return putBody;
    }

    private String buildPutIdRoleBody(String newRole){
        String putBody = "{\n" +
                "  \"" + "newRole" +"\": " + newRole + "\n" +
                "}";
        return putBody;
    }

    private String buildPutPasswordBody(String actualPassword, String newPassword){
        String putBody = "{\n" +
                "  \"password\": \"" + actualPassword + "\",\n" +
                "  \"newPassword\": \"" + newPassword + "\"\n" +
                "}";
        return putBody;
    }
}