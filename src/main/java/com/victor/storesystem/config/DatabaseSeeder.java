package com.victor.storesystem.config;

import com.victor.storesystem.model.Categoria;
import com.victor.storesystem.model.Produto;
import com.victor.storesystem.repository.CategoriaRepository;
import com.victor.storesystem.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;

    @Override
    public void run(String... args) throws Exception {

        // Seeding Categories
        Categoria cat1 = new Categoria(null,"Eletrônicos", null);
        Categoria cat2 = new Categoria(null,"Livros", null);
        Categoria cat3 = new Categoria(null,"Roupas", null);
        categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3));

        // Seeding Products
        Produto p1 = new Produto(null,"Notebook Dell", 3500.00, 10, cat1);
        Produto p2 = new Produto(null,"Smartphone Samsung", 2200.00, 15, cat1);
        Produto p3 = new Produto(null,"O Senhor dos Anéis", 80.00, 30, cat2);
        Produto p4 = new Produto(null,"Camiseta Polo", 120.00, 50, cat3);
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4));
    }
}
