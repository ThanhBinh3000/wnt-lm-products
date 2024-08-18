package vn.com.gsoft.product.model.dto.elastichsearch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "products")
public class HangHoaES {
    @Id
    private String id;
    private String name;
}
