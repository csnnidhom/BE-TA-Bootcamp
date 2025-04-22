package com.backend.inventaris.service;

import com.backend.inventaris.config.OtherConfig;
import com.backend.inventaris.core.IService;
import com.backend.inventaris.dto.FindAllDTO;
import com.backend.inventaris.dto.validation.ValDataMasterDTO;
import com.backend.inventaris.handler.GlobalResponse;
import com.backend.inventaris.model.Periode;
import com.backend.inventaris.repo.PeriodeRepo;
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
 *  Modul Code - 02
 *  Platform Code - P
 */

@Service
@Transactional
public class PeriodeService implements IService<Periode> {

    @Autowired
    private PeriodeRepo periodeRepo;

    @Autowired
    private TransformPagination transformPagination;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<Object> create(Periode periode, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        try {
            periode.setName(periode.getName());
            periode.setCreatedBy(Long.valueOf(mapToken.get("userId").toString()));
            periodeRepo.save(periode);
        }catch (Exception e) {
            LoggingFile.logException("Periode Service","Create failed"+ RequestCapture.allRequest(request),e, OtherConfig.getEnableLog());
            return GlobalResponse.failedToSave("P02CC001",request);
        }
        return GlobalResponse.savedSuccessfully(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Periode periode, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);

        try {
            Optional<Periode> periodeOptional = periodeRepo.findById(id);
            if (!periodeOptional.isPresent()){
                return GlobalResponse.dataNotFound("P02CC011",request);
            }

            Periode nextPeriode = periodeOptional.get();
            nextPeriode.setName(periode.getName());
            nextPeriode.setUpdatedBy(Long.valueOf(mapToken.get("userId").toString()));
            periodeRepo.save(nextPeriode);
        }catch (Exception e){
            LoggingFile.logException("Periode Service","Update failed"+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.updateFailed("P02CC012",request);
        }
        return GlobalResponse.updateSuccessfully(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);

        try {
            Optional<Periode> periodeOptional = periodeRepo.findById(id);
            if (!periodeOptional.isPresent()){
                return GlobalResponse.dataNotFound("P02CC021",request);
            }

            Periode nextPeriode = periodeOptional.get();
            nextPeriode.setDeleted(true);
            nextPeriode.setUpdatedBy(Long.valueOf(mapToken.get("userId").toString()));
            periodeRepo.save(nextPeriode);
        }catch (Exception e){
            LoggingFile.logException("Warehouse Service","Deleted failed"+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.deletedFailed("P02CC022",request);
        }
        return GlobalResponse.deletedSuccessfully(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Periode> page = null;
        List<Periode> list = null;
        page = periodeRepo.findAllByIsDeleted(false,pageable);
        list = page.getContent();
        List<FindAllDTO> lt = convertToFindAllDTO(list);
        return GlobalResponse.dataWasFound(transformPagination.transformPagination(lt,page,null,null),
                request);
    }

    private List<FindAllDTO> convertToFindAllDTO(List<Periode> periodes) {
        List<FindAllDTO> lt = new ArrayList<>();
        for (Periode periode : periodes) {
            FindAllDTO findAllDTO = new FindAllDTO();
            findAllDTO.setId(periode.getId());
            findAllDTO.setName(periode.getName());
            findAllDTO.setDeleted(periode.getDeleted());
            lt.add(findAllDTO);
        }
        return lt;
    }

    public Periode converToEntity(ValDataMasterDTO valDataMasterDTO) {
        Periode periode = modelMapper.map(valDataMasterDTO, Periode.class);
        return periode;
    }
}
