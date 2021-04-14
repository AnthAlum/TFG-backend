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
import org.springframework.data.domain.PageRequest;
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
public class MerchantsApiControllerTest {

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

    private void checkEqualSimplified(Merchant storedMerchant, Merchant receivedMerchant){
        assertThat(storedMerchant.getName(), equalTo(receivedMerchant.getName()));
        assertThat(storedMerchant.getEmail(), equalTo(receivedMerchant.getEmail()));
    }

    private void checkEqual(Merchant storedMerchant, Merchant receivedMerchant){
        assertThat(storedMerchant.getIdRole(), equalTo(receivedMerchant.getIdRole()));
        assertThat(storedMerchant.getName(), equalTo(receivedMerchant.getName()));
        assertThat(storedMerchant.getEmail(), equalTo(receivedMerchant.getEmail()));
        assertThat(storedMerchant.getPhone(), equalTo(receivedMerchant.getPhone()));
        assertThat(true, equalTo(passwordEncoder.matches(receivedMerchant.getPassword(), storedMerchant.getPassword())));
    }

    private void checkLists(List<Merchant> storedMerchants, List<MerchantResponse> receivedMerchants){
        for(int i = 0; i < receivedMerchants.size(); i++){
            Merchant receivedMerchant = merchantResponseToMerchant(receivedMerchants.get(i));
            Merchant storedMerchant = storedMerchants.get(i);
            checkEqualResponse(storedMerchant, receivedMerchant);
        }
    }

    @Before
    public void authorization() throws Exception{
        String username = "correo@example.com";
        String password = "password";
        String body = "{\n" +
                "  \"username\": \"" + username + "\",\n" +
                "  \"password\": \"" + password + "\"\n" +
                        "}";
        MvcResult mvcResult = mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(body))
                .andReturn();
        System.out.println(body);
        String jwtResponse = mvcResult.getResponse().getContentAsString();
        this.jwt = jwtResponse.substring(8, jwtResponse.length() - 2);
    }

    @Test
    public void addMerchant() throws Exception{
        int idRole = 0;
        String name = "Powered By Isa";
        String email = "isabel.duranchu@alum.uca.es";
        String phone = "651825712";
        String password = "password";
        try{
            String body = "{\n" +
                    "  \"idRole\":" + idRole + ",\n" +
                    "  \"name\": \"" + name + "\",\n" +
                    "  \"email\": \"" + email + "\",\n" +
                    "  \"phone\": \"" + phone + "\",\n" +
                    "  \"password\": \"" + password + "\"\n" +
                    "}";
            MockHttpServletRequestBuilder builder = post("/merchants").contentType(MediaType.APPLICATION_JSON).content(body).header("Authorization", this.jwt);
            mvc.perform(builder).andExpect(status().isOk());

            Merchant merchant = new Merchant(Long.parseLong("0"), idRole, email, name, phone, password, null);
            Merchant storedMerchant = merchantRepository.findMerchantByEmail(email);
            checkEqual(storedMerchant, merchant);
        } finally {
            deleteMerchant(email);
        }
    }

    @Test
    public void getMerchants() throws Exception { //Test responses are equal to database data.
        String pageNumber = "0", pageSize = "25";
        String url = "/merchants?page=" + pageNumber + "&size=" + pageSize;
        String response = getContentFromUrl(url);
        List<MerchantResponse> pages = new ObjectMapper().readValue(response, MerchantPaginatedResponse.class).getPages();
        //Testing received merchant responses and the merchant in database.
        pages.forEach(merchantReponse -> {
            Long idMerchant = merchantReponse.getIdMerchant();
            Merchant storedMerchant = merchantRepository.findById(idMerchant).orElse(null);
            Merchant receivedMerchant = merchantResponseToMerchant(merchantReponse);
            checkEqualResponse(storedMerchant, receivedMerchant);
        });
        //Testing pagination info received and sent?
    }

    @Test
    public void getMerchantsSimplified() throws Exception {
        String url = "/merchants-simplified";
        String response = getContentFromUrl(url);
        List<MerchantSimplifiedResponse> merchantSimplifiedResponseList = new ObjectMapper().readValue(response, MerchantsSimplifiedListResponse.class).getMerchantSimplifiedResponseList();
        //Testing received merchant simplified responses and the merchant in database.
        merchantSimplifiedResponseList.forEach(merchantSimplifiedResponse -> {
            Long idMerchant = merchantSimplifiedResponse.getIdMerchant();
            Merchant storedMerchant = merchantRepository.findById(idMerchant).orElse(null);
            Merchant receivedMerchant = merchantSimplifiedResponseToMerchant(merchantSimplifiedResponse);
            checkEqualSimplified(storedMerchant, receivedMerchant);
        });
    }

    @Test
    public void getMerchantsByName() throws Exception {
        String name = "Armando", pageNumber = "0", pageSize = "25";
        String url = "/merchants/findbyname?name=" + name + "&page=" + pageNumber + "&size=" + pageSize;
        String response = getContentFromUrl(url);
        List<MerchantResponse> receivedMerchants = new ObjectMapper().readValue(response, MerchantPaginatedResponse.class).getPages();
        //Testing received merchant responses and the merchant in database.
        List<Merchant> storedMerchants = merchantRepository.findByNameContains(name, PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize))).getContent();
        checkLists(storedMerchants, receivedMerchants);
    }

    @Test
    public void getMerchantsByEmail() throws Exception{
        String email = "correo", pageNumber = "0", pageSize = "25";
        String url = "/merchants/findbyname?name=" + email + "&page=" + pageNumber + "&size=" + pageSize;
        String response = getContentFromUrl(url);
        List<MerchantResponse> receivedMerchants = new ObjectMapper().readValue(response, MerchantPaginatedResponse.class).getPages();
        //Testing received merchant responses and the merchant in database.
        List<Merchant> storedMerchants = merchantRepository.findByNameContains(email, PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize))).getContent();
        checkLists(storedMerchants, receivedMerchants);
    }

    @Test
    public void getMerchantsByPhone() throws Exception {
        String phone = "628", pageNumber = "0", pageSize = "25";
        String url = "/merchants/findbyname?name=" + phone + "&page=" + pageNumber + "&size=" + pageSize;
        String response = getContentFromUrl(url);
        List<MerchantResponse> receivedMerchants = new ObjectMapper().readValue(response, MerchantPaginatedResponse.class).getPages();
        //Testing received merchant responses and the merchant in database.
        List<Merchant> storedMerchants = merchantRepository.findByNameContains(phone, PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize))).getContent();
        checkLists(storedMerchants, receivedMerchants);
    }

    @Test
    public void getMerchantsByIdRole() throws Exception {
        String idRole = "1", pageNumber = "0", pageSize = "25";
        String url = "/merchants/findbyidRole?idRole=" + idRole + "&page=" + pageNumber + "&size=" + pageSize;
        String response = getContentFromUrl(url);
        List<MerchantResponse> receivedMerchants = new ObjectMapper().readValue(response, MerchantPaginatedResponse.class).getPages();
        //Testing received merchant responses and the merchant in database.
        List<Merchant> storedMerchants = merchantRepository.findByIdRole(Integer.parseInt(idRole), PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize))).getContent();
        checkLists(storedMerchants, receivedMerchants);
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

    private Merchant merchantSimplifiedResponseToMerchant(MerchantSimplifiedResponse merchantSimplifiedResponse){
        Merchant merchant = new Merchant(null, null, merchantSimplifiedResponse.getEmail(), merchantSimplifiedResponse.getNameAndLastName(),
                null, null, null);
        return merchant;
    }

    private Merchant merchantResponseToMerchant(MerchantResponse merchantResponse){
        Merchant merchant = new Merchant(merchantResponse.getIdMerchant(), merchantResponse.getIdRole(), merchantResponse.getEmail(),
                merchantResponse.getName(), merchantResponse.getPhone(), null, null);
        return merchant;
    }
}