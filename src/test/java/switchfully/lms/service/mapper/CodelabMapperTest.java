package switchfully.lms.service.mapper;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import switchfully.lms.domain.Codelab;
import switchfully.lms.domain.Submodule;
import switchfully.lms.repository.CodelabRepository;
import switchfully.lms.repository.SubmoduleRepository;
import switchfully.lms.service.dto.CodelabInputDto;
import switchfully.lms.service.dto.CodelabOutputDto;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CodelabMapperTest {

    @Autowired
    private CodelabRepository codelabRepository;
    @Autowired
    private SubmoduleRepository submoduleRepository;

    @BeforeEach
    public void setUp() {
        codelabRepository.deleteAll();
        codelabRepository.flush();
    }

    @Test
    void givenValidCodelabInputDto_whenInputDtoToCodelab_thenReturnCodelab() {
        CodelabMapper codelabMapper = new CodelabMapper(submoduleRepository);
        Submodule parentSubmodule = new Submodule("Parent Submodule");
        submoduleRepository.save(parentSubmodule);

        // given
        CodelabInputDto codelabInputDto = new CodelabInputDto("Test Codelab", "Details about the codelab", parentSubmodule.getId());

        // expected
        Codelab expectedCodelab = new Codelab("Test Codelab", "Details about the codelab", parentSubmodule);

        // when
        Codelab resultCodelab = codelabMapper.inputDtoToCodelab(codelabInputDto);

        // then
        assertThat(resultCodelab).isEqualTo(expectedCodelab);
        assertThat(resultCodelab.getTitle()).isEqualTo(expectedCodelab.getTitle());
    }

    @Test
    void givenValidCodelab_whenCodelabToOutputDto_thenReturnOutputDto() {
        CodelabMapper codelabMapper = new CodelabMapper(submoduleRepository);
        Submodule parentSubmodule = new Submodule("Parent Submodule");
        submoduleRepository.save(parentSubmodule);

        // given
        Codelab codelab = new Codelab("Test Codelab", "Details about the codelab", parentSubmodule);
        codelabRepository.save(codelab);

        // expected
        CodelabOutputDto expectedOutputDto = new CodelabOutputDto(
                codelab.getId(),
                codelab.getTitle(),
                codelab.getDetails(),
                parentSubmodule.getId()
        );

        // when
        CodelabOutputDto resultOutputDto = codelabMapper.codelabToOutputDto(codelab);

        // then
        assertThat(resultOutputDto).isEqualTo(expectedOutputDto);
    }

}