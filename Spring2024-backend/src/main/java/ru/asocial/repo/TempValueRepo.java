package ru.asocial.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.asocial.entity.TempValueEntity;

public interface TempValueRepo extends JpaRepository<TempValueEntity, Long> {

    public TempValueEntity findByKey(String key);
}
