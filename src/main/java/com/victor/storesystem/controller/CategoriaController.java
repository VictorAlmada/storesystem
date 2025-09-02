package com.victor.storesystem.controller;

import com.victor.storesystem.model.Categoria;
import com.victor.storesystem.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.findAll());
        return "categorias/lista";
    }

    @GetMapping("/nova")
    public String novo(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("categoria") Categoria categoria, BindingResult bindingResult) {

        // Se houver erros de validação, retorna ao formulário
        if (bindingResult.hasErrors()) {
            return "categorias/form";
        }

        // Salva a categoria e redireciona para a lista
        categoriaService.save(categoria);
        return "redirect:/categorias";
    }
}
