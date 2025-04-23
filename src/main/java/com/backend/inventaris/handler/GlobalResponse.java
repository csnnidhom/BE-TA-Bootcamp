package com.backend.inventaris.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public class GlobalResponse {

    public static ResponseEntity<Object> failedToSave(String errorCode,HttpServletRequest request){
        return new ResponseHandler().handleResponse("Data Gagal Disimpan",
                HttpStatus.INTERNAL_SERVER_ERROR,
                null,errorCode,request);
    }

    public static ResponseEntity<Object> savedSuccessfully(HttpServletRequest request){
        return new ResponseHandler().handleResponse("Data Berhasil Disimpan",
                HttpStatus.CREATED,
                null,null,request);
    }

    public static ResponseEntity<Object> registrasiBerhasil(HttpServletRequest request){
        return new ResponseHandler().handleResponse("Registrasi Berhasil !!",
                HttpStatus.OK,
                null,null,request);
    }

    public static ResponseEntity<Object> dataTidakValid(String errorCode,HttpServletRequest request){
        return new ResponseHandler().handleResponse("Data Tidak Valid",
                HttpStatus.BAD_REQUEST,
                null,errorCode,request);
    }

    public static ResponseEntity<Object> dataNotFound(String errorCode,HttpServletRequest request){
        return new ResponseHandler().handleResponse("Data Tidak Ditemukan",
                HttpStatus.BAD_REQUEST,
                null,errorCode,request);
    }

    public static ResponseEntity<Object> dataRelasiNotFound(String errorCode,HttpServletRequest request){
        return new ResponseHandler().handleResponse("Data Relasi Tidak Ditemukan",
                HttpStatus.BAD_REQUEST,
                null,errorCode,request);
    }

    public static ResponseEntity<Object> failedToUpdate(String errorCode, HttpServletRequest request){
        return new ResponseHandler().handleResponse("Data Gagal Diubah",
                HttpStatus.INTERNAL_SERVER_ERROR,
                null,errorCode,request);
    }

    public static ResponseEntity<Object> updateSuccessfully(HttpServletRequest request){
        return new ResponseHandler().handleResponse("Data Berhasil Diubah",
                HttpStatus.OK,
                null,null,request);
    }

    public static ResponseEntity<Object> deletedFailed(String errorCode, HttpServletRequest request){
        return new ResponseHandler().handleResponse("Data Gagal Dihapus",
                HttpStatus.INTERNAL_SERVER_ERROR,
                null,errorCode,request);
    }

    public static ResponseEntity<Object> deletedSuccessfully(HttpServletRequest request){
        return new ResponseHandler().handleResponse("Data Berhasil Dihapus",
                HttpStatus.OK,
                null,null,request);
    }

    public static ResponseEntity<Object> terjadiKesalahan(String errorCode,HttpServletRequest request){
        return new ResponseHandler().handleResponse("Terjadi Kesalahan Pada Server",
                HttpStatus.INTERNAL_SERVER_ERROR,
                null,errorCode,request);
    }

    public static ResponseEntity<Object> dataWasFound(Object object,HttpServletRequest request){
        return new ResponseHandler().handleResponse("Data Ditemukan",
                HttpStatus.OK,
                object,null,request);
    }

    public static ResponseEntity<Object> formatHarusExcel(String errorCode ,HttpServletRequest request){
        return new ResponseHandler().handleResponse("Format Harus Excel",
                HttpStatus.BAD_REQUEST,
                null,errorCode,request);
    }

    public static ResponseEntity<Object> fileExcelKosong(String errorCode ,HttpServletRequest request){
        return new ResponseHandler().handleResponse("File Excel Kosong",
                HttpStatus.BAD_REQUEST,
                null,errorCode,request);
    }

    public static ResponseEntity<Object> uploadExcelGagal(String errorCode ,HttpServletRequest request){
        return new ResponseHandler().handleResponse("Upload File Excel Gagal !!",
                HttpStatus.BAD_REQUEST,
                null,errorCode,request);
    }

    public static ResponseEntity<Object> uploadExcelBerhasil(HttpServletRequest request){
        return new ResponseHandler().handleResponse("Upload File Excel Berhasil",
                HttpStatus.CREATED,
                null,null,request);
    }
    public static ResponseEntity<Object> loginProblem(String errorCode,HttpServletRequest request){
        return new ResponseHandler().handleResponse("Username / Password Salah",
                HttpStatus.BAD_REQUEST,
                null,errorCode,request);
    }
    public static ResponseEntity<Object> sudahTeregistrasi(String errorCode,HttpServletRequest request){
        return new ResponseHandler().handleResponse("User Telah Terdaftar, Silahkan Langsung Melakukan Login !!",
                HttpStatus.BAD_REQUEST,
                null,errorCode,request);
    }

    public static ResponseEntity<Object> emailTeregistrasi(String errorCode,HttpServletRequest request){
        return new ResponseHandler().handleResponse("Email Telah Digunakan !!",
                HttpStatus.BAD_REQUEST,
                null,errorCode,request);
    }
    public static ResponseEntity<Object> noHpTeregistrasi(String errorCode,HttpServletRequest request){
        return new ResponseHandler().handleResponse("No HP Telah Digunakan !!",
                HttpStatus.BAD_REQUEST,
                null,errorCode,request);
    }

    public static void manualResponse(HttpServletResponse response, ResponseEntity<Object> resObject){
        try{
            response.getWriter().write(convertObjectToJson(resObject.getBody()));
            response.setStatus(resObject.getStatusCodeValue());
        }catch (IOException e){

        }
    }

    public static String convertObjectToJson(Object obj) throws JsonProcessingException {

        if(obj == null){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}