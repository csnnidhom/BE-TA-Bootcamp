package com.backend.inventaris.service;

import com.backend.inventaris.config.JwtConfig;
import com.backend.inventaris.dto.validation.ValLoginDTO;
import com.backend.inventaris.handler.GlobalResponse;
import com.backend.inventaris.model.User;
import com.backend.inventaris.repo.UserRepo;
import com.backend.inventaris.security.BcryptImpl;
import com.backend.inventaris.security.Crypto;
import com.backend.inventaris.security.JwtUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *  Modul Code - 00
 *  Platform Code - AUTH
 */
@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtility jwtUtility;

    private Random random = new Random();

    public ResponseEntity<Object> Login(User user , HttpServletRequest request){
        Optional<User> optUser = userRepo.findByUsername(user.getUsername());
        Map<String,Object> m = new HashMap<>();
        if(!optUser.isPresent()){
            return GlobalResponse.loginProblem("AUTH00CC001",request);
        }
        User userNext = optUser.get();
//        if(!userNext.getRegistered()){
//            return GlobalResponse.loginProblem("AUTH00CC002",request);
//        }

        if(!BcryptImpl.verifyHash((user.getUsername()+user.getPassword()),userNext.getPassword())){
            return GlobalResponse.loginProblem("AUTH00CC003",request);
        }
        Map<String,Object> m1 = new HashMap<>();
        m1.put("id", userNext.getId());
        m1.put("ml", userNext.getEmail());

        /** khusus testing automation */
        String token = jwtUtility.doGenerateToken(m1,userNext.getUsername());
        if(JwtConfig.getTokenEncryptEnable().equals("y")){
            token = Crypto.performEncrypt(token);
        }
        m.put("token",token);
        m.put("username", userNext.getUsername());
        m.put("email",userNext.getEmail());
        m.put("role", userNext.getRole());
        return GlobalResponse.dataWasFound(m,request);
    }

//    public ResponseEntity<Object> regis(User user , HttpServletRequest request){
//        Optional<User> optUser = userRepo.findByUsername(user.getUsername());
//        Map<String,Object> m = new HashMap<>();
//        /** control flow yg ini jika user belum pernah sama sekali melakukan registrasi ,
//         *  sehingga proses nya tinggal simpan saja
//         */
//        int intOtp = random.nextInt(111111,999999);
//        if(!optUser.isPresent()){
//            user.setPassword(BcryptImpl.hash(user.getUsername()+user.getPassword()));
//            user.setOtp(BcryptImpl.hash(String.valueOf(intOtp)));
//            Akses akses = new Akses();
//            akses.setId(2L);//default untuk relasi nya ke akses, jadi user yang melakukan registrasi otomatis mendapatkan akses sebagai member
//            user.setAkses(akses);
//            userRepo.save(user);
//            m.put("email",user.getEmail());
//        }else{
//            User userNext = optUser.get();
//            if(userNext.getRegistered()){
//                return GlobalResponse.sudahTeregistrasi("AUTH00CC011",request);
//            }
//            Optional<User> optCheckEmailUser = userRepo.findByEmailAndIsRegistered(user.getEmail(),true);
//            if(optCheckEmailUser.isPresent()){
//                return GlobalResponse.emailTeregistrasi("AUTH00CC012",request);
//            }
//            Optional<User> optCheckNoHp = userRepo.findByNoHpAndIsRegistered(user.getNoHp(),true);
//            if(optCheckNoHp.isPresent()){
//                return GlobalResponse.noHpTeregistrasi("AUTH00CC013",request);
//            }
//            userNext.setOtp(BcryptImpl.hash(String.valueOf(intOtp)));
//            userNext.setEmail(user.getEmail());
//            userNext.setNoHp(user.getNoHp());
//            userNext.setAlamat(user.getAlamat());
//            userNext.setNama(user.getNama());
//            userNext.setTanggalLahir(user.getTanggalLahir());
//            userNext.setModifiedBy(userNext.getId());
//            userNext.setPassword(user.getUsername()+user.getPassword());
//            m.put("email",user.getEmail());
//        }
//
//        if(OtherConfig.getEnableAutomationTest().equals("y")){
//            m.put("otp",intOtp);//ini digunakan hanya untuk automation testing ataupun unit testing saja....
//        }
//        if(OtherConfig.getSmtpEnable().equals("y")){
//            SendMailOTP.verifyRegisOTP("Verifikasi OTP Registrasi",//di harcode
//                    user.getNama(),
//                    user.getEmail(),
//                    String.valueOf(intOtp)
//            );
//        }
//        return GlobalResponse.dataDitemukan(m,request);
//    }
//
//    public ResponseEntity<Object> verifyRegis(User user , HttpServletRequest request){
//        Optional<User> optUser = userRepo.findByEmail(user.getEmail());
//        if(!optUser.isPresent()){
//            return GlobalResponse.dataTidakValid("AUTH00CC021",request);
//        }
//        User userNext = optUser.get();
//        /** OTP nya sudah Valid */
//        if(!BcryptImpl.verifyHash(user.getOtp(),userNext.getOtp())){
//            return GlobalResponse.dataTidakValid("AUTH00CC022",request);
//        }
//
//        userNext.setRegistered(true);
//        userNext.setModifiedBy(userNext.getId());
//
//        return GlobalResponse.registrasiBerhasil(request);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opUser = userRepo.findByUsername(username);
        if(!opUser.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        User user = opUser.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),user.getAuthorities());
    }

//    public User convertToEntity(ValRegisDTO valRegisDTO){
//        return modelMapper.map(valRegisDTO, User.class);
//    }
//
//    public User convertToEntity(ValVerifyOTPRegisDTO valVerifyOTPRegisDTO){
//        return modelMapper.map(valVerifyOTPRegisDTO, User.class);
//    }

    public User convertToEntity(ValLoginDTO valLoginDTO){
        return modelMapper.map(valLoginDTO, User.class);
    }
}
