package com.three_iii.company.presentation.dtos;

import com.three_iii.company.application.dtos.product.ProductDto;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequest {

    @NotNull
    UUID companyId;
    @NotNull
    UUID hubId;
    @NotNull
    String name;
    String description;
    @NotNull
    int quantity;

    public ProductDto toDTO() {
        return ProductDto.create(this.companyId, this.hubId, this.name, this.description,
            this.quantity);
    }

}
