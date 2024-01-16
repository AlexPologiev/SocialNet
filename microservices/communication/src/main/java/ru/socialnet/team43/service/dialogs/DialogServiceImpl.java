package ru.socialnet.team43.service.dialogs;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ru.socialnet.team43.client.DatabaseClient;
import ru.socialnet.team43.dto.dialogs.*;

@Service
@AllArgsConstructor
public class DialogServiceImpl implements DialogService {

    private final DatabaseClient databaseClient;

    @Override
    public  ResponseEntity<Page<DialogDto>> getDialogs(String email, Pageable page) {
        return ResponseEntity.ok(databaseClient.getDialogs(email, page));
    }

    @Override
    public ResponseEntity<UnreadCountDto> getCountUnreadDialogs(String email) {
        UnreadCountDto dto = new UnreadCountDto(databaseClient.getCountUnreadDialogs(email));
        return ResponseEntity.ok(dto);
    }
}
