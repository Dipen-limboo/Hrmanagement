package com.humanresourcemanagement.ResourceMangement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.Document;
import com.humanresourcemanagement.ResourceMangement.Entity.User;

public interface DocumentRepo extends JpaRepository<Document, Long>, JpaSpecificationExecutor<Document> {

	boolean existsByCitizenship(String citizenship);

	boolean existsByPan(String pan);

	boolean existsByNationality(String nationalityId);

	Optional<Document> findByUser(User user);

	boolean existsByFilePath(String string);

	
}
