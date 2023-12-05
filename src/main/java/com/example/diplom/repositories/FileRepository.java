package com.example.diplom.repositories;

import com.example.diplom.models.File;
import com.example.diplom.models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, String> {

    File findByUserAndFileName(User user, String fileName);
    void removeByUserAndFileName(User user, String fileName);
    List<File> findAllByUser(User user, Sort sort);

    @Modifying
    @Query("update File f set f.fileName = :newName where f.fileName = :filename and f.user = :user")
    void editFileNameByUser(@Param("user") User user, @Param("filename") String filename, @Param("newName") String newName);
}
