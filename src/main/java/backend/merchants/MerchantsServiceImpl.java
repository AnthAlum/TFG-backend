package backend.merchants;

import backend.api.meetings.MeetingSimplifiedResponse;
import backend.api.merchants.*;
import backend.api.others.PaginationInfo;
import backend.meetings.MeetingMapper;
import backend.meetings.MeetingRepository;
import backend.meetings.MeetingService;
import backend.utility.AlreadyRegisteredException;
import backend.utility.BadPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MerchantsServiceImpl implements MerchantsService {
    private MerchantRepository merchantRepository;
    private MerchantMapper merchantMapper;
    private MeetingRepository meetingRepository;
    private MeetingMapper meetingMapper;
    private MeetingService meetingService;
    private PasswordEncoder passwordEncoder;

    public MerchantRepository getMerchantRepository() {
        return merchantRepository;
    }

    @Autowired
    public void setMerchantRepository(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public MerchantMapper getMerchantMapper() {
        return merchantMapper;
    }

    @Autowired
    public void setMerchantMapper(MerchantMapper merchantMapper) {
        this.merchantMapper = merchantMapper;
    }

    public MeetingRepository getMeetingRepository() {
        return meetingRepository;
    }

    @Autowired
    public void setMeetingRepository(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public MeetingService getMeetingService() {
        return meetingService;
    }

    @Autowired
    public void setMeetingService(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    public MeetingMapper getMeetingMapper() {
        return meetingMapper;
    }

    @Autowired
    public void setMeetingMapper(MeetingMapper meetingMapper) {
        this.meetingMapper = meetingMapper;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public MerchantResponse getMerchantById(Long idMerchant) {
        Merchant merchant = merchantRepository.findById(idMerchant).orElse(null);
        if(merchant == null)
            return null;
        MerchantResponse merchantResponse = merchantMapper.merchantToMerchantResponse(merchant);
        merchant.getMeetings().forEach(meeting -> {
            MeetingSimplifiedResponse meetingSimplifiedResponse = meetingMapper.meetingToMeetingSimplifiedResponse(meeting);
            meetingSimplifiedResponse.setMerchants((long)meeting.getMerchants().size());
            meetingSimplifiedResponse.setClients((long)meeting.getClients().size());
            merchantResponse.addMeetingSimplifiedResponse(meetingSimplifiedResponse);
        });
        return merchantResponse;
    }

    @Override
    @Transactional
    public MerchantResponse getMerchantByEmail(String email) {
        Merchant merchant = merchantRepository.findMerchantByEmail(email);
        return merchantMapper.merchantToMerchantResponse(merchant);
    }

    @Override
    @Transactional
    public void registerMerchant(MerchantRegistrationRequest merchantRegistrationRequest) throws AlreadyRegisteredException {
        Merchant merchant = merchantRepository.findMerchantByEmail(merchantRegistrationRequest.getEmail());
        if(merchant != null)
            throw new AlreadyRegisteredException("Already registered");
        Merchant newMerchant = merchantMapper.merchantRegistrationRequestToMerchant(merchantRegistrationRequest);
        newMerchant.setPassword(passwordEncoder.encode(merchantRegistrationRequest.getPassword()));
        merchantRepository.save(newMerchant);
    }

    @Override
    @Transactional
    public MerchantPaginatedResponse getMerchants(Integer pageNumber, Integer size) {
        Page<Merchant> merchantPage = merchantRepository.searchMerchants(PageRequest.of(pageNumber, size));
        return buildResponse(merchantPage, (int)merchantRepository.count());
    }


    @Override
    @Transactional
    public MerchantsSimplifiedListResponse getMerchantsSimplified() {
        MerchantsSimplifiedListResponse merchantsSimplifiedListResponse = new MerchantsSimplifiedListResponse();
        List<String[]> merchantsSimplified = merchantRepository.getMerchantsSimplified();
        merchantsSimplified.forEach(merchant -> {

            MerchantSimplifiedResponse merchantSimplifiedResponse = new MerchantSimplifiedResponse(
                    Long.parseLong(merchant[0]),
                    merchant[1],
                    merchant[2]
            );
            merchantsSimplifiedListResponse.addResponse(merchantSimplifiedResponse);
        });
        return merchantsSimplifiedListResponse;
    }

    @Override
    @Transactional
    public MerchantPaginatedResponse getMerchantsByIdRole(Integer idRole, Integer pageNumber, Integer size) {
        Page<Merchant> merchantPage = merchantRepository.findByIdRole(idRole, PageRequest.of(pageNumber, size));
        return buildResponse(merchantPage, (int) merchantRepository.countByIdRole(idRole));
    }

    @Override
    @Transactional
    public MerchantPaginatedResponse getMerchantsByEmail(String email, Integer pageNumber, Integer size) {
        Page<Merchant> merchantPage = merchantRepository.findByEmailContains(email, PageRequest.of(pageNumber, size));
        return buildResponse(merchantPage, (int) merchantRepository.countByEmailContains(email));
    }

    @Override
    @Transactional
    public MerchantPaginatedResponse getMerchantsByName(String name, Integer pageNumber, Integer size) {
        Page<Merchant> merchantPage = merchantRepository.findByNameContains(name, PageRequest.of(pageNumber, size));
        return buildResponse(merchantPage, (int) merchantRepository.countByNameContains(name));
    }

    @Override
    @Transactional
    public MerchantPaginatedResponse getMerchantsByPhone(String phone, Integer pageNumber, Integer size) {
        Page<Merchant> merchantPage = merchantRepository.findByPhoneContains(phone, PageRequest.of(pageNumber, size));
        return buildResponse(merchantPage, (int) merchantRepository.countByPhoneContains(phone));
    }

    @Override
    public MerchantPaginatedResponse buildResponse(Page<Merchant> merchantPage, int totalElements){
        if(merchantPage == null) {
            return null;
        }
        MerchantListResponse merchantListResponse = new MerchantListResponse(); //Crear el response de la lista vacia de comerciales
        merchantPage.forEach(merchant -> { //1: Por cada comercial
            MerchantResponse merchantResponse = merchantMapper.merchantToMerchantResponse(merchant); // obtenemos el merchantResponse
            merchantListResponse.addMerchantResponse(merchantResponse);
        });
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setTotalElements(totalElements);
        paginationInfo.setTotalPages(merchantPage.getTotalPages());

        MerchantPaginatedResponse merchantPaginatedResponse = new MerchantPaginatedResponse();
        merchantPaginatedResponse.setPages(merchantListResponse.getMerchantResponseList());
        merchantPaginatedResponse.setPaginationInfo(paginationInfo);
        return merchantPaginatedResponse;
    }

    @Override
    @Transactional
    public void deleteMerchant(Long idMerchant){
        Merchant merchant = merchantRepository.findById(idMerchant).orElse(null);
        if(merchant != null){
            merchantRepository.delete(merchant);
        }
    }

    @Override
    @Transactional
    public void modifyMerchantName(MerchantNameChangeRequest merchantNameChangeRequest, Long idMerchant) {
        Merchant merchant = merchantRepository.findById(idMerchant).orElse(null);
        if(merchant != null){
            merchant.setName(merchantNameChangeRequest.getNewName());
            merchantRepository.save(merchant);
        }
    }

    @Override
    @Transactional
    public void modifyMerchantEmail(MerchantEmailChangeRequest merchantEmailChangeRequest, Long idMerchant) throws AlreadyRegisteredException{
        Merchant merchant = merchantRepository.findMerchantByEmail(merchantEmailChangeRequest.getNewEmaiL());
        if(merchant != null)
            throw new AlreadyRegisteredException("Already registered");
        merchant = merchantRepository.findById(idMerchant).orElse(null);
        if(merchant != null){
            merchant.setEmail(merchantEmailChangeRequest.getNewEmaiL());
            merchantRepository.save(merchant);
        }
    }

    @Override
    @Transactional
    public void modifyMerchantPhone(MerchantPhoneChangeRequest merchantPhoneChangeRequest, Long idMerchant) {
        Merchant merchant = merchantRepository.findById(idMerchant).orElse(null);
        if(merchant != null){
            merchant.setPhone(merchantPhoneChangeRequest.getNewPhone());
            merchantRepository.save(merchant);
        }
    }

    @Override
    @Transactional
    public void modifyMerchantRole(MerchantRoleChangeRequest merchantRoleChangeRequest, Long idMerchant) {
        Merchant merchant = merchantRepository.findById(idMerchant).orElse(null);
        if(merchant != null){
            merchant.setIdRole(merchantRoleChangeRequest.getNewRole());
            merchantRepository.save(merchant);
        }
    }

    @Override
    @Transactional
    public void modifyMerchantPassword(MerchantPasswordChangeRequest merchantPasswordChangeRequest, Long idMerchant) throws BadPasswordException {
        Merchant merchant = merchantRepository.findById(idMerchant).orElse(null);
        if(merchant != null){
            if(passwordEncoder.matches(merchantPasswordChangeRequest.getPassword(), merchant.getPassword())){
                merchant.setPassword(passwordEncoder.encode(merchantPasswordChangeRequest.getNewPassword()));
                merchantRepository.save(merchant);
            } else{
                throw new BadPasswordException("Wrong password");
            }
        }
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return merchantRepository.findMerchantByEmail(email);
    }
}
