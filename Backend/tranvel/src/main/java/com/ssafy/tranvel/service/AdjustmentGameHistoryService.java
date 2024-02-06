package com.ssafy.tranvel.service;

import com.ssafy.tranvel.dto.AdjustmentGameHistoryDto;
import com.ssafy.tranvel.dto.RoomHistoryDto;
import com.ssafy.tranvel.entity.AdjustmentGameHistory;
import com.ssafy.tranvel.entity.RandomGame;
import com.ssafy.tranvel.entity.RoomHistory;
import com.ssafy.tranvel.entity.User;
import com.ssafy.tranvel.repository.AdjustmentGameHistoryRepository;
import com.ssafy.tranvel.repository.RandomGameRepository;
import com.ssafy.tranvel.repository.RoomHistoryRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class AdjustmentGameHistoryService {

    private final AdjustmentGameHistoryRepository adjustmentGameHistoryRepository;
    private final RoomHistoryRepository roomHistoryRepository;
    private final RandomGameRepository randomGameRepository;

    public AdjustmentGameHistory createRoomHistory(AdjustmentGameHistoryDto adjustmentGameHistoryDto) {
        RoomHistory roomHistory = roomHistoryRepository.findById(adjustmentGameHistoryDto.getRoomId()).get();
        RandomGame randomGame = randomGameRepository.findById(adjustmentGameHistoryDto.getMiniGameCodeId()).get();
//        List gameplayers =
        AdjustmentGameHistory adjustmentGameHistory = AdjustmentGameHistory.builder()
                .roomHistory(roomHistory)
                .miniGameCode(randomGame)
                .targetUser(null)
                .dateTime(adjustmentGameHistoryDto.getDateTime())
//                .gamePlayers() // dk
                .price(adjustmentGameHistoryDto.getPrice())
                .image(adjustmentGameHistoryDto.getImage())
                .category(adjustmentGameHistoryDto.getCategory())
                .detail(adjustmentGameHistoryDto.getDetail())
                .build();
        adjustmentGameHistoryRepository.save(adjustmentGameHistory);
        return adjustmentGameHistory;
    }
}
