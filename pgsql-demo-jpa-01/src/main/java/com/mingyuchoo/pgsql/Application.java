package com.mingyuchoo.pgsql;

import com.mingyuchoo.pgsql.biz.cart.model.Cart;
import com.mingyuchoo.pgsql.biz.item.model.Item;
import com.mingyuchoo.pgsql.biz.person.model.Person;
import com.mingyuchoo.pgsql.biz.address.repository.AddressRepository;
import com.mingyuchoo.pgsql.biz.cart.repository.CartRepository;
import com.mingyuchoo.pgsql.biz.item.repository.ItemRepository;
import com.mingyuchoo.pgsql.biz.person.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner personDemo(
            PersonRepository personRepository, AddressRepository addressRepository) {

        return (args) -> {
            // create people
            Person person1 = new Person("부솔", "최");
            Person person2 = new Person("혜수", "김");
            Person person3 = new Person("노들", "강");

            // save people
            personRepository.save(person1);
            personRepository.save(person2);
            personRepository.save(person3);

            // create and save address
            // addressRepository.save(new Address("강남", person1));
        };
    }

    @Bean
    public CommandLineRunner cartDemo(
            CartRepository cartRepository, ItemRepository itemRepository) {
        return (args) -> {

            // create carts
            Cart cart1 = new Cart("식품", false);
            Cart cart2 = new Cart("의류", false);
            Cart cart3 = new Cart("가전", false);

            // save carts
            cartRepository.save(cart1);
            cartRepository.save(cart2);
            cartRepository.save(cart3);

            // create and save items
            itemRepository.save(new Item("쌀", 10, cart1));
            itemRepository.save(new Item("속옷", 7, cart2));
            itemRepository.save(new Item("정장", 70, cart2));
            itemRepository.save(new Item("세탁기", 300, cart3));
            itemRepository.save(new Item("냉장고", 150, cart3));
            itemRepository.save(new Item("TV", 220, cart3));
        };
    }
}
