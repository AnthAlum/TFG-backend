package backend.api.meetings;

import backend.api.clients.ClientResponse;
import backend.api.merchants.MerchantResponse;
import backend.clients.Client;
import backend.clients.ClientRepository;
import backend.filemanagment.File;
import backend.meetings.Meeting;
import backend.meetings.MeetingMapper;
import backend.meetings.MeetingRepository;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingsApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingMapper meetingMapper;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private ClientRepository clientRepository;

    private String jwt;

    private void checkEqualMeetings(Meeting storedMeeting, Meeting receivedMeeting){ //Check equality in attributes.
        assertThat(storedMeeting.getIdMeeting(), equalTo(receivedMeeting.getIdMeeting()));
        assertThat(storedMeeting.getMatter(), equalTo(receivedMeeting.getMatter()));
        assertThat(storedMeeting.getDescription(), equalTo(receivedMeeting.getDescription()));
        checkEqualListStrings(storedMeeting.getKeywords(), receivedMeeting.getKeywords());
        checkEqualListStrings(storedMeeting.getWordCloud(), receivedMeeting.getWordCloud());
        checkEqualFiles(storedMeeting.getFiles(), receivedMeeting.getFiles());
    }

    private void checkSentData(MeetingResponse storedMeeting, String matter, String description, Long merchantId, Long clientId, String keyword){
        assertThat(matter, equalTo(storedMeeting.getMatter()));
        assertThat(description, equalTo(storedMeeting.getDescription()));
        assertThat(merchantId, equalTo(storedMeeting.getMerchants().getMerchantResponseList().get(0).getIdMerchant()));
        assertThat(clientId, equalTo(storedMeeting.getClients().getClientResponseList().get(0).getIdClient()));
        assertThat(keyword, equalTo(storedMeeting.getKeywords().get(0)));
    }

    private void checkEqualListStrings(List<String> keywodsA, List<String> keywordsB){
        for (int i = 0; i < keywodsA.size(); i++)
            assertThat(keywodsA.get(i), equalTo(keywordsB.get(i)));
    }

    private void checkEqualFiles(List<File> filesA, List<File> filesB){
        for (int i = 0; i < filesA.size(); i++) {
            assertThat(filesA.get(i).getIdFile(), equalTo(filesB.get(i).getIdFile()));
            assertThat(filesA.get(i).getFileName(), equalTo(filesA.get(i).getFileName()));
            assertThat(filesA.get(i).getFileType(), equalTo(filesB.get(i).getFileType()));
        }
    }

    private void checkEqualMeetingResponseListAndMeetingList(List<Meeting> meetings, List<MeetingResponse> meetingResponses){
        for(int i = 0; i < meetingResponses.size(); i++){
            Meeting receivedMeetingMeeting = meetingResponseToMeeting(meetingResponses.get(i));
            checkEqualMeetings(receivedMeetingMeeting, meetings.get(i));
        }
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
    public void getMeeting() throws Exception {
        String matter = "Test matter";
        String localDateTime = "12-03-1997 00:00";
        String description = "Testing description";
        String keyword = "keywordTest";
        Long merchantId = null;
        Long clientId = null;
        MeetingResponse storedMeeting = null;
        try{
            merchantId = postMerchantAndGetId(null);
            clientId = postClientAndGetId(null);
            storedMeeting = postMeeting(merchantId, clientId); //Post test meeting and get Id.
            //Check if stored data is equal to sent.
            checkSentData(storedMeeting, matter, description, merchantId, clientId, keyword);
        } finally {
            deletePostedData(merchantId, clientId, storedMeeting);
        }
    }

    @Test
    public void getWordCloudById() throws Exception {
        Long merchantId = null;
        Long clientId = null;
        MeetingResponse receivedMeeting = null;
        try{
            merchantId = postMerchantAndGetId(null);
            clientId = postClientAndGetId(null);
            receivedMeeting = postMeeting(merchantId, clientId); //Post test meeting and get Id.
            //Check if stored wordcloud is equal to received in the response.
            Meeting storedMeeting = meetingRepository.findById(receivedMeeting.getIdMeeting()).orElse(null);
            checkEqualListStrings(storedMeeting.getWordCloud(), receivedMeeting.getWordCloud());
        } finally {
            deletePostedData(merchantId, clientId, receivedMeeting);
        }
    }

    @Test
    public void getMeetings() throws Exception {
        String pageNumber = "0", pageSize = "25";
        String url = "/meetings?page=" + pageNumber + "&size=" + pageSize;
        String response = getContentFromUrl(url); //Get meeting page.
        List<MeetingResponse> pages = new ObjectMapper().readValue(response, MeetingPaginatedResponse.class).getPages();
        pages.forEach(meetingResponse -> { //This meeting response does not have files information.
            Long idMeeting = meetingResponse.getIdMeeting();
            MeetingResponse meetingResponse1 = null;
            try {
                meetingResponse1 = getMeetingWithFilesById(idMeeting); //Getting meeting with files.
            } catch (Exception e) {
                e.printStackTrace();
            }
            Meeting storedMeeting = meetingRepository.findById(idMeeting).orElse(null); //Find stored meeting.
            Meeting receivedMeeting = meetingResponseToMeeting(meetingResponse1); //From MeetingResponse to Meeting.
            checkEqualMeetings(storedMeeting, receivedMeeting);
        });
    }

    @Test
    public void getMeetingsByMatter() throws Exception{
        String matter = "Test", pageNumber = "0", pageSize = "25";
        String url = "/meetings/findbymatter?matter=" + matter + "&page" + pageNumber + "&size=" + pageSize;
        String response = getContentFromUrl(url);
        List<MeetingResponse> receivedMeetings = new ObjectMapper().readValue(response, MeetingPaginatedResponse.class).getPages();
        receivedMeetings.forEach(receivedMeeting -> {
            Meeting storedMeeting = meetingRepository.findById(receivedMeeting.getIdMeeting()).orElse(null);
            checkEqualMeetings(storedMeeting, meetingResponseToMeeting(receivedMeeting));
        });
    }

    @Test
    public void registerMeeting() throws Exception {
        String matter = "Test matter";
        String localDateTime = "12-03-1997 00:00";
        String description = "Testing description";
        String keyword = "keywordTest";
        Long merchantId = null;
        Long clientId = null;
        MeetingResponse storedMeeting = null;
        try{
            merchantId = postMerchantAndGetId(null);
            clientId = postClientAndGetId(null);
            storedMeeting = postMeeting(merchantId, clientId); //Post test meeting and get Id.
            //Check if stored data is equal to sent.
            checkSentData(storedMeeting, matter, description, merchantId, clientId, keyword);
        } finally {
            deletePostedData(merchantId, clientId, storedMeeting);
        }
    }

    @Test
    public void deleteMeeting() throws Exception {
        Long merchantId = null;
        Long clientId = null;
        MeetingResponse storedMeeting = null;
        try{
            merchantId = postMerchantAndGetId(null);
            clientId = postClientAndGetId(null);
            storedMeeting = postMeeting(merchantId, clientId); //Post test meeting and get Id.
            //Delete client.
            String url = "/meetings/" + storedMeeting.getIdMeeting();
            deleteContentFromUrl(url);
            //Check if merchant has been deleted.
            Meeting meeting = meetingRepository.findById(storedMeeting.getIdMeeting()).orElse(null);
            assertThat(meeting, equalTo(null)); //The merchants list must be empty.
        } finally {
            deletePostedData(merchantId, clientId, storedMeeting);
        }
    }

    @Test
    public void modifyMatter() throws Exception {
        Long merchantId = null;
        Long clientId = null;
        MeetingResponse storedMeeting = null;
        try{
            merchantId = postMerchantAndGetId(null);
            clientId = postClientAndGetId(null);
            storedMeeting = postMeeting(merchantId, clientId); //Post test meeting and get Id.
            String newMatter = "Modified Matter";
            //Modify meeting's matter.
            String url = "/meetings/" + storedMeeting.getIdMeeting() + "/matter";
            putContentFromUrl(url, "Matter", newMatter);
            //Check if stored matter is equal to sent matter.
            Meeting meeting = meetingRepository.findById(storedMeeting.getIdMeeting()).orElse(null);
            assertThat(meeting.getMatter(), equalTo(newMatter)); //The merchants list must be empty.
        } finally {
            deletePostedData(merchantId, clientId, storedMeeting);
        }
    }

    @Test
    public void modifyDescription() throws Exception {
        Long merchantId = null;
        Long clientId = null;
        MeetingResponse storedMeeting = null;
        try{
            merchantId = postMerchantAndGetId(null);
            clientId = postClientAndGetId(null);
            storedMeeting = postMeeting(merchantId, clientId); //Post test meeting and get Id.
            String newDescription = "Modified description";
            //Modify meeting's matter.
            String url = "/meetings/" + storedMeeting.getIdMeeting() + "/description";
            putContentFromUrl(url, "Description", newDescription);
            //Check if stored matter is equal to sent matter.
            Meeting meeting = meetingRepository.findById(storedMeeting.getIdMeeting()).orElse(null);
            assertThat(meeting.getDescription(), equalTo(newDescription)); //The merchants list must be empty.
        } finally {
            deletePostedData(merchantId, clientId, storedMeeting);
        }
    }

    @Test
    public void modifyDate() throws Exception {
        Long merchantId = null;
        Long clientId = null;
        MeetingResponse storedMeeting = null;
        try{
            merchantId = postMerchantAndGetId(null);
            clientId = postClientAndGetId(null);
            storedMeeting = postMeeting(merchantId, clientId); //Post test meeting and get Id.
            String newDate = "12-03-2012 00:00";
            //Modify meeting's matter.
            String url = "/meetings/" + storedMeeting.getIdMeeting() + "/date";
            putContentFromUrl(url, "Date", newDate);
            //Check if stored matter is equal to sent matter.
            String response = getContentFromUrl("/meetings/" + storedMeeting.getIdMeeting());
            String receivedDate = new ObjectMapper().readValue(response, MeetingResponse.class).getDate();
            assertThat(receivedDate, equalTo("12-03-2012")); //The merchants list must be empty.
        } finally {
            deletePostedData(merchantId, clientId, storedMeeting);
        }
    }

    @Test
    public void addMerchant() throws Exception {
        Long merchantId = null;
        Long clientId = null;
        MeetingResponse storedMeeting = null;
        Long secondMerchantId = null;
        try{
            merchantId = postMerchantAndGetId(null);
            clientId = postClientAndGetId(null);
            storedMeeting = postMeeting(merchantId, clientId); //Post test meeting and get Id.
            //Post another merchant
            secondMerchantId = postMerchantAndGetId("secondEmailExample@example.com");
            postContentFromUrl("/meetings/" + storedMeeting.getIdMeeting() + "/merchants", buildPostSubjectBody(secondMerchantId));
            Meeting meeting = meetingRepository.findById(storedMeeting.getIdMeeting()).orElse(null);
            boolean found = false;
            for (int i = 0; i < meeting.getMerchants().size(); i++) { //Search second merchant
                if(secondMerchantId.compareTo(meeting.getMerchants().get(i).getIdMerchant()) == 0)
                    found = true;
            }
            assertThat(found, equalTo(true)); //Check if second merchant is associated with the meeting.
        } finally {
            deleteMerchantById(secondMerchantId);
            deletePostedData(merchantId, clientId, storedMeeting);
        }
    }

    @Test
    public void addClient() throws Exception {
        Long merchantId = null;
        Long clientId = null;
        MeetingResponse storedMeeting = null;
        Long secondClientId = null;
        try{
            merchantId = postMerchantAndGetId(null);
            clientId = postClientAndGetId(null);
            storedMeeting = postMeeting(merchantId, clientId); //Post test meeting and get Id.
            //Post another Client
            secondClientId = postClientAndGetId("secondEmailExample@example.com");
            postContentFromUrl("/meetings/" + storedMeeting.getIdMeeting() + "/clients", buildPostSubjectBody(secondClientId));
            Meeting meeting = meetingRepository.findById(storedMeeting.getIdMeeting()).orElse(null);
            boolean found = false;
            for (int i = 0; i < meeting.getClients().size(); i++) { //Search second Client
                if(secondClientId.compareTo(meeting.getClients().get(i).getIdClient()) == 0)
                    found = true;
            }
            assertThat(found, equalTo(true)); //Check if second Client is associated with the meeting.
        } finally {
            deleteClientById(secondClientId);
            deletePostedData(merchantId, clientId, storedMeeting);
        }
    }

    @Test
    public void addKeyword() throws Exception {
        Long merchantId = null;
        Long clientId = null;
        MeetingResponse storedMeeting = null;
        try{
            merchantId = postMerchantAndGetId(null);
            clientId = postClientAndGetId(null);
            storedMeeting = postMeeting(merchantId, clientId); //Post test meeting and get Id.
            //Post another keyword
            String secondKeyword = "testkeyword";
            postContentFromUrl("/meetings/" + storedMeeting.getIdMeeting() + "/keywords", buildPostKeywordBody(secondKeyword));
            Meeting meeting = meetingRepository.findById(storedMeeting.getIdMeeting()).orElse(null);
            boolean found = false;
            for (int i = 0; i < meeting.getKeywords().size(); i++) { //Search second Client
                if(meeting.getKeywords().get(i).compareTo(secondKeyword) == 0)
                    found = true;
            }
            assertThat(found, equalTo(true)); //Check if second Client is associated with the meeting.
        } finally {
            deletePostedData(merchantId, clientId, storedMeeting);
        }
    }

    @Test
    public void deleteMerchant() throws Exception {
        Long merchantId = null;
        Long clientId = null;
        MeetingResponse storedMeeting = null;
        try{
            merchantId = postMerchantAndGetId(null);
            clientId = postClientAndGetId(null);
            storedMeeting = postMeeting(merchantId, clientId); //Post test meeting and get Id.
            //Delete client.
            String url = "/meetings/" + storedMeeting.getIdMeeting() + "/merchants/" + merchantId;
            deleteContentFromUrl(url);
            //Check if merchant has been deleted.
            Meeting meeting = meetingRepository.findById(storedMeeting.getIdMeeting()).orElse(null);
            assertThat(meeting.getMerchants().size(), equalTo(0)); //The merchants list must be empty.
        } finally {
            deletePostedData(merchantId, clientId, storedMeeting);
        }
    }

    @Test
    public void deleteClient() throws Exception {
        Long merchantId = null;
        Long clientId = null;
        MeetingResponse storedMeeting = null;
        try{
            merchantId = postMerchantAndGetId(null);
            clientId = postClientAndGetId(null);
            storedMeeting = postMeeting(merchantId, clientId); //Post test meeting and get Id.
            //Delete client.
            String url = "/meetings/" + storedMeeting.getIdMeeting() + "/clients/" + clientId;
            deleteContentFromUrl(url);
            //Check if client has been deleted.
            Meeting meeting = meetingRepository.findById(storedMeeting.getIdMeeting()).orElse(null);
            assertThat(meeting.getClients().size(), equalTo(0)); //The clients list must be empty.
        } finally {
            deletePostedData(merchantId, clientId, storedMeeting);
        }
    }

    @Test
    public void deleteKeyword() throws Exception {
        Long merchantId = null;
        Long clientId = null;
        MeetingResponse storedMeeting = null;
        String keyword = "keywordTest";
        try{
            merchantId = postMerchantAndGetId(null);
            clientId = postClientAndGetId(null);
            storedMeeting = postMeeting(merchantId, clientId); //Post test meeting and get Id.
            //Delete keyword.
            String url = "/meetings/" + storedMeeting.getIdMeeting() + "/keywords/" + keyword;
            deleteContentFromUrl(url);
            //Check if keyword has been deleted.
            Meeting meeting = meetingRepository.findById(storedMeeting.getIdMeeting()).orElse(null);
            assertThat(meeting.getKeywords().size(), equalTo(0)); //The keywords list must be empty.
        } finally {
            deletePostedData(merchantId, clientId, storedMeeting);
        }
    }

    private MeetingResponse getMeetingWithFilesById(Long idMeeting) throws Exception {
        String url = "/meetings/" + idMeeting.toString();
        String content = getContentFromUrl(url);
        return new ObjectMapper().readValue(content, MeetingResponse.class);
    }

    private Meeting meetingResponseToMeeting(MeetingResponse meetingResponse) {
        List<Merchant> merchants = getMerchantsFromMeetingResponse(meetingResponse);
        List<Client> clients = getClientsFromMeetingResponse(meetingResponse);
        List<File> files = getFilesFromMeetingResponse(meetingResponse);
        return new Meeting(meetingResponse.getIdMeeting(), meetingResponse.getMatter(), meetingResponse.getDescription(), null,
                merchants, clients, meetingResponse.getKeywords(), null, meetingResponse.getWordCloud(), files);
    }

    private List<Merchant> getMerchantsFromMeetingResponse(MeetingResponse meetingResponse){
        List<Merchant> merchants = new ArrayList<>();
        List<MerchantResponse> merchantResponseList = meetingResponse.getMerchants().getMerchantResponseList();
        merchantResponseList.forEach(merchantResponse -> merchants.add(new Merchant(
                merchantResponse.getIdMerchant(), merchantResponse.getIdRole(), merchantResponse.getEmail(),
                merchantResponse.getName(), merchantResponse.getPhone(), null, null)));
        return merchants;
    }

    private List<Client> getClientsFromMeetingResponse(MeetingResponse meetingResponse){
        List<Client> clients = new ArrayList<>();
        List<ClientResponse> clientResponseList = meetingResponse.getClients().getClientResponseList();
        clientResponseList.forEach(clientResponse -> clients.add(new Client(clientResponse.getIdClient(), clientResponse.getName(),
                clientResponse.getEmail(), clientResponse.getPhone(), clientResponse.getCompany(), clientResponse.getRemind(), null)));
        return clients;
    }

    private List<File> getFilesFromMeetingResponse(MeetingResponse meetingResponse){
        List<File> files = new ArrayList<>();
        List<MeetingFileResponse> fileResponseList = meetingResponse.getFiles().getMeetingFileResponseList();
        fileResponseList.forEach(fileResponse -> files.add(new File(fileResponse.getIdFile(), fileResponse.getFileName(), fileResponse.getFileType(), null, null)));
        return files;
    }

    //Sends a GET request to the given URL and returns information.
    private String getContentFromUrl(String url) throws Exception{
        MockHttpServletRequestBuilder builder = get(url).header("Authorization", this.jwt);
        MvcResult mvcResult = mvc.perform(builder).andExpect(status().isOk()).andReturn();
        return mvcResult.getResponse().getContentAsString();
    }

    private void postContentFromUrl(String url, String postBody) throws Exception {
        MockHttpServletRequestBuilder builder;
        if(postBody == null)
            builder = post(url).header("Authorization", this.jwt);
        else
            builder = post(url).contentType(MediaType.APPLICATION_JSON).content(postBody).header("Authorization", this.jwt);
        mvc.perform(builder).andExpect(status().isOk()).andReturn();
    }

    //Sends a PUT request to the given URL.
    private void putContentFromUrl(String url, String attribute, String newValue) throws Exception {
        String body = "{\n" +
                "  \"new" + attribute + "\": \"" + newValue + "\"\n" +
                "}";
        MockHttpServletRequestBuilder builder = put(url).contentType(MediaType.APPLICATION_JSON).content(body).header("Authorization", this.jwt);
        mvc.perform(builder).andExpect(status().isOk());
    }

    //Sends a DELETE request to the given URL.
    private void deleteContentFromUrl(String url) throws Exception {
        MockHttpServletRequestBuilder builder = delete(url).header("Authorization", this.jwt);
        mvc.perform(builder).andExpect(status().isOk());
    }

    //Delete posted merchant, client and meeting.
    private void deletePostedData(Long merchantId, Long clientId, MeetingResponse storedMeeting){
        deleteMerchantById(merchantId);
        deleteClientById(clientId);
        deleteMeeting(storedMeeting);
    }

    //This method deletes a merchant with the given ID of merchantId input, before deletes it checks if it exists.
    private void deleteMerchantById(Long merchantId){
        if(merchantId == null)
            return;
        Merchant merchant = merchantRepository.findById(merchantId).orElse(null);
        if(merchant == null)
            return;
        merchantRepository.deleteById(merchantId);
    }

    //This method deletes a client with the given ID of the clientId input, before deletes it checks if it exists.
    private void deleteClientById(Long clientId){
        if(clientId == null)
            return;
        Client client = clientRepository.findById(clientId).orElse(null);
        if(client == null)
            return;
        clientRepository.deleteById(clientId);
    }

    //This method deletes a meeting with the given ID of the meetingResponse input, before deletes it checks if it exists.
    private void deleteMeeting(MeetingResponse meetingResponse){
        if(meetingResponse == null)
            return;
        Meeting meeting = meetingRepository.findById(meetingResponse.getIdMeeting()).orElse(null);
        if(meeting == null)
            return;
        meetingRepository.deleteById(meetingResponse.getIdMeeting());
    }

    private String buildPostSubjectBody(Long subjectId){
        return "{\n" +
                "  \"subjectId\": " + subjectId + "\n" +
                "}";
    }

    private String buildPostKeywordBody(String keyword){
        return "{\n" +
                "  \"keyword\": \"" + keyword + "\"\n" +
                "}";
    }

    //This method post a merchant with a given email, if email is null then we use a default value.
    private Long postMerchantAndGetId(String email) throws Exception {
        //Build post merchant body.
        int idRole = 0;
        String name = "Powered By Isa";
        if(email == null)
            email = getMerchantEmail();
        String phone = "651825712";
        String password = "password";
        String postBody = "{\n" +
                "  \"idRole\": " + idRole + ",\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"phone\": \"" + phone + "\",\n" +
                "  \"password\": \"" + password + "\"\n" +
                "}";
        //Post merchant.
        MockHttpServletRequestBuilder builder = post("/merchants").contentType(MediaType.APPLICATION_JSON).content(postBody).header("Authorization", this.jwt);
        mvc.perform(builder).andExpect(status().isOk());
        //Return its ID if it exists.
        Merchant merchant = merchantRepository.findMerchantByEmail(email);
        if(merchant == null)
            return null;
        return merchant.getIdMerchant();
    }

    //This method post a client with a given email, if email is null then we use a default value.
    private Long postClientAndGetId(String email) throws Exception {
        //Build post client body.
        String name = "Powered by Isa";
        if(email == null)
            email = getClientEmail();
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
        //Post client.
        MockHttpServletRequestBuilder builder = post("/clients").contentType(MediaType.APPLICATION_JSON).content(postBody).header("Authorization", this.jwt);
        mvc.perform(builder).andExpect(status().isOk());
        //Return its ID if it exists.
        Client client = clientRepository.findClientByEmail(email).get(0); //We get the first element of the list(it only has one).
        if(client == null)
            return null;
        return client.getIdClient();
    }

    private MeetingResponse postMeeting(Long merchantId, Long clientId) throws Exception{
        String matter = "Test matter";
        String localDateTime = "12-03-1997 00:00";
        String description = "Testing description";
        String keyword = "keywordTest";
        List<String> keywords = Arrays.asList(keyword);
        String postBody = "{\n" +
                "  \"matter\": \"" + matter + "\",\n" +
                "  \"date\": \"" + localDateTime + "\",\n" +
                "  \"description\": \"" + description + "\",\n" +
                "  \"merchants\": [" + merchantId.toString() + "],\n" +
                "  \"clients\": [" + clientId.toString() + "],\n" +
                "  \"keywords\": [\"" + keywords.get(0) + "\"]\n" +
                "}";
        //Post client.
        MockHttpServletRequestBuilder builder = post("/meetings").contentType(MediaType.APPLICATION_JSON).content(postBody).header("Authorization", this.jwt);
        MvcResult mvcResult = mvc.perform(builder).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        return new ObjectMapper().readValue(response, MeetingResponse.class);
    }

    private String getMerchantEmail(){
        return "isabel.duranchu@alum.uca.es";
    }

    private String getClientEmail(){
        return "isabel.duranchu_client@alum.uca.es";
    }
}