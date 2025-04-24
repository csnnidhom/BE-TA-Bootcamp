package com.backend.inventaris.service;

import com.backend.inventaris.config.OtherConfig;
import com.backend.inventaris.core.IService;
import com.backend.inventaris.dto.response.*;
import com.backend.inventaris.dto.validation.ValTransactionDTO;
import com.backend.inventaris.enumm.TypeTransaction;
import com.backend.inventaris.handler.GlobalResponse;
import com.backend.inventaris.model.*;
import com.backend.inventaris.repo.*;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.time.LocalDate.now;

/**
 *  Modul Code - 05
 *  Platform Code - T
 */

@Service
@Transactional
public class TransactionService implements IService<Transaction> {
    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private MeasureRepo measureRepo;

    @Autowired
    private PeriodeRepo periodeRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination transformPagination;
    @Autowired
    private WarehouseRepo warehouseRepo;

    @Override
    public ResponseEntity<Object> create(Transaction transaction, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);

        try {
            //cek product
            Optional<Product> productOptional = productRepo.findById(transaction.getProduct().getId());
            if (!productOptional.isPresent()) {
                return GlobalResponse.dataRelasiNotFound("T05CC001", request);
            }

            //cek warehouse
            Optional<Warehouse> warehouseOptional = warehouseRepo.findById(transaction.getWarehouse().getId());
            if (!warehouseOptional.isPresent()) {
                return GlobalResponse.dataRelasiNotFound("T05CC002", request);
            }

            //cek periode
            Optional<Periode> periodeOptional= periodeRepo.findFirstByActive(true);
            if (!periodeOptional.isPresent()) {
                return GlobalResponse.dataRelasiNotFound("T05CC003", request);
            }

            if (transaction.getPrice()==null){
                transaction.setDate(LocalDate.now());
                transaction.setQty(transaction.getQty());
                transaction.setTypeTransaction(TypeTransaction.SO);
                transaction.setProduct(transaction.getProduct());
                transaction.setPrice(0L);
                transaction.setWarehouse(transaction.getWarehouse());
                transaction.setPeriode(periodeOptional.get());
                transaction.setCreatedBy(Long.valueOf(mapToken.get("userId").toString()));
            }else {
                transaction.setDate(transaction.getDate());
                transaction.setPrice(transaction.getPrice());
                transaction.setQty(transaction.getTypeTransaction()==TypeTransaction.S ? transaction.getQty() * -1 : transaction.getQty());
                transaction.setTypeTransaction(transaction.getTypeTransaction());
                transaction.setProduct(transaction.getProduct());
                transaction.setWarehouse(transaction.getWarehouse());
                transaction.setPeriode(periodeOptional.get());
                transaction.setCreatedBy(Long.valueOf(mapToken.get("userId").toString()));
            }

            transactionRepo.save(transaction);

        }catch (Exception e) {
            LoggingFile.logException("Transaction Service","Create failed"+ RequestCapture.allRequest(request),e, OtherConfig.getEnableLog());
            return GlobalResponse.failedToSave("T05CC003",request);
        }

        return GlobalResponse.savedSuccessfully(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Transaction transaction, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);

        try {
            Optional<Transaction> transactionOptional = transactionRepo.findById(id);
            if (!transactionOptional.isPresent()) {
                return GlobalResponse.dataNotFound("T05CC001", request);
            }

            Optional<Periode> periodeOptional= periodeRepo.findFirstByActive(true);
            Transaction transactionToUpdate = transactionOptional.get();

            if (transaction.getPrice()==null){
                transactionToUpdate.setDate(LocalDate.now());
                transactionToUpdate.setQty(transaction.getQty());
                transactionToUpdate.setTypeTransaction(TypeTransaction.SO);
                transactionToUpdate.setProduct(transaction.getProduct());
                transactionToUpdate.setWarehouse(transaction.getWarehouse());
                transactionToUpdate.setPeriode(periodeOptional.get());
                transactionToUpdate.setCreatedBy(Long.valueOf(mapToken.get("userId").toString()));
            }else {
                transactionToUpdate.setDate(transaction.getDate());
                transactionToUpdate.setPrice(transaction.getPrice());
                transactionToUpdate.setQty(transaction.getTypeTransaction()==TypeTransaction.S ? transaction.getQty() * -1 : transaction.getQty());
                transactionToUpdate.setTypeTransaction(transaction.getTypeTransaction());
                transactionToUpdate.setProduct(transaction.getProduct());
                transactionToUpdate.setWarehouse(transaction.getWarehouse());
                transactionToUpdate.setPeriode(periodeOptional.get());
                transactionToUpdate.setCreatedBy(Long.valueOf(mapToken.get("userId").toString()));
            }

            transactionRepo.save(transactionToUpdate);

        }catch (Exception e) {
            LoggingFile.logException("Transaction Service","Update failed"+ RequestCapture.allRequest(request),e, OtherConfig.getEnableLog());
            return GlobalResponse.failedToUpdate("T05CC002",request);
        }

        return GlobalResponse.updateSuccessfully(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        Map<String,Object> mapToken = GlobalFunction.extractToken(request);
        try {
            Optional<Transaction> optionalTransaction = transactionRepo.findById(id);
            if(!optionalTransaction.isPresent()) {
                return GlobalResponse.dataNotFound("T05CC021", request);
            }
            Transaction nextTransaction = optionalTransaction.get();
            nextTransaction.setDeleted(true);
            nextTransaction.setUpdatedBy(Long.valueOf(mapToken.get("userId").toString()));
            transactionRepo.save(nextTransaction);
        }catch (Exception e) {
            LoggingFile.logException("Transaction Service","Delete failed"+ RequestCapture.allRequest(request),e, OtherConfig.getEnableLog());
            return GlobalResponse.deletedFailed("P04CC022",request);
        }

        return GlobalResponse.savedSuccessfully(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Transaction> page = null;
        List<Transaction> list = null;
        page = transactionRepo.findAll(pageable);

        list = page.getContent();
        List<FindAllTransactionDTO> lt = convertToFindAllDTO(list);
        return GlobalResponse.dataWasFound(transformPagination.transformPagination(lt,page,null,null),
                request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, TypeTransaction typeTransaction, HttpServletRequest request) {
        Page<Transaction> page = null;
        List<Transaction> list = null;
        page = transactionRepo.findAllByTypeTransaction(pageable, typeTransaction);

        list = page.getContent();
        List<FindAllTransactionDTO> lt = convertToFindAllDTO(list);
        return GlobalResponse.dataWasFound(transformPagination.transformPagination(lt,page,null,null),
                request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        return null;
    }

    private List<FindAllTransactionDTO> convertToFindAllDTO(List<Transaction> transactions) {
        List<FindAllTransactionDTO> lt = new ArrayList<>();
        for (Transaction transaction : transactions) {
            FindAllTransactionDTO findAllTransactionDTO = new FindAllTransactionDTO();
            findAllTransactionDTO.setId(transaction.getId());
            findAllTransactionDTO.setProduct(convertProductToResponseDTO(transaction.getProduct()));
            findAllTransactionDTO.setQty(transaction.getQty());
            findAllTransactionDTO.setWarehouse(convertWarehouseToResponseDTO(transaction.getWarehouse()));
            findAllTransactionDTO.setPeriode(convertPeriodeToResponseDTO(transaction.getPeriode()));
            lt.add(findAllTransactionDTO);
        }
        return lt;
    }

    private FindProductDTO convertProductToResponseDTO(Product product) {
        if (product == null) {
            return null;
        }
        FindProductDTO findProductDTO = new FindProductDTO();
        findProductDTO.setId(product.getId());
        findProductDTO.setName(product.getName());
        findProductDTO.setMeasure(convertMeasureToResponseDTO(product.getMeasure()));
        findProductDTO.setPrice(product.getPrice());
        findProductDTO.setDeleted(product.getDeleted());
        findProductDTO.setWarnStock(product.getWarnStock());
        return findProductDTO;
    }

    private FindPeriodeDTO convertPeriodeToResponseDTO(Periode periode) {
        if (periode == null) {
            return null;
        }
        FindPeriodeDTO findPeriodeDTO = new FindPeriodeDTO();
        findPeriodeDTO.setId(periode.getId());
        findPeriodeDTO.setNamePeriode(periode.getName());
        return findPeriodeDTO;
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

    private FindWarehouseDTO convertWarehouseToResponseDTO(Warehouse warehouse) {
        if (warehouse == null) {
            return null;
        }
        FindWarehouseDTO findWarehouseDTO = new FindWarehouseDTO();
        findWarehouseDTO.setId(warehouse.getId());
        findWarehouseDTO.setNameWarehouse(warehouse.getName());
        return findWarehouseDTO;
    }

    public Transaction converToEntity(ValTransactionDTO valTransactionDTO) {
        Transaction transaction = modelMapper.map(valTransactionDTO, Transaction.class);
        return transaction;
    }
}
