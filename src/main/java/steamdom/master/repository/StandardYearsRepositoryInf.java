package steamdom.master.repository;

import steamdom.master.model.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/* 
* Author : petrik
*/

public interface StandardYearsRepositoryInf {

    List<StandardYears> findAll(int page, int limit);

    Long size();

    StandardYears save(@NotNull StandardYears standardyears);

    StandardYears update(@NotNull Long id, StandardYears standardyears);

    StandardYears findById(@NotNull Long id);

    StandardYears destroy(@NotNull Long id);

    // boolean existByName(String name);

}