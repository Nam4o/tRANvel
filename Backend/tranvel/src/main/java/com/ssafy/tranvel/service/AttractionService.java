package com.ssafy.tranvel.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.tranvel.dto.AttractionBaseDto;
import com.ssafy.tranvel.entity.AttractionList;
import com.ssafy.tranvel.entity.JoinUser;
import com.ssafy.tranvel.entity.RoomHistory;
import com.ssafy.tranvel.entity.User;
import com.ssafy.tranvel.repository.AttractionRepository;
import com.ssafy.tranvel.repository.RoomHistoryRepository;
import com.ssafy.tranvel.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
@Getter @Setter
@RequiredArgsConstructor
public class AttractionService {

    private final AttractionRepository attractionRepository;
    private final RoomHistoryRepository roomHistoryRepository;
    private final UserRepository userRepository;

    public Object loadAttractionList() throws UnsupportedEncodingException {
        String serviceKey = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FmeS5qZW9uZ2h3NG5AZ21haWwuY29tIiwiYXV0aCI6IlVTRVIiLCJleHAiOjE3MDcxOTI1MDh9.I8rHsyEpiKW0huPaFUsi8sym0CKx-4dx_JdQolF8NeTwe8ynUpEVBfKQz0k9y8XXooICidTVVp-8CL-Kgsnyeg";
        String apiUrl = "http://api.data.go.kr/openapi/tn_pubr_public_trrsrt_api";
        String encodedKey = URLEncoder.encode(serviceKey, "UTF-8");

        WebClient webClient = WebClient.create(apiUrl);

        try {
            Object response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("serviceKey", encodedKey)
                            .queryParam("type", "Json")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            System.out.println(response);
            return response;
            // 여기서 응답을 사용하거나 처리합니다.

        } catch (WebClientResponseException e) {
            // WebClientResponseException에서 상세한 오류 정보를 얻습니다.
            System.err.println("WebClient error: " + e.getRawStatusCode() + " " + e.getStatusText());
            return e;
        }

    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Object> readDataFromJsonFile(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), objectMapper.getTypeFactory().constructCollectionType(List.class, Object.class));
    }

    public void saveDataFromJsonFile(String filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            AttractionBaseDto[] attractionArray = objectMapper.readValue(new File(filePath), AttractionBaseDto[].class);
//            List<AttractionList> attractionList = objectMapper.readValue(new File(filePath), new TypeReference<List<AttractionList>>(){});
//            attractionRepository.saveAll(attractionList);
//            System.out.println("데이터 저장이 완료되었습니다.");
//            List<Object> dataList = readDataFromJsonFile(filePath);
            for (int idx = 0; idx < attractionArray.length; idx ++) {
                AttractionList attractionList = AttractionList.builder()
                        .city(attractionArray[idx].getProvidingAgencyName())
                        .description(attractionArray[idx].getIntroduction())
                        .latitude(attractionArray[idx].getLatitude())
                        .longitude(attractionArray[idx].getLongitude())
                        .name(attractionArray[idx].getAttractionName())
                        .build();
                attractionRepository.save(attractionList);
            }
            System.out.println(attractionArray[0].getProvidingAgencyName());
//            attractionRepository.saveAll(dataList);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    // 'roomId' 받아서, 방의 인원 중 한명을 게임 플레이어로 선정해서 '닉네임 반환'
    public String getAttractionGamePlayer(Long roomId) {
        System.out.println("AttractionService.getAttractionGamePlayer");
        RoomHistory roomHistory = roomHistoryRepository.findById(roomId).get();
        List<JoinUser> joinUsers = roomHistory.getJoinUser();
        List<Long> joinUserUserIds = new ArrayList<>();

        // 유효한 유저인지 확인
        for (JoinUser joinUser : joinUsers) {
            Optional<User> userOptional = userRepository.findById(joinUser.getUserId());
            if (!userOptional.isPresent()) {
                continue;
            }
            Long userId = userOptional.get().getId();
            joinUserUserIds.add(userId);
        }

        Random random = new Random();
        int randomIndex = random.nextInt(joinUserUserIds.size());
        Long selectedJoinUserId = joinUserUserIds.get(randomIndex);
        User user = userRepository.findById(selectedJoinUserId).get();
        System.out.println("AttractionService - SelecetedUser's Nickname = " + user.getNickName());
        return user.getNickName();
    }


}
