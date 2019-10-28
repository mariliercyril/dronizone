package com.scp.dronizone.warehouse.entity;

import com.scp.dronizone.warehouse.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class Warehouse {

    @Autowired
    private ItemRepository itemRepository;

    public Warehouse() {
    }

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public void addItems(List<Item> newItems) {
        itemRepository.saveAll(newItems);
    }

    public void addItem(Item newItem) {
        itemRepository.save(newItem);
    }

    public void resetItems() {
        itemRepository.deleteAll();
    }

    public Item getItem(String idItem){
        Optional<Item> optionalItem = itemRepository.findByItemId(idItem);
        Item item = optionalItem.get();
        return item;
    }

}
