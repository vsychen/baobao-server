package com.baobao.repository;

import com.baobao.model.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredients, UUID> {

}
