package com.cebem.rickandmorty.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cebem.rickandmorty.models.CharactersModel;
import com.cebem.rickandmorty.services.RickAndMortyServices;

@Controller
public class WebController {

  @Autowired
  RickAndMortyServices rickAndMortyServices;

  @RequestMapping("/rickandmorty/allTemplate")
  public String charactersTemplate(Model modelo) {
    CharactersModel charactersModel = rickAndMortyServices.getAllCharacters();

    modelo.addAttribute("creator", "Creado por FJ");
    modelo.addAttribute("characters", charactersModel.results);
    return "rickandmortyall";
  }

  @RequestMapping("/rickandmorty/countTemplate")
  public String charactersCountTemplate(Model modelo) {
    int count = rickAndMortyServices.getCharactersCount();
    modelo.addAttribute("count", count);
    return "rickandmortyCount";
  }
}