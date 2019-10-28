package com.scp.dronizone.warehouse.repository;

import com.scp.dronizone.warehouse.entity.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {

	@Query("{ 'item_id' : ?0 }")
	public Optional<Item> findByItemId(String idItem);

}
