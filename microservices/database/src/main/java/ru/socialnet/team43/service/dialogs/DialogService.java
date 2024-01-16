package ru.socialnet.team43.service.dialogs;

import jooq.db.tables.records.DialogRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.socialnet.team43.dto.dialogs.DialogDto;

import java.util.List;

public interface DialogService {
    Page<DialogDto> getDialogs(String email, Pageable page);

    Integer getCountUnreadDialogs(String email);
}
