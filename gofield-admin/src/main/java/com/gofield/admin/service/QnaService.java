package com.gofield.admin.service;

import com.gofield.admin.dto.BannerDto;
import com.gofield.admin.dto.BannerListDto;
import com.gofield.admin.dto.QnaDto;
import com.gofield.admin.dto.QnaListDto;
import com.gofield.common.model.Constants;
import com.gofield.domain.rds.domain.banner.Banner;
import com.gofield.domain.rds.domain.banner.BannerRepository;
import com.gofield.domain.rds.domain.item.ItemQna;
import com.gofield.domain.rds.domain.item.ItemQnaRepository;
import com.gofield.infrastructure.s3.infra.S3FileStorageClient;
import com.gofield.infrastructure.s3.model.enums.FileType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QnaService {
    private final ItemQnaRepository itemQnaRepository;

    @Transactional(readOnly = true)
    public QnaListDto getQnaList(Pageable pageable) {
       Page<ItemQna> page =  itemQnaRepository.findAllPaging(pageable);
       List<QnaDto> qnaDtoList = QnaDto.of(page.getContent());
       return QnaListDto.of(qnaDtoList, page);
    }

    @Transactional
    public void updateQna(QnaDto qnaDto){
        ItemQna qna = itemQnaRepository.findByQnaId(qnaDto.getId());
        qna.updateReplyAnswer(qnaDto.getAnswer());
    }

    @Transactional(readOnly = true)
    public QnaDto getQna(Long id){
        return QnaDto.of(itemQnaRepository.findByQnaId(id));
    }

    @Transactional
    public void delete(Long id){
        ItemQna itemQna = itemQnaRepository.findByQnaId(id);
        itemQna.delete();
    }
}