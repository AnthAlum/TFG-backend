package backend.meetings;

import backend.api.clients.ClientResponse;
import backend.api.meetings.*;
import backend.api.merchants.MerchantResponse;
import backend.api.others.PaginationInfo;
import backend.clients.Client;
import backend.clients.ClientRepository;
import backend.clients.ClientService;
import backend.merchants.Merchant;
import backend.merchants.MerchantRepository;
import backend.merchants.MerchantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MeetingServiceImpl implements MeetingService{
    private MeetingRepository meetingRepository;
    private MeetingMapper meetingMapper;
    private MerchantRepository merchantRepository;
    private MerchantsService merchantService;
    private ClientRepository clientRepository;
    private ClientService clientService;

    public MeetingRepository getMeetingRepository() {
        return meetingRepository;
    }

    @Autowired
    public void setMeetingRepository(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public MeetingMapper getMeetingMapper() {
        return meetingMapper;
    }

    @Autowired
    public void setMeetingMapper(MeetingMapper meetingMapper) {
        this.meetingMapper = meetingMapper;
    }

    public MerchantRepository getMerchantRepository() {
        return merchantRepository;
    }

    @Autowired
    public void setMerchantRepository(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public MerchantsService getMerchantService() {
        return merchantService;
    }

    @Autowired
    public void setMerchantService(MerchantsService merchantService) {
        this.merchantService = merchantService;
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientService getClientService() {
        return clientService;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    @Transactional
    public MeetingResponse getMeetingById(Long idMeeting) {
        Meeting meeting = meetingRepository.findById(idMeeting).orElse(null);
        MeetingResponse meetingResponse = meetingMapper.meetingToMeetingResponse(meeting);
        meetingResponse.setDate(getDate(meeting.getDate())); //Assign date
        meetingResponse.setTime(getTime(meeting.getDate()));
        meeting.getMerchants().forEach(merchant -> {
            MerchantResponse merchantResponse = merchantService.getMerchantById(merchant.getIdMerchant());
            meetingResponse.addMerchantResponse(merchantResponse);
        });
        meeting.getClients().forEach(client -> {
            ClientResponse clientResponse = clientService.getClientById(client.getIdClient());
            meetingResponse.addClientResponse(clientResponse);
        });
        return meetingResponse;
    }

    @Override
    @Transactional
    public MeetingPaginatedResponse getMeetings(Integer pageNumber, Integer pageSize) {
        Page<Meeting> meetingPage = meetingRepository.searchMeetings(PageRequest.of(pageNumber, pageSize));
        return buildResponse(meetingPage, (int)meetingRepository.count());
    }

    @Override
    @Transactional
    public MeetingPaginatedResponse getMeetingsByMatter(String matter, Integer pageNumber, Integer pageSize) {
        Page<Meeting> meetingPage = meetingRepository.findByMatterContains(matter, PageRequest.of(pageNumber, pageSize));
        return buildResponse(meetingPage, (int)meetingRepository.countByMatterContains(matter));
    }

    private MeetingPaginatedResponse buildResponse(Page<Meeting> meetingPage, int totalElements){
        if(meetingPage == null) {
            return null;
        }
        MeetingListResponse meetingListResponse = new MeetingListResponse();
        meetingPage.forEach(meeting -> { //1: For each meeting
            MeetingResponse meetingResponse = meetingMapper.meetingToMeetingResponse(meeting); //  create the meetingResponse
            meetingResponse.setDate(getDate(meeting.getDate())); //Assign date
            meetingResponse.setTime(getTime(meeting.getDate()));
            meeting.getMerchants().forEach(merchant ->{  // 1.1: For each merchant asociated to the given meeting
                MerchantResponse merchantResponse = merchantService.getMerchantById(merchant.getIdMerchant()); // create the merchantResponse
                meetingResponse.addMerchantResponse(merchantResponse); // and add merchantResponse to the list.
            });
            // 1.2: Same for clients.
            meeting.getClients().forEach(client -> {
                ClientResponse clientResponse = clientService.getClientById(client.getIdClient());
                meetingResponse.addClientResponse(clientResponse);
            });
            meetingListResponse.addMeetingResponse(meetingResponse); //Add the meetingResponse to the list of meetingResponses.
        });
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setTotalPages(meetingPage.getTotalPages());
        paginationInfo.setTotalElements(totalElements);

        MeetingPaginatedResponse meetingPaginatedResponse = new MeetingPaginatedResponse();
        meetingPaginatedResponse.setPages(meetingListResponse.getMeetingResponseList());
        meetingPaginatedResponse.setPaginationInfo(paginationInfo);
        return meetingPaginatedResponse;
    }

    private String getDate(LocalDateTime localDateTime){
        String date = localDateTime.toString(); // Inverse from 'yyyy/mm/dd' to 'dd/mm/yyyy'
        String year = date.substring(0, 4);
        String month = date.substring(5, 7) + '-';
        String day = date.substring(8, 10) + '-';
        return day + month + year;
    }

    private String getTime(LocalDateTime localDateTime){
        return localDateTime.toString().substring(11, 16); // Get time from localDateTime string.
    }

    @Override
    @Transactional
    public void registerMeeting(MeetingRegistrationRequest meetingRegistrationRequest) {
        Meeting meeting = meetingMapper.meetingRegistrationRequestToMeeting(meetingRegistrationRequest); //Obtenemos el meeting.
        meetingRegistrationRequest.getMerchants().forEach(idMerchant -> // 1. Con cada idMerchant del registrationRequest
                meeting.addMerchant(merchantRepository.findById(idMerchant).orElse(null)) //    buscamos el merchant y lo guardamos en la lista.
        );
        // Igual con los clientes.
        meetingRegistrationRequest.getClients().forEach(idClient ->
                meeting.addClient(clientRepository.findById(idClient).orElse(null))
        );
        meeting.setDate(stringToLocalDateTime(meetingRegistrationRequest.getLocalDateTime()));
        meeting.setWordCloud(generateWordCloud(meeting.getDescription(), meeting.getKeywords()));
        meetingRepository.save(meeting);
        meeting.getMerchants().forEach(merchant -> merchant.addMeeting(meeting)); // Guardamos el nuevo meeting en las listas de los merchants.
        meeting.getClients().forEach(client -> client.addMeeting(meeting)); // Igual con los clientes.
    }

    private LocalDateTime stringToLocalDateTime(String dateTime){
        /*
        12-03-2016 12:12
        * String str = "2016-03-04 11:30";
        * DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        * LocalDateTime dateTime = LocalDateTime.parse(str, formatter); */
        String day = dateTime.substring(0, 2);
        String month = dateTime.substring(3, 6);
        String year = dateTime.substring(6, 10) + '-';
        String modifiedDate = dateTime.replace(dateTime.substring(0, 10), year + month + day);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(modifiedDate, formatter);
    }

    private List<String> generateWordCloud(String description, List<String> keywords){
        Map<String, Integer> wordCounter = new HashMap<>(); // Map for store words and frequency.
        List<String> descriptionWords = Arrays.asList(description.split("[\\s,\\.]|[A-z]{0,3}")); //Slip words by spaces and commas.
        for(int i = 0; i < descriptionWords.size(); i++){ //Count description words in wordCounter.
            String word = descriptionWords.get(i).toLowerCase(Locale.ROOT);
            if(wordCounter.containsKey(word))
                wordCounter.put(word, wordCounter.get(word) + 1);
            else
                wordCounter.put(word, 1);
        }
        keywords.forEach(keyword -> { //Count keywords in wordCounter.
            String toLowerCase = keyword.toLowerCase(Locale.ROOT);
            if(wordCounter.containsKey(toLowerCase))
                wordCounter.put(toLowerCase, wordCounter.get(toLowerCase) + 1);
            else
                wordCounter.put(toLowerCase, 1);
        });
        List<Map.Entry<String, Integer>> wordsSorted = wordCounter.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue())).limit(10).collect(Collectors.toList());//Sort by Integer values.
        ArrayList<String> wordCloud = new ArrayList<>();
        wordsSorted.forEach(entry -> wordCloud.add(entry.getKey())); // Store the 10 words with higher frequency.
        return wordCloud;
    }

    @Override
    @Transactional
    public void modifyMeetingDate(Long meetingId, MeetingDateChangeRequest meetingDateChangeRequest) {
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
        if(meeting != null){
            meeting.setDate(stringToLocalDateTime(meetingDateChangeRequest.getLocalDateTime()));
            meetingRepository.save(meeting);
        }
    }

    @Override
    @Transactional
    public void modifyMeetingMatter(Long meetingId, MeetingMatterChangeRequest meetingMatterChangeRequest) {
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
        if(meeting != null){
            meeting.setMatter(meetingMatterChangeRequest.getMatter());
            meetingRepository.save(meeting);
        }
    }

    @Override
    @Transactional
    public void modifyMeetingDescription(Long meetingId, MeetingDescriptionChangeRequest meetingDescriptionChangeRequest) {
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
        if(meeting != null){
            meeting.setDescription(meetingDescriptionChangeRequest.getNewDescription());
            meeting.setWordCloud(generateWordCloud(meeting.getDescription(), meeting.getKeywords()));
            meetingRepository.save(meeting);
        }
    }

    @Override
    @Transactional
    public void addMeetingMerchant(Long meetingId, MeetingSubjectChangeRequest meetingSubjectChangeRequest) {
        Long merchantId = meetingSubjectChangeRequest.getSubjectId();
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
        Merchant merchant = merchantRepository.findById(merchantId).orElse(null);
        merchant.addMeeting(meeting);
        meeting.addMerchant(merchant);
        merchantRepository.save(merchant);
        meetingRepository.save(meeting);
    }

    @Override
    @Transactional
    public void addMeetingClient(Long meetingId, MeetingSubjectChangeRequest meetingSubjectChangeRequest) {
        Long clientId = meetingSubjectChangeRequest.getSubjectId();
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
        Client client = clientRepository.findById(clientId).orElse(null);
        client.addMeeting(meeting);
        meeting.addClient(client);
        clientRepository.save(client);
        meetingRepository.save(meeting);
    }

    @Override
    public void addMeetingKeyword(Long meetingId, MeetingKeywordChangeRequest meetingKeywordChangeRequest) {
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
        meeting.addKeyword(meetingKeywordChangeRequest.getKeyword());
        meeting.setWordCloud(generateWordCloud(meeting.getDescription(), meeting.getKeywords()));
        meetingRepository.save(meeting);
    }

    @Override
    @Transactional
    public void deleteMeetingMerchant(Long meetingId, Long merchantId) {
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
        Merchant merchant = merchantRepository.findById(merchantId).orElse(null);
        merchant.deleteMeeting(meeting);
        meeting.removeMerchant(merchant);
        merchantRepository.save(merchant);
        meetingRepository.save(meeting);
    }

    @Override
    @Transactional
    public void deleteMeetingClient(Long meetingId, Long clientId) {
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
        Client client = clientRepository.findById(clientId).orElse(null);
        client.deleteMeeting(meeting);
        meeting.removeClient(client);
        clientRepository.save(client);
        meetingRepository.save(meeting);
    }

    @Override
    @Transactional
    public void deleteMeetingKeyword(Long meetingId, String keyword) {
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
        meeting.removeKeyword(keyword);
        meeting.setWordCloud(generateWordCloud(meeting.getDescription(), meeting.getKeywords()));
        meetingRepository.save(meeting);
    }

    @Override
    @Transactional
    public void deleteMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
        meeting.getMerchants().forEach(merchant -> { // Por cada merchant relacionado con el meeting
            merchant.deleteMeeting(meeting);    // quitamos el meeting a eliminar.
            merchantRepository.save(merchant);  //   y actualizamos la lista de los meetings.
        });
        meeting.getClients().forEach(client -> { // Igual en los clientes.
            client.deleteMeeting(meeting);
            clientRepository.save(client);
        });
        meetingRepository.delete(meeting);
    }

}
