package com.example.granny.service;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.entities.Product;
import com.example.granny.domain.models.binding.ProductBindingModel;
import com.example.granny.domain.models.service.ProductServiceModel;
import com.example.granny.domain.models.view.CartViewModel;
import com.example.granny.domain.models.view.OrderedItemViewModel;
import com.example.granny.domain.models.view.ProductAllViewModel;
import com.example.granny.error.ProductAlreadyExistsException;
import com.example.granny.error.ProductNotFoundException;
import com.example.granny.repository.ProductRepository;
import com.example.granny.service.api.CloudinaryService;
import com.example.granny.service.api.ProductService;
import com.example.granny.validation.api.ProductValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    static final ProductNotFoundException PRODUCT_NOT_FOUND =
            new ProductNotFoundException("The cause you requested could not be found");
    static final ProductAlreadyExistsException PRODUCT_ALREADY_EXISTS =
            new ProductAlreadyExistsException("A product with the same name already exists");

    private final ProductRepository productRepository;
    private final ProductValidationService productValidation;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository, ProductValidationService productValidation, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.productValidation = productValidation;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    public ProductServiceModel create(ProductBindingModel model) throws IOException {
        if (!productValidation.isValid(model)) {
            throw new IllegalArgumentException();
        }
        Product product = this.productRepository
                .findByName(model.getName())
                .orElse(null);

        if (product != null) {
            throw PRODUCT_ALREADY_EXISTS;
        }
        product = this.modelMapper.map(model, Product.class);

        String image = GlobalConstants.DEFAULT_IMG;
        MultipartFile file = model.getImageUrl();
        if (!file.isEmpty()) {
            String imageId = UUID.randomUUID().toString();
            product.setImageId(imageId);
            image = cloudinaryService.uploadImage(file, imageId);
        }
        product.setImageUrl(image);
        product = this.productRepository.save(product);

        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public List<ProductServiceModel> findAll() {
        return this.productRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel findById(Integer id) {
        return this.productRepository.findById(id)
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .orElseThrow(() -> new ProductNotFoundException("The product you requested could not be found."));
    }


    @Override
    public ProductServiceModel edit(Integer id, ProductBindingModel model) throws IOException {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> PRODUCT_NOT_FOUND);

        MultipartFile file = model.getImageUrl();

        if (!file.isEmpty()) {
            if (!product.getImageUrl().equals(GlobalConstants.DEFAULT_IMG)) {
                cloudinaryService.deleteImage(product.getImageId());
            }
            String image = cloudinaryService.uploadImage(file, product.getImageId());
            product.setImageUrl(image);
        }

        product.setName(model.getName());
        product.setDescription(model.getDescription());
        product.setPrice(model.getPrice());

        return this.modelMapper.map(this.productRepository.saveAndFlush(product), ProductServiceModel.class);
    }

    @Override
    public void delete(Integer id) {
        Product product = this.productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("The product you requested could not be found."));

        this.productRepository.delete(product);
    }

    @Override
    public List<ProductServiceModel> findFourRandomProducts(Integer id) {
        List<ProductServiceModel> list = this.productRepository.findFiveRandomProducts()
                .stream()
                .filter(p -> p.getId() != id)
                .limit(4)
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public List<CartViewModel> findAll(Map<Integer, Integer> products) {
        List<CartViewModel> cart = new ArrayList<>();

        products.entrySet().stream().forEach(i -> {
            Product p = productRepository.findById(i.getKey())
                    .orElseThrow(() -> PRODUCT_NOT_FOUND);
            ProductAllViewModel product = modelMapper.map(p, ProductAllViewModel.class);
            int quantity = i.getValue();
            CartViewModel m = new CartViewModel(product, quantity);
            cart.add(m);
        });
        return cart;
    }

    @Override
    public BigDecimal findTotal(Map<Integer, OrderedItemViewModel> products) {
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Integer, OrderedItemViewModel> entry : products.entrySet()) {
            OrderedItemViewModel p = entry.getValue();
            total = total.add(p.getSum());
        }

        return total;
    }
}
