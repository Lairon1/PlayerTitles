package com.lairon.plugin.playertitles.registered;

import com.lairon.plugin.playertitles.title.AnimationTitle;
import com.lairon.plugin.playertitles.title.Title;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;

public class RegisteredTitles {

    private HashSet<Title> registeredTitles = new HashSet<>();

    public void registerTitle(Title title){
        if(registeredTitles.contains(title)) throw new IllegalArgumentException("This player title is already registered");
        registeredTitles.add(title);
    }

    public Title getTitleByID(String id){
        Optional<Title> first = registeredTitles.stream()
                .filter(title -> title.getId().equalsIgnoreCase(id))
                .findFirst();
        Title title;

        try {
            title = first.get();
        }catch (NoSuchElementException e){
            return null;
        }
        return title;
    }

    public HashSet<Title> getAllTitles(){
        return new HashSet<Title>(registeredTitles);
    }

    public void clear(){
        for (Title registeredTitle : registeredTitles) if(registeredTitle instanceof AnimationTitle) ((AnimationTitle)registeredTitle).setCancel(true);
        registeredTitles.clear();
    }

}
