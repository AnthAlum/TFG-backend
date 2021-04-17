package backend.api.clients;


import backend.clients.Client;
import backend.clients.ClientMapper;
import backend.clients.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
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
public class ClientsApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;
    private String jwt;

    private void checkEqualResponse(ClientResponse storedClient, ClientResponse receivedClient){
        assertThat(storedClient.getIdClient(), equalTo(receivedClient.getIdClient()));
        assertThat(storedClient.getName(), equalTo(receivedClient.getName()));
        assertThat(storedClient.getEmail(), equalTo(receivedClient.getEmail()));
        assertThat(storedClient.getPhone(), equalTo(receivedClient.getPhone()));
        assertThat(storedClient.getCompany(), equalTo(receivedClient.getCompany()));
        assertThat(storedClient.getRemind(), equalTo(receivedClient.getRemind()));
    }

    private void checkEqual(Client storedClient, Client receivedClient){
        assertThat(storedClient.getIdClient(), equalTo(receivedClient.getIdClient()));
        assertThat(storedClient.getName(), equalTo(receivedClient.getName()));
        assertThat(storedClient.getEmail(), equalTo(receivedClient.getEmail()));
        assertThat(storedClient.getPhone(), equalTo(receivedClient.getPhone()));
        assertThat(storedClient.getCompany(), equalTo(receivedClient.getCompany()));
        assertThat(storedClient.getRemind(), equalTo(receivedClient.getRemind()));
    }

    private void checkLists(List<Client> storedClients, List<ClientResponse> receivedClients){
        for(int i = 0; i < storedClients.size(); i++)
            checkEqualResponse(clientMapper.clientToClientResponse(storedClients.get(i)), receivedClients.get(i));
    }

    @Before
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
    public void createClient() throws Exception {
        String name = "Powered by Isa";
        String phone = "651825712";
        String company = "Tartas Darun";
        String email = "isabel.duranchu@alum.uca.es";
        String remind = "5";
        Long idClient = null;
        try{
            String body = buildPostClientBody(email);
            MockHttpServletRequestBuilder builder = post("/clients").contentType(MediaType.APPLICATION_JSON).content(body).header("Authorization", this.jwt);
            mvc.perform(builder).andExpect(status().isOk());
            //Testing is stored client in database matches with sent.
            Client storedClient = findOneClientByEmail(email);
            idClient = storedClient.getIdClient();
            Client client = new Client(idClient, name, email, phone, company, Long.parseLong(remind),  null);
            checkEqual(storedClient, client);
        } finally {
            deleteClientStored(idClient);
        }
    }

    @Test
    public void deleteClient() throws Exception {
        String email = getEmail();
        String postBody = buildPostClientBody(email);
        Long idClient = null;
        try{
            postClient(postBody);
            Client client = findOneClientByEmail(email);
            idClient = client.getIdClient();
            //Delete
            MockHttpServletRequestBuilder requestBuilder = delete("/clients/" + idClient).header("Authorization", this.jwt);
            mvc.perform(requestBuilder).andExpect(status().isOk());
            //Check if it has been removed.
            Client storedClient = findOneClientByEmail(email);
            assertThat(storedClient, is(nullValue()));
            //The next instruction will be deletedClientStored, then we set idClient to null
            idClient = null;
        } finally {
            deleteClientStored(idClient);
        }
    }

    @Test
    public void getClient() throws Exception {
        String email = getEmail();
        String postBody = buildPostClientBody(email);
        Long idClient = null;
        try{
            //Store client.
            postClient(postBody);
            Client storedClient = findOneClientByEmail(email);
            idClient = storedClient.getIdClient();
            //Check if the client has been stored and assert equality.
            String receivedClient = getContentFromUrl("/clients/" + idClient);
            ClientResponse clientResponse = new ObjectMapper().readValue(receivedClient, ClientResponse.class);
            checkEqual(storedClient, clientResponseToClient(clientResponse));
        } finally {
            deleteClientStored(idClient);
        }
    }

    @Test
    public void getClients() throws Exception {
        String pageNumber = "0", pageSize = "25";
        String url = "/clients?page=" + pageNumber + "&size=" + pageSize;
        String response = getContentFromUrl(url);
        List<ClientResponse> pages = new ObjectMapper().readValue(response, ClientPaginatedResponse.class).getPages();
        pages.forEach(clientResponse -> {
            Long idClient = clientResponse.getIdClient();
            Client storedClient = clientRepository.findById(idClient).orElse(null);
            Client receivedClient = clientResponseToClient(clientResponse);
            checkEqual(storedClient, receivedClient);
        });
    }

    @Test
    public void modifyClientName() throws Exception {
        String email = getEmail();
        String postBody = buildPostClientBody(email);
        String newName = "Name changed";
        String putBody = buildPutBody("newName", newName);
        Long idClient = null;
        try{
            postClient(postBody);
            Client client = findOneClientByEmail(email);
            idClient = client.getIdClient();
            //Change name value and asset that stored value is equal to sent value.
            putClient(client.getIdClient(), "name", putBody);
            Client storedClient = clientRepository.findById(idClient).orElse(null);
            assertThat(storedClient.getName(), equalTo(newName));
        } finally {
            deleteClientStored(idClient);
        }
    }

    @Test
    public void modifyClientEmail() throws Exception {
        String email = getEmail();
        String postBody = buildPostClientBody(email);
        String newEmail = "new" + email;
        String putBody = buildPutBody("newEmail", newEmail);
        Long idClient = null;
        try{
            postClient(postBody);
            Client client = findOneClientByEmail(email);
            idClient = client.getIdClient();
            //Change email value and asset that stored value is equal to sent value.
            putClient(client.getIdClient(), "email", putBody);
            Client storedClient = clientRepository.findById(idClient).orElse(null);
            assertThat(storedClient.getEmail(), equalTo(newEmail));
        } finally {
            deleteClientStored(idClient);
        }
    }

    @Test
    public void modifyClientPhone() throws Exception {
        String email = getEmail();
        String postBody = buildPostClientBody(email);
        String newPhone = "612345678";
        String putBody = buildPutBody("newPhone", newPhone);
        Long idClient = null;
        try{
            postClient(postBody);
            Client client = findOneClientByEmail(email);
            idClient = client.getIdClient();
            //Change phone value and asset that stored value is equal to sent value.
            putClient(client.getIdClient(), "phone", putBody);
            Client storedClient = clientRepository.findById(idClient).orElse(null);
            assertThat(storedClient.getPhone(), equalTo(newPhone));
        } finally {
            deleteClientStored(idClient);
        }
    }

    @Test
    public void modifyClientCompany() throws Exception {
        String email = getEmail();
        String postBody = buildPostClientBody(email);
        String newCompany = "newCompany";
        String putBody = buildPutBody("newCompany", newCompany);
        Long idClient = null;
        try{
            postClient(postBody);
            Client client = findOneClientByEmail(email);
            idClient = client.getIdClient();
            //Change company value and asset that stored value is equal to sent value.
            putClient(idClient, "company", putBody);
            Client storedClient = clientRepository.findById(idClient).orElse(null);
            assertThat(storedClient.getCompany(), equalTo(newCompany));
        } finally {
            deleteClientStored(idClient);
        }
    }

    @Test
    public void modifyClientRemind() throws Exception {
        String email = getEmail();
        String postBody = buildPostClientBody(email);
        String newRemind = "10";
        String putBody = "{\n" +
                "  \"newRemind\": " + newRemind + "\n" +
                "}";
        Long idClient = null;
        try{
            postClient(postBody);
            Client client = findOneClientByEmail(email);
            idClient = client.getIdClient();
            //Change company value and asset that stored value is equal to sent value.
            putClient(idClient, "remind", putBody);
            Client storedClient = clientRepository.findById(idClient).orElse(null);
            assertThat(storedClient.getRemind(), equalTo(Long.parseLong(newRemind)));
        } finally {
            deleteClientStored(idClient);
        }
    }

    @Test
    public void getClientsByName() throws Exception {
        String name = "Client", pageNumber = "0", pageSize = "25";
        String url = "/clients/findbyname?name=" + name + "&page=" + pageNumber + "&size=" + pageSize;
        String response = getContentFromUrl(url);
        List<ClientResponse> receivedClients = new ObjectMapper().readValue(response, ClientPaginatedResponse.class).getPages();
        //Testing received client responses and the clients in database.
        List<Client> storedClients = clientRepository.findByNameContains(name, PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize))).getContent();
        checkLists(storedClients, receivedClients);
    }

    @Test
    public void getClientsByEmail() throws Exception {
        String email = "client", pageNumber = "0", pageSize = "25";
        String url = "/clients/findbyemail?email=" + email + "&page=" + pageNumber + "&size=" + pageSize;
        String response = getContentFromUrl(url);
        List<ClientResponse> receivedClients = new ObjectMapper().readValue(response, ClientPaginatedResponse.class).getPages();
        //Testing received client responses and the clients in database.
        List<Client> storedClients = clientRepository.findByEmailContains(email, PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize))).getContent();
        checkLists(storedClients, receivedClients);
    }


    @Test
    public void getClientsByPhone() throws Exception {
        String phone = "7", pageNumber = "0", pageSize = "25";
        String url = "/clients/findbyphone?phone=" + phone + "&page=" + pageNumber + "&size=" + pageSize;
        String response = getContentFromUrl(url);
        List<ClientResponse> receivedClients = new ObjectMapper().readValue(response, ClientPaginatedResponse.class).getPages();
        //Testing received client responses and the clients in database.
        List<Client> storedClients = clientRepository.findByPhoneContains(phone, PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize))).getContent();
        checkLists(storedClients, receivedClients);
    }

    @Test
    public void getClientsByCompany() throws Exception {
        String company = "Ronaldo", pageNumber = "0", pageSize = "25";
        String url = "/clients/findbycompany?company=" + company + "&page=" + pageNumber + "&size=" + pageSize;
        String response = getContentFromUrl(url);
        List<ClientResponse> receivedClients = new ObjectMapper().readValue(response, ClientPaginatedResponse.class).getPages();
        //Testing received client responses and the clients in database.
        List<Client> storedClients = clientRepository.findByCompanyContains(company, PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize))).getContent();
        checkLists(storedClients, receivedClients);
    }



    private Client clientResponseToClient(ClientResponse clientResponse){
        Client client = new Client(clientResponse.getIdClient(), clientResponse.getName(), clientResponse.getEmail(),
                clientResponse.getPhone(), clientResponse.getCompany(), Long.parseLong("5"), null);
        return client;
    }

    private String getContentFromUrl(String url) throws Exception{
        MockHttpServletRequestBuilder builder = get(url).header("Authorization", this.jwt);
        MvcResult mvcResult = mvc.perform(builder).andExpect(status().isOk()).andReturn();
        return mvcResult.getResponse().getContentAsString();
    }

    private void deleteClientStored(Long idClient){
        if(idClient == null)
            return;
        Client client = clientRepository.findById(idClient).orElse(null);
        if(client == null)
            return;
        clientRepository.delete(client);
    }

    public void putClient(Long idClient, String attribute, String body) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = put("/clients/" + idClient + "/" + attribute).contentType(MediaType.APPLICATION_JSON)
                .content(body).header("Authorization", this.jwt);
        mvc.perform(requestBuilder).andExpect(status().isOk());
    }

    private void postClient(String body) throws Exception {
        MockHttpServletRequestBuilder builder = post("/clients").contentType(MediaType.APPLICATION_JSON).content(body).header("Authorization", this.jwt);
        mvc.perform(builder).andExpect(status().isOk());
    }

    private String buildPostClientBody(String email){
        String name = "Powered by Isa";
        String phone = "651825712";
        String company = "Tartas Darun";
        String remind = "5";
        String postBody = "{\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"phone\": \"" + phone + "\",\n" +
                "  \"company\": \"" + company + "\",\n" +
                "  \"remind\": " + remind + "\n" +
                "}";
        return postBody;
    }

    private String buildPutBody(String attribute, String newValue){
        String putBody = "{\n" +
                "  \"" + attribute +"\": \"" + newValue + "\"\n" +
                "}";
        return putBody;
    }

    private String getEmail(){
        return  "isabel.duranchu@alum.uca.es";
    }


    private Client findOneClientByEmail(String email){
        List<Client> content = clientRepository.findClientByEmail(email); //Search clients with the given email.
        if(content.size() == 0)
            return null;
        assertThat(1, equalTo(content.size())); //This page must have only one element, the created client.
        return content.get(0);
    }
}