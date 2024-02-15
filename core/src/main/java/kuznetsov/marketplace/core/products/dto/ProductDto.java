package kuznetsov.marketplace.core.products.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long id;

    private String title;

    private String description;

    private String techDescription;

    private Long categoryId;

    private String categoryName;

    private double price;

    private String sellerName;

    private String sellerAddress;

    private String sellerCountry;

    private String sellerEmail;

    private ProductPreorderDto preorder;

    private List<String> imageUrls;

}
