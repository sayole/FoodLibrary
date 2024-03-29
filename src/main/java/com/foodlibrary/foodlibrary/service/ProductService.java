package com.foodlibrary.foodlibrary.service;

import com.foodlibrary.foodlibrary.entity.Product;
import com.foodlibrary.foodlibrary.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product getOneProduct(String prdlstreportno){
        return repository.findByPrdlstreportno(prdlstreportno);
    }

    public Product saveProduct(Product product){
        return repository.save(product);
    }

    public List<Product> saveProducts(List<Product> products){
        return repository.saveAll(products);
    }

    public List<Product> getProducts(){
        return repository.findAll();
    }

    public Product getProductById(String id){
        System.out.println(repository.findById(id).orElse(null).toString());
        return repository.findById(id).orElse(null);
    }
    public String deleteProduct(String id){
        repository.deleteById(id);
        return "product removed" + id;
    }

    public Product updateProduct(Product product){
        Product existingProduct = repository.findById(product.getPrdlstreportno()).orElse(null);
        existingProduct.setPrdlstnm(product.getPrdlstnm());
        existingProduct.setManufacture(product.getManufacture());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setImg((product.getImg()));
        existingProduct.setRawmtrl(product.getRawmtrl());
        existingProduct.setNutrient(product.getNutrient());
        existingProduct.setAllergy(product.getAllergy());
        existingProduct.setDisease(product.getDisease());
        existingProduct.setProducthashtag(product.getProducthashtag());
        existingProduct.setLikecount(product.getLikecount());
        existingProduct.setZzimcount(product.getZzimcount());
        existingProduct.setSearchcount(product.getSearchcount());
        existingProduct.setStaraverage(product.getStaraverage());

        return repository.save(existingProduct);
    }


    public List<Product> getProductsAsSearch(String name){
        return repository.findByPrdlstnmContaining(name);
    }
    public Product getProductByName(String name){ return repository.findByPrdlstnm(name);}
    //이거 뭔지 찾을것
    public int changeCount(int count,String prdlstreportno){
        return repository.setFixedCount(count,prdlstreportno);
    }

    public List<Product> searchCategory(List<Product> products, String category){
        List<Product> tmpProducts = new ArrayList<Product>();
        for(Product product : products){
            if(product.getCategory().equals(category)) {
                tmpProducts.add(product);
            }
        }
        return tmpProducts;
    }

    public List<Product> searchAsCategory(String category,String filter){
        if(filter.equals("likecount")) {
            return repository.findByCategoryOrderByLikecountDesc(category);
        }else if(filter.equals("zzimcount")){
            return repository.findByCategoryOrderByZzimcountDesc(category);
        }else if(filter.equals("staraverage")){
            return repository.findByCategoryOrderByStaraverageDesc(category);
        }
        return null;
    }
}
