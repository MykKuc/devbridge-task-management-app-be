package com.BESourceryAdmissionTool.user.repositories;

import com.BESourceryAdmissionTool.task.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, Long>{

 Optional<UserEntity> findByEmail(String email);
}
