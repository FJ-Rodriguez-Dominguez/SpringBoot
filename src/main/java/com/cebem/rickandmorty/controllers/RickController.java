package com.cebem.rickandmorty.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cebem.rickandmorty.models.CharacterModel;
import com.cebem.rickandmorty.models.CharactersModel;
import com.cebem.rickandmorty.services.RickAndMortyServices;
import com.cebem.rickandmorty.untils.Untils;

@CrossOrigin(origins = "*")
@RestController
public class RickController {

    @GetMapping("/")
    public String saluda() {
        return "Bienvenid@ a mi api rest de rickAndMorty";
    }

    @GetMapping("/random")
    public String random() {
        return Math.round(Math.random() * 10) + "";
    }

    // http://localhost:8080/palindromo/
    @GetMapping("/palindromo/{word}")
    public String palindromo(@PathVariable String word) {
        return Untils.esPalindromo(word) ? "Si es un palíndromo" : "No es un palíndromo";
    }

    @GetMapping("/add")
    public String add(@RequestParam String n1, @RequestParam String n2) {
        float resultado = Float.parseFloat(n1) + Float.parseFloat(n2);
        Object params[] = { n1, n2, resultado };
        return MessageFormat.format("La suma de {0} mas {1} es igual a {2}", params);
    }

    @PostMapping("saveOnDisk")
    public String saveOnDisk(@RequestParam Map<String, String> body) {
        String name = body.get("name");
        String price = body.get("price");

        String info = name + " " + price + "\n";
        try {
            Untils.writeOnDisk("datos.txt", info);
        } catch (IOException e) {
            return "Error al intentar escribir en el fichero";
        }
        return "Gracias por enviar el formulario";
    }

    @DeleteMapping("/removeFromDisk")
    public String removeFromDisk() {
        boolean resultado = Untils.deleteFromDisk("datos.txt");
        return resultado ? "Borrado correcto" : "No he podido borrar";

    }

    /*
     * Crea un endpoint al que le pases 3 números y devuelme el mayor de ellos
     * Si alguno de los elementos pasados no es un número devolver la frese ERROR
     */

    @GetMapping("/maxNum")
    public String maxNum(@RequestParam String n1, String n2, String n3) {
        try {
            float f1 = Float.parseFloat(n1);
            float f2 = Float.parseFloat(n2);
            float f3 = Float.parseFloat(n3);

            return Untils.maxNum(f1, f2, f3) + "";
        } catch (NumberFormatException ex) {
            return "Error";
        }

    }

    /*
     * Crea un endpoint al que le pases un texto(frase)
     * Este devolverá el mismo texto, pero con la primera letra
     * de cada palabra en mayúscula(el resto en minúcula)
     */

    @GetMapping("/capitalize/{text}")
    public static String capitalize(@PathVariable String text) {
        return Untils.capitalizeText(text);
    }

    /*
     * Crea un endpoint que te devuelva la información guardada en el fichero
     * datos.txt
     */

    @GetMapping("/products")
    public String getProducts() {
        try {
            return Untils.readFromDisk("datos.txt");
        } catch (FileNotFoundException ex) {
            return "No se encontró el fichero";
        } catch (IOException ex) {
            return "Falló al acceder al fichero: " + ex.getMessage();
        }
    }
    /*
     * Crea un endpoint que al llamarlo vacie (no borre) el fichero datos.txt prueba
     * este endpoint con la extension thunderclient
     */

    @DeleteMapping("/clear")
    public String clear() {
        try {
            Untils.clearFile("datos.txt");
            return "Fichero limpiado correctamente";
        } catch (IOException ex) {
            return "Error al limpiar el fichero " + ex.getMessage();
        }
    }
    /*
     * Crea un endpoint que pasandole un número cualquiera te devuelva ese número
     * elevado al cuadrado
     */

    @GetMapping("/square")
    public String square(@RequestParam String n) {
        try {
            float value = Float.parseFloat(n);
            return value * value + " ";
        } catch (NumberFormatException ex) {
            return "El número no es válido";
        }
    }

    @GetMapping("/randomColors")
    public String randomColors() {
        final int COLOR_COUNT = 11;
        String[] Colors = new String[] { "negro", "azul", "marrón", "gris", "verde", "naranja", "rosa", "púrpura",
                "amarillo", "blanco", "morado" };
        if (COLOR_COUNT > Colors.length)
            throw new RuntimeException("Límite de colores superado");
        ArrayList<String> colores = new ArrayList<String>(Arrays.asList(Colors));
        String finalColors = "";
        for (int i = 0; i < COLOR_COUNT; i++) {
            int random = Untils.getRandomValue(colores.size());
            finalColors += colores.remove(random) + " ";
        }
        return finalColors;
    }

    // debería ir arriba del todo
    @Autowired
    RickAndMortyServices rickAndMortyServices;

    /*
     * Crea und endpoint que te devuelva un personaje random de la serie Rick and
     * Morty
     */
    @GetMapping("/rickandmorty/random")
    public String randomCharacter() {

        CharacterModel characterModel = rickAndMortyServices.getCharacterRandom();
        return characterModel.name + "<br/>" + "<img width='200' src='" + characterModel.image + "'/>";
    }

    @GetMapping("/rickandmorty/all")
    public String getAllCharacters() {
        CharactersModel charactersModel = rickAndMortyServices.getAllCharacters();
        String html = "";
        for (CharacterModel characterModel : charactersModel.results) {
            html += characterModel.name;
            html += "<br/>";
            html += "<img width='100'px src='" + characterModel.image + "'>";
            html += "<hr/>";
        }

        return html;
    }

}
