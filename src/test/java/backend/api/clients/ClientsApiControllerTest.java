package backend.api.clients;

/*
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientsApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ClientRepository clientRepository;
    private String jwt;
    //TODO: Hemos agregado el remind
    private void checkEqual(Client storedClient, Client receivedClient){
        assertThat(storedClient.getName(), equalTo(receivedClient.getName()));
        assertThat(storedClient.getEmail(), equalTo(receivedClient.getEmail()));
        assertThat(storedClient.getPhone(), equalTo(receivedClient.getPhone()));
        assertThat(storedClient.getCompany(), equalTo(receivedClient.getCompany()));
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
        System.out.println(body);
        String jwtResponse = mvcResult.getResponse().getContentAsString();
        this.jwt = jwtResponse.substring(8, jwtResponse.length() - 2);
    }

    @Test
    public void createClient() throws Exception {
        String name = "Powered by Isa";
        String phone = "651825712";
        String company = "Tartas Darun";
        String email = "isabel.duranchu@alum.uca.es";
        try{
            String body = buildPostClientBody(email);
            MockHttpServletRequestBuilder builder = post("/clients").contentType(MediaType.APPLICATION_JSON).content(body).header("Authorization", this.jwt);
            mvc.perform(builder).andExpect(status().isOk());

            Client client = new Client(Long.parseLong("0"), name, email, phone, company, Long.parseLong("5"),  null);
            Client storedClient = clientRepository.findClientByEmail(email);
            checkEqual(storedClient, client);
        } finally {
            deleteClientStored(email);
        }
    }

    @Test
    public void deleteClient() throws Exception {
        String email = getEmail();
        String postBody = buildPostClientBody(email);
        try{
            postClient(postBody);
            Client client = clientRepository.findClientByEmail(email);
            MockHttpServletRequestBuilder requestBuilder = delete("/clients/" + client.getIdClient()).header("Authorization", this.jwt);
            mvc.perform(requestBuilder).andExpect(status().isOk());
            Client storedClient = clientRepository.findClientByEmail(email);
            assertThat(storedClient, is(nullValue()));
        } finally {
            deleteClientStored(email);
        }
    }

    @Test
    public void getClient() throws Exception {
        String email = getEmail();
        String postBody = buildPostClientBody(email);
        try{
            postClient(postBody);
            Client storedClient = clientRepository.findClientByEmail(email);
            String receivedClient = getContentFromUrl("/clients/" + storedClient.getIdClient());
            ClientResponse clientResponse = new ObjectMapper().readValue(receivedClient, ClientResponse.class);
            checkEqual(storedClient, clientResponseToClient(clientResponse));
        } finally {
            deleteClientStored(email);
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
        try{
            postClient(postBody);
            Client client = clientRepository.findClientByEmail(email);
            putClient(client.getIdClient(), "name", putBody);
            Client storedClient = clientRepository.findById(client.getIdClient()).orElse(null);
            assertThat(storedClient.getName(), equalTo(newName));
        } finally {
            deleteClientStored(email);
        }
    }

    @Test
    public void modifyClientEmail() {
    }

    @Test
    public void modifyClientPhone() {
    }

    @Test
    public void modifyClientCompany() {
    }

    @Test
    public void getClientsByEmail() {
    }

    @Test
    public void getClientsByName() {
    }

    @Test
    public void getClientsByPhone() {
    }

    @Test
    public void getClientsByCompany() {
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

    private void deleteClientStored(String email){
        Client client = clientRepository.findClientByEmail(email);
        if(client != null)
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
        String postBody = "{\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"phone\": \"" + phone + "\",\n" +
                "  \"company\": \"" + company+ "\"\n" +
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
}*/