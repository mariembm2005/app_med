package com.example.social_network_for_readers.repository;

import com.example.social_network_for_readers.entity.Collection;
import com.example.social_network_for_readers.entity.LibraryEntry;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection,Integer> {

}
