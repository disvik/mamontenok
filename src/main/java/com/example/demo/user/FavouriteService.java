package com.example.demo.user;


import com.example.demo.child.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouriteService {

    private FavouriteRepository favouriteRepository;
    private UserRepository userRepository;


    @Autowired
    public void setRepository(FavouriteRepository repository) {
        this.favouriteRepository = repository;
    }

    @Autowired
    public void setRepository(UserRepository repository) {
        this.userRepository = repository;
    }


    public void saveFavourite(Favourite favourite) {
        favouriteRepository.save(favourite);
    }

    public void addFavourite(Favourite favourite) {
        favouriteRepository.save(favourite);
    }

    public List<Favourite> getAllFavourites() {
        return favouriteRepository.findAll();
    }

    public Favourite getFavouriteById(long id)
    {
        return favouriteRepository.getOne(id);
    }

    public void addFavourite(Favourite favourite, long user_id) {
        User b = userRepository.getOne(user_id);
        favourite.setUser(b);
        favouriteRepository.save(favourite);
    }
}