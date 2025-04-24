package com.backend.inventaris.service;

import com.backend.inventaris.config.OtherConfig;
import com.backend.inventaris.core.IService;
import com.backend.inventaris.dto.response.FindAllDataMasterDTO;
import com.backend.inventaris.dto.response.FindProductDTO;
import com.backend.inventaris.dto.response.FindWarehouseDTO;
import com.backend.inventaris.dto.validation.ValDataMasterDTO;
import com.backend.inventaris.enumm.TypeTransaction;
import com.backend.inventaris.handler.GlobalResponse;
import com.backend.inventaris.model.Product;
import com.backend.inventaris.model.Warehouse;
import com.backend.inventaris.repo.WarehouseRepo;
import com.backend.inventaris.security.RequestCapture;
import com.backend.inventaris.util.GlobalFunction;
import com.backend.inventaris.util.LoggingFile;
import com.backend.inventaris.util.TransformPagination;
import jakarta.servlet.http.HttpServletRequest;
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
 *  Modul Code - 01
 *  Platform Code - WH
 */

@Service
public class WarehouseService implements IService<Warehouse> {

    @Autowired
    private WarehouseRepo warehouseRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination transformPagination;

    @Override
    public ResponseEntity<Object> create(Warehouse warehouse, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        try {
            warehouse.setName(warehouse.getName());
            warehouse.setCreatedBy(Long.valueOf(mapToken.get("userId").toString()));
            warehouseRepo.save(warehouse);
        }catch (Exception e) {
            LoggingFile.logException("Warehouse Service","Create failed"+ RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.failedToSave("WH01CC001",request);
        }
        return GlobalResponse.savedSuccessfully(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Warehouse warehouse, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);

        try {
            Optional<Warehouse> warehouseOptional = warehouseRepo.findById(id);
            if (!warehouseOptional.isPresent()){
                return GlobalResponse.dataNotFound("WH01CC011",request);
            }

            Warehouse nextWarehouse = warehouseOptional.get();
            nextWarehouse.setName(warehouse.getName());
            nextWarehouse.setUpdatedBy(Long.valueOf(mapToken.get("userId").toString()));
            warehouseRepo.save(nextWarehouse);
        }catch (Exception e){
            LoggingFile.logException("Warehouse Service","Update failed"+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.failedToUpdate("WH01CC012",request);
        }
        return GlobalResponse.updateSuccessfully(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);

        try {
            Optional<Warehouse> warehouseOptional = warehouseRepo.findById(id);
            if (!warehouseOptional.isPresent()){
                return GlobalResponse.dataNotFound("WH01CC021",request);
            }

            Warehouse nextWarehouse = warehouseOptional.get();
            nextWarehouse.setDeleted(true);
            nextWarehouse.setUpdatedBy(Long.valueOf(mapToken.get("userId").toString()));
            warehouseRepo.save(nextWarehouse);
        }catch (Exception e){
            LoggingFile.logException("Warehouse Service","Deleted failed"+RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.deletedFailed("WH01CC022",request);
        }
        return GlobalResponse.deletedSuccessfully(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Warehouse> page = null;
        List<Warehouse> list = null;
        page = warehouseRepo.findAllByIsDeleted(false,pageable);
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
        Optional<Warehouse> optionalWarehouse = null;
        try {
            optionalWarehouse = warehouseRepo.findById(id);
            if (!optionalWarehouse.isPresent()){
                return GlobalResponse.dataNotFound("WH01CC051",request);
            }
        }catch (Exception e){
            LoggingFile.logException("Product Service","Get by Id error" +RequestCapture.allRequest(request),e,OtherConfig.getEnableLog());
            return GlobalResponse.error("WH01CC052",request);
        }
        return GlobalResponse.dataWasFound(modelMapper.map(optionalWarehouse.get(), FindWarehouseDTO.class), request);
    }

    private List<FindAllDataMasterDTO> convertToFindAllDTO(List<Warehouse> warehouses) {
        List<FindAllDataMasterDTO> lt = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            FindAllDataMasterDTO findAllDataMasterDTO = new FindAllDataMasterDTO();
            findAllDataMasterDTO.setId(warehouse.getId());
            findAllDataMasterDTO.setName(warehouse.getName());
            findAllDataMasterDTO.setDeleted(warehouse.getDeleted());
            lt.add(findAllDataMasterDTO);
        }
        return lt;
    }

    public Warehouse converToEntity(ValDataMasterDTO valDataMasterDTO) {
        Warehouse warehouse = modelMapper.map(valDataMasterDTO, Warehouse.class);
        return warehouse;
    }
}
