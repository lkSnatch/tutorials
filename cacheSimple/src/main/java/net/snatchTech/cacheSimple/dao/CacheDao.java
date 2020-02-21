package net.snatchTech.cacheSimple.dao;

import net.snatchTech.cacheSimple.model.CarOwner;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

@Repository
public class CacheDao {

    private static Map<String, CarOwner> cache = new ConcurrentHashMap<>();

    public CarOwner getAndPutValueIfAbsent(String number, Function<? super String, ? extends CarOwner> computeIfAbsentFunction) {
        return cache.computeIfAbsent(number, computeIfAbsentFunction);
    }

    public CarOwner get(String number) {
        return cache.get(number);
    }

    public void remove(String number) {
        cache.remove(number);
    }

    public void put(String number, CarOwner carOwner) {
        cache.put(number, carOwner);
    }

    public void putRetrievedFromDBValue(String number, BiFunction<? super String, ? super CarOwner, ? extends CarOwner> retrieveFunction) {
        cache.compute(number, retrieveFunction);
    }

}
