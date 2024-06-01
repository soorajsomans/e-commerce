package com.syntacticsuger.productservice.services;

import com.syntacticsuger.productservice.dtos.FakeStoreProductDto;
import com.syntacticsuger.productservice.models.Category;
import com.syntacticsuger.productservice.models.Product;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService{
    private RestTemplate restTemplate;

    FakeStoreProductService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    private Product convertFakeStoreDtoToProduct(FakeStoreProductDto dto){
        Product product = new Product();
        product.setId(dto.getId());
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        product.setImage(dto.getImage());

        Category category = new Category();
        category.setDescription(dto.getCategory());

        product.setCategory(category);

        return product;
    }

    private FakeStoreProductDto convertProdToFakeStoreDto(Long id, Product product) {
        FakeStoreProductDto prodToFakeStoreDto = new FakeStoreProductDto();
        prodToFakeStoreDto.setCategory(product.getCategory().getDescription());
        prodToFakeStoreDto.setTitle(product.getTitle());
        prodToFakeStoreDto.setPrice(product.getPrice());
        prodToFakeStoreDto.setDescription(product.getImage());
        prodToFakeStoreDto.setImage(product.getImage());
        prodToFakeStoreDto.setId(id);
        return prodToFakeStoreDto;
    }
    @Override
    public Product getProductById(Long id) {
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/"+ id, FakeStoreProductDto.class);
        if(null == fakeStoreProductDto){
            return null;
        }
        return convertFakeStoreDtoToProduct(fakeStoreProductDto);
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreProductDto[].class);

        List<Product> products = new ArrayList<>();

        for(FakeStoreProductDto fakeStoreProductDto: fakeStoreProductDtos){
            products.add(convertFakeStoreDtoToProduct(fakeStoreProductDto));
        }
        return products;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        FakeStoreProductDto prodToFakeStoreDto = convertProdToFakeStoreDto(id, product);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(prodToFakeStoreDto, FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor = new HttpMessageConverterExtractor(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto fakeStoreProductDto = restTemplate.execute("https://fakestoreapi.com/products/"+id, HttpMethod.PUT, requestCallback, responseExtractor);

        return convertFakeStoreDtoToProduct(fakeStoreProductDto);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        FakeStoreProductDto prodToFakeStoreDto = convertProdToFakeStoreDto(id, product);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(prodToFakeStoreDto, FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor = new HttpMessageConverterExtractor(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto fakeStoreProductDto = restTemplate.execute("https://fakestoreapi.com/products/"+id, HttpMethod.PATCH, requestCallback, responseExtractor);
        return  convertFakeStoreDtoToProduct(fakeStoreProductDto);
    }

    @Override
    public void deleteProduct(Long id) {
        restTemplate.delete("https://fakestoreapi.com/products/"+id);
    }

    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto prodToFakeStoreDto = convertProdToFakeStoreDto(null, product);
        FakeStoreProductDto fakeStoreProductDto = restTemplate.postForObject("https://fakestoreapi.com/products", prodToFakeStoreDto, FakeStoreProductDto.class);
        return convertFakeStoreDtoToProduct(fakeStoreProductDto);
    }


}
