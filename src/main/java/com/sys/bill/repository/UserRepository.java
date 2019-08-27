package com.sys.bill.repository;

import com.sys.bill.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @Title: UserRepository
 * @Description: TODO
 * @author: furg@senthink.com
 * @date: 2019/8/27 11:56
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor {

    List<User> findByAccountOrMobileOrEmail(String account, String mobile, String email);


    @Query(value = "SELECT CASE WHEN count(u)> 0 THEN true ELSE FALSE END from User u WHERE u.account = ?1 OR u.mobile = ?2 OR u.email = ?3")
    boolean existByAccountOrMobileOrEmail(String account, String mobile, String email);


    Long countByAccountOrMobileOrEmail(String account, String mobile, String email);
}
