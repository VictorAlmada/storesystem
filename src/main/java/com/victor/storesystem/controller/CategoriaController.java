package com.victor.storesystem.controller;

import com.victor.storesystem.model.Categoria;
import com.victor.storesystem.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    // Método para listar todas as categorias
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.findAll());
        return "categorias/lista";
    }
    // Método para exibir o formulário de criação de uma nova categoria
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/form";
    }

    // Método para salvar uma nova categoria ou atualizar uma existente
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

    // Método para editar uma categoria existente
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Categoria categoria = categoriaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        model.addAttribute("categoria", categoria);
        return "categorias/form";
    }

    // Método para atualizar uma categoria existente
    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("categoria") Categoria categoria, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "categorias/form";
        }
        categoriaService.update(id, categoria);
        return "redirect:/categorias";
    }

    // Método para excluir uma categoria
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        categoriaService.deleteById(id);
        return "redirect:/categorias";
    }



}
