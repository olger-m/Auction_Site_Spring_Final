package com.sda.auction_site.controller;


import com.sda.auction_site.model.Auction;
import com.sda.auction_site.model.Category;
import com.sda.auction_site.service.AuctionService;
import com.sda.auction_site.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/biddingapp/v1/admin/")
@AllArgsConstructor
public class AdminController {


    private final CategoryService categoryService;

    private final AuctionService auctionService;

    @PostMapping("/category/register")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }
    @DeleteMapping("/category/delete")
    public void deleteCategory(@RequestParam(value = "id") Long id){
        categoryService.deleteCategoryById(id);
    }

    @PutMapping("/category/update")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category){
        categoryService.updateCategory(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @GetMapping("auction/all")
    public ResponseEntity<?> getAllAuctions(){

        return ResponseEntity.ok().body(auctionService.listActiveAuctions());
    }
}
