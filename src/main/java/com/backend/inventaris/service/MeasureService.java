package com.backend.inventaris.service;

import com.backend.inventaris.config.OtherConfig;
import com.backend.inventaris.core.IService;
import com.backend.inventaris.dto.response.FindAllDataMasterDTO;
import com.backend.inventaris.dto.response.FindMeasureDTO;
import com.backend.inventaris.dto.response.FindProductDTO;
import com.backend.inventaris.dto.validation.ValDataMasterDTO;
import com.backend.inventaris.enumm.TypeTransaction;
import com.backend.inventaris.handler.GlobalResponse;
import com.backend.inventaris.model.Measure;
import com.backend.inventaris.repo.MeasureRepo;
import com.backend.inventaris.security.RequestCapture;
import com.backend.inventaris.util.GlobalFunction;
import com.backend.inventaris.util.LoggingFile;
import com.backend.inventaris.util.TransformPagination;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *  Modul Code - 03
 *  Platform Code - M
 */

@Service
@Transactional
public class MeasureService implements IService<Measure> {
    @Autowired
    private MeasureRepo measureRepo;
    
    @Autowired
    private TransformPagination transformPagination;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<Object> create(Measure measure, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        try {
            measure.setName(measure.getName());
            measure.setCreatedBy(Long.valueOf(mapToken.get("userId").toString()));
            measureRepo.save(measure);
        }catch (Exception e) {
            LoggingFile.logException("Measure Service","Create failed"+ RequestCapture.allRequest(request),e, OtherConfig.getEnableLog());
            return GlobalResponse.failedToSave("M03CC001",request);
        }
        return GlobalResponse.savedSuccessfully(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Measure measure, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);

        try {
            Optional<Measure> measureOptional = measureRepo.findById(id);
            if (!measureOptional.isPresent()){
                return GlobalResponse.dataNotFound("M03CC011",request);
            }

            Measure nextMeasure = measureOptional.get();
            nextMeasure.setName(measure.getName());
            nextMeasure.setUpdatedBy(Long.valueOf(mapToken.get("userId").toString()));
            measureRepo.save(nextMeasure);
        }catch (Exception e){
            LoggingFile.logException("Measure Service","Update failed"+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.failedToUpdate("M03CC012",request);
        }
        return GlobalResponse.updateSuccessfully(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);

        try {
            Optional<Measure> measureOptional = measureRepo.findById(id);
            if (!measureOptional.isPresent()){
                return GlobalResponse.dataNotFound("M03CC021",request);
            }

            Measure nextMeasure = measureOptional.get();
            nextMeasure.setDeleted(true);
            nextMeasure.setUpdatedBy(Long.valueOf(mapToken.get("userId").toString()));
            measureRepo.save(nextMeasure);
        }catch (Exception e){
            LoggingFile.logException("Measure Service","Deleted failed"+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.deletedFailed("M03CC022",request);
        }
        return GlobalResponse.deletedSuccessfully(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Measure> page = null;
        List<Measure> list = null;
        page = measureRepo.findAllByIsDeleted(false,pageable);
        list = page.getContent();
        List<FindAllDataMasterDTO> lt = convertToFindAllDTO(list);
        return GlobalResponse.dataWasFound(transformPagination.transformPagination(lt,page,null,null),
                request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, TypeTransaction typeTransaction, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        Optional<Measure> optionalMeasure = null;
        try {
            optionalMeasure = measureRepo.findById(id);
            if (!optionalMeasure.isPresent()){
                return GlobalResponse.dataNotFound("M03CC051",request);
            }
        }catch (Exception e){
            LoggingFile.logException("Measure Service","Get by Id error" +RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.error("M03CC052",request);
        }
        return GlobalResponse.dataWasFound(modelMapper.map(optionalMeasure.get(), FindMeasureDTO.class), request);
    }

    private List<FindAllDataMasterDTO> convertToFindAllDTO(List<Measure> measures) {
        List<FindAllDataMasterDTO> lt = new ArrayList<>();
        for (Measure measure : measures) {
            FindAllDataMasterDTO findAllDataMasterDTO = new FindAllDataMasterDTO();
            findAllDataMasterDTO.setId(measure.getId());
            findAllDataMasterDTO.setName(measure.getName());
            findAllDataMasterDTO.setDeleted(measure.getDeleted());
            lt.add(findAllDataMasterDTO);
        }
        return lt;
    }

    public Measure converToEntity(ValDataMasterDTO valDataMasterDTO) {
        Measure measure = modelMapper.map(valDataMasterDTO, Measure.class);
        return measure;
    }
}
