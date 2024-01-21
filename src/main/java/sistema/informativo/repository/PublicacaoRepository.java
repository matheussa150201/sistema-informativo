package sistema.informativo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sistema.informativo.model.Publicacao;

import java.util.List;

@Repository
public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {

    @Query("SELECT p FROM Publicacao p ORDER BY p.dataCriacaoEdicao DESC")
    List<Publicacao> findAllOrderedByDataCriacaoEdicaoAsc();
}
