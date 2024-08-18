package vn.com.gsoft.product.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.product.model.dto.elastichsearch.HangHoaES;
@Repository
public interface HangHoaESRepository extends ElasticsearchRepository<HangHoaES, String> {

}
