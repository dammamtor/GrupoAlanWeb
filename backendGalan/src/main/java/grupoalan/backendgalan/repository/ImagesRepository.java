package grupoalan.backendgalan.repository;

import grupoalan.backendgalan.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, Long> {
    List<Images> findByRef(String number);

    @Query(value = "SELECT * FROM images WHERE img_max LIKE CONCAT('%/', :imgMax) LIMIT 1", nativeQuery = true)
    Images findByImgMaxEndingWithExact(@Param("imgMax") String imgMax);
}
