package com.victor.storesystem.controller;

import com.victor.storesystem.model.Produto;
import com.victor.storesystem.service.CategoriaService;
import com.victor.storesystem.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

        // Garante que a categoria associada ao produto seja válida
        produto.setCategoria(
                categoriaService.findById(produto.getCategoria().getId())
                        .orElseThrow(() -> new RuntimeException("Categoria não encontrada")));

        produtoService.save(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Produto produto = produtoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        model.addAttribute("produto", produto);
        model.addAttribute("categorias", categoriaService.findAll());
        return "produtos/form";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("produto") Produto produtoAtualizado,
                            BindingResult bindingResult, Model model) {
        // Verifica se há erros de validação
        if (bindingResult.hasErrors()) {
            model.addAttribute("categorias", categoriaService.findAll()); // Recarrega as categorias
            return "produtos/form";
        }

        // Garante que a categoria associada ao produto seja válida
        produtoAtualizado.setCategoria(
                categoriaService.findById(produtoAtualizado.getCategoria().getId())
                        .orElseThrow(() -> new RuntimeException("Categoria não encontrada")));

        // Garante que o ID do produto seja definido corretamente
        produtoAtualizado.setId(id);

        // Salva o produto atualizado
        produtoService.save(produtoAtualizado);

        // Redireciona para a lista de produtos
        return "redirect:/produtos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        produtoService.delete(id);
        return "redirect:/produtos";
    }

}