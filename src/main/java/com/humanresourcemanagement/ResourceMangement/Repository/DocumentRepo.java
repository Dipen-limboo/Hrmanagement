package com.humanresourcemanagement.ResourceMangement.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.humanresourcemanagement.ResourceMangement.Entity.Document;
import com.humanresourcemanagement.ResourceMangement.Entity.User;

public interface DocumentRepo extends JpaRepository<Document, Long>, JpaSpecificationExecutor<Document> {

	boolean existsByCitizenship(String citizenship);

	boolean existsByPan(String pan);

	boolean existsByNationality(String nationalityId);

	List<Document> findByUser(User user);

	boolean existsByFilePath(String string);

	void deleteByIdAndUser(Long id, User user);

	Optional<Document> findByIdAndUser(Long id, User user);

	boolean existsByUser(User user);

	
}
