package com.mingyuchoo.graphql.resolver;

import com.mingyuchoo.graphql.model.City;
import com.mingyuchoo.graphql.repository.CityRepository;
import graphql.schema.DataFetcher;
import java.util.Optional;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityResolver {

    @Autowired private CityRepository cityRepository;

    // Count
    public DataFetcher<?> cityCount() {
        return environment -> {
            return cityRepository.count();
        };
    }

    // List
    public DataFetcher<?> cities() {
        return environment -> {
            return cityRepository.findAll();
        };
    }

    // Read
    public DataFetcher<?> city() {
        return environment -> {
            Long id = Long.parseLong(environment.getArgument("id"));
            return cityRepository.findById(id);
        };
    }

    // Create
    public DataFetcher<?> addCity() {
        return environment -> {
            String name = environment.getArgument("name");
            int population = environment.getArgument("population");
            return cityRepository.save(new City(name, population));
        };
    }

    // Update
    public DataFetcher<?> changeCity() {
        return environment -> {
            Long id = Long.parseLong(environment.getArgument("id"));
            String name = environment.getArgument("name");
            int population = environment.getArgument("population");

            Optional<City> optCityEntity = cityRepository.findById(id);
            if (optCityEntity.isPresent()) {
                City city = optCityEntity.get();
                if (name != null) city.setName(name);
                if (population >= 0) city.setPopulation(population);
                return cityRepository.save(city);
            }
            throw new NotFoundException("Not found City to update!");
        };
    }

    // Delete
    public DataFetcher<?> removeCity() {
        return environment -> {
            Long id = Long.parseLong(environment.getArgument("id"));
            cityRepository.deleteById(id);
            return id;
        };
    }
}
