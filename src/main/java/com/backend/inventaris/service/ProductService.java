package com.backend.inventaris.service;

import com.backend.inventaris.config.OtherConfig;
import com.backend.inventaris.core.IService;
import com.backend.inventaris.dto.response.FindProductDTO;
import com.backend.inventaris.dto.response.FindMeasureDTO;
import com.backend.inventaris.dto.validation.ValProductDTO;
import com.backend.inventaris.enumm.TypeTransaction;
import com.backend.inventaris.handler.GlobalResponse;
import com.backend.inventaris.model.Measure;
import com.backend.inventaris.model.Product;
import com.backend.inventaris.repo.MeasureRepo;
import com.backend.inventaris.repo.ProductRepo;
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
 *  Modul Code - 04
 *  Platform Code - P
 */

@Service
@Transactional
public class ProductService implements IService<Product> {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private MeasureRepo measureRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination transformPagination;

    @Override
    public ResponseEntity<Object> create(Product product, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        try {
            Optional<Measure> measureOptional= measureRepo.findById(product.getMeasure().getId());
            if (!measureOptional.isPresent()) {
                return GlobalResponse.dataRelasiNotFound("P04CC001", request);
            }
            product.setName(product.getName());
            product.setMeasure(product.getMeasure());
            product.setPrice(product.getPrice());
            product.setWamStock(product.getWamStock());
            product.setCreatedBy(Long.valueOf(mapToken.get("userId").toString()));
            productRepo.save(product);
        } catch (Exception e) {
            LoggingFile.logException("Product Service","Create failed"+ RequestCapture.allRequest(request),e, OtherConfig.getEnableLog());
            return GlobalResponse.failedToSave("P04CC002",request);
        }
        return GlobalResponse.savedSuccessfully(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Product product, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        try {
            Optional<Product> productOptional = productRepo.findById(id);
            if (!productOptional.isPresent()){
                return GlobalResponse.dataNotFound("P04CC011",request);
            }

            Optional<Measure> measureOptional= measureRepo.findById(product.getMeasure().getId());
            if (!measureOptional.isPresent()) {
                return GlobalResponse.dataRelasiNotFound("P04CC012", request);
            }
            Product productNext = productOptional.get();
            productNext.setName(product.getName());
            productNext.setMeasure(product.getMeasure());
            productNext.setPrice(product.getPrice());
            productNext.setWamStock(product.getWamStock());
            productNext.setUpdatedBy(Long.valueOf(mapToken.get("userId").toString()));
            productRepo.save(productNext);
        } catch (Exception e) {
            LoggingFile.logException("Product Service","Update failed"+ RequestCapture.allRequest(request),e, OtherConfig.getEnableLog());
            return GlobalResponse.failedToUpdate("P04CC013",request);
        }
        return GlobalResponse.updateSuccessfully(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);

        try {
            Optional<Product> productOptional = productRepo.findById(id);
            if (!productOptional.isPresent()){
                return GlobalResponse.dataNotFound("P04CC021",request);
            }

            Product nextProduct = productOptional.get();
            nextProduct.setDeleted(true);
            nextProduct.setUpdatedBy(Long.valueOf(mapToken.get("userId").toString()));
            productRepo.save(nextProduct);
        }catch (Exception e) {
            LoggingFile.logException("Product Service","Delete failed"+ RequestCapture.allRequest(request),e, OtherConfig.getEnableLog());
            return GlobalResponse.deletedFailed("P04CC022",request);
        }

        return GlobalResponse.deletedSuccessfully(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Product> page = null;
        List<Product> list = null;
        page = productRepo.findAllByIsDeleted(false,pageable);
        list = page.getContent();
        List<FindProductDTO> lt = convertToFindAllDTO(list);
        return GlobalResponse.dataWasFound(transformPagination.transformPagination(lt,page,null,null),
                request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, TypeTransaction typeTransaction, HttpServletRequest request) {
        return null;
    }

    private List<FindProductDTO> convertToFindAllDTO(List<Product> products) {
        List<FindProductDTO> lt = new ArrayList<>();
        for (Product product : products) {
            FindProductDTO findProductDTO = new FindProductDTO();
            findProductDTO.setId(product.getId());
            findProductDTO.setName(product.getName());
            findProductDTO.setMeasure(convertMeasureToResponseDTO(product.getMeasure()));
            findProductDTO.setPrice(product.getPrice());
            findProductDTO.setDeleted(product.getDeleted());
            findProductDTO.setWamStock(product.getWamStock());
            lt.add(findProductDTO);
        }
        return lt;
    }

    private FindMeasureDTO convertMeasureToResponseDTO(Measure measure) {
        if (measure == null) {
            return null;
        }
        FindMeasureDTO findMeasureDTO = new FindMeasureDTO();
        findMeasureDTO.setId(measure.getId());
        findMeasureDTO.setNameMeasure(measure.getName());
        return findMeasureDTO;
    }

    public Product converToEntity(ValProductDTO valProductDTO) {
        Product product = modelMapper.map(valProductDTO, Product.class);
        return product;
    }
}
