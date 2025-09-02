package com.victor.storesystem.controller;

import com.victor.storesystem.model.Produto;
import com.victor.storesystem.service.CategoriaService;
import com.victor.storesystem.service.ProdutoService;
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
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;
    private final CategoriaService categoriaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", produtoService.findAll());
        return "produtos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("categorias", categoriaService.findAll()); // Adiciona as categorias ao modelo
        return "produtos/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("produto") Produto produto,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categorias", categoriaService.findAll()); // Recarrega as categorias
            return "produtos/form";
        }
        produtoService.save(produto);
        return "redirect:/produtos";
    }
}